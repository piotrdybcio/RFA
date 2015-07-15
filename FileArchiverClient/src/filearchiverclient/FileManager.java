/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filearchiverclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author Piotr
 */
public class FileManager {
  
    public static String file_name=null;
    public static String file_name1=null;
    public static String file_name3=null;
    public static String MD5="null";
    public static String MD51="null";
    public static String MD53="null";
    public String[][] file_table;
    public String[][] temp_table_to_change;
    public String[][] temp_table_to_delete;
    public char znak;
    public int index;
    public int index1;
    public int index3;
    public int i=0;
    public int k=0;
    public int k1=0;
    public int line=0;
    public int line1=0;
    public int line3=0;
    public char znak1;
     public char znak3;
    /**
     * Metoda ta liczy ilość linii w pliku, aby wykorzystać to później
     * przy tworzeniu tablicy przechowującej wszystkie niezbęde dane.
     *
     * @throws java.io.FileNotFoundException
     */
    public void numberLine(String userPath) throws FileNotFoundException
    {
        String linia=null;
        //line = 0;

        FileReader reader = new FileReader(userPath+File.separator+".data\\file_send.txt");
        BufferedReader br = new BufferedReader(reader);

        try{
            while((linia = br.readLine()) !=null ){
                //System.out.println(linia);
                                
                linia.toUpperCase();
                znak=linia.charAt(0);
                if(znak!='\n'){ }
                           
                line++;
                }
            System.out.println("number line is = " + line);
            br.close();
        }
        catch(IOException e){
            System.out.println("Błąd odczytu/zapisu z pliku.");
        }
    }
    
    public void numberLine_change(String userPath) throws FileNotFoundException
    {
        String linia1=null;
        line1 = 0;
        FileReader reader = new FileReader(userPath+File.separator+".data\\file_send.txt");
        BufferedReader br = new BufferedReader(reader);

        try{
            while((linia1 = br.readLine()) !=null ){
                //System.out.println(linia);
                                
                linia1.toUpperCase();
                znak1=linia1.charAt(0);
                if(znak1!='\n'){ }
                           
                line1++;
                }
            System.out.println("number line to change is = " + line1);
            br.close();
        }
        catch(IOException e){
            System.out.println("Błąd odczytu/zapisu z pliku.");
        }
    }
    
    public void numberLine_delete(String userPath) throws FileNotFoundException
    {
        String linia5=null;
        line3 = 0;
        FileReader reader = new FileReader(userPath+File.separator+".data\\file_send.txt");
        BufferedReader br = new BufferedReader(reader);

        try{
            while((linia5 = br.readLine()) !=null ){
                //System.out.println(linia);
                                
                linia5.toUpperCase();
                znak3=linia5.charAt(0);
                if(znak3!='\n'){ }
                           
                line3++;
                }
            System.out.println("number line to change is = " + line3);
            br.close();
        }
        catch(IOException e){
            System.out.println("Błąd odczytu/zapisu z pliku.");
        }
    }
    
    
    public void loadNameAndMD5FromFile(String userPath) throws FileNotFoundException
    {
        numberLine(userPath);
        //file_table = null;
        file_table = new String[line][2]; //-------- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! !!!!!!!!!!!!! -/
        String linia=null;
        System.out.println("line " +line);
        FileReader reader1 = new FileReader(userPath+File.separator+".data\\file_send.txt");
        BufferedReader br = new BufferedReader(reader1);
        try{
            while((linia = br.readLine()) !=null ){
                //System.out.println(linia);
                
                linia.toUpperCase();
                znak=linia.charAt(0);
                if(znak!='\n'){
                    index=linia.indexOf("$");
                    file_name=linia.substring(0,index);
                    MD5=linia.substring(index+1);
                    System.out.println("TEEEEEEEEEEEEEEEEEEEEEEEEEEEEESTA " + file_name+" "+ MD5);
                    file_table [i][0] = file_name;
                    file_table [i][1] = MD5;
                                     
                }
                //System.out.println(file_name+" "+temp1);
                i++;
            }
            System.out.println(i);
           // System.out.println("DOEEEEEEEEEEEEEEEEEEEE\n"+file_table[0][1]);
            br.close();
        }
        catch(IOException e){
            System.out.println("Błąd odczytu/zapisu z pliku.");
        }
    }
    
