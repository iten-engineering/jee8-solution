package ch.itenengineering.shop.ejb;

import java.util.Map;

import javax.ejb.Local;

import ch.itenengineering.shop.domain.Item;

@Local
public interface Basket {

	public void addItem(Item item);

	public void removeItem(String isbn);

	public Map<String, Item> getBasket();

	public void order();

	public void cancel();

} // end	
