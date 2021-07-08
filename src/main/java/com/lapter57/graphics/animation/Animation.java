package com.lapter57.graphics.animation;
import com.lapter57.graphics.Graphic;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;


public class Animation {
    private javafx.animation.Animation explosive;
    private javafx.animation.Animation pointer;

    public javafx.animation.Animation getExplosive() {
        return explosive;
    }

    public void playExplosive(){
        explosive.setCycleCount(1);
        explosive.play();
        String soundExpl = "src/main/resources/sound/Explosion.mp3";
        Media expl = new Media(new File(soundExpl).toURI().toString());
        MediaPlayer mp = new MediaPlayer(expl);
        mp.play();
    }

    public void playPointer(){
        pointer.setCycleCount(1);
        pointer.play();
    }

    public ImageView getImageExpl() {
        ImageView imageExpl = new ImageView(Graphic.map_img.get("expl"));
        int count = 11;
        int columns = 11;
        int offsetX = 0;
        int offsetY = 0;
        int width = 45;
        int height = 45;

        imageExpl.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        explosive = new SpriteAnimation(
                imageExpl,
                Duration.millis(450),
                count, columns,
                offsetX, offsetY,
                width, height
        );
        return imageExpl;
    }

    public ImageView getImagePointers(int offsetY, int dur) {
        ImageView imagePointers = new ImageView(Graphic.map_img.get("an_p"));
        int count = 2;
        int columns = 2;
        int offsetX = 0;
        int width = 90;
        int height = 175;
        imagePointers.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        pointer = new SpriteAnimation(
                imagePointers,
                Duration.millis(dur),
                count, columns,
                offsetX, offsetY,
                width, height
        );
        return imagePointers;
    }

    public void soundWater(){
        String soundExpl = "src/main/resources/sound/Water.mp3";
        Media expl = new Media(new File(soundExpl).toURI().toString());
        MediaPlayer mp = new MediaPlayer(expl);
        mp.play();
    }
}