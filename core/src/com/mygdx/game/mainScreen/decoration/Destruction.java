package com.mygdx.game.mainScreen.decoration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.mainScreen.characters.Player;
import com.mygdx.game.ultils.ObjectAnimation;
import com.mygdx.game.ultils.Render;

//Lớp này tạo hoạt ảnh chết của nhân vật nhận vật do người chơi điều khiển
public class Destruction implements Render {
    private final static float SPEED_ANIMATION = 0.35f; // tốc độ khung hình
    // animation của đối tượng
    private static ObjectAnimation THUNDER_ANIMATION;
    private static ObjectAnimation BURN_ANIMATION;
    private float x; // tọa độ x
    private float y; // tọa độ y
    private static SpriteBatch BATCH;// đối tượng vẽ
    private float stateTime;  //Khoản thời gian giữa 2 khung hình
    //khởi tạo giá trị
    public Destruction (SpriteBatch batch){
        if(BATCH==null)
            BATCH=batch;
        Texture texture = new Texture("thunderEffect.png");
        THUNDER_ANIMATION = new ObjectAnimation(
                texture,SPEED_ANIMATION, texture.getWidth()/3, texture.getHeight()/2);
        Texture texture1 = new Texture("burn.png");
        BURN_ANIMATION = new ObjectAnimation(
                texture1,SPEED_ANIMATION, texture1.getWidth()/3, texture1.getHeight()/3);
    }

    //vẽ ra màn hình
    @Override
    public void render() {
        stateTime += Gdx.graphics.getDeltaTime();
        BATCH.draw((TextureRegion) BURN_ANIMATION.getAnimation().getKeyFrame(stateTime, false),
                x, y, Player.getWIDTH(), Player.getHEIGHT());
        BATCH.draw((TextureRegion) THUNDER_ANIMATION.getAnimation().getKeyFrame(stateTime, false),
                x, y, Player.getWIDTH(), Player.getHEIGHT());
    }

    /**
     * ******* Các getter, setter cần dùng đến ************
     */
    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
