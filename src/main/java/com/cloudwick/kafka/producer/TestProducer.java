package com.cloudwick.kafka.producer;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class TestProducer {
	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		if(args.length != 2)
		{
			System.out.println("Usage: java -cp KafkaProducerSample-0.0.1-SNAPSHOT-jar-with-dependencies.jar <num_of_events> <event_interval_in_ms>");
			System.exit(1);
		}

		long events = Long.parseLong(args[0]);
		Integer interval = Integer.parseInt(args[1]);
		BufferedReader br = new BufferedReader(new FileReader("Sample_Data_generator.csv"));
		
		Random rdm = new Random();
		Properties props = new Properties();
		props.put("metadata.broker.list", "172.16.6.92:6667");
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("request.required.acks", "1");
		ProducerConfig config = new ProducerConfig(props);
		Producer<String, String> producer = new Producer<String, String>(config);
		String line = "";
		String cvsSplitBy = ",";
		int i=0;
		ArrayList<List<String>> userdata = new ArrayList<>();
		try{
			while ((line = br.readLine()) != null) {
				String[] temp = line.split(cvsSplitBy);
				userdata.add(Arrays.asList(temp));
				i++;
			}
			int size = userdata.size();

			for (long nEvents = 0; nEvents < events; nEvents++) 
			{
				String msg = 
						userdata.get(rdm.nextInt(size)).get(0).toString().trim()+","+
						userdata.get(rdm.nextInt(size)).get(1).toString().trim()+","+
						userdata.get(rdm.nextInt(size)).get(2).toString().trim()+","+
						userdata.get(rdm.nextInt(size)).get(3).toString().trim()+","+
						userdata.get(rdm.nextInt(size)).get(4).toString().trim()+","+
						userdata.get(rdm.nextInt(size)).get(5).toString().replace("$", " ").trim()+","+
						userdata.get(rdm.nextInt(size)).get(6).toString().trim();

				KeyedMessage<String, String> data = new KeyedMessage<String, String>("user_data", new Date().getTime()+"", msg);
				System.out.println(msg);
				producer.send(data);
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