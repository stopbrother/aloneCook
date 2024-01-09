package com.aloneCook.user;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aloneCook.follow.Follow;
import com.aloneCook.follow.FollowRepository;
import com.aloneCook.follow.FollowService;
import com.aloneCook.like.LikeRepository;
import com.aloneCook.recipe.RecipeRepository;
import com.aloneCook.user.form.JoinForm;
import com.aloneCook.user.validate.JoinFormValid;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final JoinFormValid joinFormValid;
	private final UserRepository userRepository;
	private final RecipeRepository recipeRepository;
	private final LikeRepository likeRepository;
	private final FollowRepository followRepository;
	private final FollowService followService;
	
	
	@GetMapping("/join")
	public String joinForm(Model model) {
		model.addAttribute("joinForm", new JoinForm());
		return "user/join";
	}
	
	@PostMapping("/join")
	public String joinSubmit(@Valid JoinForm joinForm, Errors errors) {
		if (errors.hasErrors()) { //에러가 있을경우
			return "user/join";
		} //에러가 없을경우
	
		Account account = userService.saveNewUser(joinForm);
		userService.login(account);
		
		
		
		return "redirect:/";
	}
	
	@InitBinder("joinForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(joinFormValid);
	}
	
	/* 비밀번호 찾기
	@GetMapping("/lost-password")
	public String lostForm() {
		return "user/lost-password";
	}
	@PostMapping("/lost-password")
	public String sendEmailLink(String email, Model model, RedirectAttributes attributes) {
		UserDTO userDTO = userRepository.findByEmail(email);
		if (userDTO == null) {
			model.addAttribute("error", "가입되어 있지 않은 이메일입니다.");
			return "user/lost-password";
		}
		userService.lostSendLink(userDTO);
		attributes.addFlashAttribute("message", "비밀번호 변경 메일을 발송했습니다. 메일을 확인해주세요.");
		return "redirect:/lost-password";
	}
	*/
	
	
	@GetMapping("/profile/{nickname}")
	public String profileView(@PathVariable String nickname, Model model, @CurrentUser Account account) {
		if (account != null) {
			model.addAttribute(account);
			Account byNickname = userService.getAccount(nickname);			
			boolean isFollow = followRepository.existsByToUserAndFromUser(byNickname, account);
			
			model.addAttribute("byNickname", byNickname);	
			model.addAttribute("isOwner", byNickname.equals(account));
			model.addAttribute("isFollow", isFollow);
			model.addAttribute("followingCnt", followRepository.countByFromUser(byNickname));
			model.addAttribute("followerCnt", followRepository.countByToUser(byNickname));
			model.addAttribute("recipeCnt", recipeRepository.countByManagerContaining(byNickname));
			
		}
		model.addAttribute(nickname);
		return "user/profile";
	}

	@GetMapping("/profile/{nickname}/recipe")
	public String recipeCreated(@PathVariable String nickname, Model model, @CurrentUser Account account) {
		if (account != null) {			
			Account byNickname = userService.getAccount(nickname);
			boolean follow = followRepository.existsByToUserAndFromUser(byNickname, account);
			
			model.addAttribute(account);
			model.addAttribute("byNickname", byNickname);
			model.addAttribute("isOwner", byNickname.equals(account));
			model.addAttribute("isFollow", follow);
			model.addAttribute("followingCnt", followRepository.countByFromUser(byNickname));
			model.addAttribute("followerCnt", followRepository.countByToUser(byNickname));
			model.addAttribute("recipeCnt", recipeRepository.countByManagerContaining(byNickname));
			model.addAttribute("recipeList", recipeRepository.findByManagerContainingAndDraftedOrderByPublishedDateTimeDesc(byNickname, false));
		}		
		return "user/recipe";
	}
	
	@GetMapping("/profile/{nickname}/follower")
	public String followerView(@PathVariable String nickname, Model model, @CurrentUser Account account) {
		if (account != null) {			
			Account byNickname = userService.getAccount(nickname);
			boolean follow = followRepository.existsByToUserAndFromUser(byNickname, account);
			List<Follow> userList = followRepository.findAllByToUser(byNickname);
			List<Follow> myList	= followRepository.findAllByFromUser(account);
			
			
			
			model.addAttribute(account);
			model.addAttribute("byNickname", byNickname);
			model.addAttribute("isOwner", byNickname.equals(account));
			model.addAttribute("isFollow", follow);
			model.addAttribute("followingCnt", followRepository.countByFromUser(byNickname));
			model.addAttribute("followerCnt", followRepository.countByToUser(byNickname));
			model.addAttribute("followerList", followRepository.findAllByToUser(byNickname));
			model.addAttribute("recipeCnt", recipeRepository.countByManagerContaining(byNickname));
			model.addAttribute("myList", followRepository.findAllByFromUser(account));
			
		}		
		return "user/follower";
	}
	
	@GetMapping("/profile/{nickname}/following")
	public String followingView(@PathVariable String nickname, Model model, @CurrentUser Account account) {
		if (account != null) {			
			Account byNickname = userService.getAccount(nickname);
			boolean follow = followRepository.existsByToUserAndFromUser(byNickname, account);
			//List<Follow> myList = followRepository.findAllByFromUser(account); 
			
			model.addAttribute(account);
			model.addAttribute("byNickname", byNickname);		
			model.addAttribute("isOwner", byNickname.equals(account));
			model.addAttribute("isFollow", follow);
			model.addAttribute("followingCnt", followRepository.countByFromUser(byNickname));
			model.addAttribute("followerCnt", followRepository.countByToUser(byNickname));
			model.addAttribute("followingList", followRepository.findAllByFromUser(byNickname));
			model.addAttribute("recipeCnt", recipeRepository.countByManagerContaining(byNickname));
			model.addAttribute("myList", followRepository.findAllByFromUser(account));
		}	
		return "user/following";
	}

	
	@GetMapping("/my-recipe")
	public String myRecipeView(@CurrentUser Account account, Model model) {
		if (account != null) {
			model.addAttribute(account);
			model.addAttribute("recipeManager",
					recipeRepository.findByManagerContainingAndDraftedOrderByPublishedDateTimeDesc(account, false));
			return "recipe/my/recipe";
		}
		model.addAttribute("message", "잘못된 경로입니다.");
		return "index";
	}
	@GetMapping("/my-recipe/draft")
	public String viewDraftRecipe(@CurrentUser Account account, Model model) {
		if (account != null) {
			model.addAttribute(account);
			model.addAttribute("draftList", recipeRepository.findByWriterAndDraftedOrderByCreatedDateTimeDesc(account, true));															 
			return "recipe/my/draft";
		}
		model.addAttribute("message", "잘못된 경로 입니다.");
		return "index";
	}
	
	@GetMapping("/recipe/liked")
	public String myLiked(@CurrentUser Account account, Model model) {
		if (account != null) {
			model.addAttribute(account);
			model.addAttribute("recipeLiked", likeRepository.findByAccountAndLiked(account, true));
			return "recipe/my/liked";
		}		
		return "error";
	}
}
