package microservices.book.gamification.repository;

/**
 * Created by qkrwpdud1@gmail.com on 2020/04/02
 * Github : http://github.com/jypweback
 * Description :
 */

import microservices.book.gamification.domain.BadgeCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * BadgeCard 데이터 작업 처리
 */
public interface BadgeCardRepository extends JpaRepository<BadgeCard, Long> {

    /**
     * 주어진 사용자의 배지 카드를 모두 조회
     */
    List<BadgeCard> findByUserIdOrderByBadgeTimestampDesc(final Long userId);
}
