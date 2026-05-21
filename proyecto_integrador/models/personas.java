package proyecto_integrador.models;

public class personas {
    public String nombre;
    public String cédula;
    public String fecha_de_nacimiento;
    public String sexo;
    public String estado_civil;
    public String rh;
    public String email;

    public personas(
            String name,
            String id,
            String birth_date,
            String gender,
            String marital_status,
            String blood_type,
            String contacto) {
        this.nombre = name;
        this.cédula = id;
        this.fecha_de_nacimiento = birth_date;
        this.sexo = gender;
        this.estado_civil = marital_status;
        this.rh = blood_type;
        this.email = contacto;
    }
}