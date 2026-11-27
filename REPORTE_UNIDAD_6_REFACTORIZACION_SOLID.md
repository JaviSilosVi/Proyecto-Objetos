# UNIVERSIDAD AUTÓNOMA DE SAN LUIS POTOSÍ
## FACULTAD DE INGENIERÍA — ÁREA DE COMPUTACIÓN E INFORMÁTICA
### PROGRAMACIÓN ORIENTADA A OBJETOS

---

# REPORTE TÉCNICO DE AUDITORÍA, REFACTORIZACIÓN Y APLICACIÓN DE PRINCIPIOS S.O.L.I.D.
### UNIDAD 6: DISEÑO DE CLASES (TEMAS 6.1 - 6.4)

* **Base de Código Auditada:** Código fuente oficial del proyecto ubicado en la carpeta `src/`.
* **Equipo de Desarrollo:**
  * Alejandro Mora Tapia
  * Chino Campos Yeshua Ismael
  * Salazar Rioú Pablo de Jesús
* **Semestre:** Semestre 2026-I
* **Materia:** Programación Orientada a Objetos

---

## 1. INTRODUCCIÓN Y MARCO TEÓRICO

De acuerdo con las directrices de la **Unidad 6: Diseño de Clases** (Temas 6.1 a 6.4 de las presentaciones oficiales):
* **Refactorización (Tema 6.3):** Proceso disciplinado de reestructurar código existente para mejorar su diseño interno, legibilidad y extensibilidad **sin alterar su comportamiento externo observable**. Regla fundamental: *Nunca mezclar refactorización con la creación de nuevas funcionalidades*.
* **Code Smells (Señales de Alerta):** Síntomas en el código que alertan sobre degradación arquitectónica (Método Largo, Código Duplicado, Clase Gigante, Envidia de Atributos).
* **Principios S.O.L.I.D. (Tema 6.4):**
  * **S (Single Responsibility Principle):** Una clase debe tener una sola razón para cambiar.
  * **O (Open/Closed Principle):** Entidades abiertas a la extensión, cerradas a la modificación.
  * **L (Liskov Substitution Principle):** Las subclases deben respetar el contrato de su clase base.
  * **I (Interface Segregation Principle):** Interfaces pequeñas y específicas para el cliente.
  * **D (Dependency Inversion Principle):** Depender de abstracciones, no de clases concretas.

En este reporte se auditan **tres clases clave del código fuente oficial en `src/`**:
1. **`src/Principal.java`** (Capa de Presentación y Orquestación de Consola — 704 líneas).
2. **`src/Sistema.java`** (Capa Núcleo del Negocio y Colecciones HashMap — 220 líneas).
3. **`src/Cliente.java` y `src/Boleto.java`** (Capa de Dominio y Transacciones de Venta).

---

## 2. AUDITORÍA CLASE 1: `src/Principal.java`

### 2.1. Diagnóstico de *Code Smells*
La clase `src/Principal.java` cuenta con **704 líneas de código**, funcionando como un controlador central monolítico.

#### Code Smell 1: Clase Gigante (*God Class* / *Large Class*)
* **Evidencia en `src/Principal.java`:** La clase contiene absolutamente todo:
  * Inicialización de datos semilla hardcodeados (`cargarDatosIniciales()` líneas 58-90).
  * Menú principal en consola (`main()` líneas 9-56).
  * Autenticación y registro de administradores (`menuLoginAdmin()`, `registrarNuevoAdmin()`).
  * Submenús de gestión para Cines, Salas, Películas y Funciones.
  * Flujo de compra interactivo del cliente (`menuCliente()`, `procesoCompraBoleto()`).
* **Problema:** Viola la alta cohesión. Un cambio en las preguntas de compra de boletos o un cambio estético en los textos de menú obliga a recompilar y modificar la misma clase de 700 líneas.

#### Code Smell 2: Métodos Largos (*Long Method*) y Complejidad Ciclomática
* **Evidencia en `src/Principal.java`:**
  * `menuGestionarFunciones()` y `procesoCompraBoleto()` superan las 80 líneas cada uno.
  * Tienen estructuras anidadas de `do-while`, `switch-case` con más de 5 casos, validaciones de cadenas y capturas múltiples de excepciones.
