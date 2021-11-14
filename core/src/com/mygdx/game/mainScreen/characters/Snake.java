package com.mygdx.game.mainScreen.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.mainScreen.MainScreen;
import com.mygdx.game.mainScreen.decoration.MainScreenBg;
import com.mygdx.game.ultils.ObjectAnimation;
import com.mygdx.game.ultils.Render;

//Tượng trung cho con rắn trong trò chơi
public class Snake implements Render {
    private float x;//Tọa dộ trục x của rắn khi vẽ ra màn hình
    private float y;//Tọa dộ trục y của rắn khi vẽ ra màn hình
    private final static float STEP_DISTANCE =5;//khoảng cách mỗi lần di chuyển
    private final static int WIDTH = 200;// kích thước chiều rộng của rắn là 200 pixels
    private final static int HEIGHT = 100;// kích thước chiều cao của rắn là 100 pixels
    private final static float SPEED_ANIMATION = 0.3f; // tốc độ chuyển đổi khung hình của animation của rắn
    private final static float SPEED_SNAKE = 0.35f;// tốc độ di chuyển của rắn
    private static SpriteBatch BATCH = null;// đồi tượng dùng để vẽ dữ liệu ra màn hình
    private final ObjectAnimation snakeAnimation;  // Đối tượng tượng trưng cho animation của rắn khi di chuyển
    private float stateTime;//Khoảng thời gian giữa 2 khung hình
    public static float SPAWN_TIME;    //Khoảng thời gian xuất hiện giữa 2 con rắn
    private final boolean direction; // hướng di chuyển của con rắn

    // Tạo một giá trị thời gian xuất hiện ngẫu nhiên cho con rắn
    static {
        Snake.SPAWN_TIME = MainScreen.RANDOM.nextInt(1) + 1;
    }

    // khởi gán các giá trị cần thiết ban đầu cho con rắn
    public Snake(SpriteBatch batch, String path) {
        // khởi gán giá trị cho đối đượng dùng để vễ
        if (BATCH == null)
            BATCH = batch;
        /*
                Thiết lập hướng di chuyển dựa vào file hình được taải lên
             nếu là file hình di chuyển rắn bên phải thì direction sẽ là true và ngược lại
         */
        direction = path.contains("Right");

        /*
               -   Thiết lập tọa độ x cho rắn khi xuất hiện dựa vào hướng di chuyển của rắn
               -   Nếu rắn xuất phát từ bên trái thì x = 0 - chiều rông con rắn
               -   Nếu rắn xuất phát từ bên phải thì x = chiều rộng của bản đồ
         */
        x = direction ? -Snake.getWIDTH() : Gdx.graphics.getWidth();
        /*
               - Thiết lập tọa dộ y cho rắn khi xuất hiện
               - Tọa độ y sẽ được tạo một các ngậu nhiên
               - Giá trị tọa độ y được tạo phải chia hết cho chiều cao của 1 ô trong map
         */
        do {
            y = MainScreen.RANDOM.nextInt(Gdx.graphics.getHeight() - 150);
        } while (y % ((float) Gdx.graphics.getHeight() / 10) != 0);

        // tạo animation cho rắn
        snakeAnimation = new ObjectAnimation(path, SPEED_ANIMATION, WIDTH, HEIGHT);
    }

    // Phương thức này được gọi nhằm dể vẽ rắn ra màn hình liên tục
    @Override
    public void render() {
        // thiết lập quãng đưỡng rắn co thể di chuyển trong dựa vào tốc độ di chuyển
        x += (direction ? STEP_DISTANCE : -STEP_DISTANCE) * SPEED_SNAKE;

        //Tính tổng khoảng thời gian chênh lệch giữa các khung hình được vẽ ra
        stateTime += Gdx.graphics.getDeltaTime();

        // Vẽ rắn ra màn hình
        BATCH.draw((TextureRegion) snakeAnimation.getAnimation().getKeyFrame(stateTime, true)
                , x, y, WIDTH, HEIGHT);
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

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public void setY(float y) {
        this.y = y;
    }
}
