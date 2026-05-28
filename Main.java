import java.io.*; // Para manejo de archivos
import java.time.LocalDate; // Para manejo de fechas fijas dentro del sistema
import java.time.temporal.ChronoUnit; // Para calcular diferencias entre fechas (ej. días de vacaciones)
import java.util.ArrayList;
import java.util.Scanner;
import proyecto_integrador.models.DatosLaborales;
import proyecto_integrador.models.Incentivo;
import proyecto_integrador.models.Person;
import proyecto_integrador.models.SaludOcupacional;
import proyecto_integrador.models.SituacionAdministrativa;

public class Main { // Clase principal que contiene el menú y la lógica de interacción con el
                    // usuario
    private static final Scanner scanner = new Scanner(System.in); // Scanner para leer entradas del usuario
    private static final String RUTA_ARCHIVO = "lib/personas.txt"; // Ruta del archivo donde se guardarán los datos de
                                                                   // las personas
    private static final String VERDE = "\u001B[32m"; // Código ANSI para texto verde (usado para mostrar estados
                                                      // pendientes de aprobación)
    private static final String ROJO = "\u001B[31m"; // Código ANSI para texto rojo (usado para mostrar estados de
                                                     // vacaciones disfrutadas)
    private static final String RESET = "\u001B[0m"; // Código ANSI para resetear el color del texto

    private final ArrayList<Person> personas = new ArrayList<>(); // Lista que almacena todos los empleados cargados o
                                                                  // registrados en el sistema

    // ===================== MAIN =====================

    public static void main(String[] args) { // Método principal que inicia la aplicación
        Main app = new Main(); // Crea una instancia de la clase Main para acceder a los métodos no estáticos
        app.cargarDatos(); // Carga los datos persistidos en archivos antes de mostrar el menú
        int opcion;
        do {
            app.showMenu(); // Muestra el menú principal en cada iteración del bucle
            opcion = leerEntero("Seleccione una opción: "); // Lee la opción elegida por el usuario
            switch (opcion) {
                case 1 -> app.menuPersonal(); // Redirige al submenú de gestión de personal
                case 2 -> app.menuSituaciones(); // Redirige al submenú de situación administrativa
                case 3 -> app.menuVacaciones(); // Redirige al submenú de control de vacaciones
                case 4 -> app.menuIncentivos(); // Redirige al submenú de beneficios e incentivos
                case 5 -> app.menuSalud(); // Redirige al submenú de seguridad y salud en el trabajo
                case 0 -> System.out.println("Saliendo del sistema..."); // Mensaje de despedida al salir del sistema
                default -> System.out.println("Opción no válida."); // Mensaje de error si la opción no existe en el
                                                                    // menú
            }
        } while (opcion != 0); // El bucle continúa hasta que el usuario elige la opción 0 (salir)
    }

    // ===================== MENÚS =====================

    private void showMenu() { // Muestra el menú principal al usuario
        printHeader("SISTEMA DE GESTIÓN DE PERSONAL PÚBLICO", 41); // Imprime el encabezado del sistema
        System.out.println("1. Gestión de Personal");
        System.out.println("2. Situación Administrativa");
        System.out.println("3. Control de Vacaciones");
        System.out.println("4. Beneficios e Incentivos");
        System.out.println("5. Seguridad y Salud en el Trabajo");
        System.out.println("0. Salir");
        printSep(41); // Imprime una línea separadora para mejorar la legibilidad del menú
    }

    private void menuPersonal() { // Muestra el submenú de gestión de personal y maneja las opciones relacionadas
                                  // con empleados
        int op;
        do {
            printHeader("GESTIÓN DE PERSONAL", 32); // Imprime el encabezado del submenú
            System.out.println("1. Agregar Empleado");
            System.out.println("2. Ver Lista de Empleados");
            System.out.println("3. Buscar Empleado");
            System.out.println("4. Registrar Datos Laborales");
            System.out.println("0. Volver");
            printSep(32); // Imprime una línea separadora para mejorar la legibilidad del submenú
            op = leerEntero("Opción: "); // Lee la opción elegida por el usuario dentro del submenú
            switch (op) {
                case 1 -> createPerson(); // Llama al método para registrar un nuevo empleado
                case 2 -> listarPersonas(); // Llama al método para mostrar todos los empleados registrados
                case 3 -> buscarPersonaUI(); // Llama al método para buscar un empleado por documento
                case 4 -> registrarDatosLaborales(); // Llama al método para registrar los datos laborales de un
                                                     // empleado
                case 0 -> {
                    return;
                } // Sale del submenú y vuelve al menú principal
                default -> System.out.println("Opción no válida."); // Mensaje de error si la opción no existe
            }
        } while (op != 0); // El bucle continúa hasta que el usuario elige la opción 0 (volver)
    }

    private void menuSituaciones() { // Muestra el submenú de situación administrativa y maneja las opciones
                                     // relacionadas
                                     // con las situaciones de los empleados (vacaciones, licencias, etc.)
        int op;
        do {
            printHeader("SITUACIÓN ADMINISTRATIVA", 32); // Imprime el encabezado del submenú
            System.out.println("1. Registrar Situación");
            System.out.println("2. Consultar Situación");
            System.out.println("3. Finalizar Situación");
            System.out.println("0. Volver");
            printSep(32); // Imprime una línea separadora para mejorar la legibilidad del submenú
            op = leerEntero("Opción: "); // Lee la opción elegida por el usuario dentro del submenú
            switch (op) {
                case 1 -> registrarSituacion(); // Llama al método para registrar una nueva situación administrativa
                case 2 -> consultarSituacion(); // Llama al método para consultar las situaciones de un empleado
                case 3 -> finalizarSituacion(); // Llama al método para marcar una situación como finalizada
                case 0 -> {
                    return;
                } // Sale del submenú y vuelve al menú principal
            }
        } while (op != 0); // El bucle continúa hasta que el usuario elige la opción 0 (volver)
    }

    private void menuVacaciones() { // Muestra el submenú de control de vacaciones y maneja las opciones
                                    // relacionadas con la gestión de vacaciones de los empleados (solicitudes,
                                    // aprobaciones, historial, etc.)
        int op;
        do {
            printHeader("CONTROL DE VACACIONES", 32); // Imprime el encabezado del submenú
            System.out.println("1. Solicitar Vacaciones");
            System.out.println("2. Aprobar / Rechazar Solicitud");
            System.out.println("3. Ver Historial y Días Disponibles");
            System.out.println("4. Alertas de períodos adeudados");
            System.out.println("5. Personas que deben salir de vacaciones");
            System.out.println("0. Volver");
            printSep(32); // Imprime una línea separadora para mejorar la legibilidad del submenú
            op = leerEntero("Opción: "); // Lee la opción elegida por el usuario dentro del submenú
            switch (op) {
                case 1 -> solicitarVacaciones(); // Llama al método para registrar una solicitud de vacaciones
                case 2 -> aprobarRechazarVacaciones(); // Llama al método para aprobar o rechazar una solicitud
                                                       // pendiente
                case 3 -> historialVacaciones(); // Llama al método para mostrar el historial y días disponibles del
                                                 // empleado
                case 4 -> alertasVacacionesAdeudadas(); // Llama al método para mostrar empleados con más de un período
                                                        // adeudado
                case 5 -> personasDebeSalirVacaciones(); // Llama al método para mostrar empleados que deben salir de
                                                         // vacaciones
                case 0 -> {
                    return;
                } // Sale del submenú y vuelve al menú principal
                default -> System.out.println("Opción no válida."); // Mensaje de error si la opción no existe
            }
        } while (op != 0); // El bucle continúa hasta que el usuario elige la opción 0 (volver)
    }

    private void menuIncentivos() { // Muestra el submenú de beneficios e incentivos y maneja las opciones
                                    // relacionadas con el registro y consulta de incentivos para los empleados
                                    // (cumpleaños, reconocimientos, etc.)
        int op;
        do {
            printHeader("BENEFICIOS E INCENTIVOS", 32); // Imprime el encabezado del submenú
            System.out.println("1. Registrar Incentivo");
            System.out.println("2. Consultar Incentivos");
            System.out.println("0. Volver");
            printSep(32); // Imprime una línea separadora para mejorar la legibilidad del submenú
            op = leerEntero("Opción: "); // Lee la opción elegida por el usuario dentro del submenú
            switch (op) {
                case 1 -> registrarIncentivo(); // Llama al método para registrar un nuevo incentivo al empleado
                case 2 -> consultarIncentivos(); // Llama al método para consultar los incentivos registrados del
                                                 // empleado
                case 0 -> {
                    return;
                } // Sale del submenú y vuelve al menú principal
                default -> System.out.println("Opción no válida."); // Mensaje de error si la opción no existe
            }
        } while (op != 0); // El bucle continúa hasta que el usuario elige la opción 0 (volver)
    }

