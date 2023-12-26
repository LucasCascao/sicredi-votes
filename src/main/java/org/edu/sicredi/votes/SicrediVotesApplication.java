package org.edu.sicredi.votes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class SicrediVotesApplication {

  public static void main(String[] args) {
    SpringApplication.run(SicrediVotesApplication.class, args);
  }

}
