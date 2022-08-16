package editoria.editoria.service;

import editoria.editoria.domain.Following;
import editoria.editoria.dto.FollowingDto;
import editoria.editoria.repository.FollowingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowingService {

    private final FollowingRepository followingRepository;

    //== 팔로잉 수 ==//
    @Transactional(readOnly = true)
    public int getFollowingSize(String me) {
        List<Following> followingList = followingRepository.findByMe(me);
        return followingList.size();
    }

    //== 팔로잉 리스트 ==//
    @Transactional(readOnly = true)
    public List<Following> getFollowingList(String me) {
        return followingRepository.findByMe(me);
    }

    //== 팔로잉 ==//
    @Transactional
    public void saveFollowing(FollowingDto followingDto, String me, String following) {
        followingDto.setMe(me);
        followingDto.setFollowing(following);
        followingRepository.save(followingDto.toEntity());
    }
}
