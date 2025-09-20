# 🍽️ Restaurant Backend

[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue)](https://maven.apache.org/)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![GitHub last commit](https://img.shields.io/github/last-commit/rlevidev/restaurante-backend)](https://github.com/rlevidev/restaurante-backend/commits/main)
[![GitHub contributors](https://img.shields.io/github/contributors/rlevidev/restaurante-backend)](https://github.com/rlevidev/restaurante-backend/graphs/contributors)

[Versão em português](./README.md)

## About the Project 🎯

A complete REST API for restaurant management system developed with Spring Boot, offering authentication, menu management, and order system functionalities. Designed to facilitate restaurant management with a robust and scalable architecture.

## Project Roadmap 🚧

- [x] **JWT Authentication System** - Secure authentication with tokens
- [x] **Menu Management** - Complete CRUD for food items
- [x] **Order System** - Order control with status tracking
- [x] **User Profile** - Order history per user
- [ ] **Payment Integration** - Online payment system
- [ ] **Real-time Notifications** - WebSocket for updates
- [ ] **Admin Dashboard** - Management interface
- [ ] **Delivery API** - Integration with delivery apps

## 🏗️ Architecture

```
src/
├── main/
│   ├── java/com/rlevi/restaurante_backend/
│   │   ├── config/          # Security and message configurations
│   │   ├── controllers/     # REST API controllers
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── model/          # JPA database entities
│   │   ├── repository/     # Data repositories
│   │   ├── security/       # JWT and Spring Security configurations
│   │   └── service/        # Application business logic
│   └── resources/
│       └── application.properties
```

## 🚀 Technologies Used

![Static Badge](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Static Badge](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![Static Badge](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Static Badge](https://img.shields.io/badge/H2-003545?style=for-the-badge&logo=h2&logoColor=white)
![Static Badge](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white)

- **Java 17** - Main programming language
- **Spring Boot 3.5.6** - Development framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Data persistence
- **JWT** - Secure authentication tokens
- **H2 Database** - Development database
- **PostgreSQL** - Production database
- **Lombok** - Boilerplate code reduction
- **Maven** - Dependency management
- **Bean Validation** - Data validation

## 📋 Features

### 🔐 Authentication System

- New user registration with validation
- Secure JWT login
- Route protection with Spring Security
- Password encryption with BCrypt

### 🍕 Menu Management

- Create new menu items
- List all available food items
- Search specific food by ID
- Update food information
- Remove items from menu

### 📦 Order System

- Create orders with customer data
- List all orders (admin)
- Update order status
- Automatic status advancement
- Order deletion
- Status control: RECEIVED → PREPARING → READY → DELIVERED

### 👤 User Profile

- View order history
- Manage personal data
- Track order status

## 🔧 How to Run Locally

### Prerequisites

- [Java 17+](https://adoptium.net/)
- [Maven 3.6+](https://maven.apache.org/)
- [Git](https://git-scm.com/)

### Steps to Run

1. **Clone the repository**

```bash
git clone https://github.com/rlevidev/restaurante-backend.git
cd restaurante-backend
```

2. **Install dependencies**

```bash
./mvnw clean install
```

3. **Run the application**

```bash
./mvnw spring-boot:run
```

4. **Access the application**

- **API Base**: `http://localhost:8080`
- **H2 Console**: `http://localhost:8080/h2-console`
  - **JDBC URL**: `jdbc:h2:file:./restaurante-desenvolvimento`
  - **Username**: `sa`
  - **Password**: _(leave blank)_

## 📡 API Endpoints

### Authentication

```http
POST /auth/register  # Register new user
POST /auth/login     # Login
```

### Food Items (Menu)

```http
GET    /alimentos          # List all food items
POST   /alimentos          # Create new food item
GET    /alimentos/{id}     # Get food item by ID
PUT    /alimentos/{id}     # Update food item
DELETE /alimentos/{id}     # Delete food item
```

### Orders

```http
GET    /pedidos                    # List all orders
POST   /pedidos                    # Create new order
DELETE /pedidos/{id}               # Delete order
PUT    /pedidos/{id}/status        # Update status
POST   /pedidos/{id}/avancar-status # Automatic advancement
```

### User Profile

```http
GET /perfil/pedidos # User's order history
```

## 🔒 JWT Authentication

The API uses JWT-based authentication. After logging in, include the token in the request headers:

```http
Authorization: Bearer {your-jwt-token}
```

## 🗄️ Database Configuration

### Development (H2)

```properties
spring.datasource.url=jdbc:h2:file:./restaurante-desenvolvimento
spring.datasource.username=sa
spring.datasource.password=
```

### Production (PostgreSQL)

```properties
spring.profiles.active=postgres
spring.datasource.url=jdbc:postgresql://localhost:5432/restaurante
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## 📝 Usage Examples

### 1. Register User

```bash
POST /auth/register
Content-Type: application/json

{
  "nome": "João Silva",
  "email": "joao@email.com",
  "senha": "123456"
}
```

### 2. Login

```bash
POST /auth/login
Content-Type: application/json

{
  "email": "joao@email.com",
  "senha": "123456"
}
```

### 3. Create Food Item

```bash
POST /alimentos
Authorization: Bearer {token}
Content-Type: application/json

{
  "nomeAlimento": "Pizza Margherita",
  "precoAlimento": 29.90,
  "descricaoAlimento": "Traditional pizza with tomato sauce, mozzarella and basil"
}
```

### 4. Create Order

```bash
POST /pedidos
Authorization: Bearer {token}
Content-Type: application/json

{
  "alimentoId": 1,
  "quantidade": 2,
  "nomeCliente": "Maria Santos",
  "enderecoCliente": "Rua das Flores, 123",
  "telefoneCliente": "(11) 99999-9999"
}
```

## 🤝 How to Contribute

Contributions are always welcome! Check our [complete contribution guide](./CONTRIBUTING.md) to learn how to participate.

Here are some ways to contribute:

### Types of Contributions

- 🐛 **Bug Fixes**: Identify and fix issues
- ✨ **New Features**: Implement new resources
- 📚 **Documentation**: Improve documentation and guides
- 🧪 **Tests**: Add or improve tests
- 🎨 **UI/UX**: Improve interfaces and user experience

### Contribution Process

1. **Fork** the project
2. **Clone** your fork: `git clone https://github.com/your-username/restaurante-backend.git`
3. **Create a branch** for your feature: `git checkout -b feature/new-feature`
4. **Make your changes** and commit: `git commit -m 'Add new feature'`
5. **Push** to your branch: `git push origin feature/new-feature`
6. **Open a Pull Request** in the original repository

### Guidelines

- Follow existing code standards
- Add tests for new features
- Update documentation when necessary
- Keep commits small and descriptive

## 📄 License

This project is licensed under the GPL-3.0 License. See the [LICENSE](LICENSE) file for more details.

## 👨‍💻 Author

**R. Levi**

- GitHub: [@rlevidev](https://github.com/rlevidev)
- LinkedIn: [Your LinkedIn]

## Contributors ✨

<a href="https://github.com/rlevidev/restaurante-backend/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=rlevidev/restaurante-backend&anon=0&columns=20&max=100" />
</a>

All contributions are very welcome! Every contributor helps make the project better.

---

⭐ **If this project helped you, consider giving it a star!**

## 📞 Support

If you have questions or suggestions:

- Open an [issue](https://github.com/rlevidev/restaurante-backend/issues) on GitHub
- Contact us by email: your-email@example.com
