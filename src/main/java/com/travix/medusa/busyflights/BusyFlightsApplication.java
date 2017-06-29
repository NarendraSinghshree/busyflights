package com.travix.medusa.busyflights;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.travix.medusa.busyflights.domain.controller,"
		+ "com.travix.medusa.busyflights.service,"
		+ "com.travix.medusa.busyflights.httpclient"})
public class BusyFlightsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusyFlightsApplication.class, args);
	}
}
