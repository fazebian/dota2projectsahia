package xyz.sahia.org.dota2projectsahia;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;

@SpringBootApplication
public class Dota2projectsahiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(Dota2projectsahiaApplication.class, args);
	}
	@Bean
	ApplicationRunner applicationRunner(HeroService  heroService){
		return args -> heroService.all().forEach(System.out::println);
	}
	 record Hero(Integer id, String name) {}
	@Service
	static
	class HeroService {
		   private final JdbcTemplate template;
		   private final RowMapper<Hero> heroRowMapper = (rs, rowNum) ->
				   new Hero(rs.getInt("id"),
				   rs.getString("name"));

		   HeroService(JdbcTemplate template) {
			   this.template = template;
	 }

		  Collection<Hero> all() {
			   return this.template.query("select * from hero ",this.heroRowMapper);
	 }
		  Collection<Hero> byName(String name){
			   return this.template.query("select * from hero", this.heroRowMapper, name);
		  }
	}
}

