[![Build Status](https://circleci.com/gh/tornimo/tornimo-spring-reporter.svg?style=shield)](https://circleci.com/gh/tornimo/tornimo-spring-reporter)

# tornimo-spring-reporter
Spring-Boot 2 metric reporter for Tornimo.io based on [micrometer.io](micrometer.io). 

## Start Using Tornimo.io
1) If you haven't done so, signup for a free account at [tornimo.io](https://tornimo.io/start-free-trial/)
2) After a few minutes, you will receive a link to your dashboard, url for data ingestion and a token. Use the token and the URL in the configuration step.
3) Add tornimo-spring-reporter to your dependencies.

## Configuration
``` 
management.metrics.export.tornimo.token=b55670fd-ea50-40e4-9cf3-82d2ed46c629  # 1
management.metrics.export.tornimo.host=put.b55670fd.tornimo.io                # 2
management.metrics.export.tornimo.app=example-app                             # 3
``` 
1) Token provided during registration at [tornimo.io](tornimo.io)
2) URL for metrics ingestion, provided during registration
3) Application name that will be added to the metric path

## Maven Dependency

```
<dependency>
    <groupId>io.tornimo</groupId>
    <artifactId>tornimo-spring-reporter/artifactId>
    <version>0.1.0</version>
</dependency>
```

## Gradle Dependency
```
compile group: 'io.tornimo', name: 'tornimo-spring-reporter', version: '0.1.0'
```
