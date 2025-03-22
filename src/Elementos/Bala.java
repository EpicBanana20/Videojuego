package Elementos;

import java.awt.Color;
import java.awt.Graphics;
import Juegos.Juego;

public class Bala extends Cascaron {
    // Propiedades específicas de la bala
    private float velocidadX, velocidadY;
    private float velocidad = 2.0f * Juego.SCALE; // Velocidad base de la bala
    private boolean activa = true; // Indica si la bala está activa o debe eliminarse
    private int daño = 10; // Daño que causa la bala
    
    public Bala(float x, float y, float angulo) {
        // Llamamos al constructor de Cascaron con un tamaño pequeño para la bala
        super(x, y, (int)(8 * Juego.SCALE), (int)(8 * Juego.SCALE));
        
        // Calculamos las componentes X e Y de la velocidad según el ángulo
        this.velocidadX = (float) Math.cos(angulo) * velocidad;
        this.velocidadY = (float) Math.sin(angulo) * velocidad;
        
        // Inicializamos el hitbox más pequeño que el sprite visual
        initHitBox(x, y, 6 * Juego.SCALE, 6 * Juego.SCALE);
    }
    
    public void update() {
        // Actualizamos la posición de la bala según su velocidad
        hitbox.x += velocidadX;
        hitbox.y += velocidadY;
        
        // También actualizamos las coordenadas base
        x = hitbox.x;
        y = hitbox.y;
    }
    
    public void render(Graphics g, int xLvlOffset) {
        // Dibujamos la bala como un círculo amarillo
        g.setColor(Color.YELLOW);
        g.fillOval(
            (int)(hitbox.x - xLvlOffset), 
            (int)hitbox.y,
            (int)hitbox.width, 
            (int)hitbox.height
        );
        
        // Si queremos ver el hitbox para depuración
        // drawHitBox(g);
    }
    
    // Comprueba si la bala está fuera de los límites del nivel
    public boolean fueraDeLimites(int nivelAncho, int nivelAlto) {
        boolean fuera = hitbox.x < 0 || hitbox.x > nivelAncho || 
               hitbox.y < 0 || hitbox.y > nivelAlto;

        if(fuera) {
            desactivar();
        }
        return fuera;
    }
    
    // Desactiva la bala (cuando golpea algo o sale de los límites)
    public void desactivar() {
        activa = false;
    }
    
    // GETTERS Y SETTERS
    public boolean estaActiva() {
        return activa;
    }
    
    public int getDaño() {
        return daño;
    }
}