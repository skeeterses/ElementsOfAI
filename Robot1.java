/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.robot1;
import org.jgrapht.*;
import org.jgrapht.generate.*;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.*;
import org.jgrapht.util.*;
import org.jgrapht.alg.connectivity.*;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.*;
import org.jgrapht.alg.interfaces.*;
import org.jgrapht.alg.shortestpath.*;

import java.util.*;
import java.util.function.*;

//2/21/2013 -- Right now, this simply returns null for the BFS search
// but I will get this solved.

/**
 *
 * @author scott
 */
public class Robot1 {
// This implements the dumb solution of Testing out every string combination
    // until the robot actually puts block A onto block B.
    // For example, if it takes 10 moves to put A onto B, the program will
    // test out every combination of Up (U), Down (D), Left (L), Right (R),
    // Open(O), Close(C), until it creates the exact 10 letter solution.
    // To solve this problem, first year computer science students will 
    // use the smart solution of doing a manhattan search and calculating the
    // number of Us and Ds by finding delta Y and the number of Ls and Rs by
    // calculating delta X.  In AI research, the 'smart' solution isn't always
    // obvious.
    
    
    // Possible results for testing out a possible command string
    // combination on a given map.  The objective is to put block A on
    // block B.
    // Unknown is for a Node that hasn't been tested yet.
    // Miss is for a string of moves that hasn't accomplished the goal.
    // Success is putting block A on block B without a collision or a drop.
    // Collide is when the robot arm or the held block collides with another
    // block.
    // Drop is when an object is dropped with nothing directly under it.
    // AlreadyPlaced is a special case where A is already on top of B
    // nothing more needs to be done.    
    enum testResult {
        Unknown, Miss, Success, Collide, Drop, AlreadyPlaced, OpenError, CloseError
    }
    
    static class Node {
    StringBuilder testString;    
    testResult result;
    int level;
    
    Node(StringBuilder cumulativeString, int level)
    {
       testString = cumulativeString; // All the previous letters plus an add on char.              
       result = testResult.Unknown;
       this.level = level;
    }
    
    public String toString()
    {
        return "";
    }
    }
 
    static class Robot {
        public
            
            // A slight modification of the standard BFS algorithm.
            // A complete tree consisting of all possible combinations for
            // 20 moves would exhaust the memory.
            // Instead, create nodes on the fly, put them on the queue and
            // when the level gets beyond maxLevel, the queue will empty out.
            StringBuilder BFS()
            {
                StringBuilder resultString = new StringBuilder("");
                testResult result = testResult.Unknown;
                int maxLevel = 10;
                int currentLevel = 0;
                Queue<Node> queue = new LinkedList<> ();                
                
                // Tests to see if A is already on top of B
                if (this.testMoves(resultString) == testResult.AlreadyPlaced)
                    return resultString;
                
                /*
                if (this.testMoves(starterNode.testString) == testResult.Success)
                    return starterNode.testString;
                */
                
                // Put the level 1 nodes onto the queue and start the BFS search.
                StringBuilder string1 = new StringBuilder("L");
                StringBuilder string2 = new StringBuilder("R");
                StringBuilder string3 = new StringBuilder("U");
                StringBuilder string4 = new StringBuilder("D");
                StringBuilder string5 = new StringBuilder("O");
                StringBuilder string6 = new StringBuilder("C");
                
                Node node1 = new Node(string1, 1);
                Node node2 = new Node(string2, 1);
                Node node3 = new Node(string3, 1);
                Node node4 = new Node(string4, 1);
                Node node5 = new Node(string5, 1);
                Node node6 = new Node(string6, 1);
                queue.add(node1);
                queue.add(node2);
                queue.add(node3);
                queue.add(node4);
                queue.add(node5);
                queue.add(node6);
                
                while (!queue.isEmpty())
                {
                    Node currentNode = queue.remove();
                    resultString = currentNode.testString;
                    result = this.testMoves(resultString);
                    if (result == testResult.Success)
                    {                        
                        resultString = currentNode.testString;
                        return resultString;
                    }
                    currentLevel = currentNode.level;
                    currentLevel++;
                    if ( (currentLevel < maxLevel) && (result == testResult.Miss) )
                    {                                           
                       string1 = new StringBuilder(resultString);
                       string2 = new StringBuilder(resultString);
                       string3 = new StringBuilder(resultString);
                       string4 = new StringBuilder(resultString);
                       string5 = new StringBuilder(resultString);
                       string6 = new StringBuilder(resultString);
                       string1 = string1.append("L");
                       string2 = string2.append("R");
                       string3 = string3.append("U");
                       string4 = string4.append("D");
                       string5 = string5.append("O");
                       string6 = string6.append("C");                       
                       node1 = new Node(string1, currentLevel);
                       node2 = new Node(string2, currentLevel);
                       node3 = new Node(string3, currentLevel);
                       node4 = new Node(string4, currentLevel);
                       node5 = new Node(string5, currentLevel);
                       node6 = new Node(string6, currentLevel);
                       queue.add(node1);
                       queue.add(node2);
                       queue.add(node3);
                       queue.add(node4);
                       queue.add(node5);
                       queue.add(node6);
                    }
                }
                
                resultString = new StringBuilder("Fail");
                return resultString;
            }                        
                     
