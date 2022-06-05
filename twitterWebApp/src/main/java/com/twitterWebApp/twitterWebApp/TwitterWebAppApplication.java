package com.twitterWebApp.twitterWebApp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication //scan all the compoments defined in package
@EnableReactiveMongoRepositories
public class TwitterWebAppApplication {


	public static void main(String[] args) {

		SpringApplication.run(TwitterWebAppApplication.class, args);
	}

}
