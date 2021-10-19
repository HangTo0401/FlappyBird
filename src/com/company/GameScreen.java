package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class GameScreen extends JFrame implements KeyListener, MouseListener {

    public static int KEY_PRESSED = 0;
    public static int KEY_RELEASED = 1;

    public static int MOUSE_CLICKED = 2;
    public static int MOUSE_PRESSED = 3;
    public static int MOUSE_RELEASED = 4;
    public static int MOUSE_ENTERED = 5;
    public static int MOUSE_EXITED = 6;

    public int CUSTOM_HEIGHT = 500;
    public int CUSTOM_WIDTH = 500;

    public static int MASTER_HEIGHT = 800;
    public static int MASTER_WIDTH = 800;

    private GameThread gameThread;

    public GameScreen(){
        InitThread();
        InitScreen();
    }

    public GameScreen(int width, int height){
        this.CUSTOM_WIDTH = width;
        this.CUSTOM_HEIGHT = height;
        MASTER_WIDTH = CUSTOM_WIDTH;
        MASTER_HEIGHT = CUSTOM_HEIGHT;
        InitScreen();
        InitThread();
    }

    private void InitThread(){
        gameThread = new GameThread(this);

        //Appends the specified component to the end of this container.
        add(gameThread);
    }

    private void InitScreen(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        this.addMouseListener(this);
        setSize(CUSTOM_WIDTH, CUSTOM_HEIGHT);
        setVisible(true);
    }

    public void beginGame() {
        gameThread.startThread();
    }

    //keyTyped được triệu hồi khi một key đã được gõ.
    @Override
    public void keyTyped(KeyEvent e) {

    }

    //keyPressed được triệu hồi khi một key đã được nhấn.
    @Override
    public void keyPressed(KeyEvent e) {
        KEY_ACTION(e, GameScreen.KEY_PRESSED);
    }

    //keyReleased được triệu hồi khi một key đã được nhả ra
    @Override
    public void keyReleased(KeyEvent e) {
        KEY_ACTION(e, GameScreen.KEY_RELEASED);
    }

    @Override
    public void mouseClicked(MouseEvent e ) {
        MOUSE_ACTION(e, GameScreen.MOUSE_CLICKED);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        MOUSE_ACTION(e, GameScreen.MOUSE_PRESSED);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        MOUSE_ACTION(e, GameScreen.MOUSE_RELEASED);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        MOUSE_ACTION(e, GameScreen.MOUSE_ENTERED);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        MOUSE_ACTION(e, GameScreen.MOUSE_EXITED);
    }

    public abstract void GAME_UPDATE(long deltaTime);
    public abstract void GAME_PAINT(Graphics2D g2);
    public abstract void KEY_ACTION(KeyEvent e, int Event);
    public abstract void MOUSE_ACTION(MouseEvent e, int Event);
}
