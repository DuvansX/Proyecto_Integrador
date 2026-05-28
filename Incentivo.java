package proyecto_integrador.models;

public class Incentivo {

    // todos privados, se acceden solo por getters
    private final String tipo;
    private final String descripcion;
    private final String fecha; // fecha en que se registró el incentivo (dd/MM/yyyy)
    private final int anio; // año al que corresponde el incentivo, para controlar duplicados

    public Incentivo(String tipo, String descripcion, String fecha, int anio) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.anio = anio;
    }

    // getters para leer cada dato desde otras clases
    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public int getAnio() {
        return anio;
    }

    @Override
    public String toString() {
        return "Tipo: " + tipo +
                ", Descripción: " + descripcion +
                ", Fecha: " + fecha +
                ", Año: " + anio;
    }
}
