package microservices.book.gamification.repository;

import microservices.book.gamification.domain.LeaderBoardRow;
import microservices.book.gamification.domain.ScoreCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by qkrwpdud1@gmail.com on 2020/04/02
 * Github : http://github.com/jypweback
 * Description :
 */

/**
 * Score Card 작업 처리
 */
public interface ScoreCardRepository extends JpaRepository<ScoreCard, Long> {

    /**
     * ScoreCard의 점수를 합해서 사용자의 총 점수를 조회
     * @param userId
     * @return
     */
    @Query("SELECT SUM(s.score) " +
            "FROM microservices.book.gamification.domain.ScoreCard s " +
            "WHERE s.userId = :userId group by s.userId")
    int getTotalScoreForUser(@Param("userId") final Long userId);

    /**
     * 사용자와 사용자의 총 점수를 나타내는 {@link LeaderBoardRow} 리스트를 조회
     * @return 높은 점수순으로 정렬된 리더보드
     */
    @Query("SELECT NEW microservices.book.gamification.domain.LeaderBoardRow( s.userId, SUM(s.score))" +
            "FROM microservices.book.gamification.domain.ScoreCard s " +
            "GROUP BY s.userId ORDER BY SUM(s.score) DESC"
    )
    List<LeaderBoardRow> findFirst10();

    /**
     * 사용자의 모든 ScoreCard를 조회
     * @param userId
     * @return
     */
    List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(final Long userId);


}
