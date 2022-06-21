package br.com.manzatech.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorsController {

	@GetMapping("/404")
	public ModelAndView notFound(Exception ex) {
		ModelAndView mv = new ModelAndView("404");
		mv.addObject("exception", ex);
		return mv;
	}
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PATCH, RequestMethod.PUT, RequestMethod.DELETE }, path = "/500")
	public ModelAndView internalServerError(Exception ex) {
		ModelAndView mv = new ModelAndView("500");
		mv.addObject("exception", ex);
		return mv;
	}
	
}
