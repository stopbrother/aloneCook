package com.aloneCook.modules.follow;

import java.util.Collections;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aloneCook.modules.account.Account;
import com.aloneCook.modules.account.CurrentUser;
import com.aloneCook.modules.account.UserRepository;
import com.aloneCook.modules.account.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FollowController {

	private final UserService userService;
	private final UserRepository userRepository;
	private final FollowService followService;
	
	@PostMapping("/{nickname}/follow")
	@ResponseBody
	public ResponseEntity addFollow(@PathVariable String nickname, @CurrentUser Account account) {
		Account byNickname = userService.getAccount(nickname);
		followService.addFollow(byNickname, account);
		
		return ResponseEntity.ok("팔로우 성공");
	}
	@DeleteMapping("/{nickname}/unfollow")
	@ResponseBody
	public ResponseEntity unfollow(@PathVariable String nickname, @CurrentUser Account account) {
		Account byNickname = userRepository.findAccountWithFollowingsBynickname(nickname);
		followService.unFollow(byNickname, account);
		return ResponseEntity.ok("팔로우취소 성공");
	}
	
	@GetMapping("/{nickname}/status")
	@ResponseBody
	public ResponseEntity getFollowStatus(@PathVariable String nickname, @CurrentUser Account account) {
		boolean isFollow = followService.isFollow(account, nickname);
		return ResponseEntity.ok(Collections.singletonMap("isFollow", isFollow));
	}
	
	/*
	@PostMapping("/profile/{nickname}/follow")
	public String addFollow(@PathVariable String nickname, @CurrentUser Account account) {
		Account byNickname = userService.getAccount(nickname);
		//Account byNickname = userRepository.findAccountWithFollowingsBynickname(nickname);
		userService.addFollow(byNickname, account);
		return "redirect:/profile/" + nickname;
	}

	@PostMapping("/profile/{nickname}/unfollow")
	public String unFollow(@PathVariable String nickname, @CurrentUser Account account) {
		Account byNickname = userRepository.findAccountWithFollowingsBynickname(nickname);
		userService.unFollow(byNickname, account);
		return "redirect:/profile/" + nickname;
	}
	*/
}
