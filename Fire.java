import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 *
 * @author Marshall
 */
public class Fire extends JPanel implements ActionListener {
    private JButton fire = new JButton();
    private BattleshipGame window;


    /**
     * creates the fire button
     * @param newWindow BattleshipGame
     */
    public Fire(BattleshipGame newWindow) {
        this.window = newWindow;
        this.setOpaque(false);


        BorderLayout bl = new BorderLayout();

        this.setLayout(bl);
        fire.setText("Fire!");

        add(fire, BorderLayout.CENTER);

        fire.addActionListener(this);

    }

    /**
     * calls the fire method when the firebutton is clicked
     * @param ae ActionEvent
     */
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        if (source == fire) {
            window.fire();
        }
    }


    
}
