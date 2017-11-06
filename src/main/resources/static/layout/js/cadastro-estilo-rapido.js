var Brewer = Brewer || {};

Brewer.EstiloCadastroRapido = (function () {
    function EstiloCadastroRapido() {
        this.modal = $('#modalCadastroRapidoEstilo');
        this.botaoSalvar = this.modal.find('.js-btn-salvar-cadastro-estilo-rapido');
        this.form = this.modal.find('form');
        this.input = $('#input-estilo-rapido-nome');
        this.mensagemErro = this.modal.find('.js-mensagem-erro-estilo-rapido');
        this.action = this.form.attr('action');
        this.combo = $('#input-produto-estilo');
    }

    EstiloCadastroRapido.prototype.iniciar = function() {
        this.form.on('submit', function(evt){
            evt.preventDefault();
        });
        this.modal.on('shown.bs.modal', onModalShow.bind(this));
        this.modal.on('hide.bs.modal', onModalClose.bind(this));
        this.botaoSalvar.on('click', onBotaoSalvarClick.bind(this));
    }

    function onModalShow() {
        this.input.focus();
    }

    function onModalClose(){
        this.input.val("");
        this.mensagemErro.addClass('hidden');
        this.form.find('.form-group').removeClass('has-error');
    }

    function onBotaoSalvarClick() {
        var nome = this.input.val().trim();
        $.ajax({
            url: this.action,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ nome: nome }),
            error: onErroSalvandoEstilo.bind(this),
            success: onEstiloSalvo.bind(this)
        });
    }

    function onErroSalvandoEstilo(obj) {
        var msgErro = obj.responseText;
        this.mensagemErro.html(msgErro);
        this.mensagemErro.removeClass('hidden');
        this.form.find('.form-group').addClass('has-error');
    }

    function onEstiloSalvo(estilo) {
        this.combo.append('<option value=' + estilo.id +'>' + estilo.nome + '</option>')
        this.combo.val(estilo.id);
        this.modal.modal('hide');
    }
    return EstiloCadastroRapido;
}());

$(function(){

    var estiloCadastroRapido = new Brewer.EstiloCadastroRapido();
    estiloCadastroRapido.iniciar();

});