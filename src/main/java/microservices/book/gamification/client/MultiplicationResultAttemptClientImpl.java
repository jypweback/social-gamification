package microservices.book.gamification.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import microservices.book.gamification.client.dto.MultiplicationResultAttempt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by qkrwpdud1@gmail.com on 2020/04/04
 * Github : http://github.com/jypweback
 * Description :
 */

@Component
public class MultiplicationResultAttemptClientImpl implements MultiplicationResultAttemptClient{

    private final RestTemplate restTemplate;
    private final String multiplicationHost;

    public MultiplicationResultAttemptClientImpl(RestTemplate restTemplate, @Value("${multiplicationHost}") final String multiplicationHost) {
        this.restTemplate = restTemplate;
        this.multiplicationHost = multiplicationHost;
    }

    @HystrixCommand(fallbackMethod = "defaultResult")
    @Override
    public MultiplicationResultAttempt retrieveMultiplicationResultAttemptById(final Long multiplicationResultAttemptId) {
        return restTemplate.getForObject(
                multiplicationHost + "/results/" + multiplicationResultAttemptId,
                    MultiplicationResultAttempt.class
        );
    }

    private MultiplicationResultAttempt defaultResult(final Long multiplicationResultAttemptId){
        return new MultiplicationResultAttempt("fakeAlias", 10, 10, 100, true);
    }
}
