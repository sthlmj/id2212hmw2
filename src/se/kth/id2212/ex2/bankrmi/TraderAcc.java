package se.kth.id2212.ex2.bankrmi;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 *
 * @author joehulden
 */
public interface TraderAcc extends Remote {
    //TODO: THis is the Trader Account interface, for the market activities.. 
    //Similarly to the Account. But the Account is for the bank account. 
        
    //put a product up for sale on the market
    public void sell(String itemName, float price) throws RemoteException, RejectedException; 
    
    //buy a product available on the market
    public void buy(String itemName, float price) throws RemoteException, RejectedException;
    
    //wish a product to buy for example: name TSLA, price 1200. Similar to sell method but with a call-back!
    public void wish(String itemName, float price) throws RemoteException, RejectedException;
    
    //shows available products on the market
    public String listProducts() throws RemoteException; 
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