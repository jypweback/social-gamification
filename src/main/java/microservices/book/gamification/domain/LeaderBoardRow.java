package microservices.book.gamification.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Created by qkrwpdud1@gmail.com on 2020/04/02
 * Github : http://github.com/jypweback
 * Description :
 */

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class LeaderBoardRow {
    private final Long userId;
    private final Long totalScore;

    public LeaderBoardRow(){
        this(0L, 0L);
    }
}
