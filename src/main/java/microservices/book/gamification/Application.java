package microservices.book.gamification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by qkrwpdud1@gmail.com on 2020/04/01
 * Github : http://github.com/jypweback
 * Description :
 */

@EnableEurekaClient
@EnableCircuitBreaker
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
