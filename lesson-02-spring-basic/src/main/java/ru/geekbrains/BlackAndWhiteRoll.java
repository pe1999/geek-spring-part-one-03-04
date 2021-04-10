package ru.geekbrains;

import org.springframework.stereotype.Component;

@Component
public class BlackAndWhiteRoll implements CameraRoll{
    @Override
    public void process() {
        System.out.println("-1 чб кадр");
    }
}
