package se.kth.id2212.ex2.bankrmi;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * This is the Trader Account interface. It is Similar to the Account.java class.
 * @author joehulden
 */
public interface TraderAcc extends Remote {
        
    //put a product up for sale on the market
    public void sell(String itemName, float price) throws RemoteException, RejectedException; 
    
    //buy a product that is available on the market
    public void buy(String itemName, float price) throws RemoteException, RejectedException;
    
    //wish a product to buy for example: name TSLA, price 1200. Similar to sell method but with a call-back to client!
    public void wish(String itemName, float price) throws RemoteException, RejectedException;
    
    
    
}
/*

package se.kth.id2212.ex2.bankrmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Account extends Remote {
    public float getBalance() throws RemoteException;

    public void deposit(float value) throws RemoteException, RejectedException;

    public void withdraw(float value) throws RemoteException, RejectedException;
}


*/