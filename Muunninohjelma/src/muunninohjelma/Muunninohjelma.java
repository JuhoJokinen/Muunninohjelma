
package muunninohjelma;

import javax.swing.*;       
import java.awt.*;           
import java.awt.event.*;    
import javax.swing.border.Border;

public class Muunninohjelma extends JFrame{
    
    

    public Muunninohjelma() {
        //ikkunan luonti tehdään mutkan kautta jottei tapahdu virheitä
        teeIkkuna();    
    }
    
    private void teeIkkuna(){
        //tallennetaan viittaus pääikkunaan
        JFrame frame = this;

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

        //asetukset-ikkuna
        asetukset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JPanel asetusPaneeli = new JPanel();
                asetusPaneeli.setLayout(new GridLayout(2, 2, 5, 5));

                asetusPaneeli.add(new JLabel("Merkitsevien numeroiden määrä"));
                String [] merkNumLista = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
                JComboBox<String> merkNumValikko = new JComboBox<String>(merkNumLista);
                merkNumValikko.setSelectedIndex(3);
                asetusPaneeli.add(merkNumValikko);

                asetusPaneeli.add(new JLabel("Fonttikoko"));
                String [] fonttiKokoLista = {"8", "10", "12", "14", "16", "20", "24"};
                JComboBox<String> fonttiKokoValikko = new JComboBox<String>(fonttiKokoLista);
                fonttiKokoValikko.setSelectedIndex(2);
                asetusPaneeli.add(fonttiKokoValikko);

                Object [] valinnat = {"Tallenna ja sulje", "Sulje"};
                JOptionPane.showOptionDialog(frame, asetusPaneeli, "Asetukset", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, valinnat, valinnat[0]);
            }
        });
        
        //ohjeet-ikkuna
        ohjeet.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Object [] valinnat = {"Sulje"};
                JOptionPane.showOptionDialog(frame, "Ohjelmaa käytetään syöttämällä luku alkuarvo-\nkenttään, valitsemalla sille yksikkö valikosta ja\nvalitsemalla loppuyksikölle tyyppi.\n\nPainamalla plus-merkkiä voi lisätä uuden rivin, johon\nvoi laittaa muita arvoja muutettavaksi. Rivin oikeassa\nyläkulmassa on raksi, josta rivin voi poistaa.", 
                "Ohjeet", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, valinnat, valinnat[0]);
            }
        });
        
        //ohjelman sulkeminen
        suljeNappi.addActionListener((event) -> System.exit(0));
        
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
