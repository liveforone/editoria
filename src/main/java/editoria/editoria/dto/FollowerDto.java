package editoria.editoria.dto;

import editoria.editoria.domain.Follower;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FollowerDto {

    private Long id;
    private String me;
    private String follower;

    public Follower toEntity() {
        return Follower.builder()
                .id(id)
                .me(me)
                .follower(follower)
                .build();
    }
}
