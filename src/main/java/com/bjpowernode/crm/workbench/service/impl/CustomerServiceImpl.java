package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.CustomerDao;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.service.CustomerService;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    CustomerDao customerDao= SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    public List<String> getCustomerName(String name) {
        List<String> sList=customerDao.getCustomerName(name);
        return sList;
    }

    public Customer getCustomerByPhone(String phone) {
        Customer cus=customerDao.getCustomerByPhone(phone);
        return cus;
    }

    public boolean save(Customer cus) {
        boolean flag=true;
        int count=customerDao.save(cus);
        if(count!=1){flag=false;}
        return flag;
    }
}
