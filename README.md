# Projeto de automação de testes da API barrigarest

## Descrição

Este é um projeto de estudo sobre automação de testes de APIs Rest usando as ferramentas Rest Assured e JUnit5 com Java.

## Estrutura do Projeto

```plaintext
├── .github
│   └── workflows
│       └── gha-ci.yml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── br
│   │   │       └── ce
│   │   │           └── wcaquino
│   │   │               └── rest
│   │   │                   └── core
│   │   │                       └── Constantes.java
│   │   └── resources                   
│   └── test
│       ├── java
│       │   └── br
│       │        └── ce
│       │            └── wcaquino
│       │                └── rest
│       │                   ├── core
│       │                   │   └── BaseTest.java
│       │                   └── tests
│       │                       └── BarrigaTest.java
│       └── resources
│           └── allure.properties
├── .gitignore
├── pom.xml
└── README.md
```

## Pré-requisitos

* [Java JDK 22+](https://www.oracle.com/pt/java/technologies/javase/jdk11-archive-downloads.html)

* [Apache Maven 3.6+](https://maven.apache.org/docs/3.6.0/release-notes.html)

* [JUnit Jupiter API 5.11.0+](https://testng.org/)

* [REST Assured 5.5.0+](https://www.selenium.dev/)

## Configuração do Ambiente

1 Clone o repositório:
```
git clone https://github.com/leonardomelgarejo/*.git
```

2 Instale as dependências do Maven:
```
mvn clean install
```
## Estrutura dos Testes

* TO DO

## Executando os Testes
```
mvn clean test
```

## Relatório de Testes

* O relatório de testes é gerado pelo framework Allure Reports, de duas formas:
    * Localmente, executando os comando abaixo:
      * mvn allure:serve : Abrirá o relatório HTML no navegador
      * mvn allure:report : Irá gerar o HTML na pasta target/site/allure-maven-plugin
    * O local onde o arquivos arquivos referência para o relatório são definidos no src/test/resources/allure.properties
    * Remotamente, acessando o link abaixo:
      * https://leonardomelgarejo.github.io/
    