    private void menuSalud() { // Muestra el submenú de seguridad y salud en el trabajo y maneja las opciones
                               // relacionadas con el registro y consulta de evaluaciones médicas para los
                               // empleados (aptitud, restricciones, etc.)
        int op;
        do {
            printHeader("SEGURIDAD Y SALUD EN EL TRABAJO", 32); // Imprime el encabezado del submenú
            System.out.println("1. Registrar Evaluación Médica");
            System.out.println("2. Consultar Evaluaciones Médicas");
            System.out.println("0. Volver");
            printSep(32); // Imprime una línea separadora para mejorar la legibilidad del submenú
            op = leerEntero("Opción: "); // Lee la opción elegida por el usuario dentro del submenú
            switch (op) {
                case 1 -> registrarEvaluacionMedica(); // Llama al método para registrar una evaluación médica al
                                                       // empleado
                case 2 -> consultarEvaluacionesMedicas(); // Llama al método para consultar las evaluaciones médicas del
                                                          // empleado
                case 0 -> {
                    return;
                } // Sale del submenú y vuelve al menú principal
                default -> System.out.println("Opción no válida."); // Mensaje de error si la opción no existe
            }
        } while (op != 0); // El bucle continúa hasta que el usuario elige la opción 0 (volver)
    }

    // ===================== GESTIÓN DE PERSONAL =====================

    public Person createPerson() { // Pide al usuario los datos necesarios para crear un nuevo empleado,
                                   // valida la información ingresada y guarda el nuevo empleado en la lista de
                                   // personas.
                                   // Retorna el objeto Person creado o null si hubo un error (por ejemplo, si ya
                                   // existe un empleado con el mismo documento).

        String name = leerTexto("Ingrese el nombre de la persona", "[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+",
                "Error: el nombre no puede contener números ni símbolos.");
        String documentId = leerTexto("Ingrese el documento de la persona", "[0-9]+",
                "Error: el documento solo puede contener números.");

        if (buscarPersona(documentId) != null) { // Verifica si ya existe un empleado con el mismo documento antes de
                                                 // continuar
            System.out.println("Error: ya existe un empleado con ese documento.");
            return null; // Retorna null para indicar que no se pudo crear el empleado por duplicado
        }

        String birthDate = leerFecha("Ingrese la fecha de nacimiento (dd/MM/yyyy)"); // Lee y valida la fecha de
                                                                                     // nacimiento en formato dd/MM/yyyy
        String gender = leerTexto("Ingrese el género", "[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+",
                "Error: el género no puede contener números ni símbolos.");
        String stateCivil = leerTexto("Ingrese el estado civil", "[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+",
                "Error: el estado civil no puede contener números ni símbolos.");
        String rh = leerTexto("Ingrese el RH", "^(A|B|AB|O)[+-]$",
                "Error: RH inválido. Valores permitidos: A+, A-, B+, B-, AB+, AB-, O+, O-."); // Valida el RH con un
                                                                                              // regex estricto que solo
                                                                                              // permite los grupos
                                                                                              // sanguíneos estándar
        System.out.println("Ingrese el email");
        String email = scanner.nextLine(); // Lee el email sin validación regex para permitir formatos variados

        Person persona = new Person(name, documentId, birthDate, gender, stateCivil, rh, email, null); // Crea el objeto
                                                                                                       // Person con
                                                                                                       // datos
                                                                                                       // laborales
                                                                                                       // nulos; se
                                                                                                       // registran por
                                                                                                       // separado en la
                                                                                                       // opción 4
        personas.add(persona); // Agrega el nuevo empleado a la lista en memoria
        guardarDatos(); // Persiste todos los datos actuales en los archivos del sistema
        System.out.println("Empleado creado exitosamente. Recuerde registrar sus datos laborales (opción 4).");
        return persona; // Retorna el objeto Person recién creado para uso externo si se necesita
    }

    private void registrarDatosLaborales() { // Pide al usuario el documento de un empleado, verifica que el empleado
                                             // exista y luego solicita datos laborales para ese empleado.
                                             // Finalmente, guarda los datos laborales en el objeto Person
                                             // correspondiente y actualiza el archivo de datos.

        Person persona = pedirPersona(); // Pide el documento del empleado y lo busca en la lista

        if (persona == null)
            return; // Si no se encuentra el empleado, se muestra un mensaje de error y se retorna
                    // sin hacer nada

        if (persona.getDatosLaborales() != null) { // Si el empleado ya tiene datos laborales registrados, se muestra
                                                   // una advertencia y se pregunta si desea sobreescribirlos
            System.out.println("Advertencia: este empleado ya tiene datos laborales registrados.");
            System.out.println("1. Sobreescribir   0. Cancelar");
            if (leerEntero("Opción: ") != 1) // Si el usuario no elige sobreescribir, se cancela la operación y se
                                             // retorna sin hacer cambios
                return;
        }

        persona.setDatosLaborales(createDatosLaborales()); // Crea y asigna los datos laborales al empleado
        guardarDatos(); // Persiste los cambios en los archivos del sistema
        System.out.println("Datos laborales registrados correctamente.");
    }

    private void listarPersonas() { // Muestra una lista detallada de todos los empleados registrados en el sistema,
                                    // incluyendo sus datos personales y laborales (si están disponibles). Si no hay
                                    // empleados registrados, muestra un mensaje indicando que no hay empleados.
        System.out.println("==== Lista de Empleados Actual ====");
        if (personas.isEmpty()) { // Verifica si la lista está vacía antes de intentar recorrerla
            System.out.println("No hay empleados.");
            return; // Sale del método si no hay empleados que mostrar
        }
        for (Person p : personas) { // Recorre cada empleado de la lista para imprimir su información
            System.out.println();
            System.out.println("=".repeat(80));
            System.out.printf("| %-15s | %-30s | %-25s |%n", "Documento", "Nombre", "Email"); // Encabezado de columnas
                                                                                              // de datos personales
            System.out.println("-".repeat(80));
            System.out.printf("| %-15s | %-30s | %-25s |%n", p.getDocumentId(), p.getName(), p.getEmail()); // Imprime
                                                                                                            // documento,
                                                                                                            // nombre y
                                                                                                            // email del
                                                                                                            // empleado
            System.out.println("-".repeat(80));
            System.out.printf("| %-12s | %-10s | %-12s | %-10s | %-15s |%n",
                    "F. Nacimiento", "Género", "Estado Civil", "RH", "Tipo Vinculación"); // Encabezado de columnas de
                                                                                          // datos adicionales
            System.out.println("-".repeat(80));
            System.out.printf("| %-12s | %-10s | %-12s | %-10s | %-15s |%n",
                    p.getBirthDate(), p.getGender(), p.getStateCivil(), p.getRh(),
                    p.tieneDatosLaborales() ? p.getDatosLaborales().getTipoVinculacion() : "(sin datos laborales)"); // Muestra
                                                                                                                     // el
                                                                                                                     // tipo
                                                                                                                     // de
                                                                                                                     // vinculación
                                                                                                                     // solo
                                                                                                                     // si
                                                                                                                     // el
                                                                                                                     // empleado
                                                                                                                     // tiene
                                                                                                                     // datos
                                                                                                                     // laborales
                                                                                                                     // registrados
            System.out.println("-".repeat(80));
            if (p.tieneDatosLaborales()) { // Solo imprime la sección de datos laborales si el empleado los tiene
                                           // registrados
                System.out.printf("| %-15s | %-12s | %-8s | %-8s | %-12s | %-10s |%n",
                        "Dependencia", "Cargo", "Código", "Grado", "F. Ingreso", "Asignación"); // Encabezado de
                                                                                                // columnas de datos
                                                                                                // laborales
                System.out.println("-".repeat(80));
                DatosLaborales dl = p.getDatosLaborales(); // Obtiene los datos laborales del empleado para acceder a
                                                           // sus atributos
                System.out.printf("| %-15s | %-12s | %-8s | %-8s | %-12s | %-10.2f |%n",
                        dl.getDependencia(), dl.getCargo(), dl.getCodigo(),
                        dl.getGrado(), dl.getFechaIngreso(), dl.getAsignacionMensual()); // Imprime los datos laborales
                                                                                         // formateados con dos
                                                                                         // decimales para la asignación
            } else {
                System.out.printf("  (Sin datos laborales registrados — use Gestión de Personal > opción 4)%n"); // Indica
                                                                                                                 // al
                                                                                                                 // usuario
                                                                                                                 // cómo
                                                                                                                 // registrar
                                                                                                                 // los
                                                                                                                 // datos
                                                                                                                 // laborales
                                                                                                                 // faltantes
            }
            System.out.println("=".repeat(80));
        }
        System.out.printf("%nTotal empleados: %d%n%n", personas.size()); // Muestra el total de empleados registrados al
                                                                         // final de la lista
    }