             // testMoves will have to return a value for the node        
             // The testMoves method is simple.  It returns whether the
             // robot arm puts A onto B.
             testResult testMoves(StringBuilder inputMoves)
             {
               testResult result = testResult.Miss;
               // Test for special case of A already being placed on top of B.
               // If special case is true, no need to go through strings.
               if (alreadyPlaced)               
                   return testResult.AlreadyPlaced;
                            
               
               int currentX, currentY, stringPosition;
                // stringPosition is current position in inputMoves;
                boolean holdingObject = false;
                boolean gripClosed = false;
                char heldObject = '.';
                StringBuilder[] Screen = new StringBuilder[Map.length];
                for (int i = 0; i < Map.length; i++)
                    Screen[i] = new StringBuilder(Map[i]);
                
                //Test for the special case of A being on top of B already.
                //  Might want to put this in the constructor.
                
                currentX = robotX * 2;
                currentY = robotY;
                stringPosition = 0;
                while (stringPosition < inputMoves.length())
                {                    
                    char currentChar = inputMoves.charAt(stringPosition);
                    switch (currentChar)
                    {
                        case 'U':                                                        
                            if (currentY == 0)
                            {
                                result = testResult.Collide;
                                return result;
                            }
                            Screen[currentY-1].setCharAt(currentX, 'R');                            
                            if (holdingObject)
                            {
                                Screen[currentY].setCharAt(currentX, heldObject);
                                Screen[currentY+1].setCharAt(currentX, '.');
                            }
                                else
                                Screen[currentY].setCharAt(currentX, '.');
                           currentY--;
                           //System.out.println("Up");
                            break;
                        case 'D':                           
                            if ( (currentY+1) >= Screen.length)                            
                                return testResult.Collide;                                                           
                            if (holdingObject)
                            {                                
                                if ( (currentY+2) >= Screen.length)                                
                                    return testResult.Collide;                                                                                                                                    
                                if ( Character.isLetter(Screen[currentY+2].charAt(currentX)) )                                
                                    return testResult.Collide;                                                                                                       
                                Screen[currentY+2].setCharAt(currentX, heldObject);                            
                            }
                            else
                            {
                                if ( (currentY+1) >= Screen.length)
                                {
                                  result = testResult.Collide;
                                  return result;
                                }                                
                                if ( Character.isLetter(Screen[currentY+1].charAt(currentX)) )
                                {
                                    result = testResult.Collide;
                                    return result;
                                }
                            }
                            Screen[currentY+1].setCharAt(currentX, 'R');
                            Screen[currentY].setCharAt(currentX,'.');
                            currentY++;
                          //  System.out.println("Down");
                            break;
                        case 'L':
                            if ( (currentX-2) < 0)
                                return testResult.Collide;                            
                            if ( Character.isLetter(Screen[currentY].charAt(currentX-2)))                            
                                return testResult.Collide;                                                            
                            if (holdingObject)
                            {                                
                                if ( Character.isLetter( Screen[currentY+1].charAt(currentX-2)))
                                {
                                    result = testResult.Collide;
                                    return result;
                                }
                                Screen[currentY+1].setCharAt(currentX-2, heldObject);                            
                                Screen[currentY+1].setCharAt(currentX, '.');
                            }
                            Screen[currentY].setCharAt(currentX-2, 'R');
                            Screen[currentY].setCharAt(currentX, '.');       
                            currentX = currentX-2;
                           // System.out.println("Left");
                            break;
                        case 'R':
                            if ( (currentX+2) >= Screen[0].length())
                                return testResult.Collide;
                            if ( Character.isLetter(Screen[currentY].charAt(currentX+2)))                            
                                return testResult.Collide;
                                
                            
                            if (holdingObject)
                            {
                                if ( Character.isLetter( Screen[currentY+1].charAt(currentX+2)))
                                {
                                    result = testResult.Collide;
                                    return result;
                                }
                                Screen[currentY+1].setCharAt(currentX+2, heldObject);                            
                                Screen[currentY+1].setCharAt(currentX, '.');
                            }
                            Screen[currentY].setCharAt(currentX+2, 'R');
                            Screen[currentY].setCharAt(currentX, '.');    
                            currentX = currentX+2;
                           // System.out.println("Right");
                            break;                                                        
                        // The code will not cover falling objects.
                        case 'O':
                            // Test for a drop
                            if (holdingObject)
                            {                                
                                if ( (currentY+2) < Screen.length)
                                {
                                    if ( (heldObject == 'A') && (Screen[currentY+2].charAt(currentX) == 'B') )                                    
                                      return testResult.Success;                                    
                                    
                                    if (Screen[currentY+2].charAt(currentX) == '.')
                                        return testResult.Drop;
                                }
                            }
                            else
                                return testResult.OpenError;
                            
                            holdingObject = false;                            
                          //  System.out.println("Open");
                                                                                    
                            break;                           
                        case 'C':
                            if (holdingObject)
                                return testResult.CloseError;
                                                       
                               if ( (currentY+1) < Screen.length)
                               {
                                   heldObject = Screen[currentY+1].charAt(currentX);                         
                                   holdingObject = true;
                               }
                               else
                                   return testResult.CloseError;
                            //   System.out.println("Close");
                            break;
                    }
                  //  for (int i = 0; i < Map.length; i++)
                  //      System.out.println(Screen[i]);
                    stringPosition++;
                }                                
               
               return result;
             }
         
