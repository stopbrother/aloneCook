package com.aloneCook.modules.recipe;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.aloneCook.infra.MockMvcTest;
import com.aloneCook.modules.account.Account;
import com.aloneCook.modules.account.UserRepository;
import com.aloneCook.modules.account.WithUser;
import com.aloneCook.modules.image.Image;
import com.aloneCook.modules.recipe.repository.RecipeRepository;

@MockMvcTest
class RecipeControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	RecipeService recipeService;
	
	@Autowired
	RecipeRepository recipeRepository;
	
	@Autowired
	UserRepository userRepository;
	
	/*
	private MockMultipartFile image;
	
	@BeforeEach
	void beforeEach() {
		image = new MockMultipartFile("images", "image.jpg", "image/jpeg", "test image".getBytes());
	}*/
	
	@Test
	@WithUser("test")
	@DisplayName("레시피 작성 폼 조회")
	void createRecipeForm() throws Exception {
		mockMvc.perform(get("/recipe-write"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/write"))
				.andExpect(model().attributeExists("account"))
				.andExpect(model().attributeExists("recipeForm"));
	}
	
	@Test
	@WithUser("test")
	@DisplayName("레시피 작성 - 공개 - 성공")
	void createRecipe_public_success() throws Exception {
		//MockMultipartFile image = new MockMultipartFile("images", "image.jpg", "image/jpeg", "test image".getBytes());
		
		mockMvc.perform(multipart("/recipe-write")
				.file(image)
				.param("path", "test-path")
				.param("title", "test-title")
				.param("foodIntro", "test-intro")
				.param("level", "EASY")
				.param("ingredients", "test-ingre")
				.param("steps", "test-step")
				.param("draft", "false")
				.with(csrf()))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/recipe/test-path"));
		
		Recipe recipe = recipeRepository.findByPath("test-path");
		assertNotNull(recipe);
		Account account = userRepository.findByNickname("test");
		assertTrue(recipe.getManager().contains(account));
	}
	
	@Test
	@WithUser("test")
	@DisplayName("레시피 작성 - 실패")
	void createRecipe_fail() throws Exception {
		//MockMultipartFile image = new MockMultipartFile("images", "image.jpg", "image/jpeg", "test image".getBytes());
		mockMvc.perform(multipart("/recipe-write")
				.file(image)
				.param("path", "wrong path")
				.param("title", "test title")
				.param("foodIntro", "test-intro")
				.param("level", "EASY")
				.param("ingredients", "test-ingre")
				.param("steps", "test-step")
				.param("draft", "false")
				.with(csrf()))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/write"))
		.andExpect(model().hasErrors())
		.andExpect(model().attributeExists("account"))
		.andExpect(model().attributeExists("recipeForm"));
		
		Recipe recipe = recipeRepository.findByPath("test-path");
		assertNull(recipe);
	}
}
