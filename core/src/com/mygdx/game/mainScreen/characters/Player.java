package com.mygdx.game.mainScreen.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.ultils.ObjectAnimation;
import com.mygdx.game.ultils.Render;

/**
 *  Lớp này tượng trưng cho nhân vật mà người chơi điều khiện trong trò chơi
 */
public class Player implements Render {
    private float x = 540; //Tọa dộ trục x của nhân vật khi vẽ ra màn hình
    private float y = 400; // Tọa độ trục y của nhân vật khi vẽ ra màn hình
    private final static float STEP_DISTANCE=3;//khoảng cách mỗi lần di chuyển
    private final static float WIDTH = 180; // kích thước chiều rộng của nhận vật là 180 pixels
    private final static float HEIGHT = 100; // kích thước chiều cao của nhận vật là 100 pixls
    private final static float SPEED_ANIMATION = 0.1f; // tốc độ chuyển đổi khung hình của animation của nhân vật
    private static SpriteBatch BATCH = null; // đồi tượng dùng để vẽ dữ liệu ra màn hình
    // Đối tượng tượng trưng cho animation của nhân vật khi di chuyển qua trái
    private final ObjectAnimation playerAnimationLeft;
    //Đối tượng tượng trưn cho animation của nhân vật khi di chuyển qua phải
    private final ObjectAnimation playerAnimationRight;
    //Đối tượng chứa hình ảnh của nhân vật khi di chuyển lên trên hoặc xuống dưới nhưng hướng qua phải
    private final TextureRegion playerFrameUpDownRight;
    //Đối tượng chứa hình ảnh của nhân vật khi di chuyển lên trên hoặc xuống dưới nhưng hướng qua trái
    private final TextureRegion playerFrameUpDownLeft;
    private float stateTime;//Khoản thời gian giữa 2 khung hình
    private boolean direction; //hướng di chuyển của nhân vật ( true : từ trái qua phải / flase: từ phải qua trái )
    //hiệu ứng sấm sét xung quanh nhân vật
    private final ObjectAnimation thunder;
    private final ObjectAnimation thuder1;
    // Một enum quy dịnh nhân vật có 4 hướng di chuyển là lên, dxuống, trái phải
    private enum Move {
        UP, // lên trên
        DOWN, // xuống dưới
        RIGHT, // qua phải
        LEFT // qua trái
    }

    // khởi gán các giá trị cần thiết ban đầu cho nhân vật
    public Player(SpriteBatch batch) {
        direction = true; // thiết lập hướng di chuyển ban đầu của nhân vật là quay qua phải
        // khởi gán giá trị cho đối đượng dùng để vễ
        if (BATCH == null)
            BATCH = batch;

        // Tạo khung chứa hình ảnh được nạp lên từ file của nhân vật mà người chơi điều khiển khi đi qua phải
        Texture playerTextureRight = new Texture("playerRight.png");
        // Tạo khung chứa hình ảnh được nạp lên từ file của nhân vật mà người chơi điều khiển khi đi qua trái
        Texture playerTextureLeft = new Texture("playerLeft.png");

        // Tạo animation cho nhân vật di chuyển qua phải từ bức ảnh bên trên
        playerAnimationRight = new ObjectAnimation(
                playerTextureRight, SPEED_ANIMATION, playerTextureRight.getWidth(), playerTextureRight.getHeight() / 8);
        //  Tạo animation cho nhân vật di chuyển qua trái từ bức ảnh bên trên
        playerAnimationLeft = new ObjectAnimation(
                playerTextureLeft, SPEED_ANIMATION, playerTextureLeft.getWidth(), playerTextureRight.getHeight() / 8);
        // Tạo ảnh nhân vật di chuyển lên trên hoặc xuống dưới nhưng hướng qua bên phải
        playerFrameUpDownRight = playerAnimationRight.getFrames().get(0);
        // Tạo ảnh nhân vật di chuyển lên trên hoặc xuống dưới nhưng hướng qua bên trái
        playerFrameUpDownLeft = playerAnimationLeft.getFrames().get(0);

        //tạo animation cho sấm sét xung quanh người chơi
        Texture thunderTexture = new Texture("thunder.png");
        this.thunder = new ObjectAnimation(thunderTexture, 0.3f,
                thunderTexture.getWidth()/9, thunderTexture.getHeight());
        Texture thunderTexture1 = new Texture("thunder1.png");
        thuder1= new ObjectAnimation(thunderTexture1, 0.3f, thunderTexture1.getWidth()/3,
                thunderTexture1.getHeight()/3);
    }

