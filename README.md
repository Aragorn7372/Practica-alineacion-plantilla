#⚽ New Team Manager

Gestión digital de plantilla y convocatorias del club deportivo New Team

![app-icon](https://github.com/user-attachments/assets/a8527b0f-5b5c-45f6-9c88-1d0fc65f5341)



📝 Descripción del proyecto
New Team Manager es una aplicación de escritorio desarrollada para digitalizar la gestión interna del club deportivo New Team. Ofrece un entorno intuitivo, seguro y robusto para la administración de jugadores, entrenadores, convocatorias, estadísticas e historial del equipo.

Este sistema ha sido diseñado usando Kotlin y JavaFX con arquitectura MVVM, soporte para múltiples formatos de entrada/salida (CSV, JSON, XML, binario), y control de acceso basado en roles (usuario normal / administrador).

🎯 Objetivos principales
🧑‍💼 Gestión completa de la plantilla (jugadores y cuerpo técnico)

📅 Creación y modificación de convocatorias oficiales

🧾 Exportación e importación de datos (incl. ZIP y backups)

📈 Cálculo de estadísticas deportivas

🔐 Acceso mediante login seguro con roles diferenciados

🖨️ Impresión en HTML y PDF de datos relevantes

📂 Soporte multiformato (CSV, XML, JSON, Binario)

🧪 Alta cobertura de pruebas (excepto controladores)

🧩 Funcionalidades principales
📋 Gestión de Personal
Modelo base común para empleados (ID, nombre, apellidos, fecha de nacimiento, incorporación, salario, país, imagen)

Jugadores: posición, dorsal, altura, peso, minutos jugados, goles

Entrenadores: especialización (porteros, asistente, principal)

🗂️ Datos del equipo
Nombre oficial

Fecha de fundación

Escudo 

🧠 Caché LRU
Gestión de datos más recientes (hasta 5 elementos) para mayor eficiencia

🔄 Operaciones CRUD
Crear, leer, actualizar y eliminar miembros de la plantilla

Validación estricta de datos (nombre, símbolos inválidos, etc.)

📁 Entrada/Salida de datos
Formatos soportados: CSV, JSON, XML y binario

Localización configurable vía fichero externo

Exportación/importación completa de la base de datos

Soporte para backup en ZIP

🧾 Convocatorias
Fecha y descripción

Máximo 18 jugadores (máximo 2 porteros)

Excluye al cuerpo técnico (excepto el entrenador principal)

Indicación de 11 titulares

Visualización, modificación y borrado

Impresión en HTML y PDF (opcional)

Usuarios normales solo pueden consultar e imprimir

📈 Estadísticas
Cálculo automático del promedio de minutos jugados y goles (solo jugadores)

Contador de miembros en listas dinámicas

👥 Control de acceso
Usuarios almacenados en BD con contraseña cifrada (bcrypt)

Usuario normal: acceso de solo lectura

Admin: acceso total

Objeto Sesion para gestionar el usuario activo

Control de visibilidad de componentes según rol

🖼️ Interfaz gráfica
Diseño dividido: formulario a la izquierda, lista a la derecha

Contador de elementos

Botón de aceptar solo activo si el texto es válido

Confirmación al salir y alertas por entradas incorrectas

Ventana modal "Acerca de mí" con foto y datos del autor (GitHub)

Aplicación no redimensionable, con splash screen e ícono personalizado

🧪 Tests y calidad
Pruebas unitarias con alta cobertura en servicios y modelo

Excluidos: controladores JavaFX

Informes de cobertura generados automáticamente

Validación cruzada de operaciones CRUD y filtros

📚 Documentación técnica
Incluye:

✅ Requisitos funcionales y no funcionales

📄 Casos de uso (alta, baja, modificación, convocatorias)

🧭 Grafo de navegación y diseño de vistas

📊 Diagrama de clases del modelo de negocio

🧱 Diagrama entidad-relación de la base de datos

⏱️ Diagramas de secuencia

📈 Informes de cobertura y calidad

💰 Estimación de costes del desarrollo

📆 Planificación y seguimiento con Trello

Todos los diagramas están en la carpeta /docs.

📌 Tecnologías utilizadas
Java / Kotlin

JavaFX

SQLite

MVVM

JUnit 5

Gson / Jackson / JAXB

Apache Commons CSV

Bcrypt (cifrado)

Maven / Gradle

Trello (planificación)

👤 Autores
Nombre: Cristian Ortega, Carlos Cortes, Samuel Gomez, Victor Marin
GitHub: @Cristianortegaa_, @Aragorn7372, @Sggz221, @charlieecy
