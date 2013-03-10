package liu.ida.emiva760.tddc69.projekt;

import javax.swing.JFrame;

public class Brejkaot extends JFrame {
	public Brejkaot()
    {
        add(new GameBoard());
        setTitle("Brejkaot");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(SharedConstants.WIDTH, SharedConstants.HEIGHT);
        setLocationRelativeTo(null);
        setIgnoreRepaint(true);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Brejkaot();
    }
}
