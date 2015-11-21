
package se.kth.id2212.ex2.bankrmi;
import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 *
 * @author joehulden
 */
public interface Market extends Remote {    
    //TODO: make this class an interface. A market interface. Add the MarketImpl so it connects to this Market interface.
    
    //shows available products on the market
    public String[] listTraderAccs() throws RemoteException; 
        
    public TraderAcc newTraderAcc(String name) throws RemoteException, RejectedException;
    
    public TraderAcc getTraderAcc(String name) throws RemoteException;
    
    public boolean deleteTraderAcc(String name) throws RemoteException;
    
    //shows available products on the market
    public String listProducts() throws RemoteException; 
}


/*
package se.kth.id2212.ex2.bankrmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Bank extends Remote {
    public Account newAccount(String name) throws RemoteException, RejectedException;

    public Account getAccount(String name) throws RemoteException;

    public boolean deleteAccount(String name) throws RemoteException;

    public String[] listAccounts() throws RemoteException;
}

*/