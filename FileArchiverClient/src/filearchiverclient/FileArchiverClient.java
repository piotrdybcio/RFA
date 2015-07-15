/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filearchiverclient;
import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import com.healthmarketscience.rmiio.RemoteInputStreamServer;

import com.healthmarketscience.rmiio.SimpleRemoteInputStream;

import filearchiverserver.FileArchiver;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

import java.rmi.Naming;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import java.rmi.server.UnicastRemoteObject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.lang.SecurityManager;


/**
 *
 * @author Piotr
 * @version 0.0.1 Przykładowy client RMI
 * 
 */
public class FileArchiverClient extends UnicastRemoteObject implements FileArchiverC {
    
    private static final long serialVersionUID = 1L;
    public static window window ;
    static ClientLocalMetods CLM = new ClientLocalMetods();
    public static login login;
    public String list[];
   // FileArchiver service = null;
    
    private void FileArchiverClient1() {
        try {
            System.out.println("TEST");
           // Registry reg = LocateRegistry.getRegistry(1099);
            //FileArchiver server = (FileArchiver) reg.lookup("ECHO-SERVER");
            System.out.println("Connected to Server");
            Remote remote = Naming.lookup("ECHO-SERVER");
            System.out.println("TEST");
            FileArchiver server = null;
            System.out.println("TEST");
            if (remote instanceof FileArchiver)
                server = (FileArchiver) remote;
            String result = server.echo("Hello server");
            System.out.println("TEST");
            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    // Metoda pobierająca plik z serwera
    public void getFile(String fileName, RemoteInputStream inFile) throws RemoteException {  
        try {  
            System.out.println("fileName: "+fileName+"\n inFile "+inFile);
            InputStream inStream = RemoteInputStreamClient.wrap(inFile);  
            System.out.println("inStream " + inStream);  
            System.out.println("Rozpoczynam pobieranie - przesyłanie pliku");
            store(inStream, "server_" + fileName);// File.separator + fileName);  
            System.out.println("Przesyłanie zakończone");  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }
        

        
            /**
             * 
             * @param inStream
             * @param filePath 
             */
            public static void store(InputStream inStream, String filePath) {  
        try {  
            BufferedInputStream inStreamBuff = new BufferedInputStream(inStream);     
            FileOutputStream fileOutStream;  
            String fileP = window.user_local_path;
            String filePath1 = fileP+File.separator ;//+ filePath;//"C:\\Users\\Piotr\\Desktop\\Client\\";
            System.out.println("ŚCIEŻKA DO ZAPISU " +filePath1+filePath );
            fileOutStream = new FileOutputStream(filePath1+filePath);  
              
            int size = 0;  
            byte[] byteBuff = new byte[1024];  
            while ( (size = inStreamBuff.read(byteBuff)) != -1) {  
                fileOutStream.write(byteBuff, 0, size);  
            }  
          
            fileOutStream.close();  
            inStreamBuff.close();  
            if((filePath1+filePath).isEmpty() == false){
              //  JOptionPane.showMessageDialog(window, "Przywrócono plik");
                window.MsgPanel.append(Color.red, "Przywrócono plik");
            }
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }   
    } 
        //Koniec metody
    
            
    public static void sendFileClient(){
        try {  
            FileArchiver service = (FileArchiver) Naming.lookup("rmi://localhost:1099/testrmiio");  
              //  String rootPath = "D:";
            String rootPath = System.getProperty("D:/");  
            System.out.println("rootPath "+ rootPath);
            
           
            String filePath = "C:\\Users\\Piotr\\Downloads" + File.separator + "Triumfalna-Turkawka-14.04PL-DVD-amd64.iso";  
            System.out.println("filePath "+ filePath);
            InputStream inStream = new FileInputStream(filePath);  
               System.out.println("TEST");
            RemoteInputStreamServer remoteFileData = new SimpleRemoteInputStream(inStream);  
            service.sendFile("test","Triumfalna-Turkawka-14.04PL-DVD-amd64.iso", remoteFileData.export(), "32561");  
              
        } catch (MalformedURLException | RemoteException | NotBoundException | FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }
    
    //nic nie robi teraz, czyta pliki z lokalizacji
//    public static void readFile() throws IOException{
//        Files.walk(Paths.get("C:\\Users\\Piotr\\Downloads")).forEach(filePath -> {
//                if (Files.isRegularFile(filePath)) {
//                    System.out.println(filePath);
//                }
//            });
//    }
    
    
    public void connecting() throws NotBoundException, MalformedURLException, IOException{
         try {  
             System.out.println("TEST");
            FileArchiver service = (FileArchiver) Naming.lookup("rmi://localhost:1099/testrmiio");  
                String rootPath = "D:";
                System.out.println("TEST");
            //String rootPath = System.getProperty("D:/");  
            System.out.println("rootPath "+ rootPath);
            System.out.println("TEST");
           
            String filePath = "C:\\Users\\Piotr\\Downloads" + File.separator + "GPU-Z.0.7.9.exe";
            
            System.out.println("filePath "+ filePath);
            InputStream inStream = new FileInputStream(filePath);  
               System.out.println("TEST");
            RemoteInputStreamServer remoteFileData = new SimpleRemoteInputStream(inStream); 
            System.out.println("TEST "+remoteFileData );
            service.sendFile("","GPU-Z.0.7.9.exe", remoteFileData.export(), "5246");  
              System.out.println("TEST");
        } catch (RemoteException | FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        
    }
    public void metodaKlienta(String sTest) throws RemoteException{
    System.out.print("\nmetoda klienta wywolana przez serwer\n"+sTest+"\n");
    };
    
    public void register_info(String s) throws RemoteException{
       // JOptionPane.showMessageDialog(login , "Dupa", "Info", JOptionPane.PLAIN_MESSAGE);
         CLM.doit(s); //Wywietla tekst w jLabel [Status rejestracji]
    }
    
    public void login_info(String s, String pathsending) throws RemoteException{ // Zwrotny komunikat o logowaniu
    
        CLM.doit2(s, pathsending); //Wywietla tekst w jLabel [Status zalogowania] 
        pathsending = null;
    }
    public void send_info(String s) throws RemoteException{ // Zwrotny komunikat o przesłaniu pliku
        CLM.doit3(s);
    }
    public void delete_info(String s) throws RemoteException{ // Zwrotny komunikat o usunięciu pliku
        CLM.doit5(s);
    }
    
    public void server_file_name_info(String[] s, int X, int Y) throws RemoteException{ // Przesyłanie tablicy nazw plików znajdujących się na serwerze
        CLM.doit4(s, X, Y);
    }
    
    public FileArchiverClient() throws RemoteException {
    super();
    }
    
    /**
     * 
     * @param args
     * @throws UnknownHostException 
     */
    public static void main(String[] args) throws UnknownHostException, IOException, NamingException, NotBoundException {
        // TODO code application logic here
       // System.setProperty("java.security.policy","file:///home/.../<filename>.policy");
        try{
            System.setProperty("java.security.policy", "file:./src/security/java.policy");
            System.setSecurityManager(new SecurityManager());
        }
        catch(SecurityException e){
            System.err.println("Security violation " + e);
        }
        login = new login();
        login.setVisible(true);
        //CLM = new ClientLocalMetods();
        
        //readFile();
//        window.fileinfolder();
        
        System.out.println("TEST");
        
        /**
         *  Wymiary monitora, rozdzielczości
         */
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        System.out.println("width "+width+" height "+height);
        
    
        
        FileArchiverClient fac = new FileArchiverClient();
            Context context = new InitialContext();
            context.rebind("rmi:MyClientObject2", fac); 
//            FileArchiver tester = (FileArchiver) Naming.lookup("rmi://192.168.1.100:1099/testrmiio");
//            tester.test();
//            context=null;
        
    }

   
    public void metodaS(String list[]) throws RemoteException {
        this.list=list;
    }
    
}
