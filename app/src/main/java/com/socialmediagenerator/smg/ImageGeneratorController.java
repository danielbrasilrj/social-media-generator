package com.socialmediagenerator.smg;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.ai.image.Image;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

record PromptRequest(String prompt) {}

@RestController
@RequestMapping("/api/image")
public class ImageGeneratorController {

	public ImageModel imageModel;
	
	public ImageGeneratorController(final ImageModel imageModel) {
		this.imageModel = imageModel;
	}
	
	@PostMapping("/generate")
	public ResponseEntity<Map<String, String>> generateImage(
			@RequestBody PromptRequest promptRequest ) {
		
		String imageUrl = generate( promptRequest.prompt() );

		Map<String, String> response = new HashMap<>();
		response.put( "prompt", promptRequest.prompt() );
		response.put( "imageUrl", imageUrl );

		return ResponseEntity.ok( response );
	}

	public String generate(String prompt) {
		ImagePrompt imagePrompt = new ImagePrompt(prompt);
		ImageResponse imageResponse = imageModel.call(imagePrompt);
		return resolveImageContent(imageResponse);
	}

	private String resolveImageContent(ImageResponse imageResponse) {
		Image image = imageResponse.getResult().getOutput();
		return Optional
				.ofNullable(image.getUrl())
				.orElseGet(image::getB64Json);
	}

}
