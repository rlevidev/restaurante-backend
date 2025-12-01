# 🍽️ Restaurant Backend

[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue)](https://maven.apache.org/)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![GitHub last commit](https://img.shields.io/github/last-commit/rlevidev/restaurante-backend)](https://github.com/rlevidev/restaurante-backend/commits/main)
[![GitHub contributors](https://img.shields.io/github/contributors/rlevidev/restaurante-backend)](https://github.com/rlevidev/restaurante-backend/graphs/contributors)

[Portuguese version](./README.md)

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
![Static Badge](https://img.shields.io/badge/SQLite-003B57?style=for-the-badge&logo=sqlite&logoColor=white)
![Static Badge](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white)

- **Java 17** - Main programming language
- **Spring Boot 3.5.6** - Development framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Data persistence
- **JWT** - Secure authentication tokens
- **SQLite** - Development database
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

- **Multiple Items**: Create orders with multiple food items
- **Automatic Calculation**: Total calculated automatically with BigDecimal precision
- **Complete Management**: List, update and manage orders (admin)
- **Status Control**: Manual or automatic status advancement
- **Secure Transactions**: Complete transactional processing
- **Status Flow**: RECEIVED → IN_QUEUE → PREPARING → READY → ON_THE_WAY → DELIVERED
- **Detailed History**: Complete tracking of items and quantities

### 👤 User Profile

- **Detailed History**: View orders with all items and quantities
- **Complete Information**: Customer data, unit prices and subtotals
- **Tracking**: Follow each order status in real-time
- **Personal Data**: Manage profile information

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
- **Default Profile**: Development (SQLite)

## 🗄️ Running with Different Databases

The project supports two execution profiles with different databases:

### SQLite (Development Profile - `dev`)

Ideal for local development, uses an embedded SQLite database.

```bash
# Run with SQLite
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

**Features:**
- Database created automatically in `restaurante.db`
- DDL auto-update enabled
- SQL logging enabled for debugging
- No external configuration required

### PostgreSQL (Production Profile - `prod`)

For production environments, uses PostgreSQL via Docker.

```bash
# 1. Start PostgreSQL via Docker
docker-compose up -d

# 2. Run with PostgreSQL
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

**Features:**
- Connects to PostgreSQL on port 5432
- DDL auto-update enabled
- SQL logging disabled
- Tables created automatically

**Docker PostgreSQL Credentials:**
- **Host:** `localhost:5432`
- **Database:** `restaurante_db`
- **User:** `postgres`
- **Password:** `senha123`

## 📡 API Endpoints

### Authentication

| Method | Endpoint         | Description       | Required Role |
| ------ | ---------------- | ----------------- | ------------- |
| POST   | `/auth/register` | Register new user | None          |
| POST   | `/auth/login`    | Login             | None          |

### Food Items (Menu)

| Method | Endpoint                    | Description            | Required Role |
| ------ | --------------------------- | ---------------------- | ------------- |
| GET    | `/alimentos/listar`         | List all food items    | USER or ADMIN |
| GET    | `/alimentos/buscar/{id}`    | Search food item by ID | USER or ADMIN |
| POST   | `/alimentos/criar`          | Create new food item   | ADMIN         |
| PUT    | `/alimentos/atualizar/{id}` | Update food item       | ADMIN         |
| DELETE | `/alimentos/deletar/{id}`   | Delete food item       | ADMIN         |

### Orders

| Method | Endpoint                             | Description                  | Required Role |
| ------ | ------------------------------------ | ---------------------------- | ------------- |
| GET    | `/pedidos/listar`                    | List all orders              | ADMIN         |
| POST   | `/pedidos/criar`                     | Create new order             | USER or ADMIN |
| DELETE | `/pedidos/deletar/{id}`              | Delete order                 | ADMIN         |
| PATCH  | `/pedidos/{pedidoId}/status`         | Update status manually       | ADMIN         |
| PATCH  | `/pedidos/{pedidoId}/status/avancar` | Automatic status advancement | ADMIN         |

### User Profile

| Method | Endpoint          | Description          | Required Role |
| ------ | ----------------- | -------------------- | ------------- |
| GET    | `/perfil/pedidos` | User's order history | USER or ADMIN |

## 🔒 JWT Authentication

The API uses JWT-based authentication. After logging in, include the token in the request headers:

```http
Authorization: Bearer {your-jwt-token}
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
  "nome": "João Silva",
  "senha": "123456"
}
```

### 3. Create Food Item

```bash
POST /alimentos/criar
Authorization: Bearer {token}
Content-Type: application/json

{
  "nomeAlimento": "Pizza Margherita",
  "precoAlimento": 29.90,
  "descricaoAlimento": "Traditional pizza with tomato sauce, mozzarella and basil"
}
```

### 4. Create Order (Multiple Items)

```bash
POST /pedidos/criar
Authorization: Bearer {token}
Content-Type: application/json

{
  "itens": [
    {
      "alimentoId": 1,
      "quantidade": 2
    },
    {
      "alimentoId": 3,
      "quantidade": 1
    }
  ],
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

- GitHub: [@rlevidev](https://github.com/rlevidev)

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
