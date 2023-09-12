package com.aloneCook.main;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.aloneCook.user.UserRepository;
import com.aloneCook.user.UserService;
import com.aloneCook.user.form.JoinForm;

@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {

	@Autowired MockMvc mockMvc;
	@Autowired UserService userService;
	@Autowired UserRepository userRepository;
	
	@BeforeEach
	void beforeEach() {
		JoinForm joinForm = new JoinForm();
		joinForm.setNickname("test");
		joinForm.setEmail("test@naver.com");
		joinForm.setPassword("12345678");
		userService.saveNewUser(joinForm);
	}
	@AfterEach
	void afterEach() {
		userRepository.deleteAll();
	}
	
	@DisplayName("로그인 성공")
	@Test
	void login_success() throws Exception {			
		mockMvc.perform(post("/login")
				.param("username", "test@naver.com")
				.param("password", "12345678")
				.with(csrf()))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/"))
		.andExpect(authenticated().withUsername("test"));
	}
	
	@DisplayName("로그인 실패")
	@Test
	void login_fail() throws Exception {
		mockMvc.perform(post("/login")
				.param("username", "1111")
				.param("password", "12345678")
				.with(csrf()))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/login?error"))
		.andExpect(unauthenticated());
	}
	
}
