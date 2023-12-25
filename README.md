# Projeto de Criação de Pautas para Votação de Associados

Bem-vindo ao repositório do projeto de criação de pautas para votação de associados! Este projeto oferece uma solução eficiente para a gestão de pautas e votações em associações, proporcionando transparência e facilidade no processo de tomada de decisões.

## Documentação da API

A documentação completa da API pode ser encontrada no Swagger. Consulte o link abaixo para obter detalhes sobre os endpoints disponíveis, parâmetros necessários e exemplos de requisições e respostas:

[Swagger - Documentação da API](http://localhost:8080/swagger-ui/index.html)

## Inicializando a Aplicação com Spring Boot e Java 17 usando Gradle

Certifique-se de ter o Java 17 e o Gradle instalados em seu sistema antes de prosseguir.

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
   
A aplicação estará disponível em http://localhost:8080.

## Utilizando Docker para Executar a Aplicação
Se preferir, você pode executar a aplicação dentro de um contêiner Docker. Certifique-se de ter o Docker instalado em seu sistema e, em seguida, siga as instruções abaixo:

1. **Construa a imagem Docker:**

   ```bash
    docker build -t sicredi-votes .
    ```
   
2. **Execute a imagem em um contêiner:**

   ```bash
    docker run -p 8080:8080 sicredi-votes
    ```
   
A aplicação estará acessível em http://localhost:8080.

Certifique-se de configurar corretamente os parâmetros de ambiente e o banco de dados conforme necessário, consultando a documentação para obter detalhes adicionais.

Agradecemos por escolher nosso projeto! Se tiver dúvidas ou sugestões, sinta-se à vontade para abrir uma issue ou contribuir para o desenvolvimento.