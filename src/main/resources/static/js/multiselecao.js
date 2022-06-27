class MultiSelecao {
	
	constructor() {
		this.btnMultiSelection = $('.js-multiselect-action')
		this.checkboxes = $('.js-selecao')
		this.selecionarTodos = $('.js-selecionar-todos')
	}
	
	iniciar() {
		this.btnMultiSelection.on('click', this.onBtnMultiSelectionClick.bind(this))
		this.selecionarTodos.on('change', this.onSelecionarTodosChange.bind(this))	
		this.checkboxes.on('click', this.onCheckboxChange.bind(this))
	}
	
	onCheckboxChange() {
		let selecionados = this.checkboxes.filter(':checked')
		this.selecionarTodos.prop('checked', selecionados.length == this.checkboxes.length)
		this.habilitarOuDesabilitarBotoesMultiSelection(selecionados.length > 0)		
	}
		
	onSelecionarTodosChange() {
		let status = this.selecionarTodos.prop('checked')
		this.checkboxes.prop('checked', status)
		this.habilitarOuDesabilitarBotoesMultiSelection(status)
	}
	
	onBtnMultiSelectionClick(evt) {
		evt.preventDefault()
		let botao = $(evt.currentTarget)
		let selecionados = this.checkboxes.filter(':checked')
		let ids = $.map(selecionados, (c) => {
			return $(c).data('id');
		});
		$.ajax({
			'url': botao.data('url'),
			'method': 'PUT',
			'data':  {
				'ids': ids,
				'status': botao.data('name')
			},
			success: () => {
				window.location.reload(true)				
			}
		});		
	}
	
	habilitarOuDesabilitarBotoesMultiSelection(status) {
		if (status) {
			this.btnMultiSelection.removeClass('disabled')
		} else {
			this.btnMultiSelection.addClass('disabled')			
		}		
	}
	
}

(() => {
	Brewer.MultiSelecao = new MultiSelecao()
	Brewer.MultiSelecao.iniciar()	
})()
