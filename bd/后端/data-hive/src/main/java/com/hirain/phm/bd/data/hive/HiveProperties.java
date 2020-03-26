package com.hirain.phm.bd.data.hive;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
public class HiveProperties {

	@Getter
	@Value("${hive.file.seperator:|}")
	private String seperator;
}
