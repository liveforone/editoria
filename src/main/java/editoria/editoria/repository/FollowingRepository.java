package editoria.editoria.repository;

import editoria.editoria.domain.Following;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowingRepository extends JpaRepository<Following, Long> {

    //== 나로 찾기 ==//
    List<Following> findByMe(String me);
}
