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
           CustomerRemark customerRemark=new CustomerRemark();
           customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(createTime);
            customerRemark.setCustomerId(cus.getId());
            customerRemark.setEditFlag("0");
            customerRemark.setNoteContent(noteContent);
            int count3=customerRemarkDao.save(customerRemark);
            if(count3!=1){
                flag=false;
            }

            ContactsRemark contactsRemark=new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setContactsId(con.getId());
            contactsRemark.setEditFlag("0");
            contactsRemark.setNoteContent(noteContent);
            int count4=contactsRemarkDao.save(contactsRemark);
            if(count3!=1){
                flag=false;
            }
        }
        List<ClueActivityRelation> clueActivityRelationList=clueActivityRelationDao.getListByClueId(clueId);
        for(ClueActivityRelation clueActivityRelation:clueActivityRelationList){
            String activityId=clueActivityRelation.getActivityId();
            ContactsActivityRelation contactsActivityRelation =new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(activityId);
            contactsActivityRelation.setContactsId(con.getId());
            int count5=contactsActivityRelationDao.save(contactsActivityRelation);
            if(count5!=1){
                flag=false;
            }
        }
        if(t!=null){
            t.setSource(c.getSource());
            t.setOwner(c.getOwner());
            t.setNextContactTime(c.getNextContactTime());
            t.setDescription(c.getDescription());
            t.setCustomerId(cus.getId());
            t.setContactSummary(c.getContactSummary());
            t.setContactsId(con.getId());
            int count6=tranDao.save(t);
            if(count6!=1){
                flag=false;
            }
            TranHistory th=new TranHistory();
            th.setId(UUIDUtil.getUUID());
            th.setCreateBy(createBy);
            th.setCreateTime(createTime);
            th.setExpectedDate(t.getExpectedDate());
            th.setMoney(t.getMoney());
            th.setStage(t.getStage());
            th.setTranId(t.getId());
            int count7=tranHistoryDao.save(th);
            if(count7!=1){
                flag=false;
            }
        }
       for(ClueRemark clueRemark:clueRemarkList){
            int count8=clueRemarkDao.delete(clueRemark);
            if(count8!=1){
                flag=false;
            }
        }
       for(ClueActivityRelation clueActivityRelation:clueActivityRelationList){
            int count9=clueActivityRelationDao.delete(clueActivityRelation);
            if(count9!=1){
                flag=false;
            }
        }
        int count10=clueDao.delete(clueId);
        if(count10!=1){
            flag=false;
        }
        return flag;

    }


}
