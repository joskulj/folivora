#!/bin/sh

export FOLIVORA_HOME=.
export CLASSPATH=$FOLIVORA_HOME/deploy/folivora.jar
export CLASSPATH=$CLASSPATH:$FOLIVORA_HOME/lib/log4j-1.2.17.jar
export MAIN_CLASS=de.jochenskulj.taskmanager.Application

java -classpath $CLASSPATH $MAIN_CLASS

