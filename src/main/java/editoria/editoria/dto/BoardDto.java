package editoria.editoria.dto;

import editoria.editoria.domain.Board;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class BoardDto {

    private Long id;
    private String title;
    private String content;
    private String saveFileName;
    private String author;
    private int good;
    private String book;
    private LocalDateTime createdDate;

    public Board toEntity() {
        return Board.builder()
                .id(id)
                .title(title)
                .content(content)
                .saveFileName(saveFileName)
                .author(author)
                .good(good)
                .book(book)
                .build();
    }
}
