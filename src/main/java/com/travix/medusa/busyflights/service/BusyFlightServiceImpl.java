package com.travix.medusa.busyflights.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.httpclient.GetServiceResponse;
import com.travix.medusa.busyflights.utility.FlightSearchUtility;

@Service("BusyFlightService")
public class BusyFlightServiceImpl implements BusyFlightService{
	
	@Autowired
	GetServiceResponse clientService; //Service which will do all data retrieval/manipulation work
	private static String flightData;
	
	private static final String CRAZYAIR = "Crazy Air";
	private static final String TOUGHAIR = "Tough Jet";
	
	/***
	 * Method produce  available flights in json format for Crazy Air and Tough Jet flights
	 * @param busyFlightsRequest
	 * @return Json String.
	 */
	
	@SuppressWarnings("null")
	public String findAllAvailableFlights(BusyFlightsRequest busyFlightsRequest) {
		
		//StringBuilder busyFlightsResponse = null;
		CrazyAirRequest crazyRequest = new CrazyAirRequest();
		crazyRequest.setOrigin(busyFlightsRequest.getOrigin());
		crazyRequest.setDestination(busyFlightsRequest.getDestination());
		crazyRequest.setDepartureDate(busyFlightsRequest.getDepartureDate());
		crazyRequest.setReturnDate(busyFlightsRequest.getReturnDate());
		crazyRequest.setPassengerCount(busyFlightsRequest.getNumberOfPassengers());

		ToughJetRequest toughJetRequest = new ToughJetRequest();
		toughJetRequest.setFrom(busyFlightsRequest.getOrigin());
		toughJetRequest.setTo(busyFlightsRequest.getDestination());
		toughJetRequest.setOutboundDate(busyFlightsRequest.getDepartureDate());
		toughJetRequest.setInboundDate(busyFlightsRequest.getReturnDate());
		toughJetRequest.setNumberOfAdults(busyFlightsRequest.getNumberOfPassengers());
		//JSON response being stored into List
		List<CrazyAirResponse> crazyFlightList = null;
		
		//JSON response being stored into List
		List<ToughJetResponse> toughJetFlightList = null;
						
		try {

			if (null != (clientService.SendGetCrazy(crazyRequest, "crazyUrl"))) {
				crazyFlightList = (List<CrazyAirResponse>) FlightSearchUtility.jSonToObjectCrazyAir(clientService.SendGetCrazy(crazyRequest, "crazyUrl"));
			} else if (null != (clientService.SendGetTough(toughJetRequest, "toughUrl"))) {
				toughJetFlightList = (List<ToughJetResponse>) FlightSearchUtility.jSonToObjectToughJet(clientService.SendGetTough(toughJetRequest, "toughUrl"));
			} else {
							flightData = populateDummyFlights(busyFlightsRequest);
				}
			flightData = convertListToJson(crazyFlightList, toughJetFlightList, busyFlightsRequest);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flightData = populateDummyFlights(busyFlightsRequest);
			e.printStackTrace();
		}

		return flightData;
	}
	
	/***
	 * This method populates dummy flights data in case of CrazyAir & ToughJet Service unavailability. 
	 * @param busyFlightsRequest
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String populateDummyFlights(BusyFlightsRequest busyFlightsRequest){
		String flightsData = null ;
		List<CrazyAirResponse> crazyFlightList = (List<CrazyAirResponse>) FlightSearchUtility.jSonToObjectCrazyAir(clientService.crazyFlightJson);
		
		//JSON response being converted into List
		List<ToughJetResponse> toughJetFlightList = (List<ToughJetResponse>) FlightSearchUtility.jSonToObjectToughJet(clientService.toughFlightJson);
		flightsData = convertListToJson(crazyFlightList, toughJetFlightList, busyFlightsRequest);		
		
		return flightsData;
	}
	/***
	 * Method is being used to convert list to JSON repesentation.
	 * @param crazyFlightList of type CrazyAir List
	 * @param toughJetFlightList of type ToughJet List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private  String convertListToJson(List<CrazyAirResponse> crazyFlightList, List<ToughJetResponse> toughJetFlightList, BusyFlightsRequest busyFlightsRequest) {
		List<Object> allFlightsList = new ArrayList<Object>();
		String flightsData = null;
		// merging two list 
		allFlightsList.addAll((Collection<? extends Object>) crazyFlightList);
        allFlightsList.addAll((Collection<? extends Object>) toughJetFlightList);

        System.out.println("Before Sorting -------->>>>");
		for(Object obj:allFlightsList)
			System.out.println(obj);
		allFlightsList = (List<Object>) FlightSearchUtility.sortOnFlightsFare(allFlightsList);
		System.out.println("After Sorting -------->>>>");
		for(Object obj:allFlightsList)
			System.out.println(obj);
	
		allFlightsList = createBusyFlightsResponse(allFlightsList, busyFlightsRequest);
		System.out.println(allFlightsList);
		
		try {
	        	ObjectMapper mapper = new ObjectMapper();
	        	mapper.writeValue(System.out, allFlightsList);
	        	flightsData =	mapper.writeValueAsString(allFlightsList);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      
			return flightsData;
		
	}
	
	/*
	 * finally the response object to be shared
	 * */
	private List<Object> createBusyFlightsResponse(List<Object> allFlightsList, BusyFlightsRequest busyFlightsRequest) {
		BusyFlightsResponse busyFlightsResponse = null;
		List<Object> responseList = new ArrayList<Object>();
		for (Object object : allFlightsList) {
			if (object instanceof CrazyAirResponse){
				busyFlightsResponse = new BusyFlightsResponse();
				busyFlightsResponse.setAirline(((CrazyAirResponse) object).getAirline());
				busyFlightsResponse.setSupplier(CRAZYAIR);
				busyFlightsResponse.setFare(((CrazyAirResponse) object).getPrice());
				busyFlightsResponse.setDepartureAirportCode(((CrazyAirResponse) object).getDepartureAirportCode());
				busyFlightsResponse.setDestinationAirportCode(((CrazyAirResponse) object).getDestinationAirportCode());
				busyFlightsResponse.setDepartureDate(busyFlightsRequest.getDepartureDate());
				busyFlightsResponse.setArrivalDate(busyFlightsRequest.getReturnDate());
				
				responseList.add(busyFlightsResponse);
				
			}else if(object instanceof ToughJetResponse){
				busyFlightsResponse = new BusyFlightsResponse();
				busyFlightsResponse.setAirline(((ToughJetResponse) object).getCarrier());
				busyFlightsResponse.setSupplier(TOUGHAIR);
				busyFlightsResponse.setFare((((ToughJetResponse) object).getBasePrice() + ((ToughJetResponse) object).getTax()) - 
						(((ToughJetResponse) object).getDiscount()/100)* (((ToughJetResponse) object).getBasePrice()));
				busyFlightsResponse.setDepartureAirportCode(((ToughJetResponse) object).getDepartureAirportName());
				busyFlightsResponse.setDestinationAirportCode(((ToughJetResponse) object).getArrivalAirportName());
				busyFlightsResponse.setDepartureDate(busyFlightsRequest.getDepartureDate());
				busyFlightsResponse.setArrivalDate(busyFlightsRequest.getReturnDate());
				
				responseList.add(busyFlightsResponse);
			}
		}
		return responseList;
	}
	
			
}
