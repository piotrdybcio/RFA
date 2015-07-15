/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filearchiverserver;

import filearchiverclient.FileArchiverC;
import static filearchiverserver.FileArchiverServer.filePathchoose;
import static filearchiverserver.FileArchiverServer.users_name;
import static filearchiverserver.window.MsgPanel;
import static filearchiverserver.window.doPolaczenia;
import static filearchiverserver.window.jTextField1;
import static filearchiverserver.window.jTextField2;
import static filearchiverserver.window.port_create_registry;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.commons.io.IOUtils;


/**
 *
 * @author Piotr
 */
public class ServerLocalMetods {
    FileArchiverServer FAS;
    FileArchiverServer e = null;
    String RMIname = "OPA";
    window window;
    public static Thread doPolaczenia;
    
    public void connecting(){
        
        doPolaczenia=new Thread(){
        public void run(){
            try {
        FAS.filePathchoose = window.jTextField1.getText();
        String temp_port = jTextField2.getText();
        String localhost = window.jLabel4.getText();
        System.out.println("localhost: "+localhost);
        port_create_registry = parseInt(temp_port);
        System.setSecurityManager(new SecurityManager());
        try {
            e = new FileArchiverServer();
            LocateRegistry.createRegistry(port_create_registry);
            //reg.rebind("ECHO-SERVER", e);
            Naming.rebind("rmi://"+ localhost +":"+port_create_registry+"/"+RMIname, e);  
              
            System.out.println("Serwer started");
            System.out.println("Server started");
           
            //System.out.println(reg);
            
            window.jButton1.setEnabled(true);
            window.jButton3.setEnabled(false);
            MsgPanel.append(Color.GREEN.darker(), "Wybrana lokalizacja Serwera ["+FAS.filePathchoose+"]");
            System.out.println(localhost);
            MsgPanel.append(Color.GREEN.darker(), "Uruchomiono Serwer ["+localhost+":"+port_create_registry+"]");
            
        } catch (RemoteException | MalformedURLException x) {
            System.out.println("Server jest już uruchomiony\n"+x.toString());
            MsgPanel.append(Color.RED, "Serwer jest już uruchomiony");
        }
         
        
            }catch(NumberFormatException e) {  
            // TODO Auto-generated catch block  
            System.out.println("DUPA przy połączeniu " + e);
            e.printStackTrace();
            MsgPanel.append(Color.RED, "Błąd przy uruchamianiu Serwera (Thread)");
            }
            }
       
    };//koniec wątku
              doPolaczenia.start();  
                }
    
    public void register_answer(String s) throws RemoteException {
        
        String localhost = window.jLabel4.getText();
        try {
            String url = "rmi://"+ localhost +"/";
            Context context4 = new InitialContext();
            FileArchiverC myClientInt4 = (FileArchiverC)
                    context4.lookup(url + "MyClientObject2");
            myClientInt4.register_info(s);
        } catch (NamingException ex) {
            Logger.getLogger(ServerLocalMetods.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void send_answer(String s) throws RemoteException {
        
        String localhost = window.jLabel4.getText();
        try {
            String url = "rmi://"+ localhost +"/";
            Context context6 = new InitialContext();
            FileArchiverC myClientInt6 = (FileArchiverC)
                    context6.lookup(url + "MyClientObject2");
            myClientInt6.send_info(s);
        } catch (NamingException ex) {
            Logger.getLogger(ServerLocalMetods.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete_answer(String s) throws RemoteException {
        
        String localhost = window.jLabel4.getText();
        try {
            String url = "rmi://"+ localhost +"/";
            Context context36 = new InitialContext();
            FileArchiverC myClientInt36 = (FileArchiverC)
                    context36.lookup(url + "MyClientObject2");
            myClientInt36.delete_info(s);
        } catch (NamingException ex) {
            Logger.getLogger(ServerLocalMetods.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void login_answer(String s, String pathsend) throws RemoteException {
        
        String localhost = window.jLabel4.getText();
        try {
            String url = "rmi://"+ localhost +"/";
            Context context5 = new InitialContext();
            FileArchiverC myClientInt5 = (FileArchiverC)
                    context5.lookup(url + "MyClientObject2");
            myClientInt5.login_info(s, pathsend);
        } catch (NamingException ex) {
            Logger.getLogger(ServerLocalMetods.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void server_file_name_answer(String[] server_file_name ,int wymiarX, int wymiarY) throws RemoteException {
        
        String localhost = window.jLabel4.getText();
        try {
            String url = "rmi://"+ localhost +"/";
            Context context5 = new InitialContext();
            FileArchiverC myClientInt5 = (FileArchiverC)
                    context5.lookup(url + "MyClientObject");
            myClientInt5.server_file_name_info(server_file_name, wymiarX, wymiarY);
            
        } catch (NamingException ex) {
            Logger.getLogger(ServerLocalMetods.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
        public void check_password() throws FileNotFoundException, IOException{
        
                try(BufferedReader br = new BufferedReader(new FileReader(filePathchoose+File.separator+ users_name+File.separator+".security"+File.separator+users_name+".pass"))) {
                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();

                    while (line != null) {
                        sb.append(line);
                        sb.append(System.lineSeparator());
                        line = br.readLine();
                    }
                    String readpass = sb.toString();
                    System.out.println("everything "+readpass);
                }catch(IOException e){
                    e.printStackTrace();
                }
        }
        
        public static String md5 = "";
        
    public static void md5file(FileInputStream fis) throws IOException{
        md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
        System.out.println("MD5 Hash\n"+md5);
        fis.close();
        //return md5;
    }
        
      };
        
    
    

