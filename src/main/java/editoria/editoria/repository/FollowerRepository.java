package editoria.editoria.repository;

import editoria.editoria.domain.Follower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowerRepository extends JpaRepository<Follower, Long> {

    //== 나로 찾기 ==//
    List<Follower> findByMe(String me);
}
