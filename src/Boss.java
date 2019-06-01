import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Boss {
    Random rd = new Random();
    int x;
    int y;
    Image[] img = {
            new ImageIcon(getClass().getResource("/images/blink_left.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/blink_right.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/blink_up.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/blink_down.png")).getImage(),

    };
    int orient; //hướng

    public Boss(int x, int y) {
        this.x = x;
        this.y = y;
        orient = Orient.DOWN;
    }
    void draw(Graphics2D g2d){
        g2d.drawImage(img[orient], x,y, null);
    }
    void createOrient(){
        int percent = rd.nextInt(105);// để không đổi hướng liên tục
        if(percent<95){
            return;
        }
        int newOrient = rd.nextInt(4);// sinh ra ngẫu nhiên,bound=4 nó sẽ ramdom từ 0 đến 4-1;
        orient = newOrient;
    }
    void move(ArrayList<Map> arr, ArrayList<Boom> arrBoom){
        createOrient();
        int xR =x;
        int yR =y;
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
        if(checkMap(arr, arrBoom)== false){ // nếu xảy ra va chạm thì quay về vị trí trước
            x = xR;
            y = yR;
        }
    }
    boolean checkMap(ArrayList<Map> arr, ArrayList<Boom> arrBoom){ // check map đc gọi ở move
        for (Map m: arr // nếu va chạm thì true
             ) {
           if(m.bit == 0){
               continue;
           } Rectangle rect = getRect().intersection(m.getRect());
           if(rect.isEmpty()==false){
               return false;
           }//intersection k.tra , get rect là lấy từ ..
        }
        for (Boom b:arrBoom
             ) {
            if(b.checkRect(getRect())== false){
                return false;
            }

        }
            return true; //ko va chạm
    }
    Rectangle getRect(){
        // xử lí va chạm bằng cách chuyển thành hình chữ nhật, rồi ktra nếu va chạm sẽ sinh ra 1 hcn mới, còn ko thì bằng rỗng
        int w = img[orient].getWidth(null );
        int h = img[orient].getHeight(null );
        Rectangle rect = new Rectangle(x,y,w, h);
        return rect;
    }
}
