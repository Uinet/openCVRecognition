package com.uinet.opencv.service;

import com.uinet.opencv.entity.Image;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FaceDetectionService {

    Logger logger = LoggerFactory.getLogger(FaceDetectionService.class);

    private final Resource faceResource = new ClassPathResource("haarcascade_frontalface_default.xml");

    public Image getImageWithDetectedFaces(MultipartFile inputImage) throws IOException {
        Mat image = Imgcodecs.imdecode(new MatOfByte(inputImage.getBytes()), Imgcodecs.IMREAD_UNCHANGED);
        return drawRectanglesOnImage(image, getFaces(image, 2).toList());
    }

    public Image drawRectanglesOnImage(Mat image, List<Rect> rectangles) {
        for (Rect rect: rectangles) {
            Imgproc.rectangle(
                    image,
                    new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
        }

        return mat2Image(image);
    }

    private Image mat2Image(Mat frame) {
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".jpg", frame, buffer);
        return new Image(buffer.toArray());
    }

    private MatOfRect getFaces(Mat image, double scaleFactor) throws IOException {
        MatOfRect faceDetections = new MatOfRect();
        CascadeClassifier faceDetector = new CascadeClassifier(faceResource.getFile().getAbsolutePath());
        faceDetector.detectMultiScale(image, faceDetections, scaleFactor);
        logger.info(String.format("Detected %s faces", faceDetections.toArray().length));
        return faceDetections;
    }

    public List<Image> getFaceImages(MultipartFile inputImage) throws IOException {
        List<Image> result = new ArrayList<>();
        Mat image = Imgcodecs.imdecode(new MatOfByte(inputImage.getBytes()), Imgcodecs.IMREAD_UNCHANGED);
        MatOfRect faceDetections = getFaces(image, 2.1);

        for (Rect rect: faceDetections.toList()) {
            result.add(mat2Image(new Mat(image, rect)));
        }

        return result;
    }
}
