package br.com.manzatech.brewer.thymeleaf.processor;

import java.util.HashMap;
import java.util.Map;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.AttributeValueQuotes;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class InvalidFeedbackTagProcessor extends AbstractElementTagProcessor {

	private static final String TAG_NAME = "invalidfeedback";
	private static final int PRECEDENCE = 1000;
	
	public InvalidFeedbackTagProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag,
			IElementTagStructureHandler structureHandler) {
		IModelFactory modelFactory = context.getModelFactory();
		IModel model = modelFactory.createModel();

		String field = tag.getAttributeValue("field");
		
		if (null != field) {
			Map<String, String> attributes = new HashMap<>();
			String classes = "invalid-feedback";
			if (tag.hasAttribute("outside")) {
				classes += " d-block";
			}
			attributes.put("class", "'"+ classes +"'");
			attributes.put("th:if", "${#fields.hasErrors('" + field +"')}");
			attributes.put("th:errors", "*{" + field + "}");
			model.add(modelFactory.createStandaloneElementTag("div", attributes, AttributeValueQuotes.NONE, false, true));
			structureHandler.replaceWith(model, true);			
		}
	}

}
