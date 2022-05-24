package br.com.manzatech.brewer.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import br.com.manzatech.brewer.thymeleaf.processor.ClassForErrorAttributeTagProcessor;
import br.com.manzatech.brewer.thymeleaf.processor.InvalidFeedbackTagProcessor;

public class BrewerDialect extends AbstractProcessorDialect{

	public BrewerDialect() {
		super("Brewer Dialect", "brewer", StandardDialect.PROCESSOR_PRECEDENCE);
	}

	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		HashSet<IProcessor> processors = new HashSet<>();
		// Attribute Processors
		processors.add(new ClassForErrorAttributeTagProcessor(dialectPrefix));		
		// Element Processors
		processors.add(new InvalidFeedbackTagProcessor(dialectPrefix));
		return processors;
	}

}
