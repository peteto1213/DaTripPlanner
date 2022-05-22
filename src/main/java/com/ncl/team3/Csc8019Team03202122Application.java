package com.ncl.team3;

import com.ncl.team3.config.MyContextListener;
import com.ncl.team3.config.MyStartConfig;
import com.ncl.team3.config.SSHConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletContextEvent;

@SpringBootApplication
public class Csc8019Team03202122Application {

	public static void main(String[] args) throws Throwable {
		//MyStartConfig start = new MyStartConfig();
		//start.contextInitialized();
		SpringApplication.run(Csc8019Team03202122Application.class, args);
		//System.out.println("Context destroyed ... !");
		//start.contextDestroyed(); // disconnect
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
