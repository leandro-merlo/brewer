var Brewer = Brewer || {};

Brewer.ComboEstado = (function () {
    function ComboEstado() {
        this.combo = $('#input-cliente-estado');
        this.emmiter = $({});
        this.on = this.emmiter.on.bind(this.emmiter);
    }

    ComboEstado.prototype.iniciar = function () {
        this.combo.on('change', onEstadoAlterado.bind(this));
    }
    
    function onEstadoAlterado() {
        this.emmiter.trigger('alterado', this.combo.val());
    }

    return ComboEstado;
}());

Brewer.ComboCidade = (function () {

    function ComboCidade(comboEstado) {
        this.comboEstado = comboEstado;
        this.combo = $('#input-cliente-cidade');
        this.img = $('.js-image-loading');
        this.img.hide();
    }

    ComboCidade.prototype.iniciar = function () {
        this.comboEstado.on('alterado', onEstadoAlterado.bind(this));
        var idEstado = this.comboEstado.combo.val();
        if (idEstado) {
            onEstadoAlterado.call(this, undefined, idEstado);
        }
    }

    function onEstadoAlterado(evento, codigoEstado) {
        this.combo.attr('disabled', 'disabled');
        var resposta = $.ajax({
            url: this.combo.data('url'),
            method: 'get',
            contentType: 'application/json',
            data: {
                estado: codigoEstado
            },
            beforeSend: iniciarRequisicao.bind(this),
            complete: finalizarRequisicao.bind(this),
        });

        resposta.done(onBuscarCidadesFinalizado.bind(this));
    }

    function onBuscarCidadesFinalizado(cidades) {
        var options = [];
        options.push('<option value="">Selecione uma cidade</option>');
        var id = $('#id-cidade-selecionada').val();
        var idValido = false;
        cidades.forEach(function (cidade) {
            options.push('<option value="' + cidade.id + '">' + cidade.nome + '</option>');
            if (cidade.id == id) idValido = true;
        });
        if (cidades.length > 0) {
            this.combo.removeAttr('disabled');
        }
        this.combo.html(options.join(''));
        if (id && idValido){
            this.combo.val(id);
        }
    }

    function iniciarRequisicao() {
        this.img.show();
    }

    function finalizarRequisicao() {
        this.img.hide();
    }
    
    return ComboCidade;
}());

$(function () {

    var comboEstado = new Brewer.ComboEstado();
    comboEstado.iniciar();

    var comboCidade = new Brewer.ComboCidade(comboEstado);
    comboCidade.iniciar();

});