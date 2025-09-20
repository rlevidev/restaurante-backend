# Como Contribuir para o Restaurante Backend

## Bem-vindo!

Agradecemos seu interesse em contribuir para o Restaurante Backend! Estamos abertos a diversos tipos de contribuições, incluindo código, documentação, testes, e muito mais. Cada contribuição ajuda a tornar o projeto mais robusto e útil.

## Tipos de Contribuições

Você pode contribuir de várias maneiras:

- **🐛 Correção de Bugs**: Identificar e corrigir problemas no código
- **✨ Novas Funcionalidades**: Implementar novos recursos e melhorias
- **📚 Documentação**: Melhorar documentação e guias
- **🧪 Testes**: Adicionar ou melhorar testes unitários e de integração
- **🎨 Melhorias**: Otimizar performance e qualidade do código
- **📖 Exemplos**: Criar exemplos de uso e tutoriais

## Como Colaborar

### Processo de Contribuição

1. **Fork** o repositório
2. **Clone** seu fork localmente:

   ```bash
   git clone https://github.com/seu-usuario/restaurante-backend.git
   cd restaurante-backend
   ```

3. **Crie uma branch** para sua contribuição:

   ```bash
   git checkout -b feature/nome-da-sua-feature
   # ou
   git checkout -b fix/nome-do-bug
   # ou
   git checkout -b docs/melhoria-documentacao
   ```

4. **Instale as dependências**:

   ```bash
   ./mvnw clean install
   ```

5. **Execute os testes** para garantir que tudo está funcionando:

   ```bash
   ./mvnw test
   ```

6. **Faça suas alterações** seguindo as diretrizes abaixo

7. **Teste suas alterações**:

   ```bash
   ./mvnw spring-boot:run
   ```

8. **Commit suas alterações**:

   ```bash
   git add .
   git commit -m "feat: adiciona nova funcionalidade X"
   ```

9. **Push para seu fork**:

   ```bash
   git push origin feature/nome-da-sua-feature
   ```

10. **Abra um Pull Request** no repositório original

## Diretrizes de Desenvolvimento

### 📝 Padrões de Código

- Siga as convenções do **Java**
- Use **camelCase** para nomes de variáveis e métodos
- Use **PascalCase** para nomes de classes
- Mantenha a indentação consistente (4 espaços)
- Adicione comentários em métodos complexos

### 🏗️ Estrutura do Projeto

```
src/main/java/com/rlevi/restaurante_backend/
├── config/          # Configurações (Security, CORS, etc.)
├── controllers/     # Controladores REST da API
├── dto/            # Data Transfer Objects
├── model/          # Entidades JPA
├── repository/     # Interfaces de repositório
├── security/       # Configurações de segurança JWT
└── service/        # Lógica de negócio
```

### 🧪 Testes

- Escreva testes para **todas as novas funcionalidades**
- Mantenha cobertura de testes acima de 70%
- Use **JUnit 5** e **Mockito** para testes
- Testes devem ser **independentes** e **rápidos**

### 📚 Documentação

- Atualize o **README.md** quando necessário
- Documente **APIs** com comentários claros
- Mantenha exemplos de uso **atualizados**

### 🔒 Segurança

- Nunca commite **senhas** ou **chaves de API**
- Use **variáveis de ambiente** para configurações sensíveis
- Siga práticas de **OWASP** para APIs REST

## Padrões de Commit

Usamos o padrão **Conventional Commits**:

```bash
# Funcionalidades
git commit -m "feat: adiciona sistema de notificações"

# Correções
git commit -m "fix: corrige validação de email"

# Documentação
git commit -m "docs: atualiza guia de instalação"

# Testes
git commit -m "test: adiciona testes para autenticação"

# Refatoração
git commit -m "refactor: simplifica lógica de pedidos"

# Performance
git commit -m "perf: otimiza consulta de alimentos"
```

## Revisão de Pull Request

### ✅ Critérios de Aprovação

- [ ] **Código compila** sem erros
- [ ] **Testes passam** (todos os testes existentes + novos)
- [ ] **Documentação atualizada** (se necessário)
- [ ] **Segurança verificada** (nenhuma vulnerabilidade introduzida)
- [ ] **Performance mantida** (sem degradação significativa)
- [ ] **Padrões seguidos** (código limpo e consistente)

### 🔍 Processo de Revisão

1. **Análise automática**: CI/CD executa testes e verificações
2. **Revisão manual**: Pelo menos 1 maintainer revisa o código
3. **Feedback**: Comentários específicos sobre melhorias
4. **Aprovação**: Quando todos os critérios são atendidos

## Comportamento e Comunicação

### 📋 Código de Conduta

- Seja **respeitoso** com todos os contribuidores
- Mantenha discussões **construtivas** e **profissionais**
- Ajude outros contribuidores quando possível
- Celebre as conquistas da comunidade

### 💬 Comunicação

- Use **Issues** para discussões técnicas
- Mantenha conversas **on-topic** e organizadas
- Seja **claro** e **objetivo** nas comunicações
- Responda dúvidas de outros contribuidores

## Recompensas e Reconhecimento

### 🏆 Sistema de Reconhecimento

- **Contributors** são destacados no README
- **Issues** e **PRs** importantes são destacadas
- **Badges** no GitHub para contribuidores ativos
- **Mencões especiais** em releases

### 🎁 Benefícios

- Experiência prática com **Spring Boot**
- Networking com outros desenvolvedores
- Contribuição para projeto **open-source**
- Aprendizado contínuo e crescimento profissional

## Dúvidas?

Se você tiver dúvidas sobre como contribuir:

- 📖 Leia a documentação completa no [README.md](./README.md)
- ❓ Abra uma [Issue](https://github.com/rlevidev/restaurante-backend/issues) no GitHub
- 💬 Entre em contato com a comunidade

---

**Obrigado por contribuir com o Restaurante Backend!** 🚀

Cada contribuição, por menor que seja, faz a diferença para tornar o projeto melhor para toda a comunidade.
