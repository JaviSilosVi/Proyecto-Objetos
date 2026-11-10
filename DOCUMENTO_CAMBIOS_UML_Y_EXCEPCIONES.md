# DOCUMENTO DE CAMBIOS DEL UML Y PROPUESTA DE MÉTODOS CRÍTICOS / EXCEPCIONES
**Proyecto:** Sistema Gestor de Cines  
**Materia:** Tecnologías Orientadas a Objetos - UASLP  
**Profesor:** Ing. Uriel Juárez Martínez  
**Avance:** Segundo Parcial (Avance 2)

---

# PARTE 1: CAMBIOS REALIZADOS AL DIAGRAMA UML

## 1. Justificación Arquitectónica: De Listas (`List`) a Diccionarios/Mapas (`Map`)
En el **Avance 1**, de acuerdo con las restricciones de inicio de curso, todas las colecciones se implementaron mediante listas secuenciales (`List<T>` con `ArrayList<T>`) y se recorrían obligatoriamente con el patrón `Iterator`.

Para este **Avance 2**, atendiendo a los requerimientos del profesor (uso formal de `HashMap` para operaciones de alta eficiencia O(1) de registro, consulta y modificación), se transformaron las estructuras donde la búsqueda por identificador es crítica:

### Comparativa Detallada: Antes vs. Después

| Clase | Atributo / Método en Avance 1 (Listas) | Atributo / Método en Avance 2 (Mapas) | Justificación y Ventaja |
| :--- | :--- | :--- | :--- |
| **`Sistema`** | `- cines : List<Cine>`<br>`- catalogoPeliculas : List<Pelicula>`<br>`+ getCines() : List<Cine>`<br>`+ getCatalogoPeliculas() : List<Pelicula>` | `- cines : Map<String, Cine>`<br>`- catalogoPeliculas : Map<String, Pelicula>`<br>`+ getCines() : Map<String, Cine>`<br>`+ getCatalogoPeliculas() : Map<String, Pelicula>` | La búsqueda de películas por título y de cines por nombre ya no requiere iterar toda la lista uno por uno. Se accede de forma inmediata con `cines.get(nombre)` y `catalogoPeliculas.get(titulo)`. |
| **`Cine`** | `- salas : List<Sala>`<br>`+ getSalas() : List<Sala>` | `- salas : Map<String, Sala>`<br>`+ getSalas() : Map<String, Sala>` | Cada sala física tiene un identificador único dentro del cine (ej. `"Sala 1"`, `"Sala VIP"`). El `Map` asocia `idSala` con el objeto `Sala`. |
| **`Sala`** | `- asientos : List<Asiento>`<br>`+ getAsientos() : List<Asiento>` | `- asientos : Map<String, Asiento>`<br>`+ getAsientos() : Map<String, Asiento>` | Cada butaca física tiene una coordenada/código (ej. `"A1"`, `"C4"`). El mapa permite saber al instante si un asiento existe y consultar su estado sin iterar toda la sala. |
| **`Funcion`** | `- asientos : List<Asiento>`<br>`+ getAsientos() : List<Asiento>`<br>`+ inicializarAsientos() : void` | `- asientos : Map<String, Asiento>`<br>`+ getAsientos() : Map<String, Asiento>`<br>`+ inicializarAsientos(plantilla : Map<String, Asiento>) : void` | Cada función en un horario clona la plantilla de asientos de la sala hacia su propio `Map<String, Asiento>`, manteniendo ocupaciones aisladas e independientes por función. |

> **Nota:** La colección `boletosVendidos : List<Boleto>` en `Sistema`, `programacion : List<Funcion>` en `Sala` y `actores : List<String>` en `Pelicula` se mantuvieron como `List` intencionalmente, ya que representan secuencias cronológicas e historiales, demostrando el uso combinado de `ArrayList` y `HashMap` tal como pide la rúbrica.

---

## 2. Nuevas Opciones y Métodos de Menú en la Clase `Principal`
En el Avance 1, `Principal` únicamente contenía el método `main(args)`. Siguiendo la retroalimentación del profesor de enriquecer la interacción por consola para cubrir todo el ciclo administrativo, se añadieron los siguientes métodos privados de gestión en el UML y en el código:

1. **`menuAdministrador(sistema : Sistema) : void`**: Submenú principal de control de acceso protegido por PIN.
2. **`menuGestionarCines(sistema : Sistema) : void`**:
   * Registrar nueva sucursal de cine en el mapa.
   * Listar cines registrados y cantidad de salas que posee cada uno.
   * Eliminar cine del sistema.
