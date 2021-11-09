package com.genesislabs.video.controller;


import com.genesislabs.common.DataResponsePattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping(value = "/data/")
@RestController
@RequiredArgsConstructor
public class LoginDataController extends DataResponsePattern {

    @PostMapping("doLogin")
    public void doLogin() {

//        if (bindingResult.hasErrors())
//            log.error("요철실패!!!!!!!!!!!!");
        log.info("요철성공!!!!!!!!!!!!");
    }

}