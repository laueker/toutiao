package com.nowcoder.toutiao.controller;


import com.nowcoder.toutiao.model.News;
import com.nowcoder.toutiao.model.ViewObject;
import com.nowcoder.toutiao.service.NewsService;

import com.nowcoder.toutiao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;//存储模型数据
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {


    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(@RequestParam(value = "userId", defaultValue = "0") int userId,
                        Model model,@RequestParam(value = "pop",defaultValue ="0") int pop) {
        model.addAttribute("vos", getNews(0, 0, 10));//0的话就是所有的news
        model.addAttribute("pop",pop);

        return "home";
    }

    @RequestMapping(path = {"/user/{userId}/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(@PathVariable("userId") int userId,Model model)//此处是显示当前userId所发的news
    {

        model.addAttribute("vos", getNews(userId, 0, 10));//此处限制了当前页最多十条news，之前的会藏起来

        return "home";
    }

    private List<ViewObject> getNews(int userId, int offset, int limit) {
        List<News> newsList = newsService.getLatestNews(userId, offset, limit);

        List<ViewObject> vos = new ArrayList<>();
        for (News news : newsList) {
            ViewObject vo = new ViewObject();
            vo.set("news", news);
            vo.set("user", userService.getUser(news.getUserId()));
            vos.add(vo);
        }
        return vos;
    }
}
