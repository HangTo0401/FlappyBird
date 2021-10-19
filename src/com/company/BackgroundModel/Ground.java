package com.company.BackgroundModel;

import com.company.Objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Ground extends Objects {
    private BufferedImage groundImage;

    //First ground frame
    private int x1, y1;

    //Second ground frame
    private int x2, y2;

    public Ground(int x1, int y1) {
        try {
            groundImage = ImageIO.read(new File("Assets/ground.png"));
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = this.x1 + 830;
            this.y2 = y1;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void update(){
        // Decrease x to make ground moving ground from right to left
        this.x1 = this.x1 - 1;
        this.x2 = this.x2 -1;

        // How to make background keep running?

        // C1: Cách này chỉ áp dụng được khi ta chỉ có 2 image
        // If first ground image is out of screen, then make first ground image is behind second ground image and vice versa
        // Khi frame 1 chạy hết màn hình thì update frame 1 đằng sau frame 2
        if (x1 < 0) {
            // 830 is width of ground.png
            this.x2 = this.x1 + 830;
        }
        if (x2 < 0) {
            // 830 is width of ground.png
            this.x1 = this.x2 + 830;
        }
    }

    public void paint(Graphics2D graphics2D) {
        //Draw first ground image
        graphics2D.drawImage(groundImage, this.x1, this.y1, null);

        //Draw second ground image
        graphics2D.drawImage(groundImage, this.x2, this.y2, null);
    }

    // width of ground image
    public int getXGround() {
        return x1;
    }

    // height of ground image
    public int getYGround() {
        return y1;
    }
}
