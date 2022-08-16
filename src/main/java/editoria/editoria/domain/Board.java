package editoria.editoria.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column
    private String saveFileName;

    @Column
    private String author;

    @Column(columnDefinition = "integer default 0")
    private int good;  //좋아요

    @Column
    private String book;  //부모인 작품

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @Builder
    public Board(
            Long id,
            String title,
            String content,
            String saveFileName,
            String author,
            int good,
            String book
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.saveFileName = saveFileName;
        this.author = author;
        this.good = good;
        this.book = book;
    }
}