3. **`menuGestionarSalas(sistema : Sistema) : void`**:
   * Seleccionar un cine específico y agregarle salas configurando número de filas, asientos por fila y precio base.
   * Listar todas las salas configuradas en el cine seleccionado.
4. **`menuGestionarPeliculas(sistema : Sistema) : void`**:
   * Alta de películas con título, duración, clasificación, género y director.
   * Modificación de atributos de películas registradas.
   * Eliminación de películas del catálogo global.
   * Visualización del catálogo general.
5. **`menuGestionarFunciones(sistema : Sistema) : void`**:
   * Programar función cruzando: Selección de Cine $\rightarrow$ Selección de Sala $\rightarrow$ Selección de Película $\rightarrow$ Fecha y Horario.
   * Cancelación de funciones y visualización del calendario de proyecciones por sala.
6. **`menuCliente(sistema : Sistema) : void`**:
   * Consulta dinámica de cartelera disponible.
   * Proceso de compra interactiva seleccionando película, horario, butaca y pasarela de pago.

---

# PARTE 2: ACLARACIÓN DEL ARCHIVO DE ESPECIFICACIONES (Pilar 2)

> [!IMPORTANT]
> **Aclaración clave sobre las especificaciones del profesor:**
> El archivo oficial dice: **"Selección y justificación de 8 MÉTODOS CRÍTICOS del sistema con sus pruebas unitarias en JUnit 5 (@Test)"**, evaluando en cada uno:
> 1. **Camino de Éxito (*Happy Path*):** Cuando los datos son correctos y la operación se completa con éxito.
> 2. **Manejo de Excepciones (*assertThrows*):** Cuando se ingresa un dato erróneo, nulo o repetido y el sistema lanza la excepción personalizada sin colapsar.
> 
> Es decir, **no son 8 tipos de excepciones diferentes**, sino **8 métodos esenciales del negocio**, para los cuales diseñamos una familia de **4 a 5 excepciones personalizadas bien justificadas**.

---

# PARTE 3: PROPUESTA DE LAS 4 EXCEPCIONES PERSONALIZADAS

Diseñadas específicamente para el dominio de nuestro sistema de cines:

1. **`AsientoOcupadoException` (Hereda de `Exception`)**:
   * Se lanza cuando un usuario intenta reservar o comprar una butaca cuyo estado ya es `"VENDIDO"` o `"RESERVADO"`.
2. **`ConflictoHorarioException` (Hereda de `Exception`)**:
   * Se lanza cuando se intenta programar una función en una sala a la misma fecha y hora en que ya existe otra función proyectándose.
3. **`EntidadNoEncontradaException` (Hereda de `Exception`)**:
   * Se lanza cuando se busca por clave en un mapa (`Cine`, `Sala`, `Pelicula` o `Asiento`) y la entidad no existe en el sistema.
4. **`PagoRechazadoException` / `DatoInvalidoException` (Hereda de `Exception`)**:
   * Se lanza cuando un monto a pagar es negativo/cero, cuando una tarjeta no cuenta con los 16 dígitos requeridos, o cuando se intenta registrar una sala con filas negativas o nulas.

---

# PARTE 4: LOS 8 MÉTODOS CRÍTICOS A EVALUAR CON JUNIT 5

Aquí tienes los 8 métodos seleccionados, qué evalúan en el camino exitoso y qué excepción personalizada disparan en el fallo:

---

### Método 1: `Asiento.vender()` (El ejemplo que mencionaste)
* **Objetivo de negocio:** Asegurar que ningún asiento se venda dos veces (evitar sobreventa).
* **Camino de Éxito (*Happy Path*):**
  * *Escenario:* El asiento tiene estado `"DISPONIBLE"`.
  * *Acción:* Se llama a `asiento.vender()`.
  * *Resultado esperado:* Retorna `true` y el estado del asiento cambia a `"VENDIDO"`.
* **Escenario de Excepción (*assertThrows*):**
  * *Escenario:* El asiento ya fue vendido previamente (estado `"VENDIDO"`).
  * *Acción:* Se intenta volver a llamar a `asiento.vender()`.
  * *Resultado esperado:* Lanza `AsientoOcupadoException` con el mensaje: *"El asiento A1 ya se encuentra vendido y no puede comprarse nuevamente"*.

#### ¿Cómo se ve esto conceptualmente en código Java?

