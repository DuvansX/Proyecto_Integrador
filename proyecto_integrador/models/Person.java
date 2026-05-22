package proyecto_integrador.models;

import java.util.ArrayList;

public class Person {

    String name;
    String documentId;
    String birthDate;
    String gender;
    String stateCivil;
    String rh;
    String email;
    DatosLaborales datosLaborales;
    ArrayList<SituacionAdministrativa> situaciones = new ArrayList<>();

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

    public String getDocumentId() { return documentId; }
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



    // Verificacion de los datos ingresados por el usuario.
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