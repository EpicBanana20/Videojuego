package Juegos;

import java.awt.Graphics;

import Elementos.Jugador;
import Elementos.AimController;
import Niveles.LevelManager;
import Utilz.LoadSave;


public class Juego {
    private VtaJuego vta;
    private Jugador player;
    private PanelJuego pan;
    private LevelManager levelMan;
    private Camera camera;
    private Background background;
    private GameLoop gameLoop;

    public final static int TILES_DEF_SIZE = 32;
    public final static float SCALE = 1.5f;
    public final static int TILES_HEIGHT = 14;
    public final static int TILES_WIDTH = 26;
    public final static int TILES_SIZE = (int) (TILES_DEF_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_HEIGHT;

    public Juego() {
        inicializar();
        pan = new PanelJuego(this);
        vta = new VtaJuego(pan);
        vta.add(pan);
        pan.requestFocus();
        gameLoop = new GameLoop(this);
        gameLoop.start();
    }

    private void inicializar() {
        player = new Jugador(200, 200, (int) (64 * SCALE), (int) (40 * SCALE));
        levelMan = new LevelManager(this);
        player.loadLvlData(levelMan.getCurrentLevel().getLvlData());
        camera = new Camera(GAME_WIDTH, TILES_WIDTH, TILES_SIZE);
        background = new Background();
    }

    public void updates() {
        player.update(camera.getxLvlOffset());
        levelMan.update();
        pan.updateGame();
        camera.checkCloseToBorder((int) player.getHitBox().getX());
    }

    public void render(Graphics g) {
        background.draw(g, camera.getxLvlOffset());
        levelMan.draw(g, camera.getxLvlOffset());
        player.render(g, camera.getxLvlOffset());
    }

    public Jugador getPlayer() {
        return player;
    }

    public PanelJuego getPanel() {
        return pan;
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public void updateMouseInfo(int mouseX, int mouseY) {
        // Almacena o pasa directamente la información del mouse al jugador
        player.updateMouseInfo(mouseX, mouseY);
    }
}
