package proyecto_integrador.models;

public class DatosLaborales {

    String dependencia;
    String cargo;
    String codigo;
    String grado;
    String tipoVinculacion;
    String fechaIngreso;
    double asignacionMensual;

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

    // Verificacion de los datos ingresados por el usuario.
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