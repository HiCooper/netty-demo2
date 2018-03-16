package com.rongyu.fep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author berry
 */
@EnableEurekaClient
@SpringBootApplication
public class FepApplication {

	public static void main(String[] args) {
		SpringApplication.run(FepApplication.class, args);
	}
}
