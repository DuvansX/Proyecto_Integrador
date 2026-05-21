package proyec;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    Scanner scanner = new Scanner(System.in);
    ArrayList<Person> personas = new ArrayList<>();

    public static void main(String[] args) {

        Main main = new Main();

        main.showMenu();

        int option = main.scanner.nextInt();
        main.scanner.nextLine(); // limpiar buffer

        switch (option) {

            case 1:
                // Crear persona
                Person person = main.createPerson();
                main.personas.add(person);

                System.out.println("Persona creada correctamente");
                break;

            case 2:
                // Mostrar personas
                for (Person p : main.personas) {
                    System.out.println(p);
                }
                break;

            default:
                System.out.println("Opción inválida");
        }
    }

    public void showMenu() {
        System.out.println("----------- PANEL DE CONTROL -----------");
        System.out.println("1. Crear persona");
        System.out.println("2. Mostrar personas");
        System.out.println("----------------------------------------");
    }

    public Person createPerson() {

        System.out.println("Ingrese el nombre de la persona");
        String name = scanner.nextLine();

        System.out.println("Ingrese el documento de la persona");
        String documentId = scanner.nextLine();

        System.out.println("Ingrese la fecha de nacimiento");
        String birthDate = scanner.nextLine();

        System.out.println("Ingrese el género");
        String gender = scanner.nextLine();

        System.out.println("Ingrese el estado civil");
        String stateCivil = scanner.nextLine();

        System.out.println("Ingrese el RH");
        String rh = scanner.nextLine();

        System.out.println("Ingrese el email");
        String email = scanner.nextLine();

        return new Person(
                name,
                documentId,
                birthDate,
                gender,
                stateCivil,
                rh,
                email
        );
    }
}

class Person {

    String name;
    String documentId;
    String birthDate;
    String gender;
    String stateCivil;
    String rh;
    String email;

    public Person(
            String name,
            String documentId,
            String birthDate,
            String gender,
            String stateCivil,
            String rh,
            String email
    ) {
        this.name = name;
        this.documentId = documentId;
        this.birthDate = birthDate;
        this.gender = gender;
        this.stateCivil = stateCivil;
        this.rh = rh;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Nombre: " + name +
                ", Documento: " + documentId +
                ", Fecha nacimiento: " + birthDate +
                ", Género: " + gender +
                ", Estado civil: " + stateCivil +
                ", RH: " + rh +
                ", Email: " + email;
    }
}
