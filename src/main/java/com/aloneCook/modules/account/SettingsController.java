package com.aloneCook.modules.account;

import static com.aloneCook.modules.account.SettingsController.ROOT;
import static com.aloneCook.modules.account.SettingsController.SETTINGS;

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

import com.aloneCook.modules.account.form.PasswordForm;
import com.aloneCook.modules.account.form.Profile;
import com.aloneCook.modules.account.validate.NicknameValid;
import com.aloneCook.modules.account.validate.PasswordFormValid;

import lombok.RequiredArgsConstructor;

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
	@PostMapping(PROFILE)
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
	@PostMapping(PASSWORD)
	public String updatePassword(@CurrentUser Account account, @Valid PasswordForm passwordForm, Errors errors,
								Model model, RedirectAttributes attributes) {
		
		if (errors.hasErrors()) {
			model.addAttribute(account);
			return SETTINGS + PASSWORD;
		}
		
		String currentPassword = passwordForm.getCurrentPassword();
		if (!userService.isCurrentPasswordValid(account, currentPassword)) {
			model.addAttribute(account);
			errors.rejectValue("currentPassword", "wrong.value", "현재 비밀번호가 일치하지 않습니다.");
			return SETTINGS + PASSWORD;
		}
	
		userService.updatePassword(account, passwordForm.getNewPassword());
		attributes.addFlashAttribute("message", "패스워드를 변경했습니다.");
		return "redirect:/" + SETTINGS + PASSWORD;  
	}
	
	@InitBinder("passwordForm")
	public void passwordForminitBiner(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(new PasswordFormValid());
	}
	
	@GetMapping("/deleteAt")
	public String deleteIdForm(@CurrentUser Account account, Model model) {
		model.addAttribute(account);
		return SETTINGS + "/deleteAt";
	}
	
	@PostMapping("/deleteAt")
	public String deleteId(@CurrentUser Account account, RedirectAttributes attributes) {
		userService.deleteAt(account);
		attributes.addFlashAttribute("message", "회원탈퇴가 성공적으로 처리되었습니다.");
		return "redirect:/";
	}
}
