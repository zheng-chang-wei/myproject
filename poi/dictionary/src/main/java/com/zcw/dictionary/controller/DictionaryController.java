package com.zcw.dictionary.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DictionaryController {

	@GetMapping("/test")
	public String test() {
		return "helloworld";
	}
}
