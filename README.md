# Sistema de Presupuesto Mensual Personal

## Descripción

El Sistema de Presupuesto Mensual Personal es una aplicación desarrollada como proyecto académico para la asignatura Teoría de Base de Datos I.

El objetivo del sistema es permitir a los usuarios gestionar sus finanzas personales mediante el control de ingresos, gastos, presupuestos, obligaciones y reportes financieros.

El sistema integra una base de datos relacional en MySQL con lógica implementada mediante procedimientos almacenados, funciones y triggers, junto con una aplicación en Java que permite la interacción del usuario a través de una interfaz de consola.

---

## Objetivos del proyecto

- Diseñar un modelo de datos relacional completo
- Implementar lógica de negocio en la base de datos
- Aplicar procedimientos almacenados, funciones y triggers
- Desarrollar una aplicación en Java conectada a MySQL
- Generar reportes financieros en PDF con visualización gráfica
- Integrar todos los componentes en una solución funcional

---

## Arquitectura del sistema

El sistema está diseñado bajo una arquitectura por capas:
Usuario
↓
Frontend (Java - Consola)
↓
DAO (Acceso a datos)
↓
Base de datos MySQL
↓
Procedimientos / Funciones / Triggers

---

## Tecnologías utilizadas

- Java
- MySQL 8
- MySQL Connector/J
- OpenPDF
- JFreeChart
- NetBeans
- DBeaver

---

## Estructura del repositorio
Proyecto_TeoriaBaseDatosI/
│
├── backend/
│ ├── dao/
│ ├── modelo/
│ ├── service/
│ ├── Conexion/
│ └── sesion/
│
├── frontend/
│ └── Proyecto_Teoria_Base_Datos1.java
│
├── database/
│ ├── ddl.sql
│ ├── procedimientos.sql
│ ├── funciones.sql
│ └── triggers.sql
│──docs/
│  ├──Modelo_Relacional.sql
│  ├──Modelo_Relacional.dbml
│  ├──Autoevaluacion.md
│
└── README.md

---

## Componentes del sistema

### Backend

El backend está desarrollado en Java y sigue el patrón DAO.

Responsabilidades:
- conexión con MySQL
- ejecución de procedimientos almacenados
- manejo de entidades del sistema
- generación de reportes en PDF
- creación de gráficos financieros

Incluye:
- DAO por cada entidad
- modelos de datos
- servicio de generación de reportes

---

### Frontend

El frontend está implementado en Java mediante consola.

Responsabilidades:
- interacción con el usuario
- captura de datos
- navegación por menús
- ejecución de operaciones del sistema
- generación de reportes

---

### Base de datos

La base de datos fue desarrollada en MySQL y contiene:

- tablas relacionales
- llaves primarias y foráneas
- procedimientos almacenados
- funciones
- triggers

Entidades principales:
- usuario
- categoria
- subcategoria
- presupuesto
- presupuesto_detalle
- obligacion_fija
- transaccion

---

## Funcionalidades principales

### Gestión de usuarios
- registro
- inicio de sesión
- actualización de datos
- eliminación de cuenta

### Gestión de presupuestos
- creación de presupuestos
- actualización y eliminación
- validación de vigencia
- cálculo de ejecución

### Gestión de categorías y subcategorías
- CRUD completo
- organización de gastos

### Gestión de transacciones
- registro de ingresos y gastos
- actualización y eliminación
- cálculo de montos ejecutados

### Gestión de obligaciones fijas
- registro de pagos recurrentes
- control de vencimientos

### Reportes
- resumen mensual
- distribución de gastos
- cumplimiento de presupuesto
- tendencia de gastos
- estado de obligaciones

Los reportes se generan en PDF e incluyen gráficos.

---

## Generación de reportes

El sistema integra:

- JFreeChart para generación de gráficos
- OpenPDF para creación de documentos PDF

Proceso:
1. Consulta de datos desde la base de datos
2. Generación de gráficos
3. Construcción del documento PDF
4. Exportación del archivo final

---

## Requisitos

Para ejecutar el proyecto se requiere:

- Java instalado
- MySQL Server 8
- NetBeans o IDE compatible
- DBeaver (opcional para administración)
- Base de datos configurada
- Scripts SQL ejecutados
- Librerías agregadas:
  - mysql-connector-j
  - openpdf
  - jfreechart

---

## Instalación y ejecución

1. Clonar el repositorio:
2. Crear la base de datos en MySQL:
- Ejecutar scripts de:
  - tablas
  - procedimientos
  - funciones
  - triggers

3. Configurar la conexión en:

4. Importar el proyecto en NetBeans

5. Agregar las librerías necesarias

6. Ejecutar la clase principal:


---

## Flujo de uso

1. El usuario inicia sesión o se registra
2. Accede al menú principal
3. Gestiona sus datos financieros
4. Registra transacciones y presupuestos
5. Genera reportes en PDF

---

## Consideraciones

- La lógica principal del sistema se encuentra en la base de datos
- El backend actúa como intermediario
- El frontend se limita a interacción y visualización
- Se garantiza integridad mediante validaciones en procedimientos y triggers

---

## Autor

Esdras Carranza  
Teoría de Base de Datos I  
UNITEC
