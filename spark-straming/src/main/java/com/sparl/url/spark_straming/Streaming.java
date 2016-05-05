package com.sparl.url.spark_straming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Streaming extends Thread {
	public void run()
	{
		String record="";
		try {
			
			// Sets the authenticator that will be used by the networking code
		    // when a proxy or an HTTP server asks for authentication.
			//Authenticator.setDefault(new CustomAuthenticator());
			String url1="http://dev.markitondemand.com/MODApis/Api/v2/Quote/jsonp?symbol=AAPL&callback=myFunction"
;			URL url = new URL(url1);
			
			// read text returned by server
		    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		    
		    String line;
		    while ((line = in.readLine()) != null) {
		    	record+=line;
		    	
		    }
		    System.out.println(record);
		    in.close();
		    
		}
		catch (MalformedURLException e) {
			System.out.println("Malformed URL: " + e.getMessage());
		}
		catch (IOException e) {
			System.out.println("I/O Error: " + e.getMessage());
		}
	
		
	}

}
