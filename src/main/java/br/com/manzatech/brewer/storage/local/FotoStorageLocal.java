package br.com.manzatech.brewer.storage.local;

import static java.nio.file.FileSystems.getDefault;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import br.com.manzatech.brewer.storage.FotoStorage;
import br.com.manzatech.brewer.utils.ImageUtils;

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

	@Override
	public String salvarTemporariamente(MultipartFile[] files) {
		String novoNome = null;
		if (null != files && files.length > 0) {
			MultipartFile file = files[0];
			novoNome = renomearArquivo(file.getOriginalFilename());
			try {
				file.transferTo(new File(this.localTemporario.toAbsolutePath().toAbsolutePath()
						+ getDefault().getSeparator() + novoNome));
			} catch (IllegalStateException | IOException e) {
				throw new RuntimeException("Erro ao salvar foto na pasta temporária", e);
			}
		}
		return novoNome;
	}

	private void criarPastas() {
		try {
			if (!Files.exists(this.local)) {
				Files.createDirectory(this.local);
			}

			this.localTemporario = getDefault().getPath(this.local.toString(), "temp");
			if (!Files.exists(this.localTemporario)) {
				Files.createDirectories(this.localTemporario);
			}
			this.checkMockFiles();
			
			if (logger.isDebugEnabled()) {
				logger.debug("Pastas para salvar fotos criadas com sucesso");
				logger.debug("Pasta default: " + this.local.toAbsolutePath());
				logger.debug("Pasta temporária: " + this.localTemporario.toAbsolutePath());
			}
		} catch (IOException e) {
			throw new RuntimeException("Erro ao criar pasta para salvar foto", e);
		}
	}

	private void checkMockFiles() {
		Path mockPath = this.local.resolve("mock.png");
		Path thumbPath = this.local.resolve("thumb_mock.png");
		File mock = mockPath.toFile();
		File thumb = thumbPath.toFile();
		try {
			if (!mock.exists()) {
				Files.copy(getClass().getClassLoader().getResourceAsStream("static/images/cerveja-mock.png"), mockPath);
			}
			if (!thumb.exists()) {
				Files.copy(getClass().getClassLoader().getResourceAsStream("static/images/thumbnail.cerveja-mock.png"),
						thumbPath);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Erro ao copiar mock files", e);
		}
	}

	private String renomearArquivo(String nomeOriginal) {
		String novoNome = UUID.randomUUID().toString() + "_" + nomeOriginal;
		if (logger.isDebugEnabled()) {
			logger.debug(
					String.format("Arquivo renomeado - Nome original: %s - Novo nome: %s", nomeOriginal, novoNome));
		}
		return novoNome;
	}

	@Override
	public byte[] recuperarFoto(String nome, boolean temp) {
		try {
			Path l = temp ? this.localTemporario : this.local;
			return Files.readAllBytes(l.resolve(nome));
		} catch (IOException e) {
			throw new RuntimeException("Erro lendo foto", e);
		}
	}

	@Override
	public void salvar(String foto) {
		Path tempPath = this.localTemporario.resolve(foto);
		Path newPath = this.local.resolve(foto);
		try {
			Files.move(tempPath, newPath);
			ImageUtils.resizeImage(newPath, this.local, new Dimension(50, 75));
		} catch (IOException e) {
			throw new RuntimeException("Erro salvando a foto", e);
		}
	}

}
