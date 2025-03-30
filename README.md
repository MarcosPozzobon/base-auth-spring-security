# Base Auth Spring Security
* Este é um projeto de exemplo de Autenticação e Autorização utilizando o Spring Security. Ele serve como base para sistemas que requerem autenticação e controle de acesso a recursos, com suporte a JWT (JSON Web Tokens) para segurança e gerenciamento de sessão.

# Tecnologias Utilizadas
* Java 17: Linguagem de programação.
* Spring Boot: Framework para desenvolvimento de aplicações Java.
* Spring Security: Configuração de segurança.
* JWT: Mecanismo para autenticação sem estado.
* PostgreSQL: Banco de dados relacional.
* Docker: Contêineres para simplificar o ambiente de desenvolvimento e produção.
* Maven: Gerenciador de dependências e build para projetos Java.
* Lombok: Para simplificar o desenvolvimento e evitar boilerplate.

# Pré-requisitos
Antes de começar, certifique-se de ter o seguinte instalado na sua máquina:
* Java 17 ou superior
* Docker
* Maven

# Para rodar o projeto, siga os seguintes passos:
* git clone https://github.com/MarcosPozzobon/base-auth-spring-security.git
* cd base-auth-spring-security

# Suba os containers com Docker Compose:
docker-compose up -d --build

# Endpoints Principais
* POST /auth/login: Endpoint para login (gera o JWT).
* GET /protected-resource: Recurso protegido que requer o JWT no cabeçalho da requisição. (Authorization Bearer)

# Exemplo de chamada
curl -X POST "http://localhost:9000/api/v1/auth/login" -H "Content-Type: application/json" -d '{"login": "admin","password": "root"}'