    public Person buscarPersonaUI() { // Pide el documento al usuario y muestra el resultado en pantalla
        System.out.println("Ingrese el documento de la persona");
        String documentId = scanner.nextLine(); // Lee el documento ingresado por el usuario
        Person p = buscarPersona(documentId); // Busca el empleado en la lista por su documento
        System.out.println(p == null ? "Error: no se encontró una persona con ese documento." : p); // Muestra los datos
                                                                                                    // del empleado
                                                                                                    // encontrado o un
                                                                                                    // mensaje de error
                                                                                                    // si no existe
        return p; // Retorna el objeto Person encontrado o null si no se encontró
    }

    public Person buscarPersona(String documentId) { // Busca un empleado por su documento de identidad, si encuentra
                                                     // una coincidencia, retorna el objeto Person correspondiente.
                                                     // Si no encuentra ninguna coincidencia después
                                                     // de revisar toda la lista, retorna null.
        for (Person p : personas) // Recorre toda la lista de empleados buscando una coincidencia exacta de
                                  // documento
            if (p.getDocumentId().equals(documentId))
                return p; // Retorna el empleado en cuanto se encuentra la coincidencia
        return null; // Retorna null si ningún empleado tiene el documento buscado
    }

    // ===================== DATOS LABORALES =====================

    public DatosLaborales createDatosLaborales() { // Pide al usuario los datos laborales de un empleado, valida la
                                                   // información ingresada y crea un objeto
                                                   // DatosLaborales con esa información. Retorna el objeto creado.
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
        String fechaIngreso = leerFecha("Ingrese la fecha de ingreso (dd/MM/yyyy)"); // Lee y valida la fecha de ingreso
                                                                                     // en formato dd/MM/yyyy, usada
                                                                                     // para calcular vacaciones
                                                                                     // acumuladas
        double asignacionMensual = leerDouble("Ingrese la asignación mensual"); // Lee y valida que la asignación
                                                                                // mensual sea un valor numérico
                                                                                // positivo

        return new DatosLaborales(dependencia, cargo, codigo, grado, tipoVinculacion, fechaIngreso, asignacionMensual); // Construye
                                                                                                                        // y
                                                                                                                        // retorna
                                                                                                                        // el
                                                                                                                        // objeto
                                                                                                                        // con
                                                                                                                        // todos
                                                                                                                        // los
                                                                                                                        // datos
                                                                                                                        // laborales
                                                                                                                        // recopilados
    }

    // ===================== SITUACIÓN ADMINISTRATIVA =====================

    private static final String[] TIPOS_SITUACION = { // Lista de tipos de situaciones administrativas que se pueden
                                                      // registrar para un empleado (vacaciones, licencias, etc.)
            "Vacaciones", "Permiso 1 día", "Permiso 2-3 días",
            "Licencia remunerada", "Licencia no remunerada", "Licencia maternidad",
            "Licencia paternidad", "Licencia enfermedad", "Encargo", "Traslado", "Comisión"
    };

    public void registrarSituacion() { // Pide el documento del empleado, muestra los tipos de situación disponibles
                                       // y registra la situación seleccionada con sus fechas de inicio y fin,
                                       // validando que no exista un traslape con situaciones ya activas.
        Person persona = pedirPersona(); // Pide el documento del empleado y lo busca en la lista
        if (persona == null)
            return; // Sale del método si no se encontró el empleado

        printHeader("Tipos de situación:", 31); // Muestra un menú con los tipos de situaciones administrativas
                                                // disponibles para registrar, numerados del 1 al 11
        for (int i = 0; i < TIPOS_SITUACION.length; i++)
            System.out.printf("%d. %s%n", i + 1, TIPOS_SITUACION[i]); // Imprime cada tipo de situación numerado
        System.out.println("0. Volver");
        printSep(31); // Imprime una línea separadora para mejorar la legibilidad del menú

        String tipo = seleccionarDeArreglo("Seleccione el tipo de situación (1-" + TIPOS_SITUACION.length + ")",
                TIPOS_SITUACION); // Permite al usuario elegir un tipo de situación del arreglo de opciones
        if (tipo == null)
            return; // Sale del método si el usuario elige la opción 0 (cancelar)

        String fechaInicio = leerFecha("Ingrese la fecha de inicio (dd/MM/yyyy)"); // Lee y valida la fecha de inicio de
                                                                                   // la situación
        String fechaFin = leerFecha("Ingrese la fecha de fin (dd/MM/yyyy)"); // Lee y valida la fecha de fin de la
                                                                             // situación

        if (persona.tieneSituacionActivaEnFecha(fechaInicio, fechaFin)) { // Verifica que no haya un traslape con otras
                                                                          // situaciones activas en el mismo período
            System.out.println("Error: la persona ya tiene una situación administrativa activa en esas fechas.");
            return; // Sale del método sin registrar la situación si hay conflicto de fechas
        }

        persona.getSituaciones().add(new SituacionAdministrativa(tipo, fechaInicio, fechaFin)); // Crea y agrega la
                                                                                                // nueva situación a la
                                                                                                // lista del empleado
        guardarDatos(); // Persiste los cambios en los archivos del sistema
        System.out.println("Situación registrada correctamente.");
    }

    public void consultarSituacion() { // Pide al usuario el documento de un empleado, verifica que el empleado exista
                                       // y luego muestra todas las situaciones administrativas
                                       // registradas para ese empleado.
        Person persona = pedirPersona(); // Pide el documento del empleado y lo busca en la lista
        if (persona == null)
            return; // Sale del método si no se encontró el empleado
        ArrayList<SituacionAdministrativa> situaciones = persona.getSituaciones(); // Obtiene la lista de situaciones
                                                                                   // del empleado
        if (situaciones.isEmpty()) { // Verifica si el empleado tiene situaciones registradas antes de mostrarlas
            System.out.println("La persona no tiene situaciones registradas.");
            return; // Sale del método si no hay situaciones que mostrar
        }
        System.out.println("==== Situaciones de: " + persona.getDocumentId() + " ====");
        situaciones.forEach(System.out::println); // Imprime cada situación administrativa registrada para el empleado,
                                                  // mostrando su tipo, fechas y estado (activa o finalizada)
    }

