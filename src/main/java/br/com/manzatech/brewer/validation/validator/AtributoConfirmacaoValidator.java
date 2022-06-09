package br.com.manzatech.brewer.validation.validator;

import java.lang.reflect.Field;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import br.com.manzatech.brewer.validation.AtributoConfirmacao;

public class AtributoConfirmacaoValidator implements ConstraintValidator<AtributoConfirmacao, Object>{

	private String atributo;
	private String atributoConfirmacao;
	
	@Override
	public void initialize(AtributoConfirmacao constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
		this.atributo = constraintAnnotation.atributo();
		this.atributoConfirmacao = constraintAnnotation.atributoConfirmacao();
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		Object valorAtributo = this.getValueForFieldName(value, this.atributo);
		Object valorAtributoConfirmacao = this.getValueForFieldName(value, this.atributoConfirmacao);
		
		boolean valid = ObjectUtils.nullSafeEquals(valorAtributo, valorAtributoConfirmacao);
		if (!valid) {
			String message = context.getDefaultConstraintMessageTemplate();
			ConstraintViolationBuilder builder = context.buildConstraintViolationWithTemplate(message);
			builder.addPropertyNode(this.atributoConfirmacao).addConstraintViolation();
		}
		return valid;
	}
	
	private Object getValueForFieldName(Object origin, String fieldName) {
		try {
			Field field = ReflectionUtils.findField(origin.getClass(), fieldName);
			if (!field.canAccess(origin)) {
				ReflectionUtils.makeAccessible(field);
			}
			return ReflectionUtils.getField(field, origin);			
		} catch (Exception e) {
			throw new RuntimeException("Erro tentando recuperar valor do objeto", e);
		}
	}

	
}
