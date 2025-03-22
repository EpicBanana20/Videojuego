package Elementos.Armas;

import Elementos.Arma;
import Elementos.Bala;
import Juegos.Juego;
import Elementos.AimController;

public class MachineGun extends Arma {
    private int cadenciaDisparo = 1; // Disparos por segundo
    private int contadorRecarga = 0;
    private int tiempoRecarga = 5; // Frames entre disparos

    public MachineGun() {
        super("machinegun.png", 30 * Juegos.Juego.SCALE, 3.0f);
        this.nombre = "MachineGun";
    }
    
    @Override
    public void disparar() {
        // Lógica de disparo
        if(contadorRecarga <= 0) {
            System.out.println("¡Disparando ametralladora!");
            
            // Calcular la posición exacta del origen de la bala (punta del arma)
            float[] posicionDisparo = new float[2];
            float distanciaCañon = 20 * Juego.SCALE; // Distancia desde el centro del arma hasta la punta
            
            AimController.getPositionAtDistance(
                x, y,  // posición actual del arma
                distanciaCañon,  // distancia desde el centro del arma
                rotacion,  // ángulo de disparo
                posicionDisparo
            );
            
            // Crear una nueva bala en la posición calculada
            Bala nuevaBala = new Bala(
                posicionDisparo[0], 
                posicionDisparo[1], 
                rotacion
            );
            
            // Añadir la bala al administrador
            adminBalas.agregarBala(nuevaBala);
            
            // Reiniciar contador de recarga
            contadorRecarga = tiempoRecarga;
        }

    }
    
    @Override
    public void update(float playerX, float playerY, AimController aimController) {
        super.update(playerX, playerY, aimController);
        
        // Actualizar contador de recarga
        if(contadorRecarga > 0)
            contadorRecarga--;
    }
}