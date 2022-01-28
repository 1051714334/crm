package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.vo.PaginationVo;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;

import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao=SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);


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
        vo.setTatol(total);
        vo.setDataList(dataList);
       return vo;
    }
}
