package ch.itenengineering.converter.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ch.itenengineering.converter.domain.CurrencyType;

@Stateless
public class CurrencyConverterBean implements CurrencyConverter {

	/**
	 * local interface to the rate service
	 *
	 * note: the reference will by injected by the container after the creation
	 * of the bean class and before of any callback and business method calls
	 */
	@EJB
	RateService rateService;

	/**
	 * convert the given CHF value to the desired currency
	 *
	 * @param type
	 *            currency type
	 * @param value
	 *            CHF to convert
	 * @return converted value
	 */
	public Double convertCHF(Double value, CurrencyType type) {

		// get rate for the given currency type
		double rate = rateService.getRateCHF(type);

		// calulate and return value
		return value * rate;
	}

} // end of class
