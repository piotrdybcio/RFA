/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filearchiverclient;

import filearchiverserver.FileArchiver;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.JList;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

/**
 *
 * @author Piotr
 */
public final class window extends javax.swing.JFrame {
    login login;
    String user="default";
    public static DefaultListModel<String> przyklad;
    public static DefaultListModel<String> Serwmodel;
    //jList2 = new javax.swing.JList();
    //public static javax.swing.JList<String> jList1 = new javax.swing.JList<>();
 //   jList1 = new javax.swing.JList<String>();
    FileArchiverClient FAC;
    public static Thread dodawanie_pliku;
    public static Thread pobieranie_pliku;
    static MessagePanel MsgPanel = new MessagePanel();
    static FileManager FM = new FileManager();
    ClientLocalMetods CLM;
    JFileChooser chooser = new JFileChooser();
    static ClientLocalMetods CLM1 = new ClientLocalMetods();
    File loc_user;
    private FileSystemModel fileSystemModel;
    public static String[] see_serwer_names;
    boolean issend = false;
    boolean isnewversion = false;
    public static String user_name;
    public static String user_local_path;
    String tempHash = null; // Do sprawdzenia zmiany w pliku
    //private final JTree fileTree;
   // private String userName,filePathchoose;
   
    public void fileinfolder(){
 File folder = new File("c:/");
    File[] listOfFiles = folder.listFiles();
//jList1.mo("asd");
    for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].isFile()) {
        System.out.println("File " + listOfFiles[i].getName());
      } else if (listOfFiles[i].isDirectory()) {
        System.out.println("Directory " + listOfFiles[i].getName());
      }
    }
   }
    
    /**
     * Creates new form window
     */
    public window(String user,File loc) throws NotBoundException {
        
            initComponents();
            setTitle("RemoteFileArchiverClient - Piotr Dybcio, Michał Piekarski");
            ImageIcon imic = new ImageIcon(getClass().getResource("/images/logo.png"));
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            Image img = imic.getImage();
            this.setIconImage(img);
            this.user=user;
            MsgPanel.append(Color.red, "Zainicjowano komponenty");
            jLabel6.setText(loc.toString());
            jLabel8.setText(user);
            jLabel16.setText(FileArchiverClient.login.jTextField2.getText());
            String user_name = jLabel8.getText();
            user_local_path = jLabel6.getText();
            jLabel11.setText(FileArchiverClient.login.jTextField7.getText()); // ADRES SERWERA
            loc_user=loc;
            jTextField1.setText("");
            /*
            Inicjacja  listy plików
            */
            
            
            przyklad= new DefaultListModel<>();
            przyklad.removeAllElements();
            listFilesForFolder(loc);
            jList1.setModel(przyklad);
             System.out.print("przed jestem");
        try {
            // TU URUCHOM
            //===================================================================
             System.out.print("1. jestem");
             System.out.println("USER "+ user);
            CLM1.get_Server_name_File(user);
             System.out.print("2. jestem");
            see_serwer_names = ClientLocalMetods.server_file_name_to_see;
             System.out.println("3 jestem liczba plikow z serwa="+see_serwer_names.length);
             Serwmodel=new DefaultListModel<>();
             String critic1=user+".pass";
             String critic2=user+".path";
             System.out.println("critic."+critic1+"."+critic2+".");
             for(int i=0;i<see_serwer_names.length;){
                 if(see_serwer_names[i].equalsIgnoreCase(critic1)||see_serwer_names[i].equalsIgnoreCase(critic2)){
                   Serwmodel.addElement("cannot see-top secret");
                   ++i;
                 }else{ Serwmodel.addElement(see_serwer_names[i]);
                     ++i;}
             };
             jList2.setModel(Serwmodel);
             
        
             jList2.addListSelectionListener(new ListSelectionListener() {

            

                 @Override
                 public void valueChanged(ListSelectionEvent e) {
                      String s = (String) jList2.getSelectedValue();
                     jLabel14.setText(s);
                 }
        });
             
            //
            // PODEPNIJ see_serwer_names pod liste należącą do serwer
            // !!!!!!!!
            //===================================================================
            
            
            
            
            
            
            /*
            Odpowiada z double clik na plik + komunikat
            jList1. po dodaniu pliku i skożystaniu z tego się nie odświerza
            */
            /* MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
            JList jList1 = (JList) mouseEvent.getSource();
            if (mouseEvent.getClickCount() == 2) {
            int index = jList1.locationToIndex(mouseEvent.getPoint());
            if (index >= 0) {
            Object o = jList1.getModel().getElementAt(index);
            System.out.println("Double-clicked on: " + o.toString());
            JOptionPane.showMessageDialog(rootPane, "Kliknięto na "+ o.toString());
            }
            }
            }
            };
            jList1.addMouseListener(mouseListener);
            */
            
            
            //File filtr=new File();
//            try {
//            Context context2;
//                 
//                context2 = new InitialContext();
//                FileArchiver myClientInt = (FileArchiver)
//                context2.lookup("rmi://localhost:1099/testrmiio//"+ "MyClientObject"); 
//                myClientInt.dataAboutFiles(loc);
//            
//            if(Serwmodel!=null)
//            jList2.setModel(Serwmodel);
//            } catch (IOException ex) {
//            Logger.getLogger(window.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NamingException ex) {
//            Logger.getLogger(window.class.getName()).log(Level.SEVERE, null, ex);
//        }
            /////////////////////////////////////////////////
            /*
            Piotrze, w tym miejscu wczytaj zmienną usera, teraz jest default.
            */
            // loc_user=FileUtils.getTempDirectory();
            //loc_user= new File("C:\\Users\\Michal\\Dropbox\\OPA_PROJEKT");
            
            
            
            ////////////////////////////////////////////////
            //JScrollPane jsp = new JScrollPane(jTextPane1);
            //jTextPane1.scrollRectToVisible(true);
            //TreeModel model = new FileTreeModel(new File(System.getProperty("user.dir")));
            //jTree1.setModel(new File(System.getProperty("user.dir"));
            
            
            //jList1.setModel("sad","sdf");
            // InetAddress.getLocalHost().getHostAddress();
            
            //setResizable(false);
        } catch (MalformedURLException ex) {
            Logger.getLogger(window.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(window.class.getName()).log(Level.SEVERE, null, ex);
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

        jLabel1 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel9 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFont(new java.awt.Font("Cambria", 2, 12)); // NOI18N
        setMaximumSize(new java.awt.Dimension(4096, 2304));
        setMinimumSize(new java.awt.Dimension(1280, 720));
        setPreferredSize(new java.awt.Dimension(1280, 720));

        jLabel1.setText("Adres serwera:");

        jScrollPane4.setDoubleBuffered(true);
        jScrollPane4.setEnabled(false);
        jScrollPane4.setViewportView(jTextPane1);

        jLabel2.setText("Zalogowany jako:");

        jLabel3.setText("Okno wiadomości:");

        jLabel4.setText("Wybierz plik do zarchiwizowania");

        jButton1.setText("Przeglądaj");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.setText("jTextField1");

        jButton2.setText("Dodaj");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel5.setText("Ścieżka klienta:");

        jLabel6.setText("jLabel6");

        jLabel8.setText("jLabel8");

        jLabel11.setText("jLabel11");

        jLabel15.setText("Port:");

        jLabel16.setText("jLabel16");

        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setDoubleBuffered(true);

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.setMaximumSize(new java.awt.Dimension(30, 80));
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jLabel9.setText("Lista plików użytkownika:");

        jButton6.setText("Odśwież!");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel7.setText("Podglad plików uzytkownika:");

        jButton3.setForeground(new java.awt.Color(255, 102, 0));
        jButton3.setText("Podgląd!");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton7.setForeground(new java.awt.Color(0, 0, 255));
        jButton7.setText("Otwórz folder użytkownika");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(jButton6))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel7)
                                .addGap(47, 47, 47)
                                .addComponent(jButton3)
                                .addGap(18, 18, 18)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jButton3)
                    .addComponent(jButton7))
                .addContainerGap())
        );

        jList2.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList2);

        jLabel10.setText("Lista plików na serwerze:");

        jLabel12.setText("Aby pobrać plik z serwera, wybierz plik z listy.");

        jLabel13.setText("wybrałeś/aś:");

        jLabel14.setText("brak");

        jButton4.setForeground(new java.awt.Color(255, 102, 0));
        jButton4.setText("Przywróć!");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setForeground(new java.awt.Color(255, 0, 51));
        jButton5.setText("Usuń!");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jButton4)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton5))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel14))
                                    .addComponent(jLabel12)))
                            .addComponent(jLabel10))
                        .addGap(0, 355, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu1.setText("File");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit-icon.png"))); // NOI18N
        jMenuItem2.setText("Zamknij");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("?");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F12, 0));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/about.png"))); // NOI18N
        jMenuItem1.setText("About");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator3)
            .addComponent(jSeparator2)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jScrollPane4)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addGap(74, 74, 74)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 22, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel8)
                    .addComponent(jLabel11)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(8, 8, 8)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Przycisk Przeglądaj [FileArchiverCilent/window]
     * Otwiera Manager Plików z lokalnego komputera i pozwala wybrać plik do wysłania
     * @param evt nie używany
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        
        //chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Open");
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setAcceptAllFileFilterUsed(false);
   
        if (chooser.showOpenDialog(FAC.window) == JFileChooser.APPROVE_OPTION) {
          System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
          System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
          jTextField1.setText(chooser.getSelectedFile().toString());
          String str = chooser.getSelectedFile().toString();
          System.out.println("filename : "+str.replace(chooser.getCurrentDirectory().toString()+"\\", ""));
           
//           przyklad= new DefaultListModel();
//          przyklad.removeAllElements();
//            listFilesForFolder(chooser.getCurrentDirectory());
//            jList1.setModel(przyklad);
//            
        } else {
          System.out.println("No Selection ");
          MsgPanel.append(Color.BLACK, "Nie wybrano pliku!");
        }
        /*
        *obsluga listy M.P
        */
        //getTempDirectory();-pobiera aktualną ścieżkę tymczasową
        //getUserDirectory()
        //Returns a File representing the user's home directory.
        //listFiles(File directory, IOFileFilter fileFilter, IOFileFilter dirFilter)
        //Finds files within a given directory (and optionally its subdirectories).
        //trueFileFilter()
        //Returns a filter that always returns true.
          //  przyklad= new DefaultListModel();
         
            //FileUtils.listFiles(FileUtils.getUserDirectory(),FileFilterUtils.trueFileFilter() , null);
           
