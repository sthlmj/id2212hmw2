/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.id2212.ex2.bankrmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author guuurris
 */
public class ItemImpl extends UnicastRemoteObject implements Item  {
        private TraderAcc trader;
	private String name;
	private float price;
	
        
	public ItemImpl(String name, float price) throws RemoteException{
		this.name = name;
		this.price = price;
	}
	
	public ItemImpl(TraderAcc trader, String name, float price) throws RemoteException{
                this.trader = trader;
		this.name = name;
		this.price = price;
	}
	
	public String name() {
		return name;
	}

	public float price() {
		return price;
	}
	
	@Override
	public boolean equals(Object obj){
            System.out.println("jämnförs");
		if(obj instanceof ItemImpl){
			return name.equalsIgnoreCase(((ItemImpl) obj).name()) && price == ((ItemImpl) obj).price();
		}
		return false;
	}

        @Override
        public TraderAcc trader()  {
            return trader;
        }
    
}
