package br.com.manzatech.brewer.thymeleaf.processor;

import org.springframework.util.ObjectUtils;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IAttribute;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class DatepickerElementTagProcessor extends AbstractElementTagProcessor {

	private static final String TAG_NAME = "datepicker";
	private static final int PRECEDENCE = 1000;
	
	public DatepickerElementTagProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag,
			IElementTagStructureHandler structureHandler) {
		IModelFactory modelFactory = context.getModelFactory();
		IModel model = modelFactory.createModel();
		
		IAttribute fieldName = tag.getAttribute("fieldName");
		IAttribute orientation = tag.getAttribute("orientation");
		
		model.add(modelFactory.createStandaloneElementTag(
				"th:block", "th:replace", String.format("layout/fragments/datepicker :: datepicker(%s, '%s')", 
						fieldName.getValue(), orientation != null ? orientation.getValue() : null)));
		
		structureHandler.replaceWith(model, true);
	}

}
