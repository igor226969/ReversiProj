import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Arrays;
import java.io.File;
import java.io.IOException;
public class Ui extends JFrame
{
    private int counter;
    private boolean startGame;
    private JFrame frame = new JFrame();
    private JPanel board = new JPanel();
    private JPanel score = new JPanel();
    private JPanel status = new JPanel();
    
    private static JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
    private static FileManager fileManager = new FileManager();
    private static Reversi reversi = new Reversi();
    
    private JLabel player1Label = new JLabel();
    private JLabel player2Label = new JLabel();
    private JLabel blackCounters = new JLabel();
    private JLabel whiteCounters = new JLabel();
    
    private JLabel statusLabel = new JLabel();
    
    public String[][] board1 = new String[8][8];
    private JButton[][] squares = new JButton[8][8];
    private String[][] validMoves = new String[8][8];
    
    private JButton play = new JButton("Play");
    private JTextField name1 = new JTextField();
    private JTextField name2 = new JTextField();
    
    private ImageIcon whiteCounter = new ImageIcon("WhiteCounter.jpg");
    private ImageIcon blackCounter = new ImageIcon("BlackCounter.jpg");
    private ImageIcon bg = new ImageIcon("WhiteBG.jpg");
    
    private String player1;
    private String player2;
    
    private int player1Wins;
    private int player2Wins;
    
    public int totalBlackCounters;
    public int totalWhiteCounters;
    
    private int whiteLegalMoves;
    private int blackLegalMoves;
    
    public Ui()
    {
        player1 = "White";
        player2 = "Black";
        player1Wins = 0;
        player2Wins = 0;
        ButtonHandler buttonHandler = new ButtonHandler();
        setUp();
        setUpBoard();
        totalCounters();
    }
    
    private void setUp()
    {
        frame.setTitle("Reversi");
        makeMenuBar(frame);
        frame.setLayout(new BorderLayout());
        score.setLayout(new BoxLayout(score, BoxLayout.Y_AXIS));
        statusLabel.setText("Enter names and click Play to start game!");
        player1Label.setText("Player1 name: ");
        player2Label.setText("Player2 name: ");
        status.add(statusLabel);
        score.add(player1Label);
        score.add(name1);
        score.add(player2Label);
        score.add(name2);
        score.add(play);
        play.addActionListener(new ButtonHandler());
        board.setLayout(new GridLayout(8,8));
        board.setPreferredSize(new Dimension(500,500));
        score.setPreferredSize(new Dimension(100,200));
        frame.add(board,BorderLayout.CENTER);
        frame.add(score,BorderLayout.EAST);
        frame.add(status,BorderLayout.SOUTH);
        frame.setSize(550,550);
        frame.setVisible(true);
        validate();
    }
    