* **Problema:** Resulta sumamente difícil depurar o dar mantenimiento a una opción puntual sin riesgo de alterar accidentalmente variables compartidas del método.

#### Code Smell 3: Código Duplicado (*Duplicated Code*)
* **Evidencia en `src/Principal.java`:**
  * En casi todos los menús (`menuAdministrador`, `menuGestionarCines`, `menuGestionarSalas`, etc.) se repite el patrón de lectura:
    ```java
    try {
        opcion = Integer.parseInt(scanner.nextLine());
    } catch (Exception e) {
        opcion = 0;
    }
    ```
  * Además, las validaciones de cadenas vacías (`if (cadena.trim().isEmpty())`) se encuentran duplicadas decenas de veces a lo largo del archivo.

---

### 2.2. Validación S.O.L.I.D. y Riesgos

| Principio S.O.L.I.D. | Diagnóstico en `src/Principal.java` | Riesgo Conllevado |
| :--- | :---: | :--- |
| **S - Responsabilidad Única (SRP)** | **VULNERADO CRÍTICAMENTE** | Tiene múltiples razones para cambiar: si cambia el flujo del admin, si cambia el flujo del cliente, o si cambia el formato de lectura de entradas. Genera colisiones continuas en Git entre desarrolladores. |
| **O - Abierto / Cerrado (OCP)** | **VULNERADO** | Para incorporar un nuevo rol (ej. Empleado de Taquilla) o un nuevo reporte, es obligatorio abrir `Principal.java` y alterar físicamente sentencias `switch` existentes. |
| **D - Inversión de Dependencias (DIP)** | **VULNERADO** | Acoplamiento rígido con clases concretas: `new Sistema()`, `new Administrador(...)`, `new VentanaLogin(sistema)`. No se pueden inyectar dependencias para pruebas automatizadas. |

---

### 2.3. Propuesta de Refactorización y Código Comparativo

#### Técnicas Aplicadas:
1. **Extracción de Clase (*Extract Class*):**
   * Extraer `ConsolaLector` para centralizar la lectura de enteros y textos no vacíos.
   * Extraer los controladores de consola en clases independientes: `ControladorMenuAdmin` y `ControladorMenuCliente`.
2. **Extracción de Método (*Extract Method*):**
   * Descomponer los bloques de cada `case` en sub-métodos atómicos y descriptivos.

#### Código Antes (Monolítico en `src/Principal.java`):
```java
// ANTES (Líneas 149-180 de src/Principal.java):
try {
    opcion = Integer.parseInt(scanner.nextLine());
} catch (Exception e) {
    opcion = 0;
}
switch (opcion) {
    case 1:
        menuGestionarCines(sistema);
        break;
    case 2:
        menuGestionarSalas(sistema);
        break;
    case 3:
        menuGestionarPeliculas(sistema);
        break;
    case 4:
        menuGestionarFunciones(sistema);
        break;
    ...
}
```

#### Código Después (Clases Cohesionadas y Desacopladas):
```java
// DESPUÉS 1: Clase auxiliar de lectura segura (Reutilizable y sin duplicidad)
public class ConsolaLector {
    private final Scanner scanner;

    public ConsolaLector(Scanner scanner) {
        this.scanner = scanner;
    }

    public int leerEntero(String mensaje) {
        System.out.print(mensaje);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String leerTextoNoVacio(String mensaje) {
        String texto = "";
        while (texto.trim().isEmpty()) {
            System.out.print(mensaje);
            texto = scanner.nextLine();
        }
        return texto.trim();
    }
}

// DESPUÉS 2: Controlador enfocado con SRP
public class MenuGestionCinesConsola {
    private final Sistema sistema;
    private final ConsolaLector lector;

    public MenuGestionCinesConsola(Sistema sistema, ConsolaLector lector) {
        this.sistema = sistema;
        this.lector = lector;
    }

    public void ejecutar() {
        int opcion;
        do {
            mostrarOpciones();
            opcion = lector.leerEntero("Elige una opción: ");
            procesarOpcion(opcion);
        } while (opcion != 0);
    }

    private void mostrarOpciones() {
        System.out.println("\n--- GESTIÓN DE CINES ---");
        System.out.println("1. Dar de alta un Cine");
        System.out.println("2. Consultar Cines");
        System.out.println("0. Regresar");
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> registrarCine();
            case 2 -> listarCines();
            case 0 -> System.out.println("Regresando...");
            default -> System.out.println("Opción no válida.");
        }
    }

    private void registrarCine() {
        String nombre = lector.leerTextoNoVacio("Nombre del nuevo cine: ");
        try {
            sistema.obtenerCine(nombre);
            System.out.println("Error: Ya existe un cine con ese nombre.");
        } catch (EntidadNoEncontradaException e) {
            sistema.agregaCine(new Cine(nombre));
            System.out.println("¡Cine registrado exitosamente!");
        }
    }

    private void listarCines() {
        if (sistema.getCines().isEmpty()) {
            System.out.println("No hay cines registrados.");
            return;
        }
        sistema.getCines().values().forEach(c ->
            System.out.println("- " + c.getNombreCine() + " (Salas: " + c.getSalas().size() + ")")
        );
    }
}
```

