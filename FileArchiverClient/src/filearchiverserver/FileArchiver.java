/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filearchiverserver;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteOutputStream;
import filearchiverclient.FileTreeFrame;
import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 *
 * @author Piotr
 */

public interface FileArchiver extends Remote {
    public String echo(String input) throws RemoteException;
    public void sendFile(String userName, String fileName, RemoteInputStream inFile, String MD5Hash) throws RemoteException; // Wysyłąnie pliku
        public void sendFileNewVersion(String userName, String fileName, RemoteInputStream inFile, String MD5Hash) throws RemoteException; // Wysyłąnie nowej wersji pliku
    public void getFile(String fileName, RemoteOutputStream outFile) throws RemoteException;
    public void getFileServer(String choosed_file, String username) throws RemoteException; // Wybrany plik do pobrania i Użytkownik
    public void deleteFileServer(String choosed_file, String username) throws RemoteException; // Wybrany plik do usunięcia na serwerze
   
    public void getServerNameFiles(String userName) throws RemoteException; // Metoda Serwera pobierająca nazwy plików
    public void test() throws RemoteException;
    public void registerUser(String username, String pass, String client_paths) throws RemoteException; // Funkcja rejestrująca użytkownia
    public void readANDcompareFile(String username, String pass) throws RemoteException; // Funkcja odczytująca hasło z pliku, i porównuje hasło z hasłem otrzymanym od urzytkownika
     public void dataAboutFiles(File file)throws RemoteException;
     
}