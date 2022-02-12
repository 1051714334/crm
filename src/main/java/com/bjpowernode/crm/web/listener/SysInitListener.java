package com.bjpowernode.crm.web.listener;

import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicService;
import com.bjpowernode.crm.settings.service.impl.DicServiceImpl;
import com.bjpowernode.crm.utils.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

public class SysInitListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("全局作用域对象创建了");
        DicService ds= (DicService) ServiceFactory.getService(new DicServiceImpl());
        Map<String, List<DicValue>> map=ds.getAll();
        ServletContext application=event.getServletContext();
        Set<String> set =map.keySet();
        for(String key:set){
            application.setAttribute(key,map.get(key));
        }
        //application.getAttribute("数据字典",sjzd);
        Map<String,String> pMap=new HashMap<String, String>();
        ResourceBundle bundle=ResourceBundle.getBundle("Stage2Possibility");
        Enumeration<String> e=bundle.getKeys();
        while (e.hasMoreElements()){
            String key=e.nextElement();
            String value=bundle.getString(key);

        }
    }
}
