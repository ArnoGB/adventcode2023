package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class day15part2 {
	
	public static class Lense {
		String id;
		int value;
		@Override public boolean equals(Object o) {
			return id.equals(((Lense) o).id);
		}
		@Override public String toString() {
			return String.format("[%s %d]",id,value);
		}
	}
	
	public static class Operation {
		Lense lense;
		int hash;
		public enum Type {add, remove}
		Type type;
		public Operation(String description) {
			lense = new Lense();
			if(description.contains("-")) {
				type = Type.remove;
				lense.id = description.split("-")[0];
				hash = hash(lense.id);
				lense.value = 0;
			} else {
				type = Type.add;
				lense.id = description.split("=")[0];
				hash = hash(lense.id);
				lense.value = Integer.valueOf(description.split("=")[1]);
			}
		}
		public void applyTo(Box[] boxes) {
			if(type == Type.add) {
				if(boxes[hash].lenses.contains(lense)) {
					int index = boxes[hash].lenses.indexOf(lense);
					boxes[hash].lenses.set(index,lense);
				} else {
					boxes[hash].lenses.add(lense);
				}
			} else if(type == Type.remove) {
				boxes[hash].lenses.remove(lense);
			}
		}
	}

	public static class Box {
		ArrayList<Lense> lenses;
		public Box() {lenses = new ArrayList<Lense>();}
		@Override
		public String toString() {
			String s = "";
			for(Lense l : lenses)
				s += l.toString();
			return s;
		}
	}
	
	public static void main(String[] args) {
		File f = new File("./ressources/day15.txt");
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line = reader.readLine();
			String[] values = line.split(",");
			ArrayList<Operation> operations = new ArrayList<day15part2.Operation>();

			for(String s : values) {
				System.out.println(s);
				operations.add(new Operation(s));
			}
			
			Box[] boxes = new Box[256];
			for(int i = 0; i < 256; i++) {
				boxes[i] = new Box();
			}
			for(Operation o : operations) {
				o.applyTo(boxes);
			}
			

			int value = 0;
			for(int i = 0; i < 256; i++) {
				System.out.println(i+":"+boxes[i]);
			}
			for(int i = 0; i < 256; i++) {
				for(Lense l : boxes[i].lenses) {
					int lValue = ((i+1) * (boxes[i].lenses.indexOf(l)+1) * l.value);
					value += lValue;
					System.out.println(l+" is "+lValue);
				}
			}
			

			
			System.out.println("Final value is "+value);
			reader.close();
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}

	private static int hash(String s) {
		int v = 0;
		for(char c : s.toCharArray()) {
			v += (int) c;
			v = (v*17)%256;
		}
		return v;
	}

}
