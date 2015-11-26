
package se.kth.id2212.ex2.bankrmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * This is the servant class for the Market.java interface. It is similar to the BankImpl.java
 * @author joehulden
 */

//Declares the remote object
@SuppressWarnings("serial")
public class MarketImpl extends UnicastRemoteObject implements Market {
    
    private String marketName;
	private ArrayList<Item> items = new ArrayList<Item>(); 
	private ArrayList<TraderAcc> traders = new ArrayList<TraderAcc>();
	private ArrayList<Item> whishlist = new ArrayList<Item>();
    private ArrayList<TraderAcc> traderaccs = new ArrayList<TraderAcc>();
    
    
    //private Map<String, TraderAcc> traderaccs = new HashMap<>();
    
    public MarketImpl(String marketName) throws RemoteException {
        super();
        this.marketName = marketName;
    }
    
    //method for listTraderAccs interface on Market.java
    @Override
    public synchronized String[] listTraderAccs() {
    	
    	String[] out = new String[traderaccs.size()];
    	int i = 0;
    	for(TraderAcc t : traderaccs){
    		out[i++] = ((TraderAccImpl ) t).getName();
    	}
        return out;
        //return traderaccs.keySet().toArray(new String[1]);
    }
    
    //metod for newTraderAcc interface on Market.java
    @Override
    public synchronized TraderAcc newTraderAcc(String name) throws RemoteException, RejectedException {
        
    	//Account exists
    	 if( traderaccs.indexOf(name) != -1){
    		  System.out.println("Account exists");
              throw new RejectedException("Rejected: se.kth.id2212.ex2.marketrmi: " + marketName + " Account for: " + name + " already exists");    
    	 }
    	
       /* TraderAccImpl traderacc = traderaccs(name);
        		new TraderAccImpl(name);*/
        		//(TraderAccImpl) traderaccs.get(name);
       /* if (traderacc != null) {
            System.out.println("Account exists");
            throw new RejectedException("Rejected: se.kth.id2212.ex2.marketrmi: " + marketName + " Account for: " + name + " already exists: " + traderacc);
        }*/
    	 TraderAcc traderacc = new TraderAccImpl(name);
       // traderaccs.put(name, traderacc);
        traderaccs.add(traderacc);
        System.out.println("se.kth.id2212.ex2.marketrmi: " + marketName + " Account: " + traderacc + " has been created for " + name);
        
        return traderacc;
    }
    
    //metod for getTraderAcc interface on Market.java
    @Override
    public synchronized TraderAcc getTraderAcc(String name) throws RejectedException {
    	
    	int i;
    	//Account does not exist
    	
    	 try {
			if( (i =  traderaccs.indexOf(new TraderAccImpl(name))) == -1){
				 throw new RejectedException("Account " + name + "does not exist");
			 }
			 else {
				 return traderaccs.get(i);
			 }
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 return null;
    	 
        //return traderaccs.get(name);
    }

    //metod for deleteTraderAcc interface on Market.java
    @Override
    public synchronized boolean deleteTraderAcc(String name) {
        if (!hasTraderAcc(name)) {
            return false; 
        }
        traderaccs.remove(name);
        System.out.println("se.kth.id2212.ex2.marketrmi: " + marketName + " Account for " + name + " has been deleted");
        return true;
    }
    
    //metod supporting account check available for delete.
    private boolean hasTraderAcc(String name) {
        return traderaccs.indexOf(name) != -1;
    }

    //list all products available on the market.
    @Override
    public List<String> listProducts() throws RemoteException {
        
    	List <String> out = new ArrayList<String>();
    
    	for(Item i : items){
    		out.add("Name: " + i.name() + " Price: " + i.price());
    	}

    	return out;
    	//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public void sell(TraderAcc t, Item item) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Item buy(Item item) throws RemoteException, RejectedException {
		int i = 0;
		if( (i = items.indexOf(item)) != -1 ) {
			return items.remove(i);
		}
		else{//TODO dra pengar från bankonto
			throw new RejectedException("Item could not be purchased");
		}
		
	}

	@Override
	public void wish(Item item) throws RemoteException, RejectedException {
		
		whishlist.add(item);
		
		
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
