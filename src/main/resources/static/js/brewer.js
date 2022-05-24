let Brewer = {};

class MaskMoney {
    constructor(){
        this.decimal = $('.js-decimal')
        this.integer = $('.js-integer')
        this.sku = $('.js-sku')
    }

    enable() {
        this.decimal.mask("#.##0,00", {
            reverse: true,
            clearIfNotMatch: true
        })
        this.integer.mask("#.##0", {
            reverse: true,
            clearIfNotMatch: true
        });
        this.sku.mask("ZZ0000", {
            clearIfNotMatch: true,
            translation: {
                'Z': {
                  pattern: /[A-Za-z]/, optional: false
                }
            },
            onKeyPress: function(text, event, currentField, options){
				currentField.val(text.toUpperCase())
            },
        })
    }
}

(() =>{
    Brewer.MaskMoney = new MaskMoney()
    Brewer.MaskMoney.enable();  
})();
