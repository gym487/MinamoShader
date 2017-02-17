#!/bin/bash
javac  ./src/* -d ./bin/
jar cvf ./Minamo.jar ./bin/*
