import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;
import proyecto_integrador.models.DatosLaborales;
import proyecto_integrador.models.Incentivo;
import proyecto_integrador.models.Person;
import proyecto_integrador.models.SaludOcupacional;
import proyecto_integrador.models.SituacionAdministrativa;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String RUTA_ARCHIVO = "lib/personas.txt";
    private static final String VERDE = "\u001B[32m";
    private static final String ROJO = "\u001B[31m";
    private static final String RESET = "\u001B[0m";

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final ArrayList<Person> personas = new ArrayList<>();

    // ===================== MAIN =====================

    public static void main(String[] args) {
        Main app = new Main();
        app.cargarDatos();
        int opcion;
        do {
            app.showMenu();
            opcion = leerEntero("Seleccione una opción: ");
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
                case 4:
                    app.menuIncentivos();
                    break;
                case 5:
                    app.menuSalud();
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

    private void showMenu() {
        printHeader("SISTEMA DE GESTIÓN DE PERSONAL PÚBLICO", 41);
        System.out.println("1. Gestión de Personal");
        System.out.println("2. Situación Administrativa");
        System.out.println("3. Control de Vacaciones");
        System.out.println("4. Beneficios e Incentivos");
        System.out.println("5. Seguridad y Salud en el Trabajo");
        System.out.println("0. Salir");
        printSep(41);
    }

    private void menuPersonal() {
        int op;
        do {
            printHeader("GESTIÓN DE PERSONAL", 32);
            System.out.println("1. Agregar Empleado");
            System.out.println("2. Ver Lista de Empleados");
            System.out.println("3. Buscar Empleado");
            System.out.println("0. Volver");
            printSep(32);
            op = leerEntero("Opción: ");
            switch (op) {
                case 1:
                    createPerson();
                    break;
                case 2:
                    listarPersonas();
                    break;
                case 3:
                    buscarPersonaUI();
                    break;
            }
        } while (op != 0);
    }

    private void menuSituaciones() {
        int op;
        do {
            printHeader("SITUACIÓN ADMINISTRATIVA", 32);
            System.out.println("1. Registrar Situación");
            System.out.println("2. Consultar Situación");
            System.out.println("3. Finalizar Situación");
            System.out.println("0. Volver");
            printSep(32);
            op = leerEntero("Opción: ");
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

    private void menuVacaciones() {
        int op;
        do {
            printHeader("CONTROL DE VACACIONES", 32);
            System.out.println("1. Solicitar Vacaciones");
            System.out.println("2. Aprobar / Rechazar Solicitud");
            System.out.println("3. Ver Historial y Días Disponibles");
            System.out.println("4. Alertas de períodos adeudados");
            System.out.println("5. Personas que deben salir de vacaciones");
            System.out.println("0. Volver");
            printSep(32);
            op = leerEntero("Opción: ");
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

    private void menuIncentivos() {
        int op;
        do {
            printHeader("BENEFICIOS E INCENTIVOS", 32);
            System.out.println("1. Registrar Incentivo");
            System.out.println("2. Consultar Incentivos");
            System.out.println("0. Volver");
            printSep(32);
            op = leerEntero("Opción: ");
            switch (op) {
                case 1:
                    registrarIncentivo();
                    break;
                case 2:
                    consultarIncentivos();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (op != 0);
    }

    private void menuSalud() {
        int op;
        do {
            printHeader("SEGURIDAD Y SALUD EN EL TRABAJO", 32);
            System.out.println("1. Registrar Evaluación Médica");
            System.out.println("2. Consultar Evaluaciones Médicas");
            System.out.println("0. Volver");
            printSep(32);
            op = leerEntero("Opción: ");
            switch (op) {
                case 1:
                    registrarEvaluacionMedica();
                    break;
                case 2:
                    consultarEvaluacionesMedicas();
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
        String name = leerTexto("Ingrese el nombre de la persona", "[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+",
                "Error: el nombre no puede contener números ni símbolos.");
        String documentId = leerTexto("Ingrese el documento de la persona", "[0-9]+",
                "Error: el documento solo puede contener números.");
        String birthDate = leerFecha("Ingrese la fecha de nacimiento (dd/MM/yyyy)");
        String gender = leerTexto("Ingrese el género", "[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+",
                "Error: el género no puede contener números ni símbolos.");
        String stateCivil = leerTexto("Ingrese el estado civil", "[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+",
                "Error: el estado civil no puede contener números ni símbolos.");
        String rh = leerTexto("Ingrese el RH", "^(A|B|AB|O)[+-]$",
                "Error: RH inválido. Valores permitidos: A+, A-, B+, B-, AB+, AB-, O+, O-.");
        System.out.println("Ingrese el email");
        String email = scanner.nextLine();

        Person persona = new Person(name, documentId, birthDate, gender, stateCivil, rh, email,
                createDatosLaborales());
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
            DatosLaborales dl = p.getDatosLaborales();
            System.out.println();
            System.out.println("=".repeat(80));
            System.out.printf("| %-15s | %-30s | %-25s |%n", "Documento", "Nombre", "Email");
            System.out.println("-".repeat(80));
            System.out.printf("| %-15s | %-30s | %-25s |%n", p.getDocumentId(), p.getName(), p.getEmail());
            System.out.println("-".repeat(80));
            System.out.printf("| %-12s | %-10s | %-12s | %-10s | %-15s |%n",
                    "F. Nacimiento", "Género", "Estado Civil", "RH", "Tipo Vinculación");
            System.out.println("-".repeat(80));
            System.out.printf("| %-12s | %-10s | %-12s | %-10s | %-15s |%n",
                    p.getBirthDate(), p.getGender(), p.getStateCivil(), p.getRh(),
                    dl.getTipoVinculacion());
            System.out.println("-".repeat(80));
            System.out.printf("| %-15s | %-12s | %-8s | %-8s | %-12s | %-10s |%n",
                    "Dependencia", "Cargo", "Código", "Grado", "F. Ingreso", "Asignación");
            System.out.println("-".repeat(80));
            // getFechaIngreso() ahora retorna LocalDate — se formatea con FORMATO_FECHA
            System.out.printf("| %-15s | %-12s | %-8s | %-8s | %-12s | %-10s |%n",
                    dl.getDependencia(), dl.getCargo(), dl.getCodigo(), dl.getGrado(),
                    dl.getFechaIngreso(),
                    dl.getAsignacionMensual());
            System.out.println("=".repeat(80));
        }
        System.out.printf("%nTotal empleados: %d%n%n", personas.size());
    }

    /** Versión interactiva: pide el documento al usuario y muestra el resultado. */
    public Person buscarPersonaUI() {
        System.out.println("Ingrese el documento de la persona");
        String documentId = scanner.nextLine();
        Person p = buscarPersona(documentId);
        System.out.println(p == null ? "Error: no se encontró una persona con ese documento." : p);
        return p;
    }

    /** Búsqueda interna por documento; retorna null si no existe. */
    public Person buscarPersona(String documentId) {
        for (Person p : personas)
            if (p.getDocumentId().equals(documentId))
                return p;
        return null;
    }

    // ===================== DATOS LABORALES =====================

    public DatosLaborales createDatosLaborales() {
        String dependencia = leerTexto("Ingrese la dependencia", "[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9 ]+",
                "Error: la dependencia no puede contener símbolos.");
        String cargo = leerTexto("Ingrese el cargo", "[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+",
                "Error: el cargo no puede contener números ni símbolos.");
        String codigo = leerTexto("Ingrese el código", "[0-9]+",
                "Error: el código solo puede contener números.");
        String grado = leerTexto("Ingrese el grado", "[0-9]+",
                "Error: el grado solo puede contener números.");
        String tipoVinculacion = leerTexto("Ingrese el tipo de vinculación", "[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+",
                "Error: el tipo de vinculación no puede contener números ni símbolos.");
        // Se parsea a LocalDate aquí para cumplir con el tipo correcto de
        // DatosLaborales
        LocalDate fechaIngreso = LocalDate.parse(leerFecha("Ingrese la fecha de ingreso (dd/MM/yyyy)"), FORMATO_FECHA);
        BigDecimal asignacionMensual = leerBigDecimal("Ingrese la asignación mensual");

        return new DatosLaborales(dependencia, cargo, codigo, grado, tipoVinculacion,
                fechaIngreso, asignacionMensual);
    }

    // ===================== SITUACIÓN ADMINISTRATIVA =====================

    private static final String[] TIPOS_SITUACION = {
            "Vacaciones", "Permiso 1 día", "Permiso 2-3 días",
            "Licencia remunerada", "Licencia no remunerada", "Licencia maternidad",
            "Licencia paternidad", "Licencia enfermedad", "Encargo", "Traslado", "Comisión"
    };

    public void registrarSituacion() {
        Person persona = pedirPersona();
        if (persona == null)
            return;

        printHeader("Tipos de situación:", 31);
        for (int i = 0; i < TIPOS_SITUACION.length; i++)
            System.out.printf("%d. %s%n", i + 1, TIPOS_SITUACION[i]);
        System.out.println("0. Volver");
        printSep(31);

        String tipo = seleccionarDeArreglo(
                "Seleccione el tipo de situación (1-" + TIPOS_SITUACION.length + ")", TIPOS_SITUACION);
        if (tipo == null)
            return;

        String fechaInicio = leerFecha("Ingrese la fecha de inicio (dd/MM/yyyy)");
        String fechaFin = leerFecha("Ingrese la fecha de fin (dd/MM/yyyy)");

        if (persona.tieneSituacionActivaEnFecha(fechaInicio, fechaFin)) {
            System.out.println("Error: la persona ya tiene una situación administrativa activa en esas fechas.");
            return;
        }

        persona.getSituaciones().add(new SituacionAdministrativa(tipo, fechaInicio, fechaFin));
        guardarDatos();
        System.out.println("Situación registrada correctamente.");
    }

    public void consultarSituacion() {
        Person persona = pedirPersona();
        if (persona == null)
            return;
        ArrayList<SituacionAdministrativa> situaciones = persona.getSituaciones();
        if (situaciones.isEmpty()) {
            System.out.println("La persona no tiene situaciones registradas.");
            return;
        }
        System.out.println("==== Situaciones de: " + persona.getDocumentId() + " ====");
        situaciones.forEach(System.out::println);
    }

    public void finalizarSituacion() {
        Person persona = pedirPersona();
        if (persona == null)
            return;
        ArrayList<SituacionAdministrativa> situaciones = persona.getSituaciones();
        if (situaciones.isEmpty()) {
            System.out.println("La persona no tiene situaciones registradas.");
            return;
        }

        System.out.println("==== Situaciones de: " + persona.getDocumentId() + " ====");
        for (int i = 0; i < situaciones.size(); i++)
            System.out.printf("%d. %s%n", i + 1, situaciones.get(i));

        int op;
        while (true) {
            op = leerEntero("Seleccione la situación a finalizar (0 para cancelar): ");
            if (op == 0)
                return;
            if (op >= 1 && op <= situaciones.size())
                break;
            System.out.println("Error: opción inválida.");
        }

        SituacionAdministrativa sel = situaciones.get(op - 1);
        if (!sel.isActiva()) {
            System.out.println("Error: esa situación ya está finalizada.");
            return;
        }
        sel.setActiva(false);
        guardarDatos();
        System.out.println("Situación finalizada correctamente.");
    }

    // ===================== CONTROL DE VACACIONES =====================

    // Eliminado el split("/") manual — getFechaIngreso() ya retorna LocalDate
    private int calcularDiasAcumulados(Person p) {
        return (int) ChronoUnit.YEARS.between(p.getDatosLaborales().getFechaIngreso(), LocalDate.now()) * 15;
    }

    private int calcularDiasDisfrutados(Person p) {
        int total = 0;
        for (SituacionAdministrativa s : p.getSituaciones())
            if (s.getTipo().equalsIgnoreCase("Vacaciones") && !s.isActiva())
                total += diasEntreFechas(s.getFechaInicio(), s.getFechaFin());
        return total;
    }

    private int calcularDiasPendientes(Person p) {
        return calcularDiasAcumulados(p) - calcularDiasDisfrutados(p);
    }

    private int diasEntreFechas(String fechaInicio, String fechaFin) {
        return (int) ChronoUnit.DAYS.between(parseFecha(fechaInicio), parseFecha(fechaFin)) + 1;
    }

    // Centralizado con FORMATO_FECHA — antes cada método tenía su propio split
    private LocalDate parseFecha(String fecha) {
        return LocalDate.parse(fecha, FORMATO_FECHA);
    }

    private String localDateAString(LocalDate fecha) {
        return fecha.format(FORMATO_FECHA);
    }

    private void solicitarVacaciones() {
        Person persona = pedirPersona();
        if (persona == null)
            return;

        // Se calculan los tres valores una sola vez para evitar llamadas repetidas
        int acumulados = calcularDiasAcumulados(persona);
        int disfrutados = calcularDiasDisfrutados(persona);
        int disponibles = acumulados - disfrutados;

        System.out.println("--------------------------------------");
        System.out.println("Persona    : " + persona.getName());
        System.out.println("Acumulados : " + acumulados + " días");
        System.out.println("Disfrutados: " + disfrutados + " días");
        System.out.println("Disponibles: " + disponibles + " días");
        System.out.println("--------------------------------------");

        if (disponibles <= 0) {
            System.out.println("Error: la persona no tiene días de vacaciones disponibles.");
            return;
        }

        String fechaInicio = leerFecha("Ingrese la fecha de inicio de vacaciones (dd/MM/yyyy)");
        String fechaFin = leerFecha("Ingrese la fecha de fin de vacaciones (dd/MM/yyyy)");
        int diasSolicitados = diasEntreFechas(fechaInicio, fechaFin);

        if (diasSolicitados <= 0) {
            System.out.println("Error: la fecha de fin debe ser posterior a la de inicio.");
            return;
        }
        if (diasSolicitados > disponibles) {
            System.out.println("Error: los días solicitados (" + diasSolicitados
                    + ") superan los disponibles (" + disponibles + ").");
            return;
        }
        if (persona.tieneSituacionActivaEnFecha(fechaInicio, fechaFin)) {
            System.out.println("Error: la persona ya tiene una situación administrativa activa en esas fechas.");
            return;
        }

        persona.getSituaciones().add(new SituacionAdministrativa("Vacaciones", fechaInicio, fechaFin));
        guardarDatos();
        System.out.println("Solicitud de vacaciones registrada correctamente (" + diasSolicitados + " días).");
        System.out.println("Estado: Pendiente de aprobación.");
    }

    private void aprobarRechazarVacaciones() {
        Person persona = pedirPersona();
        if (persona == null)
            return;

        ArrayList<SituacionAdministrativa> pendientes = new ArrayList<>();
        for (SituacionAdministrativa s : persona.getSituaciones())
            if (s.getTipo().equalsIgnoreCase("Vacaciones") && s.isActiva())
                pendientes.add(s);

        if (pendientes.isEmpty()) {
            System.out.println("La persona no tiene solicitudes de vacaciones pendientes.");
            return;
        }

        System.out.println("==== Solicitudes pendientes de: " + persona.getName() + " ====");
        for (int i = 0; i < pendientes.size(); i++) {
            SituacionAdministrativa s = pendientes.get(i);
            System.out.printf("%d. Del %s al %s — %d días%n",
                    i + 1, s.getFechaInicio(), s.getFechaFin(),
                    diasEntreFechas(s.getFechaInicio(), s.getFechaFin()));
        }

        int op;
        while (true) {
            op = leerEntero("Seleccione la solicitud (0 para cancelar): ");
            if (op == 0)
                return;
            if (op >= 1 && op <= pendientes.size())
                break;
            System.out.println("Error: opción inválida.");
        }

        SituacionAdministrativa sel = pendientes.get(op - 1);
        System.out.println("1. Aprobar\n2. Rechazar");
        int decision = leerEntero("Opción: ");

        if (decision == 1) {
            sel.setActiva(false);
            guardarDatos();
            System.out.println("Vacaciones aprobadas correctamente.");
        } else if (decision == 2) {
            persona.getSituaciones().remove(sel);
            guardarDatos();
            System.out.println("Solicitud rechazada y eliminada.");
        } else {
            System.out.println("Opción no válida. No se realizó ningún cambio.");
        }
    }

    private void historialVacaciones() {
        Person persona = pedirPersona();
        if (persona == null)
            return;

        // Calculados una sola vez
        int acumulados = calcularDiasAcumulados(persona);
        int disfrutados = calcularDiasDisfrutados(persona);
        int pendientes = acumulados - disfrutados;

        System.out.println("=".repeat(60));
        System.out.println("  HISTORIAL DE VACACIONES — " + persona.getName());
        System.out.println("=".repeat(60));
        System.out.printf("  Fecha de ingreso   : %s%n",
                persona.getDatosLaborales().getFechaIngreso());
        System.out.printf("  Días acumulados    : %d%n", acumulados);
        System.out.printf("  Días disfrutados   : %d%n", disfrutados);
        System.out.printf("  Días disponibles   : %d%n", pendientes);
        if (pendientes > 15)
            System.out.println("=== ALERTA: adeuda más de un período de vacaciones ===");

        System.out.println("-".repeat(60));
        System.out.println("  PERÍODOS REGISTRADOS");
        System.out.println("-".repeat(60));

        boolean hay = false;
        for (SituacionAdministrativa s : persona.getSituaciones()) {
            if (!s.getTipo().equalsIgnoreCase("Vacaciones"))
                continue;
            int dias = diasEntreFechas(s.getFechaInicio(), s.getFechaFin());
            String estado = s.isActiva()
                    ? VERDE + "Pendiente aprobación" + RESET
                    : ROJO + "Disfrutada" + RESET;
            System.out.printf("  Del %s al %s — %d días — %s%n",
                    s.getFechaInicio(), s.getFechaFin(), dias, estado);
            hay = true;
        }
        if (!hay)
            System.out.println("  Sin períodos registrados.");
        System.out.println("=".repeat(60));
    }

    private void alertasVacacionesAdeudadas() {
        System.out.println("=".repeat(60));
        System.out.println("  ALERTAS — MAS DE UN PERIODO DE VACACIONES ADEUDADO");
        System.out.println("=".repeat(60));
        boolean hay = false;
        for (Person p : personas) {
            int pendientes = calcularDiasPendientes(p);
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

    private void personasDebeSalirVacaciones() {
        LocalDate hoy = LocalDate.now();
        System.out.println("=".repeat(60));
        System.out.println("  PERSONAS QUE DEBEN SALIR DE VACACIONES");
        System.out.println("=".repeat(60));
        boolean hay = false;
        for (Person p : personas) {
            int pendientes = calcularDiasPendientes(p);
            if (pendientes < 15)
                continue;

            boolean enVacacionesHoy = false;
            for (SituacionAdministrativa s : p.getSituaciones()) {
                if (s.getTipo().equalsIgnoreCase("Vacaciones") && s.isActiva()) {
                    LocalDate inicio = parseFecha(s.getFechaInicio());
                    LocalDate fin = parseFecha(s.getFechaFin());
                    // Comparación con LocalDate en vez de String.compareTo (era frágil)
                    if (!hoy.isBefore(inicio) && !hoy.isAfter(fin)) {
                        enVacacionesHoy = true;
                        break;
                    }
                }
            }
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

    // ===================== BENEFICIOS E INCENTIVOS =====================

    private static final String[] TIPOS_INCENTIVO = {
            "Cumpleaños", "Tiempo de servicio", "Reconocimiento", "Capacitación"
    };

    private void registrarIncentivo() {
        Person persona = pedirPersona();
        if (persona == null)
            return;

        printHeader("Tipos de incentivo:", 32);
        for (int i = 0; i < TIPOS_INCENTIVO.length; i++)
            System.out.printf("%d. %s%n", i + 1, TIPOS_INCENTIVO[i]);
        printSep(32);

        String tipo = seleccionarDeArreglo("Seleccione el tipo (1-4)", TIPOS_INCENTIVO);
        if (tipo == null)
            return;

        String fecha = leerFecha("Ingrese la fecha del incentivo (dd/MM/yyyy)");
        int anio = parseFecha(fecha).getYear(); // Antes usaba split("/")[2], ahora usa LocalDate

        if (tipo.equals("Cumpleaños")) {
            for (Incentivo inc : persona.getIncentivos()) {
                if (inc.getTipo().equals("Cumpleaños") && inc.getAnio() == anio) {
                    System.out.println("Error: el incentivo de cumpleaños ya fue registrado para el año " + anio + ".");
                    return;
                }
            }
        }

        System.out.println("Ingrese una descripción del incentivo");
        String descripcion = scanner.nextLine();

        persona.getIncentivos().add(new Incentivo(tipo, descripcion, fecha, anio));
        guardarDatos();
        System.out.println("Incentivo registrado correctamente.");
    }

    private void consultarIncentivos() {
        Person persona = pedirPersona();
        if (persona == null)
            return;
        if (persona.getIncentivos().isEmpty()) {
            System.out.println("La persona no tiene incentivos registrados.");
            return;
        }
        System.out.println("=".repeat(60));
        System.out.println("  INCENTIVOS — " + persona.getName());
        System.out.println("=".repeat(60));
        persona.getIncentivos().forEach(inc -> System.out.println("  " + inc));
        System.out.println("=".repeat(60));
    }

    // ===================== SEGURIDAD Y SALUD EN EL TRABAJO =====================

    private static final String[] CONCEPTOS_MEDICOS = { "Apto", "Apto con restricciones", "No apto" };

    private void registrarEvaluacionMedica() {
        Person persona = pedirPersona();
        if (persona == null)
            return;

        String fecha = leerFecha("Ingrese la fecha de la evaluación (dd/MM/yyyy)");

        printHeader("Concepto médico:", 32);
        for (int i = 0; i < CONCEPTOS_MEDICOS.length; i++)
            System.out.printf("%d. %s%n", i + 1, CONCEPTOS_MEDICOS[i]);
        printSep(32);

        String concepto = seleccionarDeArreglo("Seleccione el concepto (1-3)", CONCEPTOS_MEDICOS);
        if (concepto == null)
            return;

        System.out.println("Ingrese observaciones (o presione Enter para omitir)");
        String observaciones = scanner.nextLine();
        if (observaciones.isEmpty())
            observaciones = "Sin observaciones";

        persona.getEvaluacionesMedicas().add(new SaludOcupacional(fecha, concepto, observaciones));
        guardarDatos();
        System.out.println("Evaluación médica registrada correctamente.");
    }

    private void consultarEvaluacionesMedicas() {
        Person persona = pedirPersona();
        if (persona == null)
            return;
        if (persona.getEvaluacionesMedicas().isEmpty()) {
            System.out.println("La persona no tiene evaluaciones médicas registradas.");
            return;
        }
        System.out.println("=".repeat(60));
        System.out.println("  EVALUACIONES MÉDICAS — " + persona.getName());
        System.out.println("=".repeat(60));
        persona.getEvaluacionesMedicas().forEach(ev -> System.out.println("  " + ev));
        System.out.println("=".repeat(60));
    }

    // ===================== ARCHIVO DE DATOS =====================

    private void guardarDatos() {
        new File("lib").mkdirs();

        guardarArchivo(RUTA_ARCHIVO, pw -> {
            for (Person p : personas) {
                DatosLaborales dl = p.getDatosLaborales();
                // getFechaIngreso() es LocalDate — se formatea antes de guardar
                pw.println(String.join(",", p.getName(), p.getDocumentId(), p.getBirthDate(),
                        p.getGender(), p.getStateCivil(), p.getRh(), p.getEmail(),
                        dl.getDependencia(), dl.getCargo(), dl.getCodigo(), dl.getGrado(),
                        dl.getTipoVinculacion(),
                        dl.getFechaIngreso().toString(),
                        String.valueOf(dl.getAsignacionMensual())));
            }
        }, "personas");

        guardarArchivo("lib/situaciones.txt", pw -> {
            for (Person p : personas)
                for (SituacionAdministrativa s : p.getSituaciones())
                    pw.println(String.join(",", p.getDocumentId(), s.getTipo(),
                            s.getFechaInicio(), s.getFechaFin(), String.valueOf(s.isActiva())));
        }, "situaciones");

        guardarArchivo("lib/incentivos.txt", pw -> {
            for (Person p : personas)
                for (Incentivo inc : p.getIncentivos())
                    pw.println(String.join(",", p.getDocumentId(), inc.getTipo(),
                            inc.getDescripcion(), inc.getFecha(), String.valueOf(inc.getAnio())));
        }, "incentivos");

        guardarArchivo("lib/evaluaciones.txt", pw -> {
            for (Person p : personas)
                for (SaludOcupacional ev : p.getEvaluacionesMedicas())
                    pw.println(String.join(",", p.getDocumentId(),
                            ev.getFecha(), ev.getConcepto(), ev.getObservaciones()));
        }, "evaluaciones médicas");
    }

    @FunctionalInterface
    private interface EscritorArchivo {
        void escribir(PrintWriter pw) throws IOException;
    }

    private void guardarArchivo(String ruta, EscritorArchivo escritor, String nombre) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
            escritor.escribir(pw);
        } catch (IOException e) {
            System.out.println("Error al guardar " + nombre + ": " + e.getMessage());
        }
    }

    private void cargarDatos() {
        if (!new File(RUTA_ARCHIVO).exists())
            return;
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(",");
                // Se parsea la fecha a LocalDate y la asignación a BigDecimal al cargar
                LocalDate fechaIngreso = LocalDate.parse(d[12], FORMATO_FECHA);
                BigDecimal asignacionMensual = new BigDecimal(d[13]);
                personas.add(new Person(d[0], d[1], d[2], d[3], d[4], d[5], d[6],
                        new DatosLaborales(d[7], d[8], d[9], d[10], d[11],
                                fechaIngreso, asignacionMensual)));
            }
            System.out.println("Datos cargados: " + personas.size() + " empleados.");
        } catch (IOException e) {
            System.out.println("Error al cargar: " + e.getMessage());
        }

        cargarArchivo("lib/situaciones.txt", d -> {
            Person p = buscarPersona(d[0]);
            if (p != null) {
                SituacionAdministrativa s = new SituacionAdministrativa(d[1], d[2], d[3]);
                s.setActiva(Boolean.parseBoolean(d[4]));
                p.getSituaciones().add(s);
            }
        });

        cargarArchivo("lib/incentivos.txt", d -> {
            Person p = buscarPersona(d[0]);
            if (p != null)
                p.getIncentivos().add(new Incentivo(d[1], d[2], d[3], Integer.parseInt(d[4])));
        });

        cargarArchivo("lib/evaluaciones.txt", d -> {
            Person p = buscarPersona(d[0]);
            if (p != null)
                p.getEvaluacionesMedicas().add(new SaludOcupacional(d[1], d[2], d[3]));
        });
    }

    @FunctionalInterface
    private interface ProcesadorLinea {
        void procesar(String[] datos);
    }

    private void cargarArchivo(String ruta, ProcesadorLinea procesador) {
        if (!new File(ruta).exists())
            return;
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null)
                procesador.procesar(linea.split(","));
        } catch (IOException e) {
            System.out.println("Error al cargar " + ruta + ": " + e.getMessage());
        }
    }

    // ===================== UTILIDADES =====================

    /** Pide un número entero, reintentando si el input no es válido. */
    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número entero válido.");
            }
        }
    }

    /**
     * Pide un BigDecimal positivo, reintentando si el input no es numérico.
     * Reemplaza leerDouble() para ser consistente con el tipo de DatosLaborales.
     */
    private static BigDecimal leerBigDecimal(String mensaje) {
        while (true) {
            System.out.println(mensaje);
            String input = scanner.nextLine();
            try {
                BigDecimal valor = new BigDecimal(input);
                if (valor.compareTo(BigDecimal.ZERO) < 0) {
                    System.out.println("Error: el valor no puede ser negativo.");
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un número válido (ej: 1500000 o 1500000.50).");
            }
        }
    }

    /**
     * Pide un texto que cumpla el regex dado, mostrando el mensaje de error si no.
     */
    private static String leerTexto(String prompt, String regex, String errorMsg) {
        while (true) {
            System.out.println(prompt);
            String valor = scanner.nextLine();
            if (valor.matches(regex))
                return valor;
            System.out.println(errorMsg);
        }
    }

    /**
     * Pide una fecha en formato dd/MM/yyyy, reintentando si el formato es inválido.
     */
    private static String leerFecha(String prompt) {
        return leerTexto(prompt, "\\d{2}/\\d{2}/\\d{4}", "Error: formato de fecha inválido. Use dd/MM/yyyy.");
    }

    /**
     * Pide el documento de una persona y la retorna; muestra error y retorna null
     * si no existe.
     * Centraliza el patrón repetido "pedir doc → buscar → chequear null" que
     * aparecía en ~10 métodos.
     */
    private Person pedirPersona() {
        System.out.println("Ingrese el documento de la persona");
        String doc = scanner.nextLine();
        Person p = buscarPersona(doc);
        if (p == null)
            System.out.println("Error: no se encontró una persona con ese documento.");
        return p;
    }

    /**
     * Muestra un menú numerado basado en un arreglo de opciones y retorna el texto
     * elegido.
     * Retorna null si el usuario elige 0 (cancelar).
     */
    private static String seleccionarDeArreglo(String prompt, String[] opciones) {
        while (true) {
            System.out.println(prompt);
            try {
                int n = Integer.parseInt(scanner.nextLine());
                if (n == 0)
                    return null;
                if (n >= 1 && n <= opciones.length)
                    return opciones[n - 1];
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Error: opción inválida.");
        }
    }

    /** Imprime un encabezado de sección con el ancho dado. */
    private static void printHeader(String titulo, int ancho) {
        String sep = "=".repeat(ancho);
        System.out.println(sep);
        System.out.println("  " + titulo);
        System.out.println(sep);
    }

    /** Imprime un separador simple. */
    private static void printSep(int ancho) {
        System.out.println("=".repeat(ancho));
    }
}