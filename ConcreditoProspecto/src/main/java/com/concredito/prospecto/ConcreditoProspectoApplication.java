package com.concredito.prospecto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConcreditoProspectoApplication implements CommandLineRunner {
	
	private static final Logger LOG = LoggerFactory.getLogger(ConcreditoProspectoApplication.class);
	
	@Value("${spring.profiles.active}")
	private String profile;

	public static void main(String[] args) {
		SpringApplication.run(ConcreditoProspectoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOG.info("========================");
		LOG.info("=========== profile: " + profile);
		LOG.info("========================");		
	}

}
