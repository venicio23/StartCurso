# 🎓 StartCurso - Sistema de Cadastro de Cursos

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.2-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue)
![Maven](https://img.shields.io/badge/Maven-Build-red)

## 📋 Sobre o Projeto

O **StartCurso** é uma API REST desenvolvida em Java com Spring Boot que permite a criação, visualização e inscrição em cursos. O sistema suporta cursos gratuitos e pagos, com diferentes tipos de usuários (Alunos e Instrutores).

## 🎯 Objetivo

Criar um sistema completo que permita:
- ✅ Cadastro e autenticação de usuários
- ✅ Criação e gerenciamento de cursos
- ✅ Sistema de inscrições
- ✅ Controle de pagamentos
- ✅ Diferenciação entre usuários (Aluno/Instrutor)

## 👥 Tipos de Usuário

### 🎓 Aluno (Usuário Final)
- Cria conta na plataforma
- Visualiza lista de cursos disponíveis
- Consulta detalhes dos cursos (descrição, vagas, valor)
- Se inscreve em cursos (gratuitos ou pagos)
- Acompanha cursos em que está inscrito

### 👨‍🏫 Instrutor (Criador de Curso)
- Cria e gerencia seus próprios cursos
- Define título, descrição, número de vagas, valor
- Acompanha inscrições nos seus cursos
- Pode editar ou excluir cursos

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 17**
- **Spring Boot 3.1.2**
- **Spring Data JPA** (Hibernate)
- **Spring Security** + JWT
- **Spring Validation**
- **PostgreSQL**
- **Maven**

### Documentação
- **Springdoc OpenAPI** (Swagger)

### Dependências Adicionais
- **ModelMapper** - Conversão entre DTOs e Entidades
- **JWT (jjwt)** - Autenticação via tokens
- **H2 Database** - Testes

## 🗃️ Modelo de Dados

### Entidades Principais

#### 👤 Usuario
```java
- id: UUID
- nomeCompleto: String
- email: String (único)
- senhaHash: String
- tipo: TipoUsuario (ALUNO/INSTRUTOR)
- ativo: Boolean
```

#### 📚 Curso
```java
- id: UUID
- titulo: String
- descricao: String
- vagas: Integer
- valor: BigDecimal (0.0 para gratuito)
- dataInicio: LocalDateTime
- dataFim: LocalDateTime
- instrutor: Usuario
- ativo: Boolean
```

#### 📝 Inscricao
```java
- id: UUID
- aluno: Usuario
- curso: Curso
- dataInscricao: LocalDateTime
- status: StatusInscricao (PENDENTE/CONFIRMADA/RECUSADA)
```

#### 💳 Pagamento
```java
- id: UUID
- inscricao: Inscricao
- valor: BigDecimal
- metodoPagamento: String
- statusPagamento: StatusPagamento (AGUARDANDO/PAGO/FALHOU)
- dataPagamento: LocalDateTime
```

## 🚀 Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.6+
- PostgreSQL 12+

### 1. Clone o repositório
```bash
git clone https://github.com/seu-usuario/StartCurso.git
cd StartCurso
```

### 2. Configure o banco de dados
Crie um banco PostgreSQL chamado `startcurso` e configure as credenciais no arquivo `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/startcurso
    username: seu_usuario
    password: sua_senha
```

### 3. Execute a aplicação
```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

### 4. Acesse a documentação da API
Swagger UI: `http://localhost:8080/swagger-ui.html`

## 📡 Endpoints da API

### 🔐 Autenticação
- `POST /api/auth/cadastro` - Cadastro de usuário
- `POST /api/auth/login` - Login

### 👤 Usuários
- `GET /api/usuarios/perfil` - Perfil do usuário logado
- `PUT /api/usuarios/perfil` - Atualizar perfil

### 📚 Cursos
- `GET /api/cursos` - Listar todos os cursos
- `GET /api/cursos/{id}` - Buscar curso por ID
- `POST /api/cursos` - Criar curso (apenas instrutores)
- `PUT /api/cursos/{id}` - Atualizar curso
- `DELETE /api/cursos/{id}` - Excluir curso

### 📝 Inscrições
- `POST /api/inscricoes` - Se inscrever em curso
- `GET /api/inscricoes/minhas` - Minhas inscrições
- `GET /api/inscricoes/curso/{cursoId}` - Inscrições de um curso

### 💳 Pagamentos
- `POST /api/pagamentos` - Processar pagamento
- `GET /api/pagamentos/historico` - Histórico de pagamentos

## 🏗️ Arquitetura do Projeto

```
src/main/java/com/venitech/startcurso/
├── 📁 config/          # Configurações (Security, Beans)
├── 📁 controller/      # Controladores REST
├── 📁 dto/            # Data Transfer Objects
├── 📁 exception/      # Tratamento de exceções
├── 📁 model/          # Entidades JPA e Enums
├── 📁 repository/     # Repositórios Spring Data
├── 📁 security/       # Configurações de segurança
├── 📁 service/        # Lógica de negócio
└── 📁 util/          # Utilitários
```

## 🧪 Testes

Execute os testes com:
```bash
mvn test
```

## 📈 Funcionalidades Futuras

- [ ] API de pagamento (Stripe/Mercado Pago)
- [ ] Sistema de notificações
- [ ] Upload de imagens para cursos
- [ ] Sistema de avaliações
- [ ] Frontend React/Angular
- [ ] App Mobile (React Native/Flutter)

## 🤝 Contribuindo

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 👨‍💻 Autor

**Seu Nome**
- GitHub: [@seu-usuario](https://github.com/seu-usuario)
- LinkedIn: [Seu Perfil](https://linkedin.com/in/seu-perfil)
- Email: seu.email@example.com

---

⭐ Se este projeto te ajudou, dê uma estrela!
