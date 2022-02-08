package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.dao.*;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.service.ClueService;

import java.util.List;

public class ClueServiceImpl implements ClueService {
    //线索表
    private ClueDao clueDao= SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private ClueActivityRelationDao clueActivityRelationDao=SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);
    private ClueRemarkDao clueRemarkDao= SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);
    //客户表
    private CustomerDao customerDao=SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    private CustomerRemarkDao customerRemarkDao=SqlSessionUtil.getSqlSession().getMapper(CustomerRemarkDao.class);
    //联系人表
    private ContactsDao contactsDao=SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    private ContactsRemarkDao contactsRemarkDao=SqlSessionUtil.getSqlSession().getMapper(ContactsRemarkDao.class);
    private ContactsActivityRelationDao contactsActivityRelationDao=SqlSessionUtil.getSqlSession().getMapper(ContactsActivityRelationDao.class);
    //交易表
    private TranDao tranDao=SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao=SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);
    public boolean save(Clue clue) {
        boolean flag=true;
       int count= clueDao.save(clue);
       if(count!=1){
           flag=false;
       }
        return flag;
    }

    public Clue detail(String id) {
        Clue clue=clueDao.detail(id);
        return clue;
    }

    public boolean unbund(String id) {
        boolean flag=true;
        int count=clueActivityRelationDao.unbund(id);
        if(count!=1){
            flag=false;
        }
        return flag;
    }

    public boolean bund(String cid, String[] aid) {
        boolean flag=true;
        for(String id:aid){
            ClueActivityRelation car=new ClueActivityRelation();
            car.setId(UUIDUtil.getUUID());
            car.setClueId(cid);
            car.setActivityId(id);
           int count= clueActivityRelationDao.bund(car);
           if(count!=1){
               flag=false;
           }
        }
        return flag;
    }

    public boolean convert(String clueId, Tran t, String createBy) {
       String createTime= DateTimeUtil.getSysTime();
        boolean flag=true;
        Clue c=clueDao.getById(clueId);
        String company=c.getCompany();
        Customer cus= customerDao.getCustomerByName(company);
        if(cus==null){
            cus=new Customer();
            cus.setId(UUIDUtil.getUUID());
            cus.setAddress(c.getAddress());
            cus.setWebsite(c.getWebsite());
            cus.setPhone(c.getPhone());
            cus.setOwner(c.getOwner());
            cus.setNextContactTime(c.getNextContactTime());
            cus.setName(company);
            cus.setDescription(c.getDescription());
            cus.setCreateBy(createBy);
            cus.setCreateTime(createTime);
            cus.setContactSummary(c.getContactSummary());
            int count1=customerDao.save(cus);
            if(count1!=1){
                flag=false;
            }
        }
        Contacts con=new Contacts();
        con.setId(UUIDUtil.getUUID());
        con.setSource(c.getSource());
        con.setOwner(c.getOwner());
        con.setNextContactTime(c.getNextContactTime());
        con.setMphone(c.getMphone());
        con.setJob(c.getJob());
        con.setEmail(c.getEmail());
        con.setFullname(c.getFullname());
        con.setDescription(c.getDescription());
        con.setCustomerId(cus.getId());
        con.setCreateBy(createBy);
        con.setCreateTime(createTime);
        con.setContactSummary(c.getContactSummary());
        con.setAppellation(c.getAppellation());
        con.setAddress(c.getAddress());
        int count2=contactsDao.save(con);
        if(count2!=1){
            flag=false;
        }
        List<ClueRemark> clueRemarkList=clueRemarkDao.getListByClueId(clueId);
        for(ClueRemark clueRemark:clueRemarkList){
           String noteContent=clueRemark.getNoteContent();
        }
        return flag;

    }


}
