package Utilz;

import java.awt.geom.Rectangle2D;

import Juegos.Juego;

public class MetodoAyuda {
    public static boolean CanMoveHere(float x, float y,
            float width, float height, int[][] lvlData) {
        if (!isSolido(x, y, lvlData))
            if (!isSolido(x + width, y + height, lvlData))
                if (!isSolido(x + width, y, lvlData))
                    if (!isSolido(x, y + height, lvlData))
                        return true;
        return false;

    }

    private static boolean isSolido(float x, float y, int[][] lvlData) {
        int maxWidth = lvlData[0].length * Juego.TILES_SIZE;
        if (x < 0 || x >= maxWidth)
            return true;
        if (y < 0 || y >= Juego.GAME_HEIGHT)
            return true;
        int xIndex = (int) (x / Juego.TILES_SIZE);
        int yIndex = (int) (y / Juego.TILES_SIZE);
        int value = lvlData[yIndex][xIndex];
        return value >= 48 || value < 0 || value != 11;
    }

    public static boolean isEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        // Verificar si la parte inferior izquierda del hitbox está sobre un bloque sólido
        if (!isSolido(hitbox.x, hitbox.y + hitbox.height + 1, lvlData)) {
            // Verificar si la parte inferior derecha del hitbox está sobre un bloque sólido
            if (!isSolido(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData)) {
                return false;
            }
        }
        return true;
    }

    public static float GetEntityXPosNexttoWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int) (hitbox.x / Juego.TILES_SIZE);
        if (xSpeed > 0) {
            int tileXPos = currentTile * Juego.TILES_SIZE;
            int xOffset = (int) (Juego.TILES_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;
        } else
            return currentTile * Juego.TILES_SIZE;
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(
            Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int) (hitbox.y / Juego.TILES_SIZE);
        if(airSpeed>0){
            int tileYPos = currentTile * Juego.TILES_SIZE;
            int yOffset = (int) (Juego.TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else
            return currentTile * Juego.TILES_SIZE;

    }

}
