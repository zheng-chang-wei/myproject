/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.file;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 2, 2019 6:53:16 PM
 * @Description
 *              <p>
 *              生成xml文件
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 2, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Component
@Profile("xml")
public class XmlFileGenerator implements FileGenerator {

	private String SUFFIX_XML = ".xml";

	/**
	 * @see com.hirain.phm.synapsis.file.FileGenerator#generate(java.lang.String, java.lang.Object)
	 */
	@Override
	public void generate(String filepath, Object object) throws Exception {
		filepath = filepath + SUFFIX_XML;
		marshal(object, filepath);
	}

	private void marshal(final Object object, final String filePath) throws Exception {
		final File file = new File(filePath);
		if (!file.exists()) {
			final File parentFile = file.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			file.createNewFile();
		}
		final JAXBContext context = JAXBContext.newInstance(object.getClass());
		final Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(object, file);
	}

}
