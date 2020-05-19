#!/usr/bin/env bash
export JAVA_HOME=c:/javaplatform/jdk14
./build_h2_image.sh
./build_userdata_image.sh
./build_locations_image.sh