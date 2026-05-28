package proyecto_integrador.models;

public class SaludOcupacional {

    // todos privados, se acceden solo por getters
    private final String fecha; // fecha de la evaluación (dd/MM/yyyy)
    private final String concepto; // apto / apto con restricciones / no apto
    private final String observaciones; // detalle adicional de la evaluación

    public SaludOcupacional(String fecha, String concepto, String observaciones) {
        this.fecha = fecha;
        this.concepto = concepto;
        this.observaciones = observaciones;
    }

    // getters para leer cada dato desde otras clases
    public String getFecha() {
        return fecha;
    }

    public String getConcepto() {
        return concepto;
    }

    public String getObservaciones() {
        return observaciones;
    }

    @Override
    public String toString() {
        return "Fecha: " + fecha +
                ", Concepto: " + concepto +
                ", Observaciones: " + observaciones;
    }
}
