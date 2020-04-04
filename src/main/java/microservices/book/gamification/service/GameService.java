package microservices.book.gamification.service;

import microservices.book.gamification.domain.GameStats;

/**
 * Created by qkrwpdud1@gmail.com on 2020/04/02
 * Github : http://github.com/jypweback
 * Description :
 */
public interface GameService {

    /**
     * 주어진 사용자가 제출한 답안을 처리
     * @param userId
     * @param attemptId
     * @param correct
     * @return
     */
    GameStats newAttemptForUser(Long userId, Long attemptId, boolean correct);

    /**
     * 주어진 사용자의 게임 통계를 조회
     * @param userId
     * @return
     */
    GameStats retrieveStatsForUser(final Long userId);
}
