package com.cryxie.controller;

import com.cryxie.services.ImplServicoUsuario;
import com.cryxie.utils.AppUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppUtils.userEndpoint)

public class UserController {
    @Autowired
    ImplServicoUsuario userService;

}
