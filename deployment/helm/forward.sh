#!/bin/bash

kubectl port-forward service/yatt-axon-server-se-ui 8024:8024 & \
kubectl port-forward service/yatt-axon-server-se 8124:8124 & \
kubectl port-forward service/yatt-rest-api 8080:8080 & \
kubectl port-forward service/yatt-graphql-api 8088:8088 &
kubectl port-forward service/yatt-keycloak 8999:80 &