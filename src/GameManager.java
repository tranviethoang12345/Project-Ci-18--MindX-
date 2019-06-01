import java.awt.*;
import java.util.ArrayList;

public class GameManager {
    // quản lí nhiều đối tượng, ta quản lí trên mảng
    ArrayList<Boss> arrBoss;
    Player player;
    ArrayList<Boom> arrBoom;
    ArrayList<Map> arrMap;
    int[][] arr = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,3,1,3,1,1,1,1,1,1,1,1,1,3,1,3,1},
            {1,1,3,1,2,2,2,1,1,1,2,2,2,1,3,1,1},
            {1,1,1,2,2,2,2,2,1,1,2,2,2,2,1,1,1},
            {1,1,2,2,2,0,0,0,2,0,0,0,2,2,2,1,1},
            {1,1,2,2,0,0,0,0,0,0,0,0,0,2,2,1,1},
            {1,1,1,2,2,0,0,0,0,0,0,0,2,2,1,1,1},
            {1,1,1,1,2,2,0,0,0,0,0,2,2,1,1,1,1},
            {1,1,1,1,1,2,2,0,0,0,2,2,1,1,1,1,1},
            {1,4,1,4,1,1,2,2,0,2,2,1,1,3,1,3,1},
            {1,1,4,1,1,1,1,2,2,2,1,1,1,1,3,1,1},
            {1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,4,4,1,4,4,1,1},
            {1,3,3,1,3,3,1,1,1,1,4,4,4,4,4,1,1},
            {1,3,3,3,3,3,1,3,1,3,1,4,4,4,1,1,1},
            {1,1,3,3,3,1,1,1,3,1,1,1,4,1,1,1,1},
            {1,1,1,3,1,1,1,1,1,1,1,1,1,1,1,1,1},
    };
    void initGame(){
        SoundManager.play("wahoo.wav");
        // dùng để khởi tạo màn chơi
        arrBoom = new ArrayList<Boom>();
        player = new Player(200,150);
        arrBoss = new ArrayList<Boss>();
        arrBoss.add(new Boss(260,200));
        arrBoss.add(new Boss(340,300));
        arrBoss.add(new Boss(430,160));
        arrBoss.add(new Boss(360,220));
        arrBoss.add(new Boss(355,230));
        readMap(); // sau đó vẽ map
    }

    void readMap(){
        arrMap = new ArrayList<Map>(); // x=jx W, y= ixH ;
        for(int i=0; i<arr.length;i++){
            for(int j=0; j<arr[i].length;j++){
                int x= 40 * j;
                int y= 40 * i;
                int bit = arr[i][j];
                arrMap.add(new Map(x,y,bit));
            }
        }
    }
    void draw(Graphics2D g2d){
        for (Map m:arrMap
             ) {
            m.draw(g2d, arrBoom);

        }
        for(int i = arrBoom.size()-1; i>=0 ; i--){
            boolean remote = arrBoom.get(i).draw(g2d);
            if( remote == true ){
                arrBoom.remove(i);
            }
        }
        //duyệt tất cả các phần tử của mảng thì mới vẽ đc
        for (Boss b: arrBoss) { // dùng foreach
            b.draw(g2d);// sau đó quay lại boompanel khai báo new manager r gọi init game.
        }
        player.draw(g2d);
       /* for (Boom b: arrBoom) {
            b.draw(g2d);
        }*/

    }
    void playerFire(){
        player.fire(arrBoom);
    }
    boolean AI(){
        // hành động đc thực hiện tự động sẽ bỏ vào AI
        for(int i = arrBoss.size()-1; i>=0; i--){
            arrBoss.get(i).move(arrMap, arrBoom);// gọi vào từng phần tử trong mảng và cho nó di chuyển // quay sang panel 21
            for (Boom b:arrBoom
                 ) {
                if(b.checkRect(arrBoss.get(i).getRect())==false && b.index==3){
                    arrBoss.remove(i);
                    SoundManager.play("lugi_haha.wav");
                    break;
                }

            }
            for (Boom b:arrBoom
                 ) {
                if(b.checkRect(player.getRect())== false && b.index==3) {
                    return true;
                }
            }

        }return player.checkDie(arrBoss);
    }
}
