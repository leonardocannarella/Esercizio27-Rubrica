import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class RubricaTelefonica extends JPanel implements ActionListener
{
    //Rubrica
    JLabel titolo;
    JList lista;
    DefaultListModel model;
    JButton inserisci, elimina, salvaRubrica, caricaRubrica;

    //Inserisci contatto
    NominativoFrame nomF;
    JLabel title, nome, cognome, sesso, numero;
    JTextField no, cog, num;
    JRadioButton maschio, femmina;
    ButtonGroup grp;
    JButton aggiungi;


    public RubricaTelefonica()
    {
        //Rubrica
        titolo = new JLabel("NUMERI DI TELEFONO", SwingConstants.CENTER);
            titolo.setBounds(20,0,385,50);
            titolo.setFont(titolo.getFont().deriveFont(20.0f));
        model = new DefaultListModel();
        lista = new JList(model);
            lista.setBounds(20,60,385,400);
        inserisci = new JButton("Inserisci");
            inserisci.setBounds(20,490,100,30);
        elimina = new JButton("Elimina");
            elimina.setBounds(305,490,100,30);
        salvaRubrica = new JButton("Salva");
            salvaRubrica.setBounds(20,550,100,30);
        caricaRubrica = new JButton("Carica");
            caricaRubrica.setBounds(305,550,100,30);

        //Inserisci contatto
        nomF = new NominativoFrame("NOMINATIVO");
            Container c = nomF.getContentPane();
            JPanel nomP = new JPanel();
            c.add(nomP);
        title = new JLabel("NOMINATIVO");
        nome = new JLabel("Nome:");
        cognome = new JLabel("Cognome:");
        sesso = new JLabel("Sesso:");
        numero = new JLabel("Numero:");
        maschio = new JRadioButton("M");
            maschio.setSelected(true);
        femmina = new JRadioButton("F");
        grp = new ButtonGroup();
            grp.add(maschio);
            grp.add(femmina);
        aggiungi = new JButton("Aggiungi");
        no = new JTextField();
        cog = new JTextField();
        num = new JTextField();

        //Rubrica
        setLayout(null);
        add(titolo);
        add(lista);
        add(inserisci);
        add(elimina);
        add(salvaRubrica);
        add(caricaRubrica);

        //Inserisci contatto
        nomP.setLayout(new GridLayout(7,3));
        nomP.add(new JLabel());  nomP.add(title);        nomP.add(new JLabel());
        nomP.add(nome);          nomP.add(new JLabel());  nomP.add(no);
        nomP.add(cognome);       nomP.add(new JLabel());  nomP.add(cog);
        nomP.add(sesso);         nomP.add(maschio);       nomP.add(femmina);
        nomP.add(numero);        nomP.add(new JLabel());  nomP.add(num);
        nomP.add(new JLabel());  nomP.add(new JLabel());  nomP.add(new JLabel());
        nomP.add(new JLabel());  nomP.add(aggiungi);      nomP.add(new JLabel());


        //Rubrica
        inserisci.addActionListener(this);
        elimina.addActionListener(this);
        salvaRubrica.addActionListener(this);
        caricaRubrica.addActionListener(this);

        //Inserisci contatto
        aggiungi.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        Object pulsantePremuto = e.getSource();

        if(pulsantePremuto==inserisci)
        {
            if(!nomF.isVisible())
            {
                nomF.setVisible(true);
            }
        }

        if(pulsantePremuto==aggiungi)
        {
            if(!no.getText().equals(""))
            {
                if(!cog.getText().equals(""))
                {
                    if(maschio.isSelected()||femmina.isSelected())
                    {
                        if(!num.getText().equals(""))
                        {
                            model.addElement(no.getText()+" "+cog.getText());
                            JOptionPane.showMessageDialog(this, "Contatto aggiunto con successo!");
                            no.setText("");
                            cog.setText("");
                            maschio.setSelected(true);
                            femmina.setSelected(false);
                            num.setText("");
                            nomF.setVisible(false);
                        }
                        else
                            JOptionPane.showMessageDialog(this, "Errore! Inserisci un numero valido.");
                    }
                }
                else
                    JOptionPane.showMessageDialog(this, "Errore! Inserisci un cognome valido.");
            }
            else
                JOptionPane.showMessageDialog(this, "Errore! Inserisci un nome valido.");
        }

        if(pulsantePremuto==elimina)
        {
            int indice = lista.getSelectedIndex();
            if(indice==-1)
            {
                JOptionPane.showMessageDialog(this, "Errore! Seleziona un contatto da eliminare.");
            }
            else
            {
                model.remove(indice);
            }
        }

        if(pulsantePremuto==salvaRubrica)
        {
            try {
                salvaRubrica();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if(pulsantePremuto==caricaRubrica)
        {
            try {
                caricaRubrica();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void salvaRubrica() throws IOException
    {
        ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("rubrica.bin"));
        stream.writeObject(this.model);
        stream.close();
    }

    public void caricaRubrica() throws IOException
    {
        ObjectInputStream stream = new ObjectInputStream(new FileInputStream("rubrica.bin"));
        try
        {
            this.model = (DefaultListModel)stream.readObject();
            this.lista.setModel(model);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        stream.close();
    }
}
