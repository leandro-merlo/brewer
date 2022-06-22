package br.com.manzatech.brewer.thymeleaf.processor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IAttribute;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

import br.com.manzatech.brewer.utils.GravatarUtils;

public class GravatarElementTagProcessor extends AbstractElementTagProcessor {

	private static final String TAG_NAME = "gravatar";
	private static final int PRECEDENCE = 1000;
	
	public GravatarElementTagProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag,
		IElementTagStructureHandler structureHandler) {
		IModelFactory modelFactory = context.getModelFactory();
		IModel model = modelFactory.createModel();
		
		IAttribute classes = tag.getAttribute("classes");
		IAttribute alt = tag.getAttribute("alt");
		IAttribute size = tag.getAttribute("size");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String hash = GravatarUtils.md5Hex(auth.getName());
		
		String img = "https://www.gravatar.com/avatar/" + hash + "?d=mp";		
		if (null != size.getValue()) {
			img += "&s=" + size.getValue();
		}
		
		model.add(modelFactory.createStandaloneElementTag(
				"th:block", "th:replace", String.format("layout/fragments/gravatar :: gravatar('%s','%s','%s')", 
						img, classes.getValue(), alt.getValue())));
		
		structureHandler.replaceWith(model, true);
	}

}
