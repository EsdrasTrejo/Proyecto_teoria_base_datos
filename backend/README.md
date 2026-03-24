# Backend - Sistema de Presupuesto Mensual Personal

## Descripción

Este módulo corresponde al **backend** del proyecto **Sistema de Presupuesto Mensual Personal**, desarrollado para la asignatura **Teoría de Base de Datos I**.

El backend está construido en **Java** y se encarga de:

- conectar la aplicación con la base de datos MySQL
- ejecutar procedimientos almacenados y funciones
- gestionar la lógica de acceso a datos mediante clases DAO
- administrar entidades del sistema mediante clases modelo
- generar reportes en PDF
- construir gráficos financieros para los reportes

La lógica principal del sistema está orientada a trabajar con la base de datos a través de **procedimientos almacenados**, siguiendo el enfoque solicitado en el proyecto.

---

## Tecnologías utilizadas

- **Java**
- **MySQL 8**
- **MySQL Connector/J**
- **OpenPDF**
- **JFreeChart**
- **NetBeans**
- **DBeaver** (para administración y pruebas de la base de datos)

---

## Librerías utilizadas

### 1. MySQL Connector/J
Se utilizó para realizar la conexión entre Java y MySQL.

**Función principal:**
- abrir la conexión a la base de datos
- ejecutar procedimientos almacenados
- enviar parámetros y leer resultados
- manejar `Connection`, `CallableStatement`, `PreparedStatement` y `ResultSet`

**Uso en el proyecto:**
- la clase `ConexionBD.java` centraliza la conexión
- los DAO utilizan esta conexión para interactuar con la base de datos

---

### 2. OpenPDF
Se utilizó para la **generación de archivos PDF** dentro del sistema.

**Función principal:**
- crear documentos PDF
- agregar títulos, párrafos y tablas
- insertar imágenes
- exportar reportes financieros listos para entregar o visualizar

**Uso en el proyecto:**
- generación de reportes del sistema
- creación de documentos PDF con datos obtenidos desde la base de datos
- integración con gráficos creados previamente en el backend

---

### 3. JFreeChart
Se utilizó para la **creación de gráficos** que complementan los reportes.

**Función principal:**
- generar gráficos de barras
- generar gráficos circulares
- representar comparaciones y tendencias
- exportar gráficos como imagen para luego insertarlos en el PDF

**Uso en el proyecto:**
- gráficos de ingresos, gastos y ahorros
- distribución de gastos por categoría
- cumplimiento presupuestario
- tendencias y comparaciones financieras

---

## Estructura del backend

La estructura del proyecto está organizada por paquetes para separar responsabilidades.

