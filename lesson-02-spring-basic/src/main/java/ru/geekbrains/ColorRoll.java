package ru.geekbrains;

import org.springframework.stereotype.Component;

@Component
public class ColorRoll implements CameraRoll{

    @Override
    public void process() {
        System.out.println("-1 цветной кадр");
    }
}