    public void finalizarSituacion() { // Pide el documento del empleado, muestra sus situaciones activas y permite
                                       // al usuario marcar una de ellas como finalizada, actualizando el archivo de
                                       // datos.
        Person persona = pedirPersona(); // Pide el documento del empleado y lo busca en la lista
        if (persona == null)
            return; // Sale del método si no se encontró el empleado
        ArrayList<SituacionAdministrativa> situaciones = persona.getSituaciones(); // Obtiene la lista de situaciones
                                                                                   // del empleado
        if (situaciones.isEmpty()) { // Verifica si el empleado tiene situaciones registradas antes de continuar
            System.out.println("La persona no tiene situaciones registradas.");
            return; // Sale del método si no hay situaciones para finalizar
        }

        System.out.println("==== Situaciones de: " + persona.getDocumentId() + " ====");
        for (int i = 0; i < situaciones.size(); i++) { // Recorre e imprime cada situación numerada para que el usuario
                                                       // pueda seleccionarla
            SituacionAdministrativa s = situaciones.get(i);
            System.out.printf("%d. %s%n", i + 1, s); // Imprime el número de opción y los detalles de la situación
        }

        int op;
        while (true) { // Bucle que repite la solicitud hasta recibir una opción válida o cancelar
            op = leerEntero("Seleccione la situación a finalizar (0 para cancelar): ");
            if (op == 0)
                return; // Sale del método si el usuario elige cancelar
            if (op > 0 && op <= situaciones.size())
                break; // Sale del bucle si la opción está dentro del rango válido
            System.out.println("Error: opción inválida."); // Mensaje de error si la opción no corresponde a ninguna
                                                           // situación
        }

        SituacionAdministrativa sel = situaciones.get(op - 1); // Obtiene la situación seleccionada por el usuario
                                                               // (ajuste de índice base 0)
        if (!sel.isActiva()) { // Verifica que la situación no haya sido finalizada previamente
            System.out.println("Error: esa situación ya está finalizada.");
            return; // Sale del método sin hacer cambios si la situación ya estaba finalizada
        }
        sel.setActiva(false); // Marca la situación como inactiva (finalizada)
        guardarDatos(); // Persiste los cambios en los archivos del sistema
        System.out.println("Situación finalizada correctamente.");
    }

    // ===================== CONTROL DE VACACIONES =====================

    private int calcularDiasAcumulados(Person p) { // Calcula el total de días de vacaciones acumulados por el empleado
                                                   // según los años transcurridos desde su fecha de ingreso (15 días
                                                   // por año)
        if (!p.tieneDatosLaborales())
            return 0; // Retorna 0 si el empleado no tiene datos laborales registrados, pues no se
                      // puede calcular la fecha de ingreso
        String[] partes = p.getDatosLaborales().getFechaIngreso().split("/"); // Separa la fecha de ingreso en día, mes
                                                                              // y año
        LocalDate ingreso = LocalDate.of(Integer.parseInt(partes[2]), Integer.parseInt(partes[1]),
                Integer.parseInt(partes[0])); // Construye el objeto LocalDate a partir de las partes de la fecha de
                                              // ingreso
        return (int) ChronoUnit.YEARS.between(ingreso, LocalDate.now()) * 15; // Calcula los años trabajados y los
                                                                              // multiplica por 15 días por año de
                                                                              // vacaciones
    }

    private int calcularDiasDisfrutados(Person p) { // Calcula el total de días de vacaciones ya disfrutados por el
                                                    // empleado,
                                                    // contando únicamente las situaciones de tipo "Vacaciones" que ya
                                                    // fueron aprobadas (inactivas)
        int total = 0;
        for (SituacionAdministrativa s : p.getSituaciones()) // Recorre todas las situaciones del empleado buscando
                                                             // vacaciones finalizadas
            if (s.getTipo().equalsIgnoreCase("Vacaciones") && !s.isActiva()) // Solo cuenta vacaciones aprobadas
                                                                             // (inactivas); las pendientes no se
                                                                             // descuentan
                total += diasEntreFechas(s.getFechaInicio(), s.getFechaFin()); // Suma los días de cada período de
                                                                               // vacaciones disfrutado
        return total; // Retorna el total de días de vacaciones ya disfrutados
    }

    private int calcularDiasPendientes(Person p) { // Calcula los días de vacaciones disponibles restando los
                                                   // disfrutados de los acumulados
        return calcularDiasAcumulados(p) - calcularDiasDisfrutados(p);
    }

    private int diasEntreFechas(String fechaInicio, String fechaFin) { // Calcula la cantidad de días entre dos fechas
                                                                       // en formato dd/MM/yyyy,
                                                                       // sumando 1 para incluir el día de inicio en el
                                                                       // conteo
        return (int) ChronoUnit.DAYS.between(parseFecha(fechaInicio), parseFecha(fechaFin)) + 1;
    }

    private LocalDate parseFecha(String fecha) { // Convierte una cadena de texto en formato dd/MM/yyyy a un objeto
                                                 // LocalDate
        String[] p = fecha.split("/"); // Separa la cadena en día, mes y año usando el separador "/"
        return LocalDate.of(Integer.parseInt(p[2]), Integer.parseInt(p[1]), Integer.parseInt(p[0])); // Construye el
                                                                                                     // LocalDate con el
                                                                                                     // orden correcto:
                                                                                                     // año, mes, día
    }

    private String localDateAString(LocalDate fecha) { // Convierte un objeto LocalDate a una cadena de texto en formato
                                                       // dd/MM/yyyy
        return String.format("%02d/%02d/%04d", fecha.getDayOfMonth(), fecha.getMonthValue(), fecha.getYear()); // Formatea
                                                                                                               // con
                                                                                                               // ceros
                                                                                                               // a la
                                                                                                               // izquierda
                                                                                                               // para
                                                                                                               // garantizar
                                                                                                               // el
                                                                                                               // formato
                                                                                                               // dd/MM/yyyy
    }

    private void solicitarVacaciones() { // Muestra los días acumulados, disfrutados y disponibles del empleado,
                                         // luego permite registrar una solicitud de vacaciones si tiene días
                                         // disponibles
                                         // y las fechas no se solapan con situaciones ya activas.
        Person persona = pedirPersona(); // Pide el documento del empleado y lo busca en la lista
        if (persona == null)
            return; // Sale del método si no se encontró el empleado

        int disponibles = calcularDiasPendientes(persona); // Calcula los días de vacaciones disponibles para el
                                                           // empleado
        System.out.println("--------------------------------------");
        System.out.println("Persona    : " + persona.getName());
        System.out.println("Acumulados : " + calcularDiasAcumulados(persona) + " días");
        System.out.println("Disfrutados: " + calcularDiasDisfrutados(persona) + " días");
        System.out.println("Disponibles: " + disponibles + " días"); // Muestra el resumen de vacaciones antes de
                                                                     // procesar la solicitud
        System.out.println("--------------------------------------");

        if (disponibles <= 0) { // Impide la solicitud si el empleado no tiene días disponibles
            System.out.println("Error: la persona no tiene días de vacaciones disponibles.");
            return; // Sale del método sin registrar la solicitud
        }

        String fechaInicio = leerFecha("Ingrese la fecha de inicio de vacaciones (dd/MM/yyyy)"); // Lee y valida la
                                                                                                 // fecha de inicio del
                                                                                                 // período solicitado
        String fechaFin = leerFecha("Ingrese la fecha de fin de vacaciones (dd/MM/yyyy)"); // Lee y valida la fecha de
                                                                                           // fin del período solicitado
        int diasSolicitados = diasEntreFechas(fechaInicio, fechaFin); // Calcula cuántos días abarca la solicitud
                                                                      // ingresada

        if (diasSolicitados <= 0) { // Verifica que la fecha de fin sea posterior a la de inicio
            System.out.println("Error: la fecha de fin debe ser posterior a la de inicio.");
            return; // Sale del método si el rango de fechas es inválido
        }
        if (diasSolicitados > disponibles) { // Verifica que los días solicitados no superen los días disponibles
            System.out.println("Error: los días solicitados (" + diasSolicitados + ") superan los disponibles ("
                    + disponibles + ").");
            return; // Sale del método si se excede el límite de días disponibles
        }
        if (persona.tieneSituacionActivaEnFecha(fechaInicio, fechaFin)) { // Verifica que no haya traslape con otras
                                                                          // situaciones activas en el mismo período
            System.out.println("Error: la persona ya tiene una situación administrativa activa en esas fechas.");
            return; // Sale del método si hay conflicto de fechas con otra situación
        }

        persona.getSituaciones().add(new SituacionAdministrativa("Vacaciones", fechaInicio, fechaFin)); // Registra la
                                                                                                        // solicitud
                                                                                                        // como
                                                                                                        // situación
                                                                                                        // activa
                                                                                                        // pendiente de
                                                                                                        // aprobación
        guardarDatos(); // Persiste los cambios en los archivos del sistema
        System.out.println("Solicitud de vacaciones registrada correctamente (" + diasSolicitados + " días).");
        System.out.println("Estado: Pendiente de aprobación."); // Informa al usuario que la solicitud queda en estado
                                                                // pendiente hasta ser aprobada o rechazada
    }

