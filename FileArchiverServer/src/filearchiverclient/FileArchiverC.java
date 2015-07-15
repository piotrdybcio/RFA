/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filearchiverclient;

import com.healthmarketscience.rmiio.RemoteInputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 *
 * @author Piotr
 */
public interface FileArchiverC extends Remote {
    public void getFile(String fileName, RemoteInputStream inFile) throws RemoteException;
    public void metodaKlienta(String sTest) throws RemoteException;
    public void register_info(String s) throws RemoteException; // Zwrotny komunikat o rejestracji
    public void login_info(String s, String pathsend) throws RemoteException; // Zwrotny komunikat o logowaniu
    public void send_info(String s) throws RemoteException; // Zwrotny komunikat o przesłaniu pliku
    public void delete_info(String s) throws RemoteException; // Zwrotny komunikat o usunięciu pliku
     public void metodaS(String list[]) throws RemoteException;
     public void server_file_name_info(String[] s, int iX, int iY) throws RemoteException; // Przesyłanie tablicy nazw plików znajdujących się na serwerze

     
}
