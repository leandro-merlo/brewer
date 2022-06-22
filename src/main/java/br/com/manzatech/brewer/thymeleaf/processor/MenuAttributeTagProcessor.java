package br.com.manzatech.brewer.thymeleaf.processor;

import javax.servlet.http.HttpServletRequest;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

import br.com.manzatech.brewer.utils.MenuUtils;

public class MenuAttributeTagProcessor extends AbstractAttributeTagProcessor {

	private static final String NOME_ATRIBUTO = "menu";
	private static final int PRECEDENCE = 1000;
	
	public MenuAttributeTagProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, null, false, NOME_ATRIBUTO, true, PRECEDENCE,
				true);
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName,
			String attributeValue, IElementTagStructureHandler structureHandler) {
		
		final IEngineConfiguration configuration = context.getConfiguration();
        final IStandardExpressionParser parser =
                StandardExpressions.getExpressionParser(configuration);
        final IStandardExpression expression = parser.parseExpression(context, attributeValue);
		
        HttpServletRequest request = ((IWebContext)context).getRequest();
        String uri = request.getRequestURI();
        
		String menu = (String)expression.execute(context);
		
		String classesExistentes = tag.getAttributeValue("class");
		
		if (uri.startsWith(menu)) {
			structureHandler.setAttribute("class", classesExistentes + " active");			
		} else if(MenuUtils.menuIsOpen(menu, request)) {
			String tagName = tag.getElementCompleteName();
			if (tagName.equals("li")) {
				structureHandler.setAttribute("class", classesExistentes + " menu-open");										
			} else {
				structureHandler.setAttribute("class", classesExistentes + " active");							
			}
		}				
	}

}
