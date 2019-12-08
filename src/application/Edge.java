package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;

public class Edge {
	private int weight;
	private Vertex start;
	private Vertex end;
	private ObservableList<Node> list;
	
	public Edge(Vertex start, Vertex end, int weight) {
		setStart(start);
		setEnd(end);
		setWeight(weight);
		list = FXCollections.observableArrayList();
	}
	
	public ObservableList<Node> getObsList() {
		return list;
	}
	public void setObsList(Line line, StackPane wgtLabel, StackPane arrow) {
		list.addAll(line, wgtLabel, arrow);
	}
	public Line getLine() {
		return (Line) list.get(0);
	}
	public StackPane getWgtLabel() {
		return (StackPane) list.get(1);
	}
	public StackPane getArrow() {
		return (StackPane) list.get(2);
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public Vertex getStart() {
		return start;
	}
	public void setStart(Vertex start) {
		this.start = start;
	}
	public Vertex getEnd() {
		return end;
	}
	public void setEnd(Vertex end) {
		this.end = end;
	}
}
