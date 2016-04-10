#!/usr/bin/env bash
sbt assembly
~/Programs/spark/bin/spark-submit --class lambda.speed.StreamManager --master spark://geerdink:7077 --deploy-mode cluster fast-data.jar
~/Programs/spark/bin/spark-submit --class lambda.batch.StreamManager --master spark://geerdink:7077 --deploy-mode cluster fast-data.jar
