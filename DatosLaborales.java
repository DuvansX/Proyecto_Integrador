package proyecto_integrador.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DatosLaborales {

    // todos privados para proteger los datos laborales del empleado
    private final String dependencia;
    private final String cargo;
    private final String codigo;
    private final String grado;
    private final String tipoVinculacion;
    private final LocalDate fechaIngreso;
    private final BigDecimal asignacionMensual;

    public DatosLaborales(
            String dependencia,
            String cargo,
            String codigo,
            String grado,
            String tipoVinculacion,
            LocalDate fechaIngreso,
            BigDecimal asignacionMensual) {
        this.dependencia = dependencia;
        this.cargo = cargo;
        this.codigo = codigo;
        this.grado = grado;
        this.tipoVinculacion = tipoVinculacion;
        this.fechaIngreso = fechaIngreso;
        this.asignacionMensual = asignacionMensual;
    }

    // getters para acceder a cada dato laboral desde otras clases
    public String getDependencia() {
        return dependencia;
    }

    public String getCargo() {
        return cargo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getGrado() {
        return grado;
    }

    public String getTipoVinculacion() {
        return tipoVinculacion;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public BigDecimal getAsignacionMensual() {
        return asignacionMensual;
    }

    // muestra todos los datos laborales en texto plano, se usa en el toString de
    // Person
    @Override
    public String toString() {
        return "Dependencia: " + dependencia +
                ", Cargo: " + cargo +
                ", Código: " + codigo +
                ", Grado: " + grado +
                ", Tipo vinculación: " + tipoVinculacion +
                ", Fecha ingreso: " + fechaIngreso +
                ", Asignación mensual: " + asignacionMensual.toPlainString();
    }
}