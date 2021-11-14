package com.mygdx.game.mainScreen.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.mainScreen.MainScreen;
import com.mygdx.game.ultils.ObjectAnimation;
import com.mygdx.game.ultils.Render;
import com.mygdx.game.mainScreen.decoration.MainScreenBg;
import java.util.Arrays;
import java.util.Vector;

/**
 *  Lớp này tượng trưng cho các đồng xu trong trò chơi
 */
public class Coin implements Render {
    private float x;//Tọa dộ trục x của đồng xu khi vẽ ra màn hình
    private float y;//Tọa dộ trục y của đồng xu khi vẽ ra màn hình
    private final static int WIDTH = 80;// kích thước chiều rộng của đồng xu là 80 pixels
    private final static int HEIGHT = 60; // kích thước chiều cao của đồng xu là 60 pixels
    // Tạo khung chứa hình ảnh được nạp lên từ file của đồng xu
    private final static Texture COIN_TEXTURE = new Texture("coin.png");
    private final static float SPEED_ANIMATION = 0.1f;// tốc độ chuyển đổi khung hình của animation của đồng xu
    private static SpriteBatch BATCH = null;// đồi tượng dùng để vẽ hình ra màn hình
    private final ObjectAnimation coinAnimation; // Đối tượng tượng trưng cho animation của đồng xu
    private float stateTime;  //Khoảng thời gian giữa 2 khung hình
    public static float SPAWN_TIME;  //Khoản thời gian xuất hiện giữa 2 đồng xu
    private float timeExist = 500; // THời gian tồn tại của đồng xu trên màn hình

    // Tạo một giá trị thời gian xuất hiện ngẫu nhiên cho đồng xu
    static {
        Coin.SPAWN_TIME = MainScreen.RANDOM.nextInt(2) + 1;
    }

    // Lớp phụ dùng để chưa những vị trí mà đồng xu có thể xuất hiện trên bản đồ
    private static class PossiblePosition {
        private final float xPos; // tọa độ trục x
        private final float yPos; // tọa độ trục y

        //khởi gán giá trị lúc tạo đối tượng
        PossiblePosition(float xPos, float yPos) {
            this.xPos = xPos;
            this.yPos = yPos;
        }
    }

    // khởi gán các giá trị cần thiết ban đầu cho đồng xu
    public Coin(SpriteBatch batch) {
        // khởi gán giá trị cho đối đượng dùng để vễ
        if (BATCH == null)
            BATCH = batch;
        this.setPosition();  // thiết lâp dữ liệu cho nơi đồng xu có thể xuất hiện

        // Tạo animation cho đồng xu
        coinAnimation = new ObjectAnimation(
                COIN_TEXTURE, SPEED_ANIMATION, COIN_TEXTURE.getWidth(), COIN_TEXTURE.getHeight() / 13 + 1);
        Gdx.audio.newSound(Gdx.files.internal("coin.mp3")).play(1.0f);
    }

    // thiết lâp dữ liệu cho những nơi đồng xu có thể xuất hiện
    private void setPosition() {
        // Lấy dữ liệu các vị trí trong bản đồ
        int[][] map = MainScreenBg.getMAP();

        //tạo danh sách những ô đồng xu có thể xuất hiện trên bản đồ
        Vector<PossiblePosition> positionList = new Vector<>();
        /*
                Duyệt bản đồ, những ô có giá trị bằng 1 (đất liền) thì thêm vị trí
            bắt đầu vẽ ô đó vào danh sách các vị trí có thể xuất hiện của đồng xu
                Với: Vị trí tọa độ x = vị trí cột tìm thấy * chiều rộng cột
                    Vị trí tọa độ y = vị trí dòng tìm thấy * chiều cao của dòng
         */
        for (int i = 0; i < Arrays.stream(map).toArray().length; i++)
            for (int j = 0; j < Arrays.stream(map[i]).toArray().length; j++)
                if (map[i][j] == 1)
                    positionList.add(new PossiblePosition(j * MainScreenBg.getStepsX(), i * MainScreenBg.getStepsY()));

        /*
                - Lấy vị trí ngẫu nhiên trong danh sách các vị trí có thể xuất hiện của đồng xu,
            rồi thiết lập vị trí xuất hiên cho đồng xu đó nằm giữa ô
                - Cách tính tọa độ đồng xu:
                           x = tọa độ x của ô + (chiều rộng ô - chiều rộng đồng xu)/2
                           y = tọa dộ y của ô + (chiều cao ô - chiều cao đồng xu)/2
         */
        int randIndex = MainScreen.RANDOM.nextInt(positionList.size());
        this.x = positionList.get(randIndex).xPos + (MainScreenBg.getStepsX() - getWIDTH()) / 2;
        this.y = positionList.get(randIndex).yPos + (MainScreenBg.getStepsY() - getHEIGHT()) / 2;
    }

    // Phương thức này được gọi nhằm dể vẽ đồng xu ra màn hình liên tục
    @Override
    public void render() {

        //sau mỗi lần vẽ giảm thời gian tồn tại của đồng xu di 1 đơn vị
        timeExist--;

        //Tính tổng khoảng thời gian chênh lệch giữa các khung hình được vẽ ra
        stateTime += Gdx.graphics.getDeltaTime();

        // Vẽ đồng xu ra màn hình
        BATCH.draw((TextureRegion) coinAnimation.getAnimation().getKeyFrame(stateTime, true),
                this.x, this.y, getWIDTH(), getHEIGHT());
    }

    /**
     * ******* Các getter, setter cần dùng đến ************
     */

    public float getTimeExist() {
        return timeExist;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

}
