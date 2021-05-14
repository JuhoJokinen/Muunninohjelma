package muunninohjelma;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;

import java.text.*;
import javax.swing.text.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;


public class Muunnosrivi extends JPanel implements PropertyChangeListener{

    public JFormattedTextField alkuArvoKentta;
    public JTextField loppuArvoKentta;
    public JComboBox<String> alkuYksikkoValikko;
    public JComboBox<String> loppuYksikkoValikko;
    
    public Muunnosrivi(JFrame paaIkkuna) {

        setBackground(Color.WHITE);
        Border raja = BorderFactory.createLineBorder(Color.BLACK);

        JPanel vasenPaneeli = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        vasenPaneeli.setBackground(Color.WHITE);
        vasenPaneeli.setBorder(raja);
        
        NumberFormat format = new DecimalFormat("###.#####");
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Double.class);
        formatter.setAllowsInvalid(true);
        formatter.setCommitsOnValidEdit(true);
               
        //tekstikenttä alkuarvolle
        alkuArvoKentta =  new JFormattedTextField(formatter) {
            //reunan poisto
            @Override
            public void setBorder(Border border){
            }
        };
        alkuArvoKentta.setColumns(10);
        alkuArvoKentta.addPropertyChangeListener("value", this);
        vasenPaneeli.add(alkuArvoKentta);

        //alkuarvon yksikön valinta
        String [] alkuYksikkoLista = {"Etäisyys", "   kilometri", "   metri", "   senttimetri", "   maili", "   jalka", "   tuuma", "Paino", "   kilogramma", "   gramma", "   pauna", "   unssi", "Lämpötila", "   celsius", "   fahrenheit", "   kelvin", "Kulma", "   aste", "   radiaani", "Nopeus", "   metriä sekunnissa", "   kilometriä tunnissa", "   mailia tunnissa"};
        alkuYksikkoValikko = new JComboBox<String>(alkuYksikkoLista) {
            //comboboxin väliotsikoiden valinnan estäminen
            @Override
            public void setSelectedItem(Object item) {
                if(item.equals("Etäisyys") || item.equals("Paino") || item.equals("Lämpötila") || item.equals("Kulma") || item.equals("Nopeus"))
                    return;
                super.setSelectedItem(item);
            }
        };
        alkuYksikkoValikko.setSelectedIndex(1);
        vasenPaneeli.add(alkuYksikkoValikko);

        add(vasenPaneeli);

        //nuoli-ikoni
        ImageIcon nuoliIkoni = createImageIcon("images\\nuoli.png", "nuoli");
        JLabel nuoli = new JLabel(nuoliIkoni);
        add(nuoli);

        JPanel oikeaPaneeli = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        oikeaPaneeli.setBackground(Color.WHITE);
        oikeaPaneeli.setBorder(raja);

        //tekstikenttä loppuarvolle
        loppuArvoKentta = new JTextField(10) {
            //reunan poisto
            @Override
            public void setBorder(Border border){
            }
        };
        loppuArvoKentta.setEditable(false);
        loppuArvoKentta.setOpaque(false);
        oikeaPaneeli.add(loppuArvoKentta);

        //loppuarvon yksikön valinta
        loppuYksikkoValikko = new JComboBox<String>();