    public void save_send(String fileName, String MD5Hash, String userPath) throws UnsupportedEncodingException, IOException{
        
                FileWriter file = new FileWriter(userPath+File.separator+".data\\file_send.txt", true);
                BufferedWriter out = new BufferedWriter(file);
                
               // out.write("B2_wyniki_04_09_2014.pdf$a6565ad5d144dfface39f097b4296e5d\n");
                out.write(fileName+"$"+MD5Hash);
                out.newLine();
                out.close();
                
}
    
    
     public void findANDsave_new_send(String fileName, String MD5Hash, String userPath) throws FileNotFoundException
    {
        numberLine_change(userPath);
        temp_table_to_change = new String[line1][2]; //-------- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! !!!!!!!!!!!!! -/
        String linia=null;
        
        FileReader reader = new FileReader(userPath+File.separator+".data\\file_send.txt");
        BufferedReader br = new BufferedReader(reader);
        try{
            while((linia = br.readLine()) !=null ){
                //System.out.println(linia);
                
                linia.toUpperCase();
                znak1=linia.charAt(0);
                if(znak1!='\n'){
                    index1=linia.indexOf("$");
                    file_name1=linia.substring(0,index1);
                    MD51=linia.substring(index1+1);
                    
                    temp_table_to_change [k][0] = file_name1;
                    temp_table_to_change [k][1] = MD51;
                                     
                }
                //System.out.println(file_name+" "+temp1);
                k++;
            }
            k=0;
            System.out.println(k);
           // System.out.println("DOEEEEEEEEEEEEEEEEEEEE\n"+file_table[0][1]);
            br.close();
            
            
            //Print table
            
            for(int w=0; w<line1; w++){
                
                if(java.util.Objects.equals(temp_table_to_change [w][0],fileName) == true){
                  
                    temp_table_to_change [w][1] =   MD5Hash;
                  
                }
            }
            //Zapis do pliku
                 File file = new File(userPath+File.separator+".data\\file_send.txt");
                
                if(file.delete()== true)
                {
                    PrintWriter writer = null;
                    
                    writer = new PrintWriter(userPath+File.separator+".data\\file_send.txt", "UTF-8");
                    writer.close();
                    
                    
                FileWriter file1 = new FileWriter(userPath+File.separator+".data\\file_send.txt", true);
                BufferedWriter out1 = new BufferedWriter(file1);
                
               // out.write("B2_wyniki_04_09_2014.pdf$a6565ad5d144dfface39f097b4296e5d\n");
                for(int j=0; j<line1; j++){
                out1.write(temp_table_to_change [j][0]+"$"+temp_table_to_change [j][1]);
                out1.newLine();
                }
                
                
                out1.close(); 
                    System.out.println("ZAPISANO POPRAWNIE INFO DO PLIKU O AKTUALIZACJI");
                
                }
                else{
                    //ERROR
                    
                    System.out.println("NIE NIE ZAPISANO POPRAWNIE INFO DO PLIKU O AKTUALIZACJI");
                }
                
                
            
            
            
        }
        catch(IOException e){
            System.out.println("Błąd odczytu/zapisu z pliku.");
        }
        
        
        
    }
    
     public void findANDdeleteFile(String fileName, String userPath) throws FileNotFoundException
    {
        numberLine_delete(userPath);
        temp_table_to_delete = new String[line3][2]; //-------- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! !!!!!!!!!!!!! -/
        String linia3=null;
        
        FileReader reader = new FileReader(userPath+File.separator+".data\\file_send.txt");
        BufferedReader br = new BufferedReader(reader);
        try{
            while((linia3 = br.readLine()) !=null ){
                //System.out.println(linia);
                
                linia3.toUpperCase();
                znak3=linia3.charAt(0);
                if(znak3!='\n'){
                    index3=linia3.indexOf("$");
                    file_name3=linia3.substring(0,index1);
                    MD53=linia3.substring(index1+1);
                    
                    temp_table_to_delete [k1][0] = file_name3;
                    temp_table_to_delete [k1][1] = MD53;
                                     
                }
                //System.out.println(file_name+" "+temp1);
                k1++;
            }
            k1=0;
            System.out.println(k1);
           // System.out.println("DOEEEEEEEEEEEEEEEEEEEE\n"+file_table[0][1]);
            br.close();
            
            
            //Print table
            
            for(int w1=0; w1<line3; w1++){
                
                if(java.util.Objects.equals(temp_table_to_delete [w1][0],fileName) == true){
                  
                   // temp_table_to_delete [w1][1] =   MD5Hash;
                    temp_table_to_delete [w1][0] =   "deleted";
                    System.out.println("deleted");
                    temp_table_to_delete [w1][1] =   "deleted";
                  
                }
            }
            //Zapis do pliku
                 File file = new File(userPath+File.separator+".data\\file_send.txt");
                
                if(file.delete()== true)
                {
                    PrintWriter writer = null;
                    
                    writer = new PrintWriter(userPath+File.separator+".data\\file_send.txt", "UTF-8");
                    writer.close();
                    
                    
                FileWriter file3 = new FileWriter(userPath+File.separator+".data\\file_send.txt", true);
                BufferedWriter out3 = new BufferedWriter(file3);
                
               // out.write("B2_wyniki_04_09_2014.pdf$a6565ad5d144dfface39f097b4296e5d\n");
                for(int j12=0; j12<line3; j12++){
                out3.write(temp_table_to_delete [j12][0]+"$"+temp_table_to_delete [j12][1]);
                out3.newLine();
                }
                
                
                out3.close(); 
                    System.out.println("ZAPISANO POPRAWNIE INFO DO PLIKU O USUNIĘCIU PLIKU Z SERWERA");
                
                }
                else{
                    //ERROR
                    
                    System.out.println("NIE NIE ZAPISANO POPRAWNIE INFO DO PLIKU O USUNIĘCIU PLIKU Z SERWERA");
                }
                
                
            
            
            
        }
        catch(IOException e){
            System.out.println("Błąd odczytu/zapisu z pliku." + e);
        }
        
        
        
    }
    
    
}
