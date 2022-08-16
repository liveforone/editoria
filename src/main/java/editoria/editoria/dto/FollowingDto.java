package editoria.editoria.dto;

import editoria.editoria.domain.Following;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FollowingDto {

    private Long id;
    private String me;
    private String following;

    public Following toEntity() {
        return Following.builder()
                .id(id)
                .me(me)
                .following(following)
                .build();
    }
}
