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
    enum testResult {
        Unknown, Miss, Success, Collide, Drop
    }
    
    static class Node {
    StringBuilder testString;
        
    Node(StringBuilder cumulativeString)
    {
       testString = cumulativeString; // All the previous letters plus an add on char.
    }
    
    public String toString()
    {
        return "";
    }
    }
 
    static class Robot {
        public
     
             // testMoves will have to return a value for the node        
             // The testMoves method is simple.  It returns whether the
             // robot arm puts A onto B.
             testResult testMoves(StringBuilder inputMoves)
             {
               testResult result = testResult.Miss;
               
               int currentX, currentY, stringPosition;
                // stringPosition is current position in inputMoves;
                boolean holdingObject = false;
                boolean gripClosed = false;
                char heldObject = '.';
                StringBuilder[] Screen = new StringBuilder[Map.length];
                for (int i = 0; i < Map.length; i++)
                    Screen[i] = new StringBuilder(Map[i]);
                
                currentX = robotX * 2;
                currentY = robotY;
                stringPosition = 0;
                while (stringPosition < inputMoves.length())
                {                    
                    char currentChar = inputMoves.charAt(stringPosition);
                    switch (currentChar)
                    {
                        case 'U':                                                        
                            Screen[currentY-1].setCharAt(currentX, 'R');                            
                            if (holdingObject)
                            {
                                Screen[currentY].setCharAt(currentX, heldObject);
                                Screen[currentY+1].setCharAt(currentX, '.');
                            }
                                else
                                Screen[currentY].setCharAt(currentX, '.');
                            currentY--;
                           System.out.println("Up");
                            break;
                        case 'D':                           
                            if (holdingObject)
                            {
                                if ( Character.isLetter(Screen[currentY+2].charAt(currentX)) )
                                {
                                    result = testResult.Collide;
                                    return result;
                                }                                    
                                Screen[currentY+2].setCharAt(currentX, heldObject);                            
                            }
                            else
                            {
                                if ( Character.isLetter(Screen[currentY+1].charAt(currentX)) )
                                {
                                    result = testResult.Collide;
                                    return result;
                                }
                            }
                            Screen[currentY+1].setCharAt(currentX, 'R');
                            Screen[currentY].setCharAt(currentX,'.');
                            currentY++;
                            System.out.println("Down");
                            break;
                        case 'L':                                                                                    
                            if ( Character.isLetter(Screen[currentY].charAt(currentX-2)))
                            {
                                result = testResult.Collide;
                                return result;
                            }
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
                            System.out.println("Left");
                            break;
                        case 'R':
                            if ( Character.isLetter(Screen[currentY].charAt(currentX+2)))
                            {
                                result = testResult.Collide;
                                return result;
                            }
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
                            System.out.println("Right");
                            break;                                                        
                        // The code will not cover falling objects.
                        case 'O':
                            holdingObject = false;                            
                            System.out.println("Open");
                            if ( (heldObject == 'A') && (Screen[currentY+2].charAt(currentX) == 'B') )
                            {
                               result = testResult.Success;
                               return result; 
                            }
                            
                            break;                           
                        case 'C':
                            holdingObject = true;
                            heldObject = Screen[currentY+1].charAt(currentX);
                            System.out.println("Close");
                            break;
                    }
                    for (int i = 0; i < Map.length; i++)
                        System.out.println(Screen[i]);
                    stringPosition++;
                }                                
               
               return result;
             }
         
             Robot(String[] inputMap)
             {
                Map = inputMap;                                                      
             
                for (int i = 0; i < inputMap.length; i++)
                for (int j =0; j< inputMap[0].length()-2; j++)
                {
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
        
    }
                
    
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
