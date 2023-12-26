# Projeto de Criação de Pautas para Votação de Associados

Bem-vindo ao repositório do projeto de criação de pautas para votação de associados! Este projeto oferece uma solução eficiente para a gestão de pautas e votações em associações, proporcionando transparência e facilidade no processo de tomada de decisões.

## Documentação da API

A documentação completa da API pode ser encontrada no Swagger. Consulte o link abaixo para obter detalhes sobre os endpoints disponíveis, parâmetros necessários e exemplos de requisições e respostas (necessário inicitalizar a aplicação previamente):


[Swagger - Documentação da API](http://localhost:8080/sicredi-votes/swagger-ui/index.html) (Sistema Sicredi Votes necessita estar inicializado)

# Inicialização da Aplicação Java 17 com Gradle 8.5 ou Docker

Este guia fornece instruções sobre como subir a aplicação Java 17 utilizando Gradle 8.5. Certifique-se de seguir todos os passos corretamente e de atender aos requisitos prévios antes de iniciar.

## Requisitos Prévios

1. **Java 17**: Certifique-se de ter o Java 17 instalado em seu sistema.
2. **Gradle 8.5**: Instale o Gradle 8.5 no seu ambiente.
3. **MongoDB 6.0**: Instale e inicialize o MongoDB 6.0 no seu ambiente.
4. **Kafka 5.1.2**: Instale e inicialize o Kafka 5.1.2 no seu ambiente.
5. **Zookeeper 5.1.2**: Instale e inicialize o Zookeeper 5.1.2 no seu ambiente.

## Configuração de Variáveis de Ambiente

Antes de iniciar a aplicação, é necessário configurar algumas variáveis de ambiente. Certifique-se de ajustar os valores de acordo com o seu ambiente.

- `SICREDI_VOTES_DATABASE_URL`: URL de conexão com o banco de dados MongoDB (ex: mongodb://localhost:27017/sicred-votes).
- `SICREDI_VOTES_KAFKA_HOST`: Endereço do servidor Kafka (ex: localhost:9092).
- `SICREDI_VOTES_KAFKA_TOPIC_NAME`: Nome do tópico Kafka para mensagens relacionadas a votos.
- `SICREDI_VOTES_KAFKA_TOPIC_GROUP_ID`: Identificação do grupo de consumidores para o tópico Kafka.
- `SECONDS_TO_VOTING_FINISH_DEFAULT`: Tempo padrão (em segundos) para o término de uma votação.

### Passos para Inicialização

1. **Clone o repositório para o seu ambiente local:**

   ```bash
   git clone https://github.com/LucasCascao/sicredi-votes.git
   cd sicredi-votes
    ```
2. **Execute o seguinte comando para construir o projeto:**

   ```bash
    ./gradlew build
   ```
   
3. **Inicie a aplicação Spring Boot:**

   ```bash
    ./gradlew bootRun
   ```
   
A aplicação estará disponível em http://localhost:8080/sicredi-votes.

## Utilizando Docker para Executar a Aplicação
Se preferir, você pode executar a aplicação dentro de um contêiner Docker. Certifique-se de ter o Docker instalado em seu sistema e, em seguida, siga as instruções abaixo:

1. **Construa a imagem Docker:**

   ```bash
    docker build -t sicredi-votes-v1:1.0.0 .
    ```
   
2. **Execute a imagem em um contêiner junto com os outros sistemas necessários:**

   ```bash
    docker-compose up -d
    ```
   
A aplicação estará acessível em http://localhost:8080/sicredi-votes.

Certifique-se de configurar corretamente os parâmetros de ambiente e o banco de dados conforme necessário, consultando a documentação para obter detalhes adicionais.

Agradecemos por escolher nosso projeto! Se tiver dúvidas ou sugestões, sinta-se à vontade para abrir uma issue ou contribuir para o desenvolvimento.
