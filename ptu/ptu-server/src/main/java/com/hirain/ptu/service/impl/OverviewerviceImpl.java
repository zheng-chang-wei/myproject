package com.hirain.ptu.service.impl;

import com.hirain.ptu.common.model.*;
import com.hirain.ptu.dao.ComIdDataMapper;
import com.hirain.ptu.dao.CsPortDataMapper;
import com.hirain.ptu.model.ComIdObject;
import com.hirain.ptu.model.CsPortObject;
import com.hirain.ptu.service.ObjectConfigService;
import com.hirain.ptu.service.OverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class OverviewerviceImpl implements OverviewService {

  @Autowired ComIdDataMapper comIdDataMapper;
  @Autowired CsPortDataMapper csPortDataMapper;

  @Autowired ObjectConfigService objectConfigService;

  @Override
  public ComIdDataOverviewResponse listComIdsOverview(CommonParms commonParms) {
    List<ComIdObject> comIdObjects = objectConfigService.listComIds();
    ComIdDataOverviewResponse comIdDataOverviewResponse = new ComIdDataOverviewResponse();
    comIdDataOverviewResponse.setAllComIdObjectSize(comIdObjects.size());
    List<ComIdDataOverview> comIdDataOverviews = comIdDataMapper.getComIdDataOverview(commonParms);
    comIdDataOverviewResponse.setComIdDataOverviewTableDataList(getComIdDataOverviewTableDataList(comIdObjects, comIdDataOverviews));
    return comIdDataOverviewResponse;
  }



  @Override
  public List<CsPortDataOverviewResponse> listCsPorts(CommonParms commonParms) {
    List<CsPortObject> csPortObjects = objectConfigService.listCsPorts();
    List<CsPortDataOverviewResponse> csPortDataOverviewResponses = new ArrayList<>();
    List<CsPortDataOverview> csPortDataOverview =
        csPortDataMapper.getCsPortDataOverview(commonParms);
    for (CsPortDataOverview csPortData : csPortDataOverview) {
      CsPortDataOverviewResponse csPortDataOverviewResponse = new CsPortDataOverviewResponse();
      Iterator<CsPortObject> iterator = csPortObjects.iterator();
      while (iterator.hasNext()) {
        CsPortObject csPortObject = iterator.next();
        if (csPortData.getComId().equals(csPortObject.getComId())
            && csPortData.getIp().equals(csPortObject.getIp())
            && csPortData.getPort().equals(csPortObject.getPort())) {
          csPortDataOverviewResponse.setCsPortData(csPortData);
          csPortDataOverviewResponse.setCsPortObject(csPortObject);
          csPortDataOverviewResponses.add(csPortDataOverviewResponse);
          iterator.remove();
          break;
        }
      }
    }
    return csPortDataOverviewResponses;
  }
  private List<ComIdDataOverviewTableData> getComIdDataOverviewTableDataList(List<ComIdObject> comIdObjects, List<ComIdDataOverview> comIdDataOverviews) {
    List<ComIdDataOverviewTableData> comIdDataOverviewTableDatas = new ArrayList<>();
    for (ComIdDataOverview comIdDataOverview : comIdDataOverviews) {
      ComIdDataOverviewTableData comIdDataOverviewTableData = new ComIdDataOverviewTableData();
      Iterator<ComIdObject> iterator = comIdObjects.iterator();
      while (iterator.hasNext()) {
        ComIdObject comIdObject = iterator.next();
        if (comIdDataOverview.getComId().equals(comIdObject.getComId())
                && comIdDataOverview.getIp().equals(comIdObject.getIp())) {
          comIdDataOverviewTableData.setComIdData(comIdDataOverview);
          comIdDataOverviewTableData.setComIdObject(comIdObject);
          comIdDataOverviewTableDatas.add(comIdDataOverviewTableData);
          iterator.remove();
          break;
        }
      }
    }
    return comIdDataOverviewTableDatas;
  }
}
