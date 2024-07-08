#!/bin/bash

should_push=false
build_command=build

for ARG in "$@"; do
    if [[ "$ARG" = "--push" ]]; then
      echo "Pushing images activated ..."
      should_push=true
    fi
    if [[ "$ARG" = "--linux" ]]; then
      echo "Linux build activated ..."
      build_command='buildx build --platform linux/amd64'      
    fi    
done

echo "Building docker image for User Service ..."
docker $build_command ../backend/services/user/user-service --tag larsduelfer842/yatt:user-service

echo "Building docker image for Company Service ..."
docker $build_command ../backend/services/company/company-service --tag larsduelfer842/yatt:company-service

echo "Building docker image for Project Service ..."
docker $build_command ../backend/services/project/project-service --tag larsduelfer842/yatt:project-service

echo "Building docker image for REST API ..."
docker $build_command ../backend/apis/rest --tag larsduelfer842/yatt:rest-api

echo "Building docker image for GraphQL API ..."
docker $build_command ../backend/apis/graphql --tag larsduelfer842/yatt:graphql-api

echo "Building docker image Frontend ..."
docker $build_command ../frontend-new --tag larsduelfer842/yatt:frontend

if $should_push; then
  echo "Pushing images to docker registry ..."
 docker push larsduelfer842/yatt:user-service
 docker push larsduelfer842/yatt:company-service
 docker push larsduelfer842/yatt:project-service
 docker push larsduelfer842/yatt:rest-api
 docker push larsduelfer842/yatt:graphql-api
 docker push larsduelfer842/yatt:frontend
fi