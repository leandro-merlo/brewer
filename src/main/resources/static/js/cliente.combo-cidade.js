class ComboEstado {
	constructor(){
		this.combo = $('[name="estado"]')
		this.emitter = $({})
		this.on = this.emitter.on.bind(this.emitter)
	}
	enable() {
		this.combo.on('change', this.onEstadoChange.bind(this))
	}
	onEstadoChange() {
		this.emitter.trigger('alterado', this.combo.val())
	}
}

class ComboCidade {
	constructor(comboEstado){
		this.combo = $('[name="cidade"]')
		this.comboEstado = comboEstado
		this.img = $('.js-img-loading')
	}
	enable() {
		this.comboEstado.on('alterado', this.onEstadoChange.bind(this))
	}
	onEstadoChange(event, estado) {
		this.combo.removeAttr('disabled')
		$('.js-option-cidade').remove()
		if (estado) {
			let response = $.ajax({
				url: this.combo.data('url'),
				method: 'GET',
				contentType: 'application/json',
				data: { 'estado': estado },
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
			options.push(`<option value="${cidade.id}" class="js-option-cidade">${cidade.nome}</option>`)
		})
		this.combo.append(options.join(""))
	}
}

(() => {
	Brewer.ComboEstado = new ComboEstado()
	Brewer.ComboEstado.enable()
	Brewer.ComboCidade = new ComboCidade(Brewer.ComboEstado)
	Brewer.ComboCidade.enable()
})()