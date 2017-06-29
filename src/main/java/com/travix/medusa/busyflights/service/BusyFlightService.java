package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
/***
 * 
 * @author narendra.singh
 *
 */
public interface BusyFlightService {
	
	 /***
	 * Method produce  available flights in json format for Crazy Air and Tough Jet flights
	 * @param busyFlightsRequest
	 * @return Json String.
	 */
	String findAllAvailableFlights(BusyFlightsRequest busyFlightsRequest);
	
	
}
