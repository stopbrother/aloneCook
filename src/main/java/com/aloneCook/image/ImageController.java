package com.aloneCook.image;

import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ImageController {
	
	private final ImageService imageService;
	private final ImageRepository imageRepository;
	
	/*
	@GetMapping("/uploads/{fileName}")
	public ResponseEntity saveFile(@PathVariable String fileName) {
		Resource file = imageService.
	}*/
	
	@GetMapping("/uploads/{fileName}")
	public ResponseEntity<Resource> serveFile(@PathVariable String fileName) {
		Resource file = imageService.loadAsResource(fileName);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.noCache())
				.body(file);
	}
	
	@PostMapping("/update-foodimg")
	@ResponseBody
	public ResponseEntity updateFoodImg(@RequestParam("oldFileName") String oldFileName,
										@RequestParam("newImageFile") MultipartFile newImageFile) {
		Image updateImage = imageService.updateImage(oldFileName, newImageFile);
		
		if(updateImage != null) {
			return ResponseEntity.ok("이미지 변경 성공");
		}else {
			return ResponseEntity.badRequest().body("이미지 변경 실패");
		}
	}
	
	@PostMapping("/delete-foodimg")
	@ResponseBody
	public ResponseEntity deleteFoodImg(@RequestParam("fileName") String fileName) {
		imageService.deleteImageByFileName(fileName);
		return ResponseEntity.ok("이미지 삭제 성공");
	}
}
