package Elementos.Armas;

import Elementos.Arma;
import Elementos.AimController;

public class MachineGun extends Arma {
    private int cadenciaDisparo = 10; // Disparos por segundo
    private int contadorRecarga = 0;
    private int tiempoRecarga = 5; // Frames entre disparos
    
    public MachineGun() {
        super("machinegun.png", 30 * Juegos.Juego.SCALE); // Offset de 5 píxeles
        this.nombre = "MachineGun";
    }
    
    @Override
    public void disparar() {
        // Lógica de disparo
        if(contadorRecarga <= 0) {
            System.out.println("¡Disparando ametralladora!");
            // Aquí crearemos las balas más adelante
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