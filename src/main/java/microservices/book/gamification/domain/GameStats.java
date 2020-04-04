package microservices.book.gamification.domain;

/**
 * Created by qkrwpdud1@gmail.com on 2020/04/01
 * Github : http://github.com/jypweback
 * Description :
 */

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 한번 혹은 여러번의 게임 결과를 포함하는 객체
 * {@link ScoreCard} 객체와 {@link BadgeCard}로 이뤄짐
 *
 * 게임 한 번에 변경된 내용 또는 점수와 배지 전체를 나타낼 때 사용됨
 */

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class GameStats {

    private final Long userId;
    private final int score;
    private final List<Badge> badges;

    public GameStats(){
        this.userId = 0L;
        this.score = 0;
        this.badges = new ArrayList<>();
    }

    /**
     * 빈 인스턴스(0점과 배지 없는 상태)를 만들기 위한 팩토리 메서드
     * @param userId
     * @return
     */
    public static GameStats emptyStats(final Long userId) {
        return new GameStats(userId, 0, Collections.emptyList());
    }

    /**
     * @return 수정 불가능한 배지 카드 리스트의 뷰
     */
    public List<Badge> getBadges(){
        return Collections.unmodifiableList(badges);
    }
}
