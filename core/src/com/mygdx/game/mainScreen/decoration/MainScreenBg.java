package com.mygdx.game.mainScreen.decoration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.ultils.Render;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

//background màn hình chơi game chính
public class MainScreenBg implements Render {
    private static Texture POISON = null;
    private static Texture FROZEN = null; // chứa hình ảnh trưng băng (đất liền) trên map
    private static SpriteBatch BATCH = null;// đồi tượng dùng để vẽ dữ liệu ra màn hình
    private final static int AMO_STEPS = 10;// số dòng, cột cần chia của map
    private final static float STEPS_X = (float) Gdx.graphics.getWidth() / AMO_STEPS; // chiều rộng 1 ô trong map
    private final static float STEPS_Y = (float) Gdx.graphics.getHeight() / AMO_STEPS; // chiều cao 1 ô trong map

    //ma trận bản đồ của trò chơi. những nơi có giá trị bằng 0 là nước đôc, bằng 1 là băng (đất liền)
    private final static int[][] MAP = new int[][]{
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 1, 1, 0, 0, 0, 1, 0, 0, 1},
            {0, 1, 0, 0, 0, 1, 1, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 0, 1, 1, 1},
            {1, 0, 0, 1, 0, 0, 0, 1, 1, 0},
            {1, 0, 0, 1, 1, 0, 0, 0, 1, 0},
            {1, 1, 0, 1, 1, 1, 1, 0, 1, 1},
            {0, 1, 1, 1, 0, 0, 1, 1, 1, 0},
            {0, 0, 1, 1, 1, 1, 1, 0, 0, 0},
            {1, 1, 1, 0, 1, 1, 0, 0, 0, 0}

    };

    // khởi gán các giá trị cần thiết ban đầu cho background
    public MainScreenBg(SpriteBatch batch) {
        // khởi gán giá trị cho đối đượng dùng để vễ
        if (BATCH == null)
            BATCH = batch;
        // Nạp các hình ảnh từ file lên
        POISON = new Texture("poison.jpg");
        FROZEN = new Texture("frozen.jpg");
    }

    // Phương thức này được gọi nhằm dể vẽ đồng xu ra màn hình liên tục
    @Override
    public void render() {
         /*
                - Duyệt ma trận bản đồ những ô có giá trị bằng 1 thì vẽ đất liền, bằng 0 thì vẽ dung nham
                - Đối với những nơi có giá trị bằng 0 thì thêm vào danh animation của dung nham có thể xuất hiện
                - Tọa độ được tính bằng cách: x = cột thứ j * chiều rộng của 1 ô
                                                y = dòng thứ i * chiều cao của 1 ô
          */
        AtomicInteger i = new AtomicInteger(0);
        AtomicInteger j = new AtomicInteger(0);
        Arrays.stream(getMAP()).forEach(row -> {
            Arrays.stream(row).forEach(e -> {
                BATCH.draw((e == 0) ? POISON : FROZEN,
                        getStepsX() * j.get(),
                        getStepsY() * i.get(),
                        getStepsX(),
                        getStepsY());
                j.set(j.get() + 1);
            });
            i.set(i.get() + 1);
            j.set(0);
        });


    }

    // hủy bỏ nững đối tượng không dùng nữa khi kết thúc
    public void dispose() {
        POISON.dispose();
        FROZEN.dispose();
    }

    /**
     * ******* Các getter, setter cần dùng đến ************
     */

    public static int[][] getMAP() {
        return MAP;
    }

    public static float getStepsX() {
        return STEPS_X;
    }

    public static float getStepsY() {
        return STEPS_Y;
    }

}
