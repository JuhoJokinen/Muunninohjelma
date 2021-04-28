
package muunninohjelma;

import javax.swing.*;       
import java.awt.*;           
import java.awt.event.*;    
import javax.swing.border.Border;

public class Muunninohjelma extends JFrame{
    
    private static Point point = new Point();
    

    public Muunninohjelma() {
        //ikkunan luonti tehdään mutkan kautta jottei tapahdu virheitä
        teeIkkuna();    
    }
    
    private void teeIkkuna(){
        //ikkunan ja yläpaneelin luonti
        setUndecorated(true);
        setSize(1000,600);
        Border raja = BorderFactory.createLineBorder(Color.BLACK);
        JMenuBar ylaPaneeli = new JMenuBar();
        ylaPaneeli.setBorder(raja);
        
        //nappien alustamista
        JButton suljeNappi = new JButton("X");
        JButton minimoi = new JButton("--");
        JButton maximoi = new JButton("[]");
        JButton asetukset = new JButton("Asetukset");
        JButton ohjeet = new JButton("Ohjeet");
        JLabel titteli = new JLabel("Muunninohjelma");
        
        //ohjelman sulkeminen
        suljeNappi.addActionListener((e) -> System.exit(0));
        
        //ruudun maximointi ja palauttaminen
        maximoi.addActionListener((e) -> {        
            Dimension ruutukoko =  Toolkit.getDefaultToolkit().getScreenSize();
            
            if( ruutukoko.equals(getSize())){
                setSize(1000, 600);
            } else {
                setSize(ruutukoko.width, ruutukoko.height);
            }
        });
        
        //ikkunan siirtelyä yläpaneelista
        ylaPaneeli.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                point.x = e.getX();
                point.y = e.getY();
            }
        });

        ylaPaneeli.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point p = getLocation();
                setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
            }
        });
        
        
        //nappien sijoittaminen yläpaneeliin
        ylaPaneeli.add(asetukset);
        ylaPaneeli.add(ohjeet);
        ylaPaneeli.add(Box.createHorizontalGlue());
        ylaPaneeli.add(titteli);
        ylaPaneeli.add(Box.createHorizontalGlue());
        ylaPaneeli.add(minimoi);
        ylaPaneeli.add(maximoi);
        ylaPaneeli.add(suljeNappi);
        
        add(ylaPaneeli, BorderLayout.PAGE_START);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        
    }
    
    
    public static void main(String[] args) {
        Muunninohjelma ikkuna = new Muunninohjelma();
        ikkuna.setVisible(true);
        
        
        
    }
    
}
