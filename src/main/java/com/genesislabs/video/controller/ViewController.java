package com.genesislabs.video.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Log4j2
@RequestMapping(value = "/page/")
@Controller
public class ViewController {
    @RequestMapping("login_view")
    public ModelAndView loginView() {
        return new ModelAndView("view");
    }
}
