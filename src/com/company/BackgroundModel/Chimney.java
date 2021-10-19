package com.company.BackgroundModel;

import com.company.Objects;

import java.awt.*;

public class Chimney extends Objects {
    private Rectangle rect;

    //Check chimney is behind bird or not
    private boolean isBehindBird = false;

    public Chimney(int x, int y, int width, int height) {
        super(x, y, width, height);

        if (y < 0) {
            this.rect = new Rectangle(x, y - 100, width, 410);
        } else {
            this.rect = new Rectangle(x, y , width, height);
        }
    }

    float i = 1;
    public void update(int player_score) {
        //Decrease x to make chimney moving from right to left same as ground
        if (player_score % 5 == 0) {
            i+= 0.1;
        }
        this.setPosX(this.getPosX() - i);

        //Update position of chimney when chimney moves
        this.rect.setLocation((int) this.getPosX(), (int) this.getPosY());
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public Rectangle getRect() {
        return rect;
    }

    public boolean getIsBehindBird() {
        return this.isBehindBird;
    }

    public void setBehindBird(boolean behindBird) {
        this.isBehindBird = behindBird;
    }
}
