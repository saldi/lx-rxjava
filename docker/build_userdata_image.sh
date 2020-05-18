#!/usr/bin/env bash
export JAVA_HOME=c:/javaplatform/jdk14
cd ../codebase/users-data
mvn clean package
docker build -t lxreact/users:api .