```text
Source Packages
│
├── Conexion
│   └── ConexionBD.java
│
├── dao
│   ├── CategoriaDAO.java
│   ├── ObligacionFijaDAO.java
│   ├── PresupuestoDAO.java
│   ├── PresupuestoDetalleDAO.java
│   ├── ReporteDAO.java
│   ├── SubcategoriaDAO.java
│   ├── TransaccionDAO.java
│   └── UsuarioDAO.java
│
├── modelo
│   ├── Categoria.java
│   ├── ObligacionFija.java
│   ├── Presupuesto.java
│   ├── PresupuestoDetalle.java
│   ├── ReporteCumplimientoPresupuesto.java
│   ├── ReporteDistribucionGastosCategoria.java
│   ├── ReporteEstadoObligacionFija.java
│   ├── ReporteResumenMensual.java
│   ├── ReporteTendenciaGastosCategoria.java
│   ├── Subcategoria.java
│   ├── Transaccion.java
│   └── usuario.java
│
├── proyecto_teoria_base_datos1
│   └── Proyecto_Teoria_Base_Datos1.java
│
├── service
│   └── ReportePdfService.java
│
└── sesión
     └──sesión.java

Explicación de cada paquete
Conexion

Contiene la clase encargada de establecer la conexión con MySQL.

ConexionBD.java

Su responsabilidad es:

cargar el driver de MySQL
definir URL, usuario y contraseña
retornar una conexión reutilizable para los DAO

Es la base de toda la comunicación entre la aplicación y la base de datos.

dao

Este paquete contiene las clases DAO (Data Access Object).

Un DAO es una clase cuya responsabilidad es acceder a la base de datos.
En este proyecto, cada DAO se especializa en una entidad o módulo.

DAO implementados:
UsuarioDAO.java
CategoriaDAO.java
SubcategoriaDAO.java
PresupuestoDAO.java
PresupuestoDetalleDAO.java
ObligacionFijaDAO.java
TransaccionDAO.java
ReporteDAO.java
Responsabilidades de los DAO:
invocar procedimientos almacenados
enviar parámetros al motor MySQL
recuperar resultados
convertir datos de la base en objetos Java
encapsular la lógica de acceso a datos

Ejemplo:

UsuarioDAO registra, consulta, actualiza o elimina usuarios
TransaccionDAO registra movimientos financieros
ReporteDAO ejecuta consultas o procedimientos para alimentar los reportes
modelo

Contiene las clases que representan las entidades del sistema.

Estas clases sirven para modelar los datos que vienen de la base de datos y transportarlos dentro del programa.

Ejemplos:
Usuario
Categoria
Subcategoria
Presupuesto
PresupuestoDetalle
ObligacionFija
Transaccion

También incluye modelos específicos de reportes, por ejemplo:

ReporteResumenMensual
ReporteCumplimientoPresupuesto
ReporteDistribucionGastosCategoria
ReporteEstadoObligacionFija
ReporteTendenciaGastosCategoria

Estos modelos permiten manejar de forma ordenada la información que luego se imprime en tablas, gráficos o PDF.

service

Este paquete contiene la lógica de servicios del sistema.

ReportePdfService.java

Es una de las clases más importantes del backend porque se encarga de:

recibir datos de reportes desde el DAO
procesar esos datos
generar gráficos con JFreeChart
construir el documento PDF con OpenPDF
exportar el reporte final

Este servicio conecta tres partes:

la base de datos
la generación del gráfico
la exportación en PDF
sesion

Este paquete está orientado al manejo del usuario dentro del flujo de la aplicación, por ejemplo:

usuario autenticado
control de sesión actual
contexto del usuario que ejecuta operaciones

Esto permite que acciones como registrar transacciones, crear presupuestos o exportar reportes queden asociadas al usuario correcto.

proyecto_teoria_base_datos1

Contiene la clase principal del sistema.

Proyecto_Teoria_Base_Datos1.java

Es el punto de entrada de la aplicación.
Desde aquí se puede iniciar la ejecución del programa y coordinar el flujo general del sistema.

Cómo funciona el backend

El backend sigue una lógica por capas:

1. La interfaz o menú solicita una acción

Por ejemplo:

registrar usuario
crear presupuesto
registrar transacción
generar reporte
2. Se llama al DAO correspondiente

El DAO prepara la llamada al procedimiento almacenado en MySQL.

3. El DAO usa ConexionBD

Se abre la conexión con la base de datos mediante MySQL Connector/J.

4. MySQL procesa la lógica

La mayor parte de la lógica de negocio está implementada en:

procedimientos almacenados
funciones
triggers

Esto cumple con el enfoque del proyecto, donde no se debe depender de SQL improvisado directamente en el código.

5. Los resultados regresan a Java

Los datos obtenidos se convierten en objetos del paquete modelo.

6. Si es un reporte, entra ReportePdfService

Esta clase:

solicita los datos al ReporteDAO
crea tablas y gráficos
genera el archivo PDF final
Flujo general de reportes

El proceso de reportería funciona así:

el usuario solicita un reporte
el backend consulta la información necesaria en MySQL
los resultados se guardan en objetos modelo
si el reporte necesita visualización gráfica, se genera con JFreeChart
el gráfico se convierte en imagen
se crea el PDF con OpenPDF
se insertan:
título
datos del reporte
tablas
gráficos
se exporta el documento final
Ventajas de esta estructura
separa claramente responsabilidades
facilita mantenimiento y escalabilidad
permite reutilizar conexión, modelos y servicios
hace más limpio el acceso a datos
mejora la organización del proyecto
facilita pruebas por módulo
se adapta bien a proyectos académicos con fuerte integración con base de datos
Base de datos

El backend fue diseñado para trabajar con una base de datos MySQL donde la lógica principal reside en:

procedimientos almacenados CRUD
procedimientos de lógica de negocio
funciones
triggers

Algunas entidades principales manejadas por el backend son:

usuario
categoria
subcategoria
presupuesto
presupuesto_detalle
obligacion_fija
transaccion

Además, el sistema soporta reportería financiera basada en consultas y cálculos sobre estas tablas.

Requisitos para ejecutar el backend

Antes de ejecutar el proyecto, se necesita:

Java instalado
MySQL Server 8
NetBeans
base de datos creada
procedimientos almacenados, funciones y triggers cargados en MySQL
librerías agregadas al proyecto
Dependencias necesarias

Asegurate de tener agregadas estas librerías al proyecto Java:

mysql-connector-j
openpdf
jfreechart

Si se trabaja manualmente en NetBeans, estas librerías deben añadirse en las propiedades del proyecto dentro del apartado de libraries.

Ejemplo de responsabilidad del backend
Registro de usuario
la interfaz captura nombre, apellido, correo, clave y salario
UsuarioDAO llama al procedimiento sp_insertar_usuario
MySQL valida los datos y registra el usuario
Java recibe la respuesta y muestra el resultado
Generación de reporte
la interfaz solicita un reporte mensual
ReporteDAO consulta los datos
ReportePdfService genera gráfico + PDF
el sistema entrega el documento exportado
Objetivo del backend dentro del proyecto

Este backend fue desarrollado para servir como la capa de negocio del sistema de presupuesto personal, permitiendo:

interacción segura con la base de datos
reutilización de lógica centralizada en MySQL
manejo organizado de entidades financieras
generación automatizada de reportes en PDF
visualización gráfica de información presupuestaria
Autor

Esdras Carranza
Proyecto académico - Teoría de Base de Datos I
UNITEC