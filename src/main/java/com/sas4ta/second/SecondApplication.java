package com.sas4ta.second;

import com.sas4ta.second.data.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class SecondApplication {

	private final DataLoader dataLoader;

	public SecondApplication(DataLoader dataLoader) {
		this.dataLoader = dataLoader;
	}

	@EventListener(ContextRefreshedEvent.class)
	public void runAfterStartup() {
		dataLoader.loadChessMovesFromJson();
	}

	public static void main(String[] args) {
		SpringApplication.run(SecondApplication.class, args);
	}
}
