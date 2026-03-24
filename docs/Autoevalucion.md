# Autoevaluación del Proyecto

## Reflexión sobre el proceso de desarrollo

El desarrollo de este proyecto representó un reto importante desde el inicio, especialmente en la fase de trabajo con SQL, MySQL y el uso de herramientas como DBeaver.

En las primeras etapas me sentía bastante perdido, ya que no tenía mucha experiencia práctica con bases de datos a este nivel. Sin embargo, a medida que fui avanzando en el proyecto, fui ganando confianza en el manejo de SQL, en la creación de procedimientos almacenados, funciones y en la estructuración general del sistema.

Inicialmente utilicé como base algunos scripts proporcionados en clase y ejemplos que fui adaptando a mis necesidades. Esto me permitió tener un punto de partida y entender mejor la lógica que debía implementar.

---

## Desafíos enfrentados y soluciones

Uno de los principales desafíos fue la curva de aprendizaje inicial con SQL y la correcta sintaxis de procedimientos, funciones y triggers.

Para facilitar el desarrollo y la corrección de errores, comencé a utilizar prácticas como:

- uso de `drop procedure if exists`
- estructuración clara de bloques `begin ... end`
- validaciones con `if` dentro de procedimientos

Estas prácticas me ayudaron a trabajar de manera más ordenada y a corregir errores de forma más eficiente.

El mayor problema técnico que enfrenté ocurrió en dos ocasiones cuando olvidé la contraseña de MySQL:

### Primer caso
Tuve que eliminar completamente MySQL de mi computadora y reinstalarlo desde cero. En ese momento no tenía mucho avance, por lo que no representó un gran problema.

### Segundo caso
Este fue mucho más crítico, ya que ya tenía un avance considerable del proyecto y necesitaba conectarme desde Java. Para solucionarlo, investigué y utilicé el siguiente recurso:

https://www.youtube.com/watch?v=ivmY43PdXbw

Gracias a este proceso, logré acceder a los archivos de configuración de MySQL y cambiar mis credenciales sin perder el progreso del proyecto.

---

## Uso de inteligencia artificial

El uso de inteligencia artificial fue una herramienta de apoyo durante el desarrollo, pero no fue utilizada para generar el proyecto completo si no para despejar dudas. Sin embargo
en la parte del frontend si la utilize de lleno y para hacer los reporte en pdf también, pero en el sql como tal lo utilize para sugerencia, búsqueda de errores cuando yo no lo 
lograba encontrar.

Principalmente se utilizó para:

- comprender la sintaxis de SQL en etapas iniciales
- obtener ejemplos base de procedimientos almacenados
- identificar errores en funciones complejas
- recibir sugerencias de buenas prácticas

Algunos ejemplos clave donde la IA aportó valor:

- uso de `signal sqlstate '45000'` para manejo de errores
- uso de `str_to_date` para manipulación de fechas
- estructura de procedimientos almacenados
- generación de lógica para reportes
- implementación de gráficos con JFreeChart
- generación de PDFs con OpenPDF

En general, la IA fue utilizada como:
- guía de sintaxis
- apoyo en resolución de errores
- fuente de ideas


---

## Aprendizajes clave

Durante el desarrollo del proyecto adquirí conocimientos importantes en:

- diseño de bases de datos relacionales
- implementación de procedimientos almacenados
- uso de funciones y triggers
- conexión entre Java y MySQL
- patrón DAO para acceso a datos
- manejo de reportes en PDF
- generación de gráficos con librerías externas
- estructuración de proyectos por capas

Además, mejoré mi capacidad para:
- resolver problemas técnicos
- investigar soluciones de forma autónoma
- depurar errores
- organizar código de forma estructurada

---

## Sugerencias de mejora del proyecto

Aunque el sistema cumple con los objetivos planteados, existen varias mejoras que podrían implementarse:

- desarrollar una interfaz gráfica o aplicación web
- mejorar la experiencia de usuario
- agregar validaciones más robustas en frontend
- implementar autenticación más segura
- optimizar consultas en la base de datos
- agregar más tipos de reportes
- mejorar la visualización de datos
- implementar pruebas automatizadas

También sería recomendable:
- documentar aún más el código
- modularizar mejor algunos componentes
- agregar manejo de excepciones más detallado en Java

---

## Conclusión

Este proyecto representó un proceso de aprendizaje progresivo, donde pasé de tener poca experiencia práctica en bases de datos a poder desarrollar un sistema completo con integración entre MySQL y Java.

A pesar de los desafíos, especialmente los relacionados con la configuración del entorno y manejo de credenciales, logré superarlos mediante investigación y práctica.

El resultado final refleja no solo el cumplimiento de los requisitos académicos, sino también el desarrollo de habilidades técnicas importantes que serán útiles en futuros proyectos.