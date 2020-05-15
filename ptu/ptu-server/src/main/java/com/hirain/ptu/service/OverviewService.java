package com.hirain.ptu.service;

import com.hirain.ptu.common.model.ComIdDataOverviewResponse;
import com.hirain.ptu.common.model.CommonParms;
import com.hirain.ptu.common.model.CsPortDataOverviewResponse;

import java.util.List;

public interface OverviewService {

  ComIdDataOverviewResponse listComIdsOverview(CommonParms commonParms);

  List<CsPortDataOverviewResponse> listCsPorts(CommonParms commonParms);
}
