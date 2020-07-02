package ch.itenengineering.cdi.hello;

public class HelloServiceImpl implements HelloService {

   public String createGreeting (String name) {
      return "Hello " + name + ".";
   }

}