        //loppuyksikkövalikon muokkaus valitun alkuyksikön perusteella
        alkuYksikkoValikko.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int alkuYksikkoValinta = alkuYksikkoValikko.getSelectedIndex();
                loppuYksikkoValikko.removeAllItems();
                switch (alkuYksikkoValinta) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        loppuYksikkoValikko.addItem("   kilometri");
                        loppuYksikkoValikko.addItem("   metri");
                        loppuYksikkoValikko.addItem("   senttimetri");
                        loppuYksikkoValikko.addItem("   maili");
                        loppuYksikkoValikko.addItem("   jalka");
                        loppuYksikkoValikko.addItem("   tuuma");
                        break;
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                        loppuYksikkoValikko.addItem("   kilogramma");
                        loppuYksikkoValikko.addItem("   gramma");
                        loppuYksikkoValikko.addItem("   pauna");
                        loppuYksikkoValikko.addItem("   unssi");
                        break;
                    case 13:
                    case 14:
                    case 15:
                        loppuYksikkoValikko.addItem("   celsius");
                        loppuYksikkoValikko.addItem("   fahrenheit");
                        loppuYksikkoValikko.addItem("   kelvin");
                        break;
                    case 17:
                    case 18:
                        loppuYksikkoValikko.addItem("   aste");
                        loppuYksikkoValikko.addItem("   radiaani");
                        break;
                    case 20:
                    case 21:
                    case 22:
                        loppuYksikkoValikko.addItem("   metriä sekunnissa");
                        loppuYksikkoValikko.addItem("   kilometriä tunnissa");
                        loppuYksikkoValikko.addItem("   mailia tunnissa");
                        break;
                }
                loppuYksikkoValikko.removeItem(alkuYksikkoValikko.getSelectedItem());  
                
                
            }
            
        });

        loppuYksikkoValikko.setPrototypeDisplayValue("   kilometriä tunnissa"); //valikon leveyden säätö pisimmän mahdollisen arvon mukaan
        oikeaPaneeli.add(loppuYksikkoValikko);
        
        //kuunnellaan milloin käyttäjä valitsee loppuyksikön ja tehdään muunnos
        loppuYksikkoValikko.addPopupMenuListener(new PopupMenuListener(){
            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {                
            }
            
            @Override
             public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                muunnos();
            }

            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {                
            }       
        });
        
               
        add(oikeaPaneeli);

        //Poista rivi -nappi
        ImageIcon poistaIkoni = createImageIcon("images\\poista.png", "poista rivi");
        JButton poista = new JButton(poistaIkoni);
        poista.setToolTipText("Poista rivi");
        poista.setFocusPainted(false);
        poista.setMargin(new Insets(0, 0, 0, 0));
        poista.setContentAreaFilled(false);
        poista.setBorderPainted(false);
        poista.setOpaque(false);
        poista.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                setVisible(false);
                naytaPoistoIlmoitus(paaIkkuna);
            }
        });
        add(poista);

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
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        muunnos();
    } 
    
    public void muunnos(){
        double arvo = (double)alkuArvoKentta.getValue();
        double valiArvo = 0;
        double loppuArvo = 0;
        //muuttaa syötetyn arvon väliarvoksi
        switch (alkuYksikkoValikko.getSelectedIndex()) {
                    case 1:
                        valiArvo = arvo * 1000;//km -> m
                        break;
                    case 2:
                        valiArvo = arvo;//m -> m
                        break;
                    case 3:
                        valiArvo = arvo/100;//cm -> m
                        break;
                    case 4:
                        valiArvo = arvo * 1609;//mile -> m
                        break;
                    case 5:
                        valiArvo = arvo / 3.2808;//ft -> m
                        break;
                    case 6:
                        valiArvo = arvo / 39.370;//in -> m
                        break;
                    case 8:
                        valiArvo = arvo * 1000;//kg -> g
                        break;
                    case 9:
                        valiArvo = arvo;//g -> g
                        break;
                    case 10:
                        valiArvo = arvo / 0.0022046;//lb -> g
                        break;
                    case 11:
                        valiArvo = arvo / 0.035274;//oz -> g
                        break;
                    case 13:
                        valiArvo = arvo;// c -> c
                        break;
                    case 14:
                        valiArvo = (arvo - 32) / 1.8;//f -> c
                        break;
                    case 15:
                        valiArvo = arvo - 273.15;//k -> c
                        break;
                    case 17:
                        valiArvo = arvo;//degree -> degree
                        break;
                    case 18:
                        valiArvo = arvo * 57.296;//rad -> degree
                        break;
                    case 20:
                        valiArvo = arvo;//m/s -> m/s
                        break;
                    case 21:
                        valiArvo = (arvo * 1000) / 3600;//km/h -> m/s
                        break;
                    case 22:
                        valiArvo = (arvo * 1609) / 3600;//mile/h -> m/s
                        break;
                    default: 
                        valiArvo = 0;
                                               
                }
        //valiarvo muutetaan loppuarvoksi.
        switch (loppuYksikkoValikko.getItemAt(loppuYksikkoValikko.getSelectedIndex())) {
                    case "   kilometri":
                        loppuArvo = valiArvo / 1000;
                        break;
                    case "   metri":
                        loppuArvo = valiArvo;
                        break;
                    case "   senttimetri":
                        loppuArvo = valiArvo * 100;
                        break;
                    case "   maili":
                        loppuArvo = valiArvo * 0.00062137;
                        break;
                    case "   jalka":
                        loppuArvo = valiArvo * 3.2808;
                        break;
                    case "   tuuma":
                        loppuArvo = valiArvo * 39.370;
                        break;
                    case "   kilogramma":
                        loppuArvo = valiArvo / 1000;
                        break;
                    case "   gramma":
                        loppuArvo = valiArvo;
                        break;  
                    case "   pauna":
                        loppuArvo = valiArvo * 0.0022046;
                        break;
                    case "   unssi":
                        loppuArvo = valiArvo * 0.035274;
                        break;
                    case"   celsius":
                        loppuArvo = valiArvo;
                        break;
                    case "   fahrenheit":
                        loppuArvo = (valiArvo * 1.8) + 32;
                        break;
                    case "   kelvin":
                        loppuArvo = valiArvo + 273.15;
                        break;
                    case "   aste":
                        loppuArvo = valiArvo;
                        break;
                    case "   radiaani":
                        loppuArvo = (valiArvo * 3.14159) / 180;
                        break;
                    case "   metriä sekunnissa":
                        loppuArvo = valiArvo;
                        break;
                    case "   kilometriä tunnissa":
                        loppuArvo = valiArvo * 3.6;
                        break;
                    case "   mailia tunnissa":
                        loppuArvo = valiArvo * 2.23694;
                        break;
                    default: 
                        loppuArvo = 0;
                        break;
                } 
        loppuArvoKentta.setText(String.format("%.4f", (double)loppuArvo));
    } 
    
    //ilmoituksen näyttäminen rivin poistosta ja kumoamistoiminto
    private void naytaPoistoIlmoitus(JFrame paaIkkuna){
        JFrame poistoIlmoitus = new JFrame();
        poistoIlmoitus.setLayout(new BoxLayout(poistoIlmoitus.getContentPane(), BoxLayout.PAGE_AXIS));
        poistoIlmoitus.setUndecorated(true);
        poistoIlmoitus.setSize(200, 100);
        
        //ilmoituksen sijainnin määrittely
        Rectangle r = paaIkkuna.getBounds();
        int x = paaIkkuna.getLocationOnScreen().x;
        int y = paaIkkuna.getLocationOnScreen().y;
        poistoIlmoitus.setLocation(x+20, y+r.height-120);
        
        JLabel teksti = new JLabel("Rivi poistettu");
        JButton kumoa = new JButton("Kumoa");
        
        poistoIlmoitus.add(Box.createVerticalGlue());
        poistoIlmoitus.add(teksti);
        teksti.setAlignmentX(CENTER_ALIGNMENT);
        poistoIlmoitus.add(Box.createVerticalGlue());
        poistoIlmoitus.add(kumoa);
        kumoa.setAlignmentX(CENTER_ALIGNMENT);
        poistoIlmoitus.add(Box.createVerticalGlue());
        
        kumoa.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                setVisible(true);
                poistoIlmoitus.dispose();
            }
        });
        
        poistoIlmoitus.setVisible(true);
        poistoIlmoitus.setAlwaysOnTop(true);
        
        //sulkeutuminen 5 sekunnissa
        new Thread(){
            @Override
            public void run(){
                try{
                    Thread.sleep(5000);
                    poistoIlmoitus.dispose();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            };
        }.start();
        
    }

}
