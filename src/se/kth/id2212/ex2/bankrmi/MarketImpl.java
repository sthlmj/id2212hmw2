//MarketImpl.java is the servant class serving Market.java interface
package se.kth.id2212.ex2.bankrmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 * //TODO: This is the servant class for the Market. It is similar to the BankImpl.java
 * @author joehulden
 */

//Declaring the remote object
@SuppressWarnings("serial")
public class MarketImpl extends UnicastRemoteObject implements Market {
    
    private String marketName;
    private Map<String, TraderAcc> traderaccs = new HashMap<>();
    
    public MarketImpl(String marketName) throws RemoteException {
        super();
        this.marketName = marketName;
    }
    
    //metod for listTraderAccs interface on Market.java
    @Override
    public synchronized String[] listTraderAccs() {
        
        return traderaccs.keySet().toArray(new String[1]);
    }
    
    //metod for newTraderAcc interface on Market.java
    @Override
    public synchronized TraderAcc newTraderAcc(String name) throws RemoteException, RejectedException {
        
        TraderAccImpl traderacc = (TraderAccImpl) traderaccs.get(name);
        if (traderacc != null) {
            System.out.println("Account exists");
            throw new RejectedException("Rejected: se.kth.id2212.ex2.bankrmi: " + marketName + " Account for: " + name + " already exists: " + traderacc);
        }
        traderacc = new TraderAccImpl(name);
        traderaccs.put(name, traderacc);
        System.out.println("se.kth.id2212.ex2.bankrmi: " + marketName + " Account: " + traderacc + " has been created for " + name);
        
        return traderacc;
    }
    
    //metod for getTraderAcc interface on Market.java
    @Override
    public synchronized TraderAcc getTraderAcc(String name) {
        return traderaccs.get(name);
    }

    //metod for deleteTraderAcc interface on Market.java
    @Override
    public synchronized boolean deleteTraderAcc(String name) {
        if (!hasTraderAcc(name)) {
            return false; 
        }
        traderaccs.remove(name);
        System.out.println("se.kth.id2212.ex2.bankrmi: " + marketName + " Account for " + name + " has been deleted");
        return true;
    }
    
    //metod supporting account check available for delete.
    private boolean hasTraderAcc(String name) {
        return traderaccs.get(name) != null;
    }
}    
/*
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

// Declaring the remote object
@SuppressWarnings("serial")
public class BankImpl extends UnicastRemoteObject implements Bank {

    private String bankName;
    private Map<String, Account> accounts = new HashMap<>();

// Binding reference to remote object in the remote object registry  
    public BankImpl(String bankName) throws RemoteException {
        super();
        this.bankName = bankName;
    }

// Begin: Implementation of remote interface

    @Override
    public synchronized String[] listAccounts() {

        return accounts.keySet().toArray(new String[1]);
    }

    @Override
    public synchronized Account newAccount(String name) throws RemoteException, RejectedException {

        AccountImpl account = (AccountImpl) accounts.get(name);
        if (account != null) {
            System.out.println("Account [" + name + "] exists!!!");
            throw new RejectedException("Rejected: se.kth.id2212.ex2.Bank: " + bankName
                                        + " Account for: " + name + " already exists: " + account);
        }
        account = new AccountImpl(name);
        accounts.put(name, account);
        System.out.println("se.kth.id2212.ex2.Bank: " + bankName + " Account: " + account
                           + " has been created for " + name);
        return account;
    }

    @Override
    public synchronized Account getAccount(String name) {
        return accounts.get(name);
    }

    @Override
    public synchronized boolean deleteAccount(String name) {
        if (!hasAccount(name)) {
            return false;
        }
        accounts.remove(name);
        System.out.println("se.kth.id2212.ex2.Bank: " + bankName + " Account for " + name
                           + " has been deleted");
        return true;
    }

    private boolean hasAccount(String name) {
        return accounts.get(name) != null;
    }
}
*/