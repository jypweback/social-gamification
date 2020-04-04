package microservices.book.gamification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservices.book.gamification.client.MultiplicationResultAttemptClient;
import microservices.book.gamification.client.dto.MultiplicationResultAttempt;
import microservices.book.gamification.domain.Badge;
import microservices.book.gamification.domain.BadgeCard;
import microservices.book.gamification.domain.GameStats;
import microservices.book.gamification.domain.ScoreCard;
import microservices.book.gamification.repository.BadgeCardRepository;
import microservices.book.gamification.repository.ScoreCardRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by qkrwpdud1@gmail.com on 2020/04/04
 * Github : http://github.com/jypweback
 * Description :
 */

@RequiredArgsConstructor
@Slf4j
@Service
public class GameServiceImpl implements GameService{

    private static final int LUCKY_NUMBER = 42;

    private final ScoreCardRepository scoreCardRepository;
    private final BadgeCardRepository badgeCardRepository;
    private MultiplicationResultAttemptClient attemptClient;

    @Override
    public GameStats newAttemptForUser(Long userId, Long attemptId, boolean correct) {
        // 처음엔 답이 맞았을 때만 점수를 줌
        if(correct){
            ScoreCard scoreCard = new ScoreCard(userId, attemptId);
            scoreCardRepository.save(scoreCard);
            log.info("사용자 ID {}, 점수 {} 점, 답안 ID {}", userId, scoreCard.getScore(), attemptId);
            List<BadgeCard> badgeCards = processForBadges(userId, attemptId);
            return new GameStats(userId, scoreCard.getScore(), badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));
        }

        return GameStats.emptyStats(userId);
    }

    /**
     * 조건이 충족될 경우 새 배지를 제공하기 위해 얻은 총점수와 점수 카드를 확인
     * @param userId
     * @param attemptId
     * @return
     */
    private List<BadgeCard> processForBadges(final Long userId, final Long attemptId){

        List<BadgeCard> badgeCards = new ArrayList<>();

        int totalScore = scoreCardRepository.getTotalScoreForUser(userId);
        log.info("사용자 ID {}의 새로운 점수 {}", userId, totalScore);

        List<ScoreCard> scoreCardList = scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId);
        List<BadgeCard> badgeCardList = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);

        // 점수기반 배지
        checkAndGiveBadgeBasedOnScore(
                badgeCardList,
                Badge.BRONZE_MULTIPLICATOR,
                totalScore,
                100,
                userId)
                .ifPresent(badgeCards::add);

        checkAndGiveBadgeBasedOnScore(
                badgeCardList,
                Badge.SILVER_MULTIPLICATOR,
                totalScore,
                500,
                userId
                )
                .ifPresent(badgeCards::add);

        checkAndGiveBadgeBasedOnScore(
                badgeCardList,
                Badge.GOLD_MULTIPLICATOR,
                totalScore,
                999,
                userId
                )
                .ifPresent(badgeCards::add);

        // 첫번째 정답 배지
        if(scoreCardList.size() == 1 && !containsBadge(badgeCardList, Badge.FIRST_WON)){
            BadgeCard firstWonBadge = giveBadgeToUser(Badge.FIRST_WON, userId);
            badgeCards.add(firstWonBadge);
        }

        // 행운의 숫자 배지
        MultiplicationResultAttempt attempt = attemptClient.retrieveMultiplicationResultAttemptById(attemptId);

        if(!containsBadge(badgeCardList, Badge.LUCKY_NUMBER) && (LUCKY_NUMBER == attempt.getMultiplicationFactorA() || LUCKY_NUMBER == attempt.getMultiplicationFactorB())){
            BadgeCard luckyNumberBadge = giveBadgeToUser(Badge.LUCKY_NUMBER, userId);
            badgeCards.add(luckyNumberBadge);
        }

        return badgeCards;
    }

    private Optional<BadgeCard> checkAndGiveBadgeBasedOnScore(final List<BadgeCard> badgeCards, final Badge badge, final int score, final int scoreThreshold, final Long userId){
        if(score >= scoreThreshold && !containsBadge(badgeCards, badge)){
            return Optional.of(giveBadgeToUser(badge, userId));
        }
        return Optional.empty();
    }

    /**
     * 배지 목록에 해당 배지가 포함돼 있는지 확인하는 메서드
     * @param badgeCards
     * @param badge
     * @return
     */
    private boolean containsBadge(final List<BadgeCard> badgeCards, final Badge badge){
        return badgeCards.stream().anyMatch(b -> b.getBadge().equals(badge));
    }

    /**
     * 주어진 사용자에게 새로운 배지를 부여하는 메서
     * @return
     */
    private BadgeCard giveBadgeToUser(final Badge badge, final Long userId){
        BadgeCard badgeCard = new BadgeCard(userId, badge);
        badgeCardRepository.save(badgeCard);
        log.info("사용자 ID {} 새로운 배지 획득 : {}", userId, badge);
        return badgeCard;
    }

    @Override
    public GameStats retrieveStatsForUser(final Long userId) {
        int score = scoreCardRepository
                .getTotalScoreForUser(userId);

        List<BadgeCard> badgeCards = badgeCardRepository
                .findByUserIdOrderByBadgeTimestampDesc(userId);

        return new GameStats(userId, score, badgeCards.stream()
                .map(BadgeCard::getBadge)
                .collect(Collectors.toList())
        );
    }

}
