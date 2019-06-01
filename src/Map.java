import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Map {
    int x;
    int y;
    int bit; // khi thả bom thì đổi bit 2,3 thành 0
    Image[] img = {
            new ImageIcon(getClass().getResource("/images/snow.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/Tree.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/home.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/snowman1.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/IceBox.png")).getImage(),
    };

    public Map(int x, int y, int bit) {
        this.x = x;
        this.y = y;
        this.bit = bit;
    }
    void draw(Graphics2D g2d, ArrayList<Boom> arr){
        checkBoom(arr);
        g2d.drawImage(img[bit],x,y,null);

    }

    void checkBoom(ArrayList<Boom> arr){

            if(bit == 0|| bit==1|| bit ==4){ // chỉ xóa nhà và ng.tuyet
                return;
            }
        for (Boom b:arr
        ) {
            if(b.index!=3){
                continue;
            }
            /*Rectangle rect = b.getRect().intersection(getRect());// đổi trạng thái bit thành 0
            if(rect. isEmpty() == false){
               //  arr.remove(b); ko thực hiện vì boom tự mất
                bit = 0;
                return;
            }*/
            if(b.checkRect(getRect())== false){
                bit=0;
                return;
            }

        }
    }
    Rectangle getRect(){
        // xử lí va chạm bằng cách chuyển thành hình chữ nhật, rồi ktra nếu va chạm sẽ sinh ra 1 hcn mới, còn ko thì bằng rỗng
        int w = img[bit].getWidth(null );
        int h = img[bit].getHeight(null );
     Rectangle rect = new Rectangle(x,y,w, h);
     return rect;
    }
}
