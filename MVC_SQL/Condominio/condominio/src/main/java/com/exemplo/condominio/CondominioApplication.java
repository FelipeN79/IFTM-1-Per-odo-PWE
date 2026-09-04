package com.exemplo.condominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class CondominioApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CondominioApplication.class, args);
	}

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void run(String... args) throws Exception {

		 jdbcTemplate.execute("CREATE TABLE proprietario (id_proprietario SERIAL, nome VARCHAR(45), telefone VARCHAR(45))");
         jdbcTemplate.execute("CREATE TABLE apartamento (id_apartamento SERIAL, qtde_quartos INT, nro_porta INT, tipo VARCHAR(45), proprietario_id_proprietario INT)");

	}

}		
