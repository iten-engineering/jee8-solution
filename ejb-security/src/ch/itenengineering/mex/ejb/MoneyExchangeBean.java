package ch.itenengineering.mex.ejb;

import java.util.EnumMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

@Stateless
@DeclareRoles("VIP")
@RolesAllowed("Customer")
public class MoneyExchangeBean implements MoneyExchange {

	@Resource
	SessionContext ctx;

	/**
	 * The VIP bonus in percent, used for calculating better rates.
	 */
	private int vipBonus = 5;

	/**
	 * The exchange rates for 1 CHF.
	 */
	private Map<CurrencyType, Double> rates = new EnumMap<CurrencyType, Double>(
			CurrencyType.class);

	// --------------------------------------------------------------------------------------------
	// methods
	// --------------------------------------------------------------------------------------------

	@SuppressWarnings("unused")
	@PostConstruct
	private void init() {
		// exchange rates for 1 CHF
		this.rates.put(CurrencyType.CHF, 1.0);
		this.rates.put(CurrencyType.EUR, 0.77);
		this.rates.put(CurrencyType.USD, 1.03);
	}

	public double getRate(CurrencyType currency) {

		// get principal name
		System.out.println("get rate " + currency + " for user "
				+ ctx.getCallerPrincipal().getName());

		// get rate
		double rate = this.rates.get(currency);

		// calculate bonus rate for VIP's
		if (ctx.isCallerInRole("VIP")) {
			System.out.println("change rate with VIP bonus");
			int percent = getVIPBonus();
			rate = rate * (1 + (percent / 100.0));
		}

		return rate;
	}

	@RolesAllowed("VIP")
	public int getVIPBonus() {
		return vipBonus;
	}

} // end of class
