# API REST - Voll Med

Trilha da Alura Java e Spring Boot: Aprenda a criar aplicações com o framework mais amado do mundo Java.

API REST desenvolvida com Spring Boot para gerenciamento de médicos, pacientes e **consultas** de uma clínica médica, com autenticação de usuários via **Spring Security** e **JWT**, documentação interativa com **Swagger (SpringDoc)** e **testes automatizados**.

---

## Tecnologias utilizadas

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 17 | Linguagem principal |
| Spring Boot | 4.1.0 | Framework base |
| Spring Data JPA | - | Persistência e repositórios |
| Spring Validation | - | Validação dos dados de entrada |
| Spring Security | - | Autenticação e controle de acesso |
| Auth0 java-jwt | 4.2.1 | Geração e validação de tokens JWT |
| SpringDoc OpenAPI | 3.1.1 | Documentação da API (Swagger UI) |
| JUnit 5 / Mockito / AssertJ | - | Testes automatizados |
| Flyway | - | Versionamento do banco de dados |
| Lombok | - | Redução de boilerplate (getters, construtores) |
| MySQL | - | Banco de dados relacional |
| Maven | - | Gerenciamento de dependências |

---

## Conceitos aplicados

- **DTO (Data Transfer Object):** Records Java usados para receber e retornar dados da API, separando a camada de transporte da entidade de domínio
- **JPA / Hibernate:** Mapeamento objeto-relacional com anotações `@Entity`, `@Table`, `@Embedded`, `@Enumerated`
- **Paginação:** Resposta paginada com `Pageable` e `Page<T>` no `GET` de listagem
- **Soft Delete:** Exclusão lógica — o registro não é removido do banco, apenas o campo `ativo` é definido como `false`
- **Injeção por construtor:** Substituição do `@Autowired` no campo pela injeção via construtor (boa prática recomendada pelo Spring)
- **Bean Validation:** Anotações `@Valid`, `@NotBlank`, `@NotNull`, `@Email` nos DTOs para validar os dados recebidos
- **Flyway Migrations:** Versionamento incremental do schema com arquivos `.sql` em `resources/db/migration`
- **@Transactional:** Controle de transação nas operações de escrita
- **Códigos HTTP corretos:** `201 Created` com header `Location` no cadastro, `204 No Content` na exclusão, `200 OK` com DTO de detalhes na atualização e no detalhamento
- **Tratamento de erros centralizado:** Classe `@RestControllerAdvice` (`TratadorDeErros`) que converte exceções em respostas HTTP:
  - `EntityNotFoundException` → `404 Not Found`
  - `MethodArgumentNotValidException` → `400 Bad Request` com a lista de campos inválidos (`campo` / `mensagem`)
  - `ValidacaoException` (regra de negócio violada) → `400 Bad Request` com a mensagem do erro
  - Stacktrace oculto nas respostas de erro (`spring.web.error.include-stacktrace=never`)
- **Spring Security (stateless):** Sessões desabilitadas (`SessionCreationPolicy.STATELESS`) e CSRF desabilitado, já que a API é REST e protegida por token
- **Autenticação de usuários:** `AutenticacaoService` implementa `UserDetailsService` e `Usuario` implementa `UserDetails`; senhas armazenadas com hash **BCrypt**
- **JWT (JSON Web Token):** `TokenService` gera tokens assinados com HMAC256, com issuer `API Voll.med`, subject = login do usuário e expiração de **2 horas**
- **Filtro de segurança:** `SecurityFilter` (`OncePerRequestFilter`) lê o header `Authorization: Bearer <token>`, valida o token e autentica o usuário no `SecurityContextHolder`; é registrado **antes** do `UsernamePasswordAuthenticationFilter`
- **Princípios SOLID nas validações:** cada regra de negócio do agendamento e do cancelamento é uma classe separada que implementa uma interface (`ValidadorAgendamentoDeConsulta` / `ValidadorCancelamentoDeConsulta`). O Spring injeta todas como `List<...>` no `AgendaDeConsultas`, então para criar uma nova regra basta criar uma nova classe, sem alterar o service (*Single Responsibility*, *Open/Closed* e *Dependency Inversion*)
- **Escolha de médico aleatório:** se o médico não for informado no agendamento, uma query JPQL (`escolherMedicoAleatorioLivreNaData`) sorteia um médico ativo da especialidade que esteja livre na data
- **Documentação com SpringDoc/Swagger:** a API é documentada automaticamente e pode ser testada pelo navegador; o esquema de segurança `bearer-key` permite informar o token JWT direto no Swagger UI
- **Profiles:** configurações separadas por ambiente (`application.properties`, `application-teste.properties` e `application-prod.properties`)
- **Testes automatizados:** testes de repository com `@DataJpaTest` + `TestEntityManager` e testes de controller com `@SpringBootTest` + `MockMvc` + `JacksonTester`, usando `@MockitoBean` para simular o service

