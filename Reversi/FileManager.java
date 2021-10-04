import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class FileManager 
{
    public String [][]board; 
    public String player1;
    public String player2;
    public int counter;
    
    public FileManager()
    {
        board = new String[8][8];
    }
    
    public String[][] getBoard(JButton[][] squares,ImageIcon white,ImageIcon black)
    {
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                if(squares[x][y].getIcon() == white)
                {
                    board[x][y] = "W";
                }
                else if(squares[x][y].getIcon() == black)
                {
                    board[x][y] = "B"; 
                }
                else{
                    board[x][y] = " ";
                }
            }
        }
        return(board);
    }
    
    public void saveBoard(String [][]board,int counter,String player1,String player2,String file)
    {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(file+".txt")));
            for (int x = 0; x < 8; x++)
            {
                for (int y = 0; y < 8; y++)
                {
                    writer.write(board[x][y]);
                
                }
                writer.newLine();
            }
            writer.write(Integer.toString(counter));
            writer.newLine();
            writer.write(player1);
            writer.newLine();
            writer.write(player2);
            writer.close();
        }       
        catch (IOException ex) {
            // Report
        }   
    }
    
    public String getPlayer1Name()
    {
        return player1;
    }
    
    public String getPlayer2Name()
    {
        return player2;
    }
    
    public int getCounter()
    {
        return counter;
    }
    
    private boolean validateBoard(String [][]board)
    {
        boolean valid = false;
        for(int x = 0; x < 8; x++)
        {
            for(int y = 0; y < 8; y++)
            {
                if(board[x][y].equals("B") || board[x][y].equals("W") || board[x][y].equals(" "))
                {
                    valid = true;
                }
                else{
                    return false;
                }
            }
        }
        return valid;
    }
    
    public String[][] readSession(String filename)
    {
        String[][] board1 = new String[8][8];
        try {
            Scanner sc = new Scanner(new BufferedReader(new FileReader(filename)));
            for(int x = 0; x < 8; x++)
            {
                String[] line = sc.nextLine().split("");
                for(int y = 0; y < 8; y++)
                {
                    board1[x][y] = line[y];
                }
            }
            counter = Integer.valueOf(sc.nextLine());
            player1 = sc.nextLine();
            player2 = sc.nextLine();
            sc.close();
        }
        catch (IOException ex) {
            return null;
        }
        if(validateBoard(board1))
        {
            return board1;
        }
        else{
            return null;
        }
    }
}

