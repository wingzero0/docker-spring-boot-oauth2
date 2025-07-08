package io.github.wingzero0.ssoserver.controller;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kit.personal.ssoentity.repo.AppUserRepository;
import kit.personal.ssoentity.repo.AppUserRoleRepository;

@Controller
public class UserController {
    @Autowired
    AppUserRoleRepository roleRepository;
    @Autowired
    AppUserRepository appUserRepository;

    private static Logger LOG = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String postLogin() {
        // Enable form login with Spring Security (trigger error for now) ???
        // TODO to see if needed
        return "redirect:/login-error";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @RequestMapping("/exit")
    public void exit(HttpServletRequest request, HttpServletResponse response,
                     @RequestParam(value = "callbackURL", required = true) String callbackURL) {
        // client should manually call revokeAccessToken
        new SecurityContextLogoutHandler().logout(request, null, null);
        LOG.debug("someone call logout");
        try {
            LOG.debug("callbackURL:" + callbackURL);
            response.sendRedirect(callbackURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}