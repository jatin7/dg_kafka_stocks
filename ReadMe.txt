Example-Run-Command
-------------------
java -cp KafkaProducerSample-0.0.1-SNAPSHOT-jar-with-dependencies.jar TestProducer 172.16.6.92:6667 stock_data 100 1000

For ease, make a bash script for the above command






Suppose you have a package "com.cloudwick.kafka" under src/main/java, you would then run it as follows
-------------------------------------------------------------------------------------------------------
java -cp KafkaProducerSample-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.cloudwick.kafka.TestProducer 172.16.6.92:6667 stock_data 100 1000