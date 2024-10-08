package me.Sokhra.sokhrabackendspring.imageutility.service;

import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Setter
public class ImageService {

  private String pictureDirectory;

  public void saveImage(MultipartFile image, String fileName) throws IOException {
    byte[] imageData = image.getBytes();
    Path path = Paths.get(pictureDirectory + fileName);
    Files.write(path, imageData);
  }

  public byte[] loadImage(String filename) throws IOException {
    Path filePath = Paths.get(pictureDirectory).resolve(filename);
    return Files.readAllBytes(filePath);
  }
}
