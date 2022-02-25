package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.vo.PaginationVo;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface CustomerService {
    List<String> getCustomerName(String name);
    //Customer getCustomerByPhone(String phone);
    PaginationVo<Customer> pageList(Map<String, Object> map);
    boolean save(Customer cus,Tran t);

    Map<String, Object> getUserListAndCustomer(String id);

    boolean update(Customer cus);

    Customer detail(String id);
}
