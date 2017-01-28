#!/usr/bin/env bash

bin/activator assembly
docker build -t atlantishomeserver/radios_server:$1 .
if [ $2 = "publish" ]; then
    docker tag atlantishomeserver/radios_server:$1 atlantishomeserver/radios_server:latest
    docker push atlantishomeserver/radios_server:$1
    docker push atlantishomeserver/radios_server:latest
fi