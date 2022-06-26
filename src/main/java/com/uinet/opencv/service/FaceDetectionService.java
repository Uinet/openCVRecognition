package com.uinet.opencv.service;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class FaceDetectionService {

    Logger logger = LoggerFactory.getLogger(FaceDetectionService.class);

    private final Resource faceResource = new ClassPathResource("haarcascade_frontalface_default.xml");

    public byte[] detectFace() throws IOException {

        MatOfRect faceDetections = new MatOfRect();
        CascadeClassifier faceDetector = new CascadeClassifier(faceResource.getFile().getAbsolutePath());

        String fileName ="C:/Users/malce/Downloads/314ce31a-9469-4469-841a-85022a4dc754.jpg";

        Mat image = Imgcodecs.imread(fileName);
        faceDetector.detectMultiScale(image, faceDetections, 2);

        logger.info(String.format("Detected %s faces", faceDetections.toArray().length));

        return drawRectanglesOnImage(image, faceDetections.toList());
    }

    public byte[] drawRectanglesOnImage(Mat image, List<Rect> rectangles) {
        for (Rect rect: rectangles) {
            Imgproc.rectangle(
                    image,
                    new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
        }

        return mat2Image(image);
    }

    private byte[] mat2Image(Mat frame) {
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".jpg", frame, buffer);
        return buffer.toArray();
    }
}
