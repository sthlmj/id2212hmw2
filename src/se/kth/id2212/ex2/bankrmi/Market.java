
package se.kth.id2212.ex2.bankrmi;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
/**
 * This is the market interface.
 * @author joehulden
 */
public interface Market extends Remote {    
    
    //shows all trader accounts
    public String[] listTraderAccs() throws RemoteException; 
        
    public TraderAcc newTraderAcc(String name) throws RemoteException, RejectedException;
    
    public TraderAcc getTraderAcc(String name) throws RemoteException;
    
    public boolean deleteTraderAcc(String name) throws RemoteException;
    
    //shows all products on the market
    public List<String> listProducts() throws RemoteException;
    
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