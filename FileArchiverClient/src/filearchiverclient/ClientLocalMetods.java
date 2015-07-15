/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filearchiverclient;

import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import filearchiverserver.FileArchiver;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Piotr
 */
public class ClientLocalMetods {
    
    FileArchiver service = null;
    FileArchiverClient FAC;
    login login;
    window window;
    public static String path;
    public static String copy_path = null;
    
    static MessagePanel MsgPanel = new MessagePanel();
    public static String[] server_file_name_to_see;
    
    public static String md5 = "";
    public static String sha1 = "";
    public static String md5_to_save = "";
    public static String md5_to_check ="";
    

    
            public static void md5file(FileInputStream fis) throws IOException{
        md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
        md5_to_check=md5;
        System.out.println("MD5 Hash\n"+md5);
        fis.close();
        //return md5;
    }
    
    /*
    WYSYŁANIE PLIKU
    */
    public void GetandSendFile(String userName, String Path, String nameFile) throws NotBoundException, MalformedURLException, IOException{
    try {  
             String localhost = login.jTextField1.getText();
             String port_rmi = login.jTextField2.getText();
            System.out.println("TEST " +localhost+":"+port_rmi);
            service = (FileArchiver) Naming.lookup("rmi://"+localhost+":"+port_rmi+"/testrmiio");  
               // String rootPath = "D:";
               // System.out.println("TEST");
            //String rootPath = System.getProperty("D:/");  
           // System.out.println("rootPath "+ rootPath);
            System.out.println("TEST");
            System.out.println("Path" + Path +" nameFile" + nameFile);
            String filePath = Path + File.separator+ nameFile; // File.separator + "GPU-Z.0.7.9.exe";  
            System.out.println("filePath "+ filePath);
            InputStream inStream = new FileInputStream(filePath);  
            System.out.println("TEST");
            RemoteInputStreamServer remoteFileData = new SimpleRemoteInputStream(inStream); 
            System.out.println("TEST "+remoteFileData );
            MsgPanel.append(Color.BLUE, "Rozpoczynam przesyłąnie pliku " + filePath);
            FileInputStream fis = new FileInputStream(new File(filePath));
            md5file(fis);
            System.out.println("MD5 original " + md5);
            service.sendFile(userName,nameFile, remoteFileData.export(), md5);
            
            MsgPanel.append(Color.GREEN.darker(), "Przesyłąnie pliku " + filePath+" zakończone pomyslnie :)");
            System.out.println("DUPA WSZYSTKO OK");
            md5_to_save = md5 ;
            md5 = null;
        } catch (RemoteException | FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            System.out.println("DUPA przy połączeniu " + e);
            e.printStackTrace();  
            
        }
    }
    
    
    /*
    WYSYŁANIE NOWEJ WERSJI PLIKU
    */
     public void GetandSendFileNewVersion(String userName, String Path, String nameFile) throws NotBoundException, MalformedURLException, IOException{
    try {  
             String localhost = login.jTextField1.getText();
             String port_rmi = login.jTextField2.getText();
            System.out.println("DUPA1 " +localhost+":"+port_rmi);
            service = (FileArchiver) Naming.lookup("rmi://"+localhost+":"+port_rmi+"/testrmiio"); 
               // String rootPath = "D:";
               // System.out.println("DUPA1");
            //String rootPath = System.getProperty("D:/");  
           // System.out.println("rootPath "+ rootPath);
            System.out.println("TEST");
            System.out.println("Path" + Path +" nameFile" + nameFile);
            String filePath = Path + File.separator+ nameFile; // File.separator + "GPU-Z.0.7.9.exe";  
            System.out.println("filePath "+ filePath);
            InputStream inStream = new FileInputStream(filePath);  
            System.out.println("TEST");
            RemoteInputStreamServer remoteFileData = new SimpleRemoteInputStream(inStream); 
            System.out.println("TEST "+remoteFileData );
            MsgPanel.append(Color.BLUE, "Rozpoczynam przesyłąnie pliku " + filePath);
            FileInputStream fis = new FileInputStream(new File(filePath));
            md5file(fis);
            System.out.println("MD5 original " + md5);
            service.sendFileNewVersion(userName,nameFile, remoteFileData.export(), md5);
            
            MsgPanel.append(Color.GREEN.darker(), "Przesyłąnie pliku " + filePath+" zakończone pomyslnie :)");
            System.out.println("TEST WSZYSTKO OK");
            md5_to_save = md5 ;
            md5 = null;
        } catch (RemoteException | FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            System.out.println("TEST przy połączeniu " + e);
            e.printStackTrace();  
            
        }
    }
     
