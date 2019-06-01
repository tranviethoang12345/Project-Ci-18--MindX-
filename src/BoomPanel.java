import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BoomPanel extends JPanel {
    GameManager manager = new GameManager();
    boolean[] flag = new boolean[256];
    public BoomPanel() {
        setBackground(Color.white);
        manager.initGame();
       Thread t = new Thread(run);
        t.start();
        setFocusable(true);
        addKeyListener(key);
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);// gọi để vẽ giao diện
        manager.draw(g2d);
       /* g2d.setColor(Color.red);
        g2d.setStroke(new BasicStroke(10));
        g2d.drawLine(0,0, 200,200);
        g2d.drawOval(200,200, 50,50);
        g2d.drawRect(300, 300, 40,40 );*/

    }
    KeyListener key = new KeyListener() {// sự kiện lắng nghe việc ấn phím của ng dùng
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
        flag[e.getKeyCode()]= true;// e.getkeycode là hàm lấy cái phím của ng dùng
        }

        @Override
        public void keyReleased(KeyEvent e) {
            flag[e.getKeyCode()]= false;

        }
    };
    Runnable run = new Runnable(){
        @Override
        public void run() {
            // các hành động tự động, là 1 tiến trình lặp đi lặp lại liên tục, chỉ dừng khi kết thúc game, liên tưởng đến vòng lặp vô hạn
            while(true){
                if(flag[KeyEvent.VK_LEFT]== true){
                    manager.player.move(Orient.LEFT, manager.arrMap);
                } else if(flag[KeyEvent.VK_RIGHT]== true){
                    manager.player.move(Orient.RIGHT, manager.arrMap);
                } else if(flag[KeyEvent.VK_UP]== true){
                    manager.player.move(Orient.UP,manager.arrMap);
                } else if(flag[KeyEvent.VK_DOWN]== true){
                    manager.player.move(Orient.DOWN,manager.arrMap);
                }
                if(flag[KeyEvent.VK_SPACE]== true){
                    manager.playerFire();
                }
                AI
                boolean die = manager.AI();
                if( die == true){
                    int result = JOptionPane.showConfirmDialog(
                            null,
                            "do you want to replay?",
                            "Game Over",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if(result == JOptionPane.YES_OPTION){
                        manager.initGame();
                        flag = new boolean[236];
                    }else{
                        System.exit(0);
                    }
                }
                repaint(); // khi di chuyển thì giao diện thay đổi, nên cần cập nhật lại giao diện, chueyern lên dòng 9Panel
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
