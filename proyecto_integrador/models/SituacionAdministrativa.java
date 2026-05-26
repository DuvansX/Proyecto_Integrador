package proyecto_integrador.models;

public class SituacionAdministrativa {

    // los atributos son privados para que nadie los modifique directamente desde
    // afuera
    private String tipo;
    private String fechaInicio;
    private String fechaFin;
    private boolean activa;

    public SituacionAdministrativa(String tipo, String fechaInicio, String fechaFin) {
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.activa = true; // toda situación arranca como activa al registrarse
    }

    // getters para leer los datos desde afuera sin tocar el atributo directo
    public String getTipo() {
        return tipo;
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

    // setter solo para activa, que es el único campo que necesitamos cambiar
    // después de crear la situación
    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    @Override
    public String toString() {
        // colores para que en la terminal se vea verde si está activa y rojo si no
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