package com.aloneCook.follow;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aloneCook.user.Account;
import com.aloneCook.user.CurrentUser;
import com.aloneCook.user.UserRepository;
import com.aloneCook.user.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FollowController {

	private final UserService userService;
	private final UserRepository userRepository;
	private final FollowService followService;
	
	@PostMapping("/profile/{nickname}/follow")
	@ResponseBody
	public ResponseEntity addFollow(@PathVariable String nickname, @CurrentUser Account account) {
		Account byNickname = userService.getAccount(nickname);
		followService.addFollow(byNickname, account);
		
		return ResponseEntity.ok().build();
	}
	@DeleteMapping("/profile/{nickname}/unfollow")
	@ResponseBody
	public ResponseEntity unfollow(@PathVariable String nickname, @CurrentUser Account account) {
		Account byNickname = userRepository.findAccountWithFollowingsBynickname(nickname);
		followService.unFollow(byNickname, account);
		return ResponseEntity.ok().build();
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
