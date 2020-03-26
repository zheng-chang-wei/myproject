package com.hirain.phm.synapsis.mvb.util;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年12月4日 上午11:13:26
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年12月4日 changwei.zheng@hirain.com 1.0 create file
 */
public class ExcelConstants {

	public static String EXCEL_EXTENSION_XLS = "xls";

	public static String EXCEL_EXTENSION_XLSX = "xlsx";

	public static String DATE_FORMAT_DD_MMM_YYYY = "dd-MMM-yyyy";

	public static String CHECK_DOUBLE_LONG = ".0";

	public static String SHEET_NAME_COVER = "封面";

	public static String SHEET_NAME_TYPES = "类型声明";

	// public static String SHEET_NAME_TRAINS = "车辆信息";

	// public static String SHEET_NAME_SYSTEMS = "系统信息";

	// public static String SHEET_NAME_CARS = "车厢信息";

	public static String SHEET_NAME_DEVICES = "设备信息";

	public static String SHEET_NAME_PORTS = "端口信息";

	public static String SHEET_NAME_SIGNALS_DEFAULT = "数据信息（默认）";

	public static String[] SHEET_NAMES = { SHEET_NAME_COVER, SHEET_NAME_TYPES, SHEET_NAME_DEVICES, SHEET_NAME_PORTS };

	public static String[] SHEET_HEADER_TYPES = { "标准类型", "填表类型", "数值范围", "位数" };

	public static String[] SHEET_HEADER_DEVICES = { "设备地址", "设备名称", "所属系统", "所属车厢", "描述信息" };

	public static String[] SHEET_HEADER_PORTS = { "端口地址", "报文编号", "端口宽度", "特征周期", "时间偏移", "是否激活", "源设备", "宿设备", "大小端设置", "描述信息" };

	public static String[] SHEET_HEADER_SIGNALS = { "报文编号", "变量名称", "字节偏移", "位偏移", "数据类型", "数据长度", "乘积因子", "偏移值", "数据范围", "信号名称", "信号单位", "描述信息" };

	/**
	 * 用户可替换的bin文件夹中的模板(优先)
	 */
	public final static String MCT_EXCEL_TEMPLATE_CUSTOM = "MCT数据流模板.xlsx";

	public final static String MCT_EXCEL_TEMPLATE = "template";

	public static final String ROOT_NAME_DEFAULT = "MVB Model";

	public static final String ROOT_TYPE_DEFAULT = "mvb";

	public static final int SHEET_COVER_VERSION_ROW_INDEX = 15;

	public static final int SHEET_TYPES_START_ROW_INDEX = 24;

	public static final int SHEET_TYPES_START_COLUMN_INDEX = 2;

	public static final int SHEET_DEVICES_START_ROW_INDEX = 23;

	public static final int SHEET_DEVICES_START_COLUMN_INDEX = 2;

	public static final int SHEET_PORTS_START_ROW_INDEX = 27;

	public static final int SHEET_PORTS_START_COLUMN_INDEX = 1;

	public static final int SHEET_SIGNALS_START_ROW_INDEX = 30;

	public static final int SHEET_SIGNALS_START_COLUMN_INDEX = 1;

}
