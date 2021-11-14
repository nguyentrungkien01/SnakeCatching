package com.mygdx.game.startScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MainGame;
import com.mygdx.game.ultils.Button;
import com.mygdx.game.ultils.Render;

//danh sách các button lúc bắt đầu
public class ButtonTable implements Render {
    private static SpriteBatch BATCH; //đối tượng dùng để vẽ
    private final Button inPlayBtn; // nút chơi khi di chuyển chuột vào
    private final Button outPlayBtn; // nút chơi khi di chuyển chuột ra
    private final Button inExitBtn; // nút thoát khi di chuyển chuột vào
    private final Button outExitBtn; // nút thoát khi di chuyển chuột r

    //khởi tạo các giá trị
    public ButtonTable(SpriteBatch batch) {
        if (BATCH == null)
            BATCH = batch;
        inPlayBtn = new Button(BATCH, "inPlayBtn.png");
        outPlayBtn = new Button(BATCH, "outPlayBtn.png");
        inExitBtn = new Button(BATCH, "inExitBtn.png");
        outExitBtn = new Button(BATCH, "outExitBtn.png");
        inPlayBtn.setX((Gdx.graphics.getWidth() - inPlayBtn.getWidth()) / 2);
        inPlayBtn.setY((Gdx.graphics.getHeight() - inPlayBtn.getHeight()) / 2);
        outPlayBtn.setX(inPlayBtn.getX());
        outPlayBtn.setY(inPlayBtn.getY());
        inExitBtn.setWidth(inPlayBtn.getWidth() - 100);
        inExitBtn.setHeight(inPlayBtn.getHeight() - 50);
        inExitBtn.setX(inPlayBtn.getX() + 50);
        inExitBtn.setY(inPlayBtn.getY() - inPlayBtn.getHeight());
        outExitBtn.setWidth(inExitBtn.getWidth());
        outExitBtn.setHeight(inExitBtn.getHeight());
        outExitBtn.setX(inExitBtn.getX());
        outExitBtn.setY(inExitBtn.getY());
    }

    @Override
    public void render() {
        //kiểm tra hover để hiển thị các trạng thái của button
        if (inPlayBtn.isHover()) {
            inPlayBtn.render();
            //kiểm tra click để thiết lập cờ báo hiệu chuyển đổi màn hình sang màn hình chơi game chính
            if (Gdx.input.isTouched()) {
                MainGame.MODE = MainGame.Mode.MAIN;
                MainGame.flagChangeScreen = false;
            }
        } else
            outPlayBtn.render();

        //kiểm tra hover để hiển thị các trạng thái của button
        if (inExitBtn.isHover()) {
            inExitBtn.render();
            //kiểm tra click để thoát trò chơi
            if (Gdx.input.isTouched())
                Gdx.app.exit();

        } else
            outExitBtn.render();

    }
}
