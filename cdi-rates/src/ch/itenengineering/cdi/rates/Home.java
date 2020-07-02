package ch.itenengineering.cdi.rates;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import ch.itenengineering.cdi.rates.events.Publish;


@Named
@RequestScoped
public class Home {

	List<Rate> rates;

	String reference;

	@Inject
	private RateDAO rateDAO;

	@Inject @Publish
	private ThresholdReporter thresholdReporter;

	@Inject
	private Event<Rate> event;
	
	@PostConstruct
	public void init() {
		rates = rateDAO.read();
		
		for (Rate rate : rates) {
			//event.fire(rate);
			thresholdReporter.check(rate);
		}
		reference = rateDAO.getReference();
	}

	public List<Rate> getRates() {
		return rates;
	}

	public String getReference() {
		return reference;
	}

}
