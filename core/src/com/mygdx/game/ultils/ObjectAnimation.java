package com.mygdx.game.ultils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.Arrays;
import java.util.Vector;

//Lớp này nhằm tạo ra animation cho trò chơi
@SuppressWarnings("rawtypes")
public class ObjectAnimation {
    private final Animation animation; // animation cần tạo
    private final Vector<TextureRegion> frames = new Vector<>();//danh sách các khung hình của animation

    //Tạo animation với 1 file hình , tốc dộ các chuyển đổi của animation, chiều rộng, cao của khung hình
    public ObjectAnimation(String path, float speed, int width, int height) {
        //Cắt hình được tải lên từ file với đường dẫn vừa truyền vào thành 1 ma trận các
        // khung hình và chuyển nó thành 1 mảng 1 chiều các khung hình
        Arrays.stream(TextureRegion.split(
                new Texture(path), width, height)).forEach(e -> frames.addAll(Arrays.asList(e)));
        // Tạo animation từ mảng các khung hình đó
        this.animation = new Animation<>(speed, frames.toArray());
    }

    //Tạo animation với 1 khung hình có sẵn , tốc dộ các chuyển đổi của animation, chiều rộng, cao của khung hình
    public ObjectAnimation(Texture texture, float speed, int width, int height) {
        //Cắt hình  vừa truyền vào thành 1 ma trận các
        // khung hình và chuyển nó thành 1 mảng 1 chiều các khung hình
        Arrays.stream(TextureRegion.split(
                texture, width, height)).forEach(e -> frames.addAll(Arrays.asList(e)));
        // Tạo animation từ mảng các khung hình đó
        this.animation = new Animation<>(speed, frames.toArray());
    }

    /**
     * ******* Các getter, setter cần dùng đến ************
     */

    public Animation getAnimation() {
        return this.animation;
    }

    public Vector<TextureRegion> getFrames() {
        return frames;
    }
}
