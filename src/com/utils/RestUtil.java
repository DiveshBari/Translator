package com.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * This class will be used to call the third party API's
 */
public class RestUtil {
	
	/**
	 * Do Get call
	 */	
	public HashMap<String, String> doGetCall(String strUrl, HashMap<String, String> headers)
	{
		HttpRequest request = (HttpRequest) HttpRequest.newBuilder().uri(URI.create(strUrl));
				
		// Add headers
		if (headers!=null && !headers.isEmpty()) {
			// Loop on headers
		}
		
				/*
				.header("Accept-Encoding", "application/gzip")
				.header("X-RapidAPI-Key", "b10ddfcdd1msh8e68be4ab8ea1a9p15fd6cjsnfaad95ca0340")
				.header("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
				*/
		
		try {
			HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println(response.body());
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
