import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


/**
 * creates the arrows to move the ships
 * @author Marshall
 */
public class Arrows extends JPanel implements ActionListener {
    private Arena arena;


    private JButton left = new JButton();
    private JButton right = new JButton();
    private JButton top = new JButton();
    private JButton bottom = new JButton();
    private JButton rotate = new JButton();
    private JButton play = new JButton();
    private BattleshipGame window;

    /**
     * creates the arrows
     * @param a Arena
     * @param newWindow BattleshipGame
     */
    public Arrows(Arena a, BattleshipGame newWindow) {
        this.window = newWindow;
        JPanel panel = new JPanel();
        arena = a;
        BorderLayout bl = new BorderLayout();
        BorderLayout b2 = new BorderLayout();
        this.setLayout(bl);
        panel.setLayout(b2);

        

        left.setText("Left");
        right.setText("Right");
        top.setText("Up");
        bottom.setText("Down");
        rotate.setText("R");
        play.setText("Play");
        
        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);
        add(rotate, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.NORTH);
        panel.add(play, BorderLayout.SOUTH);
        add(panel, BorderLayout.SOUTH);

        left.addActionListener(this);
        right.addActionListener(this);
        top.addActionListener(this);
        bottom.addActionListener(this);
        rotate.addActionListener(this);
        play.addActionListener(this);

    }

    /**
     * action listener for the buttons
     * @param ae ActionEvent
     */
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        if (source == left) {
            arena.moveShipLeft();
        } else if (source == right) {
            arena.moveShipRight();
        } else if (source == top) {
            arena.moveShipUp();
        } else if (source == bottom) {
            arena.moveShipDown();
        } else if (source == rotate) {
            arena.rotateShip();
        } else if (source == play) {
            window.startGame();
        }
    }
}
