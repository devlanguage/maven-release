package org.basic.net.c11_rmi.flight2;

import java.rmi.*;

public interface FlightFactory extends Remote{
  public Flight getFlight(String flightNumber)throws RemoteException;
}


