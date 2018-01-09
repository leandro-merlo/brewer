package org.manzatech.brewer.config.utils;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.context.MessageSource;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;

public class CustomResourceBundleMessageInterpolator extends ResourceBundleMessageInterpolator {
    public CustomResourceBundleMessageInterpolator(MessageSource messageSource) {
        super(new MessageSourceResourceBundleLocator(messageSource));
    }
}
