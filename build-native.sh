#!/bin/bash
git pull
time mvn clean spring-boot:build-image -P docker-image-native
#time mvn clean spring-boot:build-image install -P docker-image-native
docker run --name spring-boot-example-graal-native --rm -p50800:50800 goafabric/example-fhir-service-native:1.0.0