class EstiloCadastroRapido {
    constructor() {
        this.modal = $('#modalCadastroRapidoEstilo')
        this.botaoSalvar = $('.js-botao-salvar-cadastro-estilo-rapido')
        this.estiloForm = this.modal.find('form');
        this.submitUrl = this.estiloForm .attr('action')
        this.inputNomeEstilo = this.estiloForm .find('#nomeEstilo')
        this.mensagemAlertTemplate = `
        <div class="alert alert-danger alert-dismissible js-modal-estilo-alert" role="alert">
            <button type="button" class="close" data-dismiss="alert">
                <span aria-hidden="true">&times;</span>
            </button>
            <p id="modal-message" class="mb-0"></p>
        </div>    
        `
        this.mensagemErroLabel = this.estiloForm.find('label');
    
        this.containerMensagemErro = null
        this.mensagemErro = null    
    }

    enable = () => {
        this.estiloForm .on('submit', (e) => e.preventDefault())
        this.modal.on('shown.bs.modal', this.onModalShow)
        this.modal.on('hidden.bs.modal', this.onModalClose)
        this.botaoSalvar.on('click', this.onBotaoSalvarClick)
    }

    onModalShow = () => {
        this.inputNomeEstilo.focus()
    }

    onModalClose = () => {
		this.inputNomeEstilo.val("")
        if (this.containerMensagemErro) {
            this.containerMensagemErro.addClass('d-none')
        }
        this.mensagemErroLabel.removeClass('bw-invalid-label')
        this.inputNomeEstilo.removeClass('is-invalid')
        this.containerMensagemErro = null
        this.mensagemErro = null
	}

	onBotaoSalvarClick = () => {
		const nome = this.inputNomeEstilo.val().trim()
		$.ajax({
            url: this.submitUrl,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                'nome': nome
            }),
            error: this.onErroSalvandoEstilo,
            success: this.onEstiloSalvo
        })
	}

    onErroSalvandoEstilo = (jqXHR) => {
        const responseText = jqXHR.responseText;
        if (this.containerMensagemErro == null || this.containerMensagemErro.length == 0) {
            this.estiloForm.before(this.mensagemAlertTemplate)
            this.reloadMessage();
        }
        this.mensagemErro.html(responseText)
        this.mensagemErroLabel.addClass('bw-invalid-label')
        this.inputNomeEstilo.addClass('is-invalid')
    }

    reloadMessage = () => {
        this.containerMensagemErro = this.modal.find('.js-modal-estilo-alert')
        this.containerMensagemErro.on('closed.bs.alert', () => { this.containerMensagemErro = null })
        this.mensagemErro = this.containerMensagemErro.find('#modal-message')
    }

    onEstiloSalvo = (estilo) => {
        const select = $('#estilo')
        select.append(`<option value="${estilo.id}">${estilo.nome}</option>`);
        select.val(estilo.id);
        this.modal.modal('hide');
    }

}

(() => {
    Brewer.EstiloCadastroRapido = new EstiloCadastroRapido()
    Brewer.EstiloCadastroRapido.enable()
})()