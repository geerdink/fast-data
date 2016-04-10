#!/usr/bin/env bash
~/Programs/spark/bin/spark-submit --class lambda.speed.StreamManager --master spark://localhost.localdomain:7077 --deploy-mode cluster fast-data_2.10-1.0.jar

~/Programs/spark/bin/spark-submit --class lambda.batch.StreamManager --master spark://localhost.localdomain:7077 --deploy-mode cluster fast-data_2.10-1.0.jar
