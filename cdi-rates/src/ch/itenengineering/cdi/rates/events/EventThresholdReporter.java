package ch.itenengineering.cdi.rates.events;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import ch.itenengineering.cdi.rates.Rate;
import ch.itenengineering.cdi.rates.ThresholdReporter;

@Publish
public class EventThresholdReporter implements ThresholdReporter {

	@Inject @Threshold
	private Event<Rate> event;
	
	@Override
	public void check(Rate rate) {
		if (rate.getChange() < MIN_THRESHOLD || rate.getChange() > MAX_THRESHOLD) {
			System.out.println("fire event for rate: " + rate);
			event.fire(rate);
		}	
	}

}
