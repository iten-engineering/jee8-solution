package ch.itenengineering.config.ejb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

@Singleton
public class ConfigServiceBean implements ConfigService {

	private Map<String, String> configuration = new HashMap<String, String>();

	@PostConstruct
	private void init() {
		for (int i = 0; i < 10; i++) {
			String key = "k" + i;
			String value = "value #" + i;
			this.configuration.put(key, value);
		}
	}

	@Override
	@Lock(LockType.READ)
	public String getConfig(String key) {
		return this.configuration.get(key);
	}

	@Override
	@Lock(LockType.WRITE)
	// Default, koennte weggelassen werden
	public void setConfig(String key, String value) {
		this.configuration.put(key, value);
	}

	@Override
	@Lock(LockType.READ)
	public List<String> listConfig() {
		List<String> result = new ArrayList<String>();

		for (String key : this.configuration.keySet()) {
			result.add(key + " = " + this.configuration.get(key));
		}

		Collections.sort(result);
		return result;
	}

}
