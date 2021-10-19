package com.company.BackgroundModel;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.company.Animation;
import com.company.GameScreen;
import com.company.AFrameOnImage;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class FlappyBird extends GameScreen {
    private BufferedImage birds;
    private BufferedImage startImage;
    private BufferedImage gameOverImage;
    private BufferedImage backgroundImage;

    //Init animation
    private Animation flappy_bird_animation;

    //Init bird
    private Bird bird;

    //Init ground
    private Ground ground;

    //Init chimney group
    private ChimneyGroup chimneyGroup;

    //Begin screen
    public int BEGIN_SCREEN = 0;

    //Exit screen
    public int GAMEOVER_SCREEN = 1;

    //Game play screen
    public int GAMEPLAY_SCREEN = 2;

    //Current screen
    public int CURRENT_SCREEN = BEGIN_SCREEN;

    //When bird is fall, we will increase falling speed with accelerator
    public static float gravity = 0.15f;

    //Position of start image
    private int startX, startY;

    //Score
    public int playerScore = 0;

    public int increase_speed = 0;

    public FlappyBird() {
        // set width and height for window
        super(800, 800);

        try {
            birds = ImageIO.read(new File("Assets/bird.png"));
            startImage = ImageIO.read(new File("Assets/start.png"));
            gameOverImage = ImageIO.read(new File("Assets/gameover.jpg"));
            backgroundImage = ImageIO.read(new File("Assets/sky.jpg"));

            //Position of start image
            startX = (this.getWidth() - startImage.getWidth(null)) / 2;
            startY = (this.getHeight() - startImage.getHeight(null)) / 2;
        } catch(IOException e) {
            e.printStackTrace();
        }

        //Create flappy bird animation
        AFrameOnImage f;
        flappy_bird_animation = new Animation(70);

        //Why we have 3 frames cause flappy bird will move from frame 1->2->3 then move back 3->2->1
        //It will make image more clear
        //Init and add first frame in big image
        f = new AFrameOnImage(0, 0, 60, 60);
        flappy_bird_animation.addFrame(f);

        //Second frame
        f = new AFrameOnImage(60, 0, 60, 60);
        flappy_bird_animation.addFrame(f);

        //Third frame
        f = new AFrameOnImage(120, 0, 60, 60);
        flappy_bird_animation.addFrame(f);

        bird = new Bird(250,250,50,50);
        ground = new Ground(0, 700);
        chimneyGroup = new ChimneyGroup();
        beginGame();
    }

    public static void main(String[] args) {
	    // write your code here
        new FlappyBird();
    }

    // update image for game. It will auto called continuously
    @Override
    public void GAME_UPDATE(long deltaTime) {
        if (CURRENT_SCREEN == BEGIN_SCREEN) {
            resetGame();
        } else if (CURRENT_SCREEN == GAMEPLAY_SCREEN) {
            if(bird.getAlive()) {
                flappy_bird_animation.updateFrame(deltaTime);
            }
            bird.update();
            ground.update();

            //Check collision between bird and chimney
            for (int i=0; i < chimneyGroup.NUMOFCHIMNEY; i++) {
                if (bird.getRect().intersects(chimneyGroup.getChimney(i).getRect())) {
                    if (bird.getAlive() == true) {
                        bird.fallSound.playSound();
                    }
                    //Bird is NOT alive. Stop animation on bird
                    bird.setAlive(false);
                }
            }

            //Increase player score when bird is fly in front of each chimney
            for (int i=0; i < chimneyGroup.NUMOFCHIMNEY; i++) {
                //Check position X of bird if its position higher than chimney then chimney is behind bird
                if(bird.getPosX() > chimneyGroup.getChimney(i).getPosX() && !chimneyGroup.getChimney(i).getIsBehindBird() && i%2==0){
                    //Increase score
                    playerScore++;
                    bird.getScoreSound.playSound();
                    chimneyGroup.getChimney(i).setBehindBird(true);
                }
            }

            chimneyGroup.update(this.playerScore);

            // Check bird fall to ground
            // If height of rectangle wrapper of bird is higher than height of ground
            if (bird.getPosY() + bird.getHeight() > ground.getYGround()) {
                CURRENT_SCREEN = GAMEOVER_SCREEN;
            }
        } else {
            //Game over do nothing
        }

    }

    public void resetGame() {
        this.playerScore = 0;
        bird.setPos(250,250);
        bird.setFallingSpeed(0);
        bird.setAlive(true);
        chimneyGroup.resetChimneys();
    }

    // draw animation component for game. It will auto called continuously
    @Override
    public void GAME_PAINT(Graphics2D graphics2D) {
        if (CURRENT_SCREEN == BEGIN_SCREEN) {
            graphics2D.drawImage(startImage, startX, startY, null);
        } else if (CURRENT_SCREEN == GAMEOVER_SCREEN) {
            graphics2D.drawImage(gameOverImage, 0, 120, null);
            graphics2D.setFont(new Font("default", Font.BOLD, 16));
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString("PRESS SPACE TO PLAY AGAIN", 300, 450);
            return;
        } else {
            graphics2D.drawImage(backgroundImage, 0, -200, null);
            chimneyGroup.paint(graphics2D);

            Font font = new Font("default", Font.BOLD, 25);
            graphics2D.setColor(Color.RED);
            graphics2D.setFont(font);
            graphics2D.drawString("PLAYER SCORE: " + this.playerScore, 0, 20);

            if (bird.getIsFlyingFlag()) {
                flappy_bird_animation.paintAnims((int) bird.getPosX(), (int) bird.getPosY(), birds, graphics2D, -1);
            } else {
                flappy_bird_animation.paintAnims((int) bird.getPosX(), (int) bird.getPosY(), birds, graphics2D, 0);
            }
        }
        ground.paint(graphics2D);
    }

    // get event from keyboard continuously
    @Override
    public void KEY_ACTION(KeyEvent e, int Event) {
        if (Event == KEY_PRESSED) {

            if (CURRENT_SCREEN == GAMEPLAY_SCREEN) {
                // When user press any on keyboard then bird is flying
                if(bird.getAlive()) {
                    bird.fly();
                }
            } else {
                //Game over do nothing
                CURRENT_SCREEN = BEGIN_SCREEN;
            }

        }

    }

    // get event from mouse continuously
    @Override
    public void MOUSE_ACTION(MouseEvent e, int Event) {
        if (Event == MOUSE_CLICKED) {
            Point point = e.getPoint();
            Rectangle startImageBounds = new Rectangle(startX, startY,400, 150);

            if (startImageBounds.contains(point)){
                //point is inside given image
                if (CURRENT_SCREEN == BEGIN_SCREEN) {
                    CURRENT_SCREEN = GAMEPLAY_SCREEN;
                }
            }
        }
    }
}