    private void aprobarRechazarVacaciones() { // Muestra las solicitudes de vacaciones pendientes de aprobación de un
                                               // empleado
                                               // y permite al usuario aprobarlas (marcándolas como finalizadas)
                                               // o rechazarlas (eliminándolas de la lista).
        Person persona = pedirPersona(); // Pide el documento del empleado y lo busca en la lista
        if (persona == null)
            return; // Sale del método si no se encontró el empleado

        ArrayList<SituacionAdministrativa> pendientes = new ArrayList<>(); // Lista temporal para almacenar solo las
                                                                           // solicitudes de vacaciones pendientes
        for (SituacionAdministrativa s : persona.getSituaciones()) // Recorre todas las situaciones buscando vacaciones
                                                                   // activas (pendientes de aprobación)
            if (s.getTipo().equalsIgnoreCase("Vacaciones") && s.isActiva())
                pendientes.add(s); // Agrega solo las vacaciones pendientes a la lista temporal

        if (pendientes.isEmpty()) { // Verifica si hay solicitudes pendientes antes de continuar
            System.out.println("La persona no tiene solicitudes de vacaciones pendientes.");
            return; // Sale del método si no hay solicitudes pendientes que gestionar
        }

        System.out.println("==== Solicitudes pendientes de: " + persona.getName() + " ====");
        for (int i = 0; i < pendientes.size(); i++) { // Recorre e imprime cada solicitud pendiente numerada con sus
                                                      // fechas y cantidad de días
            SituacionAdministrativa s = pendientes.get(i);
            System.out.printf("%d. Del %s al %s — %d días%n",
                    i + 1, s.getFechaInicio(), s.getFechaFin(), diasEntreFechas(s.getFechaInicio(), s.getFechaFin())); // Muestra
                                                                                                                       // el
                                                                                                                       // número,
                                                                                                                       // las
                                                                                                                       // fechas
                                                                                                                       // y
                                                                                                                       // la
                                                                                                                       // duración
                                                                                                                       // de
                                                                                                                       // cada
                                                                                                                       // solicitud
        }

        int op;
        while (true) { // Bucle que repite la solicitud hasta recibir una opción válida o cancelar
            op = leerEntero("Seleccione la solicitud (0 para cancelar): ");
            if (op == 0)
                return; // Sale del método si el usuario elige cancelar
            if (op >= 1 && op <= pendientes.size())
                break; // Sale del bucle si la opción está dentro del rango válido
            System.out.println("Error: opción inválida."); // Mensaje de error si la opción no corresponde a ninguna
                                                           // solicitud
        }

        SituacionAdministrativa sel = pendientes.get(op - 1); // Obtiene la solicitud seleccionada (ajuste de índice
                                                              // base 0)
        System.out.println("1. Aprobar\n2. Rechazar");
        int decision = leerEntero("Opción: "); // Lee la decisión del usuario sobre la solicitud seleccionada

        switch (decision) {
            case 1 -> { // Aprueba la solicitud marcándola como inactiva (disfrutada)
                sel.setActiva(false); // Marca la vacación como aprobada y disfrutada
                guardarDatos(); // Persiste los cambios en los archivos del sistema
                System.out.println("Vacaciones aprobadas correctamente.");
            }
            case 2 -> { // Rechaza la solicitud eliminándola completamente de la lista de situaciones
                persona.getSituaciones().remove(sel); // Elimina la solicitud rechazada de la lista del empleado
                guardarDatos(); // Persiste los cambios en los archivos del sistema
                System.out.println("Solicitud rechazada y eliminada.");
            }
            default -> System.out.println("Opción no válida. No se realizó ningún cambio."); // Mensaje de error si la
                                                                                             // decisión ingresada no es
                                                                                             // 1 ni 2
        }
    }

    private void historialVacaciones() { // Muestra el historial completo de vacaciones de un empleado,
                                         // incluyendo días acumulados, disfrutados y disponibles,
                                         // y una alerta si adeuda más de un período.
        Person persona = pedirPersona(); // Pide el documento del empleado y lo busca en la lista
        if (persona == null)
            return; // Sale del método si no se encontró el empleado

        int acumulados = calcularDiasAcumulados(persona); // Calcula los días de vacaciones generados según años de
                                                          // servicio
        int disfrutados = calcularDiasDisfrutados(persona); // Calcula los días de vacaciones ya tomados y aprobados
        int pendientes = calcularDiasPendientes(persona); // Calcula los días de vacaciones disponibles aún no tomados

        System.out.println("=".repeat(60));
        System.out.println("  HISTORIAL DE VACACIONES — " + persona.getName());
        System.out.println("=".repeat(60));
        System.out.printf("  Fecha de ingreso   : %s%n",
                persona.tieneDatosLaborales() ? persona.getDatosLaborales().getFechaIngreso() : "No registrada"); // Muestra
                                                                                                                  // la
                                                                                                                  // fecha
                                                                                                                  // de
                                                                                                                  // ingreso
                                                                                                                  // o
                                                                                                                  // un
                                                                                                                  // mensaje
                                                                                                                  // si
                                                                                                                  // no
                                                                                                                  // está
                                                                                                                  // registrada
        System.out.printf("  Días acumulados    : %d%n", acumulados);
        System.out.printf("  Días disfrutados   : %d%n", disfrutados);
        System.out.printf("  Días disponibles   : %d%n", pendientes);
        if (pendientes > 15) // Muestra una alerta si el empleado acumula más de un período sin disfrutar
            System.out.println("=== ALERTA: adeuda más de un período de vacaciones ===");

        System.out.println("-".repeat(60));
        System.out.println("  PERÍODOS REGISTRADOS");
        System.out.println("-".repeat(60));

        boolean hay = false; // Bandera para detectar si el empleado tiene al menos un período de vacaciones
                             // registrado
        for (SituacionAdministrativa s : persona.getSituaciones()) { // Recorre las situaciones buscando períodos de
                                                                     // tipo vacaciones
            if (!s.getTipo().equalsIgnoreCase("Vacaciones"))
                continue; // Ignora las situaciones que no sean de tipo vacaciones
            int dias = diasEntreFechas(s.getFechaInicio(), s.getFechaFin()); // Calcula la duración del período en días
            String estado = s.isActiva() ? VERDE + "Pendiente aprobación" + RESET : ROJO + "Disfrutada" + RESET; // Asigna
                                                                                                                 // el
                                                                                                                 // color
                                                                                                                 // y
                                                                                                                 // texto
                                                                                                                 // del
                                                                                                                 // estado
                                                                                                                 // según
                                                                                                                 // si
                                                                                                                 // la
                                                                                                                 // vacación
                                                                                                                 // está
                                                                                                                 // pendiente
                                                                                                                 // o ya
                                                                                                                 // fue
                                                                                                                 // disfrutada
            System.out.printf("  Del %s al %s — %d días — %s%n", s.getFechaInicio(), s.getFechaFin(), dias, estado); // Imprime
                                                                                                                     // el
                                                                                                                     // período
                                                                                                                     // con
                                                                                                                     // su
                                                                                                                     // estado
                                                                                                                     // coloreado
            hay = true; // Marca que se encontró al menos un período registrado
        }
        if (!hay)
            System.out.println("  Sin períodos registrados."); // Mensaje si el empleado no tiene ningún período de
                                                               // vacaciones registrado
        System.out.println("=".repeat(60));
    }

    private void alertasVacacionesAdeudadas() { // Recorre todos los empleados y muestra aquellos que tienen
                                                // más de 15 días pendientes de vacaciones (es decir, más de un período
                                                // adeudado).
        System.out.println("=".repeat(60));
        System.out.println("  ALERTAS — MAS DE UN PERIODO DE VACACIONES ADEUDADO");
        System.out.println("=".repeat(60));
        boolean hay = false; // Bandera para detectar si existe al menos un empleado en situación de alerta
        for (Person p : personas) { // Recorre todos los empleados registrados en el sistema
            int pendientes = calcularDiasPendientes(p); // Calcula los días de vacaciones disponibles del empleado
                                                        // actual
            if (pendientes > 15) { // Solo muestra al empleado si supera los 15 días pendientes (más de un período)
                System.out.printf("  %-30s | Doc: %-12s | Pendientes: %d días%n",
                        p.getName(), p.getDocumentId(), pendientes); // Imprime el nombre, documento y días pendientes
                                                                     // del empleado en alerta
                hay = true; // Marca que se encontró al menos un empleado en situación de alerta
            }
        }
        if (!hay)
            System.out.println("  Ninguna persona adeuda más de un período."); // Mensaje si ningún empleado supera el
                                                                               // período adeudado
        System.out.println("=".repeat(60));
    }

