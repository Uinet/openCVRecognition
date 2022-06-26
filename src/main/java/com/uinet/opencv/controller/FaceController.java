package com.uinet.opencv.controller;

import com.uinet.opencv.entity.Image;
import com.uinet.opencv.service.FaceDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/face-recognition")
public class FaceController {

    @Autowired
    FaceDetectionService faceDetectionService;

    @GetMapping
    private Image getImageWithDetectedFaces(@RequestBody MultipartFile file) throws IOException {
        Image img = faceDetectionService.getImageWithDetectedFaces(file);
        ByteArrayInputStream inStreambj = new ByteArrayInputStream(img.getImage());
        BufferedImage newImage = ImageIO.read(inStreambj);
        ImageIO.write(newImage, "jpg", new File("outputImage.jpg") );
        return img;
    }

    @GetMapping("/faces")
    private List<Image> getFaces(@RequestBody MultipartFile file) throws IOException {
        List<Image> faces = faceDetectionService.getFaceImages(file);
        for (Image img: faces) {
            ByteArrayInputStream inStreambj = new ByteArrayInputStream(img.getImage());
            BufferedImage newImage = ImageIO.read(inStreambj);
            ImageIO.write(newImage, "jpg", new File( UUID.randomUUID() + ".jpg") );
        }
        return faces;
    }
}
