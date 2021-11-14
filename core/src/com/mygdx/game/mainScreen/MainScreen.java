package com.mygdx.game.mainScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MainGame;
import com.mygdx.game.mainScreen.characters.Coin;
import com.mygdx.game.mainScreen.characters.Garbage;
import com.mygdx.game.mainScreen.characters.Player;
import com.mygdx.game.mainScreen.characters.Snake;
import com.mygdx.game.mainScreen.decoration.MainScreenBg;
import com.mygdx.game.mainScreen.decoration.ScoreTable;
import com.mygdx.game.ultils.Collision;
import com.mygdx.game.mainScreen.decoration.Destruction;
import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

// Màn hình chính để chơi game
public class MainScreen implements Screen {
    public final static Random RANDOM = new Random(); // đối tượng để tạo giá trị ngẫu nhiên
    public final static int MAX_SNAKE = 10; // số lượng rắn tối đa cần bắt
    public final static int MAX_GARBAGE = 10; // số lượng bịch rác tối đa có thể hiển thị trên bản đồ
    public final static int COLLECTIVE_GARBAGE = 3; // số lượng bịch rác tối đa cần thu thập để có thể bắt rắn
    public final static int MAX_TIME = 10; // thời gian tối9 đa để bắt rắn
    private final SpriteBatch batch; // đối tượng dùng để vẽ
    private final MainScreenBg background; // background của map
    private final Vector<Snake> snakeVector;//danh sách chưa những con răn để vẽ ra màn hình
    private final Vector<Coin> coinVector;//danh sách chưa những đồng xu để vẽ ra màn hình
    private final Vector<Garbage> garbageVector;//danh sách chưa những bịch rác để vẽ ra màn hình
    private final Player player;// người chơi
    private final Destruction destruction; // hình ảnh người chơi chết
    private final Vector<Collision> dangerousAreaVector; // danh sách khu vục nguy hiểm (nước acid )
    private final ScoreTable scoreTable; // bảng điểm
    private int collectiveGarbageScore; // số lượng rác thu gom được
    private int snakeCatchingScore; // số lượng rắn bắt được
    private long preTime;//thời gian khi bắt đầu bắt rắn
    private boolean flagCatchingSnake = false; // cờ báo hiệu khi nào có thể bắt được rắn
    private boolean flagGameOver = false; // cờ báo hiệu trò chơi kết thức
    private boolean isDeadSoundStart;//cờ báo hiệu để âm thanh chết chỉ phát ra 1 lần khi chết
    private final Music bgMusic;//nhạc nền
    private final Sound swallowSound;//sound khi dọn bịch rác
    private final Sound deathSound;//sound khi chết

    public MainScreen(SpriteBatch batch) {
        //khởi tạo giá trị
        this.batch = batch;
        snakeVector = new Vector<>();
        coinVector = new Vector<>();
        garbageVector = new Vector<>();
        dangerousAreaVector = new Vector<>();
        background = new MainScreenBg(this.batch);
        player = new Player(this.batch);
        destruction = new Destruction(this.batch);
        scoreTable = new ScoreTable(this.batch);
        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("mainMusic.mp3"));
        bgMusic.setLooping(true);
        bgMusic.play();
        isDeadSoundStart = true;
        swallowSound= Gdx.audio.newSound(Gdx.files.internal("swallow.mp3"));
        deathSound= Gdx.audio.newSound(Gdx.files.internal("death.mp3"));

