package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerDao {

    Customer getCustomerByName(String company);
    List<String> getCustomerName(String name);

    Customer  getCustomerByPhone(String phone);

    int getTotalByCondition(Map<String, Object> map);

    List<Customer> getCustomerListByCondition(Map<String, Object> map);
    int save(Customer cus);

    Customer getCustomerByPhones(String phone);

    Customer getById(String id);

    int update(Customer cus);
}
