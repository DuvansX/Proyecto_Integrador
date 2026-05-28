package proyecto_integrador.models;

import java.util.ArrayList;

public class Person {

    // todos los atributos son privados, se acceden solo por getters
    private final String name;
    private final String documentId;
    private final String birthDate;
    private final String gender;
    private final String stateCivil;
    private final String rh;
    private final String email;
    private final DatosLaborales datosLaborales;
    // lista de situaciones administrativas que ha tenido la persona
    private final ArrayList<SituacionAdministrativa> situaciones = new ArrayList<>();
    // lista de incentivos y beneficios del plan de bienestar recibidos por la
    // persona
    private final ArrayList<Incentivo> incentivos = new ArrayList<>();
    // lista de evaluaciones médicas ocupacionales de la persona
    private final ArrayList<SaludOcupacional> evaluacionesMedicas = new ArrayList<>();

    public Person(
            String name,
            String documentId,
            String birthDate,
            String gender,
            String stateCivil,
            String rh,
            String email,
            DatosLaborales datosLaborales) {
        this.name = name;
        this.documentId = documentId;
        this.birthDate = birthDate;
        this.gender = gender;
        this.stateCivil = stateCivil;
        this.rh = rh;
        this.email = email;
        this.datosLaborales = datosLaborales;
    }

    // getters para leer cada dato personal desde otras clases
    public String getName() {
        return name;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public String getStateCivil() {
        return stateCivil;
    }

    public String getRh() {
        return rh;
    }

    public String getEmail() {
        return email;
    }

    public DatosLaborales getDatosLaborales() {
        return datosLaborales;
    }

    public ArrayList<SituacionAdministrativa> getSituaciones() {
        return situaciones;
    }

    public ArrayList<Incentivo> getIncentivos() {
        return incentivos;
    }

    public ArrayList<SaludOcupacional> getEvaluacionesMedicas() {
        return evaluacionesMedicas;
    }

    // verifica si la persona ya tiene una situación activa que se cruce con las
    // fechas dadas
    // esto evita registrar dos situaciones en el mismo rango de fechas
    public boolean tieneSituacionActivaEnFecha(String fechaInicio, String fechaFin) {
        for (SituacionAdministrativa s : situaciones) {
            if (s.isActiva() && seFechasSolapan(s.getFechaInicio(), s.getFechaFin(), fechaInicio, fechaFin)) {
                return true;
            }
        }
        return false;
    }

    // compara dos rangos de fechas para ver si se solapan entre sí
    // si el fin del rango 2 es antes del inicio del 1, o el fin del 1 es antes del
    // inicio del 2, no se solapan
    private boolean seFechasSolapan(String ini1, String fin1, String ini2, String fin2) {
        return !(fin2.compareTo(ini1) < 0 || fin1.compareTo(ini2) < 0);
    }

    // muestra todos los datos de la persona en texto plano para debug o búsqueda
    @Override
    public String toString() {
        return "Nombre: " + name +
                ", Documento: " + documentId +
                ", Fecha nacimiento: " + birthDate +
                ", Género: " + gender +
                ", Estado civil: " + stateCivil +
                ", RH: " + rh +
                ", Email: " + email +
                ", Datos laborales: " + datosLaborales +
                ", Situaciones administrativas: " + situaciones +
                ", Incentivos: " + incentivos +
                ", Evaluaciones médicas: " + evaluacionesMedicas;
    }
}