     /*
     Funkcja żądająca od Serwera przesłanie jej plików
     */
     public void get_Server_name_File(String userName) throws NotBoundException, MalformedURLException, RemoteException{
         try{
         String localhost = login.jTextField1.getText();
             String port_rmi = login.jTextField2.getText();
            System.out.println("TEST " +localhost+":"+port_rmi);
            service = (FileArchiver) Naming.lookup("rmi://"+localhost+":"+ port_rmi +"/testrmiio");  
            service.getServerNameFiles(userName);
            
            FileArchiverClient fac = new FileArchiverClient();
            Context context7 = null;
            try {
                context7 = new InitialContext();
                } catch (NamingException ex) {
                    Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                }
            try { 
                context7.rebind("rmi:MyClientObject", fac);
                } catch (NamingException ex) {
                    Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                }
            
         }catch(Exception e){
             e.printStackTrace();
         }
         
         
     }
     
     
     public void choose_file(String choose_file, String user_name){
         try{
         String localhost = login.jTextField1.getText();
             String port_rmi = login.jTextField2.getText();
            System.out.println("TEST " +localhost+":"+port_rmi);
            service = (FileArchiver) Naming.lookup("rmi://"+localhost+":"+port_rmi+"/testrmiio");   
           // service.getServerNameFiles(user_Name);
            service.getFileServer(choose_file, user_name);
            
            FileArchiverClient fac = new FileArchiverClient();
            Context context14 = null;
            try {
                context14 = new InitialContext();
                } catch (NamingException ex) {
                    Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                }
            try { 
                context14.rebind("rmi:MyClientObject", fac);
                } catch (NamingException ex) {
                    Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                }
            
         }catch(Exception e){
             e.printStackTrace();
         }
         
     }
     
     public void delete_file(String choose_file, String user_name){
         Object[] options = {"Yes, please",
                    "No way!"};
int n = JOptionPane.showOptionDialog(window,
    "Czy na pewno chcesz usunąć wybrany plik? \n"+choose_file,
    "Delete Questrion",
    JOptionPane.YES_NO_OPTION,
    JOptionPane.QUESTION_MESSAGE,
    null,     //do not use a custom Icon
    options,  //the titles of buttons
    options[1]); //default button title
        //JOptionPane.showMessageDialog(window, "WYBRANO OPCJĘ " + n);
if(n==0){
         try{
         String localhost = login.jTextField1.getText();
             String port_rmi = login.jTextField2.getText();
            System.out.println("TEST " +localhost+":"+port_rmi);
            service = (FileArchiver) Naming.lookup("rmi://"+localhost+":"+port_rmi+"/testrmiio");  
           // service.getServerNameFiles(user_Name);
            service.deleteFileServer(choose_file, user_name);
            
            FileArchiverClient fac = new FileArchiverClient();
            Context context19 = null;
            try {
                context19 = new InitialContext();
                } catch (NamingException ex) {
                    Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                }
            try { 
                context19.rebind("rmi:MyClientObject", fac);
                } catch (NamingException ex) {
                    Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                }
            
         }catch(Exception e){
             e.printStackTrace();
         }
}else{
    MsgPanel.append(Color.BLACK, "ZREZYGNOWANO Z USUNIĘCIA");
}
         
     }
     public void actualizeMessage(){
     MsgPanel.append(Color.GREEN, "ZAKTUALIZOWANO LISTĘ PLIKÓW NA SERWERZE");
     };
      public void actualizeMessageC(){
     MsgPanel.append(Color.GREEN, "ZAKTUALIZOWANO LISTĘ PLIKÓW NA SERWERZE ORAZ W FOLDERZE UŻYTKOWNIKA");
     };
     public void refreshMessage() {
        MsgPanel.append(Color.BLUE, "Odświeżono listę plików użytkownika!");
    }
    
