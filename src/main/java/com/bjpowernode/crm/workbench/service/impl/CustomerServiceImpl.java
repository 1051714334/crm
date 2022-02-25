package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.vo.PaginationVo;
import com.bjpowernode.crm.workbench.dao.CustomerDao;
import com.bjpowernode.crm.workbench.dao.TranDao;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.service.CustomerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerServiceImpl implements CustomerService {
    CustomerDao customerDao= SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    TranDao tranDao=SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    UserDao userDao=SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
    public List<String> getCustomerName(String name) {
        List<String> sList=customerDao.getCustomerName(name);
        return sList;
    }

  /*  public Customer getCustomerByPhone(String phone) {
       Customer cus=customerDao.getCustomerByPhone(phone);
        return cus;
    }*/



    public PaginationVo<Customer> pageList(Map<String, Object> map) {
        PaginationVo<Customer> vo=new PaginationVo<Customer>();
        int total=customerDao.getTotalByCondition(map);
        List<Customer> dataList=customerDao.getCustomerListByCondition(map);
        vo.setDataList(dataList);
        vo.setTotal(total);
        return vo;
    }

    public boolean save(Customer cus,Tran t) {
       boolean flag=true;
       String phone=cus.getPhone();
       Customer cus1=customerDao.getCustomerByPhone(phone);
       if(cus1!=null){
           //不创建客户信息
           //获取ID
           t.setCustomerId(cus1.getId());
           //创建交易
           int count1=tranDao.save(t);
           if(count1!=1){flag=false;}
       }else {
           //搜索所有电话
           Customer cus2=customerDao.getCustomerByPhones(phone);
           //如果有，获取ID
           if(cus2!=null){
               cus.setFamilyId(cus2.getId());
               int count2=customerDao.save(cus);
                if(count2!=1){flag=false;}
                t.setCustomerId(cus.getId());
               int count3=tranDao.save(t);
               if(count3!=1){flag=false;}
           }else {
               int count4=customerDao.save(cus);
               if(count4!=1){flag=false;}
               int count5=tranDao.save(t);
               if(count5!=1){flag=false;}
           }
           //创建新客户，增加旧客户的ID为MID
           //增加交易
           //如果没有，创建客户，创建交易
          /*int count2= customerDao.save(cus);
          if(count2!=1){
              flag=false;
          }*/
       }
       return flag;
    }

    public Map<String, Object> getUserListAndCustomer(String id) {
        Map<String,Object> map=new HashMap<String, Object>();
        List<User> uList= userDao.getUserList();
        Customer cus=customerDao.getById(id);
        map.put("cus",cus);
        map.put("userList",uList);
        return map;
    }

    public boolean update(Customer cus) {
        boolean flag=true;
        int count=customerDao.update(cus);
        if(count!=1){
          flag=false;
        }
        return flag;
    }

    public Customer detail(String id) {

        return customerDao.getById(id);
    }


}
