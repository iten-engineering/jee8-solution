package ch.itenengineering.cdi.hellojsf;

@Relaxed
public class RelaxedGreetingService extends GreetingService {

	@Override
	public String createGreeting(String name) {
		return "Hi " + name + ", who are you?";
	}

}
