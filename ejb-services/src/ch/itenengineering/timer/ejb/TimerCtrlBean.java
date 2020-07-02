package ch.itenengineering.timer.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

@Stateless
public class TimerCtrlBean implements TimerCtrl {

	@Resource
	TimerService timerService;

	@Override
	public void start(String name, long interval) {
		timerService.createTimer(0, interval, name);
	}

	@Override
	public List<String> listNames() {
		List<String> result = new ArrayList<String>();

		for (Iterator iter = timerService.getTimers().iterator(); iter
				.hasNext();) {
			Timer timer = (Timer) iter.next();

			String timerName = (String) timer.getInfo();
			result.add(timerName);
		}

		return result;
	}

	@Override
	public void stop(String name) {
		for (Iterator iter = timerService.getTimers().iterator(); iter
				.hasNext();) {
			Timer timer = (Timer) iter.next();
			String timerName = (String) timer.getInfo();

			if (timerName.equals(name)) {
				timer.cancel();
			}
		}
	}

	@Timeout
	public void work(Timer timer) {

		String timerName = (String) timer.getInfo();
		Date nextTimeout = timer.getNextTimeout();

		System.out.println(timerName + "(next timeout = " + nextTimeout + ")");
	}

} // end of class
