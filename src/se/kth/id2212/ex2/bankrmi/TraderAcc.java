package se.kth.id2212.ex2.bankrmi;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * This is the Trader Account interface. It is Similar to the Account.java class.
 * @author joehulden
 */
public interface TraderAcc extends Remote {
        
    public String getName() throws RemoteException;
    public void itemSold(Item item) throws RemoteException;
    
    public void wishMatched(Item item) throws RemoteException;;
    
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
