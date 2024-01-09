# Spring Boot Application with OpenAI ChatGPT API Integration

This is a sample Spring Boot application that demonstrates how to integrate OpenAI's ChatGPT API.

## Prerequisites

Before running this application, ensure you have the following:

- Java installed (version 8 or later)
- Maven installed
- An OpenAI API key. If you don't have one, sign up for access on the OpenAI platform.

## Configuration

Replace `<YOUR_API_KEY>` and `<MODEL_NAME>` in the `application.properties` file with your OpenAI API key and the desired model name.

```properties
# src/main/resources/application.properties

openai.api-key=<YOUR_API_KEY>
openai.model-name=<MODEL_NAME>
