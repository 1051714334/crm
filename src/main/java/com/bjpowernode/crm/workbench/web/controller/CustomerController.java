package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.bjpowernode.crm.workbench.service.TranService;
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

public class CustomerController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/workbench/customer/getUserList.do".equals(path)) {
           getUserList(request, response);
        } else if ("/workbench/customer/save.do".equals(path)) {
           save(request, response);
        }
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        CustomerService cs= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
        Customer cus=null;
        String phone=request.getParameter("phone");
       cus=cs.getCustomerByPhone(phone);
        boolean flag=true;
        if(cus!=null){
            flag=false;
        }else if(cus==null) {
            String id=UUIDUtil.getUUID();
            String owner=request.getParameter("owner");
            String name=request.getParameter("name");
            String website=request.getParameter("website");
            String createBy=((User)request.getSession().getAttribute("user")).getName();
            String createTime=DateTimeUtil.getSysTime();
            String contactSummary=request.getParameter("contactSummary");
            String nextContactTime=request.getParameter("nextContactTime");
            String description=request.getParameter("description");
            String address=request.getParameter("address");
            String banquetDate=request.getParameter("banquetDate");
            String banquetVenue=request.getParameter("banquetVenue");
            String nature=request.getParameter("nature");
            String childrenName=request.getParameter("childrenName");
            String nPeopleName=request.getParameter("nPeopleName");
            String nPeoplePhone=request.getParameter("nPeoplePhone");
            String childrenAddress=request.getParameter("childrenAddress");
            String childrenPhone=request.getParameter("childrenPhone");
        System.out.println(childrenAddress);
            cus=new Customer();
            cus.setContactSummary(contactSummary);
            cus.setWebsite(website);
            cus.setNextContactTime(nextContactTime);
            cus.setOwner(owner);
            cus.setCreateTime(createTime);
            cus.setName(name);
            cus.setCreateBy(createBy);
            cus.setPhone(phone);
            cus.setId(id);
            cus.setAddress(address);
            cus.setDescription(description);
            cus.setBanquetDate(banquetDate);
            cus.setBanquetVenue(banquetVenue);
            cus.setChildrenAddress(childrenAddress);
            cus.setChildrenName(childrenName);
            cus.setChildrenPhone(childrenPhone);
            cus.setNature(nature);
            cus.setnPeopleName(nPeopleName);
            cus.setnPeoplePhone(nPeoplePhone);
           flag=cs.save(cus);

        }
        PrintJson.printJsonFlag(response,flag);
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        UserService us= (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> uList=us.getUserList();
        PrintJson.printJsonObj(response,uList);
    }


}