/*******************************************************************************
 * Copyright (c) 2011, 2011 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.mvb.util;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年12月4日 上午10:44:07
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年12月4日 changwei.zheng@hirain.com 1.0 create file
 */
public final class StringUtil {

	/**
	 * 工具类（私有构造方法）.
	 */
	private StringUtil() {
	}

	/******************************* 扩展名 常量 ******************************************/

	public static String EXTENSION_EXCEL_XLS = "xls";

	public static String EXTENSION_EXCEL_XLSX = "xlsx";

	public static String EXTENSION_DOC_DOC = "doc";

	public static String EXTENSION_DOC_DOCX = "docx";

	public static String EXTENSION_PPT_PPT = "ppt";

	public static String EXTENSION_PPT_PPTX = "pptx";

	public static String EXTENSION_TXT_TXT = "txt";

	public static String EXTENSION_XML_XML = "xml";

	public static String EXTENSION_PDF_PDF = "pdf";

	public static String EXTENSION_RAR_RAR = "rar";

	public static String EXTENSION_ZIP_ZIP = "zip";

	public static String EXTENSION_CFG_CFG = "cfg";

	public static String EXTENSION_CFG_CONFIGURATION = "configuration";

	public static String EXTENSION_INI_INI = "ini";

	public static String EXTENSION_LOG_LOG = "log";

	/******************************* 标点 常量 ******************************************/

	/**
	 * 英文逗号
	 */
	public static final String COMMA = ",";

	/**
	 * 点
	 */
	public static final String DOAT = ".";

	/**
	 * 英文分号
	 */
	public static final String SEMICOLON = ";";

	/**
	 * 冒号
	 */
	public static final String COLON = ":";

	/**
	 * 星号
	 */
	public static final String ASTERISK = "*";

	/**
	 * FilterExtensions *.
	 */
	public static final String DOAT_ASTERISK = ASTERISK + DOAT;

	/**
	 * 中文双引号 左引号
	 */
	public static String LEFT_QUOTATION_MARKS = "“";

	/**
	 * 中文双引号 右引号
	 */
	public static String RIGHT_QUOTATION_MARKS = "”";

	/******************************* 字符 常量 ******************************************/

	/**
	 * 
	 */
	public static final String NULL = null;

	/**
	 * 空字符串
	 */
	public static final String EMPTY = "";

	/**
	 * 空格
	 */
	public static final String SPACE = " ";

	public static final String TAB = "	";

	/**
	 * 一左斜杠
	 */
	public static final String LEFT_SLASH = "/";

	/**
	 * 
	 */
	public static final String UNDER_LINE = "_";

	/**
	 * 两右斜杠
	 */
	public static final String RIGHT_SLASH = "\\";

	/**
	 * 
	 */
	public static final String FILE_SEPARATOR = System.getProperty("file.separator");

	/**
	 * 
	 */
	public static final String USER_HOME = System.getProperty("user.home");

	/**
	 * 末尾包含file.separator(同PATH_USER_DIR)
	 */
	// public static final String PATH_INSTALL_Location = Platform.getInstallLocation().getURL().getPath().substring(1);

	/**
	 * 末尾不包含file.separator(同PATH_INSTALL_Location)
	 */
	public static final String PATH_USER_DIR = System.getProperty("user.dir");

	/**
	 * 
	 */
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	public static final String LINE_SEPARATOR_SPLIT_WINDOW = "\r\n";

	/**
	 * 十六进制的前缀"0x"
	 */
	public static final String RADIX_HEX_PREFIX = "0x";

	public static final String APP_VERSION = "版本：";

	/******************************* 格式化 常量 ******************************************/

	/**
	 * 0.00
	 */
	public static final String FORMAT_DECIMAL = "0.00";

	/**
	 * yyyyMMddHHmmss
	 */
	public static final String FORMAT_DATETIME = "yyyyMMddHHmmss";

	/**
	 * yyyyMMddHHmmssSSS
	 */
	public static final String FORMAT_DATETIME_MILLI = "yyyyMMddHHmmssSSS";

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String FORMAT_DATETIME_SPLIT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * yyyy-MM-dd HH:mm:ss SSS
	 */
	public static final String FORMAT_DATETIME_SPLIT_MILL = "yyyy-MM-dd HH:mm:ss SSS";

	/**
	 * yyyyMMdd_HHmmss
	 */
	public static final String FORMAT_DATETIME_UNDERLINE = "yyyyMMdd_HHmmss";

	/******************************* 字符 方法 ******************************************/

	/**
	 * @param string
	 *            需要判断的字符串.
	 * @return 判断结果.
	 */
	public static boolean isEmpty(final String string) {
		return EMPTY.equals(string) || NULL == string;
	}

	/**
	 * @param string
	 *            需要判断的字符串.
	 * @return 判断结果.
	 */
	public static boolean isNotEmpty(final String string) {
		return !isEmpty(string);
	}

	/**
	 * @param str1
	 *            需要判断的字符串.
	 * @param str2
	 *            需要判断的字符串.
	 * @return 判断结果.
	 */
	public static boolean isEquals(final String str1, final String str2) {
		if (str1 == NULL || str2 == NULL) {
			return false;
		}
		return str1.equals(str2);
	}
}
