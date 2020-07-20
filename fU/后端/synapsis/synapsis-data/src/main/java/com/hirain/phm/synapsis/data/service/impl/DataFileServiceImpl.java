/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.data.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.data.param.FileTreeNode;
import com.hirain.phm.synapsis.data.param.NodeLevel;
import com.hirain.phm.synapsis.data.service.DataFileService;
import com.hirain.phm.synapsis.exception.SynapsisException;

import cn.hutool.core.util.ZipUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 18, 2019 3:00:22 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 18, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Service
@ConfigurationProperties("synapsis.data")
public class DataFileServiceImpl implements DataFileService {

	@Getter
	@Setter
	private String root;

	/**
	 * 要导出的文件所在目录
	 */
	private String exportRoot = System.getProperty("user.dir") + "//data_files";

	private String zipRoot = System.getProperty("user.dir") + "//data_files.zip";

	/**
	 * @see com.hirain.phm.synapsis.data.service.DataFileService#listRoot()
	 */
	@Override
	public List<FileTreeNode> listRoot() throws SynapsisException {
		return listNodes(root, NodeLevel.Root);
	}

	/**
	 * @see com.hirain.phm.synapsis.data.service.DataFileService#listNodes(com.hirain.phm.synapsis.data.param.FileTreeNode)
	 */
	@Override
	public List<FileTreeNode> listNodes(FileTreeNode node) throws SynapsisException {
		if (node.getLevel().equals(NodeLevel.Leaf)) {
			return new ArrayList<>();
		}
		return listNodes(node.getPath(), node.getLevel());
	}

	/**
	 * @see com.hirain.phm.synapsis.data.service.DataFileService#listNodes(java.lang.String, NodeLevel)
	 */
	@Override
	public List<FileTreeNode> listNodes(String path, NodeLevel parentLevel) throws SynapsisException {
		File folder = new File(path);
		if (!folder.exists()) {
			throw new SynapsisException("文件路径不正确：" + path);
		}
		NodeLevel level = getLevel(parentLevel);
		List<FileTreeNode> nodes = new ArrayList<>();
		File rootDir = new File(path);
		File[] files = rootDir.listFiles((FilenameFilter) (dir, name) -> !name.equals("lost+found"));
		for (File file : files) {
			FileTreeNode node = new FileTreeNode();
			node.setPath(file.getAbsolutePath());
			node.setName(file.getName());
			nodes.add(node);
			node.setLevel(level);
		}
		return sortByDate(nodes);
	}

	/**
	 * 根据年月日时分秒倒序排列
	 */
	private List<FileTreeNode> sortByDate(List<FileTreeNode> nodes) {
		// 返回值为int类型，大于0表示正序，小于0表示逆序
		Collections.sort(nodes, (node1, node2) -> {
			String name1 = node1.getName();
			String name2 = node2.getName();
			return name1.compareTo(name2) > 0 ? -1 : 1;
		});
		return nodes;
	}

	/**
	 * @param parentLevel
	 */
	private NodeLevel getLevel(NodeLevel parentLevel) {
		int index = parentLevel.ordinal() + 1;
		return NodeLevel.values()[index];
	}

	/**
	 * @throws SynapsisException
	 * @see com.hirain.phm.synapsis.data.service.DataFileService#listNodes(java.lang.String, java.util.Date, java.util.Date)
	 */
	@Override
	public List<FileTreeNode> listNodes(FileTreeNode node, Date startDate, Date endDate) throws SynapsisException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		NodeLevel level = getLevel(node.getLevel());
		List<FileTreeNode> nodes = new ArrayList<>();
		File folder = new File(node.getPath());
		File[] files = folder.listFiles();
		for (File file : files) {
			String dateStr = getDate(node, folder, file);
			if (dateStr != null) {
				Date date;
				try {
					date = sdf.parse(dateStr);
					if (date != null && date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0) {
						FileTreeNode child = new FileTreeNode();
						child.setPath(file.getAbsolutePath());
						child.setName(file.getName());
						child.setLevel(level);
						nodes.add(child);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return nodes;
	}

	/**
	 * @param node
	 * @param folder
	 * @param file
	 * @return
	 */
	private String getDate(FileTreeNode node, File folder, File file) {
		String date = null;
		if (node.getLevel().equals(NodeLevel.Root)) {
			date = file.getName() + "01";
		} else if (node.getLevel().equals(NodeLevel.Month)) {
			date = folder.getName() + file.getName();

		} else if (node.getLevel().equals(NodeLevel.Date)) {
			date = folder.getParentFile().getName() + folder.getName();
		}
		return date;
	}

	/**
	 * @throws SynapsisException
	 * @see com.hirain.phm.synapsis.data.service.DataFileService#listRoot(java.util.Date, java.util.Date)
	 */
	@Override
	public List<FileTreeNode> listRoot(Date startDate, Date endDate) throws SynapsisException {
		FileTreeNode node = new FileTreeNode();
		node.setPath(root);
		node.setLevel(NodeLevel.Root);
		return listNodes(node, startDate, endDate);
	}

	/**
	 * @throws IOException
	 * @see com.hirain.phm.synapsis.data.service.DataFileService#deleteFile(java.lang.String)
	 */
	@Override
	public boolean deleteFile(String filePath) {
		boolean result = true;
		File file = new File(filePath);
		if (file.exists()) {
			result = FileUtils.deleteQuietly(file);
		}
		return result;
	}

	/**
	 * @see com.hirain.phm.synapsis.data.service.DataFileService#downloadFile(java.lang.String, java.lang.String)
	 */
	@Override
	public void downloadFile(HttpServletResponse response, String... sourceFilePath) throws Exception {
		File uploadRootDir = new File(exportRoot);
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}
		copyFile(exportRoot, sourceFilePath);
		ZipUtil.zip(exportRoot);
		exportFile(response, "download_datafiles");
		FileUtils.deleteDirectory(uploadRootDir);
	}

	private void copyFile(String destFilePath, String... srcFilePath) throws IOException {
		for (String path : srcFilePath) {
			File srcDir = new File(path);
			if (srcDir.exists()) {
				File destDir = new File(destFilePath);
				FileUtils.copyDirectoryToDirectory(srcDir, destDir);
			}
		}
	}

	private void exportFile(HttpServletResponse response, String fileName) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		File file = new File(zipRoot);
		String extension = getExtension(file.getName());
		response.reset();
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName + extension, "UTF-8"));
		InputStream input = new FileInputStream(file);
		OutputStream out = response.getOutputStream();
		byte[] buff = new byte[1024];
		int index = 0;
		while ((index = input.read(buff)) != -1) {
			out.write(buff, 0, index);
			out.flush();
		}
		out.close();
		input.close();
	}

	private String getExtension(String filename) {
		int lastIndexOf = filename.lastIndexOf(".");
		String extension = filename.substring(lastIndexOf);
		return extension;
	}

}
