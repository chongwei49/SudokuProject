package sudoku;

import java.awt.*;
import javax.swing.*;

public class LoadingDialog extends JDialog {

    static String dir = System.getProperty("user.dir").replace("\\", "/");
    private JLabel loadingLabel = new JLabel(new ImageIcon(dir + "/src/sudoku/Resource/loading-buffering.gif"));
    

    public LoadingDialog(){
        super(new JFrame(), true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        getRootPane().setOpaque(false);
        getContentPane ().setBackground (new Color (0, 0, 0, 0));
        setBackground (new Color (0, 0, 0, 0));
        add(loadingLabel);
        pack();
        setVisible(true);
    }

    void stop(){
        setVisible(false);
    }
}