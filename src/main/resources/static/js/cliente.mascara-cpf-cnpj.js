class MaskCpfCnpj {
	constructor() {
		this.radioTipoPessoa = $('.js-radio-tipo-pessoa')
		this.labelCpfCnpj = $('[for=documento]')
		this.inputDocumento = $('#documento')
	}
	
	enable() {
		this.radioTipoPessoa.on('change', this.onTipoPessoaChanged.bind(this))
		let tipoPesssoaSelecionado = this.radioTipoPessoa.filter(':checked')
		if (tipoPesssoaSelecionado.length != 0) {
			this.aplicarMascara.call(this, $(tipoPesssoaSelecionado[0]))	
		} else {
			this.inputDocumento.val("")
			this.inputDocumento.attr("disabled", true)
			this.radioTipoPessoa.prop("checked", false)			
		}
	}
	
	onTipoPessoaChanged(event) {
		let tipoSelecionado = $(event.target)
		this.inputDocumento.val("")
		this.aplicarMascara.call(this, tipoSelecionado)
	}
	
	aplicarMascara(tipoSelecionado){
		this.labelCpfCnpj.html(tipoSelecionado.data('documento'))
		this.inputDocumento.mask(tipoSelecionado.data('mascara'), {
            clearIfNotMatch: true,			
		})
		this.inputDocumento.attr('disabled', false)		
	}
}

(() => {
    Brewer.MaskCpfCnpj = new MaskCpfCnpj()
    Brewer.MaskCpfCnpj.enable()
})()