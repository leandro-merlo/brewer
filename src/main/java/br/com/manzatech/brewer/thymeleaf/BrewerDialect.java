package br.com.manzatech.brewer.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import br.com.manzatech.brewer.thymeleaf.processor.ClassForErrorAttributeTagProcessor;
import br.com.manzatech.brewer.thymeleaf.processor.DatepickerElementTagProcessor;
import br.com.manzatech.brewer.thymeleaf.processor.GravatarElementTagProcessor;
import br.com.manzatech.brewer.thymeleaf.processor.InvalidFeedbackTagProcessor;
import br.com.manzatech.brewer.thymeleaf.processor.MenuAttributeTagProcessor;
import br.com.manzatech.brewer.thymeleaf.processor.OrderElementTagProcessor;
import br.com.manzatech.brewer.thymeleaf.processor.PaginacaoElementTagProcessor;

public class BrewerDialect extends AbstractProcessorDialect{

	public BrewerDialect() {
		super("Brewer Dialect", "brewer", StandardDialect.PROCESSOR_PRECEDENCE);
	}

	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		HashSet<IProcessor> processors = new HashSet<>();
		// Attribute Processors
		processors.add(new ClassForErrorAttributeTagProcessor(dialectPrefix));		
		processors.add(new MenuAttributeTagProcessor(dialectPrefix));		
		// Element Processors
		processors.add(new InvalidFeedbackTagProcessor(dialectPrefix));
		processors.add(new OrderElementTagProcessor(dialectPrefix));		
		processors.add(new PaginacaoElementTagProcessor(dialectPrefix));
		processors.add(new DatepickerElementTagProcessor(dialectPrefix));
		processors.add(new GravatarElementTagProcessor(dialectPrefix));
		
		return processors;
	}

}