    private void setUpBoard()
    {
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                squares[x][y] = new JButton();
                board.add(squares[x][y]);
                squares[x][y].addActionListener(new ButtonHandler());
                squares[x][y].setIcon(bg);
            }
        }
        squares[3][3].setIcon(whiteCounter);
        squares[3][4].setIcon(blackCounter);
        squares[4][3].setIcon(blackCounter);
        squares[4][4].setIcon(whiteCounter);
    }
    
    private void totalCounters()
    {
       totalWhiteCounters = 0;
       totalBlackCounters = 0;
       for (int x = 0; x < 8; x++)
       {
           for (int y = 0; y < 8; y++)
           {
               if(squares[x][y].getIcon() == whiteCounter)
               {
                   totalWhiteCounters += 1; 
               }
               else if(squares[x][y].getIcon() == blackCounter)
               {
                   totalBlackCounters += 1; 
               }   
            }
       }
    }

    
    private void setUpScorePanel()
    {
        JLabel whiteLabel = new JLabel("White counters: ");
        JLabel blackLabel = new JLabel("black counters: ");
        score.setLayout(new BoxLayout(score, BoxLayout.Y_AXIS));
        player1Label.setText(player1+"'s wins: "+ player1Wins);
        player2Label.setText(player2+"'s wins: "+ player2Wins);
        whiteCounters.setText(totalWhiteCounters + " ");
        blackCounters.setText(totalBlackCounters + " ");
        score.add(player1Label);
        score.add(player2Label);
        score.add(whiteLabel);
        score.add(whiteCounters);
        score.add(blackLabel);
        score.add(blackCounters);
    }
   
    private void checkLegalMovesWhite()
    {
        if(whiteLegalMoves == 0){
            counter += 1;
        }
    }
    
    private void checkLegalMovesBlack()
    {
        if(whiteLegalMoves == 0){
            counter += 1;
        }
    }
    
    private void checkWin()
    {
        if(whiteLegalMoves == 0 && blackLegalMoves == 0){
            startGame = false;
            if(totalWhiteCounters > totalBlackCounters){
                System.out.println(player1 + " HAS WON THE GAME!!!!");
                player2Wins += 1;
            }
            else if(totalWhiteCounters < totalBlackCounters){
                System.out.println(player2 + " HAS WON THE GAME!!!!");
                player1Wins += 1;
            }
            else{
                System.out.println("IT WAS A DRAW!!!!");
            }
        }
        System.out.println("Click 'Options' and 'New game' to play again. ");
    }
    
    private void placeCounter(int x, int y)
    {
        if(reversi.getNextPlayer(counter,player1,player2) == player1 && startGame == true)
        {
            validMoves = reversi.validMoves(squares,whiteCounter,blackCounter,counter);
            if(validMoves[x][y].equals("W")){
                whiteLegalMoves = reversi.getLegalNoOfMoves();
                squares[x][y].setIcon(whiteCounter);
                board1 = reversi.flipCounters(counter,x,y,squares,whiteCounter,blackCounter);
                updateBoard();
                statusLabel.setText(player1 + "'s turn");
                totalCounters();
                whiteCounters.setText(totalWhiteCounters + " ");
                counter += 1;
                statusLabel.setText(reversi.getNextPlayer(counter,player1,player2) + "'s turn");
            }
            else{
                whiteLegalMoves = reversi.getLegalNoOfMoves(); 
                System.out.println("invalid");
            }
        }

        else if(reversi.getNextPlayer(counter,player1,player2) == player2 && startGame == true)
        {
            validMoves = reversi.validMoves(squares,whiteCounter,blackCounter,counter);
            if(validMoves[x][y].equals("B")){
                blackLegalMoves = reversi.getLegalNoOfMoves();
                squares[x][y].setIcon(blackCounter);
                board1 = reversi.flipCounters(counter,x,y,squares,whiteCounter,blackCounter);
                updateBoard();
                statusLabel.setText(player2 + "'s turn");
                totalCounters();
                blackCounters.setText(totalBlackCounters + " ");
                counter += 1;
                statusLabel.setText(reversi.getNextPlayer(counter,player1,player2) + "'s turn");
            }
            else{
                blackLegalMoves = reversi.getLegalNoOfMoves(); 
                System.out.println("invalid");
            }
        }
        validMoves = reversi.validMoves(squares,whiteCounter,blackCounter,counter);
    }
    
    private void saveSession()
    {
        if(startGame == true){
            int returnVal = fileChooser.showSaveDialog(frame);
            if(returnVal != JFileChooser.APPROVE_OPTION) {
                return;  // cancelled
            }
            File selectedFile = fileChooser.getSelectedFile();
            String fileName = selectedFile.getPath(); 
            board1 = fileManager.getBoard(squares,whiteCounter,blackCounter);
            fileManager.saveBoard(board1,counter,player1,player2,fileName);
    
        }
    }
    
    private void newSession()
    {
        score.removeAll();
        status.removeAll();
        board.removeAll();
        startGame = false;
        counter = 0;
        setUp();
        setUpBoard();
        totalCounters();
    }
    
    private void loadSession()
    {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(frame);
        if(returnVal != JFileChooser.APPROVE_OPTION) {
            return;  // cancelled
        }
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            board1 = fileManager.readSession(chooser.getSelectedFile().getName());
        }
        if(board1 == null){
            JOptionPane.showMessageDialog(frame,"The file loaded is not in a recognized format.",
                "File load error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        player1 = fileManager.getPlayer1Name();
        player2 = fileManager.getPlayer2Name();
        counter = fileManager.getCounter();
        board.removeAll();
        status.removeAll();
        setUp();
        score.removeAll();
        score.updateUI();
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                if(board1[x][y].equals("W")){
                    squares[x][y] = new JButton();
                    board.add(squares[x][y]);
                    squares[x][y].addActionListener(new ButtonHandler());
                    squares[x][y].setIcon(whiteCounter);
                }
                else if(board1[x][y].equals("B")){
                   squares[x][y] = new JButton();
                   board.add(squares[x][y]);
                   squares[x][y].addActionListener(new ButtonHandler());
                   squares[x][y].setIcon(blackCounter);
                }
                else if(board1[x][y].equals(" ")){
                   squares[x][y] = new JButton();
                   board.add(squares[x][y]);
                   squares[x][y].addActionListener(new ButtonHandler());
                   squares[x][y].setIcon(bg);
                }
            }
        }
        totalCounters();
        setUpScorePanel();
        statusLabel.setText(reversi.getNextPlayer(counter,player1,player2) + "'s turn");
        startGame = true;
    }
    
    private void updateBoard()
    {
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                if(board1[x][y].equals("W")){
                    squares[x][y].setIcon(whiteCounter);
                }
                else if(board1[x][y].equals("B")){
                   squares[x][y].setIcon(blackCounter);
                }
                else if(board1[x][y].equals(" ")){
                   squares[x][y].setIcon(bg);
                }
            }
        }
    }
    
    private void newGame()
    {
        board.removeAll();
        setUpBoard();
        totalCounters();
        counter = 0;
        blackCounters.setText(totalBlackCounters + " ");
        whiteCounters.setText(totalWhiteCounters + " ");
        statusLabel.setText(reversi.getNextPlayer(counter,player1,player2) + "'s turn");
    }

    private void makeMenuBar(JFrame frame)
    {
        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);
        
        JMenu fileMenu = new JMenu("Options");
        menubar.add(fileMenu);
        
        JMenuItem newSession = new JMenuItem("New Session");
            newSession.addActionListener(e -> newSession());
        fileMenu.add(newSession);
        
        JMenuItem newGame = new JMenuItem("New Game");
            newGame.addActionListener(e -> newGame());
        fileMenu.add(newGame);

        JMenuItem loadSession = new JMenuItem("Load Session");
            loadSession.addActionListener(e -> loadSession());
        fileMenu.add(loadSession);
        
        JMenuItem saveSession = new JMenuItem("Save session");
            saveSession.addActionListener(e -> saveSession());
        fileMenu.add(saveSession);
    }

    private class ButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Object source = e.getSource();
            for (int x = 0; x < 8; x++)
            {
                for (int y = 0; y < 8; y++)
                {
                    if(source == squares[x][y])
                    {
                        checkLegalMovesWhite();
                        checkLegalMovesBlack();
                        checkWin();
                        placeCounter(x,y);
                    }
                }
            }
            if(source == play)
            {
                startGame = true;
                score.removeAll();
                if(name1.getText().equals("")){
                    player1 = "White";
                }
                else{
                    player1 = name1.getText();
                }
                if(name2.getText().equals ("")){
                    player2 = "Black";
                }
                else{
                    player2 = name2.getText();
                }
                statusLabel.setText(player1 + "'s turn");
                validMoves = reversi.validMoves(squares,whiteCounter,blackCounter,counter);
                whiteLegalMoves = reversi.getLegalNoOfMoves();
                reversi.validMoves(squares,whiteCounter,blackCounter,counter+1);
                blackLegalMoves = reversi.getLegalNoOfMoves();
                score.updateUI();
                setUpScorePanel();
            }
        }
    }
}
