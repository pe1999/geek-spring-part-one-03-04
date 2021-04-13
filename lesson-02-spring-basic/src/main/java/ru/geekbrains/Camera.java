package ru.geekbrains;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class Camera {

    @Autowired
    private List<CameraRoll> cameraRollList;

//    @Autowired
//    public Camera(CameraRoll cameraRoll) {
//        this.cameraRoll = cameraRoll;
//    }

    @PostConstruct
    public void init() {
        System.out.println("Инициализируюсь");
    }

    public void doPhoto() {
        System.out.println("Щелк!");
        cameraRollList.forEach(CameraRoll::process);
    }
}
