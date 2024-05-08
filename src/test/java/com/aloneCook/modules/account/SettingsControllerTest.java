package com.aloneCook.modules.account;

import static com.aloneCook.modules.account.SettingsController.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.aloneCook.infra.MockMvcTest;
import com.aloneCook.modules.account.Account;
import com.aloneCook.modules.account.UserRepository;
import com.aloneCook.modules.account.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@MockMvcTest //컨트롤러테스트
class SettingsControllerTest {

	@Autowired MockMvc mockMvc;
	@Autowired UserRepository userRepository;
	@Autowired UserService userService;
	@Autowired PasswordEncoder passwordEncoder;
	@Autowired ObjectMapper objectMapper;
	
	/*
	@BeforeEach
	void beforeEach() {
		
	}*/
	
	@AfterEach
	void afterEach() {
		userRepository.deleteAll();
	}
	
	@WithUser("test")
	@DisplayName("프로필 수정 폼")
	@Test
	void updateProfileForm() throws Exception {
		mockMvc.perform(get(ROOT + SETTINGS + PROFILE))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("account"))
				.andExpect(model().attributeExists("profile"));
	}
	
	@WithUser("test")
	@DisplayName("닉네임 수정 - 정상")
	@Test
	void updateNickname_success() throws Exception {
		String updateNickname = "eclipse";
		mockMvc.perform(post(ROOT + SETTINGS + PROFILE)
				.param("nickname", updateNickname)
				.with(csrf()))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl(ROOT + SETTINGS + PROFILE))
		.andExpect(flash().attributeExists("message"));
		
		assertNotNull(userRepository.findByNickname("eclipse"));
	}
	
	@WithUser("test")
	@DisplayName("닉네임 수정 - 에러")
	@Test
	void updateNickname_fail() throws Exception {
		String updateNickname = "?!_=";
		mockMvc.perform(post(ROOT + SETTINGS + PROFILE)
				.param("nickname", updateNickname)
				.with(csrf()))
		.andExpect(status().isOk())
		.andExpect(view().name(SETTINGS + PROFILE))
		.andExpect(model().hasErrors())
		.andExpect(model().attributeExists("account"))
		.andExpect(model().attributeExists("profile"));
	}
	
	
	/*
	@WithUser("test")
	@DisplayName("프로필 수정 - 정상")
	@Test
	void updateProfile() throws Exception {
		String intro = "안녕하세요.";
		mockMvc.perform(post(ROOT + SETTINGS + PROFILE)
				.param("intro", intro)
				.with(csrf()))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl(ROOT + SETTINGS + PROFILE))
		.andExpect(flash().attributeExists("message"));
		
		Account test = userRepository.findByNickname("test");
		assertEquals(intro, test.getIntro());
	}
	
	*/
	
	@WithUser("test")
	@DisplayName("패스워드 수정 폼")
	@Test
	void updatePassword_Form() throws Exception {
		mockMvc.perform(get(ROOT + SETTINGS + PASSWORD))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("account"))
				.andExpect(model().attributeExists("passwordForm"));
	}
	
	@WithUser("test")
	@DisplayName("패스워드 수정 - 정상")
	@Test
	void updatePassword_success() throws Exception {
		mockMvc.perform(post(ROOT + SETTINGS + PASSWORD)
				.param("currentPassword", "12345678")
				.param("newPassword", "87654321")
				.param("newPasswordCheck", "87654321")
				.with(csrf()))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl(ROOT + SETTINGS + PASSWORD))
		.andExpect(flash().attributeExists("message"));
	}
	
	@WithUser("test")
	@DisplayName("패스워드 수정 - 에러 - 새비밀번호 불일치")
	@Test
	void updatePassword_fail() throws Exception {
		mockMvc.perform(post(ROOT + SETTINGS + PASSWORD)
				.param("currentPassword", "12345678")
				.param("newPassword", "87654321")
				.param("newPasswordCheck", "00700123")
				.with(csrf()))
		.andExpect(status().isOk())
		.andExpect(view().name(SETTINGS + PASSWORD))
		.andExpect(model().hasErrors())
		.andExpect(model().attributeExists("passwordForm"))
		.andExpect(model().attributeExists("account"));
	}
}
