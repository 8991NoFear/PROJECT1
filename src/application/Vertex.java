package application;

import java.util.LinkedList;

import javafx.scene.layout.StackPane;

public class Vertex {
	private String nameID;
	private StackPane dot;
	private LinkedList<Edge> adj = new LinkedList<Edge>();
	private LinkedList<Edge> reAdj = new LinkedList<Edge>();
	
	// for graph traveling
	private int color;
	
	// for dijkstra
	private long currentMinDistance;
	private Vertex vertexBeforeThisVertex;
	
	public Vertex() {
		setColor(-1);
	}
	
	public Vertex(String nameID) {
		setNameID(nameID);
		setColor(-1);
	}
	
	public String getNameID() {
		return nameID;
	}
	
	public void setNameID(String nameID) {
		this.nameID = nameID;
	}
	
	public StackPane getDot() {
		return dot;
	}
	
	public void setDot(StackPane dot) {
		this.dot = dot;
	}
	
	public LinkedList<Edge> getAdj() {
		return adj;
	}
	
	public void setAdj(LinkedList<Edge> adj) {
		this.adj = adj;
	}
	
	public LinkedList<Edge> getReAdj() {
		return reAdj;
	}
	public void setReAdj(LinkedList<Edge> reAdj) {
		this.reAdj = reAdj;
	}
	
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	
	public long getCurrentMinDistance() {
		return currentMinDistance;
	}

	public void setCurrentMinDistance(long currentMinDistance) {
		this.currentMinDistance = currentMinDistance;
	}

	public Vertex getVertexBeforeThisVertex() {
		return vertexBeforeThisVertex;
	}

	public void setVertexBeforeThisVertex(Vertex vertexBeforeThisVertex) {
		this.vertexBeforeThisVertex = vertexBeforeThisVertex;
	}
}
