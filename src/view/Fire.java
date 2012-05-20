package view;
import java.awt.*;
import javax.swing.*;


import java.awt.event.*;

/**
 *
 * @author Marshall
 */
public class Fire extends JPanel implements ActionListener {
    private JButton fire = new JButton();
    private GameView window;


    /**
     * creates the fire button
     * @param newWindow BattleshipGame
     */
    public Fire(GameView newWindow) {
        window = newWindow;
        setOpaque(false);


        BorderLayout bl = new BorderLayout();

        setLayout(bl);
        fire.setText("Fire!");

        add(fire, BorderLayout.CENTER);

        fire.addActionListener(this);

    }
    
    public void enable() {
    	fire.setEnabled(true);
    }
    
    public void disable() {
    	fire.setEnabled(false);
    }

    /**
     * calls the fire method when the firebutton is clicked
     * @param ae ActionEvent
     */
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        if (source == fire) {
            window.fireCallback();
        }
    }


    
}
