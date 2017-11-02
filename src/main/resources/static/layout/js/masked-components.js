var Brewer = Brewer || {};

Brewer.NumericMask = (function () {
    function NumericMask(el) {
        this.element = el;
        this.type = 'decimal';
        this.placeholder = '0,00';
        this.radixPoint = ',';
        this.groupSeparator = '.';
        this.groupSize = 3;
        this.autoGroup = true;
        this.rightAlign = false;
        this.digits = 2;
        this.digitsOptional = false;
        this.allowMinus = false;
        this.integerOptional = false;
        this.max = undefined;
        this.min = undefined;
    }
    NumericMask.prototype.enable = function () {
        if (typeof this.element.inputmask === 'function') {
            this.element.inputmask(this.type, {
                'placeholder': this.placeholder,
                'radixPoint': this.radixPoint,
                'groupSeparator': this.groupSeparator,
                'groupSize': this.groupSize,
                'autoGroup': this.autoGroup,
                'rightAlign': this.rightAlign,
                'digits': this.digits,
                'digitsOptional': this.digitsOptional,
                'allowMinus': this.allowMinus,
                'integerOptional': this.integerOptional,
                'max': this.max,
                'min': this.min
            });
        }
    };
    return NumericMask;
}());

Brewer.CPFCNPJMask = (function () {
    function CPFCNPJMask(el) {
        this.element = el;
    }
    CPFCNPJMask.prototype.enable = function () {
        if (typeof this.element.inputmask === 'function') {
            this.element.inputmask('999.999.999-99', {
                clearIncomplete: true,
            });
        }
    }
    return CPFCNPJMask;
}());

Brewer.PhoneMask = (function () {
    function PhoneMask(el) {
        this.element = el;
    }
    PhoneMask.prototype.enable = function () {
        if (typeof this.element.inputmask === 'function') {
            this.element.inputmask({
                mask: '(99) 9999[9]-9999',
                clearIncomplete: true,
            });
        }
    }
    return PhoneMask;
}());

Brewer.ZipMask = (function () {
    function ZipMask(el) {
        this.element = el;
    }
    ZipMask.prototype.enable = function () {
        if (typeof this.element.inputmask === 'function') {
            this.element.inputmask({
                mask: '99999-999',
                clearIncomplete: true,
            });
        }
    }
    return ZipMask;
}());

Brewer.DatePicker = (function () {
    function DatePicker(el) {
        this.element = el;
    }
    DatePicker.prototype.enable = function () {
        if (typeof this.element.datepicker === 'function') {
            this.element.datepicker({
                format: "dd/mm/yyyy",
                language: 'pt-BR'
            });
        }
        if (typeof this.element.inputmask === 'function') {
            this.element.inputmask('date', {
                placeholder: 'dd/mm/aaaa',
                dateFormat: 'dd/mm/yyyy',
                clearIncomplete: true,
            });
        }
    }
    return DatePicker;
}());

Brewer.Password = (function () {
    function Password(el) {
        this.element = el;
    }
    Password.prototype.enable = function () {
        if (typeof this.element.password === 'function') {
            this.element.password();
        }
    }
    return Password;
}());

Brewer.Status = (function () {
    function Status(el) {
        this.element = el;
    }
    Status.prototype.enable = function () {
        if (typeof this.element.bootstrapSwitch === 'function'){
            this.element.bootstrapSwitch({
                onColor: 'success',
                offColor: 'danger',
                onText: 'Ativo',
                offText: 'Inativo',
                labelWidth: '0',
                handleWidth: 'auto',
            });
        }
    }
    return Status;
}());