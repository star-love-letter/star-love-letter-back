package cn.conststar.wall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@EnableScheduling
@SpringBootApplication
public class StarLoveLetterBackApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(StarLoveLetterBackApplication.class, args);
    }

}
