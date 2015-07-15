/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filearchiverserver;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.healthmarketscience.rmiio.RemoteOutputStream;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import filearchiverclient.FileArchiverC;
import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.DefaultListModel;


/**
 *
 * @author Piotr
 */
public class FileArchiverServer extends UnicastRemoteObject implements FileArchiver {
	private static final long serialVersionUID = 7L;
        public static window window;
        public static String users_name = null;
        public static String filePathchoose = null;
        public String[] file_name_from_Server;
        static MessagePanel MsgPanel = new MessagePanel();
        static ServerLocalMetods SLM = new ServerLocalMetods();
        static DefaultListModel<String> przykl;
        public String nazwy[];// = new String();
        public static int it=0;
     
        /**
         * 
         * @param input
         * @return
         * @throws RemoteException 
         */
	public String echo(String input) throws RemoteException {
	System.out.println("from client: [" + input+ "]");        
	return "Echo: " + input;
    }
        /**
         * 
         * @param fileName
         * @param inFile
         * @throws RemoteException 
         */
        public void sendFile(String userName, String fileName, RemoteInputStream inFile, String MD5Hash) throws RemoteException {  
        try {  
            System.out.println("fileName: "+fileName+"\n inFile "+inFile);
            InputStream inStream = RemoteInputStreamClient.wrap(inFile);  
            System.out.println("inStream " + inStream);  
            System.out.println("Rozpoczynam pobieranie - przesyłanie pliku");
            store(userName,inStream, "temp_" + fileName);//+ File.separator + fileName);
            System.out.println("USERNAME" + userName);
            String filetocheck = filePathchoose+File.separator+userName+File.separator+"temp_" + fileName;
            FileInputStream fis = new FileInputStream(new File(filetocheck));
            SLM.md5file(fis);
            System.out.println("Check sum send "+SLM.md5);
            System.out.println("Check sum original "+ MD5Hash);
            if(SLM.md5.equals(MD5Hash) == true){ // Zmiana nazwy
                System.out.println("UDAŁO SIĘ PLIK PRZESŁANO POPRAWNIE :) ");
                File oldfile =new File(filePathchoose+File.separator+userName+File.separator+"temp_" + fileName);
		File newfile =new File(filePathchoose+File.separator+userName+File.separator+ fileName);
                if(oldfile.renameTo(newfile)){
			System.out.println("Rename succesful " + filePathchoose+File.separator+userName+File.separator+ fileName );
                        
                        SLM.send_answer("Plik "+ fileName+" przesłano pomyślnie");
                        
		}else{
			System.out.println("Rename failed");
                        oldfile.delete();
                        SLM.send_answer("Przesyłanie pliku nie przebiegło pomyślnie\nZostał on usunięty");
		}
            }else{
                System.out.println("BŁĄD W TRANSMISJI PLIKU :( ");
            }
            System.out.println("Przesyłanie zakończone");  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }
        
            public void sendFileNewVersion(String userName, String fileName, RemoteInputStream inFile, String MD5Hash) throws RemoteException{ // Wysyłąnie nowej wersji pliku
            
                try {  
            System.out.println("fileName: "+fileName+"\n inFile "+inFile);
            InputStream inStream = RemoteInputStreamClient.wrap(inFile);  
            System.out.println("inStream " + inStream);  
            System.out.println("Rozpoczynam pobieranie - przesyłanie pliku");
            store(userName,inStream, "temp_new_" + fileName);//+ File.separator + fileName);
            System.out.println("USERNAME" + userName);
            String filetocheck = filePathchoose+File.separator+userName+File.separator+"temp_new_" + fileName;
            FileInputStream fis = new FileInputStream(new File(filetocheck));
            SLM.md5file(fis);
            System.out.println("Check sum send "+SLM.md5);
            System.out.println("Check sum original "+ MD5Hash);
            if(SLM.md5.equals(MD5Hash) == true){ // Zmiana nazwy
                System.out.println("UDAŁO SIĘ PLIK PRZESŁANO POPRAWNIE :) ");
                File todelate = new File(filePathchoose+File.separator+userName+File.separator+ fileName);
                if(todelate.delete() == true){System.out.println("USUNIĘTO Stary plik " + filePathchoose+File.separator+userName+File.separator+ fileName);}
                File oldfile =new File(filePathchoose+File.separator+userName+File.separator+"temp_new_" + fileName);
		File newfile =new File(filePathchoose+File.separator+userName+File.separator+ fileName);
                if(oldfile.renameTo(newfile)){
			System.out.println("Rename succesful " + filePathchoose+File.separator+userName+File.separator+ fileName );
                        
                        SLM.send_answer("Plik  "+ fileName+"  został pomyślnie zastąpiony nowym plikiem");
                        
		}else{
			System.out.println("Rename failed");
                        oldfile.delete();
                        SLM.send_answer("Aktualizacja pliku nie przebiegło pomyślnie\nKopia pliku została usunieta");
		}
            }else{
                System.out.println("BŁĄD W TRANSMISJI PLIKU :( ");
            }
            System.out.println("Przesyłanie zakończone");  
        } catch (IOException e) {  
            e.printStackTrace();  
        } 
            
            }
        
            
            public void getFile(String fileName, RemoteOutputStream outFile) throws RemoteException{
            
        }
        
            /**
             * 
             * @param inStream
             * @param filePath 
             */
            public static void store(String userName, InputStream inStream, String filePath) {  
        try {  
            BufferedInputStream inStreamBuff = new BufferedInputStream(inStream);     
            FileOutputStream fileOutStream;  
            String filePath1 = filePathchoose+userName+File.separator; //"C:\\Users\\Piotr\\Desktop\\";
            fileOutStream = new FileOutputStream(filePath1+filePath);  
              
            int size = 0;  
            byte[] byteBuff = new byte[1024];  
            while ( (size = inStreamBuff.read(byteBuff)) != -1) {  
                fileOutStream.write(byteBuff, 0, size);  
            }  
          
            fileOutStream.close();  
            inStreamBuff.close();  
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }   
    } 
            public void getServerNameFiles(String userName) throws RemoteException{ // Metoda Serwera pobierająca nazwy plików
               System.out.println("1 jestem tu");
                przykl = new DefaultListModel<String>();
                File directory_server=new File(filePathchoose+userName);
                System.out.println("1.5 jestem " + filePathchoose+userName);
                //policz(directory_server);
                System.out.print("it="+it);
                it=directory_server.listFiles().length;
                System.out.print("it="+it);
                 int X =it+1; // PRZYPISZ ROZMIAR TABLICY
                 int Y = 1; // PRZYPISZ ROZMIAR TABLICY
                 file_name_from_Server = new String[X];
               // System.out.print(nazwy.length+" dlugosc tablicy");
                nazwy=new String[it+1];
                //System.out.print(nazwy.length+" dlugosc tablicy");
                it=0;
                int iter=0;
                listFilesForFolder(directory_server);
                System.out.print("2 jestem");
                //==============================================================
                // TUTAJ WSTAW POBIERANIE NAZW PLIKÓW
                // userName - nazwa użytkownika
                // filePathchoose+userName+File.separator  - katalog danego użytkownika
                //==============================================================
//                int X = nazwy.length; // PRZYPISZ ROZMIAR TABLICY
//                int Y = 1; // PRZYPISZ ROZMIAR TABLICY
//                System.out.print(nazwy.length+" dlugosc tablicy");
                
                System.out.println(X+"-x y-"+Y);
               // file_name_from_Server = new String[X][Y];
                for(int i=0;i<X;i++){
                    System.out.println(X+"-x y-"+Y+ "  w forze");
                    file_name_from_Server[i]=nazwy[i];
                    System.out.println(file_name_from_Server[i]);
                };
                //file_name_from_Server = nazwy;
                SLM.server_file_name_answer(file_name_from_Server, X, Y);
                  
            }
 public void listFilesForFolder(final File folder) {//dziala super
    for (final File fileEntry : folder.listFiles()) {
        System.out.println("100 jestem isDirectory ? " +fileEntry.isDirectory() );
       // nazwy[it] = new String[it];
        //nazwy = new String[100];
        if (fileEntry.isDirectory()) {
             System.out.println("110 jestem ");
           listFilesForFolder(fileEntry);
        } else {
           przykl.addElement(fileEntry.getName());
           //String[] nazwy[it]=new String[it];
           // nazwy[it] = new String[];
            nazwy[it]=fileEntry.getName();
            ++it;
            System.out.println(fileEntry.getName()+" it="+it);
        }
    }
}
  public void policz(final File folder) {//dziala super
    for (final File fileEntry : folder.listFiles()) {
       // System.out.println("100 jestem isDirectory ? " +fileEntry.isDirectory() );
       // nazwy[it] = new String[it];
        //nazwy = new String[100];
        if (fileEntry.isDirectory()) {
             //System.out.println("110 jestem ");
           policz(fileEntry);
        } else {
           przykl.addElement(fileEntry.getName());
           //String[] nazwy[it]=new String[it];
          // nazwy[it] = new String[];
            //nazwy[it]=fileEntry.getName();
           ++it;
            System.out.println(fileEntry.getName()+" "+it);
        }
    }
}
            public void getFileServer(String choosed_file, String username) throws RemoteException{
            try {
                getFileServer1(choosed_file, username);
            } catch (NamingException ex) {
                Logger.getLogger(FileArchiverServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            
            public static void getFileServer1(String choosed_file, String username) throws NamingException{
        try {  
           // FileArchiverC service = (FileArchiverC) Naming.lookup("rmi://localhost:1099/testrmiio");  
              //  String rootPath = "D:";
            
            String localhost = window.jLabel4.getText();
            String url = "rmi://"+localhost+"/";
            Context context2 = new InitialContext();
            FileArchiverC myClientInt = (FileArchiverC) 
            context2.lookup(url + "MyClientObject");
   
            System.out.println("filechoosepath" + filePathchoose);
            
            String filePath = filePathchoose + File.separator + username + File.separator+ choosed_file;  
            System.out.println("filePath "+ filePath);
            InputStream inStream = new FileInputStream(filePath);  
               System.out.println("TEST");
            RemoteInputStreamServer remoteFileData = new SimpleRemoteInputStream(inStream);  
            myClientInt.getFile(choosed_file, remoteFileData.export());  
            

              
        } catch (RemoteException | FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }
            
            public void deleteFileServer(String choosed_file, String username) throws RemoteException{
            try {
                deleteFileServer1(choosed_file, username);
            } catch (NamingException ex) {
                Logger.getLogger(FileArchiverServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            
            
            public static void deleteFileServer1(String choosed_file, String username) throws NamingException{
        try {  
           // FileArchiverC service = (FileArchiverC) Naming.lookup("rmi://localhost:1099/testrmiio");  
              //  String rootPath = "D:";
            
            String localhost = window.jLabel4.getText();
            String url = "rmi://"+localhost+"/";
//            Context context20 = new InitialContext();
//            FileArchiverC myClientInt = (FileArchiverC) 
//            context20.lookup(url + "MyClientObject");
   
            System.out.println("filechoosepath" + filePathchoose);
            
            String filePath = filePathchoose + username + File.separator+ choosed_file;  
            
            System.out.println("filePath "+ filePath);
            File file_to_delete = new File(filePath);
           // file_to_delete.delete();
           // file_to_delete.delete();
            if(file_to_delete.delete()==true){
             SLM.delete_answer("PLIK USUNIĘTO POPRAWNIE"); 
             System.out.println("USUNIĘTO");
            }else{
              SLM.delete_answer("PLIK NIE ZOSTAŁ USUNIĘTY POPRAWNIE");     
             System.out.println("TEST A USUWANIEM");
            }
            //InputStream inStream = new FileInputStream(filePath);  
               System.out.println("TEST");
            //RemoteInputStreamServer remoteFileData = new SimpleRemoteInputStream(inStream);  
            
            

              
        } catch (RemoteException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }
            
            
            
            public static void store_get(OutputStream outStream, String filePath) {  
        try {  
            BufferedOutputStream outStreamBuff = new BufferedOutputStream(outStream);     
            FileInputStream fileInStream;  
            String filePath2 = filePathchoose; //"C:\\Users\\Piotr\\Desktop\\";
            fileInStream = new FileInputStream(filePath2+filePath);  
              
            int size = 0;  
            byte[] byteBuff = new byte[1024];  
          //  while ( (size = outStreamBuff.read(byteBuff)) != -1) {  
           //     fileInStream.write(byteBuff, 0, size);  
           // }  
          
            fileInStream.close();  
            outStreamBuff.close();  
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }   
    } 
      public void testhelp(String sTest) throws NotBoundException, MalformedURLException, RemoteException, NamingException{
      //metodaKlienta(sTest);
          
        String localhost = window.jLabel4.getText();
         // FileArchiverC tester = (FileArchiverC) Naming.lookup("rmi://localhost:1099/testrmiio");
          String url = "rmi://"+localhost+"/";
          Context context3 = new InitialContext();
    FileArchiverC myClientInt3 = (FileArchiverC) 
            context3.lookup(url + "MyClientObject");
         myClientInt3.metodaKlienta(sTest);
      };  
            
      public void test() throws RemoteException{
//            try {
//                String sTest="przeslanie testowe";
//                System.out.print(sTest);
//               // testhelp(sTest);
//            } catch (NotBoundException ex) {
//                Logger.getLogger(FileArchiverServer.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (MalformedURLException ex) {
//                Logger.getLogger(FileArchiverServer.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (NamingException ex) {
//                Logger.getLogger(FileArchiverServer.class.getName()).log(Level.SEVERE, null, ex);
//            }
      };   
      
      public void registerUser(String username, String pass, String client_paths) throws RemoteException{
      
            System.out.println("Path" + window.jTextField1.getText());
            String dir = window.jTextField1.getText();
            String name = username;
            users_name = username;
            String passtemp = pass;
            String pathtemp = client_paths;
            boolean success = (new File(dir+File.separator+name)).mkdirs();
            boolean success1 = (new File(dir+File.separator+name+File.separator+".security")).mkdirs();
            String dir_new = dir+File.separator+name+File.separator+".security";
            if (success) { // Tworzenie pliku z hasłem
                PrintWriter writer = null;
                try {
                    // Directory creation failed
                    MsgPanel.append(Color.GRAY, "Stworzono użytkownika " + name);
                    
                    writer = new PrintWriter(dir_new+File.separator+name+".pass", "UTF-8");
                    writer.println(passtemp);
                    //writer.println("The second line");
                    writer.close();
                    SLM.register_answer("Stworzono użytkownika " + name);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FileArchiverServer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(FileArchiverServer.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    writer.close();
                }
            }else{
                 MsgPanel.append(Color.RED, "Użytkownik " + name+" już istnieje!");
                 SLM.register_answer("Użytkownik " + name+" już istnieje!");
                 //System.out.println("Czytam hasło\n");
            }
            if (success1) { // Tworzenie pliku z ścieżką lokalizacji Clienta
                PrintWriter writer1 = null;
                try {
                    // Directory creation failed
                    MsgPanel.append(Color.GRAY, "Zapisano path użytkownia " + name + " do pliku :) ");
                    
                    writer1 = new PrintWriter(dir_new+File.separator+name+".path", "UTF-8");
                    writer1.println(pathtemp);
                    //writer.println("The second line");
                    writer1.close();
                    SLM.register_answer("Stworzono użytkownika " + name);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FileArchiverServer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(FileArchiverServer.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    writer1.close();
                }
            }else{
                 MsgPanel.append(Color.RED, "Użytkownik " + name+" już istnieje!");
                 SLM.register_answer("Użytkownik " + name+" już istnieje!");
                 //System.out.println("Czytam hasło\n");
            }
            
            
          
          
      }
      
      public void readANDcompareFile(String username, String pass) throws RemoteException{
          System.out.println("tester "+filePathchoose);
          try {
              BufferedReader br = new BufferedReader(new FileReader(filePathchoose+File.separator+username+File.separator+".security"+File.separator+username+".pass"));
                   // System.out.println("path read and compare " +br);
                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();

                    while (line != null) {
                        sb.append(line);
                       // sb.append(System.lineSeparator());
                        line = br.readLine();
                    }
                   // br.close();
                BufferedReader br1 = new BufferedReader(new FileReader(filePathchoose+File.separator+username+File.separator+".security"+File.separator+username+".path"));
                   // System.out.println("path read and compare " +br);
                    StringBuilder sb1 = new StringBuilder();
                    String line1 = br1.readLine();

                    while (line1 != null) {
                        sb1.append(line1);
                       // sb.append(System.lineSeparator());
                        line1 = br.readLine();
                    }
                    //br.close();   
                    
                    String readpass = sb.toString();
                    String path_read_from_file = sb1.toString();
                    System.out.println("everything "+readpass);
                    System.out.println("everything1 "+pass);
                    System.out.println("PATH USER" + path_read_from_file);
                    String pass1 = pass;
                    if(readpass.equalsIgnoreCase(pass1)== true ){
                        System.out.println("HASŁO POPRAWNE :) \n");
                        MsgPanel.append(Color.BLUE, "Zalogowano do Serwera [Użytkownik :"+username+"]");
                        SLM.login_answer("Zalogowano jako: "+username, path_read_from_file);
                        path_read_from_file = null;
                        System.out.println("everything "+readpass);
                            System.out.println("everything1 "+pass1);
                    }else{
                        
                        System.out.println("Coś nie tak z hasłem "+pass1);
                        path_read_from_file = null;
                        SLM.login_answer("Nie zalogowano! Sprawdź nazwę użytkownia i hasło!", path_read_from_file);
                        System.out.println("everything "+readpass);
                            System.out.println("everything1 "+pass1);
                    }
                    
                }catch(Exception e){
                    System.err.println("dupeeeek " +e);
                    //e.printStackTrace();

                }
          
          
      }

    public FileArchiverServer() throws RemoteException {
        super();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException, UnknownHostException {
        // TODO code application logic here
       // MsgPanel = new MessagePanel();
        
      //  System.out.println( System.getenv("PATH") );
        try{
            System.setProperty("java.security.policy", "file:./src/security/java.policy");
            System.setSecurityManager(new SecurityManager());
        }
        catch(SecurityException e){
            System.err.println("Security violation " + e);
        }
        window = new window();
        window.setVisible(true);
        
        
        
    }

    @Override
    public void dataAboutFiles(File file) throws RemoteException {
            
//        if (file == null)
//      return "";
//    StringBuffer buffer = new StringBuffer();
//    buffer.append("Name: " + file.getName() + "\n");
//    buffer.append("Path: " + file.getPath() + "\n");
//    buffer.append("Size: " + file.length() + "\n");
//    return buffer.toString();
//      String url = "rmi://"+localhost+"/";
       // File f=new File(filePathchoose+File.separator+userName+File.separator);
        listFilesForFolder(file);
        String localhost = window.jLabel4.getText();
        String url = "rmi://"+localhost+"/";
            Context context2;
            try {
                
                context2 = new InitialContext();
                FileArchiverC myClientInt = (FileArchiverC) 
                context2.lookup(url + "MyClientObject"); 
              //  myClientInt.metodaS(przykl);
            } catch (NamingException ex) {
                Logger.getLogger(FileArchiverServer.class.getName()).log(Level.SEVERE, null, ex);
            }
                     
        
        
      
    }
     

    @Override
    public void wywolajS() throws RemoteException {
        
    //folder-file to location
        File folder = new File(filePathchoose+File.separator+users_name+File.separator);
        listFilesForFolder(folder);
         String localhost = window.jLabel4.getText();
         // FileArchiverC tester = (FileArchiverC) Naming.lookup("rmi://localhost:1099/testrmiio");
          String url = "rmi://"+localhost+"/";
          Context context3;
            try {
                context3 = new InitialContext();
                FileArchiverC myClientInt3 = (FileArchiverC) 
            context3.lookup(url + "MyClientObject");
         myClientInt3.metodaS(nazwy);
            } catch (NamingException ex) {
                Logger.getLogger(FileArchiverServer.class.getName()).log(Level.SEVERE, null, ex);
            }
    
        
        
    }
         
}
 
