package com.douye.myblog.controller;

import com.douye.myblog.dto.NotificationDTO;
import com.douye.myblog.dto.PaginationDTO;
import com.douye.myblog.dto.QuestionDTO;
import com.douye.myblog.mapper.NotificationMapper;
import com.douye.myblog.mapper.UserMapper;
import com.douye.myblog.model.User;
import com.douye.myblog.service.NotificationService;
import com.douye.myblog.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionService questionService;

    @Autowired
    NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "3") Integer size) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        if ("questions".equals(action)) {
            model.addAttribute("section","questions");
            model.addAttribute("sectionName", "我的问题");
            PaginationDTO pagination = questionService.findMyQuestion(user.getId(),page,size);
            model.addAttribute("pagination",pagination);
        } else if ("replies".equals(action)) {
            PaginationDTO pagination = notificationService.findMyComment(user.getId(),page,size);
            model.addAttribute("section","replies");
            model.addAttribute("sectionName", "最新回复");
            model.addAttribute("pagination",pagination);
        }
        return "profile";
    }
}
