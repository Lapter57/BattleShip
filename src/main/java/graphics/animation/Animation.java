package graphics.animation;
import graphics.Graphic;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Deque;


public class Animation {
    private javafx.animation.Animation explosive;
    private javafx.animation.Animation pointer;
    private Deque<javafx.animation.Animation> explosives = new ArrayDeque<>();
    
    public Animation() {
        Timeline fxTimer = new Timeline(new KeyFrame(Duration.millis(400), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	javafx.animation.Animation expl = explosives.poll();
                if (expl != null) {
                	playExplosiveImpl(expl);
                }
            }
        }));
        fxTimer.setCycleCount(Timeline.INDEFINITE);
        fxTimer.play();        
    	
    }

    public javafx.animation.Animation getExplosive() {
        return explosive;
    }

    public void playExplosive(boolean isImmediate){
    	if (isImmediate) {
    		playExplosiveImpl(explosive);
    	}
    	else {
    		explosives.add(explosive);
    	}
    }
    
    public void playExplosiveImpl(javafx.animation.Animation explosive){
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

    public void sounWater(){
        String soundExpl = "src/main/resources/sound/Water.mp3";
        Media expl = new Media(new File(soundExpl).toURI().toString());
        MediaPlayer mp = new MediaPlayer(expl);
        mp.play();
    }
}
