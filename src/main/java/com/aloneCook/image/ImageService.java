package com.aloneCook.image;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.aloneCook.recipe.Recipe;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {

	private final ImageRepository imageRepository;

	public List<Image> saveImages(List<MultipartFile> imageFiles, Recipe recipe) {
		String uploadDirPath = "src/main/resources/static/uploads"; //파일을 저장할 디렉토리 경로 (상대 경로)
		
		// 디렉토리 생성 
		File uploadDir = new File(uploadDirPath);
		if (!uploadDir.exists()) { //존재하지 않으면 새로 생성
			uploadDir.mkdirs();
		}
		
		List<Image> images = new ArrayList<>();
		
		for (MultipartFile imageFile : imageFiles) {
			if (!imageFile.isEmpty()) {
				//현재 시간을 활용한 고유한 파일명 생성
				String encodedFileName = UUID.randomUUID().toString() + "-" +
						StringUtils.cleanPath(Objects.requireNonNull(imageFile.getOriginalFilename()));
				String decodedFileName = URLDecoder.decode(encodedFileName, StandardCharsets.UTF_8);
				try {
					Path filePath = Path.of(uploadDirPath, decodedFileName); //경로 설정
					Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
					
					Image image = new Image();
					image.setFileName(decodedFileName);
					image.setRecipe(recipe); // 이미지에 레시피 설정
					
					imageRepository.save(image);
					images.add(image);				
				} catch (IOException e) {
					throw new RuntimeException("이미지 저장 실패", e);
				}
			}						
		}
		return images;
	}

	public Image updateImage(String oldFileName, MultipartFile newImageFile) {
		Image oldImage = imageRepository.findByFileName(oldFileName);
		if (oldImage != null) {
			deleteImage(oldImage);
		}
		return saveImage(newImageFile);
	}

	private Image saveImage(MultipartFile newImageFile) {
		try {
			String uploadDirPath = "src/main/resources/static/uploads";
			
			String encodedFileName = System.currentTimeMillis() + "-" +
					StringUtils.cleanPath(Objects.requireNonNull(newImageFile.getOriginalFilename()));
			String decodedFileName = URLDecoder.decode(encodedFileName, StandardCharsets.UTF_8);
			
			Path filePath = Path.of(uploadDirPath, decodedFileName);			
			Files.copy(newImageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			
			Image newImage = new Image();
			newImage.setFileName(decodedFileName);
			imageRepository.save(newImage);
			
			return newImage;
		} catch (IOException e) {
			throw new RuntimeException("이미지 저장 실패", e);
		}
	}

	private void deleteImage(Image image) {
		try {
			String uploadDirPath = "src/main/resources/static/uploads";
			Path filePath = Path.of(uploadDirPath, image.getFileName());
			Files.deleteIfExists(filePath);
			
			imageRepository.delete(image);
		} catch(IOException e) {
			throw new RuntimeException("이미지 삭제 실패", e);
		}
	}

	public Resource loadAsResource(String fileName) {
		try {
			return new UrlResource("file:./src/main/resources/static/uploads/" + fileName);
		} catch (IOException e) {
			throw new RuntimeException("이미지를 읽을 수 없습니다.", e);
		}
	}

	public void deleteImageByFileName(String fileName) {
		Image image = imageRepository.findByFileName(fileName);
		if (image != null) {
			deleteImage(image);
		}
	}

	
	
}
