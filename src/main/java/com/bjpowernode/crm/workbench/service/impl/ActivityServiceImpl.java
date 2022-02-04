package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.vo.PaginationVo;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao=SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao=SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao=SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
    public boolean save(Activity activity) {
       int count= activityDao.save(activity);
       boolean flag=true;
       if(count!=1){
           flag=false;
       }
        return flag;
    }

    public PaginationVo<Activity> pageList(Map<String, Object> map) {
       int total=activityDao.getTotalByCondition(map);

       List<Activity> dataList=activityDao.getActivityListByCondition(map);
        PaginationVo<Activity> vo=new PaginationVo<Activity>();
        vo.setTotal(total);
        vo.setDataList(dataList);
       return vo;
    }

    public boolean delete(String[] ids) {
        boolean flag=true;
        int count1=activityRemarkDao.getCountByAids(ids);
        int count2=activityRemarkDao.deleteByAids(ids);
        if(count1!=count2){
            flag=false;
        }
        int count3=activityDao.delete(ids);
        if(count3!=ids.length){
            flag=false;
        }
        return flag;
    }

    public Map<String, Object> getUserListAndActivity(String id) {
        Map<String,Object> map=new HashMap<String, Object>();
       List<User> users=userDao.getUserList();
       Activity activity=activityDao.getById(id);
       map.put("userList",users);
       map.put("a",activity);
        return map;
    }

    public boolean update(Activity activity) {
        int count= activityDao.update(activity);
        boolean flag=true;
        if(count!=1){
            flag=false;
        }
        return flag;
    }

    public Activity detail(String id) {
        Activity activity=activityDao.detail(id);
        return activity;
    }

    public List<ActivityRemark> showRemarkList(String activityId) {
        List<ActivityRemark> arList=activityRemarkDao.showRemarkList(activityId);
        return arList;
    }

    public boolean deleteRemark(String id) {
        boolean flag=true;
        int count=activityRemarkDao.deleteRemark(id);
        if(count!=1){
            flag=false;
        }
        return flag;
    }

    public boolean saveRemark(ActivityRemark ar) {
        boolean flag=true;
        int count=activityRemarkDao.saveRemark(ar);
        if(count!=1){
            flag=false;
        }
        return flag;
    }

    public boolean updateRemark(ActivityRemark ar) {
        boolean flag=true;
        int count=activityRemarkDao.updateRemark(ar);
        if(count!=1){flag=false;}
        return flag;
    }

    public List<Activity> getActivityListByClueId(String clueId) {
        List<Activity> aList=activityDao.getActivityListByClueId(clueId);
        return aList;
    }

    public List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map) {
        List<Activity> aList=activityDao.getActivityListByNameAndNotByClueId(map);
        return aList;
    }
}
