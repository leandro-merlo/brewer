package org.manzatech.brewer.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SegurancaController {

    @RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
    public String login(@AuthenticationPrincipal User user){
        if (user != null) {
            return "redirect:/cervejas";
        }
        return "layout/LoginLayout";
    }
}
