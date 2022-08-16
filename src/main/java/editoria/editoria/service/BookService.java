package editoria.editoria.service;

import editoria.editoria.domain.Book;
import editoria.editoria.dto.BookDto;
import editoria.editoria.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    //== 작품 저장 ==//
    @Transactional
    public void saveBook(BookDto bookDto, String writer, String category) {
        bookDto.setWriter(writer);
        bookDto.setCategory(category);
        bookRepository.save(bookDto.toEntity());
    }

    //== 작품 업데이트 ==//
    @Transactional
    public void updateBook(String category, BookDto bookDto, String writer, String title) {
        Book book = bookRepository.findOneByTitle(title);
        bookDto.setId(book.getId());
        bookDto.setCategory(category);
        bookDto.setWriter(writer);
        bookRepository.save(bookDto.toEntity());
    }

    //== 전체 작품 가져오기 ==//
    @Transactional(readOnly = true)
    public Page<Book> getBookList(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    //== 작품 제목으로 가져오기 - boardHome에서 쓰임 ==//
    @Transactional(readOnly = true)
    public Book getBookWithTitle(String title) {
        return bookRepository.findOneByTitle(title);
    }

    //== getBookListWithCategory ==//
    @Transactional(readOnly = true)
    public Page<Book> getBookListWithCategory(String category, Pageable pageable) {
        return bookRepository.findByCategory(category, pageable);
    }

    //== myPage 작가로 작품 가져오기 ==//
    @Transactional(readOnly = true)
    public Page<Book> getMyPage(String writer, Pageable pageable) {
        return bookRepository.findByWriter(writer, pageable);
    }

    //== 검색 + 페이징 ==//
    @Transactional(readOnly = true)
    public Page<Book> searchBook(String keyword, Pageable pageable) {
        return bookRepository.findByTitleContaining(keyword, pageable);
    }
}
