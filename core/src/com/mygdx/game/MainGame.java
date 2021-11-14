package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.endScreen.EndScreen;
import com.mygdx.game.mainScreen.MainScreen;
import com.mygdx.game.startScreen.StartScreen;


/*
	- Lớp này có nhiệm vụ tạo vòng lặp game chính vô tận
	- Lớp này kế thừa từ lớp Game của thư viện libGdx
 */
public class MainGame extends Game {
	// Đối tượng dùng để vẽ các hình ảnh ra màn hình
	public SpriteBatch batch;
	public static Mode MODE;
	public static boolean flagChangeScreen ;
	public static boolean IS_WIN;

	public enum Mode {
		START,
		MAIN,
		END
	}

	/*
			- Phương thức có chức năng khởi gán các dữ liệu ban đầu trước khi bắt đầu game
		 Ở đây ta khởi tạo các thuộc tính của lớp, thiết lập thứ tự xuất hiện các màn hình
		 (bắt đầu, màn hình chính, kết thúc) cho game
	 */
	@Override
	public void create() {
		this.batch = new SpriteBatch(); // Khởi tạo đối tượng dùng để vẽ
		MODE = Mode.START;
		flagChangeScreen=true;
		//this.setScreen(new StartScreen(this.batch));//thiết lập màn hình bắt đầu là màn hình hiển thị hiện tại
		this.setScreen(new StartScreen(this.batch));
	}

	/*
		- Phương thức này có nhiệm vụ xử lí và vẽ các hình ảnh ra màn hinh
		- Nó sẽ chạy trong một vòng lặp vô tận, liên tục vẽ các điểm ảnh ra màn hinh
	 */
	@Override
	public void render() {

		/*
				Gọi phương thức reder() của lớp cha vì lớp cha nó đã xử lí các phương thức
			 của đối tượng Screen mà ta dùng làm màn hình hiển thị hiện tại
		*/
		super.render();
		//thiết lập màn hình chơi game chính là màn hình hiển thị hiện tại
		if (MODE == Mode.MAIN && !flagChangeScreen) {
			this.setScreen(new MainScreen(this.batch));
			flagChangeScreen = true;
		}
		//thiết lập màn hình kết thúc là màn hình hiển thị hiện tại
		if (MODE == Mode.END && !flagChangeScreen) {
			this.setScreen(new EndScreen(this.batch));
			flagChangeScreen = true;
		}
		//thiết lập màn hình bắt đầu là màn hình hiển thị hiện tại
		if (MODE == Mode.START && !flagChangeScreen) {
			this.setScreen(new StartScreen(this.batch));
			flagChangeScreen = true;
		}
	}

	/*
			Phương thức này có nhiệm vụ hủy những đối tượng k cần dùng đến nữa
		nhằm tránh lãng phí bộ nhớ.
	 */
	@Override
	public void dispose() {
		/*
				Gọi phương thức dispose() của lớp cha vì lớp cha nó đã xử lí các phương thức
			 của đối tượng Screen mà ta dùng làm màn hình hiển thị hiện tại
		*/
		super.dispose();
	}
}
