/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filearchiverclient;

import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import static filearchiverclient.window.CLM1;
import static filearchiverclient.window.MsgPanel;
import filearchiverserver.FileArchiver;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.SplashScreen;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.Principal;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


/**
 *
 * @author Piotr
 */
public class login extends javax.swing.JFrame {
    //Splash
     static SplashScreen mySplash;                   // instantiated by JVM we use it to get graphics
    static Graphics2D splashGraphics;               // graphics context for overlay of the splash image
    static Rectangle2D.Double splashTextArea;       // area where we draw the text
    static Rectangle2D.Double splashProgressArea;   // area where we draw the progress bar
    static Font font;                               // used to draw our text
    //////
    FileArchiverClient FAC;
    window window;
    public static Thread doPolaczenia;
    FileArchiver service = null;
    JFileChooser chooser = new JFileChooser();
    static ClientLocalMetods CLM = new ClientLocalMetods();
    String user ="default";
    String pather; //
    File loc_user;
    public static String localhost ;
    public static String RMIport ;
    /**
     * Creates new form login
     * @throws java.net.UnknownHostException
     */
    public login() throws UnknownHostException {
       
        initComponents();
        setTitle("RemoteFileArchiverClient - Piotr Dybcio, Michał Piekarski");
        ImageIcon imic = new ImageIcon(getClass().getResource("/images/logo.png"));
        Image img = imic.getImage();
        this.setIconImage(img);
        jFrame1.setIconImage(img);
        jFrame1.setTitle("RemoteFileArchiverClient - Piotr Dybcio, Michał Piekarski");
        jFrame1.setLocationRelativeTo(null);
       // String userName = new com.sun.security.auth.module.NTSystem().getName();
        String userName;
        userName = System.getProperty("user.name");
        jTextField5.setText(userName);
        jLabel13.setText("");
        jTextField4.setText(userName);
        //jFrame1.setPreferredSize(500, 500);
        jTextField1.setText(InetAddress.getLocalHost().getHostAddress());
        jTextField7.setText(InetAddress.getLocalHost().getHostAddress());
        jTextField2.setText("1099");
        jLabel14.setText("");
        jTextField3.setText("1099");
        
        jPasswordField3.setText("");
        if(userName.equals("Piotr") == true){
        jTextField6.setText("C:\\Users\\Piotr\\Desktop\\Client");    
        }else{
        jTextField6.setText("C:\\Users\\"+userName+"\\Desktop\\Client");    
        }
        
       // InetAddress.getLocalHost().getHostAddress();
        
//        JDialog dialog = new JDialog();
//JLabel label = new JLabel("Please wait...");
//dialog.setLocationRelativeTo(null);
//dialog.setTitle("Please Wait...");
//dialog.add(label);
//dialog.pack();
//dialog.setVisible(true);        
        setResizable(false);
    }

