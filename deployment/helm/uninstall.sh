#!/bin/bash

echo "Uninstalling yatt via helm ..."
helm uninstall yatt

echo "Deleting persistent volume claims ..."
kubectl delete persistentvolumeclaim/data-yatt-axon-server-se-0
kubectl delete persistentvolumeclaim/data-yatt-postgresql-0 
kubectl delete persistentvolumeclaim/events-yatt-axon-server-se-0
