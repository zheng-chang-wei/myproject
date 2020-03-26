package com.hirain.phm.bd.common;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author jrwangxin1 字节数组压缩解压工具
 */
public class ZipUtil {

	/** 压缩 */
	public static byte[] zip(byte[] source) throws IOException {
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		final ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
		zipOutputStream.putNextEntry(new ZipEntry("0"));
		final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(zipOutputStream);
		bufferedOutputStream.write(source);
		bufferedOutputStream.close();
		return byteArrayOutputStream.toByteArray();
	}

	/** 解压 */
	public static byte[] unzip(byte[] source) throws IOException {
		final ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(source));
		while (zipInputStream.getNextEntry() != null) {
			final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

			final byte[] buffer = new byte[4096];
			int i = 0;
			while ((i = zipInputStream.read(buffer)) != -1) {
				byteArrayOutputStream.write(buffer, 0, i);
			}
			return byteArrayOutputStream.toByteArray();
		}
		return null;
	}

	public static byte[] uncompress(byte[] input) throws IOException {
		final Inflater inflater = new Inflater();
		inflater.setInput(input);
		final ByteArrayOutputStream baos = new ByteArrayOutputStream(input.length);
		try {
			final byte[] buff = new byte[1024];
			while (!inflater.finished()) {
				final int count = inflater.inflate(buff);
				// 防止死循环
				if (count == 0) {
					break;
				}
				baos.write(buff, 0, count);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			baos.close();
		}
		inflater.end();
		final byte[] output = baos.toByteArray();
		return output;
	}

	public static byte[] compress(byte[] data) {
		byte[] output;
		final Deflater compress = new Deflater();

		compress.reset();
		compress.setInput(data);
		compress.finish();
		final ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
		try {
			final byte[] buf = new byte[1024];
			while (!compress.finished()) {
				final int i = compress.deflate(buf);
				bos.write(buf, 0, i);
			}
			output = bos.toByteArray();
		} catch (final Exception e) {
			output = data;
			e.printStackTrace();
		} finally {
			try {
				bos.close();
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
		compress.end();
		return output;
	}

	public static final byte[] jdkGzip(byte[] data) throws IOException {
		final ByteArrayOutputStream out = new ByteArrayOutputStream(8192);
		GZIPOutputStream gzip = null;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(data);
			gzip.finish();
			return out.toByteArray();
		} finally {
			if (gzip != null) {
				gzip.close();
			}
		}
	}

	public static final byte[] jdkGunzip(byte[] bytes) throws IOException {
		if (bytes == null || bytes.length == 0) {
			return null;
		}
		final ByteArrayOutputStream baos = new ByteArrayOutputStream(8192);
		final GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(bytes));
		int count;
		final byte[] buffer = new byte[8192];
		while ((count = gis.read(buffer)) != -1) {
			baos.write(buffer, 0, count);
		}
		gis.close();
		return baos.toByteArray();
	}
}