    // Phương thức này được gọi dể vẽ nhân vật người chơi ra màn hình liên tục
    @Override
    public void render() {
        // khởi gán hướng di chuyển bằng null qua mỗi lần vẽ ra màn hình
        Move move = null;
        // Tính tổng khoảng thời gian chênh lệch giữa các khung hình được vẽ ra
        stateTime += Gdx.graphics.getDeltaTime();

        /*
                Kiểm tra nếu người chơi ấn phím mũi tên lên trên hay không
            nếu có thì thiết lập tọa độ trục y cho nhân vật tăng thêm một khoảng nhất định để
            hình ảnh của nhân vật được vẽ ra màn hình sẽ ở phía trện hình ảnh trước đó
         */
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            // tăng tọa dộ trục y của nhân vật
            y += STEP_DISTANCE;
            /*
                    Kiểm tra nếu hình ảnh đi lên phía trên của nhân vật ra khỏi mản hình thì
                thiết lập lại tọa độ trục y
            */
            if (y + HEIGHT > Gdx.graphics.getHeight())
                y = Gdx.graphics.getHeight() - HEIGHT;
            // thiết lập hướng di chuyển hiện tại của nhân vật là hướng lên trên
            move = Move.UP;
        }

         /*
                Kiểm tra nếu người chơi ấn phím mũi tên xuống dưới hay không
            nếu có thì thiết lập tọa độ trục y cho nhân vật giảm đi một khoảng nhất định để
            hình ảnh của nhân vật được vẽ ra màn hình sẽ ở phía dưới hình ảnh trước đó
         */
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            //giảm tọa độ trục y
            y -= STEP_DISTANCE;
             /*
                    Kiểm tra nếu hình ảnh đi xuống phía dưới của nhân vật ra khỏi mản hình thì
                thiết lập lại tọa độ trục y
            */
            if (y < 0)
                y = 0;

            // thiết lập hướng di chuyển hiện tại của nhân vật là hướng xuống phía dưới
            move = Move.DOWN;
        }

         /*
                Kiểm tra nếu người chơi ấn phím mũi tên sang trái hay không
            nếu có thì thiết lập tọa độ trục x cho nhân vật giảm đi một khoảng nhất định để
            hình ảnh của nhân vật được vẽ ra màn hình sẽ ở phía bên trái hình ảnh trước đó
         */
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            // giảm tọa độ trục x
            x -= STEP_DISTANCE;
             /*
                    Kiểm tra nếu hình ảnh đi sang bên trái của nhân vật ra khỏi mản hình thì
                thiết lập lại tọa độ trục x
            */
            if (x < -100)
                x = 0;
            // thiết lập hướng di chuyển hiện tại của nhân vật là hướng sang trái
            move = Move.LEFT;
            direction = false;
        }

        /*
                Kiểm tra nếu người chơi ấn phím mũi tên sang phải hay không
            nếu có thì thiết lập tọa độ trục x cho nhân vật tăng lên một khoảng nhất định để
            hình ảnh của nhân vật được vẽ ra màn hình sẽ ở phía bên phải hình ảnh trước đó
         */
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            // tăng tọa độ x
            x += STEP_DISTANCE;
            /*
                    Kiểm tra nếu hình ảnh đi sang bên phải của nhân vật ra khỏi mản hình thì
                thiết lập lại tọa độ trục x
            */
            if (x + WIDTH > Gdx.graphics.getWidth())
                x = Gdx.graphics.getWidth() - WIDTH + 50;
            // thiết lập hướng di chuyển hiện tại của nhân vật là hướng sang phải
            move = Move.RIGHT;
            direction = true;
        }
        // Kiểm tra điều kiện để vẽ các hình ảnh cụ thể ra màn hình
        /*
                - Nếu nhân vật không di chyển hoặc nhân vật di chuyển lên trên hoặc xuống dưới
                            + Nếu hướng di chuyển đang là qua phải (direction = true) thì sẽ vẽ playerFrameUpDownRight
                            + Nếu hướng di chuyển đang là qua trái (direction = false) thì sẽ vẽ playerFrameUpDownLeft
                - Nếu nhân vật di chuyển qua phải thì vẽ animation di chuyển qua phải của nhân vật
                - Nếu nhân vật di chuyển qua trái thì vẽ animation di chuyển qua trái quả nhân vật
            => Tất cả hình vẽ ra ở vị trí (x,y), chiều rông = WIDTH , chiều cao = HEIGHT
         */
        BATCH.draw((move == null || move == Move.UP || move == Move.DOWN) ?
                        (direction ? playerFrameUpDownRight : playerFrameUpDownLeft) :
                        (move == Move.RIGHT ?
                                (TextureRegion) playerAnimationRight.getAnimation().getKeyFrame(stateTime, true) :
                                (TextureRegion) playerAnimationLeft.getAnimation().getKeyFrame(stateTime, true))
                , x, y, WIDTH, HEIGHT);
        //vẽ hiệu ứng sấm sét
        BATCH.draw((TextureRegion) thunder.getAnimation().getKeyFrame(stateTime, true),
                x-20, y+HEIGHT/2, WIDTH, HEIGHT);
        BATCH.draw((TextureRegion) thuder1.getAnimation().getKeyFrame(stateTime, true),
                x, y, WIDTH, HEIGHT);
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

    public boolean getDirection() {
        return direction;
    }
}
