// Baroque.java
// 8/22/2022
// Scott Stinson
//
// This program is for playing Baroque Chess as outlined in Elements of AI by Steven Tanimoto.

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

// A class of (int,int) to help organize the chess pieces into a TreeMap
class Coordinate {
   public
	   int getX()
	   {
		   return x;
	   }
	   int getY()
	   {
		   return y;
	   }
  
  // A method to compute a key with the keys going from top row to bottom,
  // left to right.  Or 0 to 63.
  	int computeKey(int x, int y)
	{
	   return (y*8 + x);
	}	

   private
	  int x, y;
}
// A TreeMap is probably not the best class to use for mapping individual chess
// pieces to coordinates.

// A helper class to define the Comparator or sorter for the Coordinate class.
// For this, I've chosen to turn each coordinate into 1 number representing the
// number of pixels from the origin, going in the left to right and top to down
// count.  Value = (rows * 8) + column.
/*class CoordinateSorter implements Comparator<Coordinate> {
  public int compare(Coordinate a, Coordinate b)
  {
     int aCoordinate, bCoordinate;
     aCoordinate = (a.getY() * 8) + a.getX();
     bCoordinate = (b.getY() * 8) + b.getX();
     return aCoordinate - bCoordinate;
  }

}
*/

public class Baroque {
        private static final Insets insets = new Insets(0,0,0,0);
	public static void main(final String args[]) {
	  Runnable runner = new Runnable() {
		  public void run() {
			  JFrame frame = new JFrame("Baroque Chess Game");
			  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			 
			  frame.setLayout(new GridLayout(8,8));			  

			  // Each piece on the chessboard will be on a Treemap containing the
			  // coordinates and type of piece on the square.
			//  TreeMap<ChessPiece, Coordinate> chessMap
			//	= new TreeMap<ChessPiece, Coordinate>(new CoordinateSorter());
                          HashMap<Integer, ChessPiece> chessMap = new HashMap<>();			  
			  // Testing out HashMap
			  Coordinate coordinate1 = new Coordinate();
			  chessMap.put(0, new UpRook("black"));
			  chessMap.put(1, new Knight("black"));
			  chessMap.put(2, new Bishop("black"));
			  chessMap.put(3, new Queen("black"));
			  chessMap.put(4, new King("black"));
			  chessMap.put(5, new Bishop("black"));
			  chessMap.put(6, new Knight("black"));
			  chessMap.put(7, new DownRook("black"));
			  for (int i = 8; i < 16; i++)
			  {
			    chessMap.put(i, new Pawn("black"));
			  }
			  chessMap.put(56, new UpRook("white"));
			  chessMap.put(57, new Knight("white"));
			  chessMap.put(58, new Bishop("white"));
			  chessMap.put(59, new Queen("white"));
			  chessMap.put(60, new King("white"));
			  chessMap.put(61, new Bishop("white"));
			  chessMap.put(62, new Knight("white"));
			  chessMap.put(63, new DownRook("white"));
			  for (int i = 48; i < 56; i++)
			  {
			     chessMap.put(i, new Pawn("white"));
			  }


		          JButton[][] chessPosition;
			  chessPosition = new JButton[8][8];
			  // Instead of loading 1 big chessboard, make each position a button
			  // on the grid.
			  for (int i=0; i < 8; i++)
			  for (int j=0; j < 8; j++)
			  {
			    chessPosition[i][j] = new JButton();
			    if ( ((i%2 == 0) && (j%2 == 0)) ||
				 ((i%2 == 1) && (j%2 == 1)))
		               chessPosition[i][j].setBackground(Color.green);
			    else
			       chessPosition[i][j].setBackground(new Color(200,255,200));
			   
			    // Test to see if the hashtable contains a piece for this position
			    // on the grid.
			    int key = coordinate1.computeKey(j,i);
			    Icon chessIcon;
			    ChessPiece piece;

			    if (chessMap.containsKey(key))
			    {
			       piece = chessMap.get(key);	
			       chessIcon = new ImageIcon(piece.getImageName());
			       chessPosition[i][j].setIcon(chessIcon);
			    }

			    frame.add(chessPosition[i][j]);
			  }
						  
			  
			  JMenuBar menuBar = new JMenuBar();						  					  //3 options on the menu bar:  Restart game, Instructions, and Exit
			  //  The Restart option will have a pop up window where the player selects
			  //  his/her color and decides whether to go up against the computer or
			  //  another player.
			  JMenu optionsMenu = new JMenu("Options");
			  menuBar.add(optionsMenu);
	                  JMenuItem restartMenuItem = new JMenuItem("Restart", KeyEvent.VK_R);
			  optionsMenu.add(restartMenuItem);
			  JMenuItem instructionsMenuItem = new JMenuItem("Instructions",KeyEvent.VK_I);
			  optionsMenu.add(instructionsMenuItem);
			  JMenuItem exitItem = new JMenuItem("Exit",KeyEvent.VK_X);
			  optionsMenu.add(exitItem);

			  frame.setJMenuBar(menuBar);
			  frame.setSize(1000,1000);
			  frame.setVisible(true);
		  }
	  };
        EventQueue.invokeLater(runner);
	  }
}
