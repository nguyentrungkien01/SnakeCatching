package com.mygdx.game.ultils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// Button trong trò chơi
public class Button implements Render{
    private float x; // tọa độ trục x
    private float y; // tọa độ trục y
    private float width = 300; // chiều rộng button
    private float height = 200; // chiều coa button
    private final Texture button;//khung chứa hình của button
    private static SpriteBatch BATCH;// đối tượng dùng để vẽ hình ra màn hình

    //khởi tạo dữ liệu
    public Button(SpriteBatch batch, String path) {
        if (BATCH == null)
            BATCH = batch;
        button = new Texture(path);
    }
    @Override
    public void render() {
        BATCH.draw(button, getX(), getY(), width, height);
    }

    //kiểm tra xem button có được hover hay không
    /*
        Khoanh vùng phạm vi button:
           -    khi xMouse >= xButton và xMouse<= xButton + widthButto => laoị bỏ ohạm vi 2 bên trái phải của button
           -    Do trong phương thức lấy tọa độ y của con trỏ chuột dùng theo hệ tọa dộ trục y nằm bên dưới trục x mà tọa độ
           button thì lại theo tọa dộ truc y nằm bên trên trục x nên để khoanh vùng button ta phải đụa thêm vào chiều cao
           của màn hinh. Cụ thể là yButton <= heightScreen - yMouse và
                                    yButton + heightButton <= heightScreen - yMouse
     */
    public boolean isHover() {
        return Gdx.input.getX() >= this.x && Gdx.input.getX() <= this.x + this.width&&
                Gdx.graphics.getHeight()-Gdx.input.getY() >= this.y &&
                Gdx.graphics.getHeight()- Gdx.input.getY() <= this.y + this.height;
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

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

}
