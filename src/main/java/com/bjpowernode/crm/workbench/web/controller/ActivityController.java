package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.vo.PaginationVo;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path=request.getServletPath();
        if("/workbench/activity/getUserList.do".equals(path)){
            getUserList(request,response);
        }else if("/workbench/activity/save.do".equals(path)){
            save(request,response);
        }else if("/workbench/activity/pageList.do".equals(path)){
            pageList(request,response);
        }
    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        String pageNoStr=request.getParameter("pageNo");
        String pageSizeStr=request.getParameter("pageSize");
        String name=request.getParameter("name");
        String owner=request.getParameter("owner");
        String StartDate=request.getParameter("startDate");
        String endDate=request.getParameter("endDate");
        int pageNo=Integer.valueOf(pageNoStr);
        int pageSize=Integer.valueOf(pageSizeStr);
        int skipCount=(pageNo-1)*pageSize;
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("StartDate",StartDate);
        map.put("endDate",endDate);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
       PaginationVo<Activity> vo=as.pageList(map);
       PrintJson.printJsonObj(response,vo);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {

        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        String id= UUIDUtil.getUUID();
        String owner=request.getParameter("owner");
        String name=request.getParameter("name");
        String startDate=request.getParameter("startDate");
        String endDate=request.getParameter("endDate");
        String cost=request.getParameter("cost");
        String description=request.getParameter("description");
        String createTime= DateTimeUtil.getSysTime();
        String createBy =((User)request.getSession().getAttribute("user")).getName();
        Activity activity=new Activity();
        activity.setId(id);
        activity.setName(name);
        activity.setOwner(owner);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);
        boolean flag=as.save(activity);
       PrintJson.printJsonFlag(response,flag);
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        UserService userService= (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> userList=userService.getUserList();
        PrintJson.printJsonObj(response,userList);
    }
}
