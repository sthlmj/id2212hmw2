
package se.kth.id2212.ex2.bankrmi;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
/**
 *TODO: This is the servant class for the TraderAcc interface. It is similar to the AccountImpl
 * @author joehulden
 */
@SuppressWarnings("serial")
public class TraderAccImpl extends UnicastRemoteObject implements TraderAcc {
    
    private float balance = 0;
    private String name;
    
    private float price;
    private String itemName;
    
    //constructs a persistently named object.
    public TraderAccImpl(String name) throws RemoteException {
        super();
        this.name = name;
    }
    
    public String getName(){
    	return this.name;
    }
    
    @Override
    public boolean equals(Object obj){
        System.out.println("anropas ");
    	if(obj instanceof TraderAcc){
    		return ((TraderAccImpl) obj ).name.equals(this.name);
    	}
    	return false;
    }

/*
    @Override
    public synchronized void deposit(float value) throws RemoteException,
                                                         RejectedException {
        if (value < 0) {
            throw new RejectedException("Rejected: Account " + name + ": Illegal value: " + value);
        }
        balance += value;
        System.out.println("Transaction: Account " + name + ": deposit: $" + value + ", balance: $"
                           + balance);
    }
*/
    
/*
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
*/



	@Override
	public void itemSold(Item item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void wishMatched(Item item) {
		// TODO Auto-generated method stub
		
	}
}

/*
package se.kth.id2212.ex2.bankrmi;


//AccountImpl.java is the servant class serving Account.java


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("serial")
// Declaring the remote object
public class AccountImpl extends UnicastRemoteObject implements Account {
    private float balance = 0;
    private String name;

    
    //Constructs a persistently named object.

    public AccountImpl(String name) throws RemoteException {
        super();
        this.name = name;
    }

    @Override
    public synchronized void deposit(float value) throws RemoteException,
                                                         RejectedException {
        if (value < 0) {
            throw new RejectedException("Rejected: Account " + name + ": Illegal value: " + value);
        }
        balance += value;
        System.out.println("Transaction: Account " + name + ": deposit: $" + value + ", balance: $"
                           + balance);
    }

    @Override
    public synchronized void withdraw(float value) throws RemoteException,
                                                          RejectedException {
        if (value < 0) {
            throw new RejectedException("Rejected: Account " + name + ": Illegal value: " + value);
        }
        if ((balance - value) < 0) {
            throw new RejectedException("Rejected: Account " + name
                                        + ": Negative balance on withdraw: " + (balance - value));
        }
        balance -= value;
        System.out.println("Transaction: Account " + name + ": withdraw: $" + value + ", balance: $"
                           + balance);
    }

    @Override
    public synchronized float getBalance() throws RemoteException {
        return balance;
    }
}

*/
