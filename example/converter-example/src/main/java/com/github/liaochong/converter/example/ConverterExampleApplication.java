package com.github.liaochong.converter.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.liaochong.converter.annoation.EnableConverter;

@SpringBootApplication
@EnableConverter
public class ConverterExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConverterExampleApplication.class, args);
	}
}
