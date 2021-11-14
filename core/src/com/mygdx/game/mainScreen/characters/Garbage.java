package com.mygdx.game.mainScreen.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.ultils.Render;

/**
 *  Lớp này tượng trưng cho các đồng xu trong trò chơi
 */
public class Garbage implements Render {
    private final float x;//Tọa dộ trục x của bịch rác khi vẽ ra màn hình
    private final float y;//Tọa dộ trục y của bịch rác khi vẽ ra màn hình
    // Tạo khung chứa hình ảnh được nạp lên từ file của bịch rác
    private final static Texture garbageTexture = new Texture("garbage.png");
    // kích thước chiều rộng của bịch rác là 80 pixels = chiều rộng của đồng xu
    private final static float WIDTH = Coin.getWIDTH();
    // kích thước chiều cao của bịch rác là 60 pixls = chiều cao của đồng xu
    private final static float HEIGHT = Coin.getHEIGHT();
    private static SpriteBatch BATCH;// đồi tượng dùng để vẽ dữ liệu ra màn hình

    // khởi gán các giá trị cần thiết ban đầu cho bịch rác
    public Garbage(SpriteBatch batch, float x, float y) {
        if (BATCH == null)
            BATCH = batch;
        this.x = x;
        this.y = y;
    }

    // Phương thức này được gọi nhằm dể vẽ bịch rác ra màn hình liên tục
    @Override
    public void render() {
        BATCH.draw(garbageTexture, x, y, WIDTH, HEIGHT);
    }

    /**
     * ******* Các getter, setter cần dùng đến ************
     */

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public static float getWIDTH() {
        return WIDTH;
    }

    public static float getHEIGHT() {
        return HEIGHT;
    }
}
