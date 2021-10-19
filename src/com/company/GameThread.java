package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

// JPanel is a container that can store a group of components
public class GameThread extends JPanel implements Runnable {
    private GameScreen context;
    private Thread thread;
    public static int FPS = 70;

    //Lớp BufferedImage là lớp chuyên để làm việc với ảnh, lớp này lưu một mảng 2 chiều chứa thông tin của từng pixel trong ảnh.
    //The BufferedImage subclass describes an Image with an accessible buffer of image data
    private BufferedImage bufferedImage;

    public static float scaleX_ = 1, scaleY_ = 1;
    private int masterWidth, masterHeight;

    public GameThread(GameScreen context) {
        this.context = context;
        this.masterWidth = context.CUSTOM_WIDTH;
        this.masterHeight = context.CUSTOM_HEIGHT;
        this.thread = new Thread(this);
    }

    public void startThread() {
        thread.start();
    }

    // Paints the component to the specified graphics context.
    // This method is called when the contents of the component should be painted
    public void paint(Graphics graphics) {
        graphics.setColor(Color.WHITE);

        //fillRect được sử dụng để điền màu mặc định và độ rộng và chiều cao đã cho vào hình chữ nhật.
        graphics.fillRect(0, 0, context.CUSTOM_WIDTH, context.CUSTOM_HEIGHT);

        Graphics2D graphics2D = (Graphics2D) graphics;

        if(bufferedImage != null) {
            graphics2D.scale(scaleX_, scaleY_);

            // draw an image on the JPanel
            graphics2D.drawImage(bufferedImage, 0, 0, this);
        }
    }

    private void updateSize() {
        if (this.getWidth() <= 0) return;

        context.CUSTOM_WIDTH = this.getWidth();
        context.CUSTOM_HEIGHT = this.getHeight();

        scaleX_ = (float) context.CUSTOM_WIDTH / masterWidth;
        scaleY_ = (float) context.CUSTOM_HEIGHT / masterHeight;
    }

    @Override
    public void run() {
        long T = 1000/FPS;
        long TimeBuffer = T/2;

        long beginTime = System.currentTimeMillis();
        long endTime;
        long sleepTime;

        while(true) {
            updateSize();

            context.GAME_UPDATE(System.currentTimeMillis());
            try {
                bufferedImage = new BufferedImage(masterWidth, masterHeight, BufferedImage.TYPE_INT_ARGB);
                if(bufferedImage == null) return;

                // Returns Graphics2D, which can be used to draw into this image.
                Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();

                if(graphics2D != null){
                    // Paint the specific area that we draw
                    context.GAME_PAINT(graphics2D);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Repaints this component
            repaint();

            // Returns the current time in milliseconds
            endTime = System.currentTimeMillis();
            sleepTime = T - (endTime - beginTime);

            if(sleepTime < 0) sleepTime = TimeBuffer;

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {}

            // Returns the current time in milliseconds
            beginTime = System.currentTimeMillis();
        }
    }
}
