import javax.swing.*;
import java.awt.*;

public class Test
{
    public static void main(String[] args) {
        MyFrame f = new MyFrame("Rubrica");
        RubricaTelefonica rubrica = new RubricaTelefonica();
        Container c = f.getContentPane();
        c.add(rubrica);

        f.setVisible(true);
    }
}
