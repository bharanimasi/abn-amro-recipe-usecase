package com.abn.amro.recipe;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@AutoConfiguration
@SpringBootApplication
@ComponentScan("com.abn.amro.recipe*")
public class AbnAmroReceipeCaseApplication {

	@Bean
	@Primary
	DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setScriptEncoding("UTF-8")
				.setName("abnamro-recipes").build();
	}

	public static void main(String[] args) {
		SpringApplication.run(AbnAmroReceipeCaseApplication.class, args);
	}

}
