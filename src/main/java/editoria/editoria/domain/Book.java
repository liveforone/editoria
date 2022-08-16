package editoria.editoria.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String writer;

    @Column
    private String category;

    @Builder
    public Book(Long id, String title, String writer, String category) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.category = category;
    }
}
