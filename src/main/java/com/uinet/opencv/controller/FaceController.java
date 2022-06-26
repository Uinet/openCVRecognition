package com.uinet.opencv.controller;

import com.uinet.opencv.service.FaceDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/faces")
public class FaceController {

    @Autowired
    FaceDetectionService faceDetectionService;

    @GetMapping
    private byte[] getImageWithDetectedFaces() throws IOException {
        byte [] byteArray = faceDetectionService.detectFace();
        ByteArrayInputStream inStreambj = new ByteArrayInputStream(byteArray);
        BufferedImage newImage = ImageIO.read(inStreambj);
        ImageIO.write(newImage, "jpg", new File("outputImage.jpg") );
        return byteArray;
    }
}
