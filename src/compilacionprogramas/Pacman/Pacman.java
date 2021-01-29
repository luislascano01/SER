/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilacionprogramas.Pacman;



import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Pacman extends JFrame {
 String direction = new File (".").getCanonicalPath()+"/media/images/";
    ImageIcon iicon = new ImageIcon(direction+"logo.png");
     
    public Pacman() throws IOException {
        this.setIconImage(iicon.getImage());
        initUI();
        ImageIcon iicon = new ImageIcon(direction+"logo.png");
    this.setIconImage(iicon.getImage());
    }
    
    private void initUI() throws IOException {
        
        add(new Board());
        
        setTitle("Pacman");
        setSize(380, 420);
        setLocationRelativeTo(null);
        setVisible(true);        
        
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Pacman ex = null;
            try {
                ex = new Pacman();
            } catch (IOException ex1) {
                Logger.getLogger(Pacman.class.getName()).log(Level.SEVERE, null, ex1);
            }
            ex.setVisible(true);
            ex.show();
        });
    }
}

