package com.travix.medusa.busyflights.utility;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;

public class FlightSearchUtility {
	
	public static Date StringToDate(String strDate)  {
	    Date dtReturn = null;
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
	    try {
	        dtReturn = simpleDateFormat.parse(strDate);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    return dtReturn;
	}
	
	
	// 2004-06-14T19:GMT20:30Z
    // 2004-06-20T06:GMT22:01Z
    //    Year:
    //       YYYY (eg 1997)
    //    Year and month:
    //       YYYY-MM (eg 1997-07)
    //    Complete date:
    //       YYYY-MM-DD (eg 1997-07-16)
    //    Complete date plus hours and minutes:
    //       YYYY-MM-DDThh:mmTZD (eg 1997-07-16T19:20+01:00)
    //    Complete date plus hours, minutes and seconds:
    //       YYYY-MM-DDThh:mm:ssTZD (eg 1997-07-16T19:20:30+01:00)
    //    Complete date plus hours, minutes, seconds and a decimal fraction of a
    // second
    //       YYYY-MM-DDThh:mm:ss.sTZD (eg 1997-07-16T19:20:30.45+01:00)

    // where:

    //      YYYY = four-digit year
    //      MM   = two-digit month (01=January, etc.)
    //      DD   = two-digit day of month (01 through 31)
    //      hh   = two digits of hour (00 through 23) (am/pm NOT allowed)
    //      mm   = two digits of minute (00 through 59)
    //      ss   = two digits of second (00 through 59)
    //      s    = one or more digits representing a decimal fraction of a second
    //      TZD  = time zone designator (Z or +hh:mm or -hh:mm)
    public static Date parse( String input ) throws java.text.ParseException {

        //NOTE: SimpleDateFormat uses GMT[-+]hh:mm for the TZ which breaks
        //things a bit.  Before we go on we have to repair this.
        SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssz" );
        
        //this is zero time so we need to add that TZ indicator for 
        if ( input.endsWith( "Z" ) ) {
            input = input.substring( 0, input.length() - 1) + "GMT-00:00";
        } else {
            int inset = 6;
        
            String s0 = input.substring( 0, input.length() - inset );
            String s1 = input.substring( input.length() - inset, input.length() );

            input = s0 + "GMT" + s1;
        }
        
        return df.parse( input );
        
    }

    public static String toString( Date date ) {
        
        SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssz" );
        
        TimeZone tz = TimeZone.getTimeZone( "UTC" );
        
        df.setTimeZone( tz );

        String output = df.format( date );

        int inset0 = 9;
        int inset1 = 6;
        
        String s0 = output.substring( 0, output.length() - inset0 );
        String s1 = output.substring( output.length() - inset1, output.length() );

        String result = s0 + s1;

        result = result.replaceAll( "UTC", "+00:00" );
        
        return result;
        
    }
    
    public static List<?> sortOnFlightsFare(List<Object> allFlightsList) {
    	BusyFlightsSortedorder sortFlightList=new BusyFlightsSortedorder();
		sortFlightList.sortAllFlightList(allFlightsList);
		return allFlightsList;
	}
    
    public static List<CrazyAirResponse> jSonToObjectCrazyAir(String responseJSON) {
		List<CrazyAirResponse> list = null;
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			list = mapper.readValue(responseJSON, new TypeReference<List<CrazyAirResponse>>(){});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
    
    public static List<ToughJetResponse> jSonToObjectToughJet(String responseJSON) {
		List<ToughJetResponse> list = null;
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			list = mapper.readValue(responseJSON, new TypeReference<List<ToughJetResponse>>(){});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
		
}
