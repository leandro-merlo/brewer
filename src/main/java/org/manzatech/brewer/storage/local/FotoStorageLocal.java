package org.manzatech.brewer.storage.local;

import net.bytebuddy.implementation.bytecode.Throw;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import org.manzatech.brewer.storage.FotoStorage;
import org.manzatech.brewer.utils.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import static java.nio.file.FileSystems.getDefault;
import java.nio.file.Files;
import java.nio.file.Path;

public class FotoStorageLocal implements FotoStorage {

    private static final Logger logger = LoggerFactory.getLogger(FotoStorageLocal.class);


    private Path local;
    private Path localTemporario;

    public FotoStorageLocal() {
        this(getDefault().getPath(System.getProperty("user.home"), ".brewerfotos"));
    }

    public FotoStorageLocal(Path path) {
        this.local = path;
        criarPastas();
    }

    private void criarPastas() {
        try {
            Files.createDirectories(this.local);
            this.localTemporario = getDefault().getPath(this.local.toString(), "temp");
            Files.createDirectories(this.localTemporario);
            if (logger.isDebugEnabled()) {
                logger.debug("Pastas para upload de fotos criadas");
                logger.debug("Pasta default: " + this.local.toAbsolutePath());
                logger.debug("Pasta temporária: " + this.localTemporario.toAbsolutePath());
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar pasta para salvar as fotos. - " + this.local.toAbsolutePath() + " - " + System.getProperty("user.home"), e);
        }
    }

    @Override
    public String salvarTemporariamente(MultipartFile[] files) {
        if (files != null && files.length > 0) {
            MultipartFile file = files[0];
            try {
                String novoNome = renomearArquivo(file.getOriginalFilename());
                file.transferTo(
                    new File(
                        this.localTemporario.toAbsolutePath().toString() +
                        getDefault().getSeparator() + novoNome
                    )
                );
                return novoNome;
            } catch (IOException e) {
                throw new RuntimeException("Erro salvando a foto na pasta temporária", e);
            }
        }
        return null;
    }

    private byte[] recuperar(String nome, boolean tempFile) throws IOException {
        Path path = tempFile ? this.localTemporario : this.local;
        return Files.readAllBytes(path.resolve(nome));
    }

    @Override
    public byte[] recuperarFotoTemporaria(String nome) {
        try {
            return recuperar(nome, true);
        } catch (IOException e) {
            throw new RuntimeException("Erro lendo a foto temporária", e);
        }
    }


    @Override
    public byte[] recuperarFoto(String nome) {
        try {
            return recuperar(nome, false);
        } catch (IOException e) {
            throw new RuntimeException("Erro lendo a foto", e);
        }
    }

    @Override
    public void salvar(String foto) {
        try {
            Files.move(this.localTemporario.resolve(foto), this.local.resolve(foto));
        } catch (IOException e) {
            throw new RuntimeException("Erro movendo a foto para destino final", e);
        }
        try {
            Thumbnails.of(this.local.resolve(foto).toString()).size(40,68).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar miniatura da foto", e);
        }
    }

    private String renomearArquivo(String nomeOriginal){
        String newString = RandomString.generate() + "_" + nomeOriginal;
        if (logger.isDebugEnabled()) {
            logger.debug("O arquivo foi renomeado!");
            logger.debug(String.format("Nome original: %s - Novo nome: %s", nomeOriginal, newString));
        }
        return newString;
    }
}
