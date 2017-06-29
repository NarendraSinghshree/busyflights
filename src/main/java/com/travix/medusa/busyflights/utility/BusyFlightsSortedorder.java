package com.travix.medusa.busyflights.utility;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;

public class BusyFlightsSortedorder implements Comparator<Object> {

	@Override
	public int compare(Object obj1, Object obj2) {
		
			// TODO Auto-generated method stub
			//return 0;
			{
				CrazyAirResponse obj11=null;
				CrazyAirResponse obj12=null;
				ToughJetResponse obj21=null;
				ToughJetResponse obj22=null;
				if(obj1 instanceof CrazyAirResponse )
				  obj11=(CrazyAirResponse)obj1;
				else if(obj1 instanceof ToughJetResponse )
				  obj21=(ToughJetResponse)obj1;
				if(obj2 instanceof CrazyAirResponse )
					  obj12=(CrazyAirResponse)obj2;
				else if(obj2 instanceof ToughJetResponse )
				      obj22=(ToughJetResponse)obj2;
				if((obj11==null?obj21.getBasePrice()+obj21.getTax()-(obj21.getBasePrice()*(obj21.getDiscount()/100)):obj11.getPrice()) > (obj22==null?obj12.getPrice():obj22.getBasePrice()+obj22.getTax()-(obj22.getBasePrice()*(obj22.getDiscount()/100)))){
					 return 1;
				} else {
			    	return -1;
			    }
			}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sortAllFlightList(List list){
		Collections.sort(list,new BusyFlightsSortedorder());
	}

}
