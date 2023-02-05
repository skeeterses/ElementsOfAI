/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.robotsearch;

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
public class RobotSearch {
   
    // A node will contain the letter and X,Y coordinates.
    static class Node {
    int x, y;
    char c;
    Node(int x, int y, char c)
    {
        this.x = x;
        this.y = y;
        this.c = c;
    }
    
    public String toString()
    {
        return c + ": (" + x + "," + y + ")";
    }
    }
    
    /* The list of possible moves is:
       U -- Move Robot Up
       D -- Move Robot Down
       R -- Move Robot Right
       L -- Move Robot Left
       O -- Open Robot grip
       C -- Close Robot grip
    */    
    static class Robot {
        public
             // Using the Moves string, update and print the Map strings
             // to simulate the moves of the robot.
             // The second testMoves method is for testing the testMoves
             // method by using a test string before actually writing the
             // graph traversal methods.             
             
             void testMoves(StringBuilder inputMoves)
             {
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
                            Screen[currentY+1].setCharAt(currentX, 'R');
                            Screen[currentY].setCharAt(currentX,'.');
                            if (holdingObject)
                                Screen[currentY+2].setCharAt(currentX, heldObject);                            
                            currentY++;
                            System.out.println("Down");
                            break;
                        case 'L':
                            Screen[currentY].setCharAt(currentX-2, 'R');
                            Screen[currentY].setCharAt(currentX, '.');
                            if (holdingObject)
                            {
                                Screen[currentY+1].setCharAt(currentX-2, heldObject);                            
                                Screen[currentY+1].setCharAt(currentX, '.');
                            }
                                //else
                                //Screen[currentY+1].setCharAt(currentX-2, heldObject);
                            currentX = currentX-2;
                            System.out.println("Left");
                            break;
                        case 'R':
                            Screen[currentY].setCharAt(currentX+2, 'R');
                            Screen[currentY].setCharAt(currentX, '.');
                            if (holdingObject)
                            {
                                Screen[currentY+1].setCharAt(currentX+2, heldObject);                            
                                Screen[currentY+1].setCharAt(currentX, '.');
                            }
                                //else
                                //Screen[currentY+1].setCharAt(currentX+2, heldObject);
                            currentX = currentX+2;
                            System.out.println("Right");
                            break;                                                        
                        // The code will not cover falling objects.
                        case 'O':
                            holdingObject = false;                            
                            System.out.println("Open");
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
             }
             

        // Read through a list of strings representing the a map, and determine
        // the X and Y position of the Robot.
        Robot(String[] inputMap)
        {
            Map = inputMap;
            robotMoved = false;            
            for (int i = 0; i < inputMap.length; i++)
                for (int j =0; j< inputMap[0].length()-2; j++)
                {
                    	if (Map[i].charAt​(j) == 'R')
                        {
                            robotX = j/2;
                            robotY = i;                            
                            robotNode = new Node(robotX, robotY, 'R');
                        }
                }
        // I'm going to start with the BaseHeuristic Test and model my code on
        // that and then refine the code as needed.
        
        graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
        nodes = new Node[Map[0].length()][Map.length];        
        usedColumn = new boolean[nodes[0].length];
        for(boolean bool:usedColumn)
            bool = false;
        
        // Create the nodes.
        for (int i = 0; i < Map.length; i++)
            for (int j=0; j<Map[0].length(); j++)
            {                              
                if (Map[i].charAt(j) == '#' || Map[i].charAt(j) == ' ')
                    continue;
                nodes[j][i] = new Node(j/2, i, Map[i].charAt(j));
                graph.addVertex(nodes[j][i]);                
            }
        
        //Create top horizontal edges.
        //  The robot will not move horizontally when its below ceiling height.
        for (int i = 0; i < Map[0].length()-2; i++)            
        {
            if ( (nodes[i][0] == null) || nodes[i+2][0] == null)
                continue;
            Graphs.addEdge(graph, nodes[i][0], nodes[i+2][0], 1);
  // Put in a reverse edge to see if that solves the null path bug.
            Graphs.addEdge(graph, nodes[i+2][0], nodes[i][0], 1);
        }               
        
        //Create vertical edges
        for (int i=0; i < Map.length-1 ; i++)
            for (int j=0; j < Map[0].length(); j++)
            {
                if (nodes[j][i] == null || nodes[j][i+1] == null)
                    continue;
                Graphs.addEdge(graph, nodes[j][i], nodes[j][i+1], 1);
                Graphs.addEdge(graph, nodes[j][i+1], nodes[j][i], 1);
            }
        
        }
        
