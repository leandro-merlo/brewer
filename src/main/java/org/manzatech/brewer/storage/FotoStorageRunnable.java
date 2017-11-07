package org.manzatech.brewer.storage;

import com.sun.org.apache.xpath.internal.operations.Mult;
import org.manzatech.brewer.dto.FotoDTO;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

public class FotoStorageRunnable implements Runnable {

    private MultipartFile[] files;
    private DeferredResult<FotoDTO> result;

    public FotoStorageRunnable(MultipartFile[] files, DeferredResult<FotoDTO> result) {
        this.files = files;
        this.result = result;
    }

    @Override
    public void run() {
        FotoDTO dto = new FotoDTO(files[0].getOriginalFilename(), files[0].getContentType());
        result.setResult(dto);
    }
}
