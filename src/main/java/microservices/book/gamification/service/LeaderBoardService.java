package microservices.book.gamification.service;

import microservices.book.gamification.domain.LeaderBoardRow;

import java.util.List;

/**
 * Created by qkrwpdud1@gmail.com on 2020/04/02
 * Github : http://github.com/jypweback
 * Description :
 */
public interface LeaderBoardService {

    /**
     * 최고 점수 사용자와 함께 현재 리더 보드를 검색
     * @return 최고 점수와 사용자
     */
    List<LeaderBoardRow> getCurrentLeaderBoard();
}
