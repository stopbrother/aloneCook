package com.aloneCook.image;

import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ImageController {
	
	private final ImageService imageService;
	
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
}
