package com.emse.spring.faircorp.api;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/username")
public class SecurityController {

    @Secured("ADMIN")
    @GetMapping(path = "/{id}")
    public String findUserName(@AuthenticationPrincipal UserDetails userDetails) {
        if(userDetails != null){
            return userDetails.getUsername();
        }
        else{
            return "No user logged in";
        }
    }
}