//                 for(int i=0; i< 20; i++)
//                    przyklad.addElement(i+1);
                // File fil=new File("D://biblioteki");
                // System.out.print(FileUtils.getTempDirectory()+"\n");
               // System.out.println(FileUtils.listFilesAndDirs(FileUtils.getUserDirectory(),FileFilterUtils.trueFileFilter(), null).toString());
                //// System.out.println(FileUtils.listFiles(FileUtils.getUserDirectory(),FileFilterUtils.directoryFileFilter(), null).toString());
                // listFilesForFolder(FileUtils.getUserDirectory());
               // jList1.setModel(przyklad);
    }//GEN-LAST:event_jButton1ActionPerformed
    public void listFilesForFolder(final File folder) {//dziala super
        if(folder.listFiles()!=null){
    for (final File fileEntry : folder.listFiles()) {
        if (fileEntry.isDirectory()) {
             
           listFilesForFolder(fileEntry);
        } else {
           przyklad.addElement(fileEntry.getName());
            //System.out.println(fileEntry.getName());
        }
    }
    }
}


    
    /**
     * Przycisk Dodaj
     * Pobiera lokalizacje pliku, przechodzi do przesyłania pliku na serwer + kopia na dysku lokalnym
     * @param evt nie używany
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dodawanie_pliku=new Thread(){
        public void run(){
        if(jTextField1.getText().equals("") == false){ //Sprawdzenie czy dany plik istnieje (czy go wybrano)
        try {
            FM.loadNameAndMD5FromFile(jLabel6.getText()); // Ładowanie informacji z pliku file_send.txt do tablicy
            
            
            
            
            String usertosend = jLabel8.getText();
            // TODO add your handling code here:
            String filename = chooser.getSelectedFile().toString();
            String filenametosend = filename.replace(chooser.getCurrentDirectory().toString()+"\\", "");
            System.out.println("filenametosend " + filenametosend +"\n filename " + filename);
            
            if(FM.line == 0){     
            }else{
            
                MsgPanel.append(Color.red, "TEST FM.file_table[i][0] "+FM.file_table[0][0] +" filenametosend " +filenametosend );
            CLM.md5file(new FileInputStream(new File(filename))); 
            tempHash = null;
            for(int i=0; i<FM.line;i++){
           //  if(FM.file_table[i][0].equals(filenametosend)){ // TAK NIE ROBIĆ
             if(java.util.Objects.equals(FM.file_table[i][0],filenametosend )){ // Sprawdzanie czy plik nie znajduje się na liście
                 // FM.file_table[1][1] = ;
                 MsgPanel.append(Color.red, "FM.file_table[i][0] "+FM.file_table[i][0] +" filenametosend " +filenametosend );
                 tempHash = FM.file_table[i][1];
                 issend = true; 
            }  
                
            }
        }
            
            
            if(issend == false){
            
            
            CLM1.GetandSendFile(usertosend,chooser.getCurrentDirectory().toString(), filenametosend); //Przechodzi do funkcji porzesyłającej pliki
            jTextField1.setText("");
            
            //
              FileArchiverClient fac = new FileArchiverClient();
            Context context6 = null;
            try {
                context6 = new InitialContext();
                } catch (NamingException ex) {
                    Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                }
            try { 
                context6.rebind("rmi:MyClientObject", fac);
                } catch (NamingException ex) {
                    Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                }         
            //
            
            
                //copy files using apache commons io
            File source = new File(filename);
            File dest = new File(CLM.copy_path + File.separator + filenametosend);
            System.out.println("clm path" + CLM.path);
            System.out.println("dest copy " + CLM.path + filenametosend);
            /*#############################################################################################
            TU TO WSTAW
            -wstawiłęm niżej, bo tutaj jeszcze przesłanie nie było wykonane
            
            ##############################################################################################*/
            
            long start = System.nanoTime();
            try {
                CLM.copyFileUsingApacheCommonsIO(source, dest);
                } catch (IOException ex) {
                    Logger.getLogger(window.class.getName()).log(Level.SEVERE, null, ex);
                }
            System.out.println("Time taken by Apache Commons IO Copy = "+(System.nanoTime()-start)/ 1000000000.0);
            
            issend = false; // Zerowanie zmiennej
            // TU wstawić zapis informcji o wysyłce do pliku
            
            FM.save_send(filenametosend, CLM.md5_to_save, jLabel6.getText()); // Dopisywanie wysłanego poli do listy
            
            }
            else{// Check new version
               //  for(int i=0; i<FM.line;i++){
                   if(java.util.Objects.equals(tempHash,CLM.md5_to_check) == false ){
                   JOptionPane.showMessageDialog(rootPane, "Plik   " + filenametosend +"   zawiera zmiany\n Dlatego zostanie wysłany na Serwer", "Info", JOptionPane.INFORMATION_MESSAGE);
                   isnewversion = true; 
                   CLM.md5_to_check = null;
                   }  
                // }
                
                 
                 if(isnewversion == true){ // Wysyłamy jeszcze raz ten plik, tyle że w nowej wersji
                     CLM1.GetandSendFileNewVersion(usertosend,chooser.getCurrentDirectory().toString(), filenametosend); //Przechodzi do funkcji porzesyłającej pliki
                            jTextField1.setText("");

                            //
                              FileArchiverClient fac = new FileArchiverClient();
                            Context context6 = null;
                            try {
                                context6 = new InitialContext();
                                } catch (NamingException ex) {
                                    Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            try { 
                                context6.rebind("rmi:MyClientObject", fac);
                                } catch (NamingException ex) {
                                    Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                                }         
                            //


                                //copy files using apache commons io
                            File source = new File(filename);
                            File dest = new File(CLM.copy_path + File.separator + filenametosend);
                            System.out.println("clm path" + CLM.path);
                            System.out.println("dest copy " + CLM.path + filenametosend);
                            /*#############################################################################################
                            TU TO WSTAW
                            -wstawiłęm niżej, bo tutaj jeszcze przesłanie nie było wykonane

                            ##############################################################################################*/

                            long start = System.nanoTime();
                            try {
                                CLM.copyFileUsingApacheCommonsIO(source, dest);
                                } catch (IOException ex) {
                                    Logger.getLogger(window.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            System.out.println("Time taken by Apache Commons IO Copy = "+(System.nanoTime()-start)/ 1000000000.0);

                            issend = false; // Zerowanie zmiennej
                            // TU wstawić zapis informcji o wysyłce do pliku
                            // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Zamienić
                            FM.findANDsave_new_send(filenametosend, CLM.md5_to_save, jLabel6.getText()); // Dopisywanie wysłanego poli do listy
                            // Zmienić na update_send
                     
                     
                     
                 }else{
                   JOptionPane.showMessageDialog(rootPane, "Plik   " + filenametosend +"   znajduje się już na Serwerze\n I nie uległ zmianie", "Info", JOptionPane.ERROR_MESSAGE);
                  
                 }
                
                issend = false;
                isnewversion = false;
                System.out.println("ENDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDEEEEEEEEEEEEEEEEEEEEEEDDDDDDDDDDDDDDDD");
            }
            //tu
            
            } catch (NotBoundException | MalformedURLException ex) {
                Logger.getLogger(window.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(window.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            MsgPanel.append(Color.red, "Nie wybrano pliku! Wybierz ponownie");
        }
         //przyklad=null;
           // przyklad= new DefaultListModel();
        /////Tutaj to wstawiam bo dopiero tu działa
            przyklad.removeAllElements();
            listFilesForFolder(loc_user);
            jList1.setModel(przyklad);
      
        try {
            // TU URUCHOMiony zostaje kod aktualizacji PLIKÓW SERWERA
            try {
                CLM1.get_Server_name_File(user);
                CLM1.actualizeMessageC();
            } catch (NotBoundException ex) {
                Logger.getLogger(window.class.getName()).log(Level.SEVERE, null, ex);
            }
            see_serwer_names = ClientLocalMetods.server_file_name_to_see;
             Serwmodel=new DefaultListModel<>();
             String critic1=user+".pass";
             String critic2=user+".path";
             System.out.println("critic."+critic1+"."+critic2+".");
             for(int i=0;i<see_serwer_names.length;){
                 if(see_serwer_names[i].equalsIgnoreCase(critic1)||see_serwer_names[i].equalsIgnoreCase(critic2)){
                   Serwmodel.addElement("cannot see-top secret");
                   ++i;
                 }else{ Serwmodel.addElement(see_serwer_names[i]);
                     ++i;}
             };
             jList2.setModel(Serwmodel);
             jList2.addListSelectionListener((ListSelectionEvent e) -> {
                 String s = (String) jList2.getSelectedValue();
                 jLabel14.setText(s);
            });            
        } catch (MalformedURLException | RemoteException ex) {
            Logger.getLogger(window.class.getName()).log(Level.SEVERE, null, ex);
        }
            /////////////////////////////////////
        }
                        
                        };//koniec wątku
       dodawanie_pliku.start();
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        FileTreeFrame ft =new FileTreeFrame(loc_user);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        // FileTreeFrame ft =new FileTreeFrame(fileSystemModel,"Serwer");
        pobieranie_pliku=new Thread(){
        public void run(){
        String choose_file = jLabel14.getText();
        if(choose_file.equals("brak")== true){
            JOptionPane.showMessageDialog(rootPane, "Nie wybrano pliku");
        }else{
        try {
            // TODO add your handling code here:
            
            
            //FileArchiver service = (FileArchiver) Naming.lookup("rmi://localhost:1099/testrmiio");
          //  String choose_file = jLabel14.getText();
            String user_name1 = jLabel8.getText();
           // String filePath = 
            CLM1.choose_file(choose_file, user_name1);
            
            System.out.println("WYBRANO PLIK "+ choose_file+" USER"+user_name1);
            //service.getFileServer(choose_file, user_name);
            
            //
                              FileArchiverClient fac = new FileArchiverClient();
                            Context context12 = null;
                            try {
                                context12 = new InitialContext();
                                } catch (NamingException ex) {
                                    Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            try { 
                                context12.rebind("rmi:MyClientObject", fac);
                                } catch (NamingException ex) {
                                    Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                                }         
                            //
            
//            int licz =0;
//            if (licz == 0){
//            
//            licz++;
//            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
        
        }//koniec wątku
       
        };
        pobieranie_pliku.start();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:
//        jList1 list = (jList1)evt.getSource();
//        if (evt.getClickCount() == 2) {
//            int index = list.locationToIndex(evt.getPoint());
//        } else if (evt.getClickCount() == 3) {   // Triple-click
//            int index = list.locationToIndex(evt.getPoint());
//
    }//GEN-LAST:event_jList1MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        String choose_file = jLabel14.getText();
        if(choose_file.equals("brak")== true){
            JOptionPane.showMessageDialog(rootPane, "Nie wybrano pliku");
        }else{
        
        try {
            // TODO add your handling code here:
            
            
            //FileArchiver service = (FileArchiver) Naming.lookup("rmi://localhost:1099/testrmiio");
            //String choose_file = jLabel14.getText();
            String user_name1 = jLabel8.getText();
           // String filePath = 
            CLM1.delete_file(choose_file, user_name1);
            
            System.out.println("WYBRANO PLIK "+ choose_file+" DO UsUNIĘCIA USER"+user_name1);
            //service.getFileServer(choose_file, user_name);
            
            //
                              FileArchiverClient fac = new FileArchiverClient();
                            Context context18 = null;
                            try {
                                context18 = new InitialContext();
                                } catch (NamingException ex) {
                                    Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            try { 
                                context18.rebind("rmi:MyClientObject", fac);
                                } catch (NamingException ex) {
                                    Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            String uPath = jLabel6.getText();
            
               // FM.findANDdeleteFile(choose_file, uPath);
                //
                
//            int licz =0;
//            if (licz == 0){
//            
//            licz++;
//            }
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
          System.out.print("przed jestem");
        try {
            // TU URUCHOMiony zostaje kod aktualizacji
           
             System.out.print("1. jestem");
             System.out.println("USER "+ user);
            try {
                CLM1.get_Server_name_File(user);
                CLM1.actualizeMessage();
            } catch (NotBoundException ex) {
                Logger.getLogger(window.class.getName()).log(Level.SEVERE, null, ex);
            }
             System.out.print("2. jestem");
            see_serwer_names = CLM1.server_file_name_to_see;
             System.out.println("3 jestem liczba plikow z serwa="+see_serwer_names.length);
             Serwmodel=new DefaultListModel<>();
             String critic1=user+".pass";
             String critic2=user+".path";
             System.out.println("critic."+critic1+"."+critic2+".");
             for(int i=0;i<see_serwer_names.length;){
                 if(see_serwer_names[i].equalsIgnoreCase(critic1)||see_serwer_names[i].equalsIgnoreCase(critic2)){
                   Serwmodel.addElement("cannot see-top secret");
                   ++i;
                 }else{ Serwmodel.addElement(see_serwer_names[i]);
                     ++i;}
             };
             jList2.setModel(Serwmodel);
             
        
             jList2.addListSelectionListener((ListSelectionEvent e) -> {
                 String s = (String) jList2.getSelectedValue();
                 jLabel14.setText(s);
             });
             

        } catch (MalformedURLException | RemoteException ex) {
            Logger.getLogger(window.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        przyklad.removeAllElements();
            listFilesForFolder(loc_user);
            jList1.setModel(przyklad);
            CLM1.refreshMessage();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
         JOptionPane.showMessageDialog(FileArchiverClient.window , "Remote File Archive Client\nwykonany przez Piotr Dybcio i Michał Piekraski\nna przedmiot OPA\nListopad 2014", "About", JOptionPane.PLAIN_MESSAGE);
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        Desktop desktop = Desktop.getDesktop();
        File dirToOpen = null;
        try {
            String open = jLabel6.getText();
            dirToOpen = new File(open);
            desktop.open(dirToOpen);
        } catch (IllegalArgumentException iae) {
            System.out.println("File Not Found" + iae);
        } catch (IOException ex) {
            Logger.getLogger(window.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton7ActionPerformed
 //   }
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
            java.util.logging.Logger.getLogger(window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        //w ciul niepotrzebne tu nic ;)
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//               // new window().setVisible(true);
//                
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton jButton1;
    javax.swing.JButton jButton2;
    javax.swing.JButton jButton3;
    javax.swing.JButton jButton4;
    javax.swing.JButton jButton5;
    javax.swing.JButton jButton6;
    javax.swing.JButton jButton7;
    javax.swing.JLabel jLabel1;
    javax.swing.JLabel jLabel10;
    javax.swing.JLabel jLabel11;
    javax.swing.JLabel jLabel12;
    javax.swing.JLabel jLabel13;
    javax.swing.JLabel jLabel14;
    javax.swing.JLabel jLabel15;
    javax.swing.JLabel jLabel16;
    javax.swing.JLabel jLabel2;
    javax.swing.JLabel jLabel3;
    javax.swing.JLabel jLabel4;
    javax.swing.JLabel jLabel5;
    javax.swing.JLabel jLabel6;
    javax.swing.JLabel jLabel7;
    javax.swing.JLabel jLabel8;
    javax.swing.JLabel jLabel9;
    javax.swing.JList jList1;
    javax.swing.JList jList2;
    javax.swing.JMenu jMenu1;
    javax.swing.JMenu jMenu2;
    javax.swing.JMenuBar jMenuBar1;
    javax.swing.JMenuItem jMenuItem1;
    javax.swing.JMenuItem jMenuItem2;
    javax.swing.JPanel jPanel1;
    javax.swing.JPanel jPanel2;
    javax.swing.JScrollPane jScrollPane1;
    javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JScrollPane jScrollPane4;
    javax.swing.JSeparator jSeparator1;
    javax.swing.JSeparator jSeparator2;
    javax.swing.JSeparator jSeparator3;
    javax.swing.JTextField jTextField1;
    public static javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables

}