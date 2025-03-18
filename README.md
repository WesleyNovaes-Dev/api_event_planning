# Event Planning

Event Planning é uma API projetada para gerenciar o planejamento de eventos, permitindo a criação, edição e visualização de eventos, sejam eles presenciais ou online por meio de videoconferência.

## 📖 Visão Geral do Modelo de Negócio

Nosso projeto busca solucionar [problema ou necessidade] através de [solução proposta]. O fluxo de funcionamento é representado pela imagem abaixo:



## 🏗 Arquitetura e Entidades do Java

A aplicação utiliza Java para a camada de backend, com as seguintes entidades principais:

![Entidades Java](https://drive.usercontent.google.com/download?id=1zPw6xxa2oM-lNMdGNYq5RWlaYzPeuWwU&export=view&authuser=0.png)

Principais entidades e suas responsabilidades:
- **Event**: Representa um evento com ID, descrição, localização e tipo.
- **Coupon**: Gerenciamento de cupons associados a eventos.
- **Address**: Endereço vinculado aos eventos.
- **Admin**: Administração dos eventos.

## 🛢 Banco de Dados

A modelagem do banco de dados segue a estrutura abaixo:

![Banco de Dados](https://drive.usercontent.google.com/download?id=1VKvOnG00glDwscv1cR9wgBT9qZoxPREq&export=view&authuser=0.png)

Tecnologias utilizadas:
- **Banco de dados**: PostgreSQL & H2 (para testes)
- **ORM**: Hibernate/JPA
- **Estrutura**: Relacionamento das tabelas, chaves primárias e estrangeiras

## ☁ Solução na AWS

O projeto está implantado na AWS com a seguinte arquitetura:

![Solução AWS](https://drive.usercontent.google.com/download?id=1OrInN9tUsUo7CijiIhaZxmRQ0qS2XAEQ&export=view&authuser=0.png)

Principais serviços utilizados:
- **EC2** para hospedagem da API
- **RDS** para gerenciamento do banco de dados
- **S3** para armazenamento de arquivos
- **Internet Gateway** para comunicação com a internet

## 🚀 Como Executar o Projeto

### Pré-requisitos

Certifique-se de ter os seguintes softwares instalados:
- Java 17+
- Maven
- Docker (se aplicável)
- Banco de dados configurado

### Passos para execução

1. Clone o repositório:
   ```sh
   git clone https://github.com/seu-usuario/event-planning.git
   ```
2. Acesse a pasta do projeto:
   ```sh
   cd event-planning
   ```
3. Configure o banco de dados (PostgreSQL ou H2 para testes).
4. Execute o projeto com Maven:
   ```sh
   mvn spring-boot:run
   ```
5. Acesse a API pelo navegador ou Postman: `http://localhost:9190`

## 📫 Contribuição

Contribuições são bem-vindas! Fique à vontade.

## 📞 Informações para Contato

- **WhatsApp**: +55 11 96583-5656
- **E-mail**: wesleyzanon17@gmail.com
- **LinkedIn**: [Wesley Novaes](https://www.linkedin.com/in/wesley-novaes-641577193/)

---
Projeto desenvolvido para melhorar meu portfólio.

---
