package proyecto_integrador.models;

import java.util.ArrayList;

public class Person {

    private String name;
    private String documentId;
    private String birthDate;
    private String gender;
    private String stateCivil;
    private String rh;
    private String email;
    private DatosLaborales datosLaborales;
    private ArrayList<SituacionAdministrativa> situaciones = new ArrayList<>();

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

    public String getName() { return name; }
    public String getDocumentId() { return documentId; }
    public String getBirthDate() { return birthDate; }
    public String getGender() { return gender; }
    public String getStateCivil() { return stateCivil; }
    public String getRh() { return rh; }
    public String getEmail() { return email; }
    public DatosLaborales getDatosLaborales() { return datosLaborales; }
    public ArrayList<SituacionAdministrativa> getSituaciones() { return situaciones; }

    public boolean tieneSituacionActivaEnFecha(String fechaInicio, String fechaFin) {
        for (SituacionAdministrativa s : situaciones) {
            if (s.isActiva() && seFechasSolapan(s.getFechaInicio(), s.getFechaFin(), fechaInicio, fechaFin)) {
                return true;
            }
        }
        return false;
    }

    private boolean seFechasSolapan(String ini1, String fin1, String ini2, String fin2) {
        return !(fin2.compareTo(ini1) < 0 || fin1.compareTo(ini2) < 0);
    }

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
                ", Situaciones administrativas: " + situaciones;
    }
}