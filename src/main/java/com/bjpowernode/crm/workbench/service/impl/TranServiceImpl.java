package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.dao.CustomerDao;
import com.bjpowernode.crm.workbench.dao.TranDao;
import com.bjpowernode.crm.workbench.dao.TranHistoryDao;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;
import com.bjpowernode.crm.workbench.service.TranService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TranServiceImpl implements TranService {
    TranDao tranDao= SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    TranHistoryDao tranHistoryDao=SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);
    CustomerDao customerDao= SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    public boolean save(Tran t,String customerName,String customerId) {
        boolean flag=true;
        Customer cus=null;
      //  Customer cus=customerDao.getCustomerByPhone(customerPhone);
        if("".equals(customerId)){
            cus=new Customer();
            cus.setId(UUIDUtil.getUUID());
            cus.setName(customerName);
            cus.setCreateTime(t.getCreateTime());
            cus.setCreateBy(t.getCreateBy());
            cus.setContactSummary(t.getContactSummary());
            cus.setNextContactTime(t.getNextContactTime());
            cus.setOwner(t.getOwner());
            customerId=cus.getId();
            int count1=customerDao.save(cus);
            if(count1!=1){
                flag=false;
            }
        }
       // if("".equals(customerId)){t.setCustomerId(cus.getId());}else{t.setCustomerId(customerId);}
        System.out.println(customerId+"++++++++++++++++++++++++++");
        t.setCustomerId(customerId);
        int count2=tranDao.save(t);
        if(count2!=1){
            flag=false;
        }
        TranHistory tranHistory=new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setTranId(t.getId());
        tranHistory.setStage(t.getStage());
        tranHistory.setMoney(t.getMoney());
        tranHistory.setExpectedDate(t.getExpectedDate());
        tranHistory.setCreateTime(DateTimeUtil.getSysTime());
        tranHistory.setCreateBy(t.getCreateBy());
        int count3=tranHistoryDao.save(tranHistory);
        if(count3!=1){
            flag=false;
        }
        return flag;
    }

    public Tran detail(String id) {
        Tran t=tranDao.detail(id);
        return t;
    }

    public List<TranHistory> getHistoryListByTranId(String tranId) {
        List<TranHistory> thList=tranHistoryDao.getHistoryListByTranId(tranId);
        return thList;
    }

    public boolean changeStage(Tran t) {
        boolean flag=true;
        int count1=tranDao.changeStage(t);
        if(count1!=1){
            flag=false;
        }
        TranHistory th= new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setCreateBy(t.getEditBy());
        th.setCreateTime(DateTimeUtil.getSysTime());
        th.setExpectedDate(t.getExpectedDate());
        th.setMoney(t.getMoney());
        th.setTranId(t.getId());
        int count2=tranHistoryDao.save(th);
        if(count2!=1){
            flag=false;
        }
        return flag;
    }

    public Map<String, Object> getCharts() {
        int total =tranDao.getTotal();
        List<Map<String,Object>> tranList=tranDao.getCharts();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("total",total);
        map.put("dataList",tranList);
        return map;
    }


}
