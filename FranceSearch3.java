/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.francesearch3;

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
public class FranceSearch3 {
   public static class Node {
     public final double longitude;
     public final String city; 
     Node(String city, double longitude)
     {
         this.longitude = longitude;
         this.city = city;
     }
     public String toString()
     {
         return city;     
     }
   }
    
   public static class LongitudeDistance implements
           AStarAdmissibleHeuristic<Node>
   {       
    @Override
    public double getCostEstimate(Node sourceVertex, Node targetVertex)
    {
        return Math.abs(sourceVertex.longitude - targetVertex.longitude);
    }
   }
    public static void main(String[] args) {                
        Graph<Node, DefaultEdge> RoadMap =
            new DefaultDirectedWeightedGraph<Node, DefaultEdge>(DefaultEdge.class);
        
        Node Brest = new Node("Brest",-4.48);
        Node Rennes = new Node("Rennes", -1.67);
        Node Caen = new Node("Caen", -0.37);
        Node Calais = new Node("Calais", 1.85);
        Node Nancy = new Node("Nancy", 6.18);
        Node Strasbourg = new Node("Strasbourg", 7.73);
        Node Dijon = new Node("Dijon", 5.02);
        Node Lyon = new Node("Lyon", 4.83);
        Node Grenoble = new Node("Grenoble", 5.72);
        Node Avignon = new Node("Avignon", 4.80);
        Node Marseille = new Node("Marseille", 5.37);
        Node Nice = new Node("Nice", 7.25);
        Node Montpellier = new Node("Montpellier", 3.87);
        Node Toulouse = new Node("Toulouse", 1.43);
        Node Bordeaux = new Node("Bordeaux",-0.58);
        Node Limoges = new Node("Limoges", 1.25);
        Node Nantes = new Node("Nantes", -1.55);
        Node Paris = new Node("Paris", 2.35);
    
    RoadMap.addVertex(Brest);
    RoadMap.addVertex(Rennes);
    RoadMap.addVertex(Caen);
    RoadMap.addVertex(Calais);
    RoadMap.addVertex(Nancy);
    RoadMap.addVertex(Strasbourg);
    RoadMap.addVertex(Dijon);
    RoadMap.addVertex(Lyon);
    RoadMap.addVertex(Grenoble);
    RoadMap.addVertex(Avignon);
    RoadMap.addVertex(Marseille);
    RoadMap.addVertex(Nice);
    RoadMap.addVertex(Montpellier);
    RoadMap.addVertex(Toulouse);
    RoadMap.addVertex(Bordeaux);
    RoadMap.addVertex(Limoges);
    RoadMap.addVertex(Nantes);
    RoadMap.addVertex(Paris);
    
    RoadMap.addEdge(Brest, Rennes);
    RoadMap.addEdge(Nice, Marseille);
    RoadMap.setEdgeWeight(Brest, Rennes, 244);
    RoadMap.setEdgeWeight(Nice, Marseille, 188);

    LinkedList<Node> RennesList = new LinkedList<Node>
        (Arrays.asList(Caen, Paris, Brest, Nantes));
    LinkedList<Node> CaenList = new LinkedList<Node>
        (Arrays.asList(Calais, Paris, Rennes));
    LinkedList<Node> CalaisList = new LinkedList<Node>
        (Arrays.asList(Nancy, Paris, Caen));
    LinkedList<Node> NancyList = new LinkedList<Node>
            (Arrays.asList(Strasbourg, Dijon, Paris, Calais));
    LinkedList<Node> StrasbourgList = new LinkedList<Node>
            (Arrays.asList(Dijon, Nancy));
    LinkedList<Node> DijonList = new LinkedList<Node>
            (Arrays.asList(Strasbourg, Lyon, Paris, Nancy));
    LinkedList<Node> LyonList = new LinkedList<Node>
            (Arrays.asList(Grenoble, Avignon, Limoges, Dijon));
    LinkedList<Node> GrenobleList = new LinkedList<Node>
            (Arrays.asList(Avignon, Lyon));
    LinkedList<Node> AvignonList = new LinkedList<Node>
            (Arrays.asList(Grenoble, Marseille, Montpellier, Lyon));
    LinkedList<Node> MarseilleList = new LinkedList<Node>
            (Arrays.asList(Nice, Avignon));
    LinkedList<Node> MontpellierList = new LinkedList<Node>
            (Arrays.asList(Avignon, Toulouse));
    LinkedList<Node> ToulouseList = new LinkedList<Node>
            (Arrays.asList(Montpellier, Bordeaux, Limoges));
    LinkedList<Node> BordeauxList = new LinkedList<Node>
            (Arrays.asList(Limoges, Toulouse, Nantes));
    LinkedList<Node> LimogesList = new LinkedList<Node>
            (Arrays.asList(Lyon, Toulouse, Bordeaux, Nantes, Paris));
    LinkedList<Node> NantesList = new LinkedList<Node>
            (Arrays.asList(Limoges, Bordeaux, Rennes));
    LinkedList<Node> ParisList = new LinkedList<Node>
            (Arrays.asList(Calais, Nancy, Dijon, Limoges, Rennes, Caen));
    
    Iterator<Node> itr = RennesList.iterator();
    while(itr.hasNext())
      RoadMap.addEdge(Rennes, itr.next());              
    itr = CaenList.iterator();
    while (itr.hasNext())
        RoadMap.addEdge(Caen, itr.next());
    itr = CalaisList.iterator();
    while (itr.hasNext())
        RoadMap.addEdge(Calais, itr.next());
    itr = NancyList.iterator();
    while (itr.hasNext())
        RoadMap.addEdge(Nancy, itr.next());
    itr = StrasbourgList.iterator();
    while (itr.hasNext())
        RoadMap.addEdge(Strasbourg, itr.next());
    itr = DijonList.iterator();
    while (itr.hasNext())
        RoadMap.addEdge(Dijon, itr.next());
    itr = LyonList.iterator();
    while (itr.hasNext())
        RoadMap.addEdge(Lyon, itr.next());
    itr = GrenobleList.iterator();
    while (itr.hasNext())
        RoadMap.addEdge(Grenoble, itr.next());
    itr = AvignonList.iterator();
    while (itr.hasNext())
        RoadMap.addEdge(Avignon, itr.next());
    itr = MarseilleList.iterator();
    while (itr.hasNext())
        RoadMap.addEdge(Marseille, itr.next());
    itr = MontpellierList.iterator();
    while (itr.hasNext())
        RoadMap.addEdge(Montpellier, itr.next());
    itr = ToulouseList.iterator();
    while (itr.hasNext())
        RoadMap.addEdge(Toulouse, itr.next());
    itr = BordeauxList.iterator();
    while (itr.hasNext())
        RoadMap.addEdge(Bordeaux, itr.next());
    itr = LimogesList.iterator();
    while (itr.hasNext())
        RoadMap.addEdge(Limoges, itr.next());
    itr = NantesList.iterator();
    while (itr.hasNext())
        RoadMap.addEdge(Nantes, itr.next());
    itr = ParisList.iterator();
    while (itr.hasNext())
        RoadMap.addEdge(Paris, itr.next());

//Set the edge weights for all these paths.
    RoadMap.setEdgeWeight(Rennes, Caen, 176);
    RoadMap.setEdgeWeight(Rennes, Paris, 348);
    RoadMap.setEdgeWeight(Rennes, Brest, 244);
    RoadMap.setEdgeWeight(Rennes, Nantes, 107);
    
    RoadMap.setEdgeWeight(Caen, Calais, 120);
    RoadMap.setEdgeWeight(Caen, Paris, 241);
    RoadMap.setEdgeWeight(Caen, Rennes, 176);
    
    RoadMap.setEdgeWeight(Calais, Nancy, 534);
    RoadMap.setEdgeWeight(Calais, Paris, 297);
    RoadMap.setEdgeWeight(Calais, Caen, 120);
    
    RoadMap.setEdgeWeight(Nancy, Strasbourg, 145);
    RoadMap.setEdgeWeight(Nancy, Dijon, 201);    
    RoadMap.setEdgeWeight(Nancy, Paris, 372);
    RoadMap.setEdgeWeight(Nancy, Calais, 534);
    
    RoadMap.setEdgeWeight(Strasbourg, Dijon, 335);
    RoadMap.setEdgeWeight(Strasbourg, Nancy, 145);
    
    RoadMap.setEdgeWeight(Dijon, Strasbourg, 335);
    RoadMap.setEdgeWeight(Dijon, Lyon, 192);
    RoadMap.setEdgeWeight(Dijon, Paris, 313);
    RoadMap.setEdgeWeight(Dijon, Nancy, 201);
    
    RoadMap.setEdgeWeight(Lyon, Grenoble, 104);
    RoadMap.setEdgeWeight(Lyon, Avignon, 216);
    RoadMap.setEdgeWeight(Lyon, Limoges, 389);
    RoadMap.setEdgeWeight(Lyon, Dijon, 192);
    
    RoadMap.setEdgeWeight(Grenoble, Avignon, 227);
    RoadMap.setEdgeWeight(Grenoble, Lyon,104);
    
    RoadMap.setEdgeWeight(Avignon, Grenoble, 227);
    RoadMap.setEdgeWeight(Avignon, Marseille, 99);
    RoadMap.setEdgeWeight(Avignon, Montpellier, 91);
    RoadMap.setEdgeWeight(Avignon, Lyon, 216);
    
    RoadMap.setEdgeWeight(Marseille, Nice, 188);
    RoadMap.setEdgeWeight(Marseille, Avignon, 99);
    
    RoadMap.setEdgeWeight(Montpellier, Avignon, 91);
    RoadMap.setEdgeWeight(Montpellier, Toulouse, 240);
    
    RoadMap.setEdgeWeight(Toulouse, Montpellier, 240);
    RoadMap.setEdgeWeight(Toulouse, Bordeaux, 253);
    RoadMap.setEdgeWeight(Toulouse, Limoges, 313);
    
    RoadMap.setEdgeWeight(Bordeaux,Limoges , 220);
    RoadMap.setEdgeWeight(Bordeaux,Toulouse , 253);
    RoadMap.setEdgeWeight(Bordeaux,Nantes , 329);
    
    RoadMap.setEdgeWeight(Limoges,Lyon , 389);
    RoadMap.setEdgeWeight(Limoges,Toulouse , 313);
    RoadMap.setEdgeWeight(Limoges,Bordeaux , 220);
    RoadMap.setEdgeWeight(Limoges,Nantes , 329);
    RoadMap.setEdgeWeight(Limoges,Paris , 396);
    
    RoadMap.setEdgeWeight(Nantes,Limoges , 329);
    RoadMap.setEdgeWeight(Nantes,Bordeaux , 329);
    RoadMap.setEdgeWeight(Nantes,Rennes , 107);
    
    RoadMap.setEdgeWeight(Paris,Calais , 297);
    RoadMap.setEdgeWeight(Paris,Nancy , 372);
    RoadMap.setEdgeWeight(Paris,Dijon , 313);
    RoadMap.setEdgeWeight(Paris,Limoges , 396);
    RoadMap.setEdgeWeight(Paris,Rennes , 348);
    RoadMap.setEdgeWeight(Paris,Caen , 241);    
    
    AStarShortestPath<Node,  DefaultEdge> aStarShortestPath =
            new AStarShortestPath<>(RoadMap, new LongitudeDistance());
    GraphPath<Node, DefaultEdge> path = aStarShortestPath.getPath(Rennes, Avignon);
    System.out.println(path);
    //System.out.println("Total weight of shortest path is " + dijkstraAlg.getPathWeight(Rennes, Avignon));
    }
}
