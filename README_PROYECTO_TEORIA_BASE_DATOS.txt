#  Sistema de Presupuesto Personal

Proyecto académico desarrollado para la asignatura **Fundamentos de Sistemas de Bases de Datos**.

El sistema permite a los usuarios gestionar presupuestos mensuales, categorías, subcategorías, obligaciones fijas y transacciones financieras.

---

##  Objetivo del Proyecto

Diseñar e implementar:

- Modelo Entidad–Relación (ERD)
- Modelo Relacional (MR)
- Base de datos en MySQL
- Aplicación en Java conectada a la base de datos

Aplicando correctamente reglas de cardinalidad, opcionalidad, integridad referencial y normalización.

---

##  Tecnologías Utilizadas

| Tecnología | Uso |
|------------|------|
| **MySQL** | Motor de base de datos |
| **DBeaver** | Administración y modelado de la base de datos |
| **Java** | Lógica de aplicación |
| **NetBeans** | IDE para desarrollo en Java |
| **dbdiagram.io** | Diseño del Modelo Relacional |

---

##  Estructura del Proyecto

---

##  Modelo de Datos

### Entidades Principales

- Usuario
- Presupuesto
- Categoría
- Subcategoría
- Presupuesto_Detalle
- Obligación_Fija
- Transacción

### Relaciones Clave

- Usuario 1 — N Presupuesto
- Categoría 1 — N Subcategoría
- Presupuesto 1 — N Presupuesto_Detalle
- Subcategoría 1 — N Presupuesto_Detalle
- Usuario 1 — N Obligación_Fija
- Subcategoría 1 — N Obligación_Fija
- Usuario 1 — N Transacción
- Presupuesto 1 — N Transacción
- Subcategoría 1 — N Transacción
- Obligación_Fija 1 — N Transacción (opcional)

---

##  Reglas de Negocio Implementadas

- Solo puede existir **un presupuesto activo por usuario en un período dado**.
- No se permiten transacciones sin usuario.
- Una obligación fija puede generar múltiples transacciones.
- Las subcategorías pertenecen obligatoriamente a una categoría.

---

##  Configuración de la Base de Datos

### Crear Base de Datos en MySQL

```sql
CREATE DATABASE sistema_presupuesto;
USE sistema_presupuesto;


