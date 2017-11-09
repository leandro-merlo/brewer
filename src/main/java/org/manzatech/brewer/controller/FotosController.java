package org.manzatech.brewer.controller;

import org.manzatech.brewer.dto.FotoDTO;
import org.manzatech.brewer.storage.FotoStorage;
import org.manzatech.brewer.storage.FotoStorageRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/fotos")
@RestController
public class FotosController {

    @Autowired
    public FotoStorage fotoStorage;

    /*
     * DeferredResult é utilizado para postergar o retorno, tornando o mesmo assíncrono
     */
    @PostMapping
    public DeferredResult<FotoDTO> upload(@RequestParam("files[]") MultipartFile[] files){
        DeferredResult<FotoDTO> result = new DeferredResult<>();
        new Thread(new FotoStorageRunnable(files, result, fotoStorage)).start();
        return result;
    }

}
