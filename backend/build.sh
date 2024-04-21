#!/bin/bash

echo "Building Service - User API ..."
./gradlew :services:user:api:build
echo "Building Service - User Application ..."
./gradlew :services:user:application:build

echo "Building Service - Company API ..."
./gradlew :services:company:api:build
echo "Building Service - Company Application ..."
./gradlew :services:company:application:build

echo "Building Service - Project API ..."
./gradlew :services:project:api:build
echo "Building Service - Project Application ..."
./gradlew :services:project:application:build

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
