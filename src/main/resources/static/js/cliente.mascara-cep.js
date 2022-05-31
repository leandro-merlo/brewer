class CepMascara {
	constructor() {
		this.input = $('.js-cep')
	}
	enable() {
		this.input.mask('00.000-000')
	}
}

(() => {
	Brewer.CepMascara = new CepMascara()
	Brewer.CepMascara.enable()
})()