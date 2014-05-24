#!/bin/sh

export FOLIVORA_HOME=/home/joskulj/src/java/eclipse/TaskManager
export CLASSPATH=$FOLIVORA_HOME/deploy/folivora.jar
export MAIN_CLASS=de.jochenskulj.taskmanager.Application

java -classpath $CLASSPATH $MAIN_CLASS

