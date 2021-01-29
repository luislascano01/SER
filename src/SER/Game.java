
package SER;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

import javax.swing.JFrame;

public class Game extends JFrame {
 String direction = new File (".").getCanonicalPath()+"/media/images/";
 static String path = "/media/images/";
    ImageIcon iicon = new ImageIcon(direction+"logo.png");
     
public Game() throws IOException {
    add(new Board());
    setResizable(false);
    pack();
    
    setTitle("snake");
    setLocationRelativeTo(null);
     this.setIconImage(iicon.getImage());
    
    
}

public static void main(String[] args) {

    // Creates a new thread so our GUI can process itself
    EventQueue.invokeLater(new Runnable() {
        @Override
        public void run() {
            JFrame frame = null;
            try {
                frame = new Game();
            } catch (IOException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            frame.setVisible(true);
        }
    });
}


}