package com.aws.s3_dataread;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;


public class S3DataRead {
	
	public S3Object read_s3Data(AmazonS3 s3Client,String bucketName, String key) throws IOException {       
		S3Object objectPortion = null;
        try {
            // Get the object file in s3 bucket sub folders
            S3Object s3object = s3Client.getObject(new GetObjectRequest(bucketName, key));
            
            GetObjectRequest rangeObjectRequest = new GetObjectRequest(bucketName, key);
            //rangeObjectRequest.setRange(0, 10);
            objectPortion = s3Client.getObject(rangeObjectRequest);
            //Placing the object 
            
            
        } catch (AmazonServiceException ase) {
            ase.printStackTrace();
        } catch (AmazonClientException ace) {            
            System.out.println("Error Message: " + ace.getMessage());
        } 
        return objectPortion;
    }    
    
    public List<String> listKeysInDirectory(AmazonS3 client,String bucketName, String prefix) {
        String delimiter = "/";
        if (!prefix.endsWith(delimiter)) {
            prefix += delimiter;
        }
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                .withBucketName(bucketName).withPrefix(prefix)
                .withDelimiter(delimiter);
        ObjectListing objects = client.listObjects(listObjectsRequest);
        return objects.getCommonPrefixes();
    }
}