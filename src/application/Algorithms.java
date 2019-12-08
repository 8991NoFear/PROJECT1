package application;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

public class Algorithms {
	private SceneController sceneController;
	private LinkedList<Vertex> header;
	private LinkedList<PauseTransition> transList;
	private TextFlow result;
	private int milis = 360;
	
	public Algorithms(SceneController sceneController) {
		transList = new LinkedList<PauseTransition>();
		this.sceneController = sceneController;
		header = sceneController.getHeader();
		result = sceneController.getResultText();
	}
	
	private void changeColorVertex(String color, Vertex vtx) {
		vtx.getDot().getChildren().get(0).setStyle("-fx-fill: " + color);
	}
	
	private void changeColorEdge(String color, Edge edge) {
		edge.getLine().setStyle("-fx-stroke: " + color);
	}
	
	private void sortAdjEdge(Vertex vtx) {
		vtx.getAdj().sort(new Comparator<Edge>() {

			@Override
			public int compare(Edge edge1, Edge edge2) {
				return edge1.getEnd().getNameID().compareTo(edge2.getEnd().getNameID());
			}
			
		});
	}
	
	public void resetGraph() {
		Iterator<Vertex> iterVtx = header.iterator();
		while(iterVtx.hasNext()) {
			// change color for vertex
			Vertex vtx = iterVtx.next();
			vtx.setColor(-1);
			changeColorVertex("black", vtx);
			
			// sort adjacent edge for vertex
			sortAdjEdge(vtx);
			
			// change color for adjacent edge
			Iterator<Edge> iterEdge = vtx.getAdj().iterator();
			while(iterEdge.hasNext()) {
				Edge edge = iterEdge.next();
				changeColorEdge("black", edge);
			}
		}
	}
	
	private void clearTransList() {
		transList.clear();
	}
	
	private void transForVertex(Vertex vtx, String color, int milis) {
		PauseTransition pt = new PauseTransition();
		pt.setAutoReverse(false);
		pt.setDuration(Duration.millis(milis));
		pt.setOnFinished(e -> {;
			changeColorVertex(color, vtx);
		});
		transList.add(pt);
	}
	
	private void transForEdge(Edge edge, String color, int milis) {
		PauseTransition pt = new PauseTransition();
		pt.setAutoReverse(false);
		pt.setDuration(Duration.millis(milis));
		pt.setOnFinished(e -> {;
			changeColorEdge(color, edge);
		});
		transList.add(pt);
	}
	
	private void playTrans() {
		SequentialTransition seqTrans = new SequentialTransition();
		seqTrans.getChildren().addAll(transList);
		sceneController.disableAllJFXToggleButton();
		seqTrans.play();
		seqTrans.setOnFinished(e -> {
			sceneController.enableAllJFXToggleButton();
		});
	}
	
	private void BFSVisit(Vertex s, StringBuffer sb) {
		s.setColor(0);
		transForVertex(s, "blue", milis);
		Queue<Vertex> queue = (Queue<Vertex>) new LinkedList<Vertex>();
		queue.add(s);
		while(!queue.isEmpty()) {
			Vertex u = queue.poll();
			Iterator<Edge> iterEdge = u.getAdj().iterator();
			while(iterEdge.hasNext()) {
				Edge edge = iterEdge.next();
				Vertex v = edge.getEnd();
				if(v.getColor() == -1) {
					transForEdge(edge, "red", milis);
					v.setColor(0);
					transForVertex(v, "blue", milis);
					queue.add(v);
				}
			}
			u.setColor(1);
			transForVertex(u, "red", milis);
			sb.append(u.getNameID() + " -> ");
		}
	}
	
