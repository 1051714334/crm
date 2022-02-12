package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.ClueService;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.bjpowernode.crm.workbench.service.TranService;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.ClueServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.CustomerServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.TranServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/workbench/transaction/add.do".equals(path)) {
           add(request, response);
        } else if ("/workbench/transaction/getCustomerName.do".equals(path)) {
            getCustomerName(request, response);
        }else if("/workbench/transaction/save.do".equals(path)){
            save(request,response);
        }
    }

    private void save(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        TranService ts= (TranService) ServiceFactory.getService(new TranServiceImpl());
        String id=UUIDUtil.getUUID();
        String owner=request.getParameter("owner");
        String money=request.getParameter("money");
        String name=request.getParameter("name");
        String expectedDate=request.getParameter("expectedDate");
        String customerName=request.getParameter("customerName");
        String stage=request.getParameter("stage");
        String type=request.getParameter("type");
        String source=request.getParameter("source");
        String activityId=request.getParameter("activityId");
        String contactsId=request.getParameter("contactsId");
        String createBy=((User)request.getSession().getAttribute("user")).getName();
        String createTime=DateTimeUtil.getSysTime();
        String description=request.getParameter("description");
        String contactSummary=request.getParameter("contactSummary");
        String nextContactTime=request.getParameter("nextContactTime");
        Tran t=new Tran();
        t.setContactsId(contactsId);
        t.setId(id);
        t.setContactSummary(contactSummary);
        t.setNextContactTime(nextContactTime);
        t.setDescription(description);
        t.setCreateTime(createTime);
        t.setCreateBy(createBy);
        t.setActivityId(activityId);
        t.setSource(source);
        t.setType(type);
        t.setStage(stage);
        t.setExpectedDate(expectedDate);
        t.setName(name);
        t.setOwner(owner);
        t.setMoney(money);
        boolean flag=ts.save(t,customerName);
        response.sendRedirect(request.getContextPath()+"/workbench/transaction/index.jsp");
    }

    private void getCustomerName(HttpServletRequest request, HttpServletResponse response) {
        String name= request.getParameter("name");
        CustomerService cs= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
        List<String> sList=cs.getCustomerName(name);
        PrintJson.printJsonObj(response,sList);
    }

    private void add(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        UserService us= (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> uList=us.getUserList();
        request.setAttribute("uList",uList);
        request.getRequestDispatcher("/workbench/transaction/save.jsp").forward(request,response);
    }
}