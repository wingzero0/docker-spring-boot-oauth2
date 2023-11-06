#!/bin/bash
cp /etc/hosts /tmp/hosts
echo "127.0.0.1 postgresqldb" >> /tmp/hosts
sudo cp /tmp/hosts /etc/hosts
docker compose -f .gitpod.docker-compose.yaml up