package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;

import java.util.List;

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
}