        // This will be a recursive method that calls clearTop(Node)
        // if there's something on top of what the method is clearing.
        //
        // ClearTop applied to C -- B  results in clearTop C calling clearTop B
        //                          C  before moving C      
        //                          A
        //                     ###########
        //                                    
        StringBuilder clearTop(Node inputNode)
        {
           StringBuilder result = new StringBuilder("..............................");
           StringBuilder clearTopResult1 = new StringBuilder();
           Node targetNode1 = new Node(0,0,' ');
           Node targetNode2 = new Node(0,0,' ');
           targetNode1 = nodes[inputNode.x*2][inputNode.y-1];
           int block1X, block1Y, block2X, block2Y;
           block1X = 0;
           block1Y = 0;
           block2X = 0;
           block2Y = 0;
           char block2C = inputNode.c;
           
           if (Character.isLetter(targetNode1.c))
               clearTopResult1 = clearTop(targetNode1);
           
           block1X = inputNode.x;
           block1Y = inputNode.y;
           
            BFSShortestPath<Node, DefaultWeightedEdge> bfsAlg =
            new BFSShortestPath<>(graph);
            GraphPath<Node, DefaultWeightedEdge> path = bfsAlg.getPath(robotNode, targetNode1);
            System.out.println(path);
            
           List<Node> block1Path = path.getVertexList();
            ListIterator<Node> itr = block1Path.listIterator();
            Node temp1Node = new Node(0, 0, ' ');            
            Node temp2Node = new Node(0, 0, ' ');
       
            temp1Node = itr.next();            
            int currentElement = 0;
            while (itr.hasNext())
            {
                temp2Node = itr.next();
                
                if ((temp2Node.x - temp1Node.x) == 1)
                    result.insert(currentElement, 'R');
                    //movesResult.append('R');
                if ((temp2Node.x - temp1Node.x) == -1)
                    result.insert(currentElement, 'L');
                    //movesResult.append('L');
                if ((temp2Node.y - temp1Node.y) == 1) // The origin is top left
                    result.insert(currentElement, 'D');
                    //movesResult.append('D');
                if ((temp2Node.y - temp1Node.y) == -1)
                    result.insert(currentElement, 'U');
                    //movesResult.append('U');
                temp1Node = temp2Node;
                currentElement++;
            }
            //currentElement--;
            result.insert(currentElement, 'C');
            currentElement++;
            
           int wasteColumn = 0;
           for (int i = 0; i < usedColumn.length; i++)
           {
               if (usedColumn[i] == false)
               {
                   wasteColumn = i;
                   break;
               }               
           }
           
           // Now to find the exact position
           // The LZ cannot be the sky
           for (int i = nodes[wasteColumn].length-1; i > 0; i--)
           {
               if (!Character.isLetter(nodes[wasteColumn][i].c))
               {
                   targetNode2 = nodes[wasteColumn][i-1];
                   block2Y = i;
                   block2X = wasteColumn;
                   break;
               }               
           }
           
            path = bfsAlg.getPath(targetNode1, targetNode2);                       
            block1Path = path.getVertexList();
            itr = block1Path.listIterator();
            temp1Node = itr.next();
            
            while (itr.hasNext())
            {
                int x1, y1, x2, y2;               
                       
                temp2Node = itr.next();
                if ((temp2Node.x - temp1Node.x) == 1)
                    result.insert(currentElement, 'R');
                    //movesResult.append('R');
                if ((temp2Node.x - temp1Node.x) == -1)
                    result.insert(currentElement, 'L');
                    //movesResult.append('L');
                if ((temp2Node.y - temp1Node.y) == 1) // The origin is top left
                    result.insert(currentElement, 'D');
                    //movesResult.append('D');
                if ((temp2Node.y - temp1Node.y) == -1)
                    result.insert(currentElement, 'U');
                    //movesResult.append('U');
                temp1Node = temp2Node;
                currentElement++;
            }
            result.insert(currentElement, 'O');
            currentElement++;
            //Update the nodes
            nodes[block1X*2][block1Y].c = '.';
            //nodes[block2X*2][block2Y-1].c = inputNode.c;
            //nodes[wasteColumn][block2Y].c = inputNode.c;
            nodes[wasteColumn][block2Y].c = block2C;
            
            //Now, get the remaining 'U's in and move the robotic arm back to
            //the top.
            for (int Y=targetNode2.y; Y>0; Y--)
            {
                result.insert(currentElement, 'U');
                currentElement++;
            }
            // Set the robot position to the top of this column            
            //robotNode = nodes[block2X * 2][block2Y];
            robotNode = nodes[wasteColumn][0];
            // Now to cut out the '.'s at the end.
            result = result.delete(currentElement, result.length());
           
            if (clearTopResult1.length() > 0)
                result = clearTopResult1.append(result);
            
           return result;
        }
        
