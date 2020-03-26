/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.decode.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.avro.Schema;
import org.springframework.stereotype.Component;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月22日 上午11:53:06
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月22日 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class SchemaManager {

	private Map<String, Schema> cache = new ConcurrentHashMap<>();

	public Schema getSchema(String file) {
		Schema schema = cache.get(file);
		if (schema == null) {
			InputStream inputStream = ClassLoader.getSystemResourceAsStream(file);
			try {
				Schema schema2 = new Schema.Parser().parse(inputStream);
				schema = schema2;
				cache.put(file, schema);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return schema;
	}
}
