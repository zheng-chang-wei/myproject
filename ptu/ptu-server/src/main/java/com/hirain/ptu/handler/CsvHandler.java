// package com.hirain.ptu.handler;
//
// import com.hirain.ptu.common.model.AttributeMappingConfigurationData;
// import com.hirain.ptu.model.ComIdData;
// import com.hirain.ptu.model.CsPortData;
// import com.hirain.ptu.model.DownloadedFile;
// import com.hirain.ptu.service.DataService;
// import com.hirain.ptu.service.ManageService;
// import org.apache.commons.beanutils.BeanUtils;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.scheduling.annotation.Async;
// import org.springframework.stereotype.Service;
//
// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStream;
// import java.io.InputStreamReader;
// import java.time.Duration;
// import java.time.Instant;
// import java.time.LocalDateTime;
// import java.time.ZoneId;
// import java.time.format.DateTimeFormatter;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.Date;
// import java.util.List;
//
/// **
// * @author changwei.zheng
// * @date 2020/4/4 20:10
// * @describe
// */
// @Service
// public class CsvHandler {
//    @Autowired
//    DataService dataService;
//
//    @Autowired
//    ManageService manageService;
//    /**
//     * 读取CSV文件
//     *
//     * @return
//     */
//    @Async
//    public void readComIdCSV(InputStream fileInputStream) throws Exception {
//        List<List<ComIdData>> comIdDataList = new ArrayList<>();
//        List<String> comIdAndIps = new ArrayList<>();
//        List<String> firstRowData = null;
//        String line = null;
//        BufferedReader bufferedReader = null;
//        InputStreamReader inputStreamReader = null;
//        try {
//            inputStreamReader = new InputStreamReader(fileInputStream, "utf-8");
//            bufferedReader = new BufferedReader(inputStreamReader);
//            if ((line = bufferedReader.readLine()) != null) {
//                firstRowData = Arrays.asList(line.split(","));
//            }
//            while ((line = bufferedReader.readLine()) != null) {
//                ComIdData comIdData = new ComIdData();
//                // 数据行
//                String[] items = line.split(",");
//                for (int i = 0; i < firstRowData.size(); i++) {
//                    String head = firstRowData.get(i);
//                    BeanUtils.setProperty(
//                            comIdData, AttributeMappingConfigurationData.comIdMapData.get(head),
// items[i].trim());
//                }
//                String comIdAndIp = comIdData.getComId() + "_" + comIdData.getIp();
//                int index = comIdAndIps.indexOf(comIdAndIp);
//                if (index == -1) {
//                    comIdAndIps.add(comIdAndIp);
//                    List<ComIdData> comIdDatas = new ArrayList<>();
//                    comIdDatas.add(comIdData);
//                    comIdDataList.add(comIdDatas);
//                } else {
//                    comIdDataList.get(index).add(comIdData);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            closeIO(fileInputStream, bufferedReader, inputStreamReader);
//        }
//        insertComIdData(comIdDataList);
//    }
////    @Async
//    public void readCsPortCSV(InputStream fileInputStream) throws Exception {
//        List<List<CsPortData>> csPortDataDataList = new ArrayList<>();
//        List<String> comIdAndIps = new ArrayList<>();
//        List<String> firstRowData = null;
//        String line = null;
//        BufferedReader bufferedReader = null;
//        InputStreamReader inputStreamReader = null;
//        try {
//            inputStreamReader = new InputStreamReader(fileInputStream, "utf-8");
//            bufferedReader = new BufferedReader(inputStreamReader);
//            if ((line = bufferedReader.readLine()) != null) {
//                firstRowData = Arrays.asList(line.split(","));
//            }
//            while ((line = bufferedReader.readLine()) != null) {
//                CsPortData csPortData = new CsPortData();
//                // 数据行
//                String[] items = line.split(",");
//                for (int i = 0; i < firstRowData.size(); i++) {
//                    String head = firstRowData.get(i);
//
// BeanUtils.setProperty(csPortData,AttributeMappingConfigurationData.csPortMapData.get(head),items[i].trim());
//                }
//                String comIdAndIp =
//                        csPortData.getComId() + "_" + csPortData.getIp() + "_" +
// csPortData.getPort();
//                int index = comIdAndIps.indexOf(comIdAndIp);
//                if (index == -1) {
//                    comIdAndIps.add(comIdAndIp);
//                    List<CsPortData> csPortDatas = new ArrayList<>();
//                    csPortDatas.add(csPortData);
//                    csPortDataDataList.add(csPortDatas);
//                } else {
//                    csPortDataDataList.get(index).add(csPortData);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            closeIO(fileInputStream, bufferedReader, inputStreamReader);
//        }
//        insertCsPortData(csPortDataDataList);
//    }
//
//    private void closeIO(InputStream fileInputStream, BufferedReader bufferedReader,
// InputStreamReader inputStreamReader) throws IOException {
//        if (bufferedReader != null) {
//            bufferedReader.close();
//        }
//        if (inputStreamReader != null) {
//            inputStreamReader.close();
//        }
//        if (fileInputStream != null) {
//            fileInputStream.close();
//        }
//    }
//
//    private void insertComIdData(List<List<ComIdData>> comIdDataList) {
//        for (List<ComIdData> comIdDatas : comIdDataList) {
//            ComIdData comIdData = comIdDatas.get(0);
//            String tableName =
//                    dataService.getComIdTableName(String.valueOf(comIdData.getComId()),
// comIdData.getIp());
//            Boolean isTableExist = dataService.isTableExist(tableName);
//            if (isTableExist) {
//                Date date = comIdDatas.get(comIdDatas.size() - 1).getDate();
//                // 如果表存在，查看分区是否够用，不够用添加分区
//                addPartition(tableName, date);
//            } else {
//                manageService.createComIdDataTable(tableName, partitions());
//            }
//            dataService.insertComIdData(tableName, comIdDatas);
//        }
//    }
//
//    private void insertCsPortData(List<List<CsPortData>> comIdDataList) {
//        for (List<CsPortData> csPortDatas : comIdDataList) {
//            CsPortData csPortData = csPortDatas.get(0);
//            String tableName =
//                    dataService.getCsPostTableName(
//                            String.valueOf(csPortData.getComId()),
//                            csPortData.getIp(),
//                            String.valueOf(csPortData.getPort()));
//            Boolean isTableExist = dataService.isTableExist(tableName);
//            if (isTableExist) {
//                Date date = csPortDatas.get(csPortDatas.size() - 1).getDate();
//                // 如果表存在，查看分区是否够用，不够用添加分区
//                addPartition(tableName, date);
//            } else {
//                manageService.createCsPortDataTable(tableName, partitions());
//            }
//            dataService.insertCsPortData(tableName, csPortDatas);
//        }
//    }
//
//    /**
//     * 获取下月的今天到前70天的日期
//     *
//     * @return
//     */
//    private List<String> partitions() {
//        final List<String> partitions = new ArrayList<>();
//        final LocalDateTime date = LocalDateTime.now();
//        LocalDateTime nextMonth = date.plusMonths(1);
//        for (int i = 70; i >= 0; i--) {
//            LocalDateTime dateTime = nextMonth.plusDays(-i);
//            final String time = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//            partitions.add(time);
//        }
//        return partitions;
//    }
//
//    private void addPartition(String tableName, Date date) {
//        String partition = manageService.lastPartition(tableName);
//        if (partition.startsWith("p")) {
//            partition = partition.substring(1);
//        }
//        // 最后一个分区时间
//        final LocalDateTime lastPartitionDate =
//                LocalDateTime.parse(partition + "000000",
// DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
//        // 插入数据的最大时间
//        LocalDateTime maxInsertDate = date2LocalDateTime(date);
//        final Duration duration = Duration.between(lastPartitionDate, maxInsertDate);
//        final long days = duration.toDays();
//        if (days >= 0) {
//            manageService.addPartitions(tableName, partitions(lastPartitionDate, days));
//        }
//    }
//
//    private List<String> partitions(LocalDateTime lastPartitionDate, long days) {
//        final List<String> partitions = new ArrayList<>();
//        for (int i = 0; i <= days + 31; i++) {
//            LocalDateTime dateTime = lastPartitionDate.plusDays(i + 1);
//            final String time = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//            partitions.add(time);
//        }
//        return partitions;
//    }
//    /**
//     * Date转LocalDateTime
//     *
//     * @param date
//     * @return
//     */
//    private LocalDateTime date2LocalDateTime(Date date) {
//        Instant instant = date.toInstant();
//        ZoneId zoneId = ZoneId.systemDefault();
//        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
//        return localDateTime;
//    }
//  //  private void setValue(Object obj, String paramName, String value) throws
//  // IllegalAccessException {
//  //    Field[] fields = obj.getClass().getDeclaredFields();
//  //    if (paramName != null && paramName.length() != 0) {
//  //      for (Field field : fields) {
//  //        if (field.getName().equals(paramName)) {
//  //          field.setAccessible(true);
//  //          Type genericType = field.getGenericType();
//  //          if (genericType.toString().contains("Integer")) {
//  //            field.set(obj, Float.valueOf(value).intValue());
//  //          } else if (genericType.toString().contains("Byte")) {
//  //            field.set(obj, (byte) Float.valueOf(value).intValue());
//  //          } else if (genericType.toString().contains("Float")) {
//  //            field.set(obj, Float.valueOf(value));
//  //          } else {
//  //            field.set(obj, value);
//  //          }
//  //          break;
//  //        }
//  //      }
//  //    }
//  //  }
//
// }
