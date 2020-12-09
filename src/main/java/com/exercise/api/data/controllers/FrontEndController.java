package com.exercise.api.data.controllers;

import com.exercise.api.data.services.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontEndController {

    private final TeacherService teacherService;
    private Logger logger = LoggerFactory.getLogger(FrontEndController.class);

    @Autowired
    public FrontEndController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping({"/teachers", "/"})
    public String teachers(Model model){
        model.addAttribute("teachers", teacherService.getAll());
        logPrincipal("/teachers");
        return "teachers";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    private void logPrincipal(String resource){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Object principal = authentication.getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        logger.info("Principal '" + username + "' is accessing to '" + resource + "'");
    }
}
