# Lanchonete Microserviço de Pagamentos
Este repositório contém o código-fonte de um microserviço desenvolvido em Kotlin e Spring Boot para gerenciamento de pagamentos em uma aplicação de lanchonete. O microserviço é projetado para fornecer funcionalidades eficientes e escaláveis, utilizando diversas tecnologias modernas, incluindo validação, load balancer, Lombok, MySQL, testes unitários, Eureka, e Swagger.


## Tecnologias Utilizadas

- Kotlin: Linguagem de programação moderna e concisa que é totalmente interoperável com o Java.
- Spring Boot: Framework que simplifica o desenvolvimento de aplicativos Java, proporcionando uma configuração rápida e produtiva.
- Validation: Utilizado para garantir a validação dos dados de entrada, melhorando a robustez e a segurança da aplicação.
- Load Balancer: Implementado para distribuir o tráfego entre várias instâncias do microserviço, proporcionando escalabilidade e alta disponibilidade.
- Lombok: Biblioteca que reduz a verbosidade do código Java/Kotlin, facilitando a leitura e manutenção do código.
- MySQL: Banco de dados relacional utilizado para armazenar os dados relacionados aos pagamentos.
- Testes Unitários: Conjunto abrangente de testes para garantir a confiabilidade e a qualidade do código.
- Eureka: Serviço de registro e descoberta que permite a fácil identificação e comunicação entre os microserviços.
- Swagger: Ferramenta para design, construção, documentação e consumo de serviços da Web RESTful.


## Configuração e Execução

### 1 - Configuração do Banco de Dados:
- Configure as propriedades do banco de dados no arquivo application.properties.

### 2 - Execução:
- Execute a aplicação utilizando a classe principal PagamentosApplication.kt.

### 3 - Documentação API (Swagger):
- Acesse a documentação da API Swagger em http://localhost:8080/swagger-ui.html.


