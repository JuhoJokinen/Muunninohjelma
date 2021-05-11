package muunninohjelma;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;

import java.text.*;
import javax.swing.text.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class Muunnosrivi extends JPanel implements PropertyChangeListener{

    public JFormattedTextField alkuArvoKentta;
    public JTextField loppuArvoKentta;
    
    public Muunnosrivi() {

        setBackground(Color.WHITE);
        Border raja = BorderFactory.createLineBorder(Color.BLACK);

        JPanel vasenPaneeli = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        vasenPaneeli.setBackground(Color.WHITE);
        vasenPaneeli.setBorder(raja);
        
        NumberFormat format = new DecimalFormat("###.####");
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
        JComboBox<String> alkuYksikkoValikko = new JComboBox<String>(alkuYksikkoLista) {
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
        JComboBox<String> loppuYksikkoValikko = new JComboBox<String>();

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

        //valikon leveyden säätö pisimmän mahdollisen arvon mukaan
        loppuYksikkoValikko.setPrototypeDisplayValue("   kilometriä tunnissa");
        oikeaPaneeli.add(loppuYksikkoValikko);

        add(oikeaPaneeli);

        //Poista rivi -nappi
        ImageIcon poistaIkoni = createImageIcon("images\\poista.png", "poista rivi");
        JLabel poista = new JLabel(poistaIkoni);
        poista.setToolTipText("Poista rivi");
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
        double arvo = (double)alkuArvoKentta.getValue();
        
        double loppuarvo = arvo*2;
        loppuArvoKentta.setText(String.valueOf(loppuarvo));
    } 

}
