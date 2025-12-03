# 🛡️ Sistema de Microsserviços: Autenticação, Usuários e Notificações

Este projeto implementa uma arquitetura de microsserviços usando **Java** e **Spring Boot**. O sistema é dividido em três módulos principais que se comunicam via **API REST** e **Mensageria Assíncrona** (RabbitMQ), garantindo desacoplamento e escalabilidade.

---

## ⚙️ 1. Tecnologias e Arquitetura

| Categoria | Tecnologia | Uso no Projeto |
| :--- | :--- | :--- |
| **Linguagem/Framework** | Java 17+ / Spring Boot 3+ | Desenvolvimento principal de todos os módulos. |
| **Segurança** | Spring Security / JWT | Autenticação, *hashing* de senha (BCrypt) e autorização via Token. |
| **Mensageria** | RabbitMQ | Comunicação assíncrona entre `users` (Produtor) e `email` (Consumidor). |
| **Persistência** | Spring Data JPA / Hibernate | Mapeamento Objeto-Relacional (ORM) para PostgreSQL. |
| **Containerização** | Docker / Docker Compose | Isolamento, portabilidade e facilidade no *setup* do ambiente completo. |

---

## 🏗️ 2. Estrutura dos Módulos

O projeto é organizado em três microsserviços independentes:

### 1. 🔑 Módulo `auth` (Autenticação)
* **Função:** API REST dedicada à segurança.
* **Responsabilidades:** Validação de credenciais, descriptografia BCrypt, e geração/assinatura de **Tokens JWT**.
* **Rotas:** `/auth/login`

### 2. 👤 Módulo `users` (Gerenciamento de Usuários e Produtor)
* **Função:** API REST para operações CRUD e disparo de eventos.
* **Responsabilidades:** Gerenciar a criação (`POST /users`), listagem (`GET /users`) e busca de usuários. Atua como **Produtor RabbitMQ** ao disparar notificações em massa (`POST /users/notify-all`).

### 3. 📧 Módulo `email` (Serviço de E-mail Assíncrono)
* **Função:** Serviço rodando em segundo plano para envio de e-mails.
* **Responsabilidades:** Atua como **Consumidor RabbitMQ**, ouvindo a fila **`email-queue`** e utilizando o **JavaMailSender** para enviar a mensagem via SMTP.

---

## 🚀 3. Configuração e Execução

A maneira mais eficiente para iniciar todos os serviços (APIs, RabbitMQ e Banco de Dados) é usando o Docker Compose.

### Pré-requisitos
* **Docker** e **Docker Compose** instalados.

### Passos de Inicialização

1.  **Construir e Iniciar:** Na raiz do projeto, use o comando abaixo. Ele construirá as imagens dos seus microsserviços e subirá todas as dependências (RabbitMQ, DB, etc.).

    ```bash
    docker-compose up --build -d 
    ```

2.  **Verificar Status:** Confirme que todos os contêineres estão ativos:

    ```bash
    docker-compose ps
    ```

3.  **Encerrar Ambiente:** Para parar e remover todos os contêineres:

    ```bash
    docker-compose down
    ```

---

## 🗺️ 4. Endpoints da API

Para interagir com o sistema, utilize os seguintes *endpoints*:

| Módulo | Método | URL | Descrição | Requer JWT |
| :--- | :--- | :--- | :--- | :--- |
| **`auth`** | `POST` | `/auth/login` | Autentica o usuário e retorna o Token JWT. | ❌ Não |
| **`users`** | `POST` | `/users` | Cria um novo usuário no sistema. | ❌ Não |
| **`users`** | `GET` | `/users` | Lista todos os usuários cadastrados. | ✅ Sim |
| **`users`** | `POST` | `/users/notify-all` | Dispara notificações em massa, enviando mensagens para a fila. | ✅ Sim |

### Estrutura da Requisição de Login

```json
{
  "email": "usuario@exemplo.com",
  "password": "sua_senha_secreta"
} 