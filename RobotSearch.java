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
             void testMoves() {
               this.testMoves(Moves);
             }
             void testMoves(String inputMoves)
             {
                int currentX, currentY, stringPosition;
                // stringPosition is current position in inputMoves;
                boolean holdingObject = false;
                boolean gripClosed = false;
                char heldObject = '.';
                StringBuffer[] Screen = new StringBuffer[Map.length];
                for (int i = 0; i < Map.length; i++)
                    Screen[i] = new StringBuffer(Map[i]);
                
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
                                else
                                Screen[currentY+1].setCharAt(currentX-2, heldObject);
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
                                else
                                Screen[currentY+1].setCharAt(currentX+2, heldObject);
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
            
        void putOn(char block1, char block2)
        {
            Node block1Node, block2Node;
            block1Node = new Node(0,0,' ');
            block2Node = new Node(0, 0, ' ');
            /*
            int block1X, block1Y, block2X, block2Y;
            block1X = 0;
            block1Y = 0;
            block2X = 0;
            block2Y = 0;
            */
            
            for (int i = 0; i < nodes.length; i++)
                for (int j=0; j<nodes[0].length; j++)
                {
                    if (nodes[i][j] == null)
                        continue;
                    if (nodes[i][j].c == block1)                                                                           
                       block1Node = nodes[i][j];                                            
                    if (nodes[i][j].c == block2)                                                               
                        block2Node = nodes[i][j];
                    if (nodes[i][j].c == 'R')
                        robotNode = nodes[i][j];                                    
                }
                
            BFSShortestPath<Node, DefaultWeightedEdge> bfsAlg =
            new BFSShortestPath<>(graph);
           
            DijkstraShortestPath<Node, DefaultWeightedEdge> dijkstraAlg =
            new DijkstraShortestPath<>(graph);
            
            System.out.println(block1Node);
            System.out.println(robotNode);
            
            // Print out the graph to be sure it's really complete
            Iterator<Node> iter = new DepthFirstIterator<>(graph);
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

            
            GraphPath<Node, DefaultWeightedEdge> path = bfsAlg.getPath( robotNode, block1Node);
            System.out.println(path);                                      
                
        }
private
        int robotX, robotY;
        String[] Map;
        String Moves;
        Graph<Node, DefaultWeightedEdge> graph;
        Node[][] nodes;        
        Node robotNode;
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
        String testString[] = searchString1;
        Robot testRobot1 = new Robot(testString);
        String testMoves1 = "LLDDDDCUUUULLLDDDOUUU";
        testRobot1.testMoves(testMoves1);
        testRobot1.putOn('B', 'A');
        
}
}