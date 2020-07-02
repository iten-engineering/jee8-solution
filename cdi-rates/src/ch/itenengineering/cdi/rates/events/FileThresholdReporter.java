package ch.itenengineering.cdi.rates.events;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

import ch.itenengineering.cdi.rates.Rate;
import ch.itenengineering.cdi.rates.ThresholdReporter;


public class FileThresholdReporter implements ThresholdReporter {

	@PostConstruct
	public void open() {
		System.out.println("### open file ###");
	}
	
	@Override
	public void check(Rate rate) {
		if (rate.getChange() < MIN_THRESHOLD || rate.getChange() > MAX_THRESHOLD) {
			System.out.println("report to file: " + rate);
		}
		
	}

	@PreDestroy
	public void close() {
		System.out.println("### close file ###");
	}

}
