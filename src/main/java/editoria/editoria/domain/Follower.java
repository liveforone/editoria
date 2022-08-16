package editoria.editoria.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Follower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String me;

    @Column
    private String follower;

    @Builder
    public Follower(Long id, String me, String follower) {
        this.id = id;
        this.me = me;
        this.follower = follower;
    }
}
