package com.genesislabs.video.controller;

import com.genesislabs.video.service.VideoDataService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Log4j2
@RequestMapping(value = "/page/")
@Controller
public class ViewController {
    private String REQ_VIDEO_URI = "/data/video/play-video/";
    @Autowired
    VideoDataService videoDataService;

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

    @RequestMapping("video-player-view/{vf_idx}")
    public String videoPlayView(
            @PathVariable String vf_idx
            , Model model
    ) {
        String reqUri = REQ_VIDEO_URI + vf_idx;
        model.addAttribute("reqUri", reqUri);
        return "videoPlayerView";
    }
}
