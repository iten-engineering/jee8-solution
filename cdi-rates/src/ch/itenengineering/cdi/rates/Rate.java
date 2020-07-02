package ch.itenengineering.cdi.rates;

public class Rate {

	private String currency;
	private double rate; // rate to CHF
	private double change; // change of the day in %
	
	public Rate(String currency, double rate, double change) {
		super();
		this.currency = currency;
		this.rate = rate;
		this.change = change;
	}

	@Override
	public String toString() {
		return String.format("%s = %2.4f (%+2.2f%%)", currency, rate, change);
	}
	
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getChange() {
		return change;
	}

	public void setChange(double change) {
		this.change = change;
	}
	
	
	public static void main (String[] args) {
		
		System.out.println(new Rate("USD", 0.9148, -0.66));
		System.out.println(new Rate("EUR", 1.2018, +0.02));
		System.out.println(new Rate("JPY", 0.0113, +1.17));
		System.out.println(new Rate("GBP", 1.4732, 0));
		System.out.println(new Rate("CAD", 0.9178, -1.25));
		System.out.println(new Rate("AUD", 0.9411, +1.55));
		System.out.println(new Rate("HKD", 0.1179, +5.02));
		
	}
	
}
