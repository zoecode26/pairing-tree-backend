package com.makers.pairingapp;

import com.makers.pairingapp.dao.LanguageDAO;
import com.makers.pairingapp.model.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
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
			String[] languages = new String[]{"java", "python", "ruby", "javascript", "C#", "C", "PHP", "swift", "go"};

			for (String language: languages) {
				if (languageDAO.findByName(language) == null) {
					languageDAO.save(new Language(language));
				}
			}
		};
	}



}