---

## Estrutura do projeto

```
src/main/java/med/voll/api/
├── controller/
│   ├── AutenticacaoController.java      (POST /login)
│   ├── ConsultaController.java
│   ├── MedicoController.java
│   └── PacienteController.java
├── domain/
│   ├── consulta/
│   │   ├── Consulta.java                (entidade)
│   │   ├── ConsultaRepository.java
│   │   ├── AgendaDeConsultas.java       (service: agendar / cancelar)
│   │   ├── MotivoCancelamento.java      (enum)
│   │   ├── ValidacaoException.java      (exceção de regra de negócio)
│   │   ├── DadosAgendamentoConsulta.java   (DTO)
│   │   ├── DadosCancelamentoConsulta.java  (DTO)
│   │   ├── DadosDetalhamentoConsulta.java  (DTO)
│   │   └── validacoes/
│   │       ├── agendamento/             (ValidadorAgendamentoDeConsulta + regras)
│   │       └── cancelamento/            (ValidadorCancelamentoDeConsulta + regras)
│   ├── endereco/
│   │   ├── Endereco.java                (embeddable)
│   │   └── DadosEndereco.java           (DTO)
│   ├── medico/
│   │   ├── Medico.java                  (entidade)
│   │   ├── MedicoRepository.java
│   │   ├── Especialidade.java           (enum)
│   │   ├── DadosCadastroMedico.java     (DTO)
│   │   ├── DadosListagemMedico.java     (DTO)
│   │   ├── DadosAtualizacaoMedico.java  (DTO)
│   │   └── DadosDetalhesMedico.java     (DTO)
│   ├── paciente/
│   │   ├── Paciente.java                (entidade)
│   │   ├── PacienteRepository.java
│   │   ├── DadosCadastroPaciente.java   (DTO)
│   │   ├── DadosListagemPaciente.java   (DTO)
│   │   ├── DadosAtualizacaoPaciente.java(DTO)
│   │   └── DadosDetalhesPaciente.java   (DTO)
│   └── usuario/
│       ├── Usuario.java                 (entidade / UserDetails)
│       ├── UsuarioRepository.java
│       ├── AutenticacaoService.java     (UserDetailsService)
│       └── DadosAutenticacao.java       (DTO)
└── infra/
    ├── exception/
    │   └── TratadorDeErros.java         (@RestControllerAdvice)
    ├── security/
    │   ├── SecurityConfigurations.java  (SecurityFilterChain, BCrypt, AuthenticationManager)
    │   ├── SecurityFilter.java          (filtro que valida o JWT)
    │   ├── TokenService.java            (geração/validação do JWT)
    │   └── DadosTokenJWT.java           (DTO)
    └── springdoc/
        └── SpringDocConfigurations.java (configuração do OpenAPI / Swagger)

src/test/java/med/voll/api/
├── controller/
│   └── ConsultaControllerTest.java      (testes do endpoint /consultas)
└── domain/medico/
    └── MedicoRepositoryTest.java        (testes da query de médico aleatório)
```

---

## Endpoints

> Com exceção de `POST /login` e das rotas do Swagger, **todas as rotas exigem autenticação**. Envie o token no header:
> ```
> Authorization: Bearer <token>
> ```

### Autenticação — `/login`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/login` | Autentica o usuário e retorna um token JWT |

Exemplo de requisição:

```json
{
  "login": "usuario@voll.med",
  "senha": "123456"
}
```

Exemplo de resposta:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Médicos — `/medicos`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/medicos` | Cadastra um novo médico (`201 Created`) |
| `GET` | `/medicos` | Lista médicos ativos (paginado, ordenado por nome) |
| `GET` | `/medicos/{id}` | Detalha um médico |
| `PUT` | `/medicos` | Atualiza dados de um médico |
| `DELETE` | `/medicos/{id}` | Inativa um médico (soft delete, `204 No Content`) |

### Pacientes — `/pacientes`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/pacientes` | Cadastra um novo paciente (`201 Created`) |
| `GET` | `/pacientes` | Lista pacientes ativos (paginado, ordenado por nome) |
| `GET` | `/pacientes/{id}` | Detalha um paciente |
| `PUT` | `/pacientes` | Atualiza dados de um paciente |
| `DELETE` | `/pacientes/{id}` | Inativa um paciente (soft delete, `204 No Content`) |

