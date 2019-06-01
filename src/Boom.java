import javax.swing.*;
import java.awt.*;

public class Boom {
    Image[] img = {
            new ImageIcon(getClass().getResource("/images/boom1.gif")).getImage(),
            new ImageIcon(getClass().getResource("/images/no1.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/no2.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/no3.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/no4.png")).getImage(),
    };
    int x;
    int y;
    int index;// 4 hình
    int time = 300;

    public Boom(int x, int y) {
        this.x = x; //- img[0].getWidth(null)/2;
        this.y = y; //- img[0].getHeight(null)/2 + 20 ;
    }
    boolean draw(Graphics2D g2d){
        time--;
        if(time== 80){
            SoundManager.play("bigexplode.wav");
        }
        if(time <= 0){
            return true;
        }
        if( time <=20){
            index= 4;
        } else if(time<=40){
            index=3;
        } else if(time<=60){
            index =2;
        }else if(time<=80){
            index =1;
        } else {
            index = 0;}
        g2d.drawImage(img[index], x-img[index].getWidth(null)/2,
                y-img[index].getHeight(null)/2, null);
        return false;
    }
 /*   Rectangle getRect(){
        // xử lí va chạm bằng cách chuyển thành hình chữ nhật, rồi ktra nếu va chạm sẽ sinh ra 1 hcn mới, còn ko thì bằng rỗng
        int w = img[index].getWidth(null );
        int h = img[index].getHeight(null );
        int xR = x- w / 2;
        int yR = y- h / 2;
        Rectangle rect = new Rectangle(xR,yR,w, h);
        return rect;
    }*/
    boolean checkRect(Rectangle rect){
        int w = img[index].getWidth(null );
        int h = img[index].getHeight(null );
        int xR = x- w / 2;
        int yR = y- h / 2;
        Rectangle r = new Rectangle(x-10,yR,20, h);
        if(r.intersection(rect).isEmpty()==false){
            return false;
        }
        r = new Rectangle(xR,y-10,w,20);
        if(r.intersection(rect).isEmpty()== false){
            return false;
        }return true;
    }
}
