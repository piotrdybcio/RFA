/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filearchiverserver;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author Piotr
 */
public class MessagePanel extends JTextPane{
    
    FileArchiverServer FAS;
    window window;
    
    public void append(Color c, String s) {
        
        /*DATE & TIME*/
       
        DateFormat dateFormat1 = new SimpleDateFormat("HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String time = dateFormat1.format(cal.getTime()).toString();
        
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        int len = getDocument().getLength();
        window.jTextPane1.setCaretPosition(len); 
        window.jTextPane1.replaceSelection(time + " " +s+"\n");
        window.jTextPane1.setCharacterAttributes(aset, false);
        
    }
    
}
