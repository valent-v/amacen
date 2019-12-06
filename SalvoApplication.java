package com.codeoftheweb.salvo;

import org.apache.catalina.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository, GamePlayerRepository gamePlayerRepository, ShipRepository shipRepository, ScoreRepository scoreRepository) {
		return (args) -> {
			// save a couple of customers
			Player Jack=playerRepository.save(new Player("jBauer", "j.bauer@ctu.gov", passwordEncoder().encode("24"), true));
			Player Chloe=playerRepository.save(new Player("Chloe", "c.obrian@ctu.gov", passwordEncoder().encode("42")));
			Player Kim=playerRepository.save(new Player("Kim", "kim_bauer@gmail.com", passwordEncoder().encode("kb")));
			Player David=playerRepository.save(new Player("David", "david_palmer@xd.com", passwordEncoder().encode("yolo"),true));

			Game gamefirst=gameRepository.save(new Game(LocalDateTime.now()));
			Game gamesecond=gameRepository.save(new Game(LocalDateTime.now().plusHours(1)));
			Game gamethird=gameRepository.save(new Game(LocalDateTime.now().plusHours(2)));
			Game gamefourth=gameRepository.save(new Game(LocalDateTime.now().plusHours(24)));


			GamePlayer gp1 =gamePlayerRepository.save(new GamePlayer(gamefirst, Jack));
			GamePlayer gp2=gamePlayerRepository.save(new GamePlayer (gamefirst, Chloe));
			GamePlayer gp3=gamePlayerRepository.save(new GamePlayer(gamesecond, Kim));
			GamePlayer gp4=gamePlayerRepository.save(new GamePlayer (gamesecond, Jack));
			GamePlayer gp5=gamePlayerRepository.save(new GamePlayer(gamethird, David));
			GamePlayer gp6=gamePlayerRepository.save(new GamePlayer(gamethird,Chloe));
			GamePlayer gp7=gamePlayerRepository.save(new GamePlayer(gamefourth, Chloe));

			Ship ship1=new Ship("submarine", Arrays.asList("A1","A2","A3"));
			Ship ship2=new Ship ("destroyer",Arrays.asList("C1","C3","C2","C4","C5"));
			Ship ship3=new Ship ("submarine",Arrays.asList("G5","H5","I5"));
			Ship ship4= new Ship ("patrol_boat", Arrays.asList("E3","E4"));
			Ship ship5= new Ship ("battleship", Arrays.asList("F1","F2","F3","F5"));

			Salvo salvo1=new Salvo("1", Arrays.asList("C5"));
			Salvo salvo2=new Salvo("2", Arrays.asList("J10"));
			Salvo salvo3=new Salvo("3", Arrays.asList("A1"));

			Salvo salvo4=new Salvo("1", Arrays.asList("A1"));
			Salvo salvo5=new Salvo("2", Arrays.asList("B1"));
			Salvo salvo6=new Salvo("3",Arrays.asList("A2"));
			Salvo salvo7=new Salvo ("4", Arrays.asList("G5"));

			Salvo salvo8=new Salvo ("1", Arrays.asList ("H6"));

			Score score1=scoreRepository.save(new Score (1.0, LocalDateTime.now(),Jack, gamefirst));
			Score score2=scoreRepository.save(new Score(0.0, LocalDateTime.now(), Chloe, gamefirst));
			Score score3=scoreRepository.save(new Score (0.5, LocalDateTime.now(),Kim, gamesecond));
			Score score4=scoreRepository.save(new Score(0.5,LocalDateTime.now(),Jack, gamesecond));
			Score score5=scoreRepository.save(new Score(1.0,LocalDateTime.now(),David, gamethird));
			Score score6=scoreRepository.save(new Score(0.0,LocalDateTime.now(), Chloe, gamethird));



			gp1.addShip(ship1);
			gp1.addShip(ship2);
			gp1.addShip(ship5);

			gp1.addSalvo(salvo1);
			gp1.addSalvo(salvo2);
			gp1.addSalvo(salvo3);
			gamePlayerRepository.save(gp1);

			gp2.addShip(ship3);
			gp2.addShip(ship4);

			gp2.addSalvo(salvo4);
			gp2.addSalvo(salvo5);
			gp2.addSalvo(salvo6);
			gp2.addSalvo(salvo7);
			gamePlayerRepository.save(gp2);

			gp3.addSalvo(salvo8);
			gamePlayerRepository.save(gp3);



		};
	}
}





