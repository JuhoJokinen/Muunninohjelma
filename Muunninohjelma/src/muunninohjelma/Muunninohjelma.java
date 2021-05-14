
package muunninohjelma;

import javax.swing.*;       
import java.awt.*;           
import java.awt.event.*;    
import javax.swing.border.Border;

public class Muunninohjelma extends JFrame{
    
    private static Point point = new Point();
    private Font fontti = new Font("dialog", Font.PLAIN, 12);
    private int fonttiValinta = 2;
    public int merkNum = 4;
    private int merkValinta = 1;
    

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
        JPanel keskiPaneeli = new JPanel();
        keskiPaneeli.setBackground(Color.WHITE);
        ylaPaneeli.setBorder(raja);
        rootPane.setBorder(raja);
        
        //nappien alustamista
        JButton suljeNappi = new JButton("X");
        JButton minimoi = new JButton("--");
        JButton maximoi = new JButton("[]");
        JButton asetukset = new JButton("Asetukset");
        JButton ohjeet = new JButton("Ohjeet");
        JLabel titteli = new JLabel("Muunninohjelma");
        JScrollPane vieritys = new JScrollPane(keskiPaneeli);
        
        ImageIcon lisaaIkoni = createImageIcon("images\\lisaa.png", "lisää rivi");
        JButton lisaaRivi = new JButton(lisaaIkoni);
        lisaaRivi.setToolTipText("Lisää muunnosrivi");
        lisaaRivi.setFocusPainted(false);
        lisaaRivi.setMargin(new Insets(0, 0, 0, 0));
        lisaaRivi.setContentAreaFilled(false);
        lisaaRivi.setBorderPainted(false);
        lisaaRivi.setOpaque(false);
        
        //asetukset-ikkuna
        asetukset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JPanel asetusPaneeli = new JPanel();
                asetusPaneeli.setLayout(new GridLayout(2, 2, 5, 5));

                asetusPaneeli.add(new JLabel("Merkitsevien numeroiden määrä"));
                Integer [] merkNumLista = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                JComboBox<Integer> merkNumValikko = new JComboBox<Integer>(merkNumLista);
                merkNumValikko.setSelectedIndex(merkValinta);
                asetusPaneeli.add(merkNumValikko);

                asetusPaneeli.add(new JLabel("Fonttikoko"));
                Integer [] fonttiKokoLista = {8, 10, 12, 14, 16, 20, 24};
                JComboBox<Integer> fonttiKokoValikko = new JComboBox<Integer>(fonttiKokoLista);
                fonttiKokoValikko.setSelectedIndex(fonttiValinta);
                asetusPaneeli.add(fonttiKokoValikko);

                Object [] valinnat = {"Tallenna ja sulje", "Sulje"};
                int valinta = JOptionPane.showOptionDialog(frame, asetusPaneeli, "Asetukset", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, valinnat, valinnat[0]);
                if(valinta == 0){
                    int fonttiKoko = fonttiKokoValikko.getItemAt(fonttiKokoValikko.getSelectedIndex());
                    fonttiValinta = fonttiKokoValikko.getSelectedIndex();//uuden fonttikoon paikka listassa
                    fontti = new Font("dialog", Font.PLAIN, fonttiKoko);//luodaan uusi fontti
                    muutaFontti(keskiPaneeli);//uusi fontti asetetaan kaikkiin keskipaneelissa oleviin komponentteihin
                    
                    merkNum = merkNumValikko.getItemAt(merkNumValikko.getSelectedIndex());
                    merkValinta = merkNumValikko.getSelectedIndex();
                }
            }
        });
        
        //ohjeet-ikkuna
        ohjeet.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Object [] valinnat = {"Sulje"};
                String viesti = "Ohjelmaa käytetään syöttämällä luku alkuarvon kenttään, valitsemalla sille yksikkö valikosta ja valitsemalla loppuyksikölle tyyppi. Painamalla plus-merkkiä voi lisätä uuden rivin, johon voi laittaa muita arvoja muutettavaksi. Rivin oikeassa yläkulmassa on raksi, josta rivin voi poistaa.";
                JLabel teksti = new JLabel("<html><p style=\"width:400px\">"+viesti+"</p></html>");//asettaa tekstirivin maksimileveyden neljäänsataan pixeliin
                teksti.setFont(fontti);
                JOptionPane.showOptionDialog(frame, teksti, "Ohjeet", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, valinnat, valinnat[0]);
                
            }
        });
        
        //ohjelman sulkeminen
        suljeNappi.addActionListener((e) -> System.exit(0));
        
        //ruudun maximointi ja palauttaminen
        maximoi.addActionListener((e) -> {                   
            if(getExtendedState() == Frame.MAXIMIZED_BOTH){
                setExtendedState(NORMAL);
            } else {
                setExtendedState(MAXIMIZED_BOTH);
            }
        });
        
        //Ohjelman minimointi taskbaariin
        minimoi.addActionListener((e) ->{
            setState(ICONIFIED);
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
        
        vieritys.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        
        
        //komponentin lisääminen dynaamisesti
        lisaaRivi.addActionListener((e) -> {

            JPanel rivi = new Muunnosrivi(frame);
            keskiPaneeli.add(rivi);

            keskiPaneeli.add(lisaaRivi);
            keskiPaneeli.add(Box.createRigidArea(new Dimension(0, 50)));
            keskiPaneeli.add(Box.createVerticalGlue());
            muutaFontti(keskiPaneeli);
            keskiPaneeli.revalidate();
        
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
        
        //keskipaneelin layout
        keskiPaneeli.setLayout(new BoxLayout(keskiPaneeli, BoxLayout.PAGE_AXIS));
        keskiPaneeli.add(Box.createVerticalGlue());
        JPanel rivi = new Muunnosrivi(frame);
        keskiPaneeli.add(rivi);
        keskiPaneeli.add(lisaaRivi);
        keskiPaneeli.add(Box.createRigidArea(new Dimension(0, 50)));
        keskiPaneeli.add(Box.createVerticalGlue());
        lisaaRivi.setAlignmentX(CENTER_ALIGNMENT);
        muutaFontti(keskiPaneeli);
        
        
        add(ylaPaneeli, BorderLayout.PAGE_START);
        add(vieritys, BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        
    }   
    
    public static void main(String[] args) {
        
        Muunninohjelma ikkuna = new Muunninohjelma();
        ikkuna.setVisible(true);
        
               
    }   
    
    //rekursiivisesti vaihtaa komponentin sisällön fonttikoon. 
    //kopioitu verkosta
    public void muutaFontti(Component component){       
    component.setFont(fontti);
    
    if(component instanceof Container){
        for(Component child:((Container)component).getComponents()){
            muutaFontti(child);
        }
    }
    }
    
    protected ImageIcon createImageIcon(String polku, String kuvaus) {
        java.net.URL url = getClass().getResource(polku);
        if(url != null) {
            return new ImageIcon(url, kuvaus);
        } else {
            System.err.println("Tiedostoa " + polku + " ei löytynyt");
            return null;
        }
    }
    
}
