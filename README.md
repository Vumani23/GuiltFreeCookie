# 🍪 GuiltFreeCookie

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Vue.js](https://img.shields.io/badge/Vue.js-4FC08D?style=for-the-badge&logo=vuedotjs&logoColor=white)](https://vuejs.org/)
[![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)](https://tailwindcss.com/)
[![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)

**GuiltFreeCookie** is a premium, high-performance e-commerce platform for diet-conscious cookie enthusiasts. Built with a "Glassmorphism" aesthetic, it combines a sleek **Vue 3** frontend with a secure **Spring Boot 3** backend to deliver a seamless shopping experience.

---

## 🛠 Tech Stack

### Backend 🖥️
- **Language:** ![Java](https://img.shields.io/badge/Java_21-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
- **Framework:** ![Spring Boot](https://img.shields.io/badge/Spring_Boot_3.3.2-6DB33F?style=flat-square&logo=spring-boot&logoColor=white)
- **Security:** ![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=flat-square&logo=springsecurity&logoColor=white)
- **Token Auth:** ![JWT](https://img.shields.io/badge/JWT-black?style=flat-square&logo=JSON%20web%20tokens)
- **Database:** ![MySQL](https://img.shields.io/badge/MySQL_8.0-4479A1?style=flat-square&logo=mysql&logoColor=white)
- **Build Tool:** ![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat-square&logo=apache-maven&logoColor=white)

### Frontend 🎨
- **Framework:** ![Vue 3](https://img.shields.io/badge/Vue_3-4FC08D?style=flat-square&logo=vuedotjs&logoColor=white)
- **Build Tool:** ![Vite](https://img.shields.io/badge/Vite-646CFF?style=flat-square&logo=vite&logoColor=white)
- **State Management:** ![Pinia](https://img.shields.io/badge/Pinia-yellow?style=flat-square&logo=vuedotjs&logoColor=black)
- **Styling:** ![Tailwind](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=flat-square&logo=tailwind-css&logoColor=white)
- **HTTP Client:** ![Axios](https://img.shields.io/badge/Axios-5A29E4?style=flat-square&logo=axios&logoColor=white)

---

## 📂 Project Architecture

The application follows a **Decoupled Architecture** with a stateless REST API.

### Backend Structure (`za.ac.cput.guiltfreecookie`)
- **`api/`**: Integration logic.
- **`controller/`**: REST API Endpoints.
- **`domain/`**: JPA Entities using the **Builder Pattern**.
- **`repository/`**: Data Access Layer.
- **`service/`**: Core business logic.

### Frontend Structure (`src/`)
- **`api/`**: Axios instance with JWT interceptors.
- **`layout/`**: Persistent components (`TheNavbar`, `TheFooter`).
- **`router/`**: Navigation guards and route definitions.
- **`stores/`**: Pinia global auth state.
- **`views/`**: Page components (`AuthView`, `HomeView`).


---

## 🚀 Getting Started

### 1. Database Setup
```sql
CREATE DATABASE guiltfreecookie_db;
```

### 2. Backend Configuration
Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/guiltfreecookie_db
spring.datasource.username=root
spring.datasource.password=yourpassword
```

### 3. Run the Backend
```bash
mvn clean spring-boot:run
```

### 4. Run the Frontend
```bash
npm install
npm run dev
```

---

## 🍪 Key Features

- ✅ **Glassmorphism UI**: High-end visual design with backdrop blurs.
- ✅ **Dynamic Navbar**: Responsive sticky navigation that changes based on auth state.
- ✅ **Secure CRUD**: Cookie management is protected via JWT roles.
- ✅ **Manual POJOs**: Optimized Java objects using strict Builder patterns.
- ✅ **Mobile Optimized**: Sidebar drawer for smaller screens.

---

## 📜 License
Licensed under the [MIT License](LICENSE).
