package com.company;

import com.company.BackgroundModel.FlappyBird;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class AFrameOnImage {
    private boolean enablePaintRect = false;

    // Array to store postion of bird image
    private int[] dimsBounds = new int[4];

    // One picture has 3 frame so that means xOnImage and yOnImage is position of 1 frame on width
    public AFrameOnImage(int xOnImage, int yOnImage, int width, int height){
        dimsBounds[0] = xOnImage;
        dimsBounds[1] = yOnImage;
        dimsBounds[2] = width;
        dimsBounds[3] = height;
    }

    public void paint(int x, int y, BufferedImage image, Graphics2D g2, float rotation) {
        //Returns a subimage defined by a specified rectangular region.
        BufferedImage imageDraw = image.getSubimage(dimsBounds[0], dimsBounds[1], dimsBounds[2], dimsBounds[3]);

        // Rotation information

        //AffineTransform đc dùng để thực hiện phép biến đổi hình
        AffineTransform tx = new AffineTransform();

        // Bird fly
        tx.rotate(rotation, imageDraw.getWidth() / 2, imageDraw.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        imageDraw = op.filter(imageDraw, null);

        // Drawing the rotated image at the required drawing locations
        // Draw bird fly
        g2.drawImage(imageDraw, x, y, null);

    }
}
