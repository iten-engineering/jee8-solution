package ch.itenengineering.helloworld.ejb;

import javax.ejb.Stateless;

@Stateless 
public class HelloWorldBean implements HelloWorldRemote {

    @Override
    public String helloWorld(String greeting) {
        return "Echo from EJB: Greeting <" + greeting + "> successfully received!";
    }

} // end
