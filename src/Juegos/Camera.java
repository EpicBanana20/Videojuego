package Juegos;

import Utilz.LoadSave;

public class Camera {
    private int xLvlOffset;
    private int leftBorder;
    private int rightBorder;
    private int maxLvlOffset;

    public Camera(int gameWidth, int tilesWidth, int tilesSize) {
        this.leftBorder = (int) (0.2 * gameWidth);
        this.rightBorder = (int) (0.8 * gameWidth);
        int lvlTileWide = LoadSave.GetLevelData()[0].length;
        int maxTilesOffset = lvlTileWide - tilesWidth;
        this.maxLvlOffset = maxTilesOffset * tilesSize;
    }

    public void checkCloseToBorder(int playerX) {
        int diff = playerX - xLvlOffset;
        if (diff > rightBorder)
            xLvlOffset += diff - rightBorder;
        else if (diff < leftBorder)
            xLvlOffset += diff - leftBorder;
        if (xLvlOffset > maxLvlOffset)
            xLvlOffset = maxLvlOffset;
        else if (xLvlOffset < 0)
            xLvlOffset = 0;
    }

    public int getxLvlOffset() {
        return xLvlOffset;
    }
}
