import proyecto_integrador.models.DatosLaborales;
import proyecto_integrador.models.Person;
import proyecto_integrador.models.SituacionAdministrativa;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                int val = Integer.parseInt(scanner.nextLine());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número entero válido.");
            }
        }
    }

    private ArrayList<Person> personas = new ArrayList<>();

    public static void main(String[] args) {
        Main app = new Main();

        int opcion;
        do {
            app.showMenu();
            opcion = Main.leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1:
                    app.menuPersonal();
                    break;
                case 2:
                    app.menuSituaciones();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    // Menu de opciones
    public void showMenu() {
        System.out.println("=========================================");
        System.out.println("  SISTEMA DE GESTIÓN DE PERSONAL PÚBLICO");
        System.out.println("=========================================");
        System.out.println("1. Gestión de Personal");
        System.out.println("2. Situación Administrativa");
        System.out.println("3. Control de Vacaciones");
        System.out.println("4. Beneficios e Incentivos");
        System.out.println("0. Salir");
        System.out.println("=========================================");
    }

    private void menuPersonal() {
        int op;
        do {
            System.out.println("--- GESTIÓN DE PERSONAL ---");
            System.out.println("1. Agregar Empleado");
            System.out.println("2. Ver Lista de Empleados");
            System.out.println("3. Buscar Empleado");
            System.out.println("0. Volver");
            op = Main.leerEntero("Opción: ");
            switch (op) {
                case 1:
                    createPerson();
                    break;
                case 2:
                    listarPersonas();
                    break;
                case 3:
                    buscarPersona();
                    break;
            }
        } while (op != 0);
    }

    private void menuSituaciones() {
        int op;
        do {
            System.out.println("--- SITUACIÓN ADMINISTRATIVA ---");
            System.out.println("1. Registrar Situación");
            System.out.println("2. Consultar Situación");
            System.out.println("0. Volver");
            op = Main.leerEntero("Opción: ");
            switch (op) {
                case 1:
                    registrarSituacion();
                    break;
                case 2:
                    consultarSituacion();
                    break;
            }
        } while (op != 0);
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

        Person persona = new Person(name, documentId, birthDate, gender, stateCivil, rh, email, createDatosLaborales());
        personas.add(persona);
        return persona;
    }

    // Listar las personas registradas en el sistema.
    private void listarPersonas() {
        System.out.println("==== Lista de Empleados Actual ====");
        if (personas.isEmpty()) {
            System.out.println("No hay empleados.");
            return;
        }
        for (Person p : personas) {
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("=".repeat(80));
            System.out.printf("| %-15s | %-30s | %-25s |%n", "Documento", "Nombre", "Email");
            System.out.println("-".repeat(80));
            System.out.printf("| %-15s | %-30s | %-25s |%n",
                    p.getDocumentId(), p.getName(), p.getEmail());
            System.out.println("-".repeat(80));
            System.out.printf("| %-12s | %-10s | %-12s | %-10s | %-15s |%n",
                    "F. Nacimiento", "Género", "Estado Civil", "RH", "Tipo Vinculación");
            System.out.println("-".repeat(80));
            System.out.printf("| %-12s | %-10s | %-12s | %-10s | %-15s |%n",
                    p.getBirthDate(), p.getGender(), p.getStateCivil(), p.getRh(),
                    p.getDatosLaborales().getTipoVinculacion());
            System.out.println("-".repeat(80));
            System.out.printf("| %-15s | %-12s | %-8s | %-8s | %-12s | %-10s |%n",
                    "Dependencia", "Cargo", "Código", "Grado", "F. Ingreso", "Asignación");
            System.out.println("-".repeat(80));
            System.out.printf("| %-15s | %-12s | %-8s | %-8s | %-12s | %-10.2f |%n",
                    p.getDatosLaborales().getDependencia(),
                    p.getDatosLaborales().getCargo(),
                    p.getDatosLaborales().getCodigo(),
                    p.getDatosLaborales().getGrado(),
                    p.getDatosLaborales().getFechaIngreso(),
                    p.getDatosLaborales().getAsignacionMensual());
            System.out.println("=".repeat(80));
        }
        System.out.println();
        System.out.println("Total empleados: " + personas.size());
        System.out.println();
    }

    // Busqueda de la persona
    public Person buscarPersona() {
        System.out.println("Ingrese el documento del servidor");
        String documentId = scanner.nextLine();
        Person p = buscarPersona(documentId);
        if (p == null) {
            System.out.println("Error: no se encontró una persona con ese documento.");
        } else {
            System.out.println(p);
        }
        return p;
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

        System.out.println("---- Situaciones de: " + doc + " ----");
        for (SituacionAdministrativa s : situaciones) {
            System.out.println(s);
        }
    }

    public Person buscarPersona(String documentId) {
        for (Person p : personas) {
            if (p.getDocumentId().equals(documentId)) {
                return p;
            }
        }
        return null;
    }
}