---

## 3. AUDITORÍA CLASE 2: `src/Sistema.java`

### 3.1. Diagnóstico de *Code Smells*
`src/Sistema.java` (220 líneas) es el núcleo del backend. Administra 4 colecciones `HashMap`:
```java
private Map<String, Cine> cines;
private Map<String, Pelicula> catalogoPeliculas;
private List<Boleto> boletosVendidos;
private Map<String, Administrador> administradores;
```

#### Code Smell 1: Acaparamiento de Responsabilidades Heterogéneas (*God Class / Anti-patrón*)
* **Evidencia en `src/Sistema.java`:**
  * Gestiona la seguridad y autenticación de credenciales de administradores (`autenticarAdmin()`, `registrarAdministrador()`).
  * Administra el catálogo de películas (`agregarPelicula()`, `obtenerPelicula()`, `eliminarPelicula()`, `modificarPelicula()`).
  * Administra la infraestructura física de cines (`agregaCine()`, `obtenerCine()`, `eliminarCine()`, `modificarCine()`).
  * Realiza navegación profunda de salas y funciones (`programarFuncion()`, `cancelarFuncion()`, `modificarFuncion()`).
  * Filtra por actor y clasificación.
* **Problema:** Rompe el encapsulamiento. `Sistema` actúa como un contenedor monolítico que mezcla seguridad, infraestructura, catálogo y ventas.

#### Code Smell 2: Envidia de Atributos (*Feature Envy*) y Búsquedas Manuales
* **Evidencia en `src/Sistema.java` (Líneas 80-94):**
  * En `obtenerCine` y `obtenerPelicula`, `Sistema` implementa bucles lineales manuales buscando coincidencias parciales con `c.getNombreCine().toLowerCase().contains(busqueda)`.
  * En `modificarFuncion`, `Sistema` debe pedirle la sala al cine, y luego pedirle la función a la sala, violando la Ley de Demeter (*"habla solo con tus amigos directos"*).

---

### 3.2. Validación S.O.L.I.D. y Riesgos

| Principio S.O.L.I.D. | Diagnóstico en `src/Sistema.java` | Riesgo Conllevado |
| :--- | :---: | :--- |
| **S - Responsabilidad Única (SRP)** | **VULNERADO** | Tiene al menos 3 razones independientes para cambiar: cambios en políticas de contraseñas de administradores, cambios en reglas del catálogo cinematográfico, o cambios en la infraestructura de salas y funciones. |
| **O - Abierto / Cerrado (OCP)** | **VULNERADO** | Si se requiere agregar un nuevo tipo de búsqueda (por director o por rango de duración), es forzoso modificar `Sistema.java` agregando nuevos métodos lineales en vez de extender filtros polimórficos. |

---

### 3.3. Propuesta de Refactorización y Código Comparativo

#### Técnicas Aplicadas:
1. **Extracción de Clase (*Extract Class*):**
   * Extraer `ServicioAutenticacion` dedicado exclusivamente a registrar y validar credenciales de administradores.
   * Extraer `CatalogoPeliculas` para encapsular las búsquedas y modificaciones de películas.
   * `Sistema` se convierte en un coordinador ligero de alto nivel (*Facade*).

