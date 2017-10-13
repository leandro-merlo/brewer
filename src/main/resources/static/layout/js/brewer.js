$(function () {
    if (typeof $(".js-masked-decimal").inputmask === 'function') {
        var maskedDecimal = $(".js-masked-decimal");
        if (maskedDecimal) {
            maskedDecimal.inputmask('decimal', {
                placeholder: '0,00',
                radixPoint: ',',
                groupSeparator: '.',
                groupSize: 3,
                autoGroup: true,
                rightAlign: false,
                digits: 2,
                digitsOptional: false,
                allowMinus: false,
                integerOptional: false,
                max: 100,
                min: 0,
            });
        }
        var maskedInteger = $(".js-masked-integer");
        if (maskedInteger) {
            maskedInteger.inputmask('integer', {
                placeholder: '0',
                groupSeparator: '.',
                groupSize: 3,
                autoGroup: true,
                rightAlign: false,
                integerOptional: false,
                max: 100,
                min: 0,
            });
        }
        var maskedCPFCNPJ = $('.js-cpf-cnpj');
        if (maskedCPFCNPJ) {
            maskedCPFCNPJ.inputmask('999.999.999-99', {
                clearIncomplete: true,
            });
        }
        var radioTipoPessoa = $('.js-tipo-pessoa');
        if (radioTipoPessoa) {
            $(radioTipoPessoa).find('input[type="radio"]').on('change', function () {
                var radios = $(radioTipoPessoa).find('input[type="radio"]');
                $(radios).each(function () {
                    $(this).prop('checked', false);
                });
                $(this).prop('checked', true);
                var value = $(this).data('value');
                var doc = $('.js-cpf-cnpj');
                doc.inputmask('remove');
                doc.val(null);
                if (value.indexOf('Física') > 0) {
                    $('.js-label-documento').text('CPF');
                    doc.inputmask('999.999.999-99');
                } else {
                    $('.js-label-documento').text('CNPJ');
                    doc.inputmask({
                        mask: '99[9].999.999/9999-99',
                        skipOptionalPartCharacter: '',
                        clearMaskOnLostFocus: true,
                        clearIncomplete: true,
                        autoUnmask: true,
                    });
                }
            });
        }

        var maskedPhone = $('.js-telefone-celular');
        if (maskedPhone) {
            maskedPhone.inputmask({
                mask: '(99) 9999[9]-9999',
                clearIncomplete: true,
            });
        }

        var maskedZip = $('.js-cep');
        if (maskedZip) {
            maskedZip.inputmask({
                mask: '99999-999',
                clearIncomplete: true,
            });
        }
    }
    $(document).ready(function () {
        if ($.fn.datepicker !== undefined) {
            $.fn.datepicker.defaults.language = "pt-BR";
        }
    });
    $(document).ready(function () {
        var datepicker = $(".js-datepicker");
        if (datepicker) {
            if (typeof datepicker.datepicker === 'function') {
                if (datepicker) {
                    datepicker.datepicker({
                        format: "dd/mm/yyyy",
                        language: 'pt-BR'
                    });
                }
            }
            if (typeof datepicker.inputmask === 'function') {
                datepicker.inputmask('date', {
                    placeholder: 'dd/mm/aaaa',
                    dateFormat: 'dd/mm/yyyy',
                    clearIncomplete: true,
                });
            }
        }
    });

    var password = $('.js-password');
    if (password) {
        if (typeof password.password === 'function'){
            password.password();
        }
    }

    var status = $('.js-status');
    if (status) {
        if (typeof status.bootstrapSwitch === 'function'){
            status.bootstrapSwitch({
                onColor: 'success',
                offColor: 'danger',
                onText: 'Ativo',
                offText: 'Inativo',
                labelWidth: '0',
                handleWidth: 'auto',
            });
        }
    }
});