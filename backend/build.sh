#!/bin/bash

echo "Building Service - User API ..."
./gradlew :services:user:user-api:build
echo "Building Service - User Service ..."
./gradlew :services:user:user-service:build

echo "Building Service - Company API ..."
./gradlew :services:company:company-api:build
echo "Building Service - Company Service ..."
./gradlew :services:company:company-service:build

echo "Building Service - Project API ..."
./gradlew :services:project:project-api:build
echo "Building Service - Project Service ..."
./gradlew :services:project:project-service:build

echo "Building Data Importer ..."
./gradlew :data-import:initial:build

echo "Building API Common ..."
./gradlew :apis:common:build
echo "Building API GraphQL ..."
./gradlew :apis:graphql:build
echo "Building API gRPC"
./gradlew :apis:grpc:build
echo "Building API REST ..."
./gradlew :apis:rest:build
echo "Building API RSocket ..."
./gradlew :apis:rsocket:build

echo "Building Demo Client GRPC ..."
./gradlew :clients:grpc:build
echo "Building Demo Client RSocket ..."
./gradlew :clients:rsocket:build
