package editoria.editoria.dto;

import editoria.editoria.domain.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentDto {

    private Long id;
    private String user;
    private String content;
    private Long boardNum;
    private LocalDateTime createdDate;

    public Comment toEntity() {
        return Comment.builder()
                .id(id)
                .user(user)
                .content(content)
                .boardNum(boardNum)
                .build();
    }
}
