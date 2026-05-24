import proyecto_integrador.models.DatosLaborales;
import proyecto_integrador.models.Person;
import proyecto_integrador.models.SituacionAdministrativa;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static final String RUTA_ARCHIVO = "lib/personas.txt";
    private ArrayList<Person> personas = new ArrayList<>();
    // Códigos de color para la terminal
    private static final String VERDE = "001B[32m";
    private static final String ROJO = "001B[31m";
    private static final String RESET = "001B[0m";

    // ===================== MAIN =====================

    public static void main(String[] args) {
        Main app = new Main();
        app.cargarDatos();
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
                case 3:
                    app.menuVacaciones();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    // ===================== MENÚS =====================

    public void showMenu() {
        System.out.println("=========================================");
        System.out.println("  SISTEMA DE GESTIÓN DE PERSONAL PÚBLICO  ");
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
            System.out.println("================================");
            System.out.println("      GESTIÓN DE PERSONAL       ");
            System.out.println("================================");
            System.out.println("1. Agregar Empleado");
            System.out.println("2. Ver Lista de Empleados");
            System.out.println("3. Buscar Empleado");
            System.out.println("0. Volver");
            System.out.println("================================");
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
            System.out.println("================================");
            System.out.println("   SITUACIÓN ADMINISTRATIVA   ");
            System.out.println("================================");
            System.out.println("1. Registrar Situación");
            System.out.println("2. Consultar Situación");
            System.out.println("3. Finalizar Situación");
            System.out.println("0. Volver");
            System.out.println("===============================");
            op = Main.leerEntero("Opción: ");
            switch (op) {
                case 1:
                    registrarSituacion();
                    break;
                case 2:
                    consultarSituacion();
                    break;
                case 3:
                    finalizarSituacion();
                    break;
                case 0:
                    return;
            }
        } while (op != 0);
    }

    // Menú principal de vacaciones con todas sus opciones
    private void menuVacaciones() {
        int op;
        do {
            System.out.println("================================");
            System.out.println("     CONTROL DE VACACIONES      ");
            System.out.println("================================");
            System.out.println("1. Solicitar Vacaciones");
            System.out.println("2. Aprobar / Rechazar Solicitud");
            System.out.println("3. Ver Historial y Dias Disponibles");
            System.out.println("4. Alertas de periodos adeudados");
            System.out.println("5. Personas que deben salir de vacaciones");
            System.out.println("0. Volver");
            System.out.println("================================");
            op = Main.leerEntero("Opción: ");
            switch (op) {
                case 1:
                    solicitarVacaciones();
                    break;
                case 2:
                    aprobarRechazarVacaciones();
                    break;
                case 3:
                    historialVacaciones();
                    break;
                case 4:
                    alertasVacacionesAdeudadas();
                    break;
                case 5:
                    personasDebeSalirVacaciones();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (op != 0);
    }

    // ===================== GESTIÓN DE PERSONAL =====================

    public Person createPerson() {
        String name;
        while (true) {
            System.out.println("Ingrese el nombre de la persona");
            name = scanner.nextLine();
            if (name.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+"))
                break;
            System.out.println(
                    "Error: el nombre no puede contener números ni símbolos. Por favor vuelva a ingresar el dato.");
        }

        String documentId;
        while (true) {
            System.out.println("Ingrese el documento de la persona");
            documentId = scanner.nextLine();
            if (documentId.matches("[0-9]+"))
                break;
            System.out.println("Error: el documento solo puede contener números. Por favor vuelva a ingresar el dato.");
        }

        String birthDate;
        while (true) {
            System.out.println("Ingrese la fecha de nacimiento (dd/MM/yyyy)");
            birthDate = scanner.nextLine();
            if (birthDate.matches("\\d{2}/\\d{2}/\\d{4}"))
                break;
            System.out
                    .println("Error: formato de fecha inválido. Use dd/MM/yyyy. Por favor vuelva a ingresar el dato.");
        }

        String gender;
        while (true) {
            System.out.println("Ingrese el género");
            gender = scanner.nextLine();
            if (gender.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+"))
                break;
            System.out.println(
                    "Error: el género no puede contener números ni símbolos. Por favor vuelva a ingresar el dato.");
        }

        String stateCivil;
        while (true) {
            System.out.println("Ingrese el estado civil");
            stateCivil = scanner.nextLine();
            if (stateCivil.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+"))
                break;
            System.out.println(
                    "Error: el estado civil no puede contener números ni símbolos. Por favor vuelva a ingresar el dato.");
        }

        String rh;
        while (true) {
            System.out.println("Ingrese el RH");
            rh = scanner.nextLine();
            if (rh.matches("^(A|B|AB|O)[+-]$"))
                break;
            System.out.println(
                    "Error: RH inválido. Valores permitidos: A+, A-, B+, B-, AB+, AB-, O+, O-. Por favor vuelva a ingresar el dato.");
        }

        System.out.println("Ingrese el email");
        String email = scanner.nextLine();

        Person persona = new Person(name, documentId, birthDate, gender, stateCivil, rh, email, createDatosLaborales());
        personas.add(persona);
        guardarDatos();
        System.out.println("Persona creada exitosamente.");
        return persona;
    }

    private void listarPersonas() {
        System.out.println("==== Lista de Empleados Actual ====");
        if (personas.isEmpty()) {
            System.out.println("No hay empleados.");
            return;
        }
        for (Person p : personas) {
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

    public Person buscarPersona() {
        System.out.println("Ingrese el documento de la persona");
        String documentId = scanner.nextLine();
        Person p = buscarPersona(documentId);
        if (p == null) {
            System.out.println("Error: no se encontró una persona con ese documento.");
        } else {
            System.out.println(p);
        }
        return p;
    }

    public Person buscarPersona(String documentId) {
        for (Person p : personas) {
            if (p.getDocumentId().equals(documentId)) {
                return p;
            }
        }
        return null;
    }

    // ===================== DATOS LABORALES =====================

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

    // ===================== SITUACIÓN ADMINISTRATIVA =====================

    public void registrarSituacion() {
        System.out.println("Ingrese el documento de la persona");
        String doc = scanner.nextLine();

        Person persona = buscarPersona(doc);
        if (persona == null) {
            System.out.println("Error: no se encontró una persona con ese documento.");
            return;
        }

        System.out.println("===============================");
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
        System.out.println("0. Volver");
        System.out.println("===============================");
        System.err.println();

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
                case "0":
                    return;
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
            System.out.println("Error: la persona ya tiene una situación administrativa activa en esas fechas.");
            return;
        }

        persona.getSituaciones().add(new SituacionAdministrativa(tipo, fechaInicio, fechaFin));
        System.out.println("Situación registrada correctamente.");
        guardarDatos();
    }

    public void consultarSituacion() {
        System.out.println("Ingrese el documento de la persona");
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

        System.out.println("==== Situaciones de: " + doc + " ====");
        for (SituacionAdministrativa s : situaciones) {
            System.out.println(s);
        }
    }

    public void finalizarSituacion() {
        System.out.println("Ingrese el documento de la persona");
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

        System.out.println("==== Situaciones de: " + doc + " ==== ");
        for (int i = 0; i < situaciones.size(); i++) {
            SituacionAdministrativa s = situaciones.get(i);
            System.out.printf("%d. %s%n", i + 1, s);
        }

        int op;
        while (true) {
            op = Main.leerEntero("Seleccione la situación a finalizar (0 para cancelar): ");
            if (op == 0)
                return;
            if (op > 0 && op <= situaciones.size())
                break;
            System.out.println("Error: opción inválida.");
        }

        SituacionAdministrativa seleccionada = situaciones.get(op - 1);
        if (!seleccionada.isActiva()) {
            System.out.println("Error: esa situación ya está finalizada.");
            return;
        }

        seleccionada.setActiva(false);
        System.out.println("Situación finalizada correctamente.");
        guardarDatos();
    }

    // ===================== CONTROL DE VACACIONES =====================

    // Mira cuántos días de vacaciones ha ganado la persona según los años que
    // lleva trabajando. En Colombia la ley dice que por cada año completo
    // trabajado se tienen 15 días de vacaciones.
    private int calcularDiasAcumulados(Person p) {
        // La fecha de ingreso viene guardada como texto "dd/MM/yyyy",
        // entonces la partimos por "/" para sacar el día, mes y año por separado
        String[] partes = p.getDatosLaborales().getFechaIngreso().split("/");
        LocalDate ingreso = LocalDate.of(
                Integer.parseInt(partes[2]), // año
                Integer.parseInt(partes[1]), // mes
                Integer.parseInt(partes[0]));// día

        // Contamos cuántos años completos han pasado desde que entró hasta hoy
        long anios = ChronoUnit.YEARS.between(ingreso, LocalDate.now());

        // Multiplicamos los años completos por 15 para saber los días ganados
        return (int) anios * 15;
    }

    // Mira cuántos días de vacaciones ya disfrutó la persona.
    // Solo cuenta las vacaciones que ya fueron aprobadas (activa = false),
    // no las que están pendientes de aprobación.
    private int calcularDiasDisfrutados(Person p) {
        int total = 0;
        for (SituacionAdministrativa s : p.getSituaciones()) {
            // Solo cuenta si es de tipo Vacaciones Y ya fue aprobada (activa=false)
            if (s.getTipo().equalsIgnoreCase("Vacaciones") && !s.isActiva()) {
                total += diasEntreFechas(s.getFechaInicio(), s.getFechaFin());
            }
        }
        return total;
    }

    // Calcula cuántos días de vacaciones le quedan a la persona disponibles.
    // Es simplemente lo que ganó menos lo que ya usó.
    private int calcularDiasPendientes(Person p) {
        return calcularDiasAcumulados(p) - calcularDiasDisfrutados(p);
    }

    // Cuenta cuántos días hay entre dos fechas, incluyendo el primer y último día.
    // Por ejemplo del 01/05 al 05/05 son 5 días, no 4.
    private int diasEntreFechas(String fechaInicio, String fechaFin) {
        String[] i = fechaInicio.split("/");
        String[] f = fechaFin.split("/");
        LocalDate inicio = LocalDate.of(
                Integer.parseInt(i[2]), Integer.parseInt(i[1]), Integer.parseInt(i[0]));
        LocalDate fin = LocalDate.of(
                Integer.parseInt(f[2]), Integer.parseInt(f[1]), Integer.parseInt(f[0]));
        // +1 para incluir el día de inicio en la cuenta
        return (int) ChronoUnit.DAYS.between(inicio, fin) + 1;
    }

    // Convierte una fecha de LocalDate al formato de texto dd/MM/yyyy
    // que es el formato que usa el resto del código para guardar fechas.
    private String localDateAString(LocalDate fecha) {
        return String.format("%02d/%02d/%04d",
                fecha.getDayOfMonth(), fecha.getMonthValue(), fecha.getYear());
    }

    // Permite a la persona pedir un período de vacaciones.
    // Verifica que tenga días disponibles, que las fechas sean válidas
    // y que no tenga otra situación registrada en esas mismas fechas.
    // La solicitud queda como pendiente hasta que alguien la apruebe o rechace.
    private void solicitarVacaciones() {
        System.out.println("Ingrese el documento de la persona");
        String doc = scanner.nextLine();

        Person persona = buscarPersona(doc);
        if (persona == null) {
            System.out.println("Error: no se encontró una persona con ese documento.");
            return;
        }

        // Mostramos el resumen de días antes de que ingrese las fechas
        int disponibles = calcularDiasPendientes(persona);
        System.out.println("--------------------------------------");
        System.out.println("Persona    : " + persona.getName());
        System.out.println("Acumulados : " + calcularDiasAcumulados(persona) + " días");
        System.out.println("Disfrutados: " + calcularDiasDisfrutados(persona) + " días");
        System.out.println("Disponibles: " + disponibles + " días");
        System.out.println("--------------------------------------");

        // Si no tiene días disponibles no tiene sentido continuar
        if (disponibles <= 0) {
            System.out.println("Error: la persona no tiene días de vacaciones disponibles.");
            return;
        }

        String fechaInicio;
        while (true) {
            System.out.println("Ingrese la fecha de inicio de vacaciones (dd/MM/yyyy)");
            fechaInicio = scanner.nextLine();
            if (fechaInicio.matches("\\d{2}/\\d{2}/\\d{4}"))
                break;
            System.out.println("Error: formato inválido. Use dd/MM/yyyy.");
        }

        String fechaFin;
        while (true) {
            System.out.println("Ingrese la fecha de fin de vacaciones (dd/MM/yyyy)");
            fechaFin = scanner.nextLine();
            if (fechaFin.matches("\\d{2}/\\d{2}/\\d{4}"))
                break;
            System.out.println("Error: formato inválido. Use dd/MM/yyyy.");
        }

        int diasSolicitados = diasEntreFechas(fechaInicio, fechaFin);

        // La fecha de fin no puede ser antes que la de inicio
        if (diasSolicitados <= 0) {
            System.out.println("Error: la fecha de fin debe ser posterior a la de inicio.");
            return;
        }

        // No puede pedir más días de los que tiene disponibles
        if (diasSolicitados > disponibles) {
            System.out.println("Error: los días solicitados (" + diasSolicitados +
                    ") superan los disponibles (" + disponibles + ").");
            return;
        }

        // No puede pedir vacaciones en fechas donde ya tiene otra situación registrada
        if (persona.tieneSituacionActivaEnFecha(fechaInicio, fechaFin)) {
            System.out.println("Error: la persona ya tiene una situación administrativa activa en esas fechas.");
            return;
        }

        // Se guarda como situación tipo "Vacaciones" con activa=true
        // que significa que está pendiente de aprobación
        persona.getSituaciones().add(new SituacionAdministrativa("Vacaciones", fechaInicio, fechaFin));
        guardarDatos();
        System.out.println("Solicitud de vacaciones registrada correctamente (" + diasSolicitados + " días).");
        System.out.println("Estado: Pendiente de aprobación.");
    }

    // Permite aprobar o rechazar una solicitud de vacaciones que está pendiente.
    // Si se aprueba, la situación queda con activa=false y los días se descuentan
    // del saldo.
    // Si se rechaza, la solicitud se borra como si nunca hubiera existido.
    private void aprobarRechazarVacaciones() {
        System.out.println("Ingrese el documento de la persona");
        String doc = scanner.nextLine();

        Person persona = buscarPersona(doc);
        if (persona == null) {
            System.out.println("Error: no se encontró una persona con ese documento.");
            return;
        }

        // Buscamos solo las solicitudes de vacaciones que aún no han sido revisadas
        // (activa=true)
        ArrayList<SituacionAdministrativa> pendientes = new ArrayList<>();
        for (SituacionAdministrativa s : persona.getSituaciones()) {
            if (s.getTipo().equalsIgnoreCase("Vacaciones") && s.isActiva()) {
                pendientes.add(s);
            }
        }

        if (pendientes.isEmpty()) {
            System.out.println("La persona no tiene solicitudes de vacaciones pendientes.");
            return;
        }

        // Mostramos las solicitudes pendientes para que el usuario elija cuál revisar
        System.out.println("==== Solicitudes pendientes de: " + persona.getName() + " ====");
        for (int i = 0; i < pendientes.size(); i++) {
            SituacionAdministrativa s = pendientes.get(i);
            int dias = diasEntreFechas(s.getFechaInicio(), s.getFechaFin());
            System.out.printf("%d. Del %s al %s — %d días%n",
                    i + 1, s.getFechaInicio(), s.getFechaFin(), dias);
        }

        int op;
        while (true) {
            op = Main.leerEntero("Seleccione la solicitud (0 para cancelar): ");
            if (op == 0)
                return;
            if (op >= 1 && op <= pendientes.size())
                break;
            System.out.println("Error: opción inválida.");
        }

        SituacionAdministrativa seleccionada = pendientes.get(op - 1);

        System.out.println("1. Aprobar");
        System.out.println("2. Rechazar");
        int decision = Main.leerEntero("Opción: ");

        if (decision == 1) {
            // Al aprobar se pone activa=false, así el sistema ya la cuenta como disfrutada
            seleccionada.setActiva(false);
            guardarDatos();
            System.out.println("Vacaciones aprobadas correctamente.");
        } else if (decision == 2) {
            // Al rechazar se elimina la solicitud para que no afecte el saldo
            persona.getSituaciones().remove(seleccionada);
            guardarDatos();
            System.out.println("Solicitud rechazada y eliminada.");
        } else {
            System.out.println("Opción no válida. No se realizó ningún cambio.");
        }
    }

    // Muestra el historial completo de vacaciones de una persona:
    // cuántos días ha ganado, cuántos ha usado y cuántos le quedan,
    // además de cada período registrado con sus fechas y estado.
    private void historialVacaciones() {
        System.out.println("Ingrese el documento de la persona");
        String doc = scanner.nextLine();

        Person persona = buscarPersona(doc);
        if (persona == null) {
            System.out.println("Error: no se encontró una persona con ese documento.");
            return;
        }

        int acumulados = calcularDiasAcumulados(persona);
        int disfrutados = calcularDiasDisfrutados(persona);
        int pendientes = calcularDiasPendientes(persona);

        System.out.println("=".repeat(60));
        System.out.println("  HISTORIAL DE VACACIONES — " + persona.getName());
        System.out.println("=".repeat(60));
        System.out.printf("  Fecha de ingreso   : %s%n", persona.getDatosLaborales().getFechaIngreso());
        System.out.printf("  Días acumulados    : %d%n", acumulados);
        System.out.printf("  Días disfrutados   : %d%n", disfrutados);
        System.out.printf("  Días disponibles   : %d%n", pendientes);

        // Si tiene más de 15 días sin usar, le mostramos una advertencia
        if (pendientes > 15) {
            System.out.println("=== ALERTA: adeuda más de un período de vacaciones ===");
        }

        System.out.println("-".repeat(60));
        System.out.println("  PERÍODOS REGISTRADOS");
        System.out.println("-".repeat(60));

        // Recorremos todas las situaciones y mostramos solo las de tipo Vacaciones
        boolean hay = false;
        for (SituacionAdministrativa s : persona.getSituaciones()) {
            if (s.getTipo().equalsIgnoreCase("Vacaciones")) {
                int dias = diasEntreFechas(s.getFechaInicio(), s.getFechaFin());
                // Si activa=true está pendiente, si activa=false ya fue disfrutada
                // Si activa=true está pendiente (verde), si activa=false ya fue disfrutada
                // (rojo)
                String estado = s.isActiva()
                        ? VERDE + "Pendiente aprobación" + RESET
                        : ROJO + "Disfrutada" + RESET;
                System.out.printf("  Del %s al %s — %d días — %s%n",
                        s.getFechaInicio(), s.getFechaFin(), dias, estado);
                hay = true;
            }
        }
        if (!hay)
            System.out.println("  Sin períodos registrados.");
        System.out.println("=".repeat(60));
    }

    // Recorre todas las personas y muestra solo las que tienen
    // más de 15 días de vacaciones sin tomar, o sea, las que ya
    // acumularon más de un período completo sin descansar.
    private void alertasVacacionesAdeudadas() {
        System.out.println("=".repeat(60));
        System.out.println("  ALERTAS — MAS DE UN PERIODO DE VACACIONES ADEUDADO");
        System.out.println("=".repeat(60));

        boolean hay = false;
        for (Person p : personas) {
            int pendientes = calcularDiasPendientes(p);
            // Si tiene más de 15 días sin usar, aparece en la alerta
            if (pendientes > 15) {
                System.out.printf("  %-30s | Doc: %-12s | Pendientes: %d días%n",
                        p.getName(), p.getDocumentId(), pendientes);
                hay = true;
            }
        }
        if (!hay)
            System.out.println("  Ninguna persona adeuda más de un período.");
        System.out.println("=".repeat(60));
    }

    // Muestra las personas que ya tienen 15 o más días de vacaciones
    // acumuladas y aún no están disfrutándolas hoy.
    // Estas son las que el sistema recomienda que salgan de vacaciones pronto.
    private void personasDebeSalirVacaciones() {
        LocalDate hoy = LocalDate.now();
        String hoyStr = localDateAString(hoy); // convertimos la fecha de hoy al formato dd/MM/yyyy

        System.out.println("=".repeat(60));
        System.out.println("  PERSONAS QUE DEBEN SALIR DE VACACIONES");
        System.out.println("=".repeat(60));

        boolean hay = false;
        for (Person p : personas) {
            int pendientes = calcularDiasPendientes(p);

            // Si tiene menos de 15 días pendientes, aún no es urgente que salga
            if (pendientes < 15)
                continue;

            // Verificamos si ya está de vacaciones hoy para no listarla de nuevo
            boolean enVacacionesHoy = false;
            for (SituacionAdministrativa s : p.getSituaciones()) {
                // Buscamos una situación de Vacaciones activa que cubra el día de hoy
                if (s.getTipo().equalsIgnoreCase("Vacaciones") && s.isActiva()) {
                    if (hoyStr.compareTo(s.getFechaInicio()) >= 0
                            && hoyStr.compareTo(s.getFechaFin()) <= 0) {
                        enVacacionesHoy = true;
                        break;
                    }
                }
            }

            // Solo la mostramos si NO está ya de vacaciones hoy
            if (!enVacacionesHoy) {
                System.out.printf("  %-30s | Doc: %-12s | Días pendientes: %d%n",
                        p.getName(), p.getDocumentId(), pendientes);
                hay = true;
            }
        }
        if (!hay)
            System.out.println("  Ninguna persona pendiente de salir de vacaciones.");
        System.out.println("=".repeat(60));
    }

    // ===================== ARCHIVO DE DATOS =====================

    private void guardarDatos() {
        File carpeta = new File("lib");
        carpeta.mkdirs();
        System.out.println("Guardando en: " + new File(RUTA_ARCHIVO).getAbsolutePath());
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA_ARCHIVO))) {
            for (Person p : personas) {
                pw.println(p.getName() + "," +
                        p.getDocumentId() + "," +
                        p.getBirthDate() + "," +
                        p.getGender() + "," +
                        p.getStateCivil() + "," +
                        p.getRh() + "," +
                        p.getEmail() + "," +
                        p.getDatosLaborales().getDependencia() + "," +
                        p.getDatosLaborales().getCargo() + "," +
                        p.getDatosLaborales().getCodigo() + "," +
                        p.getDatosLaborales().getGrado() + "," +
                        p.getDatosLaborales().getTipoVinculacion() + "," +
                        p.getDatosLaborales().getFechaIngreso() + "," +
                        p.getDatosLaborales().getAsignacionMensual());
            }
            System.out.println("Datos guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter("lib/situaciones.txt"))) {
            for (Person p : personas) {
                for (SituacionAdministrativa s : p.getSituaciones()) {
                    pw.println(p.getDocumentId() + "," +
                            s.getTipo() + "," +
                            s.getFechaInicio() + "," +
                            s.getFechaFin() + "," +
                            s.isActiva());
                }
            }
        } catch (IOException e) {
            System.out.println("Error al guardar situaciones: " + e.getMessage());
        }
    }

    private void cargarDatos() {
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists())
            return;
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(",");
                personas.add(new Person(
                        d[0], d[1], d[2], d[3], d[4], d[5], d[6],
                        new DatosLaborales(d[7], d[8], d[9], d[10], d[11], d[12],
                                Double.parseDouble(d[13]))));
            }
            System.out.println("Datos cargados: " + personas.size() + " empleados.");
        } catch (IOException e) {
            System.out.println("Error al cargar: " + e.getMessage());
        }

        File archivoSituaciones = new File("lib/situaciones.txt");
        if (!archivoSituaciones.exists())
            return;
        try (BufferedReader br = new BufferedReader(new FileReader("lib/situaciones.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(",");
                Person persona = buscarPersona(d[0]);
                if (persona != null) {
                    SituacionAdministrativa s = new SituacionAdministrativa(d[1], d[2], d[3]);
                    s.setActiva(Boolean.parseBoolean(d[4]));
                    persona.getSituaciones().add(s);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar situaciones: " + e.getMessage());
        }
    }

    // ===================== UTILIDADES =====================

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
}