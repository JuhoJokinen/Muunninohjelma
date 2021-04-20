
package muunninohjelma;

import javax.swing.*;       
import java.awt.*;           
import java.awt.event.*;    
import javax.swing.border.Border;

public class Muunninohjelma extends JFrame{
    
    

    public Muunninohjelma() {
        teeIkkuna();    
    }
    
    private void teeIkkuna(){
        setUndecorated(true);
        setSize(1000,600);
        Border raja = BorderFactory.createLineBorder(Color.BLACK);
        JMenuBar ylaPaneeli = new JMenuBar();
        ylaPaneeli.setBorder(raja);
        
        JButton suljeNappi = new JButton("X");
        JButton minimoi = new JButton("-");
        JButton maximoi = new JButton("[]");
        JButton asetukset = new JButton("Asetukset");
        JButton ohjeet = new JButton("Ohjeet");
        JLabel titteli = new JLabel("Muunninohjelma");
        
        suljeNappi.addActionListener((event) -> System.exit(0));
        
        ylaPaneeli.add(asetukset);
        ylaPaneeli.add(ohjeet);
        ylaPaneeli.add(Box.createHorizontalGlue());
        ylaPaneeli.add(titteli);
        ylaPaneeli.add(Box.createHorizontalGlue());
        ylaPaneeli.add(minimoi);
        ylaPaneeli.add(maximoi);
        ylaPaneeli.add(suljeNappi);
        
        this.add(ylaPaneeli, BorderLayout.PAGE_START);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        
    }
    
    
    public static void main(String[] args) {
        Muunninohjelma ikkuna = new Muunninohjelma();
        ikkuna.setVisible(true);
        
        
        
    }
    
}
