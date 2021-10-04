import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Arrays;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Arrays;
public class Reversi
{
    private String player1;
    private String player2;
    private String validBoard[][];
    private String board[][];
    private int legalMoves;
    
    public Reversi()
    {
        validBoard = new String[8][8];
        board = new String[8][8];
        validBoard[3][3] = (" ");
        validBoard[3][4] = (" ");
        validBoard[4][3] = (" ");
        validBoard[4][4] = (" ");
    }
    
    public String getNextPlayer(int counter,String player1,String player2)
    {
        if( counter % 2 == 0)
        {
            return player1;
        }
        else{
            return player2;
        }        
    }
    
    private boolean checkCompleteLine(String currentPlayer, String [][]board,int row,int column,int differenceX,int differenceY)
    {
        if(board[row][column].equals(currentPlayer)){
            return true;
        }
        if(row + differenceX > 7 || row + differenceX < 0){
            return false;
        }
        if(column + differenceY > 7 || row + differenceY < 0){
            return false;
        }
        return(checkCompleteLine(currentPlayer,board,row,column,differenceX + differenceX,differenceY + differenceY));
    }
    
    private boolean checkOutOfBounds(int row,int column,int differenceX,int differenceY)
    {
        boolean notOutOfBounds = true;
        if((row + differenceX) < 0 || (row + differenceX) > 7){
            notOutOfBounds = false;
        }
        if((column + differenceY) < 0 || (column + differenceY) > 7){
            notOutOfBounds = false;
        }
        return notOutOfBounds;
    }
    
    private boolean checkMove(String [][]board,int row,int column,int counter,int differenceX,int differenceY)
    {
        String currentMovePlayer = getNextPlayer(counter,"W","B");
        String nextPlayer = getNextPlayer(counter+1,"W","B");
        if((row + differenceX) < 0 || (row + differenceX) > 7){
            return false;
        }
        if((column + differenceY) < 0 || (column + differenceY) > 7){
            return false;
        }
        if(board[differenceX + row][differenceY+column] != (nextPlayer)){
            return false;
        }
        if(row + differenceX + differenceX > 7 || row + differenceX + differenceX < 0){
            return false;
        }
        if(column + differenceY + differenceY > 7 || column + differenceY + differenceY < 0){
            return false;
        }
        return checkCompleteLine(currentMovePlayer,board,row+differenceX+differenceX,column+differenceY+differenceY,differenceX,differenceY);
    }

    
    public String[][] validMoves(JButton[][] squares,ImageIcon white,ImageIcon black,int counter)
    {   
        legalMoves = 0;
        board = getBoard(squares,white,black);
        String currentMovePlayer = getNextPlayer(counter,"W","B");
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                if(board[x][y].equals(" "))
                {
                    boolean n = checkMove(board,x,y,counter,-1,0);
                    boolean ne = checkMove(board,x,y,counter,-1,1);
                    boolean nw = checkMove(board,x,y,counter,-1,-1);
                    
                    boolean e = checkMove(board,x,y,counter,0,1);
                    
                    boolean s = checkMove(board,x,y,counter,1,0);
                    boolean se = checkMove(board,x,y,counter,1,1);
                    boolean sw = checkMove(board,x,y,counter,1,-1);
                    
                    boolean w = checkMove(board,x,y,counter,0,-1);
                    if(n || ne || nw || e || s || se || sw || w){
                        validBoard[x][y] = currentMovePlayer;
                        legalMoves += 1;
                    }
                    else{
                        validBoard[x][y] = " ";
                    }
                }
            }
        }
        return validBoard;
    }
    
    public int getLegalNoOfMoves()
    {
        return legalMoves;
    }
    
    public  boolean flipLine(String[][]board,int row,int column,int counter,int differenceX,int differenceY)
    {
        String currentMovePlayer = getNextPlayer(counter,"W","B");
   
        if((row + differenceX) < 0 || (row + differenceX) > 7){
            return false;
        }
        if((column + differenceY) < 0 || (column + differenceY) > 7){
            return false;
        }
        if(board[row + differenceX][column + differenceY].equals(" ")){
            return false;
        }
        if(board[differenceX + row][differenceY+column].equals(currentMovePlayer)){
            return true;
        }
        else{
            if(flipLine(board,row+differenceX,column+differenceY,counter,differenceX,differenceY)){
                board[row + differenceX][column + differenceY] = currentMovePlayer;
                return true;
            }
            else{
                return false;
            }
        }
    }
    
    public String[][] flipCounters(int counter,int x, int y,JButton[][] squares,ImageIcon white,ImageIcon black)
    {
        board = getBoard(squares,white,black);
        flipLine(board,x,y,counter,-1,0);
        flipLine(board,x,y,counter,-1,1);
        flipLine(board,x,y,counter,-1,-1);
                    
        flipLine(board,x,y,counter,0,1);
                    
        flipLine(board,x,y,counter,1,0);
        flipLine(board,x,y,counter,1,1);
        flipLine(board,x,y,counter,1,-1);
                    
        flipLine(board,x,y,counter,0,-1);
        return board;
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
}