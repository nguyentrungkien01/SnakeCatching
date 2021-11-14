package com.mygdx.game.endScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MainGame;
import com.mygdx.game.ultils.Button;
import com.mygdx.game.ultils.Render;

/**
 *  lớp này để chưa bảng các button sẽ xuất hiện trên màn hình khi kết thúc trò chơi
 */
public class ButtonTable implements Render {
    private static SpriteBatch BATCH;//Đối tượng dùng để vẽ các hình ảnh ra màn hình
    private final Button inMenuBtn;//button tượng trung cho menu button khi người chơi hover vào
    private final Button outMenuBtn;// button tượng trưng cho menu button khi người chơi không hover vào

    public ButtonTable (SpriteBatch batch){
        //Khởi tạo dối tượng dùng để vẽ
        if(BATCH ==null)
            BATCH=batch;
        //nạp hình ảnh từ file lên
        inMenuBtn=new Button(BATCH, "inMenuBtn.png");
        outMenuBtn = new Button(BATCH, "outMenuBtn.png");
        //thiết lập các giá trị cho các button
        // chiều rộng, chiều cao
        inMenuBtn.setWidth(100);
        inMenuBtn.setHeight(50);
        outMenuBtn.setWidth(inMenuBtn.getWidth());
        outMenuBtn.setHeight(inMenuBtn.getHeight());
        //thiết lập tọa độ x của button luôn ở giữa màn hình
        // x = chiều rộng màn hình /2 - chiều rộng của button /2
        //     = ( màn hình - chiều rộng button)/2
        inMenuBtn.setX((float)(Gdx.graphics.getWidth()-100)/2);
        inMenuBtn.setY(50);
        outMenuBtn.setX(inMenuBtn.getX());
        outMenuBtn.setY(inMenuBtn.getY());
    }

    @Override
    public void render() {
        //kiểm tra nếu nếu button được hover thì vẽ hình button hover ngược
        // lại thì vẽ hình button không được hover ra màn hình
        if(inMenuBtn.isHover()) {
            inMenuBtn.render();
            // Kiểm tra xem button có được click hay không. Nếu có thì chuyển trạng thái
            // màn hình kế tiếp sẽ là màn hình bắt đầu. Và đừng nhạc nền của màn hình hiện tại lại
            if(Gdx.input.isTouched()) {
                MainGame.MODE = MainGame.Mode.START;
                MainGame.flagChangeScreen=false;
                EndScreen.music.stop();
                EndScreen.music.dispose();

            }
        }
        else
        outMenuBtn.render();
    }
}