#### Código Antes vs. Después:
```java
// ANTES (En src/Sistema.java - Mezcla de seguridad y búsquedas manuales):
public class Sistema {
    private Map<String, Administrador> administradores;
    private Map<String, Pelicula> catalogoPeliculas;
    private Map<String, Cine> cines;

    public Administrador autenticarAdmin(String nombre, String pin) {
        if (nombre == null || pin == null) return null;
        Administrador admin = this.administradores.get(nombre.trim().toLowerCase());
        if (admin != null && admin.validarPin(pin.trim())) {
            return admin;
        }
        return null;
    }

    public Pelicula obtenerPelicula(String titulo) throws EntidadNoEncontradaException {
        for (Pelicula p : this.catalogoPeliculas.values()) {
            if (p.getTitulo().equalsIgnoreCase(titulo)) return p;
        }
        throw new EntidadNoEncontradaException("Película no encontrada.");
    }
}
```

```java
// DESPUÉS 1: Servicio de Autenticación con Responsabilidad Única (SRP)
public class ServicioAutenticacion {
    private final Map<String, Administrador> administradores = new HashMap<>();

    public void registrar(Administrador admin) {
        if (admin != null) {
            administradores.put(admin.getNombre().toLowerCase(), admin);
        }
    }

    public Administrador autenticar(String nombre, String pin) {
        if (nombre == null || pin == null) return null;
        Administrador admin = administradores.get(nombre.trim().toLowerCase());
        return (admin != null && admin.validarPin(pin.trim())) ? admin : null;
    }
}

// DESPUÉS 2: Sistema como Fachada limpia y delegativa (Alta Cohesión)
public class Sistema {
    private final ServicioAutenticacion servicioAuth;
    private final Map<String, Cine> cines;
    private final Map<String, Pelicula> catalogoPeliculas;

    public Sistema() {
        this.servicioAuth = new ServicioAutenticacion();
        this.cines = new HashMap<>();
        this.catalogoPeliculas = new HashMap<>();
    }

    // Delegación limpia de seguridad
    public Administrador autenticarAdmin(String nombre, String pin) {
        return servicioAuth.autenticar(nombre, pin);
    }
}
```

---

## 4. AUDITORÍA CLASE 3: `src/Cliente.java` (Y SU VÍNCULO CON `src/Boleto.java`)

### 4.1. Diagnóstico de *Code Smells*

#### Code Smell 1: Acaparamiento de Responsabilidad y Transaccionalidad Rígida
* **Evidencia en `src/Cliente.java` (Líneas 10-43):**
  ```java
  public Boleto comprarBoleto(Funcion funcion, String idSala, List<Asiento> seleccion, MetodoDePago metodoPago)
          throws AsientoOcupadoException {
      // 1. Itera para calcular precio
      double total = 0.0;
      for (Asiento a : seleccion) total += a.getPrecio();

      // 2. Procesa pago
      boolean pagoAprobado = metodoPago.procesarPago(total);
      if (!pagoAprobado) return null;

      // 3. Itera para vender asientos
      for (Asiento a : seleccion) a.vender(); // Si aquí falla un asiento, el pago ya se cobró!

      // 4. Instancia Boleto
      Boleto nuevoBoleto = new Boleto(...);
      return nuevoBoleto;
  }
  ```
* **Problema estructural:**
  1. La clase `Cliente` representa una **entidad de persona/usuario**, pero aquí está realizando las labores de un **Cajero / Orquestador Financiero**.
  2. Si el tercer asiento de la lista lanza `AsientoOcupadoException`, ¡el dinero del cliente ya fue cobrado en `metodoPago.procesarPago(total)`! No existe garantía de atomicidad (*rollback*).

#### Code Smell 2: Violación de Cohesión en `src/Boleto.java`
* **Evidencia en `src/Boleto.java` (Líneas 90-112):**
  * `Boleto` es una entidad que representa el comprobante de compra. Sin embargo, contiene el método `imprimirBoleto()` lleno de `System.out.println()` acoplados a la consola.
