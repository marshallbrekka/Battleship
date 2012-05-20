package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * creates the arrows to move the ships
 * 
 * @author Marshall
 */
public class Arrows extends JPanel implements ActionListener {
    private Arena arena;

    private JButton left;
    private JButton right;
    private JButton top;
    private JButton bottom;
    private JButton rotate;
    private JButton play;
    private GameView window;

    /**
     * creates the arrows
     * 
     * @param a
     *            Arena
     * @param newWindow
     *            BattleshipGame
     */
    public Arrows(GameView newWindow) {
        window = newWindow;
        JPanel panel = new JPanel();
        BorderLayout bl = new BorderLayout();
        BorderLayout b2 = new BorderLayout();
        this.setLayout(bl);
        panel.setLayout(b2);

        left = new JButton("Left");
        right = new JButton("Right");
        top = new JButton("Up");
        bottom = new JButton("Down");
        rotate = new JButton("R");
        play = new JButton("Play");

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

    public void setArena(Arena a) {
        arena = a;
    }

    /**
     * action listener for the buttons
     * 
     * @param ae
     *            ActionEvent
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
            window.startGameCallback();
        }
    }

    public void enable() {
        JButton[] buttons = { top, bottom, left, right, rotate, play };
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setEnabled(true);
        }
    }

    public void disable() {
        JButton[] buttons = { top, bottom, left, right, rotate, play };
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setEnabled(false);
        }
    }
}
