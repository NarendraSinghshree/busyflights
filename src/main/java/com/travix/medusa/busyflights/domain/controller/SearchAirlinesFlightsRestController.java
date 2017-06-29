package com.travix.medusa.busyflights.domain.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.service.BusyFlightService;
import com.travix.medusa.busyflights.utility.CustomErrorType;

@RestController
@RequestMapping("/flightApi")
public class SearchAirlinesFlightsRestController {
	
	@Autowired
	BusyFlightService busyFlightSearch; //Service which will do all flights data retrieval/manipulation work
	
	public static final Logger logger = LoggerFactory.getLogger(SearchAirlinesFlightsRestController.class);
	
	/*
	 * ----------->>>> Retrieve Airline Flights Search
	 */

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@RequestMapping(value = "/search/{origin}/{destination}/{departureDate}/{returnDate}/{numberOfPassengers}", method = RequestMethod.GET)
		public ResponseEntity<?> getFlights(@PathVariable("origin") String origin,
											@PathVariable("destination") String destination,
											@PathVariable("departureDate") String departureDate,
											@PathVariable("returnDate") String returnDate,
											@PathVariable("numberOfPassengers") int numberOfPassengers) {
			logger.info("Fetching available Airline Flights  with origin {} , destination {} , departureDate {}, returnDate {}, numberOfPassengers {} ::", 
															      origin, destination, departureDate, returnDate, numberOfPassengers);
			BusyFlightsRequest busyFlightsrequest = new BusyFlightsRequest();
			busyFlightsrequest.setOrigin(origin);
			busyFlightsrequest.setDestination(destination);
			busyFlightsrequest.setDepartureDate(departureDate);
			busyFlightsrequest.setReturnDate(returnDate);
			busyFlightsrequest.setNumberOfPassengers(numberOfPassengers);
			String flightsSearchData = null;
			if(((busyFlightsrequest.getNumberOfPassengers())==0) || ((busyFlightsrequest.getNumberOfPassengers())>4)) {
				return new ResponseEntity(new CustomErrorType("Flight search with " 
							+ numberOfPassengers+ " Passengers not Allowed try to book with minimum 1 or  maximum 4 passenger"), HttpStatus.FORBIDDEN);
			} else if ((busyFlightSearch.findAllAvailableFlights(busyFlightsrequest)) == null) {
				
				return new ResponseEntity(new CustomErrorType("Flight search with origin " + origin 
					+ " destination "	+ destination + " departureDate " + departureDate 
					+ " returnDate " + returnDate	+ " numberOfPassengers "
					+ numberOfPassengers + " not found"), HttpStatus.NOT_FOUND);
			} else {
					flightsSearchData = busyFlightSearch.findAllAvailableFlights(busyFlightsrequest);
					return new ResponseEntity<>(flightsSearchData, HttpStatus.OK);
			}
		}

}
