package com.company;

public class Objects {
    private float posX, posY;
    private float width, height;

    public Objects(){
        posX = posY = width = height = 0;
    }

    // x and y is position of object
    // width and height is rectangle wrapper bounds for object
    public Objects(float x, float y, float width, float height){
        this.posX = x;
        this.posY = y;
        this.width = width;
        this.height = height;
    }

//    public boolean isCollisionHappenWith(float x, float y){
//        if(x > posX && x < posX + width && y > posY && y < posY + height)
//            return true;
//        return false;
//    }
//
//    public boolean isCollisionHappenWith(float x, float y, float width, float height){
//        if(x < posX + this.width && x + width > posX && y < posY + this.height && height + y > posY)
//            return true;
//        return false;
//    }

    public void setPos(float x, float y){
        posX = x;
        posY = y;
    }
    
    public void setPosX(float posX) {
        this.posX = posX;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void increasePosX(float m){
        posX+=m;
    }

    public void increasePosY(float m){
        posY+=m;
    }
}
