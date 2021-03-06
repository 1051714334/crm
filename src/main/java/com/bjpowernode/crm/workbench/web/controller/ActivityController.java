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
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
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
        }else if("/workbench/activity/delete.do".equals(path)){
            delete(request,response);
        }else if("/workbench/activity/getUserListAndActivity.do".equals((path))){
            getUserListAndActivity(request,response);
        }else if("/workbench/activity/update.do".equals(path)){
            update(request,response);
        }else if("/workbench/activity/detail.do".equals(path)){
            detail(request,response);
        }else if("/workbench/activity/showRemarkList.do".equals(path)){
            showRemarkList(request,response);
        }else if("/workbench/activity/deleteRemark.do".equals(path)){
            deleteRemark(request,response);
        }else if("/workbench/activity/saveRemark.do".equals(path)){
            saveRemark(request,response);
        }else if("/workbench/activity/updateRemark.do".equals(path)){
            updateRemark(request,response);
        }
    }

    private void updateRemark(HttpServletRequest request, HttpServletResponse response) {
        String id=request.getParameter("id");
        String noteContent=request.getParameter("noteContent");
        String editTime=DateTimeUtil.getSysTime();
        String editBy=((User)request.getSession().getAttribute("user")).getName();
        String editFlag="1";
        ActivityRemark ar=new ActivityRemark();
        ar.setId(id);
        ar.setNoteContent(noteContent);
        ar.setEditFlag(editFlag);
        ar.setEditBy(editBy);
        ar.setEditTime(editTime);
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag=as.updateRemark(ar);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("success",flag);
        map.put("ar",ar);
        PrintJson.printJsonObj(response,map);
    }

    private void saveRemark(HttpServletRequest request, HttpServletResponse response) {
        String noteContent=request.getParameter("noteContent");
        String activityId=request.getParameter("activityId");
        String id=UUIDUtil.getUUID();
        String createTime=DateTimeUtil.getSysTime();
        String createBy=((User)request.getSession().getAttribute("user")).getName();
        String editFlag="0";
        ActivityRemark ar=new ActivityRemark();
        ar.setId(id);
        ar.setActivityId(activityId);
        ar.setCreateBy(createBy);
        ar.setCreateTime(createTime);
        ar.setEditFlag(editFlag);
        ar.setNoteContent(noteContent);
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag=as.saveRemark(ar);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("success",flag);
        map.put("ar",ar);
        PrintJson.printJsonObj(response,map);
    }

    private void deleteRemark(HttpServletRequest request, HttpServletResponse response) {
        String id=request.getParameter("id");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag=as.deleteRemark(id);
        PrintJson.printJsonFlag(response,flag);
    }

    private void showRemarkList(HttpServletRequest request, HttpServletResponse response) {
        String activityId=request.getParameter("activityId");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<ActivityRemark> arList=as.showRemarkList(activityId);
        PrintJson.printJsonObj(response,arList);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String id=request.getParameter("id");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
       Activity activity=as.detail(id);
       request.setAttribute("a",activity);
        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request,response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        String id= request.getParameter("id");
        String owner=request.getParameter("owner");
        String name=request.getParameter("name");
        String startDate=request.getParameter("startDate");
        String endDate=request.getParameter("endDate");
        String cost=request.getParameter("cost");
        String description=request.getParameter("description");
        String editTime= DateTimeUtil.getSysTime();
        String editBy =((User)request.getSession().getAttribute("user")).getName();
        Activity activity=new Activity();
        activity.setId(id);
        activity.setName(name);
        activity.setOwner(owner);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setEditTime(editTime);
        activity.setEditBy(editBy);
        boolean flag=as.update(activity);
        PrintJson.printJsonFlag(response,flag);
    }

    private void getUserListAndActivity(HttpServletRequest request, HttpServletResponse response) {
        String id=request.getParameter("id");
        ActivityService activityService= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Map<String,Object> map=activityService.getUserListAndActivity(id);
        PrintJson.printJsonObj(response,map);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        String ids[]=request.getParameterValues("id");
        boolean flag=as.delete(ids);
        PrintJson.printJsonFlag(response,flag);
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
