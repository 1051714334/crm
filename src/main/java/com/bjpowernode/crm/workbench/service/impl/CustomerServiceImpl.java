package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.vo.PaginationVo;
import com.bjpowernode.crm.workbench.dao.CustomerDao;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.service.CustomerService;

import java.util.List;
import java.util.Map;

public class CustomerServiceImpl implements CustomerService {
    CustomerDao customerDao= SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    public List<String> getCustomerName(String name) {
        List<String> sList=customerDao.getCustomerName(name);
        return sList;
    }

    public int getCustomerByPhone(String phone) {
        int count=customerDao.getCustomerByPhone(phone);
        return count;
    }



    public PaginationVo<Customer> pageList(Map<String, Object> map) {
        PaginationVo<Customer> vo=new PaginationVo<Customer>();
        int total=customerDao.getTotalByCondition(map);
        List<Customer> dataList=customerDao.getCustomerListByCondition(map);
        vo.setDataList(dataList);
        vo.setTotal(total);
        return vo;
    }

    public boolean save(Customer cus) {

       /* boolean flag=true;
        int count=customerDao.save(cus);
        if(count!=1){
            flag=false;
        }
        return flag;*/
       boolean flag=true;
       String phone=cus.getPhone();
       int count1=customerDao.getCustomerByPhone(phone);
       if(count1==1){
           flag=false;
       }else {
          int count2= customerDao.save(cus);
          if(count2!=1){
              flag=false;
          }
       }
       return flag;
    }


}
