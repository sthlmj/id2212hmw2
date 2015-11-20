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
    
    //shows available products
    public String listProducts() throws RemoteException; 
    
    //put a product up for sale on market
    public void sell(String itemName, float price) throws RemoteException, RejectedException; 
    
    //buy a product available on market
    public void buy(String itemName, float price) throws RemoteException, RejectedException;
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