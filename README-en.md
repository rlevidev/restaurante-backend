# 🍽️ Restaurant Backend + Frontend

Complete order management system for restaurants, developed with **Spring Boot** (backend) and **HTML/CSS/JavaScript** (frontend).

## 📋 Overview

This project offers a complete solution for restaurants that want to manage online orders, including:

- **Backend REST API** with Spring Boot
- **Modern Web Frontend** responsive design
- **JWT Authentication System**
- **Dynamic Menu Management**
- **Complete Order System**
- **Administrative Interface** for managing orders

## 🚀 Technologies Used

### Backend

- **Java 17**
- **Spring Boot 3.5.6**
- **Spring Security** (JWT)
- **Spring Data JPA**
- **H2 Database** (development)
- **PostgreSQL** (production)
- **Maven**

### Frontend

- **HTML5**
- **CSS3** (Flexbox, Grid, Gradients)
- **JavaScript ES6+** (Vanilla)
- **Fetch API**
- **LocalStorage**

## 📁 Project Structure

```
restaurant-backend/
├── src/
│   ├── main/
│   │   ├── java/com/rlevi/restaurant_backend/
│   │   │   ├── config/          # Security, CORS configurations
│   │   │   ├── controllers/     # REST endpoints
│   │   │   ├── dto/            # Data Transfer Objects
│   │   │   ├── exception/      # Error handling
│   │   │   ├── model/          # JPA entities
│   │   │   ├── repository/     # Repositories
│   │   │   ├── security/       # JWT, Authentication
│   │   │   └── service/        # Business logic
│   │   └── resources/          # application.properties
│   └── test/                   # Unit tests
├── frontend/
│   ├── index.html             # Main page
│   ├── login.html             # Authentication
│   └── README-frontend.md     # Frontend docs
├── pom.xml                    # Maven dependencies
└── README.md                  # This file
```

## 🛠️ Installation and Configuration

### Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- **Git**
- Modern web browser

### 1. Clone Repository

```bash
git clone https://github.com/rlevidev/restaurante-backend.git
cd restaurante-backend
```

### 2. Backend - Configuration

#### Development (H2)

```bash
# Compile and run
./mvnw clean compile spring-boot:run
```

Backend will be available at: `http://localhost:8080`

#### Production (PostgreSQL)

1. Configure PostgreSQL in `application-postgres.properties`
2. Run with production profile:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=postgres
```

### 3. Frontend - Access

Open `frontend/index.html` in browser or use a local server:

```bash
# Python
python -m http.server 3000

# Node.js
npx serve frontend

# Or simply open index.html in browser
```

## 🔐 Authentication

### First Access

1. **Test Login**: `admin` / `admin`
2. **Registration**: Create new users through the "Register" tab

### Available Users

- **admin/admin**: Administrator (can manage everything)
- **Regular users**: Created via registration

## 📱 Features

### 👤 For Customers

- **🍽️ Interactive Menu**: View foods with prices
- **🛒 Shopping Cart**: Add/remove items
- **📝 Place Orders**: Complete delivery form
- **📋 Track Orders**: History and real-time status
- **👤 Profile**: Personal information

### 👨‍💼 For Administrators

- **📊 Manage Orders**: View and update status
- **🍕 Manage Menu**: CRUD operations for foods
- **👥 Manage Users**: Access control

## 🎯 API Endpoints

### Authentication

- `POST /auth/login` - Login
- `POST /auth/register` - Registration

### Foods

- `GET /alimentos/listar` - List all
- `POST /alimentos/criar` - Create food
- `PUT /alimentos/atualizar/{id}` - Update
- `DELETE /alimentos/deletar/{id}` - Delete

### Orders

- `POST /pedidos/criar` - Create order
- `GET /pedidos/listar` - List all (admin)
- `PATCH /pedidos/{id}/status` - Update status
- `DELETE /pedidos/deletar/{id}` - Cancel order

### Profile

- `GET /perfil` - User data
- `GET /perfil/pedidos` - User orders

## 🎨 Frontend - Technical Details

### Structure

- **Single Page Application** (SPA) vanilla
- **Modular components** via JavaScript functions
- **Global state** managed via variables
- **Responsive** for desktop, tablet and mobile

### Design System

- **Colors**: Blue-purple gradient (#667eea → #764ba2)
- **Typography**: Segoe UI with fallbacks
- **Shadows**: Box-shadow for depth
- **Animations**: Smooth transitions (0.3s)

### Navigation

- **Home**: Welcome page
- **Menu**: Food selection
- **Profile**: Order history
- **Logout**: Red colored logout

## 📊 Order Status

- **RECEIVED**: Order awaiting processing
- **IN_PREPARATION**: Being prepared in kitchen
- **READY**: Ready for delivery
- **ON_THE_WAY**: On the way to customer
- **DELIVERED**: Successfully delivered

## 🔧 Development

### Run Tests

```bash
./mvnw test
```

### Production Build

```bash
./mvnw clean package
java -jar target/restaurante-backend-0.0.1-SNAPSHOT.jar
```

### Debug Logs

Frontend includes detailed console logs for debugging.

## 🤝 Contributing

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License. See the `LICENSE` file for details.

## 📞 Support

For support, open an issue on GitHub or contact the development team.

---

**Developed with ❤️ for restaurants that want to innovate in online service.**
