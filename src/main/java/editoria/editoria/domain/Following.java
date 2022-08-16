package editoria.editoria.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Following {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //== 나 ==//
    @Column
    private String me;

    //== 내가 팔로잉 하는 사람 ==//
    private String following;

    @Builder
    public Following(Long id, String me, String following) {
        this.id = id;
        this.me = me;
        this.following = following;
    }
}
