public abstract class ChessPiece {
   
	   // The class itself does not actually move the piece but checks
	   // the legality of the move.
	   // The actual move itself is done by the main chess program.	   
	   public abstract boolean legalMove(int startX, int startY, int endX, int endY);
 	   public String getImageName()
	   {
		   return imageName;
	   }
	   public String getColorName()
	   {
		   return colorName;
	   }
  	   public ChessPiece(String color) 
	   {
	     colorName = color;
	   }	     
  protected 
	   String imageName;
	   String colorName;
}


class Pawn extends ChessPiece
{
  public
	  Pawn(String color)
	  {
	     super(color);
	     if (color.equalsIgnoreCase("white"))
		imageName = "WPawn.jpg";
	     if (color.equalsIgnoreCase("black"))
		     imageName = "BPawn.jpg";
	  }
  	  // I'll have to add some more functionality to the ChessPiece class in order
	  // for the pieces to detect other nearby pieces or add more if statements to
	  // the main chess program.
  	  public boolean legalMove(int startX, int startY, int endX, int endY)
	  {
	    if ((startX == endX) && (endX == (startX + 1)))
	        return true;
	    else
		    return false;
	  }
}


class Bishop extends ChessPiece
{
   public Bishop(String color)
   {
	   super(color);
	   if (color.equalsIgnoreCase("white"))
		   imageName = "WBishop.jpg";
	   if (color.equalsIgnoreCase("black"))
		   imageName = "BBishop.jpg";
   }
   public boolean legalMove(int startX, int startY, int endX, int endY)
   {
	   return true;
   }
}

class Knight extends ChessPiece
{
   public
	   Knight(String color)
	   {
	 	super(color);
		if (color.equalsIgnoreCase("white"))
			imageName = "WKnight.jpg";
		if (color.equalsIgnoreCase("black"))
			imageName = "BKnight.jpg";
	   }
   	   public boolean legalMove(int startX, int startY, int endX, int endY)
	   {
	     return true;
	   }
}


class UpRook extends ChessPiece
{
  public
	  UpRook(String color)
	  {
		  super(color);
		  if (color.equalsIgnoreCase("white"))
			  imageName = "WUPRook.jpg";
		  if (color.equalsIgnoreCase("black"))
			  imageName = "BUPRook.jpg";
	  }
   	   public boolean legalMove(int startX, int startY, int endX, int endY)
	   {
	     return true;
	   }

}

class DownRook extends ChessPiece
{
  public
	  DownRook(String color)
	  {
		  super(color);
		  if (color.equalsIgnoreCase("white"))
			  imageName = "WDownRook.jpg";
		  if (color.equalsIgnoreCase("black"))
			  imageName = "BDownRook.jpg";
	  }
   	   public boolean legalMove(int startX, int startY, int endX, int endY)
	   {
	     return true;
	   }

}

class Queen extends ChessPiece
{
   public
	   Queen(String color)
	   {
	     super(color);
	     if (color.equalsIgnoreCase("white"))
		     imageName = "WQueen.jpg";
	     if (color.equalsIgnoreCase("black"))
		     imageName = "BQueen.jpg";
	   }
   	   public boolean legalMove(int startX, int startY, int endX, int endY)
	   {
	     return true;
	   }

}

class King extends ChessPiece
{
  public
	  King(String color)
	  {
	     super(color);
	     if (color.equalsIgnoreCase("white"))
		     imageName = "WKing.jpg";
	     if (color.equalsIgnoreCase("black"))
		     imageName = "BKing.jpg";
	  }
   	   public boolean legalMove(int startX, int startY, int endX, int endY)
	   {
	     return true;
	   }

}
