

import javax.swing.JFrame;

/**
 * Created by Wojciech Zdzarski on 14.12.2016.
 * Class responsible for AWT/Swing Api
 */

public class WinApp extends JFrame {
    // Jesli zdzaze to tu będzie wersja okienkowa
    public WinApp() {
        super("Sejmometr Project");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}

