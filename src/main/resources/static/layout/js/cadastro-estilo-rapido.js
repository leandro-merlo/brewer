$(function(){
    var modal = $('#modalCadastroRapidoEstilo');
    var botaoSalvar = modal.find('.js-btn-salvar-cadastro-estilo-rapido');
    var form = modal.find('form');
    var input = $('#input-estilo-rapido-nome');
    var mensagemErro = modal.find('.js-mensagem-erro-estilo-rapido');
    form.on('submit', function(evt){
        evt.preventDefault();
    });
    var action = form.attr('action');

    modal.on('shown.bs.modal', onModalShow);
    modal.on('hide.bs.modal', onModalClose);
    botaoSalvar.on('click', onBotaoSalvarClick);

    function onModalShow() {
        input.focus();
    }

    function onModalClose(){
        input.val("");
        mensagemErro.addClass('hidden');
        form.find('.form-group').removeClass('has-error');
    }
    
    function onBotaoSalvarClick() {
        var nome = input.val().trim();
        $.ajax({
            url: action,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ nome: nome }),
            error: onErroSalvandoEstilo,
            success: onEstiloSalvo
        });
    }
    
    function onErroSalvandoEstilo(obj) {
        var msgErro = obj.responseText;
        mensagemErro.html(msgErro);
        mensagemErro.removeClass('hidden');
        form.find('.form-group').addClass('has-error');
    }
    
    function onEstiloSalvo(estilo) {
        var combo = $('#input-produto-estilo');
        combo.append('<option value=' + estilo.id +'>' + estilo.nome + '</option>')
        combo.val(estilo.id);
        modal.modal('hide');
    }
});