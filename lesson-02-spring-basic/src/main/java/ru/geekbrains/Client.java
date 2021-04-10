package ru.geekbrains;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Client {

    public static void main(String[] args) {
//        CameraRoll cameraRoll = new ColorRoll();
//        Camera camera = new Camera(cameraRoll);

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Camera camera = context.getBean("camera", Camera.class);
        camera.doPhoto();
    }
}
