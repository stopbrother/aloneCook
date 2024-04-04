package com.aloneCook.user;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.aloneCook.modules.account.Account;
import com.aloneCook.modules.account.UserRepository;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired MockMvc mockMvc;
	@Autowired UserRepository userRepository;
	
	@DisplayName("회원가입 view")
	@Test
	void joinForm() throws Exception {
		mockMvc.perform(get("/join"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("user/join"))
				.andExpect(model().attributeExists("joinForm"))
				.andExpect(unauthenticated());
	}
	
	@DisplayName("회원가입 - 오류")
    @Test
    void join_submit_fail() throws Exception {
        mockMvc.perform(post("/join")
                .param("nickname", "testname")
                .param("email", "email")
                .param("password", "123")
                .with(csrf()))
                	.andExpect(status().isOk())
                	.andExpect(view().name("user/join"))
                	.andExpect(unauthenticated());                	
    }
	
	@DisplayName("회원가입 - 정상")
	@Test
	void join_submit_success() throws Exception {
		mockMvc.perform(post("/join")
				.param("nickname", "testname")
				.param("email", "testname@naver.com")
				.param("password", "12345678")
				.with(csrf()))
					.andExpect(status().is3xxRedirection())
					.andExpect(view().name("redirect:/"))
					.andExpect(authenticated().withUsername("testname"));
					
		
		Account user = userRepository.findByEmail("testname@naver.com");
		assertNotNull(user);
		assertNotEquals(user.getPassword(), "12345678");
	}
}
