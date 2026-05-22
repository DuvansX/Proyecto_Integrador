import proyecto_integrador.models.DatosLaborales;
import proyecto_integrador.models.Person;
import proyecto_integrador.models.SituacionAdministrativa;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    Scanner scanner = new Scanner(System.in);
    ArrayList<Person> personas = new ArrayList<>();

    public static void main(String[] args) {

        Main main = new Main(); // Creacion metodos no estaticos.
        boolean running = true; // verificar para que el programa siga corriendo.
        while (running) {
            main.showMenu();

            int option = main.scanner.nextInt();
            main.scanner.nextLine(); // limpiar buffer

            switch (option) {

                case 0:
                    // Salir
                    running = false;
                    System.out.println("Saliendo del programa...");
                    break;

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

                case 3:
                    // Registrar situación administrativa
                    main.registrarSituacion();
                    break;

                case 4:
                    // Consultar situación actual de un servidor
                    main.consultarSituacion();
                    break;

                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    // Menu de opciones
    public void showMenu() {
        System.out.println("----------- PANEL DE CONTROL -----------");
        System.out.println("0. Salir");
        System.out.println("1. Crear persona");
        System.out.println("2. Mostrar personas");
        System.out.println("3. Registrar situación administrativa");
        System.out.println("4. Consultar situación actual de un servidor");
        System.out.println("----------------------------------------");
    }

    // Creacion de la perosna con sus validaciones.
    public Person createPerson() {
        String name;
        while (true) {
            System.out.println("Ingrese el nombre de la persona");
            name = scanner.nextLine();
            if (name.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                break;
            }
            System.out.println(
                    "Error: el nombre no puede contener números ni símbolos. Por favor vuelva a ingresar el dato.");
        }

        String documentId;
        while (true) {
            System.out.println("Ingrese el documento de la persona");
            documentId = scanner.nextLine();
            if (documentId.matches("[0-9]+")) {
                break;
            }
            System.out.println("Error: el documento solo puede contener números. Por favor vuelva a ingresar el dato.");
        }

        String birthDate;
        while (true) {
            System.out.println("Ingrese la fecha de nacimiento (dd/MM/yyyy)");
            birthDate = scanner.nextLine();
            if (birthDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
                break;
            }
            System.out
                    .println("Error: formato de fecha inválido. Use dd/MM/yyyy. Por favor vuelva a ingresar el dato.");
        }

        String gender;
        while (true) {
            System.out.println("Ingrese el género");
            gender = scanner.nextLine();
            if (gender.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                break;
            }
            System.out.println(
                    "Error: el género no puede contener números ni símbolos. Por favor vuelva a ingresar el dato.");
        }

        String stateCivil;
        while (true) {
            System.out.println("Ingrese el estado civil");
            stateCivil = scanner.nextLine();
            if (stateCivil.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                break;
            }
            System.out.println(
                    "Error: el estado civil no puede contener números ni símbolos. Por favor vuelva a ingresar el dato.");
        }

        String rh;
        while (true) {
            System.out.println("Ingrese el RH");
            rh = scanner.nextLine();
            if (rh.matches("^(A|B|AB|O)[+-]$")) {
                break;
            }
            System.out.println(
                    "Error: RH inválido. Valores permitidos: A+, A-, B+, B-, AB+, AB-, O+, O-. Por favor vuelva a ingresar el dato.");
        }

        System.out.println("Ingrese el email");
        String email = scanner.nextLine();
        // Crear y retornar la persona al arraylist .
        return new Person(name, documentId, birthDate, gender, stateCivil, rh, email, createDatosLaborales());
    }

    // ASignacion datos laborales a la persona creada con sus validaciones.

    public DatosLaborales createDatosLaborales() {

        String dependencia;
        while (true) {
            System.out.println("Ingrese la dependencia");
            dependencia = scanner.nextLine();
            if (dependencia.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9 ]+"))
                break;
            System.out.println("Error: la dependencia no puede contener símbolos.");
        }

        String cargo;
        while (true) {
            System.out.println("Ingrese el cargo");
            cargo = scanner.nextLine();
            if (cargo.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+"))
                break;
            System.out.println("Error: el cargo no puede contener números ni símbolos.");
        }

        String codigo;
        while (true) {
            System.out.println("Ingrese el código");
            codigo = scanner.nextLine();
            if (codigo.matches("[0-9]+"))
                break;
            System.out.println("Error: el código solo puede contener números.");
        }

        String grado;
        while (true) {
            System.out.println("Ingrese el grado");
            grado = scanner.nextLine();
            if (grado.matches("[0-9]+"))
                break;
            System.out.println("Error: el grado solo puede contener números.");
        }

        String tipoVinculacion;
        while (true) {
            System.out.println("Ingrese el tipo de vinculación");
            tipoVinculacion = scanner.nextLine();
            if (tipoVinculacion.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+"))
                break;
            System.out.println("Error: el tipo de vinculación no puede contener números ni símbolos.");
        }

        String fechaIngreso;
        while (true) {
            System.out.println("Ingrese la fecha de ingreso (dd/MM/yyyy)");
            fechaIngreso = scanner.nextLine();
            if (fechaIngreso.matches("\\d{2}/\\d{2}/\\d{4}"))
                break;
            System.out.println("Error: formato inválido. Use dd/MM/yyyy.");
        }

        double asignacionMensual = 0;
        while (true) {
            System.out.println("Ingrese la asignación mensual");
            String input = scanner.nextLine();
            if (input.matches("\\d+(\\.\\d+)?")) {
                asignacionMensual = Double.parseDouble(input);
                break;
            }
            System.out.println("Error: la asignación mensual solo puede contener números.");
        }

        // Crear y retornar los datos laborales para asignarlos a la persona.

        return new DatosLaborales(dependencia, cargo, codigo, grado, tipoVinculacion, fechaIngreso, asignacionMensual);

    }

    // Registro de la situacion administrativa de un servidor.
    public void registrarSituacion() {
        System.out.println("Ingrese el documento del servidor");
        String doc = scanner.nextLine();

        Person persona = buscarPersona(doc);
        if (persona == null) {
            System.out.println("Error: no se encontró una persona con ese documento.");
            return;
        }

        System.out.println("Tipos de situación:");
        System.out.println("1. Vacaciones");
        System.out.println("2. Permiso 1 día");
        System.out.println("3. Permiso 2-3 días");
        System.out.println("4. Licencia remunerada");
        System.out.println("5. Licencia no remunerada");
        System.out.println("6. Licencia maternidad");
        System.out.println("7. Licencia paternidad");
        System.out.println("8. Licencia enfermedad");
        System.out.println("9. Encargo");
        System.out.println("10. Traslado");
        System.out.println("11. Comisión");

        String tipo;
        while (true) {
            System.out.println("Seleccione el tipo de situación (1-11)");
            String op = scanner.nextLine();
            switch (op) {
                case "1":
                    tipo = "Vacaciones";
                    break;
                case "2":
                    tipo = "Permiso 1 día";
                    break;
                case "3":
                    tipo = "Permiso 2-3 días";
                    break;
                case "4":
                    tipo = "Licencia remunerada";
                    break;
                case "5":
                    tipo = "Licencia no remunerada";
                    break;
                case "6":
                    tipo = "Licencia maternidad";
                    break;
                case "7":
                    tipo = "Licencia paternidad";
                    break;
                case "8":
                    tipo = "Licencia enfermedad";
                    break;
                case "9":
                    tipo = "Encargo";
                    break;
                case "10":
                    tipo = "Traslado";
                    break;
                case "11":
                    tipo = "Comisión";
                    break;
                default:
                    tipo = null;
            }
            if (tipo != null)
                break;
            System.out.println("Error: opción inválida.");
        }


        String fechaInicio;
        while (true) {
            System.out.println("Ingrese la fecha de inicio (dd/MM/yyyy)");
            fechaInicio = scanner.nextLine();
            if (fechaInicio.matches("\\d{2}/\\d{2}/\\d{4}"))
                break;
            System.out.println("Error: formato inválido. Use dd/MM/yyyy.");
        }

        String fechaFin;
        while (true) {
            System.out.println("Ingrese la fecha de fin (dd/MM/yyyy)");
            fechaFin = scanner.nextLine();
            if (fechaFin.matches("\\d{2}/\\d{2}/\\d{4}"))
                break;
            System.out.println("Error: formato inválido. Use dd/MM/yyyy.");
        }

        // Regla de negocio crítica
        if (persona.tieneSituacionActivaEnFecha(fechaInicio, fechaFin)) {
            System.out.println("Error: el servidor ya tiene una situación administrativa activa en esas fechas.");
            return;
        }

        persona.getSituaciones().add(new SituacionAdministrativa(tipo, fechaInicio, fechaFin));
        System.out.println("Situación registrada correctamente.");
    }

    public void consultarSituacion() {
        System.out.println("Ingrese el documento del servidor");
        String doc = scanner.nextLine();

        Person persona = buscarPersona(doc);
        if (persona == null) {
            System.out.println("Error: no se encontró una persona con ese documento.");
            return;
        }

        ArrayList<SituacionAdministrativa> situaciones = persona.getSituaciones();
        if (situaciones.isEmpty()) {
            System.out.println("La persona no tiene situaciones registradas.");
            return;
        }

        System.out.println("---- Situaciones de5: " + doc + " ----");
        for (SituacionAdministrativa s : situaciones) { // muestra las situaciones administrativas de la persona buscada
            System.out.println(s);
        }
    }

    public Person buscarPersona(String documentId) {
        for (Person p : personas) { // <-- busca las personas 
            if (p.getDocumentId().equals(documentId)) { // compara el documento ingresado con el de cada persona
                return p; //retorna la persona si encuentra una coincidencia
            }
        }
        return null; // si no lo encuentra retoneara nulll y se muestra el mesje de la linea 2263
    }
}