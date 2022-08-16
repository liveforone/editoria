package editoria.editoria.dto;

import editoria.editoria.domain.Book;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDto {

    private Long id;
    private String title;
    private String writer;
    private String category;

    public Book toEntity() {
        return Book.builder()
                .id(id)
                .title(title)
                .writer(writer)
                .category(category)
                .build();
    }
}