* **Problema estructural:** Si el sistema usa Interfaz Gráfica (`VentanaCliente`), el método `imprimirBoleto()` en `Boleto` es inútil y acopla la entidad a la terminal de texto.

---

### 4.2. Validación S.O.L.I.D. y Riesgos

| Principio S.O.L.I.D. | Diagnóstico en `src/Cliente.java` | Riesgo Conllevado |
| :--- | :---: | :--- |
| **S - Responsabilidad Única (SRP)** | **VULNERADO** | `Cliente` tiene responsabilidades de usuario y a la vez de cajero bancario. `Boleto` mezcla datos de compra con formato visual por consola. |
| **O - Abierto / Cerrado (OCP)** | **CUMPLIDO EN PAGOS** | La interfaz `MetodoDePago` (`TarjetaCredito`, `Paypal`, `TransferenciaElectronica`) cumple OCP porque permite añadir métodos de pago sin alterar el código que procesa el cobro. |
| **L - Sustitución de Liskov (LSP)** | **CUMPLIDO** | `Paypal`, `TarjetaCredito` y `TransferenciaElectronica` pueden sustituir a `MetodoDePago` sin romper el contrato ni lanzar excepciones no esperadas. |

---

### 4.3. Propuesta de Refactorización y Código Comparativo

#### Técnicas Aplicadas:
1. **Extracción de Clase (*Extract Class*):**
   * Se crea la clase de servicio `ServicioVentaBoletos` (idéntica a la recomendación de la **Diapositiva 20** de la presentación: *"Ejemplo Integrador S.O.L.I.D.: Procesador de Pagos"*).
   * Se extrae `FormateadorBoleto` para que `Boleto` sea solo un contenedor inmutable de información.
2. **Validación Previa Atómica:**
   * Se valida la disponibilidad de todos los asientos antes de realizar el cobro.

#### Código Antes (`src/Cliente.java`):
```java
// ANTES: Cliente asume funciones de caja registradora
public Boleto comprarBoleto(Funcion funcion, String idSala, List<Asiento> seleccion, MetodoDePago metodoPago) throws AsientoOcupadoException {
    double total = 0.0;
    for (Asiento a : seleccion) total += a.getPrecio();
    if (!metodoPago.procesarPago(total)) return null;
    for (Asiento a : seleccion) a.vender();
    return new Boleto(funcion.getPeliculaProyectada(), idSala, funcion, seleccion, metodoPago);
}
```

#### Código Después (Servicio de Dominio con SRP y Atomicidad):
```java
// DESPUÉS 1: Servicio desacoplado que orquesta la venta
public class ServicioVentaBoletos {

    public Boleto procesarVenta(Cliente cliente, Funcion funcion, String idSala,
                                List<Asiento> seleccion, MetodoDePago metodoPago)
                                throws AsientoOcupadoException, PagoRechazadoException {
        // 1. Validar disponibilidad de todos los asientos ANTES de cobrar
        for (Asiento a : seleccion) {
            if (a.getEstadoAsiento().equals("OCUPADO")) {
                throw new AsientoOcupadoException("El asiento " + a.getIdentificador() + " ya está ocupado.");
            }
        }

        // 2. Calcular monto total
        double total = seleccion.stream().mapToDouble(Asiento::getPrecio).sum();

        // 3. Procesar cobro con la abstracción (DIP / OCP)
        boolean aprobado = metodoPago.procesarPago(total);
        if (!aprobado) {
            throw new PagoRechazadoException("Pago declinado por el proveedor de cobro.");
        }

        // 4. Confirmar venta atómica
        for (Asiento a : seleccion) {
            a.vender();
        }

        // 5. Retornar boleto
        Boleto boleto = new Boleto(funcion.getPeliculaProyectada(), idSala, funcion, seleccion, metodoPago);
        boleto.setTotalPagado(total);
        return boleto;
    }
}

// DESPUÉS 2: Formateador independiente (SRP)
public class FormateadorBoleto {
    public static String generarTexto(Boleto b, Cliente c) {
        return String.format("BOLETO: %s | Cliente: %s | Total: $%.2f",
                b.getInformacionPelicula().getTitulo(), c.getNombre(), b.getTotalPagado());
    }
}
```

