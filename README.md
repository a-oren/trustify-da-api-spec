# Trustify Dependency Analytics API Specification
Trustify Dependency Analytics API Specification

## OpenAPI v5

Find the OpenAPI definition for V5 under the [/api](./api) folder

## Generated data model

The Java and Javascript data models are generated at build time using the
[openapi-generator-maven-plugin](https://github.com/OpenAPITools/openapi-generator/tree/master/modules/openapi-generator-maven-plugin)

Run `mvn package` to generate it.

## Use the Java generated data model

The packages are published to the GitHub maven repository. Make sure to add it to your settings or to your project configuration.

```xml
<dependency>
  <groupId>io.github.guacsec</groupId>
  <artifactId>trustify-da-api-model</artifactId>
  <version>2.0.11-SNAPSHOT</version>
</dependency>
```

## Use the Javascript data model

Add it to your project as follows:

```bash
npm install @guacsec/trustify-da-api-model@2.0.11-SNAPSHOT
```
