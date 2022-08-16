package editoria.editoria.service;

import editoria.editoria.domain.Comment;
import editoria.editoria.dto.CommentDto;
import editoria.editoria.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    //== 댓글 리스트 ==//
    @Transactional(readOnly = true)
    public List<Comment> getCommentList(Long boardNum) {
        return commentRepository.findByBoardNum(boardNum);
    }

    //== 댓글 등록 ==//
    @Transactional
    public void saveComment(CommentDto commentDto, String user, Long boardNum) {
        commentDto.setId(null);
        /*
        null을 넣어준다.
        save되어서 쿼리가 나갈때 jpa는 id값이 없어서 새로운 객체로 인식하고
        업데이트 되지 않고 저장된다.
         */
        commentDto.setUser(user);
        commentDto.setBoardNum(boardNum);
        commentRepository.save(commentDto.toEntity());
    }

    //== 댓글 삭제 ==//
    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
