package org.manzatech.brewer.storage;

import com.sun.org.apache.xpath.internal.operations.Mult;
import org.manzatech.brewer.dto.FotoDTO;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

public class FotoStorageRunnable implements Runnable {

    private MultipartFile[] files;
    private DeferredResult<FotoDTO> result;
    private FotoStorage fotoStorage;

    public FotoStorageRunnable(MultipartFile[] files, DeferredResult<FotoDTO> result, FotoStorage fotoStorage) {
        this.files = files;
        this.result = result;
        this.fotoStorage = fotoStorage;
    }

    @Override
    public void run() {
        String novoNome = fotoStorage.salvarTemporariamente(files);
        FotoDTO dto = new FotoDTO(novoNome, files[0].getContentType());
        result.setResult(dto);
    }
}
