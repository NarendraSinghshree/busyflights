package com.travix.medusa.busyflights.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;

@Service("GetServiceResponse")
public class GetServiceResponse {

	public static final Logger logger = LoggerFactory.getLogger(GetServiceResponse.class);

	/**
	 * Sending Request to fetch CrazyAirResponse from airline
	 * 
	 * @param crazyReq
	 * @param crazyUrl
	 * @return
	 * @throws Exception
	 */
	public String SendGetCrazy(CrazyAirRequest crazyReq, String crazyUrl) {
		String crazyHTTP = "http"; // https
		String crazyPort = "80";
		URL objUrl;
		BufferedReader in;
		StringBuffer response = null;
		try {
			objUrl = new URL(crazyUrl);
			InetSocketAddress proxyinet = new InetSocketAddress(crazyHTTP, Integer.parseInt(crazyPort));
			Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyinet);
			HttpURLConnection con = (HttpURLConnection) objUrl.openConnection(proxy);
			con.setRequestMethod("GET");
			int responsecode = con.getResponseCode();
			logger.info("Fetching CrzyAirResponse for CrazyAirRequest{} ::", crazyReq);
			logger.info("Fetching CrzyAirResponse Response code ::", responsecode);

			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 if(null != response) {
			 return response.toString();
		 } else {
			 return crazyFlightJson;
		 }

	}

	/**
	 * Sending Request to fetch ToughJetResponse from airline
	 * 
	 * @param toughReq
	 * @param toughUrl
	 * @return
	 * @throws Exception
	 */
	public String SendGetTough(ToughJetRequest toughReq, String toughUrl) {
		String crazyHTTP = "http"; // https
		String crazyPort = "82";
		URL objUrl;
		BufferedReader in;
		StringBuffer response = null;
		try {

			objUrl = new URL(toughUrl);
			InetSocketAddress proxyinet = new InetSocketAddress(crazyHTTP, Integer.parseInt(crazyPort));
			Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyinet);
			HttpURLConnection con = (HttpURLConnection) objUrl.openConnection(proxy);
			con.setRequestMethod("GET");
			int responsecode = con.getResponseCode();
			logger.info("Fetching CrzyAirResponse for CrazyAirRequest{} ::", toughReq);
			logger.info("Fetching CrzyAirResponse Response code ::", responsecode);

			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(null != response) {
			 return response.toString();
		 } else {
			 return toughFlightJson;
		 }

	}
	
	public String crazyFlightJson =  ""
            + "[{\"airline\": \"Indigo Airlines\",\"price\": \"9865\",\"cabinclass\": \"B\",\"departureAirportCode\": \"LHR\","
            + "\"destinationAirportCode\": \"AMS\",   \"departureDate\": \"2017-11-04\",\"arrivalDate\": \"2017-12-06\"}, "
            + "{\"airline\": \"British Airways\",\"price\": \"7689\",      \"cabinclass\": \"E\",  \"departureAirportCode\": \"LHR\",  "
            + "\"destinationAirportCode\": \"AMS\",   \"departureDate\": \"2017-11-04\",    \"arrivalDate\": \"2017-12-11\"     },    "
            + "{\"airline\": \"Virgin Australia\",  \"price\": \"6895\",      \"cabinclass\": \"E\",  \"departureAirportCode\": \"LHR\",  "
            + "\"destinationAirportCode\": \"AMS\",   \"departureDate\": \"2017-11-08\",    \"arrivalDate\": \"2017-12-14\" } ]";;
	
	public String toughFlightJson =  ""
            + "[{\"carrier\": \"Jet Airways\",\"basePrice\": \"8436\",\"tax\": \"1234\",\"discount\": \"3\",\"departureAirportName\": \"LHR\","
            + "\"arrivalAirportName\": \"AMS\", \"outboundDateTime\": \"2017-10-15\",\"inboundDateTime\": \"2017-12-13\"},      "
            + "{\"carrier\": \"Go Air\",\"basePrice\": \"7652\",\"tax\": \"1067\",\"discount\": \"2\",      \"departureAirportName\": \"LHR\",  "
            + "\"arrivalAirportName\": \"AMS\", \"outboundDateTime\": \"2017-11-16\", \"inboundDateTime\": \"2017-12-12\" },    "
            + "{\"carrier\": \"Virgin America\", \"basePrice\": \"6126\",\"tax\": \"999\",\"discount\": \"4\",  \"departureAirportName\": \"LHR\",  "
            + "\"arrivalAirportName\": \"AMS\", \"outboundDateTime\": \"2017-11-09\", \"inboundDateTime\": \"2017-12-23\" }]";


}
