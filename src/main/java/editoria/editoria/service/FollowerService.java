package editoria.editoria.service;

import editoria.editoria.domain.Follower;
import editoria.editoria.dto.FollowerDto;
import editoria.editoria.repository.FollowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowerService {

    private final FollowerRepository followerRepository;

    //== 팔로워 수 ==//
    @Transactional(readOnly = true)
    public int getFollowerSize(String me) {
        List<Follower> followerList = followerRepository.findByMe(me);
        return followerList.size();
    }

    //== 팔로워 리스트 ==//
    @Transactional(readOnly = true)
    public List<Follower> getFollowerList(String me) {
        return followerRepository.findByMe(me);
    }

    //== 팔로워 저장 ==//
    @Transactional
    public void saveFollower(FollowerDto followerDto, String me, String follower) {
        followerDto.setMe(me);
        followerDto.setFollower(follower);
        followerRepository.save(followerDto.toEntity());
    }
}
