$(document).ready(function () {
    if ($.fn.datepicker !== undefined) {
        $.fn.datepicker.defaults.language = "pt-BR";
    }
});

var Brewer = Brewer || {};

Brewer.Components = (function () {
    function Components() {
        this.decimal = $(".js-masked-decimal");
        this.integer = $(".js-masked-integer");
        this.zip = $(".js-cep");
        this.doc = $(".js-cpf-cnpj");
        this.phone = $(".js-telefone-celular");
        this.date = $('.js-datepicker');
        this.radioTipoPessoa = $('.js-tipo-pessoa');
        this.password = $('.js-password');
        this.status = $('.js-status');
    }
    Components.prototype.enable = function () {
        if (this.decimal){
            var decimal = new Brewer.NumericMask(this.decimal);
            if (decimal) {
                decimal.placeholder = '0,00';
                decimal.min = 0;
                decimal.enable();
            }
        }
        if (this.integer) {
            var integer = new Brewer.NumericMask(this.integer);
            if (integer) {
                integer.placeholder = '0';
                integer.min = 0;
                integer.digits = 0;
                integer.enable();
            }
        }
        if (this.zip) {
            var zip = new Brewer.ZipMask(this.zip);
            zip.enable();
        }
        if (this.doc) {
            var doc = new Brewer.CPFCNPJMask(this.doc, this.radioTipoPessoa);
            doc.enable();
        }
        if (this.phone) {
            var phone = new Brewer.PhoneMask(this.phone);
            phone.enable();
        }
        if (this.date) {
            var date = new Brewer.DatePicker(this.date);
            date.enable();
        }
        if (this.radioTipoPessoa) {
            this.radioTipoPessoa.find('input[type="radio"]').on('change', onRadioTipoPessoaChange.bind(this));
            var tipoPessoaSelecionada =  this.radioTipoPessoa.find('input[type="radio"]').filter(':checked')[0];
            if (tipoPessoaSelecionada) {
                aplicarMascara.call(this, tipoPessoaSelecionada);
            }
        }
        if (this.password){
            var password = new Brewer.Password(this.password);
            password.enable();
        }
        if (this.status){
            var status = new Brewer.Status(this.status);
            status.enable();
        }
    }

    function onRadioTipoPessoaChange(evt){
        var radios = this.radioTipoPessoa.find('input[type="radio"]');
        $(radios).each(function () {
            $(this).prop('checked', false);
        });
        var target = $(evt.target);
        target.prop('checked', true);
        this.doc.val(null);
        this.doc.removeAttr('disabled');
        aplicarMascara.call(this, target);
    }
    
    function aplicarMascara(tipoPessoaSelecionada) {
        var target = $(tipoPessoaSelecionada);
        var value = target.val();
        this.doc.inputmask('remove');

        var label = target.data('documento');
        var mask = target.data('mascara');

        if (value == 'FISICA') {
            $('.js-label-documento').text(label);
            this.doc.inputmask(mask);
        } else {
            $('.js-label-documento').text(label);
            this.doc.inputmask({
                mask: mask,
                skipOptionalPartCharacter: '',
                clearMaskOnLostFocus: true,
                clearIncomplete: true,
                autoUnmask: true,
            });
        }
    }
    return Components;
}());



$(document).ready(function () {
    var Components = new Brewer.Components();
    Components.enable();
});