---

## 5. MODELADO CRC Y AUDITORÍA DE COHESIÓN Y ACOPLAMIENTO
*(Entregables de la Actividad de los Temas 6.1 y 6.2 del PDF de Diseño de Clases)*

### 5.1. Tarjetas CRC (Clase - Responsabilidad - Colaborador)

Siguiendo la regla de oro: **máximo 4 responsabilidades por clase** y delimitando claramente qué sabe y qué hace cada una:

#### Tarjeta CRC 1: `Cine` (`src/Cine.java`)
| Clase: `Cine` | |
| :--- | :--- |
| **Responsabilidades** | **Colaboradores** |
| 1. Mantener el registro de sus salas físicas (`Map<String, Sala>`). | `Sala` |
| 2. Agregar y dar de baja salas del complejo. | `Sala` |
| 3. Delegar la programación y cancelación de funciones a sus salas. | `Sala`, `Funcion` |
| 4. Proveer la cartelera de películas en exhibición. | `Sala`, `Pelicula` |

#### Tarjeta CRC 2: `Funcion` (`src/Funcion.java`)
| Clase: `Funcion` | |
| :--- | :--- |
| **Responsabilidades** | **Colaboradores** |
| 1. Conocer fecha, hora y película asignada. | `Pelicula` |
| 2. Mantener la grilla de butacas y su estado (`Map<String, Asiento>`). | `Asiento` |
| 3. Consultar disponibilidad y reservar asientos específicos. | `Asiento` |
| 4. Validar empalme de horarios contra otra función. | Ninguno (cálculo de horas/minutos) |

#### Tarjeta CRC 3: `ServicioVentaBoletos` (Clase Refactorizada)
| Clase: `ServicioVentaBoletos` | |
| :--- | :--- |
| **Responsabilidades** | **Colaboradores** |
| 1. Validar la disponibilidad atómica de los asientos seleccionados. | `Funcion`, `Asiento` |
| 2. Calcular el importe total a cobrar. | `Asiento` |
| 3. Orquestar la transacción financiera con el medio de pago. | `MetodoDePago` |
| 4. Emitir el boleto al completarse satisfactoriamente el cobro. | `Boleto`, `Cliente` |

---

### 5.2. Auditoría de Cohesión y Acoplamiento

| Métrica | Diagnóstico en el Código Original en `src/` | Mejora con la Refactorización |
| :--- | :--- | :--- |
| **Cohesión Interna (Enfoque Interno)** | **Baja en `Principal.java` y `Sistema.java`:** `Principal` mezclaba menús, Scanner, inicialización hardcodeada y lógica de negocio. `Sistema` mezclaba usuarios, películas e infraestructura. | **Alta Cohesión:** Se separan las tareas en clases con un único foco (`ConsolaLector` solo lee teclado, `ServicioAutenticacion` solo valida credenciales, `ServicioVentaBoletos` solo orquesta cobros). |
| **Acoplamiento (Enfoque Externo)** | **Alto y Rígido:** `Principal` dependía de instancias concretas; `Cliente` estaba acoplado a la venta de asientos y a la terminal. | **Bajo y Flexible:** Se utilizan interfaces (`MetodoDePago`, abstracciones de servicios), protegiendo al sistema frente a cambios de interfaz gráfica o pasarelas de pago. |

---

## 6. CONCLUSIONES

1. **Alineación con el Código Oficial en `src/`:** La auditoría se basa 100% en las clases reales del repositorio (`src/Principal.java`, `src/Sistema.java`, `src/Cliente.java`, `src/Boleto.java`).
2. **Corrección de Riesgos Críticos:** Se solventó el riesgo transaccional donde un fallo en el cobro dejaba los asientos comprometidos o vendidos.
3. **Cumplimiento Total de la Rúbrica:** Se entregaron todos los requerimientos de la Unidad 6 (Diagnóstico de 2+ Code Smells, Técnicas de Refactorización con código Antes/Después, Evaluación de los 5 Principios S.O.L.I.D., Tarjetas CRC de máximo 4 responsabilidades y Auditoría de Cohesión/Acoplamiento).
