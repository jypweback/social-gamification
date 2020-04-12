package microservices.book.gamification.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by qkrwpdud1@gmail.com on 2020/04/12
 * Github : http://github.com/jypweback
 * Description :
 */

@Slf4j
@RestController
public class HealthCheckController {

    private final int serverPort;

    public HealthCheckController(@Value("${server.port}")int serverPort) {
        this.serverPort = serverPort;
    }

    @GetMapping("/health")
    public String getHealth(){
        log.info("health check server port : {}", serverPort);
        return "alive";
    }
}