### Consultas — `/consultas`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/consultas` | Agenda uma consulta (`200 OK` com os detalhes da consulta) |
| `DELETE` | `/consultas` | Cancela uma consulta informando o motivo (`204 No Content`) |

Exemplo de agendamento (o `idMedico` é opcional; se não for enviado, a `especialidade` é obrigatória e um médico livre é sorteado):

```json
{
  "idMedico": 1,
  "idPaciente": 1,
  "data": "2026-11-10T10:00",
  "especialidade": "CARDIOLOGIA"
}
```

Exemplo de cancelamento (`motivo`: `PACIENTE_DESISTIU`, `MEDICO_CANCELOU` ou `OUTROS`):

```json
{
  "idConsulta": 1,
  "motivo": "PACIENTE_DESISTIU"
}
```

#### Regras de negócio do agendamento

- A clínica funciona de **segunda a sábado, das 07h às 19h**
- A consulta deve ser agendada com **no mínimo 30 minutos de antecedência**
- Não é permitido agendar com **médico ou paciente inativo**
- O médico não pode ter **outra consulta no mesmo horário**
- O paciente não pode ter **mais de uma consulta no mesmo dia**
- Se o médico não for informado, é escolhido um **médico aleatório** da especialidade que esteja disponível na data

#### Regras de negócio do cancelamento

- É obrigatório informar o **motivo do cancelamento**
- A consulta só pode ser cancelada com **no mínimo 24 horas de antecedência**

### Documentação (Swagger)

| Rota | Descrição |
|---|---|
| `/swagger-ui.html` | Interface do Swagger para visualizar e testar os endpoints |
| `/v3/api-docs` | Documentação no formato OpenAPI (JSON) |

Para testar rotas protegidas pelo Swagger, faça `POST /login`, copie o token e clique em **Authorize**.

---

## Migrations (Flyway)

```
V1__create-table-medicos.sql
V2__alter-table-medicos-add-column-telefone.sql
V3__alter-table-medicos-add-column-ativo.sql
V4__create-table-pacientes.sql
V5__alter-table-pacientes-add-column-ativo.sql
V6__create-table-usuarios.sql
V7__create-table-consultas.sql
V8__alter-table-condultas-add-column-motivo-cancelamento.sql
```

---

## Como executar

**Pré-requisitos:** Java 17, Maven e MySQL instalados.

1. Configure o banco de dados em `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost/vollmed_api
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   ```

2. Defina o segredo usado para assinar os tokens JWT pela variável de ambiente `JWT_SECRET`
   (se não for definida, o valor padrão `12345678` é usado — **não use em produção**):
   ```properties
   api.security.token.secret=${JWT_SECRET:12345678}
   ```

3. Execute:
   ```bash
   mvn spring-boot:run
   ```
   O Flyway criará as tabelas automaticamente na primeira execução.

4. Cadastre um usuário na tabela `usuarios` com a senha em hash BCrypt. Exemplo (senha `123456`):
   ```sql
   INSERT INTO usuarios (login, senha)
   VALUES ('usuario@voll.med', '$2a$12$iIAdFWDsyXIh8z88jt5CoON1fHH60jRpSJWgixrn8kvVs3u4vEUKu');
   ```

5. Faça `POST /login` para obter o token e use-o no header `Authorization` das demais requisições
   (ou acesse `http://localhost:8080/swagger-ui.html`).

### Executando em produção

O profile `prod` lê os dados do banco por variáveis de ambiente:

```bash
DATASOURCE_URL=jdbc:mysql://host/vollmed_api \
DATASOURCE_USERNAME=usuario \
DATASOURCE_PASSWORD=senha \
JWT_SECRET=um_segredo_forte \
java -jar -Dspring.profiles.active=prod target/api-0.0.1-SNAPSHOT.jar
```

---

## Testes

Os testes de repository usam um banco MySQL separado, configurado em `application-teste.properties`
(`vollmed_api_test`), por isso esse banco precisa existir antes de rodar os testes.

```bash
mvn test
```
