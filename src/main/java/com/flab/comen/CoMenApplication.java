package com.flab.comen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackageClasses = CoMenApplication.class)
public class CoMenApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoMenApplication.class, args);
	}

}
