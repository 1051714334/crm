package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.vo.PaginationVo;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.CustomerRemark;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.bjpowernode.crm.workbench.service.impl.CustomerServiceImpl;

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
        }else if("/workbench/customer/pageList.do".equals(path)){
            pageList(request,response);
        }else if("/workbench/customer/getUserListAndCustomer.do".equals(path)){
            getUserListAndCustomer(request,response);
        }else if("/workbench/customer/update.do".equals(path)){
            update(request,response);
        }else if("/workbench/customer/detail.do".equals(path)){
            detail(request,response);
        }else if("/workbench/customer/showRemarkList.do".equals(path)){
            showRemarkList(request,response);
        }else if("/workbench/customer/saveRemark.do".equals(path)){
            saveRemark(request,response);
        }else if("/workbench/customer/deleteRemark.do".equals(path)){
            deleteRemark(request,response);
        }else if("/workbench/customer/updateRemark.do".equals(path)){
            updateRemark(request,response);
        }else if("/workbench/customer/getTranList.do".equals(path)){
            getTranList(request,response);
        }else if("/workbench/customer/getScusList.do".equals(path)){
            getScusList(request,response);
        }
    }

    private void getScusList(HttpServletRequest request, HttpServletResponse response) {
        String familyId=request.getParameter("familyId");
        String id=request.getParameter("id");
        CustomerService cs= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
        Map<String,Object> map=cs.getScusList(familyId,id);
        PrintJson.printJsonObj(response,map);
    }

    private void getTranList(HttpServletRequest request, HttpServletResponse response) {
        CustomerService cs= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
    String customerId=request.getParameter("id");
    List<Tran> tList=cs.getTranList(customerId);
    PrintJson.printJsonObj(response,tList);
    }

    private void updateRemark(HttpServletRequest request, HttpServletResponse response) {
        String id=request.getParameter("id");
        String noteContent=request.getParameter("noteContent");
        String editTime=DateTimeUtil.getSysTime();
        String editBy=((User)request.getSession().getAttribute("user")).getName();
        String editFlag="1";
        CustomerRemark cr=new CustomerRemark();
        cr.setId(id);
        cr.setNoteContent(noteContent);
        cr.setEditFlag(editFlag);
        cr.setEditBy(editBy);
        cr.setEditTime(editTime);
        CustomerService cs= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
        boolean flag=cs.updateRemark(cr);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("success",flag);
        map.put("cr",cr);
        PrintJson.printJsonObj(response,map);
    }

    private void deleteRemark(HttpServletRequest request, HttpServletResponse response) {
   CustomerService cs= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
   String id=request.getParameter("id");
   boolean flag=cs.deleteRemark(id);
   PrintJson.printJsonFlag(response,flag);
    }

    private void saveRemark(HttpServletRequest request, HttpServletResponse response) {

            String noteContent=request.getParameter("noteContent");
            String customerId=request.getParameter("customerId");
            String id=UUIDUtil.getUUID();
            String createTime=DateTimeUtil.getSysTime();
            String createBy=((User)request.getSession().getAttribute("user")).getName();
            String editFlag="0";
            CustomerRemark cusRemark=new CustomerRemark();
        cusRemark.setId(id);
        cusRemark.setCustomerId(customerId);
        cusRemark.setCreateBy(createBy);
        cusRemark.setCreateTime(createTime);
        cusRemark.setEditFlag(editFlag);
        cusRemark.setNoteContent(noteContent);
            CustomerService as= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
            boolean flag=as.saveRemark(cusRemark);
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("success",flag);
            map.put("cus",cusRemark);
            PrintJson.printJsonObj(response,map);

    }

    private void showRemarkList(HttpServletRequest request, HttpServletResponse response) {
        String customerId=request.getParameter("customerId");
        CustomerService cs= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
       List<CustomerRemark>  ckList=cs.showRemarkList(customerId);
        PrintJson.printJsonObj(response,ckList);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException  {
   CustomerService cs= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
   String id=request.getParameter("id");
   Customer cus=cs.detail(id);
   request.setAttribute("cus",cus);
   request.getRequestDispatcher("/workbench/customer/detail.jsp").forward(request,response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        CustomerService cs= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
                String id=request.getParameter("id");
                String owner=request.getParameter("owner");
                String name=request.getParameter("name");
                String website=request.getParameter("website");
                String phone=request.getParameter("phone");
                String description=request.getParameter("description");
                String contactSummary=request.getParameter("contactSummary");
                String nextContactTime=request.getParameter("nextContactTime");
                String address=request.getParameter("address");
                String editBy=((User)request.getSession().getAttribute("user")).getName();
                String editDate=DateTimeUtil.getSysTime();
                Customer cus=new Customer();
                cus.setEditBy(editBy);
                cus.setEditTime(editDate);
                cus.setId(id);
                cus.setOwner(owner);
                cus.setName(name);
                cus.setWebsite(website);
                cus.setPhone(phone);
                cus.setDescription(description);
                cus.setContactSummary(contactSummary);
                cus.setNextContactTime(nextContactTime);
                cus.setAddress(address);
                boolean flag=cs.update(cus);
                PrintJson.printJsonFlag(response,flag);
    }

    private void getUserListAndCustomer(HttpServletRequest request, HttpServletResponse response) {
    String id=request.getParameter("id");
    CustomerService cs= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
    Map<String,Object> map=cs.getUserListAndCustomer(id);
    PrintJson.printJsonObj(response,map);
    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        String name=request.getParameter("name");
        String owner=request.getParameter("owner");
        String phone=request.getParameter("phone");
        String startDate=request.getParameter("startDate");
        String endDate=request.getParameter("endDate");
        int pageNo=Integer.valueOf(request.getParameter("pageNo"));
        int pageSize=Integer.valueOf(request.getParameter("pageSize"));
        int skipCount=(pageNo-1)*pageSize;
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("phone",phone);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);
        CustomerService cs= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
        PaginationVo<Customer> vo=cs.pageList(map);
        PrintJson.printJsonObj(response,vo);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        CustomerService cs= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());

        String phone=request.getParameter("phone");
        boolean flag;
          Customer cus=new Customer();
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
            String deposit=request.getParameter("deposit");
            String money=request.getParameter("money");
            String nature=request.getParameter("nature");
            String childrenName=request.getParameter("childrenName");
            String nPeopleName=request.getParameter("nPeopleName");
            String nPeoplePhone=request.getParameter("nPeoplePhone");
            String childrenAddress=request.getParameter("childrenAddress");
            String childrenPhone=request.getParameter("childrenPhone");
            cus.setContactSummary(contactSummary);
        cus.setWebsite(website);
        cus.setNextContactTime(nextContactTime);
        cus.setCreateTime(createTime);
        cus.setName(name);
        cus.setCreateBy(createBy);
        cus.setPhone(phone);
        cus.setAddress(address);
        cus.setDescription(description);
        cus.setChildrenAddress(childrenAddress);
        cus.setChildrenName(childrenName);
        cus.setChildrenPhone(childrenPhone);
        cus.setnPeopleName(nPeopleName);
        cus.setnPeoplePhone(nPeoplePhone);
        cus.setOwner(owner);
        cus.setId(id);
        cus.setCreateBy(createBy);
        cus.setCreateTime(createTime);
        Tran t=new Tran();
        t.setId(UUIDUtil.getUUID());
        t.setOwner(owner);
        t.setMoney(money);
        t.setName(banquetVenue);
        t.setExpectedDate(banquetDate);
        t.setCustomerId(cus.getId());
        t.setDeposit(deposit);
        t.setStage("07成交");
        t.setCreateBy(createBy);
        t.setCreateTime(createTime);
        t.setDescription(description);
        t.setContactSummary(contactSummary);
        t.setNextContactTime(nextContactTime);
        t.setType(nature);
           flag=cs.save(cus,t);
           //}
        PrintJson.printJsonFlag(response,flag);
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        UserService us= (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> uList=us.getUserList();
        PrintJson.printJsonObj(response,uList);
    }


}