    private void personasDebeSalirVacaciones() { // Muestra los empleados que tienen más de 15 días pendientes de
                                                 // vacaciones
                                                 // y que actualmente no están en vacaciones, indicando que deben
                                                 // programarlas.
        String hoyStr = localDateAString(LocalDate.now()); // Obtiene la fecha actual formateada como dd/MM/yyyy para
                                                           // comparar con las situaciones
        System.out.println("=".repeat(60));
        System.out.println("  PERSONAS QUE DEBEN SALIR DE VACACIONES");
        System.out.println("=".repeat(60));
        boolean hay = false; // Bandera para detectar si existe al menos un empleado que debe salir de
                             // vacaciones
        for (Person p : personas) { // Recorre todos los empleados registrados en el sistema
            if (calcularDiasPendientes(p) < 15)
                continue; // Ignora al empleado si tiene menos de 15 días pendientes (no adeuda un período
                          // completo)
            boolean enVacacionesHoy = false; // Bandera para verificar si el empleado ya está disfrutando vacaciones hoy
            for (SituacionAdministrativa s : p.getSituaciones()) { // Recorre las situaciones del empleado buscando
                                                                   // vacaciones activas en la fecha actual
                if (s.getTipo().equalsIgnoreCase("Vacaciones") && s.isActiva()
                        && hoyStr.compareTo(s.getFechaInicio()) >= 0
                        && hoyStr.compareTo(s.getFechaFin()) <= 0) { // Verifica que la fecha actual esté dentro del
                                                                     // período de vacaciones activo
                    enVacacionesHoy = true; // Marca que el empleado ya está en vacaciones hoy
                    break; // Sale del bucle interno en cuanto se confirma que ya está en vacaciones
                }
            }
            if (!enVacacionesHoy) { // Solo muestra al empleado si no está actualmente en vacaciones y debe salir
                System.out.printf("  %-30s | Doc: %-12s | Días pendientes: %d%n",
                        p.getName(), p.getDocumentId(), calcularDiasPendientes(p)); // Imprime el nombre, documento y
                                                                                    // días pendientes del empleado que
                                                                                    // debe salir
                hay = true; // Marca que se encontró al menos un empleado que debe salir de vacaciones
            }
        }
        if (!hay)
            System.out.println("  Ninguna persona pendiente de salir de vacaciones."); // Mensaje si todos los empleados
                                                                                       // con período adeudado ya están
                                                                                       // en vacaciones
        System.out.println("=".repeat(60));
    }

    // ===================== BENEFICIOS E INCENTIVOS =====================

    private static final String[] TIPOS_INCENTIVO = { // Arreglo con los tipos de incentivos disponibles para registrar
                                                      // a los empleados
            "Cumpleaños", "Tiempo de servicio", "Reconocimiento", "Capacitación"
    };

    private void registrarIncentivo() { // Pide el documento del empleado, muestra los tipos de incentivo disponibles
                                        // y registra el incentivo seleccionado con su fecha y descripción,
                                        // evitando duplicados de cumpleaños en el mismo año.
        Person persona = pedirPersona(); // Pide el documento del empleado y lo busca en la lista
        if (persona == null)
            return; // Sale del método si no se encontró el empleado

        printHeader("Tipos de incentivo:", 32); // Muestra el encabezado del menú de tipos de incentivo
        System.out.println("1. Cumpleaños (Celebra la Vida)");
        System.out.println("2. Tiempo de servicio");
        System.out.println("3. Reconocimiento");
        System.out.println("4. Capacitación");
        printSep(32); // Imprime una línea separadora para mejorar la legibilidad del menú

        String tipo = seleccionarDeArreglo("Seleccione el tipo (1-4)", TIPOS_INCENTIVO); // Permite al usuario elegir un
                                                                                         // tipo de incentivo del
                                                                                         // arreglo de opciones
        if (tipo == null)
            return; // Sale del método si el usuario elige la opción 0 (cancelar)

        String fecha = leerFecha("Ingrese la fecha del incentivo (dd/MM/yyyy)"); // Lee y valida la fecha del incentivo
                                                                                 // en formato dd/MM/yyyy
        int anio = Integer.parseInt(fecha.split("/")[2]); // Extrae el año de la fecha para validar duplicados de
                                                          // cumpleaños

        if (tipo.equals("Cumpleaños")) { // Solo aplica validación de duplicado para el incentivo de cumpleaños
            for (Incentivo inc : persona.getIncentivos()) { // Recorre los incentivos registrados buscando un cumpleaños
                                                            // en el mismo año
                if (inc.getTipo().equals("Cumpleaños") && inc.getAnio() == anio) { // Si ya existe un cumpleaños
                                                                                   // registrado para ese año, no
                                                                                   // permite registrar otro
                    System.out.println("Error: el incentivo de cumpleaños ya fue registrado para el año " + anio + ".");
                    return; // Sale del método sin registrar el incentivo duplicado
                }
            }
        }

        System.out.println("Ingrese una descripción del incentivo");
        String descripcion = scanner.nextLine(); // Lee la descripción libre del incentivo ingresada por el usuario

        persona.getIncentivos().add(new Incentivo(tipo, descripcion, fecha, anio)); // Crea y agrega el nuevo incentivo
                                                                                    // a la lista del empleado
        guardarDatos(); // Persiste los cambios en los archivos del sistema
        System.out.println("Incentivo registrado correctamente.");
    }

    private void consultarIncentivos() { // Pide el documento del empleado y muestra todos los incentivos registrados,
                                         // o un mensaje si no tiene ninguno.
        Person persona = pedirPersona(); // Pide el documento del empleado y lo busca en la lista
        if (persona == null)
            return; // Sale del método si no se encontró el empleado
        if (persona.getIncentivos().isEmpty()) { // Verifica si el empleado tiene incentivos registrados antes de
                                                 // mostrarlos
            System.out.println("La persona no tiene incentivos registrados.");
            return; // Sale del método si no hay incentivos que mostrar
        }
        System.out.println("=".repeat(60));
        System.out.println("  INCENTIVOS — " + persona.getName());
        System.out.println("=".repeat(60));
        persona.getIncentivos().forEach(inc -> System.out.println("  " + inc)); // Imprime cada incentivo registrado del
                                                                                // empleado usando su método toString
        System.out.println("=".repeat(60));
    }

    // ===================== SEGURIDAD Y SALUD EN EL TRABAJO =====================

    private static final String[] CONCEPTOS_MEDICOS = { "Apto", "Apto con restricciones", "No apto" }; // Arreglo con
                                                                                                       // los posibles
                                                                                                       // conceptos
                                                                                                       // médicos que
                                                                                                       // puede emitir
                                                                                                       // una evaluación
                                                                                                       // ocupacional

    private void registrarEvaluacionMedica() { // Pide el documento del empleado, solicita la fecha y concepto médico,
                                               // y registra la evaluación con sus observaciones opcionales.
        Person persona = pedirPersona(); // Pide el documento del empleado y lo busca en la lista
        if (persona == null)
            return; // Sale del método si no se encontró el empleado

        String fecha = leerFecha("Ingrese la fecha de la evaluación (dd/MM/yyyy)"); // Lee y valida la fecha de la
                                                                                    // evaluación médica

        printHeader("Concepto médico:", 32); // Muestra el encabezado del menú de conceptos médicos disponibles
        System.out.println("1. Apto");
        System.out.println("2. Apto con restricciones");
        System.out.println("3. No apto");
        printSep(32); // Imprime una línea separadora para mejorar la legibilidad del menú

        String concepto = seleccionarDeArreglo("Seleccione el concepto (1-3)", CONCEPTOS_MEDICOS); // Permite al usuario
                                                                                                   // elegir el concepto
                                                                                                   // médico del arreglo
                                                                                                   // de opciones
        if (concepto == null)
            return; // Sale del método si el usuario elige la opción 0 (cancelar)

        System.out.println("Ingrese observaciones (o presione Enter para omitir)");
        String observaciones = scanner.nextLine(); // Lee las observaciones opcionales del evaluador
        if (observaciones.isEmpty())
            observaciones = "Sin observaciones"; // Asigna texto por defecto si el usuario no ingresa observaciones

        persona.getEvaluacionesMedicas().add(new SaludOcupacional(fecha, concepto, observaciones)); // Crea y agrega la
                                                                                                    // nueva evaluación
                                                                                                    // médica a la lista
                                                                                                    // del empleado
        guardarDatos(); // Persiste los cambios en los archivos del sistema
        System.out.println("Evaluación médica registrada correctamente.");
    }

