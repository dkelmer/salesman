package salesman;

import java.util.InputMismatchException;
import java.util.Scanner;
 
public class Prims
{
    private boolean unsettled[];
    private boolean settled[];
    private int numberofvertices;
    private double adjacencyMatrix[][];
    private double key[];
    public static final double INFINITE = Double.MAX_VALUE;
    private int parent[];
 
    public Prims(int numberofvertices)
    {
        this.numberofvertices = numberofvertices;
        unsettled = new boolean[numberofvertices];
        settled = new boolean[numberofvertices];
        adjacencyMatrix = new double[numberofvertices][numberofvertices];
        key = new double[numberofvertices];
        parent = new int[numberofvertices];
    }
 
    public int getUnsettledCount(boolean unsettled[])
    {
        int count = 0;
        for (int index = 0; index < unsettled.length; index++)
        {
            if (unsettled[index])
            {
                count++;
            }
        }
        return count;
    }
 
    public void primsAlgorithm(double[][] adjacency_matrix)
    {
        int evaluationVertex;
        for (int source = 0; source < numberofvertices; source++)
        {
            for (int destination = 0; destination < numberofvertices; destination++)
            {
                this.adjacencyMatrix[source][destination] = adjacency_matrix[source][destination];
            }
        }
 
        for (int index = 0; index < numberofvertices; index++)
        {
            key[index] = INFINITE;
        }
        key[1] = 0;
        unsettled[1] = true;
        parent[1] = 1;
 
        while (getUnsettledCount(unsettled) != 0)
        {
            evaluationVertex = getMimumKeyVertexFromUnsettled(unsettled);
            unsettled[evaluationVertex] = false;
            settled[evaluationVertex] = true;
            evaluateNeighbours(evaluationVertex);
        }
    } 
 
    private int getMimumKeyVertexFromUnsettled(boolean[] unsettled2)
    {
        double min = Double.MAX_VALUE;
        int node = 0;
        for (int vertex = 0; vertex < numberofvertices; vertex++)
        {
            if (unsettled[vertex] == true && key[vertex] < min)
            {
                node = vertex;
                min = key[vertex];
            }
        }
        return node;
    }
 
    public void evaluateNeighbours(int evaluationVertex)
    {
 
        for (int destinationvertex = 0; destinationvertex < numberofvertices; destinationvertex++)
        {
            if (settled[destinationvertex] == false)
            {
                if (adjacencyMatrix[evaluationVertex][destinationvertex] != INFINITE)
                {
                    if (adjacencyMatrix[evaluationVertex][destinationvertex] < key[destinationvertex])
                    {
                        key[destinationvertex] = adjacencyMatrix[evaluationVertex][destinationvertex];
                        parent[destinationvertex] = evaluationVertex;
                    }
                    unsettled[destinationvertex] = true;
                }
            }
        }
    }
 
    public void printMST()
    {
        System.out.println("SOURCE  : DESTINATION = WEIGHT");
        for (int vertex = 0; vertex < numberofvertices; vertex++)
        {
            System.out.println(parent[vertex] + "\t:\t" + vertex +"\t=\t"+ adjacencyMatrix[parent[vertex]][vertex]);
        }
    }
    
    public static void main(String... arg)
    {
        double adjacency_matrix[][];
        int number_of_vertices;
        Scanner scan = new Scanner(System.in);
 
        try
        {
            System.out.println("Enter the number of vertices");
            number_of_vertices = scan.nextInt();
            adjacency_matrix = new double[number_of_vertices + 1][number_of_vertices + 1];
 
            System.out.println("Enter the Weighted Matrix for the graph");
            for (int i = 1; i <= number_of_vertices; i++)
            {
                for (int j = 1; j <= number_of_vertices; j++)
                {
                    adjacency_matrix[i][j] = scan.nextInt();
                    if (i == j)
                    {
                        adjacency_matrix[i][j] = 0;
                        continue;
                    }
                    if (adjacency_matrix[i][j] == 0)
                    {
                        adjacency_matrix[i][j] = INFINITE;
                    }
                }
            }
            
            for(int i = 0; i < adjacency_matrix.length; i++) {
            	for(int j = 0; j < adjacency_matrix.length; j++) {
            		System.out.print(adjacency_matrix[i][j] + " ");
            	}
            	System.out.println();
            }
 
            Prims prims = new Prims(number_of_vertices);
            prims.primsAlgorithm(adjacency_matrix);
            prims.printMST();
 
        } catch (InputMismatchException inputMismatch)
        {
            System.out.println("Wrong Input Format");
        }
        scan.close();
    }
 
    
}