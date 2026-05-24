package proyecto_integrador.models;

public class SituacionAdministrativa {

    String tipo;
    String fechaInicio;
    String fechaFin;
    boolean activa;

    public SituacionAdministrativa(String tipo, String fechaInicio, String fechaFin) {
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.activa = true;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        String VERDE = "\u001B[32m";
        String ROJO = "\u001B[31m";
        String RESET = "\u001B[0m";

        // Si está activa pinta el estado en verde, si no en rojo
        String estadoColoreado = activa
                ? VERDE + "Activa" + RESET
                : ROJO + "Inactiva" + RESET;

        return "Tipo: " + tipo +
                ", Fecha inicio: " + fechaInicio +
                ", Fecha fin: " + fechaFin +
                ", Estado: " + estadoColoreado;
    }
}