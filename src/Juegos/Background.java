package Juegos;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import Utilz.LoadSave;
import static Utilz.Constantes.Ambiente.*;

public class Background {
    private BufferedImage backgroundImg, bigCloud, smallCloud;
    private int[] small_cloudsPos;
    private Random rnd = new Random();

    public Background() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);
        bigCloud = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUD);
        smallCloud = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUD);
        small_cloudsPos = new int[8];
        for (int i = 0; i < small_cloudsPos.length; i++) {
            small_cloudsPos[i] = (int) (90 * Juego.SCALE) + rnd.nextInt((int) (100 * Juego.SCALE));
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        g.drawImage(backgroundImg, 0, 0, (int) (Juego.GAME_WIDTH * 2.5), Juego.GAME_HEIGHT, null);
        drawClouds(g, xLvlOffset);
    }

    private void drawClouds(Graphics g, int xLvlOffset) {
        for (int i = 0; i < 4; i++) {
            g.drawImage(bigCloud, i * BIG_CLOUD_WIDTH - (int) (xLvlOffset), (int) (204 * Juego.SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);
        }

        for (int i = 0; i < small_cloudsPos.length; i++) {
            g.drawImage(smallCloud, 4 * i * SMALL_CLOUD_WIDTH - (int) (xLvlOffset * 0.7), small_cloudsPos[i], SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
        }
    }
}