**En la clase `Asiento.java`:**
```java
public boolean vender() throws AsientoOcupadoException {
    if (this.estadoAsiento.equalsIgnoreCase("VENDIDO")) {
        throw new AsientoOcupadoException("El asiento " + this.identificador + " ya se encuentra ocupado.");
    }
    this.estadoAsiento = "VENDIDO";
    return true;
}
```

**En la prueba unitaria `AsientoTest.java` (JUnit 5):**
```java
@Test
void testVenderAsientoExitoso() throws AsientoOcupadoException {
    Asiento a = new Asiento("A1", "TRADICIONAL", 80.0);
    boolean resultado = a.vender();
    assertTrue(resultado);
    assertEquals("VENDIDO", a.getEstadoAsiento());
}

@Test
void testVenderAsientoYaOcupadoLanzaExcepcion() throws AsientoOcupadoException {
    Asiento a = new Asiento("A1", "TRADICIONAL", 80.0);
    a.vender(); // Primera venta (exitosa)

    // Segunda venta forzada -> Debe lanzar AsientoOcupadoException
    assertThrows(AsientoOcupadoException.class, () -> {
        a.vender();
    });
}
```

---

### Método 2: `Sala.agregarFuncion(Funcion nuevaFuncion)`
* **Objetivo de negocio:** Evitar que se empalmen proyecciones en la misma sala física.
* **Camino de Éxito:** Se agrega una función en una fecha/hora disponible. Retorna `true` y la función se añade a la programación.
* **Escenario de Excepción:** Se intenta agregar otra función a la misma sala con la misma fecha (ej. `"2026-11-20"`) y la misma hora (`18:00`). Debe lanzar **`ConflictoHorarioException`**.

---

### Método 3: `Sistema.obtenerPelicula(String titulo)`
* **Objetivo de negocio:** Garantizar la integridad en la búsqueda de películas en el `Map`.
* **Camino de Éxito:** Se busca `"Kung Fu Panda"` que está en el mapa. Retorna el objeto `Pelicula` correspondiente.
* **Escenario de Excepción:** Se busca una película inexistente `"PeliculaFantasma"`. En lugar de regresar `null` silencioso, lanza **`EntidadNoEncontradaException`**.

---

### Método 4: `Sistema.eliminarCine(String nombreCine)`
* **Objetivo de negocio:** Control de bajas en la infraestructura del mapa.
* **Camino de Éxito:** Se elimina un cine existente (`"Cinepolis Centro"`). Retorna `true` y el tamaño del mapa de cines disminuye en 1.
* **Escenario de Excepción:** Se intenta eliminar un cine que no existe. Lanza **`EntidadNoEncontradaException`**.

---

### Método 5: `Cine.obtenerSala(String idSala)`
* **Objetivo de negocio:** Localizar una sala física dentro del mapa del cine.
* **Camino de Éxito:** Se busca `"Sala 1"` en el cine. Retorna la instancia de la sala.
* **Escenario de Excepción:** Se busca `"Sala 999"` inexistente. Lanza **`EntidadNoEncontradaException`**.

---

### Método 6: `TarjetaCredito.procesarPago(double monto)` (Polimorfismo)
* **Objetivo de negocio:** Validación de transacciones financieras y pasarelas de pago.
* **Camino de Éxito:** Se cobra un monto de `$160.0` con tarjeta válida de 16 dígitos. Retorna `true`.
* **Escenario de Excepción:** Se intenta pagar un monto de `$-50.0` o con una tarjeta de solo 4 dígitos. Lanza **`PagoRechazadoException`**.

---

### Método 7: `Administrador.validarPin(String pinIngresado)`
* **Objetivo de negocio:** Control de seguridad de acceso al panel administrativo.
* **Camino de Éxito:** Se ingresa el PIN correcto (`"1234"`). Retorna `true`.
* **Escenario de Excepción:** Se ingresa un PIN incorrecto o nulo. Retorna `false` o lanza **`AccesoDenegadoException`** tras superar el número de intentos.

---

### Método 8: `Boleto.calcularTotal()`
* **Objetivo de negocio:** Precisión financiera en el cobro de butacas seleccionadas.
* **Camino de Éxito:** Se compran 2 asientos de `$80.0` cada uno. `calcularTotal()` retorna exactamente `160.0`.
* **Escenario de Excepción:** La lista de asientos asignados está vacía o es nula al momento de calcular. Lanza **`DatoInvalidoException`** (*"No se puede emitir un boleto sin asientos asignados"*).

---

Con este esquema tienes el documento formal de cambios para tu informe y la justificación perfecta de los 8 métodos con sus pruebas unitarias para el profesor.
