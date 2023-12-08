package com.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.util.HashMap;

/**
 * This class will be used to call the third party API's
 */
public class RestUtil {
	
	/**
	 * Do Get call
	 */	
	public HashMap<String, String> doRestCall(String strHTTPAction, String strUrl, HashMap<String, String> headers, String payload)
	{
		HashMap<String, String> hmResponse = new HashMap<String, String>();
		
		Builder request = HttpRequest.newBuilder().uri(URI.create(strUrl));	
		// Add headers
		if (headers!=null && !headers.isEmpty()) {
			// Loop on headers and add them o the request.
			for(String key: headers.keySet()) {
				request.header(key.toString(), headers.get(key).toString());
			}
		}
		
		// Add the method type
		switch(strHTTPAction) {
		case "GET":
			request.method("GET", HttpRequest.BodyPublishers.noBody());
			break;
		case "POST":
			request.method("POST", HttpRequest.BodyPublishers.ofString(payload));
			break;		
		}

		try {
			HttpResponse<String> response = HttpClient.newHttpClient().send(request.build(), HttpResponse.BodyHandlers.ofString());
			System.out.println(response.body());
			
			// In case of response code=200 return status ok and complete received response.
			if(response.statusCode() == HttpURLConnection.HTTP_OK) {
				hmResponse.put("Status", "OK");
				hmResponse.put("Response", response.body().toString());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			// TODO add error handling
		} catch (InterruptedException e) {
			e.printStackTrace();
			// TODO add error handling
		}
		
		return hmResponse;
	}	
}