package org.manzatech.brewer.thymeleaf;

import org.manzatech.brewer.thymeleaf.processor.ClassForErrorAttributeTagProcessor;
import org.manzatech.brewer.thymeleaf.processor.MessageElementTagProcessor;
import org.manzatech.brewer.thymeleaf.processor.OrderElementTagProcessor;
import org.manzatech.brewer.thymeleaf.processor.PaginationElementTagProcessor;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import java.util.HashSet;
import java.util.Set;

public class BrewerDialect extends AbstractProcessorDialect {

    public BrewerDialect() {
        super("Brewer", "brewer", StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<>();
        processors.add(new ClassForErrorAttributeTagProcessor("brewer"));
        processors.add(new MessageElementTagProcessor("brewer"));
        processors.add(new OrderElementTagProcessor("brewer"));
        processors.add(new PaginationElementTagProcessor("brewer"));
        return processors;
    }
}
