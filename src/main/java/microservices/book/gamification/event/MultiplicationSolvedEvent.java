package microservices.book.gamification.event;

import lombok.*;

import java.io.Serializable;

/**
 * Created by qkrwpdud1@gmail.com on 2020/03/31
 * Github : http://github.com/jypweback
 * Description :
 */

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
class MultiplicationSolvedEvent implements Serializable {

    MultiplicationSolvedEvent(){
        multiplicationResultAttemptId = -1L;
        userId = -1L;
        correct = false;
    }

    private final Long multiplicationResultAttemptId;
    private final Long userId;
    private final boolean correct;

}
