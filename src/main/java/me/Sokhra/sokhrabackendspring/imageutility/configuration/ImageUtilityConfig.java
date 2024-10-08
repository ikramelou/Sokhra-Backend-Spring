package me.Sokhra.sokhrabackendspring.imageutility.configuration;

import lombok.RequiredArgsConstructor;
import me.Sokhra.sokhrabackendspring.imageutility.service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ImageUtilityConfig {

  @Bean
  public ImageService profileImageService(@Value("${profile-picture-directory}") String directory) {
    ImageService imageService = new ImageService();
    imageService.setPictureDirectory(directory);
    return imageService;
  }

  @Bean
  public ImageService shipmentImageService(@Value("${shipment-picture-directory}") String directory) {
    ImageService imageService = new ImageService();
    imageService.setPictureDirectory(directory);
    return imageService;
  }
}
