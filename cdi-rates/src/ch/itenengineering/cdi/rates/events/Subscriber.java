package ch.itenengineering.cdi.rates.events;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;

import ch.itenengineering.cdi.rates.Rate;

@RequestScoped
public class Subscriber {

	@PostConstruct
	public void start() {
		System.out.println("### observer start ###");
	}
	
	
	public void thresholdListener(@Observes @Threshold Rate rate) {
		System.out.println("catched threshold event: " + rate);
	}
	
	public void rateListener(@Observes Rate rate) {
		System.out.println("catched rate event: " + rate);
	}

	@PreDestroy
	public void end() {
		System.out.println("### observer end ###");
	}

}
