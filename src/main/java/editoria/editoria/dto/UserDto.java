package editoria.editoria.dto;

import editoria.editoria.domain.Role;
import editoria.editoria.domain.Users;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String email;
    private String password;
    private Role auth;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    //==dto -> entity==//
    public Users toEntity() {
        return Users.builder()
                .id(id)
                .email(email)
                .password(password)
                .auth(auth)
                .build();
    }

    @Builder
    public UserDto(Long id, String email, String password, Role auth) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.auth = auth;
    }
}
