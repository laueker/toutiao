package com.nowcoder.toutiao.service;


import com.nowcoder.toutiao.dao.UserDAO;
import com.nowcoder.toutiao.dao.LoginTicketDAO;

import com.nowcoder.toutiao.model.LoginTicket;
import com.nowcoder.toutiao.model.User;

import com.nowcoder.toutiao.util.ToutiaoUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserService {    //考虑密码强度大不大，敏感词之类的做一下
    @Autowired
    UserDAO userDAO;

    @Autowired
    private LoginTicketDAO loginTicketDAO;

    public Map<String, Object> register(String username, String password) {//注册
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(username)) {  //是否为空，apache的库
            map.put("msgname", "用户名不能为空");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("msgpwd", "密码不能为空");
            return map;
        }

        User user = userDAO.selectByName(username);  //如果能找到user，不为null，说明被注册

        if (user != null) {
            map.put("msgname", "用户名已经被注册");
            return map;
        }

        // 密码强度(将用户上传)
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        String head = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
        user.setHeadUrl(head);
        user.setPassword(ToutiaoUtil.MD5(password+user.getSalt())); //不要明文保存，md5加密
        userDAO.addUser(user);

        //注册成功直接给一个ticket，登录
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(username)) {
            map.put("msgname", "用户名不能为空");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("msgpwd", "密码不能为空");
            return map;
        }

        User user = userDAO.selectByName(username);

        if (user == null) {
            map.put("msgname", "用户名不存在");
            return map;
        }

        if (!ToutiaoUtil.MD5(password+user.getSalt()).equals(user.getPassword())) {
            map.put("msgpwd", "密码不正确");
            return map;}

        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
        }

    private String addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000*3600*24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDAO.addTicket(ticket);
        return ticket.getTicket();   //返回ticket字符串
    }





    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    public void logout(String ticket) {
        loginTicketDAO.updateStatus(ticket, 1);
    }//改变ticket状态，为1就为登出状态
}