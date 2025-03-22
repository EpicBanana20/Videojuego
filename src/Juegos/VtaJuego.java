package Juegos;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class VtaJuego extends JFrame {

    public VtaJuego(PanelJuego n){
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.add(n);
        this.setVisible(true);
        this.addWindowFocusListener(new WindowFocusListener() {

            @Override
            public void windowGainedFocus(WindowEvent e) {
              }

            @Override
            public void windowLostFocus(WindowEvent e) {
                n.getGame().windowFocusLost();
            }
            
        });
        this.pack();
    }    
}
