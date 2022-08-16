package editoria.editoria.controller;

import editoria.editoria.domain.Board;
import editoria.editoria.domain.Comment;
import editoria.editoria.dto.CommentDto;
import editoria.editoria.service.BoardService;
import editoria.editoria.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final BoardService boardService;

    //== 댓글 리스트 ==//
    @GetMapping("/user/comment/{id}")
    public String commentList(
            @PathVariable("id") Long id,
            Model model,
            Principal principal
    ) {
        String member = principal.getName();
        List<Comment> commentList = commentService.getCommentList(id);
        Board board = boardService.getPost(id);
        model.addAttribute("commentList", commentList);
        model.addAttribute("board", board);
        model.addAttribute("member", member);
        return "/comment/commentList";
    }

    //== 댓글 작성 ==//
    @PostMapping("/user/comment/post/{id}")
    public String commentPost(
            @PathVariable("id") Long id,
            CommentDto commentDto,
            Principal principal
    ) {
        String url = "/user/comment/" + id;
        String user = principal.getName();
        commentService.saveComment(commentDto, user, id);
        log.info("Comment Posting Success!!");
        return "redirect:" + url;
    }

    //== 댓글 삭제 ==//
    @PostMapping("/user/comment/delete/{comment}")
    public String deleteComment(
            @PathVariable("comment") Long comment,
            @RequestParam("boardId") Long boardId
    ) {
        String url = "/user/comment/" + boardId;
        commentService.deleteComment(comment);
        log.info("Comment Deletion Successful!!");
        return "redirect:" + url;
    }
}
