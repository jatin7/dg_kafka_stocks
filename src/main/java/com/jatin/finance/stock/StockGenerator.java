package com.jatin.finance.stock;

import com.jatin.util.kafka.KafkaProducer;
import com.jatin.util.kafka.KafkaProducerImpl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ExecutionException;


//import kafka.javaapi.producer.Producer;


public class StockGenerator {

	//private static Producer<String, String> producer;
	private static KafkaProducer producer;
	public static void main(String[] args) throws InterruptedException, FileNotFoundException, ExecutionException {
		if(args.length != 4)
		{
			System.out.println("Usage: java -cp KafkaProducerSample-0.0.1-SNAPSHOT-jar-with-dependencies.jar  <kafka-broker> <topics_seperated_by_comma> <num_of_events> <event_interval_in_ms>");
			System.exit(1);
		}
		
		//initializing variables
		//192.168.1.66:9092 stock 100 1000
		String brokerList = args[0];
		String topic = args[1];
		long events = Long.parseLong(args[2]);
		Integer interval = Integer.parseInt(args[3]);
		String[] topics = args[1].split(",");

		BufferedReader br = new BufferedReader(new InputStreamReader(StockGenerator.class.getResourceAsStream("/sampleset.txt")));

		producer = new KafkaProducerImpl(topic);

		/* start a producer */
		producer.configure(brokerList, "async");
		producer.start();

		long startTime = System.currentTimeMillis();
		System.out.println("Starting...");
		producer.produce("Starting...");

		String line = "";
		String csvSplitBy = ",";
		Random rdm = new Random();
		ArrayList<List<String>> userdata = new ArrayList<>();
		
		try{
			//loading the data as Arrays of Lists to be able to pick different cells for generating random data from sample set
			while ((line = br.readLine()) != null) {
				String[] temp = line.split(csvSplitBy);
				userdata.add(Arrays.asList(temp));
			}
			//getting the size of the Array to use as random seed so that we wont run into ArrayIndexOutOfBounds Exception
			int size = userdata.size();

			//creating the data and sending it
			for (long nEvents = 1; nEvents != events; nEvents++) 
			{
				String msg = "{"+
						"\"StockSymbol\":"+userdata.get(rdm.nextInt(size)).get(0).toString().trim()+","+
						"\"StockNumber\":"+userdata.get(rdm.nextInt(size)).get(1).toString().trim()+","+
						"\"Mp\":"+userdata.get(rdm.nextInt(size)).get(2).toString().trim()+","+
						"\"Bp\":"+userdata.get(rdm.nextInt(size)).get(3).toString().trim()+","+
						"\"Ap\":"+userdata.get(rdm.nextInt(size)).get(4).toString().trim()+","+
						"\"BQ\":"+userdata.get(rdm.nextInt(size)).get(5).toString().trim()+","+
						"\"Aq\":"+userdata.get(rdm.nextInt(size)).get(6).toString().trim()+","+
						"\"Vol\":"+userdata.get(rdm.nextInt(size)).get(7).toString().trim()+","+
						"\"rowId\":"+userdata.get(rdm.nextInt(size)).get(8).toString().trim()
						+"}";
				
				System.out.println(msg);
				producer.produce(msg);
				Thread.sleep(interval);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		producer.close();
	}
}