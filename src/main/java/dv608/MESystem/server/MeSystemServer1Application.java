package dv608.MESystem.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class MeSystemServer1Application {

	public static void main(String[] args) {
		SpringApplication.run(MeSystemServer1Application.class, args);
	}
}
