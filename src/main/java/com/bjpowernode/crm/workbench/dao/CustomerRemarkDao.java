package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.CustomerRemark;

import java.util.List;

public interface CustomerRemarkDao {

    int save(CustomerRemark customerRemark);

    List<CustomerRemark> showRemarkList(String customerId);

    int saveRemark(CustomerRemark cusRemark);

    int deleteRemark(String id);

    int updateRemark(CustomerRemark cr);
}
