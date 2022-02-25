package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.vo.PaginationVo;
import com.bjpowernode.crm.workbench.domain.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerService {
    List<String> getCustomerName(String name);
    int getCustomerByPhone(String phone);
    PaginationVo<Customer> pageList(Map<String, Object> map);
    boolean save(Customer cus);
}