    public void connect_button(){
        
        doPolaczenia=new Thread(){
        public void run(){
            try {  
             System.out.println("TEST");
            service = (FileArchiver) Naming.lookup("rmi://localhost:1099/testrmiio");  
                String rootPath = "D:";
                System.out.println("TEST");
            //String rootPath = System.getProperty("D:/");  
            System.out.println("rootPath "+ rootPath);
            System.out.println("TEST");
            //String userNtosend = j
           
            String filePath = "C:\\Users\\Piotr\\Downloads" + File.separator + "GPU-Z.0.7.9.exe";  
            System.out.println("filePath "+ filePath);
            InputStream inStream = new FileInputStream(filePath);  
               System.out.println("TEST");
            RemoteInputStreamServer remoteFileData = new SimpleRemoteInputStream(inStream); 
            System.out.println("TEST "+remoteFileData );
            service.sendFile("","1_GPU-Z.0.7.9.exe", remoteFileData.export(), "5658425");  
              System.out.println("TEST WSZYSTKO OK");
              user=jTextField5.getText();
              /*te zmienna ustaw na file usera
              loc_user
              */
//              if(pather==null)
//              {
//              loc_user= new File("C:\\Users\\Piotr\\Dropbox\\OPA_PROJEKT");
//              System.out.println("COŚ poszło nie tak login -> window");
//              }
//              else{
//              loc_user = new File(pather);
//              window = new window(user,loc_user);
//              window.setVisible(true);
//              }
              
              
        } catch (RemoteException | FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            System.out.println("TEST przy połączeniu " + e);
            e.printStackTrace();  
            
        }   catch (NotBoundException ex) {
                Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
                        };//koniec wątku
       doPolaczenia.start();
        
       
        
    }
    
        public void login_button(){
        
        
            try {  
             System.out.println("TEST");
             String localhost = jTextField1.getText();
             String RMIport = jTextField2.getText();
            service = (FileArchiver) Naming.lookup("rmi://"+localhost+":"+ RMIport +"/testrmiio");  
               
           
           // String filePath = "C:\\Users\\Piotr\\Downloads" + File.separator + "GPU-Z.0.7.9.exe";  
           // System.out.println("filePath "+ filePath);
           // InputStream inStream = new FileInputStream(filePath);  
           //    System.out.println("DUPA1");
           // RemoteInputStreamServer remoteFileData = new SimpleRemoteInputStream(inStream); 
            //System.out.println("DUPA2 "+remoteFileData );
            String user = jTextField5.getText();
            String temppass = new String(jPasswordField3.getPassword());
                System.out.println("temppass: " + temppass + "\n user " + user);
            service.readANDcompareFile(user, temppass ); 
              System.out.println("TEST WSZYSTKO OK");
              FileArchiverClient fac = new FileArchiverClient();
            Context context1 = null;
            try {
                context1 = new InitialContext();
            } catch (NamingException ex) {
                Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
            }
            try { 
                context1.rebind("rmi:MyClientObject", fac);
            } catch (NamingException ex) {
                Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
            }
            // String user1=jTextField5.getText();
                System.out.println("user1" + user);
              /*te zmienna ustaw na file usera
              loc_user
              */
                System.out.println("path" + CLM.path+"\n loc_user " + loc_user);
              if(CLM.path==null)
              {
              //loc_user= new File("C:\\Users\\Piotr\\Dropbox\\OPA_PROJEKT");
              System.out.println("COŚ poszło nie tak login -> window");
              }
              else{
              loc_user = new File(CLM.path);
              System.out.println("pather" + CLM.path+"\n loc_user " + loc_user);
              CLM.path=null;
              window = new window(user,loc_user);
              jLabel14.setText("Trwa ładowanie ...");
              window.setVisible(true);
              jLabel14.setText("");
              this.setVisible(false);
                  System.out.println("USER " + user);
              }
        } catch (RemoteException e) {  
            // TODO Auto-generated catch block  
            System.out.println("TEST przy połączeniu " + e);
            e.printStackTrace();  
            
        }   catch (NotBoundException ex) {
                Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jOptionPane1 = new javax.swing.JOptionPane();
        jFrame1 = new javax.swing.JFrame();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPasswordField1 = new javax.swing.JPasswordField();
        jPasswordField2 = new javax.swing.JPasswordField();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jPasswordField3 = new javax.swing.JPasswordField();
        jLabel14 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        jFrame1.setMinimumSize(new java.awt.Dimension(650, 300));
        jFrame1.setResizable(false);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Formularz rejestracyjny");

        jLabel5.setText("Adres serwera");

        jLabel6.setText("Port");

        jLabel7.setText("Login");

        jLabel8.setText("Hasło");

        jTextField3.setText("jTextField3");

        jTextField4.setText("jTextField4");

        jLabel10.setText("Powtórz hasło");

        jTextField7.setText("jTextField7");
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });

        jButton4.setText("Zarejestruj");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Wróć");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel12.setText("Lokalizacja Clienta");

        jTextField6.setText("jTextField6");

        jButton6.setText("Przeglądaj");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame1Layout.createSequentialGroup()
                .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jFrame1Layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jLabel4))
                    .addGroup(jFrame1Layout.createSequentialGroup()
                        .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jFrame1Layout.createSequentialGroup()
                                .addGap(98, 98, 98)
                                .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel12)))
                            .addGroup(jFrame1Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jButton5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField7, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPasswordField2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField4)
                            .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField6, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4)
                            .addComponent(jButton6))))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jButton6))
                .addGap(26, 26, 26)
                .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton4)
                        .addComponent(jButton5))
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Remote File Archive Client");

        jLabel2.setText("Adres Serwera");

        jLabel3.setText("Port");

        jTextField1.setText("jTextField1");

        jTextField2.setText("jTextField2");

        jButton1.setText("Połącz");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Rozłącz");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Zamknij");
        jButton3.setToolTipText("");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel9.setText("Login");

        jLabel11.setText("Hasło");

        jTextField5.setToolTipText("");
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jPasswordField3.setText("jPasswordField3");
        jPasswordField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField3ActionPerformed(evt);
            }
        });

        jLabel14.setText("jLabel14");

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user_add.png"))); // NOI18N
        jMenuItem1.setText("Register");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit-icon.png"))); // NOI18N
        jMenuItem2.setText("Zamknij");
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("?");

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F12, 0));
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/about.png"))); // NOI18N
        jMenuItem3.setText("About");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel9)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPasswordField3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(162, 162, 162))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addGap(30, 30, 30))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 74, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jPasswordField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(48, 48, 48))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        System.exit(0);
//        try {
//             TODO add your handling code here:
//            int licz =0;
//            if (licz == 0){
//            FileArchiverClient fac = new FileArchiverClient();
//            Context context = new InitialContext();
//            context.bind("rmi:MyClientObject", fac);
//            licz++;
//            }
//            FileArchiver service = (FileArchiver) Naming.lookup("rmi://localhost:1099/testrmiio"); 
//            service.getFileServer();
//        } catch (RemoteException ex) {
//            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NamingException ex) {
//            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NotBoundException ex) {
//            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(jTextField6.getText().equals("") == false){        
        login_button();
            //connect_button();
        }else{
            //MsgPanel.append(Color.red, "Nie wybrano pliku! Wybierz ponownie");
        }
        
