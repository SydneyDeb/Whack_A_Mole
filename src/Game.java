import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.event.*;

public class Game extends JFrame implements MouseListener, ActionListener {
    private JPanel frame;
    private JLabel[] holes = new JLabel[12];
    private int[] board = new int[12];
    private JButton start;
    private JLabel scoreText;
    private int score = 0;
    private JLabel timeText;
    private Timer timer;
    private int time;

    public Game() {
        loadUI();
        resetGame();
        addMouseListener(this);
        time = 30;
        timer = new Timer(1000, this);
        start.addActionListener(this);
    }

    public void mouseClicked(MouseEvent e) {
    }
    public void mousePressed(MouseEvent e) {
    }
    public void mouseReleased(MouseEvent e) {
        score++;
        System.out.println(score);
        scoreText.setText("Score: " + score);
        resetGame();
        spawnMole();
        JLabel lbl = (JLabel)e.getSource();
        int id = Integer.parseInt(lbl.getName());
        clicked(id);
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton) {
            resetGame();
            spawnMole();
            timer.start();
        }
        if(e.getSource() instanceof Timer) {
            timer = new Timer(1000, this);
            if(time == 0){
                timeText.setText("" + time);
                timer.stop();
                endGame();
            }
            timeText.setText("" + time);
            time--;
        }
    }

    public void loadUI() {
        setTitle("Whack-A-Mole");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 700);
        JPanel game = new JPanel();
        game.setBackground(new Color(75, 159, 74));
        game.setLayout(null);

        JLabel lblTitle = new JLabel("Whack-A-Mole!");
        lblTitle.setForeground(new Color(35, 101, 51));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        lblTitle.setBounds(0, 0, 600, 50);
        game.add(lblTitle);

        scoreText = new JLabel("Score: 0");
        scoreText.setHorizontalAlignment(SwingConstants.LEADING);
        scoreText.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        scoreText.setForeground(new Color(35, 101, 51));
        scoreText.setBounds(25, 10, 600, 50);
        game.add(scoreText);

        timeText = new JLabel("Time: 30");
        timeText.setHorizontalAlignment(SwingConstants.CENTER);
        timeText.setForeground(new Color(240, 128, 128));
        timeText.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        timeText.setBounds(232, 54, 144, 33);
        game.add(timeText);

        start = new JButton("Start");
        start.setBackground(new Color(157, 193, 131));
        start.setForeground(new Color(35, 101, 51));
        start.setBounds(10, 45, 100, 30);
        game.add(start);

        frame = new JPanel();
        frame.setBackground(new Color(157, 193, 131));
        frame.setBounds(100, 100, 395, 500);
        frame.setLayout(null);
        game.add(frame);

        holes[0] = new JLabel("0");
        holes[0].setName("0");
        holes[0].setBounds(0, 400, 132, 132);
        frame.add(holes[0]);

        holes[1] = new JLabel("1");
        holes[1].setName("1");
        holes[1].setBounds(135, 400, 132, 132);
        frame.add(holes[1]);

        holes[2] = new JLabel("2");
        holes[2].setName("2");
        holes[2].setBounds(270, 400, 132, 132);
        frame.add(holes[2]);

        holes[3] = new JLabel("3");
        holes[3].setName("3");
        holes[3].setBounds(0, 270, 132, 132);
        frame.add(holes[3]);

        holes[4] = new JLabel("4");
        holes[4].setName("4");
        holes[4].setBounds(135, 270, 132, 132);
        frame.add(holes[4]);

        holes[5] = new JLabel("5");
        holes[5].setName("5");
        holes[5].setBounds(270, 270, 132, 132);
        frame.add(holes[5]);

        holes[6] = new JLabel("6");
        holes[6].setName("6");
        holes[6].setBounds(0, 135, 132, 132);
        frame.add(holes[6]);

        holes[7] = new JLabel("7");
        holes[7].setName("7");
        holes[7].setBounds(135, 135, 132, 132);
        frame.add(holes[7]);

        holes[8] = new JLabel("8");
        holes[8].setName("8");
        holes[8].setBounds(270, 135, 132, 132);
        frame.add(holes[8]);

        holes[9] = new JLabel("9");
        holes[9].setName("9");
        holes[9].setBounds(0, 0, 132, 132);
        frame.add(holes[9]);

        holes[10] = new JLabel("10");
        holes[10].setName("10");
        holes[10].setBounds(135, 0, 132, 132);
        frame.add(holes[10]);

        holes[11] = new JLabel("11");
        holes[11].setName("11");
        holes[11].setBounds(270, 0, 132, 132);
        frame.add(holes[11]);

        for(int i = 0; i < 11; i++) {
            final int p = i;
            holes[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getSource() == holes[p]) {
                        int id = Integer.parseInt(holes[p].getName());
                        clicked(id);
                    }
                }
            });
        }
        setContentPane(game);
    }

    public void clicked(int mole){
        int val = board[mole];
        if(val==1){
            score++;
        }else{
            score--;
        }
        scoreText.setText("Score: " + score);
        resetGame();
        spawnMole();
    }

    public void resetGame() {
        for(int i = 0; i < 12; i++){
            holes[i].setIcon(loadImage("/moleIn.png"));
            board[i] = 0;
        }
    }

    public void spawnMole(){
        int randomHole = (int)(Math.random()*12);
        board[randomHole] = 1;
        holes[randomHole].setIcon(loadImage("/moleOut.png"));
    }

    public void endGame() {
        JOptionPane.showMessageDialog(this, "Your Scored " + score + " points!","Game Over!", JOptionPane.INFORMATION_MESSAGE);
        score = 0;
        time = 30;
        timeText.setText("30");
        scoreText.setText("Score: 0");
        resetGame();
    }

    public ImageIcon loadImage(String file){
        Image mole = new ImageIcon(this.getClass().getResource(file)).getImage();
        return new ImageIcon(mole);
    }
}