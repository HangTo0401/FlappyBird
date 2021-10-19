package com.company.BackgroundModel;

import com.company.QueueList;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

//Make images keep moving continuously
public class ChimneyGroup {
    private QueueList<Chimney> chimneyQueueList = new QueueList<>();
    private BufferedImage chimneyImageUp;
    private BufferedImage chimneyImageDown;

    //Number of chimneys
    public static int NUMOFCHIMNEY = 6;

    //Player score
    public static int increase_speed = 1;

    //Top chimney
    public static int topChimney = -200;

    //Bottom chimney
    public static int bottomChimney = 450;

    public ChimneyGroup() {

        try {
            chimneyImageUp = ImageIO.read(new File("Assets/chimney.png"));
            chimneyImageDown = ImageIO.read(new File("Assets/chimney_.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.resetChimneys();
    }

    public Chimney getChimney(int id) {
        return chimneyQueueList.get(id);
    }

    public void resetChimneys() {
        int randomY = this.getRandomY();
        chimneyQueueList = new QueueList<>();

        Chimney chimneyObject;
        //Create 3 couples of chimney
        for(int i=0; i < NUMOFCHIMNEY/2; i++) {
            //Ống khói nằm trên mặt đất
            chimneyObject = new Chimney(700+i*300, bottomChimney + randomY, 74, 200);
            chimneyQueueList.push(chimneyObject);

            //Ống khói nằm từ trên xuống
            chimneyObject = new Chimney(700+i*300, topChimney + randomY, 74, 300);
            chimneyQueueList.push(chimneyObject);
        }
    }

    public void update(int player_score) {
        int randomY = this.getRandomY();

        for(int i=0; i < NUMOFCHIMNEY;i++) {
            //Make chimney move from right to left
            chimneyQueueList.get(i).update(player_score);
        }

        //Update chimney continuously
        if (chimneyQueueList.get(0).getPosX() < -74) {

            //This chimney is running out of screen
            Chimney chimneyObject;
            chimneyObject = chimneyQueueList.pop();

            //Set position for the first couple of chimney and set it behind the last chimney in group
            //Lúc này chimney thứ 5 sẽ cách chimney thứ 4 vì chúng là ống khói quay lên
            chimneyObject.setPosX(chimneyQueueList.get(4).getPosX() + 300);
            chimneyObject.setPosY(bottomChimney + randomY);
            chimneyObject.setBehindBird(false);
            chimneyQueueList.push(chimneyObject);

            //This chimney is running out of screen
            chimneyObject = chimneyQueueList.pop();

            //Set position for the first couple of chimney and set it behind the last chimney in group
            //posX lúc này chính là cặp đối xứng của chimney quay lên nên ta k cần cộng 300 vì nó là ống khói quay xuống
            chimneyObject.setPosX(chimneyQueueList.get(4).getPosX());
            chimneyObject.setPosY(topChimney + randomY);
            chimneyObject.setBehindBird(false);
            chimneyQueueList.push(chimneyObject);
        }

    }

    public void paint(Graphics2D graphics2D) {
        //Draw all chimney images
        for(int i=0; i < NUMOFCHIMNEY;i++) {
            if (i % 2 == 0) {
                //Ống khói quay lên
                graphics2D.setColor(Color.WHITE);
                graphics2D.drawRect((int) chimneyQueueList.get(i).getPosX(), (int) chimneyQueueList.get(i).getPosY(), 74, 500);
                graphics2D.drawImage(chimneyImageUp, (int) chimneyQueueList.get(i).getPosX(), (int) chimneyQueueList.get(i).getPosY(), null);
            } else {
                //Ống khói quay xuống
                graphics2D.setColor(Color.WHITE);
                graphics2D.drawRect((int) chimneyQueueList.get(i).getPosX(), (int) chimneyQueueList.get(i).getPosY() - 100, 74, 500);
                graphics2D.drawImage(chimneyImageDown, (int) chimneyQueueList.get(i).getPosX(), (int) chimneyQueueList.get(i).getPosY(), null);
            }

        }
    }

    public int getRandomY(){
        Random random = new Random();
        int a;
        a = random.nextInt(10);
        return a*20;
    }
}
