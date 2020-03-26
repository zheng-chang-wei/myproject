package com.hirain.phm.synapsis.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JaxbUtil {

	private static final String JAXB_ENCODING_VALUE = "UTF-8";

	private static final boolean JAXB_FORMATTED_OUTPUT_VALUE = true;

	/**
	 * 序列化
	 */
	public static void marshall(final Object object, final String path) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, JAXB_ENCODING_VALUE);
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, JAXB_FORMATTED_OUTPUT_VALUE);
		marshaller.marshal(object, new File(path));
	}

	public static String marshall(Object object) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, JAXB_ENCODING_VALUE);
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
		StringWriter writer = new StringWriter();
		marshaller.marshal(object, writer);
		return writer.toString();
	}

	/**
	 * 解析
	 */
	public static Object unmarshaller(final Class<?> clazz, final String path) throws Exception {
		File file = new File(path);
		if (path == null || !file.exists()) {
			throw new FileNotFoundException();
		}
		JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		return unmarshaller.unmarshal(file);
	}

	public static Object xmlToObject(Class<?> clz, String xml) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(clz);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		StringReader reader = new StringReader(xml);
		return unmarshaller.unmarshal(reader);
	}
}
