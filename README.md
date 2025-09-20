# 🍽️ Restaurante Backend

[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue)](https://maven.apache.org/)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![GitHub last commit](https://img.shields.io/github/last-commit/rlevidev/restaurante-backend)](https://github.com/rlevidev/restaurante-backend/commits/main)
[![GitHub contributors](https://img.shields.io/github/contributors/rlevidev/restaurante-backend)](https://github.com/rlevidev/restaurante-backend/graphs/contributors)

[English version](./README-en.md)

## Sobre o Projeto 🎯

Uma API REST completa para sistema de restaurante desenvolvida com Spring Boot, oferecendo funcionalidades de autenticação, gerenciamento de cardápio e sistema de pedidos. Projetada para facilitar a gestão de restaurantes com uma arquitetura robusta e escalável.

## Roadmap do Projeto 🚧

- [x] **Sistema de Autenticação JWT** - Autenticação segura com tokens
- [x] **Gerenciamento de Cardápio** - CRUD completo de alimentos
- [x] **Sistema de Pedidos** - Controle de pedidos com status
- [x] **Perfil do Usuário** - Histórico de pedidos por usuário
- [ ] **Integração com Pagamentos** - Sistema de pagamento online
- [ ] **Notificações em Tempo Real** - WebSocket para atualizações
- [ ] **Dashboard Administrativo** - Interface para gestão
- [ ] **API de Delivery** - Integração com apps de entrega

## 🏗️ Arquitetura

```
src/
├── main/
│   ├── java/com/rlevi/restaurante_backend/
│   │   ├── config/          # Configurações de segurança e mensagens
│   │   ├── controllers/     # Controladores REST da API
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── model/          # Entidades JPA do banco de dados
│   │   ├── repository/     # Repositórios de dados
│   │   ├── security/       # Configurações JWT e Spring Security
│   │   └── service/        # Lógica de negócio da aplicação
│   └── resources/
│       └── application.properties
```

## 🚀 Tecnologias Utilizadas

![Static Badge](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Static Badge](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![Static Badge](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Static Badge](https://img.shields.io/badge/H2-003545?style=for-the-badge&logo=h2&logoColor=white)
![Static Badge](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white)

- **Java 17** - Linguagem de programação principal
- **Spring Boot 3.5.6** - Framework para desenvolvimento
- **Spring Security** - Autenticação e autorização
- **Spring Data JPA** - Persistência de dados
- **JWT** - Tokens de autenticação segura
- **H2 Database** - Banco para desenvolvimento
- **PostgreSQL** - Banco para produção
- **Lombok** - Redução de código boilerplate
- **Maven** - Gerenciamento de dependências
- **Bean Validation** - Validação de dados

## 📋 Funcionalidades

### 🔐 Sistema de Autenticação

- Registro de novos usuários com validação
- Login seguro com JWT
- Proteção de rotas com Spring Security
- Criptografia de senhas com BCrypt

### 🍕 Gerenciamento de Cardápio

- Criar novos alimentos no cardápio
- Listar todos os alimentos disponíveis
- Buscar alimento específico por ID
- Atualizar informações de alimentos
- Remover alimentos do cardápio

### 📦 Sistema de Pedidos

- Criar pedidos com dados do cliente
- Listar todos os pedidos (admin)
- Atualizar status dos pedidos
- Avanço automático de status
- Exclusão de pedidos
- Controle de status: RECEBIDO → EM_PREPARO → PRONTO → ENTREGUE

### 👤 Perfil do Usuário

- Visualizar histórico de pedidos
- Gerenciar dados pessoais
- Acompanhar status dos pedidos

## 🔧 Como Executar Localmente

### Pré-requisitos

- [Java 17+](https://adoptium.net/)
- [Maven 3.6+](https://maven.apache.org/)
- [Git](https://git-scm.com/)

### Passos para Executar

1. **Clone o repositório**

```bash
git clone https://github.com/rlevidev/restaurante-backend.git
cd restaurante-backend
```

2. **Instale as dependências**

```bash
./mvnw clean install
```

3. **Execute a aplicação**

```bash
./mvnw spring-boot:run
```

4. **Acesse a aplicação**

- **API Base**: `http://localhost:8080`
- **Console H2**: `http://localhost:8080/h2-console`
  - **JDBC URL**: `jdbc:h2:file:./restaurante-desenvolvimento`
  - **Username**: `sa`
  - **Password**: _(deixe em branco)_

## 📡 Endpoints da API

### Autenticação

```http
POST /auth/register  # Registrar novo usuário
POST /auth/login     # Fazer login
```

### Alimentos (Cardápio)

```http
GET    /alimentos          # Listar todos os alimentos
POST   /alimentos          # Criar novo alimento
GET    /alimentos/{id}     # Buscar alimento por ID
PUT    /alimentos/{id}     # Atualizar alimento
DELETE /alimentos/{id}     # Remover alimento
```

### Pedidos

```http
GET    /pedidos                    # Listar todos os pedidos
POST   /pedidos                    # Criar novo pedido
DELETE /pedidos/{id}               # Remover pedido
PUT    /pedidos/{id}/status        # Atualizar status
POST   /pedidos/{id}/avancar-status # Avanço automático
```

### Perfil do Usuário

```http
GET /perfil/pedidos # Histórico de pedidos do usuário
```

## 🔒 Autenticação JWT

A API utiliza autenticação baseada em JWT. Após fazer login, inclua o token no header das requisições:

```http
Authorization: Bearer {seu-jwt-token}
```

## 🗄️ Configuração do Banco de Dados

### Desenvolvimento (H2)

```properties
spring.datasource.url=jdbc:h2:file:./restaurante-desenvolvimento
spring.datasource.username=sa
spring.datasource.password=
```

### Produção (PostgreSQL)

```properties
spring.profiles.active=postgres
spring.datasource.url=jdbc:postgresql://localhost:5432/restaurante
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

## 📝 Exemplos de Uso

### 1. Registrar Usuário

```bash
POST /auth/register
Content-Type: application/json

{
  "nome": "João Silva",
  "email": "joao@email.com",
  "senha": "123456"
}
```

### 2. Fazer Login

```bash
POST /auth/login
Content-Type: application/json

{
  "email": "joao@email.com",
  "senha": "123456"
}
```

### 3. Criar Alimento

```bash
POST /alimentos
Authorization: Bearer {token}
Content-Type: application/json

{
  "nomeAlimento": "Pizza Margherita",
  "precoAlimento": 29.90,
  "descricaoAlimento": "Pizza tradicional com molho de tomate, mussarela e manjericão"
}
```

### 4. Criar Pedido

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

## 🤝 Como Contribuir

Contribuições são sempre bem-vindas! Veja nosso [guia completo de contribuição](./CONTRIBUTING.md) para saber como participar.

Aqui estão algumas maneiras de contribuir:

### Tipos de Contribuições

- 🐛 **Correção de Bugs**: Identificar e corrigir problemas
- ✨ **Novas Funcionalidades**: Implementar novos recursos
- 📚 **Documentação**: Melhorar documentação e guias
- 🧪 **Testes**: Adicionar ou melhorar testes
- 🎨 **UI/UX**: Melhorar interfaces e experiência do usuário

### Processo de Contribuição

1. **Fork** o projeto
2. **Clone** seu fork: `git clone https://github.com/seu-usuario/restaurante-backend.git`
3. **Crie uma branch** para sua feature: `git checkout -b feature/nova-funcionalidade`
4. **Faça suas alterações** e commit: `git commit -m 'Adiciona nova funcionalidade'`
5. **Push** para sua branch: `git push origin feature/nova-funcionalidade`
6. **Abra um Pull Request** no repositório original

### Diretrizes

- Siga os padrões de código existentes
- Adicione testes para novas funcionalidades
- Atualize a documentação quando necessário
- Mantenha commits pequenos e descritivos

## 📄 Licença

Este projeto está sob a licença GPL-3.0. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 👨‍💻 Autor

**R. Levi**

- GitHub: [@rlevidev](https://github.com/rlevidev)
- LinkedIn: [Seu LinkedIn]

## Contribuidores ✨

<a href="https://github.com/rlevidev/restaurante-backend/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=rlevidev/restaurante-backend&anon=0&columns=20&max=100" />
</a>

Toda contribuição é muito bem-vinda! Cada colaborador ajuda a tornar o projeto melhor.

---

⭐ **Se este projeto te ajudou, considere dar uma estrela!**

## 📞 Suporte

Se você tiver dúvidas ou sugestões:

- Abra uma [issue](https://github.com/rlevidev/restaurante-backend/issues) no GitHub
