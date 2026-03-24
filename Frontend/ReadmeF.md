# Frontend - Sistema de Presupuesto Mensual Personal

## Descripción

Este módulo corresponde al frontend del proyecto Sistema de Presupuesto Mensual Personal, desarrollado para la asignatura Teoría de Base de Datos I.

El frontend fue implementado completamente en Java utilizando una interfaz por consola, centralizada en la clase principal:

- Proyecto_Teoria_Base_Datos1.java

Desde esta clase se controla el flujo completo del sistema, incluyendo:
- inicio de sesión
- registro de usuarios
- navegación por menús
- captura de datos
- ejecución de operaciones del backend
- generación de reportes

Este enfoque permite una interacción directa con el usuario mediante menús y entradas por teclado.

---

## Arquitectura

El sistema sigue una arquitectura por capas donde el frontend actúa como la capa de presentación:
Usuario
↓
Frontend (Consola - Main)
↓
DAO
↓
Base de datos MySQL
↓
Procedimientos / Funciones / Triggers


---

## Tecnologías utilizadas

- Java
- Scanner (entrada por consola)
- NetBeans
- MySQL
- Patrón DAO

---

## Estructura del frontend

El frontend está concentrado principalmente en la clase principal:
proyecto_teoria_base_datos1/
└── Proyecto_Teoria_Base_Datos1.java


Esta clase se encarga de:
- mostrar menús
- capturar entradas del usuario
- validar opciones
- gestionar sesión
- invocar métodos del backend
- mostrar resultados en consola

---

## Flujo principal

Al iniciar la aplicación se muestra el menú inicial:

1. Iniciar sesión  
2. Registrarse  
0. Salir  

Si el usuario inicia sesión correctamente, se guarda en la sesión y se accede al menú principal.

---

## Gestión de sesión

Se utiliza una clase de sesión para mantener el usuario autenticado durante la ejecución.

Permite:
- identificar el usuario actual
- restringir acceso a datos de otros usuarios
- cerrar sesión y regresar al inicio

---

## Menú principal

Una vez autenticado, el sistema presenta las siguientes opciones:

- Presupuestos
- Presupuesto detalle
- Categorías
- Subcategorías
- Obligaciones fijas
- Transacciones
- Mi usuario
- Reportes
- Cerrar sesión

---

## Módulos del sistema

### 1. Mi usuario

Permite:
- actualizar datos del usuario
- eliminar cuenta

---

### 2. Categorías

Permite:
- registrar categoría
- listar categorías
- consultar categoría
- actualizar categoría
- eliminar categoría
- obtener resumen por mes
- calcular montos presupuestados y ejecutados

---

### 3. Subcategorías

Permite:
- registrar subcategoría
- listar subcategorías
- consultar subcategoría
- actualizar subcategoría
- eliminar subcategoría
- obtener balance
- calcular proyección de gasto
- obtener promedio de gasto

---

### 4. Presupuestos

Permite:
- registrar presupuesto
- listar presupuestos
- consultar presupuesto
- actualizar presupuesto
- eliminar presupuesto
- calcular monto ejecutado
- calcular porcentaje de ejecución
- calcular balance mensual
- cerrar presupuesto
- validar vigencia

---

### 5. Presupuesto detalle

Permite:
- registrar detalle de presupuesto
- listar detalles
- consultar detalle
- actualizar detalle
- eliminar detalle

---

### 6. Transacciones

Permite:
- registrar transacción
- listar transacciones
- consultar transacción
- actualizar transacción
- eliminar transacción
- calcular montos ejecutados
- calcular porcentaje ejecutado

---

### 7. Obligaciones fijas

Permite:
- registrar obligación
- listar obligaciones
- consultar obligación
- actualizar obligación
- eliminar obligación
- calcular días hasta vencimiento
- procesar obligaciones del mes

---

### 8. Reportes

Permite generar reportes en PDF:

- resumen mensual
- distribución de gastos por categoría
- cumplimiento de presupuesto
- tendencia de gastos
- estado de obligaciones

El sistema solicita parámetros como año, mes o presupuesto, y genera el archivo PDF correspondiente.

---

## Funcionamiento

El flujo del sistema es el siguiente:

1. El usuario selecciona una opción
2. El sistema solicita los datos necesarios
3. Se invoca el DAO correspondiente
4. El DAO ejecuta procedimientos en MySQL
5. Los resultados regresan al sistema
6. Se muestran en consola o se genera un reporte

---

## Características

- Interfaz basada en consola
- Navegación por menús estructurados
- Gestión de sesión
- Validación básica de entradas
- Integración completa con el backend
- Generación de reportes desde la misma interfaz

---

## Requisitos

Para ejecutar el frontend se requiere:

- Java instalado
- NetBeans o IDE compatible
- Backend configurado
- Base de datos MySQL disponible
- Procedimientos almacenados cargados
- Librerías del proyecto agregadas

---

## Ejemplos de uso

### Inicio de sesión
El usuario ingresa correo y contraseña.  
Si son correctos, accede al sistema.

### Registro de usuario
Se ingresan los datos y el sistema crea el usuario e inicia sesión automáticamente.

### Generación de reportes
El usuario selecciona el tipo de reporte, ingresa parámetros y el sistema genera un archivo PDF.

---

## Objetivo

El frontend permite interactuar con el sistema de forma simple y estructurada, facilitando la gestión de presupuestos personales mediante una interfaz de consola.

---

## Autor

Esdras Carranza  
Teoría de Base de Datos I  
UNITEC