package Juegos;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Eventos.EventoMouse;
import Eventos.EventoTeclado;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import static Utilz.Constantes.ConstanteJugador.*;
import static Utilz.Constantes.Direccion.*;

public class PanelJuego extends JPanel {
    private EventoMouse ev;
    private EventoTeclado et;
    Juego game;

    public PanelJuego(Juego game) {
        ev = new EventoMouse(this);
        et = new EventoTeclado(this);
        this.game = game;
        setPanelSize();
        addKeyListener(et);
        addMouseListener(ev);
        addMouseMotionListener(ev);
    }

    private void setPanelSize() {
        Dimension size=new Dimension(1280,800);
        this.setPreferredSize(size);
    }

    public void paint(Graphics g) {
        super.paint(g);
        Juego game = getGame();
        game.render(g);
    }

    public Juego getGame() {
        return game;
    }
    void updateGame() {
        // Actualiza la información del mouse en el jugador antes de la actualización general
        game.updateMouseInfo(ev.getMouseX(), ev.getMouseY());
        // La actualización normal del juego continúa en el método update() de la clase Juego
    }

}
