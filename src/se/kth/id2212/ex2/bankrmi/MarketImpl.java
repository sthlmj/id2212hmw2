
package se.kth.id2212.ex2.bankrmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * This is the servant class for the Market.java interface.
 * @author joehulden
 */

//implementerar market interface creates and exports a new unicast remote object
@SuppressWarnings("serial")
public class MarketImpl extends UnicastRemoteObject implements Market {
    
    private String marketName;
    private ArrayList<Item> items = new ArrayList<Item>(); 
    private ArrayList<TraderAcc> traders = new ArrayList<TraderAcc>(); //fix
    private ArrayList<Item> wishlist = new ArrayList<Item>();
    private ArrayList<TraderAcc> traderaccs = new ArrayList<TraderAcc>();
    private Bank bank;
    
    //startar rmi registret(som dns uppslagning) kopplingen mot banken
    public MarketImpl(String marketName) throws RemoteException {
        super();
        this.marketName = marketName;
        try {
            try {
                LocateRegistry.getRegistry(1099).list();
            } catch (RemoteException e) {
                LocateRegistry.createRegistry(1099);
            }
            bank = (Bank) Naming.lookup("Nordea");
            
        } catch (Exception e) {
            System.out.println("The runtime failed: " + e.getMessage());
            System.exit(0);
        }
    }
    
    //implements interface
    @Override
    public synchronized String[] listTraderAccs() {
    	
    	String[] out = new String[traderaccs.size()];
    	int i = 0;
    	for(TraderAcc t : traderaccs){
    		out[i++] = ((TraderAccImpl ) t).getName();
    	}
        return out;
    }
    
    //implements interface
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
    
    //implements interface
    @Override
    public synchronized TraderAcc getTraderAcc(String name) throws RejectedException {
    
        try {
                for(TraderAcc t : traderaccs){
                     if(t.getName().equals(name)){ //account found
                        return t;  
                     }
                 } 

        } catch (RemoteException e) {
                e.printStackTrace();
        }
    	 throw new RejectedException("Account " + name + "does not exist");
    }

    //implements interface
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

    //list all products available on the market.
    @Override
    public List<String> listProducts() throws RemoteException {
    	List <String> out = new ArrayList<>();
    	for(Item i : items){
            out.add("Name: " + i.name() + " Price: " + i.price());
    	}
        return out;
    }	

    //implements interface
    @Override
    public Item buy(Item item) throws RemoteException, RejectedException {
        
        Account buyer = bank.getAccount(item.trader().getName());
        if(buyer == null) {
            throw new RejectedException("You have no bank account");
        }
            int i = 0;
            for(Item it: items) {
                if(it.name().equals(item.name()) && it.price() <= item.price()) {
                    buyer.withdraw(it.price());
                    bank.getAccount(it.trader().getName()).deposit(it.price());
                    Item temp  = items.remove(i);
                    temp.trader().itemSold(temp);
                    return temp;
                }
                i++;
            }
            throw new RejectedException("Item could not be purchased");
    }

    //implements interface
    @Override
    public void wish(Item item) throws RemoteException, RejectedException 
    {
            for(Item it: items) 
            {
                if(it.name().equals(item.name()) && it.price() <= item.price()) 
                {
                    it.trader().wishMatched(it);
                    return;
                }
            }
		wishlist.add(item);
    }

    //implements interface
    @Override
    public void sell(Item item) throws RemoteException {

        for(Item it: wishlist) {
            
            if(item.name().equals(it.name())  && item.price() <= it.price()) {
                it.trader().wishMatched(item);
            }  
        }
        items.add(item);
    }
}    