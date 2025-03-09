package Elementos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
/*import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;*/

import Juegos.Juego;
import Utilz.LoadSave;

import static Utilz.Constantes.ConstanteJugador.*;
//import static Utilz.Constantes.Direccion.*;
import static Utilz.MetodoAyuda.*;

public class Jugador extends Cascaron {
    private BufferedImage[][] idleAni;
    private int animInd, animTick = 0, animSpeed = 15;
    private int playerAction = INACTIVO;
    // private int playerDirec = -1;
    private boolean moving = false;
    private boolean attacking = false;
    private boolean left, right, down, up, jump;
    private float playerSpeed = 2.0f;
    private int[][] lvlData;
    private float xDrawOffset = 21 * Juego.SCALE;
    private float yDrawOffset = 4 * Juego.SCALE;

    ///// graveda y salto
    private float airSpeed = 0f;
    private float gravity = 0.01f * Juego.SCALE;
    private float jumpSpeed = -2.25f * Juego.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Juego.SCALE;
    private boolean inAir = false;

    // Apuntado
    private AimController aimController;
    private int currentMouseX, currentMouseY;

    // Armas
    private Elementos.Armas.MachineGun armaActual;
    //FIN DECLARACIÓN DE VARIABLES
    //INICIO DE FUNCIONES

    public Jugador(float x, float y, int w, int h) {
        super(x, y, w, h);
        loadAnimation();
        initHitBox(x, y, 20 * Juego.SCALE, 27 * Juego.SCALE);
        aimController = new AimController(200* Juego.SCALE);
        armaActual = new Elementos.Armas.MachineGun();
    }

    // FUNCIONES DE MOUSE
    public void updateMouseInfo(int mouseX, int mouseY) {
        this.currentMouseX = mouseX;
        this.currentMouseY = mouseY;
    }
    
    public void renderAim(Graphics g, int xlvlOffset) {
        // Obtener las coordenadas del apuntador
        float aimX = AimController.getAimedX();
        float aimY = AimController.getAimedY();
        
        // Dibujar un círculo en la posición del apuntador
        int cursorSize = 10; // Tamaño del círculo
        g.setColor(Color.RED);
        g.fillOval((int)aimX - cursorSize/2, (int)aimY - cursorSize/2, cursorSize, cursorSize);
        
        // Opcional: Dibuja una línea desde el jugador hasta el cursor
        g.setColor(Color.WHITE);
        g.drawLine(
            (int)(getXCenter() - xlvlOffset), 
            (int)getYCenter(), 
            (int)aimX, 
            (int)aimY
        );

        // En tu método renderAim, añade:
g.setColor(Color.YELLOW);
g.drawString("MouseX: " + currentMouseX + ", MouseY: " + currentMouseY, 10, 20);
g.drawString("AimX: " + AimController.getAimedX() + ", AimY: " + AimController.getAimedY(), 10, 40);
    }

    // FIN FUNCIONES DE MOUSE
    public void update(int xlvlOffset) {
        aimController.update(getXCenter() - xlvlOffset,getYCenter(), currentMouseX, currentMouseY);
        armaActual.update(getXCenter(), getYCenter(), aimController);
        actualizaAnim();
        colocarAnim();
        actuPosicion();
    }

    public void update() {
        update(0);
    }

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if(!isEntityOnFloor(hitbox, lvlData))
           inAir=true;
    }

    public void render(Graphics g, int xlvlOffset) {
        g.drawImage(idleAni[playerAction][animInd],
                (int) (hitbox.x - xDrawOffset)-xlvlOffset, (int) (hitbox.y - yDrawOffset),
                w, h, null);
        renderAim(g, xlvlOffset);
        armaActual.render(g, xlvlOffset);
    }

    private void actualizaAnim() {
        animTick++;
        if (animTick >= animSpeed) {
            animTick = 0;
            animInd++;
            if (animInd >= GetNoSprite(playerAction))
            {    animInd = 0;
                attacking=false;
            }    
        }

    }

    private void actuPosicion() {
        moving = false;
        if (jump) {
            jump();
        }
        if (!left && !right && !inAir)
            return;
    
        float xSpeed = 0;
        if (left)
            xSpeed -= playerSpeed;
        if (right)
            xSpeed += playerSpeed;
    
        // 🔴 Modificación: Verificar si está en el suelo ANTES de activar inAir
        boolean enSuelo = isEntityOnFloor(hitbox, lvlData);
        
        if (!inAir && !enSuelo)
            inAir = true;
    
        if (inAir) {
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
    
                // 🔴 Corregido: Solo desactivar inAir si realmente está tocando el suelo
                if (enSuelo) {
                    resetInAir();
                } else {
                    if (airSpeed > 0)
                        resetInAir();
                    else
                        airSpeed = fallSpeedAfterCollision;
                }
                updateXPos(xSpeed);
            }
        } else {
            updateXPos(xSpeed);
        }
    
        // 🔴 Nueva condición: Si el personaje está en el suelo después de moverse, quitar inAir
        if (isEntityOnFloor(hitbox, lvlData)) {
            inAir = false;
        }
    
        moving = true;
    }
    
    

    private void jump() {
        if(inAir)
         return;
        inAir=true;
        airSpeed=jumpSpeed;
    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y,
                 hitbox.width,  hitbox.height, lvlData))
            hitbox.x += xSpeed;
        else {
            hitbox.x = GetEntityXPosNexttoWall(hitbox, xSpeed);
            /*if (airSpeed > 0)
                resetInAir();*/
        }
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;

    }

    private void colocarAnim() {
        int starAnim = playerAction;
        if (moving)
            playerAction = CORRER;
        else
            playerAction = INACTIVO;
        if(inAir){
            if(airSpeed<0)
                playerAction=SALTAR;
            else
                playerAction=CAYENDO;
        }
        if (attacking)
            playerAction = ATACAR1;
        if (starAnim != playerAction)
            resetAnimTick();
    }

    public boolean isJump() {
        return jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    private void resetAnimTick() {
        this.animTick = 0;
        animInd = 0;
    }

    private void loadAnimation() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
        idleAni = new BufferedImage[9][6];
        for (int i = 0; i < idleAni.length; i++)
            for (int j = 0; j < idleAni[i].length; j++)
                idleAni[i][j] = img.getSubimage(j * 64, i * 40, 64, 40);

    }

    //FIN FUNCIONES
    //GETTERS Y SETTERS
    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void resetDirBooleans() {
        left = right = up = down = false;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public float getXCenter() {
        return hitbox.x + hitbox.width / 2f;
    }

    public float getYCenter() {
        return hitbox.y + hitbox.height / 2f;
    }

}
