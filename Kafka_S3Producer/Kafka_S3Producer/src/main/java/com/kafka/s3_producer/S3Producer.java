package com.kafka.s3_producer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*; 

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.aws.s3_dataread.S3DataRead;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
public class S3Producer {
	private static String bucketName = "kafka-input"; 
	private static String key        = "input1/input11";  
	private static String access_key_id="AKIAJKYFLU3Z4Z54EC6A";
	private static String secret_access_key="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";
    public static void main(String[] args) throws IOException {
        String modified_key="";      
        String topic_name="test";
        // For configuring the aws credentials
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(access_key_id, secret_access_key);
		AmazonS3 s3Client = new AmazonS3Client(awsCreds);
		 //AmazonS3 s3Client = new AmazonS3Client(new ProfileCredentialsProvider());
		
		// Reading the S3 Data folders
		S3DataRead s3=new S3DataRead();
		S3Producer s3_producer = new S3Producer();
		List<String> sub_folders=s3.listKeysInDirectory(s3Client,bucketName,key);
		System.out.println("############################## Sub folders"+sub_folders);
		
		for(int ind_folder=0;ind_folder<sub_folders.size();ind_folder++)
		{
			
			modified_key=sub_folders.get(ind_folder)==null ? key :sub_folders.get(ind_folder) ;	
			System.out.println(modified_key);
			
			ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
			.withBucketName(bucketName)
			.withPrefix(modified_key);
			ObjectListing objectListing;
			do {
				objectListing = s3Client.listObjects(listObjectsRequest);
				for (S3ObjectSummary objectSummary : 
					objectListing.getObjectSummaries()) {
					if(objectSummary.getSize()!=0){
						S3Object s3_object=s3.read_s3Data(s3Client,bucketName,objectSummary.getKey());	
						InputStream s3_input=s3_object.getObjectContent();
						s3_producer.send_to_kafka_producer(s3_input, topic_name);
					}					
				}
				listObjectsRequest.setMarker(objectListing.getNextMarker());
			} while (objectListing.isTruncated());		
			
		}		
		
    }
    public void send_to_kafka_producer(InputStream s3_input,String topic_name) throws IOException{
    	
    	 Properties props = new Properties();
         props.put("metadata.broker.list", "localhost:9092");
         props.put("serializer.class", "kafka.serializer.StringEncoder");
         props.put("partitioner.class", "com.kafka.s3_producer.SimplePartitioner");
         props.put("request.required.acks", "1");
  
         ProducerConfig config = new ProducerConfig(props);
  
         Producer<String, String> producer = new Producer<String, String>(config); 
    	
    	BufferedReader reader = new BufferedReader(new InputStreamReader(s3_input));
        while (true) {
            String line = reader.readLine();
            KeyedMessage<String, String> data = new KeyedMessage<String, String>(topic_name, line);            
            if (line == null) break;
            producer.send(data);
        }
        producer.close();
        
    }
}