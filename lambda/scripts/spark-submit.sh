#!/usr/bin/env bash
sbt assembly
~/Programs/spark/bin/spark-submit --class lambda.speed.SearchHistory --master spark://geerdink:6066 --deploy-mode cluster fast-data.jar
~/Programs/spark/bin/spark-submit --class lambda.batch.SocialMedia --master spark://geerdink:6066 --deploy-mode cluster fast-data.jar