    public void doit(String str){
        System.out.println("str" + str);
        String str1 = str;
        //JOptionPane.showMessageDialog(FAC.login , str1);
        //MsgPanel.append(Color.BLUE, str);
       FAC.login.jLabel13.setText(""+str); 
       FAC.login.jTextField4.setText("");
       FAC.login.jPasswordField1.setText("");
       FAC.login.jPasswordField2.setText("");
   }
    public void doit2(String str2, String pathsended){
        System.out.println("str" + str2);
        String str1 = str2;
        //JOptionPane.showMessageDialog(FAC.login , str1);
        //MsgPanel.append(Color.BLUE, str);
       login.jLabel14.setText(""+str1); 
       login.jTextField5.setText("");
       login.jPasswordField3.setText("");
        System.out.println("pathsended " + pathsended);
       path = pathsended;
       copy_path = pathsended;
        System.out.println("path !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + path);
      // FAC.login.jPasswordField2.setText("");
   }
    
    public void doit3(String str3){
        System.out.println("str" + str3);
        String str4 = str3;
        JOptionPane.showMessageDialog(FAC.window , str4);
        //MsgPanel.append(Color.BLUE, str);
        System.out.println("########################################" + str4);
        MsgPanel.append(Color.red, str4);
        System.out.println("########################################" + str4);
//       FAC.login.jLabel13.setText(""+str); 
//       FAC.login.jTextField4.setText("");
//       FAC.login.jPasswordField1.setText("");
//       FAC.login.jPasswordField2.setText("");
   }
    public void doit5(String str3){
        System.out.println("str" + str3);
        String str4 = str3;
        System.out.println("########################################" + str4);
        MsgPanel.append(Color.red, str4);
        //JOptionPane.showMessageDialog(FAC.window , str4);
        //MsgPanel.append(Color.BLUE, str);
        
       // MsgPanel.append(Color.red, str4);
        System.out.println("########################################" + str4);
//       FAC.login.jLabel13.setText(""+str); 
//       FAC.login.jTextField4.setText("");
//       FAC.login.jPasswordField1.setText("");
//       FAC.login.jPasswordField2.setText("");
   }
    
    public void doit4(String[] str, int X, int Y){
      //  System.out.println("str" + str3);
      //  String str4 = str3;
        server_file_name_to_see = new String[X];
        server_file_name_to_see = str;

   }
    
    
    
    
    /**
     * Funkcja kopiująca plik z dysku, do folderu Clienta
     * @param source Parametr określający źródło (element startowy)
     * @param dest Miejsce elementu docelowego
     * @throws IOException 
     */
    public static void copyFileUsingApacheCommonsIO(File source, File dest) throws IOException {
    FileUtils.copyFile(source, dest);
}
    //////////////////////////////////////////////
    public void GetFileInfoList(String userName,File file) throws NotBoundException, MalformedURLException, IOException{
    try {  
             System.out.println("Request of getting data about files in serwer location");
            service = (FileArchiver) Naming.lookup("rmi://"+login.localhost+":"+ login.RMIport +"/testrmiio");  
            service.dataAboutFiles(file);
    }catch(Exception e){
        e.printStackTrace();
    }
            
          

    }
    
    //////////////////////////////////////////////

    
}
