package drzewa;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Node {
	public static List<Integer> path = new ArrayList<Integer>();
	public List<Field> node = new ArrayList<Field>();
	public Ramka r;
	public int level = 0;
	public int number = MyDialog.nodes.size();
	public static int licznik = 2;
	public Node() {
	}
	public void dodajDwa(double x) {
		this.node.add(new Field(x, 0));
		Collections.sort(this.node);
		Node w1 = new Node();
		MyDialog.nodes.add(w1);
		Node w2 = new Node();
		MyDialog.nodes.add(w2);
		w1.level = this.level;
		w2.level = this.level;
		if(this.level == 1) {
			MyDialog.poziom2.add(w1);
			MyDialog.poziom2.add(w2);
		}
		else if(this.level == 0) {
			MyDialog.poziom3.add(w1);
			MyDialog.poziom3.add(w2);
		}
		w1.node.add(new Field(this.node.get(0).getValue(), this.node.get(0).getPointer()));
		w1.node.add(new Field(this.node.get(1).getValue(), this.node.get(1).getPointer()));
		w2.node.add(new Field(this.node.get(2).getValue(), this.node.get(2).getPointer()));
		w2.node.add(new Field(this.node.get(3).getValue(), this.node.get(3).getPointer()));
		w2.node.add(new Field(this.node.get(4).getValue(), this.node.get(4).getPointer()));
		this.node.clear();
		this.node.add(new Field(w1.node.get(0).getValue(), w1.node.get(0).getPointer()));
		this.node.add(new Field(w2.node.get(0).getValue(), w2.node.get(0).getPointer()));
		this.node.get(0).setPointer(w1.number);
		this.node.get(1).setPointer(w2.number);
		this.level++;
	}
	public void dodajJeden(double x) {
		//int szukany = szukaj(this.node.get(0).getValue());
		this.node.add(new Field(x, 0));
		Collections.sort(this.node);
		double temp = this.node.get(2).getValue();
		Node w3 = new Node();
		w3.level = this.level;
		if(this.level == 1) {
			MyDialog.poziom2.add(w3);
		}
		else if(this.level == 0) {
			MyDialog.poziom3.add(w3);
		}
		MyDialog.nodes.add(w3);
		System.out.println(this.node.size());
		w3.node.add(new Field(this.node.get(2).getValue(), this.node.get(2).getPointer()));
		w3.node.add(new Field(this.node.get(3).getValue(), this.node.get(3).getPointer()));
		w3.node.add(new Field(this.node.get(4).getValue(), this.node.get(4).getPointer()));
		for(int i = 0; i < 3; i++) {
			this.node.remove(this.node.size()-1);
		}
		MyDialog.nodes.get(path.get(path.size()-licznik)).addW(temp, w3.number);
		licznik++;
	}
	public static int szukaj(double x) {
		Node z = MyDialog.root;
		path.clear();
		path.add(0);
		int temp = 0;
		for(int i = 0; i < MyDialog.root.level; i++) {
			for(int j = z.node.size(); j >0; j--) {
				if(x >= z.node.get(j-1).getValue()) {
					temp = z.node.get(j-1).getPointer();
					z = MyDialog.nodes.get(temp);
					path.add(temp);
					break;
				}
			}
		}
		return temp;
	}
	public void addN(double x) {
		if(this.node.isEmpty() == true) {
			this.node.add(new Field(x, 0));
		}
		else if(this.node.size() < 4 && this.node.get(0).getPointer() == 0) {
			this.node.add(new Field(x, 0));
			Collections.sort(this.node);
		}
		else if(this.node.get(0).getPointer() != 0) {
			MyDialog.nodes.get(szukaj(x)).addN(x);
		}
		else if(this == MyDialog.root) {
			dodajDwa(x);
		}
		else {
			dodajJeden(x);
			licznik = 2;
		}
	}
	public void addW(double x, int number) {
		if(this.node.size() < 4) {
			this.node.add(new Field(x, number));
			Collections.sort(this.node);
		}
		else if(this == MyDialog.root) {
			dodajDwa(x);
		}
		else {
			dodajJeden(x);
		}
	}
}