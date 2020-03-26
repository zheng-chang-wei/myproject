package com.hirain.phm.bd.common.serialize;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.file.SeekableByteArrayInput;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.reflect.ReflectDatumWriter;

public class AvroUtil {

	public static <T> byte[] toBytes(Schema schema, T object) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DatumWriter<T> datumWriter = new ReflectDatumWriter<>(schema);
		DataFileWriter<T> dataFileWriter = new DataFileWriter<>(datumWriter);

		dataFileWriter.create(schema, bos);
		dataFileWriter.append(object);

		dataFileWriter.close();

		return bos.toByteArray();
	}

	public static <T> T toObject(Schema schema, byte[] bs, T object) throws IOException {
		SeekableByteArrayInput input = new SeekableByteArrayInput(bs);

		DatumReader<T> datumReader = new ReflectDatumReader<>(schema);
		DataFileReader<T> dataFileReader = new DataFileReader<>(input, datumReader);
		object = dataFileReader.next(object);
		dataFileReader.close();
		return object;
	}
}