        //lấy dữ liệu từ map và thiết lập danh sách nhựng vùng nguy hiểm (ô nước acid)
        int[][] map = MainScreenBg.getMAP();
        for (int i = 0; i < Arrays.stream(map).toArray().length; i++)
            for (int j = 0; j < Arrays.stream(map[i]).toArray().length; j++)
                if (map[i][j] == 0) // kiểm tra xem ô đó có phải cùng nước acid hay không
                    dangerousAreaVector.add(new Collision(
                            j * MainScreenBg.getStepsX(),
                            i * MainScreenBg.getStepsY(),
                            MainScreenBg.getStepsX(),
                            MainScreenBg.getStepsY())); // tạo vùng va chạm của ô đó bằng kích thước của ô

    }

    @Override
    public void render(float delta) {
        long nextTime = System.currentTimeMillis(); // thiết lập thời gian để tính thời gian có thể bắt rắn
        Vector<Coin> coinRemovedBin = new Vector<>(); // Tạo thùng chứa các dồng tiền k dùng nữa
        Vector<Snake> snakeRemovedBin = new Vector<>();    // Tạo danh sách các con rắn không dùng nữa
        Vector<Garbage> garbageRemovedBin = new Vector<>();    // Tạo danh sách các bịch rác không dùng nữa
        Snake.SPAWN_TIME -= Gdx.graphics.getDeltaTime(); // Thiết lập thời gian xuất hiện của 1 con rắn mới
        Coin.SPAWN_TIME -= Gdx.graphics.getDeltaTime(); // hiết lập thời gian xuất hiện của 1 con rắn mới

        //Tạo một vùng biên va chạm cho nhân vật của người chơi
        Collision playerCollision = new Collision(
                player.getX() + (player.getDirection() ? Player.getWIDTH() / 5 : Player.getWIDTH() - Player.getWIDTH() / 5),
                player.getY(),
                5,
                10);

        // kiểm tra xem đến thời gian sinh thêm đồng xu mới trên map hay chưa, nếu < 0 (đến thời gian) thì sinh ra
        // 1 đồng tiền mới và đưa vào danh sách những đồng xu cần vẽ
        if (Coin.SPAWN_TIME < 0) {
            Coin.SPAWN_TIME = MainScreen.RANDOM.nextInt(2) + 2;
            Coin coin = new Coin(this.batch);
            coinVector.add(coin);
            // khi sinh đồng xu mới thi kiểm tra xem số lượng rắn trên màn hình có vượt quá số lượng tối đa cho phép
            // hay chưa, nếu chưa thì sinh ra một con rắn với hướng đi sao cho gần đồng xu nhất, rồi thêm vào danh sách
            // để hiển thi rắn
            if (snakeVector.size() <= MAX_SNAKE) {
                Snake snake = new Snake(this.batch,
                        coin.getX() < (float) Gdx.graphics.getWidth() / 2 ? "snakeRight.png" : "snakeLeft.png");
                snake.setY(coin.getY() - (MainScreenBg.getStepsY() - (float) Coin.getHEIGHT()) / 2);
                snakeVector.add(snake);
            }
        }

		/*
		 		Kiểm tra sau một khoảng thời gian nhất định thì tạo một con rắn mới và thêm nó vào danh sách
		 	những con rắn cần hiển thị ra màn hình
		 */
        if (Snake.SPAWN_TIME < 0 && snakeVector.size() <= MAX_SNAKE) {
			/*
			 	- Thiết lập tại thời gian để sản sinh ra 1 con rắn tiếp theo
			 	- Thời gian sản sinh sẽ là 1 giá trị ngẫu nhiên nằm trong khoảng (3->5) giây
			 */
            Snake.SPAWN_TIME = MainScreen.RANDOM.nextInt(10) + 10;
			/*
				- Thêm con rắn mới sinh ra vào danh sách rắn cần hiển thị
				- Hướng di chuyển của rắn (trái -> phải) hoặc (phải -> trái) sẽ được chọn 1 cách ngẫu nhiên
				- Nếu số ngẫu nhiên sinh ra là 1 số chẵn thì hướng sẽ là trái -> phải hoặc ngược lại

			 */
            snakeVector.add(new Snake(this.batch, RANDOM.nextInt() % 2 == 0 ? "snakeRight.png" : "snakeLeft.png"));
        }

		/*
		 	- Thêm những đồng tiền đã tồn tại quá thời giàn quy định trêp bản đồ vào thùng chứa
			- Thêm chững đồng tiền mà rắn đã ăn vào thùng chứa
		 */
        coinVector.forEach(e -> {
            if (e.getTimeExist() <= 0)
                coinRemovedBin.add(e);
        });


        // Thêm những con rắn đã di chuyển ra khỏi phạm vi bản đồ vào thùng chứa
        snakeVector.forEach(e -> {
            if (e.getX() < -250 || e.getX() > Gdx.graphics.getWidth())
                snakeRemovedBin.add(e);
        });

         /*
                Duyệt danh sách những vùng nguy hiểm (những vùng có dung nham trên bản đồ), nếu nhân vật va chạm với
              vùng đó thì đặt cờ báo hiệu game over thành true và thiết lập tọa độ hoạt ảnh nhân vật mình chết tại
              vị trí của nhân vật
         */
        dangerousAreaVector.forEach(e -> {
            if (playerCollision.checkCollision(e) || e.checkCollision(playerCollision))
                flagGameOver = true;
        });

        /*
                Kiểm tra sự va chạm giữa đồng xu và con rắn bằng cách8 duyệt từng đồng xu trong danh sách đồng xu dùng
             để hiển thị ra màn hình. Với mỗi đồng xu ta kiểm tra va chạm giữa nó và từng còn rắn trang danh sách rắn
             hiện có của chúng ta. Nếu 2 cái có sự va chạm với nhau thì ta sẽ thêm đồng xu đó vào danh sách cá đồng xu
             cần xóa và sinh ra một bịch rác ngay tại vị trí của đồng xu đó
         */
        coinVector.forEach(coin -> {
            Collision coinCollision = new Collision(coin.getX(), coin.getY(), Coin.getWIDTH(), Coin.getHEIGHT());
            snakeVector.forEach(snake -> {
                Collision snakeCollision = new Collision(snake.getX(), snake.getY(), Snake.getWIDTH(), Snake.getHEIGHT());
                if ((coinCollision.checkCollision(snakeCollision) || snakeCollision.checkCollision(coinCollision)) &&
                        !coinRemovedBin.contains(coin)) {
                    coinRemovedBin.add(coin);
                    garbageVector.add(new Garbage(this.batch, coin.getX(), coin.getY()));
                }

            });
        });

        /*
                - Thiết lập vùng biên va chạm của nhân vật để tăng độ chính xác hơn
                - Duyệt danh sách các bịch rác hiện có trên bản đồ, và kiểm tra va chạm giữa nó và người chơi
                Nếu người chơi và bịch rác có sự va chạm thì ta sẽ thêm bịch rác đó vào danh sách các bịch rác cần
              xóa.
                - Tiếp đó kiểm tra xem cờ bào hiệu người chơi có được bắt rắn đã bật hay chưa và số lượng bịch rác thu thập
              có nhỏ hơn bằng số lượng bịch rác tối đa có thể thu thập hay không. Nếu có thì tiếp tục tăng điểm thu thập
              của bịch rác lên
                - Nếu trường hợp mà số lượng bịch rác đã thu thập được bằng số lượng bịch rác tối đa có thể thu thập thì
                    + Thiết lập thời gian bắt đầu có thể bắt rắn
                    + reset lại số bịch rác có thể thu thập của người chơi
                    + bật cờ có thể bắt rắn lên thành true
         */
        playerCollision.setWidth(20);
        playerCollision.setHeight(40);
        garbageVector.forEach(e -> {
            Collision garbageCollision = new Collision(e.getX(), e.getY(), Garbage.getWIDTH(), Garbage.getHEIGHT());
            if (playerCollision.checkCollision(garbageCollision) || garbageCollision.checkCollision(playerCollision)) {
                garbageRemovedBin.add(e);
                if (!flagCatchingSnake && collectiveGarbageScore <= COLLECTIVE_GARBAGE)
                    collectiveGarbageScore++;
                if (collectiveGarbageScore == COLLECTIVE_GARBAGE) {
                    preTime = System.currentTimeMillis();
                    collectiveGarbageScore = 0;
                    flagCatchingSnake = true;
                }
                //âm thanh nhai nuốt
               swallowSound.play(1.0f);
            }
        });

        /*
                Nếu người chơi có thể bắt rắn (cờ có thể bắt rắn bật thành true) thì ta thết lập lại vùng va chạm
              của nhân vật người chơi, sau đó duyệt từng con rắn hiện có trên bản đồ và kiểm tra va chạm giữa nó và
              người chơi
                nếu có sự va chạm thì ta thêm con rắn đó vào danh sách các con rắn cần xóa và tăng số lượng con rắn
              bắt được của người chơi lên 1
         */
        if (flagCatchingSnake) {
            playerCollision.setX(player.getX() + 15);
            playerCollision.setY(player.getY() + 15);
            playerCollision.setWidth(Player.getWIDTH() - 30);
            playerCollision.setHeight(Player.getHEIGHT() - 3);
            snakeVector.forEach(e -> {
                Collision snakeCollision = new Collision(
                        e.getX() + 10,
                        e.getY() - 10,
                        Snake.getWIDTH() - 20,
                        Snake.getHEIGHT() - 20);
                if (playerCollision.checkCollision(snakeCollision) ||
                        snakeCollision.checkCollision(playerCollision)) {
                    snakeRemovedBin.add(e);
                    snakeCatchingScore++;
                    //âm thanh nhai nuốt
                    swallowSound.play(1.0f);
                }
            });
        }

		/*
				kiếm tra nếu thùng chứa không rỗng thì xóa tất cả các đồng tiền mà nó giống với
		 	những đồng tiền trong danh các đồng tiền dùng để giữ hiển thị trên bản đồ
		 */
        if (!coinRemovedBin.isEmpty())
            coinVector.removeAll(coinRemovedBin);

		/*
				kiếm tra nếu thùng chứa không rỗng thì xóa tất cả các đồng tiền mà nó giống với
			những đồng tiền trong danh các đồng tiền dùng để giữ hiển thị trên bản đồ
		 */
        if (!snakeRemovedBin.isEmpty())
            snakeVector.removeAll(snakeRemovedBin);
        /*
				kiếm tra nếu thùng chứa không rỗng thì xóa tất cả các bịch rác mà nó giống với
			những bịch rác trong danh các bịch rác dùng để giữ hiển thị trên bản đồ
		 */
        if (!garbageRemovedBin.isEmpty())
            garbageVector.removeAll(garbageRemovedBin);


        // cập nhật các giá trị của bảng diểm
        scoreTable.updateValues(
                snakeCatchingScore, // số rắn bắt được
                collectiveGarbageScore, // số rác gom được
                garbageVector.size(),// số rác hiện có trên bản đồ
                /*
                        - thời gian có thể bắt rắn
                        - khi cờ báo hiệu có thể bắt rắn ( flagCatchingSnake = true ) bật thì bắt đầu tính thời gian
                        - thời gian sẽ được tính bằng thời gian hiện tại - thời gian lúc bắt đầu bật cờ có thể bắt rắn
                     ta muốn giới hạn thời gian bắt rắn là MAX_TIME nên ta lấy phần dư của khoảng thời gian vừa tính được
                     với ((MAX_TIME + 1)  * 1000) mili giậy vì (do đơn vị tính của hệ thống là mili giây nên
                     ta có 1s = 1000 mili giây )tiếp tục ta chia cho 1000 để chuyển để chuyển lại thành giây
                        - Nếu k có báo hiệu có thể bắt rắn (flagCatchingSnake = false) thì thiết lập thời gian =0
                 */
                flagCatchingSnake ? Math.abs((int) (nextTime - preTime) % ((MAX_TIME + 1) * 1000)) / 1000 : 0);

        // nếu thơi gian bắt rắn bằng MAXTIME thì thiết lập lại cờ báo hiệu để k thể bắt rắn nữa, phải chờ cho
        // đến khi nhân vật thu gom đủ 1 số lượng bịch rác nhất định
        if (Math.abs((int) (nextTime - preTime) % ((MAX_TIME + 1) * 1000)) / 1000 == MAX_TIME)
            flagCatchingSnake = false;

        // nếu số lượng bắt được đạt đủ yêu cầu9 hoạc9 số lượng bịch rác trên bản đồ đạt tối đa thì bật cờ kết thúc game
        if (snakeCatchingScore == MAX_SNAKE || garbageVector.size() == MAX_GARBAGE)
                flagGameOver=true;
        //Thiết lập vị trí animation tự hủy
        if(flagGameOver) {
            destruction.setX(player.getX());
            destruction.setY(player.getY());
            MainGame.IS_WIN= snakeCatchingScore==MAX_SNAKE;
            //âm thanh khi chết
            if(isDeadSoundStart)
                deathSound.play(1.0f);
            isDeadSoundStart =false;
        }
        // Hiển thi hình ảnh ra màn hình
        batch.begin();
        // xử lí khi game chưa kết thúc
        if (!flagGameOver) {
            background.render();
            coinVector.forEach(Coin::render);
            garbageVector.forEach(Garbage::render);
            snakeVector.forEach(Snake::render);
            player.render();
            scoreTable.render();
        } else { // xử lí khi game kết thúc
            background.render();
            coinVector.forEach(Coin::render);
            destruction.render();
            garbageVector.forEach(Garbage::render);
            snakeVector.forEach(Snake::render);
            batch.draw(new Texture("pressContinue.png"),
                    (float)(Gdx.graphics.getWidth()-300)/2,
                    (float)(Gdx.graphics.getHeight()-100)/2,
                    300,100);
            //khi người chơi bấm escape
            if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                MainGame.MODE = MainGame.Mode.END;
                MainGame.flagChangeScreen= false;
                bgMusic.stop();
                this.dispose();
            }
        }
        batch.end();

    }

    // hủy các thứ không cần dùng tới
    @Override
    public void dispose() {
        background.dispose();
        bgMusic.dispose();
        swallowSound.dispose();
        deathSound.dispose();
    }

    @Override
    public void show() {
        // do nothing
    }


    @Override
    public void resize(int width, int height) {
        // do nothing
    }

    @Override
    public void pause() {
        // do nothing
    }

    @Override
    public void resume() {
        // do nothing
    }

    @Override
    public void hide() {
        // do nothing
    }


}