//        window = new window();
//        window.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        jFrame1.setVisible(true);
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
         JOptionPane.showMessageDialog(FAC.login , "Remote File Archive Client\nwykonany przez Piotr Dybcio i Michał Piekraski\nna przedmiot OPA\nListopad 2014", "About", JOptionPane.PLAIN_MESSAGE);
        
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    /**
     * Przycisk Zarejestruj
     * @param evt 
     */
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        System.out.println(jTextField4.getText().equals(""));
        System.out.println(jTextField6.getText().equals(""));
        System.out.println(jPasswordField1.getPassword().length);
        System.out.println(jPasswordField2.getPassword().length);
        System.out.println(Arrays.equals(jPasswordField1.getPassword(), jPasswordField2.getPassword()));
        if((jTextField4.getText().equals("")==false) && (jTextField6.getText().equals("")==false) && 
                (jPasswordField1.getPassword().length!=0) && 
                (jPasswordField2.getPassword().length!=0 ) &&
                ( Arrays.equals(jPasswordField1.getPassword(), jPasswordField2.getPassword())==true)
                ){
        try {
            // TODO add your handling code here:
            // String username, Password pass;
            System.out.println("TEST");
            String nametosend = jTextField4.getText();
            String client_path = jTextField6.getText();
            //char[] temppass = jPasswordField1.getPassword();
            String passText = new String(jPasswordField1.getPassword());
            System.out.println("temmmmmmmmmpassssssss "+ passText);
            boolean success = (new File(client_path+File.separator+".data")).mkdirs();
            if (success) { // Tworzenie pliku z hasłem
                PrintWriter writer = null;
                try {
                    // Directory creation failed
                   // MsgPanel.append(Color.GRAY, "Stworzono użytkownika " + name);
                    
                    writer = new PrintWriter(client_path+File.separator+".data"+File.separator+"file_send.txt", "UTF-8");
                   // writer.println("");
                    //writer.println("The second line");
                    writer.close();
                    System.out.println("Stworzono folder .data\n");
                  //  SLM.register_answer("Stworzono użytkownika " + name);
                } catch (FileNotFoundException ex) {
                    } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    writer.close();
                }
            }else{
                // MsgPanel.append(Color.RED, "Użytkownik " + name+" już istnieje!");
                 //SLM.register_answer("Użytkownik " + name+" już istnieje!");
                 System.out.println("nie stworzyłem folderu .data\n");
            }
            localhost = jTextField1.getText();
            RMIport = jTextField2.getText();
            service = (FileArchiver) Naming.lookup("rmi://"+localhost+":"+ RMIport +"/testrmiio");
             System.out.println("TEST "+nametosend+" \n Client Path " +client_path );
            service.registerUser(nametosend, passText, client_path);
             FileArchiverClient fac = new FileArchiverClient();
            Context context = null;
            try {
                context = new InitialContext();
            } catch (NamingException ex) {
                Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
            }
            try { 
                context.rebind("rmi:MyClientObject", fac);
            } catch (NamingException ex) {
                Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
            }
           // FileArchiver tester = (FileArchiver) Naming.lookup("rmi://localhost:1099/testrmiio");
            //tester.test();
            //context=null;
             System.out.println("TEST");
        } catch (NotBoundException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }}else{
            JOptionPane.showMessageDialog(FAC.login , "Nie podałeś nazwy użytkownika\nalbo hasło jest niepoprawne \nlub go nie wpisałeś\nLub nie wybrałeś lokalizacji", "Error", JOptionPane.PLAIN_MESSAGE);
         
        }
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        jFrame1.setVisible(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        //chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Open");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(FAC.window) == JFileChooser.APPROVE_OPTION) {
          System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
          System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
          jTextField6.setText(chooser.getSelectedFile().toString());
          String str = chooser.getSelectedFile().toString();
          System.out.println("filename : "+str.replace(chooser.getCurrentDirectory().toString()+"\\", ""));

        } else {
          System.out.println("No Selection ");
         // MsgPanel.append(Color.BLACK, "Nie wybrano pliku!");
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jPasswordField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordField3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new login().setVisible(true);
                } catch (UnknownHostException ex) {
                    Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    public static javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    public static javax.swing.JLabel jLabel13;
    public static javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    public static javax.swing.JOptionPane jOptionPane1;
    public static javax.swing.JPasswordField jPasswordField1;
    public static javax.swing.JPasswordField jPasswordField2;
    public static javax.swing.JPasswordField jPasswordField3;
    public static javax.swing.JTextField jTextField1;
    public static javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    public static javax.swing.JTextField jTextField4;
    public static javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    public static javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables
}
