package ch.itenengineering.cdi.rates.alternatives;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Alternative;

import ch.itenengineering.cdi.rates.Rate;
import ch.itenengineering.cdi.rates.RateDAO;


@Alternative
public class ValiantRateDAO implements RateDAO {

	@Override
	public List<Rate> read() {
		List<Rate> rates = new ArrayList<Rate>();
		
		rates.add(new Rate("USD", 0.9148, -0.66));
		rates.add(new Rate("EUR", 1.2018, +0.02));
		rates.add(new Rate("JPY", 0.0113, +1.17));
		rates.add(new Rate("GBP", 1.4732, 0));
		
		return rates;
	}

	@Override
	public String getReference() {
		return "Valiant";
	}

}
