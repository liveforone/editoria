package editoria.editoria.repository;

import editoria.editoria.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    //== 제목으로 카테고리 찾기 ==//
    Book findOneByTitle(String title);

    //== by뒤의 like를 찾아줌 - 검색쿼리 ==//
    Page<Book> findByTitleContaining(String keyword, Pageable pageable);

    //== category별 분류 - 카테고리로 가져오기 ==//
    Page<Book> findByCategory(String category, Pageable pageable);

    //== myPage에서 사용할 유저를 기준으로 카테고리(강좌) 가져오기 ==//
    Page<Book> findByWriter(String writer, Pageable pageable);
}
