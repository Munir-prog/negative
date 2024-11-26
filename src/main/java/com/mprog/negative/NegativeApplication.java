package com.mprog.negative;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@SpringBootApplication
@RequestMapping("/api/negative")
public class NegativeApplication {

//	@Value("${spring.cloud.discovery.instance-id}")
//	private String service;
//

	public static void main(String[] args) {
		SpringApplication.run(NegativeApplication.class, args);
	}

	record Negative(int id, int reliability, String text) {
	}

	@GetMapping
	public List<Negative> getNegatives() throws UnknownHostException {
		log.info("service: {} {}", InetAddress.getLocalHost().getHostName(), LocalDateTime.now());
		return List.of(new Negative(1, 20, "Bank robbery"), new Negative(2, 85, "Mall robbery"));
	}
}
