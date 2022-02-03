package com.bjpowernode.crm.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SysInitListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("全局作用域对象创建了");
        ServletContext application=event.getServletContext();
        //application.getAttribute("数据字典",sjzd);
    }
}
