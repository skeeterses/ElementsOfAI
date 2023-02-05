# ElementsOfAI
Sample programs from the Elements of AI Book:

Baroque.java is based off the Baroque chess project suggested by Tanimoto in Chapter 5.
I chose to use Java because of the ease of readability and vast number of built in data structures
and algorithsm.  
Later on, I will attempt to code the examples from that book using SBCL and/or Emacs Lisp.

8/28/2022 -- The chessboard and chess pieces have been drawn out in Java Swing.  The location of the
chess pieces on the board are stored in a hash map.

1/9/2023 -- I plan to put the Baroque Chess game into its own repository later on.  Right now, I will
use this directory for typing out the examples in Java and use the Java JgraphT library instead of 
writing out the graph search algorithms manually.

1/20/2023 -- I'm close to implementing the trivial case of the robot search program of having the robot
find a block in a text based map and picking up the block and putting it on top of another.  The goal
of the program is to use the BFS search algorithm to plan the robotic arm movement when there are multiple
blocks to be moved.

2/4/2023 -- In the robot search program, work on debugging the recursive clearTop function.  The program places
block B on top of A but the values in the waste column get clobbered when clearing the excess blocks off the top of
A.
2/5/2003 -- Robot search program is closed to being done.  I created an extra variable to hold the inputNode char value
and that solved the problem of the waste column being clobbered.  The next step is to continue reading the blue book and get started on
writing a Tic Tac Toe game in Java.
