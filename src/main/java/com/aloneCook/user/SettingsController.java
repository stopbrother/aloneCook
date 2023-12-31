package com.aloneCook.user;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aloneCook.user.form.PasswordForm;
import com.aloneCook.user.form.Profile;
import com.aloneCook.user.validate.NicknameValid;
import com.aloneCook.user.validate.PasswordFormValid;

import lombok.RequiredArgsConstructor;

import static com.aloneCook.user.SettingsController.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(ROOT + SETTINGS)
public class SettingsController {

	static final String ROOT = "/";
	static final String SETTINGS = "settings";
	static final String PROFILE = "/profile";
	static final String PASSWORD = "/password";
	
	private final UserService userService;
	private final NicknameValid nicknameValid;
	private final ModelMapper modelMapper;
	
	@GetMapping(PROFILE)
	public String settingsForm(@CurrentUser Account account, Model model) {
		model.addAttribute(account);
		model.addAttribute(modelMapper.map(account, Profile.class));
		return SETTINGS + PROFILE;
	}
	@PostMapping(ROOT + SETTINGS + PROFILE)
	public String updateProfile(@CurrentUser Account account, @Valid Profile profile,
								Errors errors, Model model, RedirectAttributes attributes) {
		if (errors.hasErrors()) {
			model.addAttribute(account);
			return SETTINGS + PROFILE;
		}
		
		userService.updateProfile(account, profile);
		userService.updateNickname(account, profile.getNickname());
		attributes.addFlashAttribute("message", "프로필을 수정했습니다.");
		
		return "redirect:/" + SETTINGS + PROFILE; 
	}
	
	@InitBinder("profile.getNickname()")
	public void nicknameInitBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(nicknameValid);
	}
	
	@GetMapping(PASSWORD)
	public String updatePasswordForm(@CurrentUser Account account, Model model) {
		model.addAttribute(account);
		model.addAttribute(new PasswordForm());
		return SETTINGS + PASSWORD;
	}
	@PostMapping(ROOT + SETTINGS + PASSWORD)
	public String updatePassword(@CurrentUser Account account, @Valid PasswordForm passwordForm,Errors errors,
								Model model, RedirectAttributes attributes) {
		
		if (errors.hasErrors()) {
			model.addAttribute(account);
			return SETTINGS + PASSWORD;
		}
		userService.updatePassword(account, passwordForm.getNewPassword());
		attributes.addFlashAttribute("message", "패스워드를 변경했습니다.");
		return "redirect:/" + SETTINGS + PASSWORD;  
	}
	
	@InitBinder("passwordForm")
	public void initBiner(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(new PasswordFormValid());
	}
	
}
