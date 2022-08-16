package editoria.editoria.controller;

import editoria.editoria.domain.Board;
import editoria.editoria.domain.Book;
import editoria.editoria.dto.BookDto;
import editoria.editoria.service.BookService;
import editoria.editoria.service.FollowerService;
import editoria.editoria.service.FollowingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;
    private final FollowingService followingService;
    private final FollowerService followerService;

    //----------------페이징 위치 함수 시작----------------//
    //== nowPage 함수 ==//
    public int getNowPage(Page<Book> bookList) {
        return bookList.getPageable().getPageNumber() + 1;
    }

    //== startPage 함수 ==//
    public int getStartPage(int nowPage) {
        return Math.max(nowPage - 4, 1);
    }

    //== endPage 함수 ==//
    public int getEndPage(int nowPage, Page<Book> bookList) {
        return Math.min(nowPage + 5, bookList.getTotalPages());
    }
    //----------------페이징 위치 함수 종료----------------//

    //== book list - home ==//
    @GetMapping("/user/book")
    public String home(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            Model model
    ) {
        Page<Book> bookList = bookService.getBookList(pageable);
        int nowPage = getNowPage(bookList);
        int startPage = getStartPage(nowPage);
        int endPage = getEndPage(nowPage, bookList);

        model.addAttribute("bookList", bookList);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "/book/bookHome";
    }

    @GetMapping("/user/book/post")
    public String postPage() {
        return "/book/bookPost";
    }

    //== 작품 등록 ==//
    @PostMapping("/user/book/post")
    public String post(
            @RequestParam("category") String category,
            BookDto bookDto,
            Principal principal
    ) {
        String author = principal.getName();
        bookService.saveBook(bookDto, author, category);
        log.info("Book create Success!!");
        return "redirect:/user/book";
    }

    @GetMapping("/user/book/edit/{title}")
    public String editPage(
            @PathVariable("title") String title,
            Model model
    ) {
        Book book = bookService.getBookWithTitle(title);
        model.addAttribute("bookTitle", title);
        model.addAttribute("book", book);
        return "/book/bookEdit";
    }
     //== 작품 수정 ==//
    @PostMapping("/user/book/edit/{title}")
    public String editBook(
            @PathVariable("title") String title,
            @RequestParam("category") String category,
            BookDto bookDto,
            Principal principal
            ) {
        String writer = principal.getName();
        bookService.updateBook(category, bookDto, writer, title);
        return "redirect:/user/book";
    }

    //== category로 가져오기 ==//
    @GetMapping("/user/book/category/{category}")
    public String getCategory(
            @PathVariable("category") String category,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            Model model
    ) {
        Page<Book> bookList = bookService.getBookListWithCategory(category, pageable);
        int nowPage = getNowPage(bookList);
        int startPage = getStartPage(nowPage);
        int endPage = getEndPage(nowPage, bookList);

        model.addAttribute("category", category);
        model.addAttribute("bookList", bookList);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "/book/categoryPage";
    }

    //== myPage ==//
    @GetMapping("/user/myPage/{author}")
    public String myPage(
            @PathVariable("author") String author,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            Model model
    ) {
        Page<Book> myPage = bookService.getMyPage(author, pageable);
        int followingSize = followingService.getFollowingSize(author);  //팔로잉 수
        int followerSize = followerService.getFollowerSize(author);
        int nowPage = getNowPage(myPage);
        int startPage = getStartPage(nowPage);
        int endPage = getEndPage(nowPage, myPage);

        model.addAttribute("followingSize", followingSize);
        model.addAttribute("followerSize", followerSize);
        model.addAttribute("author", author);
        model.addAttribute("myPage", myPage);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "/book/writerPage";
    }

    //== 작품 검색 ==//
    @GetMapping("/user/book/search")
    public String search(
            @PageableDefault(page = 0, size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam("keyword") String keyword,
            Model model
    ) {
        Page<Book> searchList = bookService.searchBook(keyword, pageable);
        int nowPage = getNowPage(searchList);
        int startPage = getStartPage(nowPage);
        int endPage = getEndPage(nowPage, searchList);

        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("searchList", searchList);
        model.addAttribute("keyword", keyword);
        return "/book/bookSearch";
    }
}
