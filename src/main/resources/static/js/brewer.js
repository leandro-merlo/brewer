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

class MaskPhoneNumber {
	constructor() {
		this.phoneNumber = $('.js-phone-number')
	}
	
	enable() {
		const maskBehavior = (val) => {
		  return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
		}
		const maskOtions = {
		  onKeyPress: function(val, e, field, options) {
		      field.mask(maskBehavior.apply({}, arguments), options);
	      },
          clearIfNotMatch: true
		}
		this.phoneNumber.mask(maskBehavior, maskOtions);		
	}
	

}


(() =>{
    Brewer.MaskMoney = new MaskMoney()
    Brewer.MaskMoney.enable()
    Brewer.MaskPhoneNumber = new MaskPhoneNumber()
    Brewer.MaskPhoneNumber.enable()
})();
