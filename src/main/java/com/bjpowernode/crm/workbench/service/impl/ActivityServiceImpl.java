package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.vo.PaginationVo;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;

import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao=SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao=SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);

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
}
