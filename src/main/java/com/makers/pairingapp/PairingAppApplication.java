package com.makers.pairingapp;

import com.makers.pairingapp.dao.LanguageDAO;
import com.makers.pairingapp.model.Language;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class PairingAppApplication {

	@Autowired
	private LanguageDAO languageDAO;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(PairingAppApplication.class, args);
	}

	@Bean
	InitializingBean sendDatabase() {

		return () -> {
			languageDAO.save(new Language("java"));
			languageDAO.save(new Language("python"));
			languageDAO.save(new Language("javascript"));
			languageDAO.save(new Language("C#"));
			languageDAO.save(new Language("C"));
			languageDAO.save(new Language("PHP"));
			languageDAO.save(new Language("ruby"));
			languageDAO.save(new Language("swift"));
			languageDAO.save(new Language("go"));
		};
	}
}