        StringBuilder putOn(char block1, char block2)
        {
            Node block1Node, block2Node;
            // Later on, figure out a better way to solve the heap space exception than merely creating
            // one giant string.
            StringBuilder movesResult = new StringBuilder("..............................");
            StringBuilder clearTopResult1, clearTopResult2;
            clearTopResult1 = new StringBuilder();
            clearTopResult2 = new StringBuilder();
            block1Node = new Node(0,0,' ');
            block2Node = new Node(0, 0, ' ');
            Node targetNode1 = new Node(0,0,' ');
            Node targetNode2 = new Node(0,0,' ');
            Node dropNode = new Node(0, 0, ' ');
            
            int block1X, block1Y, block2X, block2Y;
            block1X = 0;
            block1Y = 0;
            block2X = 0;
            block2Y = 0;
            
            
            for (int i = 0; i < nodes.length; i++)
                for (int j=0; j<nodes[0].length; j++)
                {
                    if (nodes[i][j] == null)
                        continue;
                    if (nodes[i][j].c == block1)                                                                           
                    {
                        block1Node = nodes[i][j];                                            
                        targetNode1 = nodes[i][j-1];
                        block1X = block1Node.x;
                        block1Y = block1Node.y;
                        usedColumn[i/2] = true;                        
                    }
                    if (nodes[i][j].c == block2)                                                               
                    {
                        block2Node = nodes[i][j];
                        dropNode = nodes[i][j-1];
                        targetNode2 = nodes[i][j-2];
                        block2X = block2Node.x;
                        block2Y = block2Node.y;
                        usedColumn[i/2] = true;
                    }
                    if ( (nodes[i][j].c == 'R') && (robotMoved == false))
                    { 
                    // Any subsequent robot moves should use the robot coordinates
                    // stored from previous calls of putOn or clear
                        robotNode = nodes[i][j];
                        robotMoved = true;
                    }                                    
                }
                
            BFSShortestPath<Node, DefaultWeightedEdge> bfsAlg =
            new BFSShortestPath<>(graph);
           
            System.out.println(block1Node);
            System.out.println(robotNode);
            System.out.println(targetNode1);
            
            // Print out the graph to be sure it's really complete
           /* Iterator<Node> iter = new DepthFirstIterator<>(graph);
            while (iter.hasNext()) {
            Node vertex = iter.next();
            System.out
                .println(
                    "Vertex " + vertex + " is connected to: "
                        + graph.edgesOf(vertex).toString());        
            }
            
            System.out.println(robotNode);
            System.out.println(graph.containsVertex(robotNode));
            System.out.println(block1Node);
            System.out.println(graph.containsVertex(block1Node));                                                           
            */
            
           //Here, check to see if targetNode1 is occupied and run the
           // recursive clearTop method if there is.
           if (Character.isLetter(targetNode1.c))           
               clearTopResult1 = clearTop(targetNode1);                          
           //Do another check above targetNode2 and run clearTop if there
            // is an object above targetNode2.
             if (Character.isLetter(dropNode.c))           
               clearTopResult1 = clearTop(dropNode); 
             
            GraphPath<Node, DefaultWeightedEdge> path = bfsAlg.getPath(robotNode, targetNode1);
            System.out.println(path);                                      
            // Now to actually create the string representing the moves.
            List<Node> block1Path = path.getVertexList();
            ListIterator<Node> itr = block1Path.listIterator();
            Node temp1Node = new Node(0, 0, ' ');            
            Node temp2Node = new Node(0, 0, ' ');
       
            temp1Node = itr.next();            
            int currentElement = 0;
            while (itr.hasNext())
            {
                temp2Node = itr.next();
                
                if ((temp2Node.x - temp1Node.x) == 1)
                    movesResult.insert(currentElement, 'R');
                    //movesResult.append('R');
                if ((temp2Node.x - temp1Node.x) == -1)
                    movesResult.insert(currentElement, 'L');
                    //movesResult.append('L');
                if ((temp2Node.y - temp1Node.y) == 1) // The origin is top left
                    movesResult.insert(currentElement, 'D');
                    //movesResult.append('D');
                if ((temp2Node.y - temp1Node.y) == -1)
                    movesResult.insert(currentElement, 'U');
                    //movesResult.append('U');
                temp1Node = temp2Node;
                currentElement++;
            }
            //currentElement--;
            movesResult.insert(currentElement, 'C');
            currentElement++;
            
            
            
            // Now to put the block1 on top of block2          
            path = bfsAlg.getPath(targetNode1, targetNode2);                       
            block1Path = path.getVertexList();
            itr = block1Path.listIterator();
            temp1Node = itr.next();
            
            while (itr.hasNext())
            {
                int x1, y1, x2, y2;               
                       
                temp2Node = itr.next();
                if ((temp2Node.x - temp1Node.x) == 1)
                    movesResult.insert(currentElement, 'R');
                    //movesResult.append('R');
                if ((temp2Node.x - temp1Node.x) == -1)
                    movesResult.insert(currentElement, 'L');
                    //movesResult.append('L');
                if ((temp2Node.y - temp1Node.y) == 1) // The origin is top left
                    movesResult.insert(currentElement, 'D');
                    //movesResult.append('D');
                if ((temp2Node.y - temp1Node.y) == -1)
                    movesResult.insert(currentElement, 'U');
                    //movesResult.append('U');
                temp1Node = temp2Node;
                currentElement++;
            }
            movesResult.insert(currentElement, 'O');
            currentElement++;
            //Update the nodes
            nodes[block1X*2][block1Y].c = '.';
            nodes[block2X*2][block2Y-1].c = block2;

            //Now, get the remaining 'U's in and move the robotic arm back to
            //the top.
            for (int Y=targetNode2.y; Y>0; Y--)
            {
                movesResult.insert(currentElement, 'U');
                currentElement++;
            }
            // Set the robot position to the top of this column            
            robotNode = nodes[block2X * 2][block2Y];
            // Now to cut out the '.'s at the end.
            movesResult = movesResult.delete(currentElement, movesResult.length());
            
            if (clearTopResult1.length()> 0)
                movesResult = clearTopResult1.append(movesResult);
            if (clearTopResult2.length() > 0)
                movesResult = clearTopResult2.append(movesResult);
            
            System.out.println(movesResult);
            return movesResult;
        }
private
        int robotX, robotY;
        String[] Map;
        boolean robotMoved;
        
        Graph<Node, DefaultWeightedEdge> graph;
        Node[][] nodes;        
        Node robotNode;
        boolean[] usedColumn; // The false columns will be used for clearing
                              // the tops off of the blocks for putOn.        
    }
    
    public static void main(String[] args) {
         // R is the search robot itself.
    // # is the ground symbol which isn't needed
    //   for flat terrain.
    
    // The trivial case.  The Robot only has to go pick up B and then carry
    // it to the top of A.
    String[] searchString1 =
    { ". . . . . . R . . .",
      ". . . . . . . . . .",
      ". . . . . . . . . .",
      ". . . . . . . . . .",
      ". . . . . . . . . .",
      ". A . . B . . . . .",
    };
    
    String[] searchString2 =
    { ". . . . . . R . . .",
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
            
//        String testString[] = searchString1;
        String testString[] = searchString3;
        Robot testRobot1 = new Robot(testString);
        StringBuilder testMoves1 = new StringBuilder("LLDDDDCUUUULLLDDDOUUU");
        StringBuilder testMovesResult = new StringBuilder();
        //testRobot1.testMoves(testMoves1);       
        testMovesResult = testRobot1.putOn('B', 'A');
       testRobot1.testMoves(testMovesResult);
        
        
}
}