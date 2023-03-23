package server.database;

import commons.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    //@Query("SELECT BOARD.board_id FROM CARD JOIN TDLIST ON CARD.LIST_ID=TDLIST.LIST_ID " +
    //        "JOIN BOARD ON BOARD.BOARD_ID=TDLIST.BOARD_ID WHERE CARD.ID = :cardID")
    //Long findCorrespondingBoardID(@Param("cardID") Long cardId);
}
