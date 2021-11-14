package com.mygdx.game.ultils;

//Lớp này nhằm tạo viền hình chữ nhật của vật thể và kiểm tra xem 2 vật thể có va cham với nhau hay không
public class Collision {
    private float x; // tọa độ x
    private float y;// tọa độ y
    private float width;// chiều rộng
    private float height;// chiều cao

    //khởi tạo giá trị
    public Collision(float x, float y, float width, float height) {
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
    }


    // kiểm tra va chạm giữa 2 vật thể
    // Tham khảo rõ hơn tại core/assets/collisionCondition.png
    public boolean checkCollision(Collision collision) {
        return (this.y < collision.y && this.y + this.height > collision.y ||
                this.y > collision.y && this.y < collision.y + collision.height) &&
                (this.x < collision.x && this.x + this.width > collision.x &&
                        this.x > collision.x && this.x < collision.x + collision.width) ||
                this.y <= collision.y && this.y + this.height >= collision.y + collision.height &&
                        (this.x < collision.x && this.x + this.width > collision.x ||
                                this.x > collision.x && this.x < collision.x + collision.width) ||
                this.x <= collision.x && this.x + this.width >= collision.x + collision.width &&
                        (this.y < collision.y && this.y + this.height > collision.y ||
                                this.y > collision.y && this.y < collision.y + collision.height) ||
                this.x <= collision.x && this.x + this.width >= collision.x + collision.width &&
                        this.y <= collision.y && this.y + this.height >= collision.y + collision.height;
    }

    /**
     * ******* Các getter, setter cần dùng đến ************
     */

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
