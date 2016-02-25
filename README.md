# Kafka-Data-Generator
#### This is a sample stock data generator for Kafka
* After you pull this code into your IDE, wait for it to download all the necesasry dependencys and click **Maven Install** to generate the Uber jar with dependencies nested under the **target** folder.
* **Copy** the jar onto you machine and run the following command

   <code>`java -cp KafkaProducerSample-0.0.1-SNAPSHOT-jar-with-dependencies.jar TestProducer 172.16.6.92:6667 stock_topic 100 1000`</code>
   
* In the above command you pass **four** arguments apart from specifying your class file `TestProducer`
  * **_Broker List_**
    * give your kafka broker address with its port
  * **_Topic Name_**
    * you can specify multiple kafka topics seperated by comma and implement your own logic for that topic
  * **_Number of Events_**
    * Total number of events or messages you want to send
      * specify a _**positive number**_ if you want it to stop your generator automatically
      * specify a _**negative number**_ if you want to hard stop it by 'ctrl+c'
  * **_Interval Between Events (ms)_**
    * Number of milliseconds the generator should wait before sending another event

* Important files in the project
  * `/src/main/java/TestProducer.java`
    * This is the main class
  * `/src/main/resources/sampleset.txt`
    * This is the sample stock set used to generate random stock feed
  * `pom.xml`
    * add you dependencies here, if anymore needed
For ease, make a bash script for the above command
