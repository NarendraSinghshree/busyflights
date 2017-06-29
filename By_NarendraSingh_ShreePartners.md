**Travix - How to run existing application**

**What is required:**
apache-tomcat-8.0.44 is required to run this application

There are few steps to run current application, which are listed below :

1)Maven clean
2)Maven install
3)Run As "BusyFlightsApplication.java" Java Application on doing so ..
	*****  Tomcat server will start with default 8080 port.
	
	a) open Uri :---> 		http://localhost:8080/flightApi//search/{origin}/{destination}/{departureDate}/{returnDate}/{numberOfPassengers}
	b) Json error message will display on browser if passenger count is less than 1 or more than 4.
	c) Uri :--> http://localhost:8080/flightApi/search/LHR/SHR/22-07-2017/12-07-2017/4/ will display Json response in sorted fare 		(Ascending Order).
	
**Assumption - during code written**
	
	I already took prior assumption that CrazyAir and ToughJet services are available on 
	specified uri running on specified port and protocol.
	
	** In absence of both service uri dummy data is being shown. 