package com.genesislabs.video.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Log4j2
@RequestMapping(value = "/page/")
@Controller
public class ViewController {

    @RequestMapping("login-view")
    public ModelAndView loginView() {
        return new ModelAndView("loginView");
    }

    @RequestMapping("join-view")
    public ModelAndView joinMemeberView() {
        return new ModelAndView("joinMemberView");
    }

    @RequestMapping("video-upload-view")
    public ModelAndView videoUploadView() {
        return new ModelAndView("videoUploadView");
    }

    @RequestMapping("video-search-view")
    public ModelAndView videoSearchView() {
        return new ModelAndView("videoSearchView");
    }
}
