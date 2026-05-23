package proyecto_integrador.models;

public class DatosLaborales {

    private String dependencia;
    private String cargo;
    private String codigo;
    private String grado;
    private String tipoVinculacion;
    private String fechaIngreso;
    private double asignacionMensual;

    public DatosLaborales(
            String dependencia,
            String cargo,
            String codigo,
            String grado,
            String tipoVinculacion,
            String fechaIngreso,
            double asignacionMensual) {
        this.dependencia = dependencia;
        this.cargo = cargo;
        this.codigo = codigo;
        this.grado = grado;
        this.tipoVinculacion = tipoVinculacion;
        this.fechaIngreso = fechaIngreso;
        this.asignacionMensual = asignacionMensual;
    }

    public String getDependencia() { return dependencia; }
    public String getCargo() { return cargo; }
    public String getCodigo() { return codigo; }
    public String getGrado() { return grado; }
    public String getTipoVinculacion() { return tipoVinculacion; }
    public String getFechaIngreso() { return fechaIngreso; }
    public double getAsignacionMensual() { return asignacionMensual; }

    @Override
    public String toString() {
        return "Dependencia: " + dependencia +
                ", Cargo: " + cargo +
                ", Código: " + codigo +
                ", Grado: " + grado +
                ", Tipo vinculación: " + tipoVinculacion +
                ", Fecha ingreso: " + fechaIngreso +
                ", Asignación mensual: " + asignacionMensual;
    }
}