
package se.kth.id2212.ex2.bankrmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;



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
	private ArrayList<Item> wishlist = new ArrayList<Item>();
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
        for(TraderAcc t : traderaccs){
            if(t.getName().equals(name)){
                throw new RejectedException("Rejected: se.kth.id2212.ex2.marketrmi: " + marketName + " Account for: " + name + " already exists");  
            }
        }
        
    	 
    	 TraderAcc traderacc = new TraderAccImpl(name);
      
        traderaccs.add(traderacc);
        System.out.println("se.kth.id2212.ex2.marketrmi: " + marketName + " Account: " + traderacc + " has been created for " + name);
        
        return traderacc;
       
    }
    
    //metod for getTraderAcc interface on Market.java
    @Override
    public synchronized TraderAcc getTraderAcc(String name) throws RejectedException {
    
        try {
                for(TraderAcc t : traderaccs){
                     if(t.getName().equals(name)){ //account found
                        return t;  
                     }
                 } 

        } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
        //Account does not exist
    	 throw new RejectedException("Account " + name + "does not exist");

    }

    //metod for deleteTraderAcc interface on Market.java
    @Override
    public synchronized boolean deleteTraderAcc(String name) {
        try{
            
        for(TraderAcc t : traderaccs){
            if(t.getName().equals(name)){ //account found
               traderaccs.remove(t);
               System.out.println("se.kth.id2212.ex2.marketrmi: " + marketName + " Account for " + name + " has been deleted");
               return true;
            }
        } 
        } catch (RemoteException ex) {
               
            }
        return false;
     
    }
    
    //metod supporting account check available for delete.
    private boolean hasTraderAcc(String name) {
            
         try {
            return traderaccs.indexOf(new TraderAccImpl(name)) != -1;
            } catch (RemoteException ex) {
               
            }
        
        return false;
    }

    //list all products available on the market.
    @Override
    public List<String> listProducts() throws RemoteException {
        
    	List <String> out = new ArrayList<>();
    
    	for(Item i : items){
    		out.add("Name: " + i.name() + " Price: " + i.price());
    	}

    	return out;
    }

	

	@Override
	public Item buy(Item item) throws RemoteException, RejectedException {
		int i = 0;
                for(Item it: items) {
                    if(it.name().equals(item.name()) && it.price() <= item.price()) {
                        Item temp  = items.remove(i);
                        temp.trader().itemSold(temp);
                        return temp;
                    }
                    
                    i++;
                }
                
		/*if( (i = items.indexOf((ItemImpl)item)) != -1 ) {
			return items.remove(i);
                        //TODO dra pengar från bankonto annars skicka rejectex...
		}*/
		
			throw new RejectedException("Item could not be purchased");
		
		
	}

	@Override
	public void wish(Item item) throws RemoteException, RejectedException {
		try{
                    Item temp = buy(item);
                    if(temp != null) { // Item bought
                        
                        //Callback item bought
                        return;
                    }
                }catch(RejectedException e){ //Not in market yet
                    
                }
		
                
		//TODO kolla om item redan finns till det priset
		wishlist.add(item);
	}

    @Override
    public void sell(Item item) throws RemoteException {
        items.add(item);
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
