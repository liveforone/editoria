package editoria.editoria.controller;

import editoria.editoria.domain.Board;
import editoria.editoria.domain.Book;
import editoria.editoria.dto.BoardDto;
import editoria.editoria.service.BoardService;
import editoria.editoria.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final BookService bookService;

    //----------------페이징 위치 함수 시작----------------//
    //== nowPage 함수 ==//
    public int getNowPage(Page<Board> boardList) {
        return boardList.getPageable().getPageNumber() + 1;
    }

    //== startPage 함수 ==//
    public int getStartPage(int nowPage) {
        return Math.max(nowPage - 4, 1);
    }

    //== endPage 함수 ==//
    public int getEndPage(int nowPage, Page<Board> boardList) {
        return Math.min(nowPage + 5, boardList.getTotalPages());
    }
    //----------------페이징 위치 함수 종료----------------//

    //== 작품 속 게시글 ==//
    @GetMapping("/user/book/{title}")
    public String list(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable("title") String title,
            Principal principal,
            Model model
    ) {
        Page<Board> boardList = boardService.getBoardList(title, pageable);
        Book book = bookService.getBookWithTitle(title);
        String member = principal.getName();
        int nowPage = getNowPage(boardList);
        int startPage = getStartPage(nowPage);
        int endPage = getEndPage(nowPage, boardList);

        model.addAttribute("boardList", boardList);
        model.addAttribute("book", book);
        model.addAttribute("member", member);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "/board/boardHome";
    }

    @GetMapping("/user/board/post/{title}")
    public String postPage(@PathVariable("title") String title, Model model) {
        model.addAttribute("title", title);
        return "/board/boardPost";
    }

    //== 게시글 포스팅 ==//
    @PostMapping("/user/board/post/{title}")
    public String post(
            @RequestParam MultipartFile uploadFile,
            @PathVariable("title") String title,
            BoardDto boardDto,
            Principal principal
    ) throws IllegalStateException, IOException {
        String url = "/user/book/" + title;
        String author = principal.getName();

        if (!uploadFile.isEmpty()) {  //파일을 첨부하여 게시글을 업로드 한다면
            boardService.saveBoardWithFile(uploadFile, title, boardDto, author);
        } else {  //파일 없이 게시글을 업로드 한다면
            boardService.saveBoardWithNoFile(boardDto, author, title);
        }
        log.info("Posting Success!!");

        return "redirect:" + url;
    }

    //== 게시글 상세조회 - detail ==//
    @GetMapping("/user/board/{id}")
    public String detail(
            @PathVariable("id") Long id,
            Principal principal,
            Model model
    ) {
        String writer = principal.getName();
        Board board = boardService.getPost(id);

        model.addAttribute("post", board);
        model.addAttribute("writer", writer);
        return "/board/boardDetail";
    }

    //== 이미지 보여주기 - detail안에 이미지태그 ==//
    @GetMapping("/user/image/{saveFileName}")
    @ResponseBody
    public Resource showImage(
            @PathVariable("saveFileName") String saveFileName
    ) throws MalformedURLException {
        return new UrlResource("file:C:\\Temp\\upload\\" + saveFileName);
    }

    //== 좋아요 업데이트 ==//
    @PostMapping("/user/board/good/{id}")
    public String updateGood(@PathVariable("id") Long id) {
        String url = "/user/board/" + id;
        boardService.updateGood(id);
        log.info("Good update!!");
        return "redirect:" + url;
    }

    @GetMapping("/user/board/edit/{id}")
    public String editPage(
            @PathVariable("id") Long id,
            Model model
    ) {
        Board board = boardService.getPost(id);

        model.addAttribute("post", board);
        return "/board/boardEdit";
    }

    //== 수정 - edit ==//
    @PostMapping("/user/board/edit/{id}")
    public String edit(
            @RequestParam MultipartFile uploadFile,
            @PathVariable("id") Long id,
            @RequestParam(value = "saveFileName", required = false) String saveFileName,
            BoardDto boardDto
    ) throws IllegalStateException, IOException {
        String url = "/user/board/" + id;

        if (!uploadFile.isEmpty()) {  //파일을 바꿔서 수정
            boardService.updateBoardWithFile(id, uploadFile, boardDto);
        } else if(saveFileName.isEmpty()) {   //파일없이 수정
            /*
            null이 타임리프의 input에 들어가면 null이아니고 empty가된다.
             따라서 null 말고 empty로 체크해주어야함
             */
            boardService.updateBoardWithNoFile(id, boardDto);
        } else {  //기존파일을 유지하며 수정
            boardService.updateBoardWithSaveFileName(id, boardDto, saveFileName);
        }
        log.info("Edit Success!!");

        return "redirect:" + url;
    }

    //== 게시글 삭제 ==//
    @PostMapping("/user/board/delete/{id}")
    public String delete(
            @PathVariable("id") Long id,
            @RequestParam("bookTitle") String bookTitle
    ) {
        String url = "/user/book/" + bookTitle;
        boardService.deletePost(id);
        log.info("Delete Success!!");
        return "redirect:" + url;
    }
}
