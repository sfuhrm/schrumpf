#! /bin/bash

# where this script is located
SCRIPTROOT=$(dirname $0)
JAR=$SCRIPTROOT/../lib/Schrumpf-*.jar
CP=.:$JAR

echo >/dev/null 1>&2 java $JAVA_OPTS  -jar $JAR "$@"
java $JAVA_OPTS -jar $JAR "$@"
