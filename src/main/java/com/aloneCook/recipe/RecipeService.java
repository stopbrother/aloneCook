package com.aloneCook.recipe;

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

import com.aloneCook.like.Likes;
import com.aloneCook.recipe.event.RecipeUpdateEvent;import com.aloneCook.recipe.event.RecipeWatchedEvent;
import com.aloneCook.recipe.form.BasicForm;
import com.aloneCook.recipe.form.IngredientForm;
import com.aloneCook.recipe.form.RecipeForm;
import com.aloneCook.recipe.form.StepForm;
import com.aloneCook.community.Community;
import com.aloneCook.community.CommunityRepository;
import com.aloneCook.image.Image;
import com.aloneCook.image.ImageRepository;
import com.aloneCook.image.ImageService;
import com.aloneCook.like.LikeRepository;
import com.aloneCook.user.Account;
import com.aloneCook.user.UserAccount;
import com.aloneCook.user.history.UserHistoryRepository;

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
	
	public void testRecipes(Account account) {
		for (int i = 0; i < 30; i++) {
			String randomvalue = RandomString.make(5);
			Recipe recipe = Recipe.builder()
					.title("테스트 레시피 " + randomvalue)
					.path(randomvalue)
					.foodIntro("테스트")
					.ingredients("test")
					.steps("teststep")
					.manager(new HashSet<>())
					.images(new ArrayList<>())
					.likeCount(0L)
					.commentCount(0L)
					.level(Level.EASY)
					.videoUrl("")
					.viewCount(0L)
					.build();
			
			//실제 이미지파일 이름
			String imageName = "02a93a52-b689-453a-bc03-6b83d237b4f7-StopBrother.png";
			
			//이미지 파일 생성 및 이미지 객체 생성
			Image image = new Image();
			image.setFileName(imageName);
			image.setRecipe(recipe);
			
			recipe.getImages().add(image); //레시피에 이미지 추가
			this.createTestRecipe(recipe, account); //레시피 저장 및 추가작업 수행
		}
	}
	private Recipe createTestRecipe(Recipe recipe, Account account) {
		recipe.setWriter(account);
		recipe.addManager(account);
		recipe.setDrafted(false);
		recipe.setPublishedDateTime(LocalDateTime.now());
		return recipeRepository.save(recipe);
	}
		

	
}
