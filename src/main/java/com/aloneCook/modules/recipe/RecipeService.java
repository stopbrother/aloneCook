package com.aloneCook.modules.recipe;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.aloneCook.modules.account.Account;
import com.aloneCook.modules.account.UserAccount;
import com.aloneCook.modules.account.history.UserHistoryRepository;
import com.aloneCook.modules.community.Community;
import com.aloneCook.modules.community.CommunityRepository;
import com.aloneCook.modules.image.Image;
import com.aloneCook.modules.image.ImageRepository;
import com.aloneCook.modules.image.ImageService;
import com.aloneCook.modules.like.LikeRepository;
import com.aloneCook.modules.like.Likes;
import com.aloneCook.modules.recipe.event.RecipeUpdateEvent;
import com.aloneCook.modules.recipe.event.RecipeWatchedEvent;
import com.aloneCook.modules.recipe.form.RecipeForm;
import com.aloneCook.modules.recipe.repository.RecipeRepository;
import com.aloneCook.recipe.form.BasicForm;
import com.aloneCook.recipe.form.IngredientForm;
import com.aloneCook.recipe.form.StepForm;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeService {
	
	private final RecipeRepository recipeRepository;
	private final LikeRepository likeRepository;
	private final ModelMapper modelMapper;
	private final ApplicationEventPublisher eventPublisher;
	private final CommunityRepository communityRepository;
	private final ImageService imageService;
	private final UserHistoryRepository userHistoryRepository;
	
	
	public Recipe createNewRecipe(Recipe recipe, Account account, List<MultipartFile> imageFiles) {
		recipe.setWriter(account);
		recipe.addManager(account);
		recipe.setDrafted(false);
		recipe.setPublishedDateTime(LocalDateTime.now());
		
		List<Image> images = imageService.saveImages(imageFiles, recipe);
		
		recipe.setImages(images);
		
		return recipeRepository.save(recipe);
	}
	public Recipe draftRecipe(Recipe recipe, Account account, List<MultipartFile> imageFiles) {		
		recipe.setWriter(account);
		recipe.addManager(account);
		recipe.setCreatedDateTime(LocalDateTime.now());
		recipe.setDrafted(true);
		
		List<Image> images = imageService.saveImages(imageFiles, recipe);

		recipe.setImages(images);
		return recipeRepository.save(recipe);
	}
	
	public void editDraftRecipe(Recipe recipe, RecipeForm recipeForm, List<MultipartFile> images) {		
		modelMapper.map(recipeForm, recipe); //RecipeForm의 내용을 기존 레시피 엔티티에 매핑
		recipe.setDrafted(true);
		
		//if (images != null && !images.isEmpty()) {
		
		//이미지 업로드 및 기타 로직 수행
		List<Image> imageFiles = imageService.saveImages(images, recipe);
			
		//기존 레시피의 이미지들을 제거하고 새로운 이미지들로 설정
		recipe.getImages().clear();
		recipe.getImages().addAll(imageFiles);			
				
	}
	public void editRecipe(Recipe recipe, RecipeForm recipeForm, List<MultipartFile> images) {
		modelMapper.map(recipeForm, recipe);
		recipe.setDrafted(false);
							
		List<Image> imageFiles = imageService.saveImages(images, recipe);
		recipe.getImages().clear();
		recipe.getImages().addAll(imageFiles);		
				
	}
	
	
	/*
	public Recipe draftRecipe(Recipe recipe, Account account) {
		recipe.setWriter(account);
		recipe.addManager(account);
		recipe.setDrafted(true);
		recipe.setCreatedDateTime(LocalDateTime.now());
		return recipeRepository.save(recipe);
	}
	*/

	
/*
	private List<Image> saveImages(List<MultipartFile> imageFiles) {
		File uploadDir = new File("uploads");
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		List<Image> images = new ArrayList<>();
		for (MultipartFile imageFile : imageFiles) {
			String uniqueFileName = System.currentTimeMillis() + "-" +
					StringUtils.cleanPath(Objects.requireNonNull(imageFile.getOriginalFilename()));
			Path filePath = Path.of("uploads", uniqueFileName);
			try {
				Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
				
				Image image = new Image();
				image.setFileName(uniqueFileName);
				images.add(image);
			} catch (IOException e) {
				throw new RuntimeException("이미지 저장 실패했습니다.", e);
			}
		}
		return images;
	}
	*/
	public Recipe getRecipe(String path) {
		Recipe recipe = this.recipeRepository.findByPath(path);
		checkIfRecipe(path, recipe);
		eventPublisher.publishEvent(new RecipeWatchedEvent(recipe));
		return recipe;
	}
	
	public Recipe getRecipeToEdit(Account account, String path) {
		Recipe recipe = this.getRecipe(path);
		checkIfManager(account, recipe);
		return recipe;
	}
	
	
	public Recipe getRecipeToStatus(Account account, String path) {
		Recipe recipe = recipeRepository.findRecipeWithManagerByPath(path);
		checkIfRecipe(path, recipe);
		checkIfManager(account, recipe);
		return recipe;
	}
		
	
	
	private void checkIfRecipe(String path, Recipe recipe) {
		if (recipe == null) {
			throw new IllegalArgumentException(path + "에 해당하는 레시피가 없습니다.");
		}
	}
	private void checkIfManager(Account account, Recipe recipe) {
		if (!recipe.isManage(account)) {
			throw new AccessDeniedException("해당 기능을 사용할 수 없습니다.");
		}
	}
/*
	public void addLiker(Recipe recipe, UserDTO userDTO) {
		recipe.addLiker(userDTO);
	}

	public void removeLiker(Recipe recipe, UserDTO userDTO) {
		recipe.removeLiker(userDTO);
	}
*/
	public Recipe getRecipeToPath(String path) {
		Recipe recipe = recipeRepository.findRecipeOnlyByPath(path);
		checkIfRecipe(path, recipe);
		return recipe;
	}
	
	public void getRecipeCnt(String path, Account account) {
		Recipe recipe = this.getRecipe(path);
		recipe.viewCnt(account);
	}



	
	public void remove(Recipe recipe) {		
		recipeRepository.delete(recipe);				
	}


/*
	public void editBasic(Recipe recipe, BasicForm basicForm) {
		modelMapper.map(basicForm, recipe);
	}

	public void editIngredient(Recipe recipe, IngredientForm ingredientForm) {
		modelMapper.map(ingredientForm, recipe);
	}

	public void editStep(Recipe recipe, StepForm stepForm) {
		modelMapper.map(stepForm, recipe);
	}
*/

/*
	public Recipe getRecipeToComment(String path) {
		Recipe recipe = recipeRepository.findRecipeWithCommunityByPath(path);
		checkIfRecipe(path, recipe);
		return recipe;
	}
*/



	public void removeComment(Recipe recipe, Community community) {
		recipe.getComments().remove(community);	
	}

	public void addComment(Recipe recipe, Community community) {
		recipe.getComments().add(community);
	}
	

		

	
}
