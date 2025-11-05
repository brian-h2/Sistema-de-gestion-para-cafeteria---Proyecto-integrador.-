# ☕ Cafetería 

**Sistema de Gestión para Cafeterías** desarrollado en **Java + JavaFX + MySQL**, aplicando principios de **POO** (herencia, encapsulamiento, polimorfismo) e integración con base de datos mediante **JDBC** para la materia  **Interfaz grafica**.

---

## 🚀 Características Principales

✅ Gestión de productos (bebidas, alimentos, stock, categorías).
✅ Gestión de clientes con historial y puntos de fidelización.
✅ Control automático de inventario al registrar ventas.
✅ Reportes en PDF con iText y exportación por fecha.
✅ Login seguro con `bcrypt` y roles (Administrador / Empleado).
✅ Interfaz moderna con JavaFX y CSS personalizado.

---

## 🧩 Tecnologías Utilizadas

| Componente    | Tecnología |
| ------------- | ---------- |
| Lenguaje      | Java 24    |
| GUI           | JavaFX 25  |
| Base de datos | MySQL 8    |
| Conexión      | JDBC       |
| Seguridad     | Bcrypt     |
| Reportes PDF  | iText 9    |
| Logging       | SLF4J      |
| Testing       | JUnit 5    |

---

## 🏗️ Estructura del Proyecto

```
CafeteriaJava/
 ├─ src/
 │   ├─ com/cafe/ui/              → Controladores y vistas (FXML)
 │   ├─ com/cafe/domain/          → Clases de dominio (POO)
 │   ├─ com/cafe/dao/             → DAO y lógica de acceso a datos
 │   ├─ com/cafe/config/          → Conexión JDBC y utilidades
 │
 ├─ lib/                          → Dependencias externas (JARs)
 │   ├─ mysql-connector-j-9.0.0.jar
 │   ├─ itext-core-9.x.jar
 │   ├─ slf4j-api.jar
 │   ├─ bcrypt.jar
 │
 ├─ CorrerProyecto.bat                       → Ejecución rápida del sistema
 ├─ launch.json                   → Configuración para VS Code
 └─ README.md                     → Este archivo 😄
```

---

## ⚙️ Instalación y Ejecución

### 📦 Requisitos previos

* Java JDK **24 o superior**
* JavaFX SDK **25**
* MySQL Server **8.x**

### 🪜 Pasos para ejecutar

1. **Clonar el repositorio:**

   ```bash
   git clone https://github.com/tuusuario/cafeteria-java.git
   cd cafeteria-java
   ```

2. **Configurar base de datos:**

   * La base de datos esta subida en un servidor externo **Railway** (Al realizar las peticiones aguardar unos segundos 
   hasta el servidor responda)

3. **Ejecutar desde VS Code:** 
-  Primera opcion:
   * Abrir el proyecto.
   * Presionar **F5** (usa `launch.json` ya configurado).
- Segunda opcion: 
    * Abrir el proyecto.
    * Ejecutar el archivo CorrerProyecto.bat ubicado en la raiz del mismo. 


---


## ☁️ Despliegue en la Nube (opcional)

1. Crear una base de datos en [Railway.app](https://railway.app) o [PlanetScale](https://planetscale.com/).
2. Copiar la URL de conexión en `DB.java`.
3. Subir el proyecto a **GitHub** o **GitLab**.
4. Cualquier usuario puede clonar y correr con **F5** o `CorrerProyecto.bat`.

---

## 💡 Créditos

Desarrollado por **Brian**
🎯 Enfoque: escalabilidad, usabilidad y diseño modular.
📅 2025 — Proyecto completo con JavaFX + MySQL + POO.

