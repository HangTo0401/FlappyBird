package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Animation {
    private long beginTime = 0;

    // Time in miliseconds that one frame exist
    private long measure = 20;

    private AFrameOnImage[] frames;
    private int NumOfFrame = 0;
    private int CurrentFrame = 0;

    public Animation(long measure){
        this.measure = measure;
    }

    public void updateFrame(long deltaTime){
        if (NumOfFrame > 0) {
            if (deltaTime - beginTime > measure) {
                // Sau 1 khoảng tgian, số frame sẽ tự tăng lên từ 0,1,2,3...
                CurrentFrame++;
                if(CurrentFrame >= NumOfFrame) {
                    CurrentFrame = 0;
                }

                beginTime = deltaTime;
            }
        }
    }

    public void addFrame(AFrameOnImage newFrame) {
        // Init new frame arrays
        AFrameOnImage[] bufferFrames = frames;
        frames = new AFrameOnImage[NumOfFrame+1];
        for (int i = 0; i < NumOfFrame; i++) {
            frames[i] = bufferFrames[i];
        }

        frames[NumOfFrame] = newFrame;
        NumOfFrame++;
    }

    // x and y are position
    // image is bird image
    // rotation of image
    public void paintAnims(int x, int y, BufferedImage image, Graphics2D g2, float rotation){
        frames[CurrentFrame].paint(x, y, image, g2, rotation);
    }
}
