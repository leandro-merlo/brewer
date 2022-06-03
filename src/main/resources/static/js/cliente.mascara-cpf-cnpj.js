class MaskCpfCnpj {
	constructor() {
		this.radioTipoPessoa = $('.js-radio-tipo-pessoa')
		this.labelCpfCnpj = $('[for=documento]')
		this.inputDocumento = $('#documento')
	}
	
	enable() {
		this.radioTipoPessoa.on('change', this.onTipoPessoaChanged.bind(this))
		this.inputDocumento.val("")
		this.inputDocumento.attr("disabled", true)
		this.radioTipoPessoa.prop("checked", false)
	}
	
	onTipoPessoaChanged(event) {
		let tipoSelecionado = $(event.target)
		this.labelCpfCnpj.html(tipoSelecionado.data('documento'))
		this.inputDocumento.val('')
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