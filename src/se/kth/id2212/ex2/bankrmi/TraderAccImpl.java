
package se.kth.id2212.ex2.bankrmi;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
/**
 *TODO: This is the servant class for the TraderAcc interface. It is similar to the AccountImpl
 * @author joehulden
 */
@SuppressWarnings("serial")
public class TraderAccImpl  implements TraderAcc,Serializable  {
    
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

	@Override
	public void itemSold(Item item) throws RemoteException {
		System.out.println("Item sold: " + item.name()  + " for " + item.price());
	}
        
	@Override
	public void wishMatched(Item item) throws RemoteException {
		System.out.println("Whished item bought: " + item.name() + " for " + item.price());
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