    private void consultarEvaluacionesMedicas() { // Pide el documento del empleado y muestra todas las evaluaciones
                                                  // médicas registradas,
                                                  // o un mensaje si no tiene ninguna.
        Person persona = pedirPersona(); // Pide el documento del empleado y lo busca en la lista
        if (persona == null)
            return; // Sale del método si no se encontró el empleado
        if (persona.getEvaluacionesMedicas().isEmpty()) { // Verifica si el empleado tiene evaluaciones registradas
                                                          // antes de mostrarlas
            System.out.println("La persona no tiene evaluaciones médicas registradas.");
            return; // Sale del método si no hay evaluaciones que mostrar
        }
        System.out.println("=".repeat(60));
        System.out.println("  EVALUACIONES MÉDICAS — " + persona.getName());
        System.out.println("=".repeat(60));
        persona.getEvaluacionesMedicas().forEach(ev -> System.out.println("  " + ev)); // Imprime cada evaluación médica
                                                                                       // del empleado usando su método
                                                                                       // toString
        System.out.println("=".repeat(60));
    }

    // ===================== ARCHIVO DE DATOS =====================

    private void guardarDatos() { // Guarda toda la información actual del sistema en cuatro archivos separados:
                                  // personas, situaciones, incentivos y evaluaciones médicas.
        new File("lib").mkdirs(); // Crea el directorio "lib" si no existe para evitar errores al escribir los
                                  // archivos
        guardarArchivo(RUTA_ARCHIVO, pw -> { // Guarda los datos personales y laborales de cada empleado
            for (Person p : personas) { // Recorre todos los empleados para escribirlos en el archivo
                if (p.tieneDatosLaborales()) { // Si el empleado tiene datos laborales, guarda la línea completa con el
                                               // prefijo COMPLETO
                    DatosLaborales dl = p.getDatosLaborales(); // Obtiene los datos laborales para incluirlos en la
                                                               // línea
                    pw.println("COMPLETO," + String.join(",", p.getName(), p.getDocumentId(), p.getBirthDate(),
                            p.getGender(), p.getStateCivil(), p.getRh(), p.getEmail(),
                            dl.getDependencia(), dl.getCargo(), dl.getCodigo(), dl.getGrado(),
                            dl.getTipoVinculacion(), dl.getFechaIngreso(), String.valueOf(dl.getAsignacionMensual()))); // Serializa
                                                                                                                        // todos
                                                                                                                        // los
                                                                                                                        // campos
                                                                                                                        // en
                                                                                                                        // una
                                                                                                                        // línea
                                                                                                                        // CSV
                                                                                                                        // con
                                                                                                                        // el
                                                                                                                        // prefijo
                                                                                                                        // COMPLETO
                } else {
                    pw.println("BASICO," + String.join(",", p.getName(), p.getDocumentId(), p.getBirthDate(),
                            p.getGender(), p.getStateCivil(), p.getRh(), p.getEmail())); // Serializa solo los datos
                                                                                         // personales con el prefijo
                                                                                         // BASICO cuando no hay datos
                                                                                         // laborales
                }
            }
        }, "personas");

        guardarArchivo("lib/situaciones.txt", pw -> { // Guarda todas las situaciones administrativas de cada empleado
            for (Person p : personas) // Recorre todos los empleados para escribir sus situaciones
                for (SituacionAdministrativa s : p.getSituaciones()) // Recorre las situaciones de cada empleado
                    pw.println(String.join(",", p.getDocumentId(), s.getTipo(),
                            s.getFechaInicio(), s.getFechaFin(), String.valueOf(s.isActiva()))); // Serializa cada
                                                                                                 // situación con el
                                                                                                 // documento del
                                                                                                 // empleado y su estado
                                                                                                 // activo/inactivo
        }, "situaciones");

        guardarArchivo("lib/incentivos.txt", pw -> { // Guarda todos los incentivos registrados de cada empleado
            for (Person p : personas) // Recorre todos los empleados para escribir sus incentivos
                for (Incentivo inc : p.getIncentivos()) // Recorre los incentivos de cada empleado
                    pw.println(String.join(",", p.getDocumentId(), inc.getTipo(),
                            inc.getDescripcion(), inc.getFecha(), String.valueOf(inc.getAnio()))); // Serializa cada
                                                                                                   // incentivo con el
                                                                                                   // documento del
                                                                                                   // empleado y el año
                                                                                                   // del incentivo
        }, "incentivos");

        guardarArchivo("lib/evaluaciones.txt", pw -> { // Guarda todas las evaluaciones médicas de cada empleado
            for (Person p : personas) // Recorre todos los empleados para escribir sus evaluaciones médicas
                for (SaludOcupacional ev : p.getEvaluacionesMedicas()) // Recorre las evaluaciones de cada empleado
                    pw.println(String.join(",", p.getDocumentId(),
                            ev.getFecha(), ev.getConcepto(), ev.getObservaciones())); // Serializa cada evaluación con
                                                                                      // el documento del empleado,
                                                                                      // fecha, concepto y observaciones
        }, "evaluaciones médicas");
    }

    @FunctionalInterface
    private interface EscritorArchivo { // Interfaz funcional que define el contrato para escribir datos en un archivo,
                                        // usada como parámetro en el método guardarArchivo para aplicar el patrón
                                        // estrategia.
        void escribir(PrintWriter pw) throws IOException;
    }

