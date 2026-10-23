# Event & Email Microservices

Projeto desenvolvido com **Java e Spring Boot**, explorando arquitetura de microsserviços, desenvolvimento de APIs REST, gerenciamento de eventos, inscrição de participantes e envio de e-mails utilizando o **Amazon Simple Email Service (AWS SES)**.

A plataforma é composta por duas aplicações independentes: o **Event Microservice**, responsável pelo gerenciamento de eventos e inscrições, e o **Email Microservice**, responsável pelo envio de e-mails.

## Arquitetura

```text
                  Cliente
                     |
                     v
            Event Microservice
             Spring Boot :8080
                     |
                     | HTTP / REST
                     v
            Email Microservice
             Spring Boot :8090
                     |
                     v
              Amazon SES (AWS)
                     |
                     v
                 Destinatário
```

Cada microsserviço possui sua própria aplicação Spring Boot, configurações e dependências, mantendo responsabilidades separadas.

## Microsserviços

### Event Microservice

Responsável pelo gerenciamento de eventos e inscrições de participantes.

- Consultar todos os eventos cadastrados.
- Listar eventos futuros com base nas datas.
- Cadastrar novos eventos.
- Registrar participantes respeitando a capacidade máxima do evento.
- Persistir os dados utilizando o banco de dados H2.

### Email Microservice

Responsável pelo processamento das solicitações de envio de e-mails.

- Disponibilizar um endpoint REST para envio de mensagens.
- Receber destinatário, assunto e conteúdo do e-mail.
- Integrar com o Amazon SES por meio do AWS SDK para Java.
- Tratar exceções e falhas durante o envio das mensagens.

## Tecnologias utilizadas

- **Java** — linguagem de programação.
- **Spring Boot** — desenvolvimento das aplicações backend.
- **Spring Web** — criação de APIs REST.
- **Spring Data JPA** — persistência e acesso a dados.
- **H2 Database** — banco de dados utilizado pelo Event Microservice.
- **Spring Cloud OpenFeign** — comunicação HTTP declarativa, caso configurada no projeto.
- **AWS SDK for Java — SES** — integração com o serviço de e-mail da AWS.
- **Amazon Simple Email Service (SES)** — envio de e-mails.
- **Maven** — gerenciamento de dependências e build.
- **Lombok** — redução de código repetitivo.

## Estrutura do projeto

```text
email-platform/
├── event-microservice/
│   ├── src/
│   └── pom.xml
├── email-microservice/
│   ├── src/
│   └── pom.xml
├── .gitignore
└── README.md
```

A estrutura acima é ilustrativa. Os nomes das pastas devem corresponder aos diretórios reais do repositório.

## Instalação e execução

### Pré-requisitos

- JDK compatível com a versão Java utilizada nos projetos.
- Maven.
- Conta AWS com acesso ao Amazon SES.
- Credenciais AWS válidas.
- Permissões IAM adequadas para envio de e-mails.

### 1. Clonar o repositório

```bash
git clone https://github.com/ghsantos-software/email-platform.git
cd email-platform
```

### 2. Configurar o Email Microservice

Configure suas credenciais AWS utilizando a AWS CLI, um perfil de credenciais ou variáveis de ambiente.

Verifique se o remetente está verificado no Amazon SES e se a conta possui autorização para enviar mensagens aos destinatários pretendidos.

**Importante:** nunca versione chaves de acesso, senhas ou tokens de sessão da AWS no GitHub.

### 3. Executar o Email Microservice

Abra um terminal no diretório do microsserviço de e-mail e execute:

```bash
mvn spring-boot:run
```

O serviço deve estar configurado para iniciar na porta `8090`.

### 4. Executar o Event Microservice

Abra outro terminal no diretório do microsserviço de eventos e execute:

```bash
mvn spring-boot:run
```

A aplicação estará disponível em:

`http://localhost:8080`

Verifique se a URL configurada no cliente HTTP do Event Microservice corresponde ao endereço do Email Microservice.

## Endpoints da API

### Event Microservice

URL base: `http://localhost:8080`

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/events` | Consulta todos os eventos cadastrados. |
| GET | `/events/upcoming` | Consulta os eventos futuros. |
| POST | `/events` | Cadastra um novo evento. |
| POST | `/events/{id}` | Registra um participante em um evento. |

### Cadastrar um evento

**POST** `/events`

Exemplo de requisição:

```json
{
  "maxParticipants": 20,
  "registeredParticipants": 0,
  "date": "28/11/2026",
  "title": "Java Developer Meetup",
  "description": "Evento de tecnologia para desenvolvedores Java."
}
```

### Registrar um participante

**POST** `/events/{id}`

Exemplo de requisição:

```json
{
  "participantEmail": "participante@exemplo.com"
}
```

A aplicação verifica a capacidade disponível do evento antes de registrar o participante.

### Email Microservice

URL base: `http://localhost:8090`

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/api/email/send` | Solicita o envio de um e-mail por meio do Amazon SES. |

### Enviar um e-mail

**POST** `/api/email/send`

Exemplo de requisição:

```json
{
  "to": "destinatario@exemplo.com",
  "subject": "Inscrição em evento",
  "body": "Sua inscrição foi recebida."
}
```

O envio depende de credenciais AWS válidas, permissões adequadas e configuração correta do Amazon SES.

## Banco de dados

O Event Microservice utiliza o **H2 Database**, um banco de dados relacional escrito em Java, adequado para desenvolvimento e ambientes de aprendizado.

Ele é utilizado para persistir os dados relacionados aos eventos e às operações implementadas pela aplicação.

## Tratamento de erros

O Email Microservice utiliza a exceção personalizada `EmailServiceException` para representar falhas durante o envio de e-mails.

Entre os possíveis problemas de integração estão:

- Credenciais AWS inválidas.
- Permissões IAM insuficientes.
- Remetentes ou destinatários não verificados, conforme as regras do SES.
- Mensagens rejeitadas pelo serviço de e-mail.

## Objetivos de aprendizado

Este projeto permite praticar conceitos importantes do desenvolvimento backend:

- Arquitetura de microsserviços com Java e Spring Boot.
- Separação de responsabilidades entre aplicações independentes.
- Comunicação entre serviços por meio de APIs REST.
- Desenvolvimento de endpoints e contratos HTTP.
- Gerenciamento de eventos e inscrições de participantes.
- Persistência de dados com Spring Data JPA e H2.
- Integração de aplicações Java com serviços da AWS.
- Gerenciamento de dependências com Maven.
- Tratamento de exceções e diagnóstico de falhas.

## Melhorias futuras

- Implementar testes unitários e de integração.
- Documentar as APIs com OpenAPI/Swagger.
- Containerizar os serviços com Docker.
- Criar um ambiente local com Docker Compose.
- Implementar pipelines de CI/CD.
- Melhorar logs, métricas e monitoramento.
- Aprimorar o tratamento de falhas na comunicação entre os serviços.

## Status do projeto

Projeto de estudo em desenvolvimento, com foco em Java, Spring Boot, comunicação entre microsserviços e integração com serviços de nuvem da AWS.
