package com.aloneCook.user;

import static com.aloneCook.modules.account.SettingsController.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.aloneCook.modules.account.Account;
import com.aloneCook.modules.account.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
class SettingsControllerTest {

	@Autowired MockMvc mockMvc;
	@Autowired UserRepository userRepository;
	
	@AfterEach
	void afterEach() {
		userRepository.deleteAll();
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
}
