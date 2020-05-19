#!/usr/bin/env bash
export JAVA_HOME=c:/javaplatform/jdk14
cd ../codebase/locations-data
mvn clean package
docker build -t lxreact/locations:api .