class ComboEstado {
	constructor(){
		this.combo = $('[name="endereco.estado"]')
		this.emitter = $({})
		this.on = this.emitter.on.bind(this.emitter)
	}
	enable() {
		this.combo.on('change', this.onEstadoChange.bind(this))
		let estadoId = this.combo.val()
		if (estadoId) {
			this.onEstadoChange.call(this)
		}
	}
	onEstadoChange() {
		this.emitter.trigger('alterado', this.combo.val())
	}
	value() {
		return this.combo.val()
	}
}

class ComboCidade {
	constructor(comboEstado){
		this.combo = $('[name="endereco.cidade"]')
		this.comboEstado = comboEstado
		this.img = $('.js-img-loading')
		this.inputCidade = $('#idCidadeSelecionada')
	}
	enable() {
		this.combo.on('change', this.onChange.bind(this))
		this.comboEstado.on('alterado', this.onEstadoChange.bind(this))
	}
	onChange() {
		this.inputCidade.val(this.combo.val())
	}
	onEstadoChange(event, estado) {
		this.inicializarCidade.call(this, estado)
	}
	inicializarCidade(estado) {
		this.combo.removeAttr('disabled')
		let url = this.combo.data('url')
		$('.js-option-cidade').remove()
		if (estado) {
			let response = $.ajax({
				url: url,
				method: 'GET',
				contentType: 'application/json',
				data: { estado: estado },
				beforeSend: this.beforeSend.bind(this),
				complete: this.onComplete.bind(this)
			})
			response.done(this.onDone.bind(this))			
		} else {
			this.combo.attr('disabled', true)
		}
		
	}
	beforeSend() {
		this.img.addClass('d-flex')
		this.img.removeClass('d-none')		
	}
	
	onComplete() {
		this.img.addClass('d-none')
		this.img.removeClass('d-flex')
	}
	
	onDone(cidades){
		let options = [];
		cidades.forEach((cidade) => {
			let selected = cidade.id == this.inputCidade.val() ? ' selected' : ''
			options.push(`<option value="${cidade.id}" class="js-option-cidade" ${selected}>${cidade.nome}</option>`)
		})
		this.combo.append(options.join(""))
	}
}

(() => {
	Brewer.ComboEstado = new ComboEstado()
	Brewer.ComboCidade = new ComboCidade(Brewer.ComboEstado)
	Brewer.ComboCidade.enable()
	Brewer.ComboEstado.enable()
})()