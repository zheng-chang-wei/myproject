package com.zcw.dictionary.util;

import java.io.File;
import java.io.IOException;

import com.hg.xdoc.XDocService;

public class Word2PDF {

	public static void main(String[] args) {
		long old = System.currentTimeMillis();
		XDocService service = new XDocService();
		try {
			service.to("d:\\a.doc", new File("d:/a.pdf"));
			long now = System.currentTimeMillis();
			System.out.println((now - old) / 1000.0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
