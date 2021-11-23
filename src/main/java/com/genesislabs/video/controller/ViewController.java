package com.genesislabs.video.controller;

import com.genesislabs.common.enums.FailMessage;
import com.genesislabs.config.security.CookieComponent;
import com.genesislabs.config.security.LoginFailHandler;
import com.genesislabs.video.entity.UserEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Log4j2
@RequestMapping(value = "/page/")
@Controller
public class ViewController {

    @Autowired
    CookieComponent cookieComponent;

    @RequestMapping("login-view")
    public String loginView(
            @ModelAttribute(LoginFailHandler.LOGIN_FAIL_MESSAGE) final String _loginFailMsg
            , Model _model
    ) {
        if(_loginFailMsg!=null && !_loginFailMsg.equals(""))
            _model.addAttribute("loginFailMsg", _loginFailMsg);

        return "loginView";
    }

    @RequestMapping("join-view")
    public ModelAndView joinMemeberView() {
        return new ModelAndView("joinMemberView");
    }

    @RequestMapping("edit-user-view")
    public String editUserView(Model _model) {
        UserEntity userEntity = cookieComponent.findUserInfoByCookie();
        if(ObjectUtils.isEmpty(userEntity))
            throw new UsernameNotFoundException(FailMessage.FIND_ACCOUNT_FAIL.getMessage());
        else
            _model.addAttribute("user", userEntity);

        return "editMemberInfoView";
    }

    @RequestMapping("video-upload-view")
    public ModelAndView videoUploadView() {
        return new ModelAndView("videoUploadView");
    }

    @RequestMapping("video-search-view")
    public String videoSearchView(Model _model) {
        UserEntity userEntity = cookieComponent.findUserInfoByCookie();
        _model.addAttribute("level", userEntity.getVu_level());
        return "videoSearchView";
    }

    @RequestMapping("video-player-view/{vf_idx}")
    public String videoPlayView(
            @PathVariable String vf_idx
            , Model _model
    ) {
        _model.addAttribute("vf_idx", vf_idx);
        return "videoPlayerView";
    }
}
