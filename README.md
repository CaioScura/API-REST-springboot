# API REST - Voll Med

Trilha da Alura Java e Spring Boot: Aprenda a criar aplicações com o framework mais amado do mundo Java.

API REST desenvolvida com Spring Boot para gerenciamento de médicos e pacientes de uma clínica médica.

---

## Tecnologias utilizadas

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 17 | Linguagem principal |
| Spring Boot | 4.1.0 | Framework base |
| Spring Data JPA | - | Persistência e repositórios |
| Spring Validation | - | Validação dos dados de entrada |
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

---

## Estrutura do projeto

```
src/main/java/med/voll/api/
├── controller/
│   ├── MedicoController.java
│   └── PacienteController.java
├── medico/
│   ├── Medico.java                   (entidade)
│   ├── MedicoRepository.java
│   ├── Especialidade.java            (enum)
│   ├── DadosCadastroMedico.java      (DTO)
│   ├── DadosListagemMedico.java      (DTO)
│   └── DadosAtualizacaoMedico.java   (DTO)
├── paciente/
│   ├── Paciente.java                 (entidade)
│   ├── PacienteRepository.java
│   ├── DadosCadastroPaciente.java    (DTO)
│   ├── DadosListagemPaciente.java    (DTO)
│   └── DadosAtualizacaoPaciente.java (DTO)
└── endereco/
    ├── Endereco.java                 (embeddable)
    └── DadosEndereco.java            (DTO)
```

---

## Endpoints

### Médicos — `/medicos`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/medicos` | Cadastra um novo médico |
| `GET` | `/medicos` | Lista médicos ativos (paginado, ordenado por nome) |
| `PUT` | `/medicos` | Atualiza dados de um médico |
| `DELETE` | `/medicos/{id}` | Inativa um médico (soft delete) |

### Pacientes — `/pacientes`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/pacientes` | Cadastra um novo paciente |
| `GET` | `/pacientes` | Lista pacientes ativos (paginado, ordenado por nome) |
| `PUT` | `/pacientes` | Atualiza dados de um paciente |
| `DELETE` | `/pacientes/{id}` | Inativa um paciente (soft delete) |

---

## Migrations (Flyway)

```
V1__create-table-medicos.sql
V2__alter-table-medicos-add-column-telefone.sql
V3__alter-table-medicos-add-column-ativo.sql
V1__create-table-pacientes.sql
V3__alter-table-pacientes-add-column-ativo.sql
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

2. Execute:
   ```bash
   mvn spring-boot:run
   ```

O Flyway criará as tabelas automaticamente na primeira execução.
