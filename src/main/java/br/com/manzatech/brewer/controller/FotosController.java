package br.com.manzatech.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import br.com.manzatech.brewer.dto.FotoDTO;
import br.com.manzatech.brewer.storage.FotoStorage;
import br.com.manzatech.brewer.storage.FotosStorageRunnable;

@RestController
@RequestMapping("/fotos")
public class FotosController {

	@Autowired
	private FotoStorage fotoStorage;
	
	@PostMapping
	public DeferredResult<FotoDTO> upload(MultipartFile[] files) {
		DeferredResult<FotoDTO> resultado = new DeferredResult<>();
		
		Thread t = new Thread(new FotosStorageRunnable(files, resultado, this.fotoStorage));
		t.start();
		
		return resultado;
	}
	
	@GetMapping(value = "/temp/{nome:.*}", produces = {
		MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE
	})
	@ResponseBody
	public byte[] recuperarFotoTemporaria(@PathVariable String nome) {
		return this.fotoStorage.recuperarFoto(nome, true);
	}
	
	@GetMapping(value = "/thumbnail/{nome:.*}", produces = {
		MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE
	})
	@ResponseBody
	public byte[] recuperarThumb(@PathVariable String nome) {
		return this.fotoStorage.recuperarFoto("thumb_" + nome, false);
	}	
}