    private void guardarArchivo(String ruta, EscritorArchivo escritor, String nombre) { // Método genérico que abre un
                                                                                        // archivo en la ruta dada y
                                                                                        // delega la escritura
                                                                                        // al EscritorArchivo recibido,
                                                                                        // manejando errores de I/O con
                                                                                        // un mensaje descriptivo.
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) { // Abre el archivo con try-with-resources para
                                                                       // garantizar su cierre automático
            escritor.escribir(pw); // Delega la escritura al objeto funcional recibido como parámetro
        } catch (IOException e) {
            System.out.println("Error al guardar " + nombre + ": " + e.getMessage()); // Muestra el nombre del archivo y
                                                                                      // el mensaje de error para
                                                                                      // facilitar el diagnóstico
        }
    }

    private void cargarDatos() { // Carga todos los datos persistidos en archivos al iniciar el sistema:
                                 // primero las personas, luego situaciones, incentivos y evaluaciones médicas.
        if (!new File(RUTA_ARCHIVO).exists())
            return; // Si el archivo de personas no existe aún, no hay datos que cargar y se retorna
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) { // Lee el archivo línea por línea hasta el final
                String[] d = linea.split(","); // Separa cada línea en sus campos usando la coma como delimitador
                if (d[0].equals("COMPLETO")) { // Si la línea tiene el prefijo COMPLETO, crea la persona con datos
                                               // laborales
                    // d[1..7] = datos personales, d[8..14] = datos laborales
                    personas.add(new Person(d[1], d[2], d[3], d[4], d[5], d[6], d[7],
                            new DatosLaborales(d[8], d[9], d[10], d[11], d[12], d[13], Double.parseDouble(d[14])))); // Reconstruye
                                                                                                                     // el
                                                                                                                     // objeto
                                                                                                                     // Person
                                                                                                                     // completo
                                                                                                                     // con
                                                                                                                     // sus
                                                                                                                     // datos
                                                                                                                     // laborales
                } else { // Si la línea tiene el prefijo BASICO, crea la persona solo con datos
                         // personales
                    // BASICO: d[1..7] = datos personales, sin datos laborales
                    personas.add(new Person(d[1], d[2], d[3], d[4], d[5], d[6], d[7], null)); // Reconstruye el objeto
                                                                                              // Person básico con datos
                                                                                              // laborales nulos
                }
            }
            System.out.println("Datos cargados: " + personas.size() + " empleados."); // Confirma cuántos empleados
                                                                                      // fueron cargados exitosamente
                                                                                      // desde el archivo
        } catch (IOException e) {
            System.out.println("Error al cargar: " + e.getMessage()); // Muestra el error si ocurre algún problema al
                                                                      // leer el archivo de personas
        }

        cargarArchivo("lib/situaciones.txt", d -> { // Carga las situaciones administrativas y las asocia a cada persona
                                                    // por documento
            Person p = buscarPersona(d[0]); // Busca la persona dueña de esta situación usando su documento
            if (p != null) { // Solo procesa la situación si se encontró la persona correspondiente
                SituacionAdministrativa s = new SituacionAdministrativa(d[1], d[2], d[3]); // Reconstruye la situación
                                                                                           // con tipo, fecha de inicio
                                                                                           // y fecha de fin
                s.setActiva(Boolean.parseBoolean(d[4])); // Restaura el estado activo/inactivo de la situación desde el
                                                         // archivo
                p.getSituaciones().add(s); // Agrega la situación reconstruida a la lista de la persona
            }
        });

        cargarArchivo("lib/incentivos.txt", d -> { // Carga los incentivos y los asocia a cada persona por documento
            Person p = buscarPersona(d[0]); // Busca la persona dueña de este incentivo usando su documento
            if (p != null) // Solo procesa el incentivo si se encontró la persona correspondiente
                p.getIncentivos().add(new Incentivo(d[1], d[2], d[3], Integer.parseInt(d[4]))); // Reconstruye el
                                                                                                // incentivo con tipo,
                                                                                                // descripción, fecha y
                                                                                                // año, y lo agrega a la
                                                                                                // persona
        });

        cargarArchivo("lib/evaluaciones.txt", d -> { // Carga las evaluaciones médicas y las asocia a cada persona por
                                                     // documento
            Person p = buscarPersona(d[0]); // Busca la persona dueña de esta evaluación usando su documento
            if (p != null) // Solo procesa la evaluación si se encontró la persona correspondiente
                p.getEvaluacionesMedicas().add(new SaludOcupacional(d[1], d[2], d[3])); // Reconstruye la evaluación con
                                                                                        // fecha, concepto y
                                                                                        // observaciones, y la agrega a
                                                                                        // la persona
        });
    }

    @FunctionalInterface
    private interface ProcesadorLinea { // Interfaz funcional que define el contrato para procesar una línea del
                                        // archivo,
                                        // usada como parámetro en el método cargarArchivo para aplicar el patrón
                                        // estrategia.
        void procesar(String[] datos);
    }

    private void cargarArchivo(String ruta, ProcesadorLinea procesador) { // Método genérico que lee un archivo línea
                                                                          // por línea y delega el procesamiento
                                                                          // de cada línea al ProcesadorLinea recibido,
                                                                          // manejando errores de I/O.
        if (!new File(ruta).exists())
            return; // Si el archivo no existe todavía, no hay datos que cargar y se retorna sin
                    // error
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) { // Abre el archivo con try-with-resources
                                                                             // para garantizar su cierre automático
            String linea;
            while ((linea = br.readLine()) != null) // Lee el archivo línea por línea hasta el final
                procesador.procesar(linea.split(",")); // Divide la línea en campos y los delega al procesador funcional
                                                       // recibido
        } catch (IOException e) {
            System.out.println("Error al cargar " + ruta + ": " + e.getMessage()); // Muestra la ruta del archivo y el
                                                                                   // mensaje de error para facilitar el
                                                                                   // diagnóstico
        }
    }

    // ===================== UTILIDADES =====================

    /** Pide un número entero, reintentando si el input no es válido. */
    private static int leerEntero(String mensaje) { // Lee un número entero del usuario, repitiendo la solicitud hasta
                                                    // que el input sea válido
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine()); // Intenta parsear el input como entero y retorna si tiene
                                                             // éxito
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número entero válido."); // Mensaje de error si el input no es
                                                                                  // un número entero
            }
        }
    }

    /** Pide un double, reintentando si el input no es numérico. */
    private static double leerDouble(String mensaje) { // Lee un número decimal del usuario, repitiendo la solicitud
                                                       // hasta que el input sea válido
        while (true) {
            System.out.println(mensaje);
            String input = scanner.nextLine(); // Lee el input del usuario como texto para validarlo con regex antes de
                                               // parsearlo
            if (input.matches("\\d+(\\.\\d+)?"))
                return Double.parseDouble(input); // Retorna el valor si coincide con el patrón de número entero o
                                                  // decimal positivo
            System.out.println("Error: la asignación mensual solo puede contener números."); // Mensaje de error si el
                                                                                             // input no es un número
                                                                                             // válido
        }
    }

    /**
     * Pide un texto que cumpla el regex dado, mostrando el mensaje de error si no.
     */
    private static String leerTexto(String prompt, String regex, String errorMsg) { // Lee un texto del usuario y lo
                                                                                    // valida contra el regex dado,
                                                                                    // repitiendo hasta que el input sea
                                                                                    // válido
        while (true) {
            System.out.println(prompt);
            String valor = scanner.nextLine(); // Lee el texto ingresado por el usuario
            if (valor.matches(regex))
                return valor; // Retorna el texto si cumple con el patrón esperado
            System.out.println(errorMsg); // Muestra el mensaje de error específico si el texto no cumple el regex
        }
    }

    /**
     * Pide una fecha en formato dd/MM/yyyy, reintentando si el formato es inválido.
     */
    private static String leerFecha(String prompt) { // Solicita y valida una fecha en formato dd/MM/yyyy usando
                                                     // leerTexto con un regex específico
        return leerTexto(prompt, "\\d{2}/\\d{2}/\\d{4}", "Error: formato de fecha inválido. Use dd/MM/yyyy.");
    }

    /**
     * Pide el documento de una persona y la retorna; muestra error y retorna null
     * si no existe.
     * Centraliza el patrón repetido "pedir doc → buscar → chequear null" que
     * aparecía en ~10 métodos.
     */
    private Person pedirPersona() { // Solicita el documento de una persona, la busca en la lista y retorna el
                                    // objeto,
                                    // o muestra un mensaje de error y retorna null si no se encuentra.
        System.out.println("Ingrese el documento de la persona");
        String doc = scanner.nextLine(); // Lee el documento ingresado por el usuario
        Person p = buscarPersona(doc); // Busca el empleado en la lista usando el documento
        if (p == null)
            System.out.println("Error: no se encontró una persona con ese documento."); // Muestra error si no se
                                                                                        // encontró ningún empleado con
                                                                                        // ese documento
        return p; // Retorna el objeto Person encontrado o null si no existe
    }

    /**
     * Muestra un menú numerado basado en un arreglo de opciones y retorna el texto
     * elegido.
     * Retorna null si el usuario elige 0 (cancelar).
     */
    private static String seleccionarDeArreglo(String prompt, String[] opciones) { // Solicita al usuario que elija un
                                                                                   // número dentro del rango del
                                                                                   // arreglo,
                                                                                   // retorna el texto de la opción
                                                                                   // elegida o null si el usuario
                                                                                   // cancela con 0.
        while (true) { // Repite la solicitud hasta recibir una opción válida o cancelar
            System.out.println(prompt);
            String entrada = scanner.nextLine(); // Lee la opción ingresada por el usuario como texto
            try {
                int n = Integer.parseInt(entrada); // Intenta convertir la entrada a número entero
                if (n == 0)
                    return null; // Retorna null si el usuario elige 0 para cancelar
                if (n >= 1 && n <= opciones.length)
                    return opciones[n - 1]; // Retorna el texto de la opción elegida (ajuste de índice base 0)
            } catch (NumberFormatException ignored) { // Ignora la excepción si la entrada no es un número y repite la
                                                      // solicitud
            }
            System.out.println("Error: opción inválida."); // Mensaje de error si la opción no está en el rango válido
        }
    }

    /** Imprime un encabezado de sección con el ancho dado. */
    private static void printHeader(String titulo, int ancho) { // Imprime un encabezado visual formado por una línea de
                                                                // "=", el título y otra línea de "="
        String sep = "=".repeat(ancho); // Crea la línea separadora con el número de caracteres especificado
        System.out.println(sep);
        System.out.println("  " + titulo); // Imprime el título con sangría de dos espacios para mejor legibilidad
        System.out.println(sep);
    }

    /** Imprime un separador simple. */
    private static void printSep(int ancho) { // Imprime una línea de "=" con el ancho especificado como separador
                                              // visual
        System.out.println("=".repeat(ancho));
    }
}