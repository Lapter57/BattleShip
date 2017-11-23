package graphics.animation;
import graphics.Graphic;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Animation {
    private javafx.animation.Animation explosive;
    private javafx.animation.Animation pointer;

    public void playExplosive(){
        explosive.setCycleCount(1);
        explosive.play();
    }

    public void playPointer(){
        pointer.setCycleCount(1);
        pointer.play();
    }

    public ImageView getImageExpl() {
        final ImageView imageExpl = new ImageView(Graphic.map_img.get("expl"));
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

    public ImageView getImagePointers() {
        final ImageView imagePointers = new ImageView(Graphic.map_img.get("an_p"));
        int count = 2;
        int columns = 2;
        int offsetX = 0;
        int offsetY = 0;
        int width = 90;
        int height = 175;
        imagePointers.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        pointer = new SpriteAnimation(
                imagePointers,
                Duration.millis(350),
                count, columns,
                offsetX, offsetY,
                width, height
        );
        return imagePointers;
    }
}
