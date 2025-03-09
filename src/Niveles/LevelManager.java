package Niveles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Juegos.Juego;
import Utilz.LoadSave;

public class LevelManager {
    private Juego game;
    private BufferedImage[] levelSprite;
    private Level levelOne;

    public LevelManager(Juego game) {
        this.game = game;
        importOutsideSprite();
        levelOne=new Level(LoadSave.GetLevelData());
    }

    private void importOutsideSprite() {
        BufferedImage img=LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite=new BufferedImage[48];
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 12; i++) {
                int index=j*12+i;
                levelSprite[index]=img.getSubimage(i*32, j*32, 32, 32);
        }
    }

}

    public void update() {
         }

    public void draw(Graphics g, int xLvlOffset) {
       for(int j=0;j<Juego.TILES_HEIGHT;j++)
            for(int i=0;i<levelOne.getLvlData()[0].length;i++){
                int index=levelOne.getSpriteIndex(j, i);
                g.drawImage(levelSprite[index], Juego.TILES_SIZE*i-xLvlOffset,
                Juego.TILES_SIZE*j,Juego.TILES_SIZE,
                Juego.TILES_SIZE,null);
            }
    }
    public Level getCurrentLevel() {
		return levelOne;
	}

}
