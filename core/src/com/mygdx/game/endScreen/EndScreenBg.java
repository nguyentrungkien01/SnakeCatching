package com.mygdx.game.endScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.MainGame;
import com.mygdx.game.ultils.ObjectAnimation;
import com.mygdx.game.ultils.Render;

//Lớp này dùng để vẽ background cho màn hình kết thúc
public class EndScreenBg implements Render {
    private static SpriteBatch BATCH;// đối tượng dùng để vẽ
    private final Texture bgTexture;//background
    private final Texture winTexture;//thông báo win
    private final Texture loseTexture;//thông báo lose
    private final ObjectAnimation fireAnimation;//Hoat anh đám lửa
    private final static float WIDTH = 700;//Chiều rộng dòng chữ thông báo
    private final static float HEIGHT = 500;//Chiều cao dòng chữ thông báo
    private float stateTime;//chênh lệch thời gian giữa các khung hình trong animation
    private final BitmapFont bitmapFont;//font chữ
    private final GlyphLayout glyphLayout;//layout cho thông diệp
    private final static String strLose = "To win this game, in addition to catching snakes, " +
            "you also need to clean up trash. As well as wanting to develop sustainably," +
            " you need to care about recycling waste and protecting the environment. ";//chuỗi thông diệp xuất hiện khi win
    private final static String strWin = "Congrats, you did it! you know how to strike a balance" +
            " between achieving your goals and caring about the environment." +
            " Let's share this with people around you!";//chuỗi thông diệp xuất hiện khi lose

    //Khởi tạo các giá trị cho các thuộc tính
    public EndScreenBg(SpriteBatch batch) {
        if (BATCH == null)
            BATCH = batch;
        bgTexture = new Texture("bgEnd.jpg");
        winTexture = new Texture("win.png");
        loseTexture = new Texture("lose.png");
        Texture fireTexture = new Texture("endFire.png");
        fireAnimation = new ObjectAnimation(fireTexture, 0.2f,
                fireTexture.getWidth(), fireTexture.getHeight() / 8);
        bitmapFont = new BitmapFont(Gdx.files.internal("fontEnd.fnt"));
        glyphLayout = new GlyphLayout();
    }

    //Vẽ ra màn hình
    @Override
    public void render() {
        stateTime += Gdx.graphics.getDeltaTime();
        //vẽ background
        BATCH.draw(bgTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //vẽ thông diệp ra màn hình
        glyphLayout.setText(bitmapFont, MainGame.IS_WIN ? strWin : strLose,
                0, MainGame.IS_WIN ? strWin.length() : strLose.length(),
                Color.BLACK, 500, Align.center, true, null);
        bitmapFont.draw(BATCH, glyphLayout, (Gdx.graphics.getWidth() - glyphLayout.width) / 2,
                Gdx.graphics.getHeight() - glyphLayout.height / 2 - 50);
        //vẽ dòng chữ lose hoặc win
        BATCH.draw(MainGame.IS_WIN ? winTexture : loseTexture,
                (Gdx.graphics.getWidth() - WIDTH / 2) / 2, ((float) Gdx.graphics.getHeight() - HEIGHT) / 2,
                WIDTH / 2, HEIGHT / 2);
        //vẽ lửa
        BATCH.draw((TextureRegion) fireAnimation.getAnimation().getKeyFrame(stateTime, true),
                100, 100, 200, 400);
        BATCH.draw((TextureRegion) fireAnimation.getAnimation().getKeyFrame(stateTime, true),
                Gdx.graphics.getWidth() - 300, 100, 200, 400);
        BATCH.draw((TextureRegion) fireAnimation.getAnimation().getKeyFrame(stateTime, true),
                500, 20, 100, 200);
        BATCH.draw((TextureRegion) fireAnimation.getAnimation().getKeyFrame(stateTime, true),
                Gdx.graphics.getWidth() - 600, 20, 100, 200);
    }
}
