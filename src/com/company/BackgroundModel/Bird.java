package com.company.BackgroundModel;

import com.company.Objects;
import com.company.SoundPlayer;

import java.awt.*;
import java.io.File;

//wrapper class of flappy bird
public class Bird extends Objects {

    private float falling_speed = 0;

    //Rectangle wrapper for bird object
    private Rectangle rect;

    // Flag to check bird is flying or not
    private boolean isFlying = false;

    // Flag to check bird is alive. If bird touch the bounds, then bird is dead
    private boolean isAlive = true;

    public SoundPlayer flapSound, getScoreSound, fallSound;

    public Bird(int x, int y, int width, int height) {
        super(x, y, width, height);

        //Create rectangle for bird object
        rect = new Rectangle(x, y, width, height);

        this.flapSound = new SoundPlayer(new File("Assets/fap.wav"));
        this.fallSound = new SoundPlayer(new File("Assets/fall.wav"));
        this.getScoreSound = new SoundPlayer(new File("Assets/getpoint.wav"));
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public boolean getAlive() {
        return isAlive;
    }

    // Bird is falling
    public void update(){
        //When bird is fall then falling speed will increase
        falling_speed += FlappyBird.gravity;

        // the distance when bird fly will change by accelerator
        this.setPosY(this.getPosY() + falling_speed);

        //Update position of bird when bird flies
        this.rect.setLocation((int) this.getPosX(), (int) this.getPosY());

        if(falling_speed < 0) isFlying = true;
        else isFlying = false;
    }

    public void setFallingSpeed(float resetSpeed){
        this.falling_speed = resetSpeed;
    }

    // Bird is flying
    public void fly(){
        // the bird is fly up
        falling_speed = -3;
        this.flapSound.playSound();
    }

    public boolean getIsFlyingFlag(){
        return isFlying;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public Rectangle getRect() {
        return rect;
    }
}