	public void BFSGraph() {
		StringBuffer sb = new StringBuffer();
		sb.append("Breadth First Search Graph\n");
		
		resetGraph();
		clearTransList();
		Iterator<Vertex> iter = header.iterator();
		while(iter.hasNext()) {
			Vertex vtx = iter.next();
			if(vtx.getColor() == -1) {
				BFSVisit(vtx, sb);
			}
		}
		
		sb.setLength(sb.length() - 4);
		sb.append("\n-------------\n");
		Text text = new Text();
		text.setText(sb.toString());
		result.getChildren().add(text);
		playTrans();
	}
	
	private void DFSVisit(Vertex u, StringBuffer sb) {
		u.setColor(0);
		transForVertex(u, "blue", milis);
		Iterator<Edge> iterEdge = u.getAdj().iterator();
		while(iterEdge.hasNext()) {
			Edge edge = iterEdge.next();
			Vertex v = edge.getEnd();
			if(v.getColor() == -1) {
				transForEdge(edge, "red", milis);
				DFSVisit(v, sb);
			}
		}
		u.setColor(1);
		transForVertex(u, "red", milis);
		sb.append(u.getNameID() + " -> ");
	}
	
	public void DFSGraph() {
		StringBuffer sb = new StringBuffer();
		sb.append("Depth First Search Graph\n");
		
		resetGraph();
		clearTransList();
		Iterator<Vertex> iterVtx = header.iterator();
		while(iterVtx.hasNext()) {
			Vertex u = iterVtx.next();
			if(u.getColor() == - 1) {
				DFSVisit(u, sb);
			}
		}
		
		sb.setLength(sb.length() - 4);
		sb.append("\n-------------\n");
		Text text = new Text();
		text.setText(sb.toString());
		result.getChildren().add(text);
		
		playTrans();
	}
	
	public void dijkstra(Vertex source) {
		LinkedList<Vertex> Q = new LinkedList<Vertex>();
		for(Vertex v: header) {
			v.setCurrentMinDistance(Integer.MAX_VALUE);
			v.setVertexBeforeThisVertex(source);
			Q.add(v);
		}
		source.setCurrentMinDistance(0);
		
		while(!Q.isEmpty()) {
			Vertex u = getVertexHasMinDistance(Q);
			Q.remove(u);
			for(Edge edge: u.getAdj()) {
				Vertex v = edge.getEnd();
				long alt = u.getCurrentMinDistance() + edge.getWeight();
				if(alt < v.getCurrentMinDistance()) {
					v.setCurrentMinDistance(alt);
					v.setVertexBeforeThisVertex(u);
				}
			}
		}
		
		showSP(source);
	}
	
	private Vertex getVertexHasMinDistance(LinkedList<Vertex> Q) {
		Vertex v = Q.getFirst();
		for(Vertex u: Q) {
			if(u.getCurrentMinDistance() < v.getCurrentMinDistance()) {
				v = u;
			}
		}
		return v;
	}
	
	private void showSP(Vertex source) {
		// visual result
		for(Vertex end: header) {
			changeColorVertex("red", end);
			Vertex start = end.getVertexBeforeThisVertex();
			for(Edge edge: start.getAdj()) {
				if(edge.getEnd() == end) {
					changeColorEdge("red", edge);
				}
			}
		}
		
		// text result
		AllSPFromSrc(source);
	}
	
	private void AllSPFromSrc(Vertex source) {
		StringBuffer sb = new StringBuffer();
		sb.append("Shortest path from vertex (" + source.getNameID() + ") to vertex");
		for(Vertex dest: header) {
			sb.append("\n(" + dest.getNameID() + "): path = ");
			SPFromSrcToDest(source, dest, sb);
			sb.append(dest.getNameID() + ", price = " + dest.getCurrentMinDistance());
		}
		
		sb.append("\n-------------\n");
		Text text = new Text(sb.toString());
		result.getChildren().add(text);
	}
	
	private void SPFromSrcToDest(Vertex source, Vertex dest, StringBuffer sb) {
		if(source != dest) {
			Vertex prev = dest.getVertexBeforeThisVertex();
			SPFromSrcToDest(source, prev, sb);
			sb.append(prev.getNameID() + " -> ");
		}
	}
	
}
