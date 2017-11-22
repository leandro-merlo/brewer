var Brewer = Brewer || {};

Brewer.UploadFoto = (function () {
    function UploadFoto() {
        this.inputNomeFoto = $("input[name=foto]");
        this.inputContentType = $("input[name=contentType]");
        this.source = $('#foto-cerveja').html();
        this.template = Handlebars.compile(this.source);
        this.upload = $('.js-upload');
        this.container = $('.js-foto-cerveja-wrapper');

    }

    UploadFoto.prototype.iniciar = function () {

        var settings = {
            type: 'POST',
            url: this.container.data('url-fotos'),
            'data-type': 'json',
            multiple: false,
            allow: '*.(jpg|jpeg|png|gif|bmp)',
            complete: onUploadComplete.bind(this)
        }

        UIkit.upload(this.upload, settings);

        if (this.inputNomeFoto.val()){
            /**
             * Simulando o conteúdo da resposta do upload, para manter a imagem que foi selecionado no formulário, no
             * caso de uma falha na validação do formulário.
             */
            var dummy = {
                nome: this.inputNomeFoto.val(),
                contentType: this.inputContentType.val()
            }
            var response = { response: JSON.stringify(dummy) };
            onUploadComplete.call(this, response);
        }

    };

    function onUploadComplete(response) {
        var json = JSON.parse(response.response);
        var html = this.template({nomeFoto: json.nome});
        this.inputNomeFoto.val(json.nome);
        this.inputContentType.val(json.contentType);
        this.upload.addClass("hidden");
        this.container.append(html);
        $('.js-remove-foto').on('click', onRemoveFotoClick.bind(this));
    }
    
    function onRemoveFotoClick() {
        $('.js-foto-cerveja').remove();
        this.upload.removeClass("hidden");
        this.inputNomeFoto.val("");
        this.inputContentType.val("");
    }

    return UploadFoto;
})();

$(function () {
   var uploadFoto = new Brewer.UploadFoto();
   uploadFoto.iniciar();
});