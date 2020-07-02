package ch.itenengineering.helloworld.ejb;

import javax.ejb.Remote;

@Remote
public interface HelloWorldRemote {

    public String helloWorld(String greeting);
    
} // end