             Robot(String[] inputMap)
             {
                alreadyPlaced = false;
                Map = inputMap;                                                      
                                
                for (int i = 0; i < inputMap.length; i++)
                for (int j =0; j< inputMap[0].length()-2; j++)
                {
                       if (i < inputMap.length-1)
                       {
                           if ( (Map[i].charAt(j) == 'A') && (Map[i+1].charAt(j) == 'B') )
                                   {
                                       alreadyPlaced = true;
                                   }
                           
                       }

                       if (Map[i].charAt​(j) == 'R')
                        {
                            robotX = j/2;
                            robotY = i;                                                        
                        }
                }
             }
                     
        private
        int robotX, robotY;
        String[] Map;
        boolean alreadyPlaced;
        StringBuilder globalString;
    }
                
    
    public static void main(String[] args) {
    String[] searchString0 =
    { ". . . . . . . . . .",
      ". . . . . . . . . .",
      ". . . . . . . . . .",
      ". . . . . . . . . .",
      ". A . . . . . . . .",
      ". B . . . . . . . .",
    };        

    // The trivial case.  The Robot only has to go pick up B and then carry
    // it to the top of A.
    String[] searchString1 =
    { ". . . . . . . . . .",
      ". . . . . . . . . .",
      ". . . . . . . . . .",
      ". . . . . . . . . .",
      ". . R . . . . . . .",
      ". B A . . . . . . .",
    };
    StringBuilder testString1 = new StringBuilder("CULO");
    
    String[] searchString2 =
    { ". R . . . . . . . .",
      ". . . . . . . . . .",
      ". . . . . . . . . .",
      ". . . . . . . . . .",
      ". A . . . . . . . .",
      ". C . . B . . . . .",
    };
    
    String[] searchString3 =
    { ". . . . . . R . . .",
      ". . . . . . . . . .",
      ". F . . . . . . . .",
      ". D . . . . . . . .",
      ". A . . . . . . . .",
      ". C . . B . . . . .",
    };
    
    String testString[] = searchString2;
    Robot testRobot1 = new Robot(testString);       
    StringBuilder BFSResult = testRobot1.BFS();
    System.out.println("Result: ");
    System.out.println(BFSResult);
    /*
    if (testRobot1.testMoves(testString1) == testResult.Success)
    {
        System.out.println(testString1 + " is a success");
    }
*/    

    }
}
