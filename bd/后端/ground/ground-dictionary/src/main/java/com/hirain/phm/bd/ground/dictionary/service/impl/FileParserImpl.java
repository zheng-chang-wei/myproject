/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.dictionary.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.dictionary.controller.StringUtils;
import com.hirain.phm.bd.ground.dictionary.domain.FirstComponent;
import com.hirain.phm.bd.ground.dictionary.domain.SecondComponent;
import com.hirain.phm.bd.ground.dictionary.service.FileParser;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 13, 2020 2:18:41 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 13, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class FileParserImpl implements FileParser {

	/**
	 * @throws IOException
	 * @see com.hirain.phm.bd.ground.dictionary.service.FileParser#parse(java.io.InputStream)
	 */
	@Override
	public List<FirstComponent> parse(InputStream inputStream) throws IOException {
		List<String> lines = IOUtils.readLines(inputStream, Charset.forName("utf-8"));
		List<FirstComponent> list1 = new ArrayList<>();
		lines.forEach(l -> {
			String[] split = l.split(",");
			if (split.length > 0) {
				FirstComponent f = new FirstComponent();
				f.setName(split[0]);
				f.setActive(true);
				List<SecondComponent> list2 = new ArrayList<>();
				for (int i = 1; i < split.length; i++) {
					if (StringUtils.isNotEmpty(split[i])) {
						SecondComponent s = new SecondComponent();
						s.setName(split[i]);
						s.setActive(true);
						list2.add(s);
					}
				}
				f.setChildren(list2);
				list1.add(f);
			}
		});
		return list1;
	}

}
