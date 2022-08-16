package editoria.editoria.controller;

import editoria.editoria.domain.Follower;
import editoria.editoria.domain.Following;
import editoria.editoria.dto.FollowerDto;
import editoria.editoria.dto.FollowingDto;
import editoria.editoria.service.FollowerService;
import editoria.editoria.service.FollowingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FollowController {  //Following 엔티티와 Follower 엔티티를 담당한다.

    private final FollowingService followingService;
    private final FollowerService followerService;

    //== 팔로잉 리스트 ==//
    @GetMapping("/user/following/{author}")
    public String followingList(
            @PathVariable("author") String author,
            Model model
    ) {
        List<Following> followingList = followingService.getFollowingList(author);

        model.addAttribute("followingList", followingList);
        model.addAttribute("writer", author);
        return "/book/followingPage";
    }

    //== 팔로워 리스트 ==//
    @GetMapping("/user/follower/{author}")
    public String followerList(
            @PathVariable("author") String author,
            Model model
    ) {
        List<Follower> followerList = followerService.getFollowerList(author);

        model.addAttribute("followerList", followerList);
        model.addAttribute("writer", author);
        return "/book/followerPage";
    }

    //== 팔로잉하기 + 팔로워 추가 ==//
    @PostMapping("/user/myPage/following/{author}")
    public String subscribe(
            @PathVariable("author") String author,
            Principal principal,
            FollowingDto followingDto,
            FollowerDto followerDto
    ) {
        String url = "/user/myPage/" + author;
        String me = principal.getName();
        followingService.saveFollowing(followingDto, me, author);
        followerService.saveFollower(followerDto, author, me);

        log.info("Following Success!!");
        return "redirect:" + url;
    }
}
