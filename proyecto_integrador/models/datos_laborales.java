package proyecto_integrador.models;

public class datos_laborales {
    public String dependencia;
    public String cargo;
    public String código;
    public String grado;
    public String tipo_de_vinculación;
    public String fecha_de_ingreso;
    public String asignación_mensual;

    public datos_laborales(
            String department,
            String position,
            String code,
            String grade,
            String vinculation_type,
            String joining_date,
            String monthly_salary) {
        this.dependencia = department;
        this.cargo = position;
        this.código = code;
        this.grado = grade;
        this.tipo_de_vinculación = vinculation_type;
        this.fecha_de_ingreso = joining_date;
        this.asignación_mensual = monthly_salary;
    }
}