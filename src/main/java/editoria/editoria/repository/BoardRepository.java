package editoria.editoria.repository;

import editoria.editoria.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {

    //== 좋아요 업데이트 ==//
    @Modifying
    @Query("update Board b set b.good = b.good + 1 where b.id = :id")
    int updateGood(@Param("id") Long id);

    //== 작품별로 찾기 + 페이징 ==//
    Page<Board> findByBook(String book, Pageable pageable);

    //== 게시글 한 개 가져오기 - detail ==//
    Board findOneById(Long id);  //findById는 옵셔널타입이라 형변환을 했어야해서 새로 작성함.
}
