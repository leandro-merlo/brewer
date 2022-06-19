class UploadFoto {

    constructor() {
        this.foto = $("#foto")
        this.contentType = $("#contentType")
        this.wrapper = $("#wrapper")
        this.inputFoto = $("#inputFoto")
        this.url = $('#upload').data('url')
        this.csrfToken = $("meta[name='_csrf']").attr("content")
		this.csrfHeader = $("meta[name='_csrf_header']").attr("content")
		
        this.upload = new Dropzone("#upload", {
            dictRemoveFile: 'Remover',
            previewTemplate: `
            <div class="dz-preview dz-file-preview">
                <div class="dz-details">
                    <img data-dz-thumbnail />
                </div>
            </div>
            `,
            url: this.url,            
            headers: {
				[this.csrfHeader]: this.csrfToken
			},
            uploadMultiple: false,
            maxFiles: 1,
            paramName: "files",
            acceptedFiles: "image/gif,image/png,image/jpeg",
            addRemoveLinks: true,
            addedfiles: this.onAddedFile.bind(this),
            success: this.onSuccess.bind(this),
            removedfile: this.onRemovedFile.bind(this)
        })

        this.inputFoto.on('change', this.onInputFotoChange.bind(this))
        if (this.foto.val()) {
            let mock = { name: this.foto.val(), type: this.contentType.val() }
            this.upload.displayExistingFile(mock, this.url + "/temp/" + mock.name)
            this.wrapper.addClass("bw-hidden")
        }
    }

    onInputFotoChange = (evt) => {
        let target = evt.target;
        this.upload.addFile(target.files[0]);					
    }

    onAddedFile = (files) => {
        var f = files[files.length - 1].name;
        for (var i = 0; i <= files.length - 1; i++) {
            if (f != files[i].name)
                this.upload.removeFile(files[i]);
        }        
    }

    onSuccess = (resposta) => {
        let response = JSON.parse(resposta.xhr.response)
        let nome = response.nome
        let contentType = response.contentType
        this.foto.val(nome)
        this.contentType.val(contentType)
        this.wrapper.addClass("bw-hidden")
    }

    onRemovedFile = () => {
        this.foto.val("")
        this.contentType.val("")
        $('.dz-preview').remove();				
        this.wrapper.removeClass("bw-hidden")
    }
}

(() => {
    Brewer.UploadFoto = new UploadFoto()
})()