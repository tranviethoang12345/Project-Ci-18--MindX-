import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Player {
    int x;
    int y;
    Image[] imgLeft = {
            new ImageIcon(getClass().getResource("/santa_images/left0.png")).getImage(),
            new ImageIcon(getClass().getResource("/santa_images/left1.png")).getImage(),
            new ImageIcon(getClass().getResource("/santa_images/left2.png")).getImage(),
            new ImageIcon(getClass().getResource("/santa_images/left3.png")).getImage(),
            new ImageIcon(getClass().getResource("/santa_images/left4.png")).getImage(),
            new ImageIcon(getClass().getResource("/santa_images/left5.png")).getImage(),
            new ImageIcon(getClass().getResource("/santa_images/left6.png")).getImage(),
            new ImageIcon(getClass().getResource("/santa_images/left7.png")).getImage(),

    };
    Image[] imgRight = {
            new ImageIcon(getClass().getResource("/santa_images/right0.png")).getImage(),
            new ImageIcon(getClass().getResource("/santa_images/right1.png")).getImage(),
            new ImageIcon(getClass().getResource("/santa_images/right2.png")).getImage(),
            new ImageIcon(getClass().getResource("/santa_images/right3.png")).getImage(),
            new ImageIcon(getClass().getResource("/santa_images/right4.png")).getImage(),
            new ImageIcon(getClass().getResource("/santa_images/right5.png")).getImage(),
            new ImageIcon(getClass().getResource("/santa_images/right6.png")).getImage(),
            new ImageIcon(getClass().getResource("/santa_images/right7.png")).getImage(),

    };
    Image[] imgUp = {
            new ImageIcon(getClass().getResource("/santa_images/up0.png")).getImage(),
            new ImageIcon(getClass().getResource("/santa_images/up1.png")).getImage(),
            new ImageIcon(getClass().getResource("/santa_images/up2.png")).getImage(),
            new ImageIcon(getClass().getResource("/santa_images/up3.png")).getImage(),
            new ImageIcon(getClass().getResource("/santa_images/up4.png")).getImage(),
            new ImageIcon(getClass().getResource("/santa_images/up5.png")).getImage(),
            new ImageIcon(getClass().getResource("/santa_images/up6.png")).getImage(),
            new ImageIcon(getClass().getResource("/santa_images/up7.png")).getImage(),

    };
    Image[] imgDown = {
            new ImageIcon(getClass().getResource("/santa_images/down0.png")).getImage(),
            new ImageIcon(getClass().getResource("/santa_images/down1.png")).getImage(),
            new ImageIcon(getClass().getResource("/santa_images/down2.png")).getImage(),
            new ImageIcon(getClass().getResource("/santa_images/down3.png")).getImage(),
            new ImageIcon(getClass().getResource("/santa_images/down4.png")).getImage(),
            new ImageIcon(getClass().getResource("/santa_images/down5.png")).getImage(),
            new ImageIcon(getClass().getResource("/santa_images/down6.png")).getImage(),
            new ImageIcon(getClass().getResource("/santa_images/down7.png")).getImage(),

    };
    Image[][] img = { imgLeft, imgRight, imgUp, imgDown
    };
    int orient; //hướng
    int index = 0;
    int count = 0; // đếm số lần di chuyển
    long t;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        orient = Orient.DOWN;
    }
    void draw(Graphics2D g2d){
        g2d.drawImage(img[orient][index], x,y, null);
    }

    void fire(ArrayList<Boom> arrBoom){
        long T = System.currentTimeMillis(); // T thời gian hiện tại
        if( T - t < 1000){
            return;
        }
        t = T;
        SoundManager.play("explosion.wav");
        // bom đặt ở trung tâm nhân vật
        int xB = x + img[orient][index].getWidth(null)/2;
        int yB = y + img[orient][index].getHeight(null)/2;
        Boom b = new Boom(xB, yB);
        arrBoom.add(b);
    }

    void move(int newOrient, ArrayList<Map> arr){
        count++;
        if (count == 5){
            index++;
            if(index>= img[orient].length){
                index =0;
            }
            count =0;
        }

        int xR= x;
        int yR = y;
        orient = newOrient;
        //cho boss di chuyển
        switch (orient){
            case Orient.LEFT: //khi di chuyển sang trái, tọa độ x giảm 1 đơn vị
                x--;
                break;
            case Orient.RIGHT:
                x++;
                break;
            case Orient.UP:
                y--;
                break;
            case Orient.DOWN:
                y++;
                break; // chuyển sang gamemanager
        }
        if (checkMap(arr)==false){
            x= xR;
            y=yR;
        }
    }
    boolean checkMap(ArrayList<Map> arr){ // check map đc gọi ở move
        for (Map m: arr // nếu va chạm thì true
        ) {
            if(m.bit == 0){
                continue;
            } Rectangle rect = getRect().intersection(m.getRect());
            if(rect.isEmpty()==false){
                return false;
            }//intersection k.tra , get rect là lấy từ nhân vật
        } return true; //ko va chạm
    }

    boolean checkDie(ArrayList<Boss>arr){
        for (Boss b:arr
             ) {Rectangle rect = b.getRect().intersection(getRect());
             if(rect.isEmpty()==false){
                 return true;
             }

        }return false;
    }
    Rectangle getRect(){
        // xử lí va chạm bằng cách chuyển thành hình chữ nhật, rồi ktra nếu va chạm sẽ sinh ra 1 hcn mới, còn ko thì bằng rỗng
        int w = img[orient][index].getWidth(null );
        int h = img[orient][index].getHeight(null );
        Rectangle rect = new Rectangle(x+10,y+40 ,w-20, h-60);
        return rect;
    }
}
