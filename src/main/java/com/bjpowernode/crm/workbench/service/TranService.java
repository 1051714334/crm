package com.bjpowernode.crm.workbench.service;


import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

public interface TranService {

    boolean save(Tran t,String customerName,String customerId);

    Tran detail(String id);

    List<TranHistory> getHistoryListByTranId(String tranId);


    boolean changeStage(Tran t);

    Map<String, Object> getCharts();

    Map<String,Object> getById(String id,Map<String,String> pMap);

    boolean update(Tran t);
}
