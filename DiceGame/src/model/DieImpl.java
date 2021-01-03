package model;

import model.interfaces.Die;

public class DieImpl implements Die {
	
	private int dieNum = 0;
	private int dieValue = 0;
	private int numFaces = NUM_FACES;
	
	public DieImpl(int number, int value, int numFaces) throws IllegalArgumentException{
		if(number < 1 || number > 2) {
			throw new IllegalArgumentException("number should between 1 and 2");
		}
		if(numFaces < 1) {
			throw new IllegalArgumentException("numFaces should be at least greater than 1");
		}
		if(value < 1 || value > numFaces) {
			throw new IllegalArgumentException("values should between 1 and NumFaces");
		}
		
		this.dieNum =  number;
		this.dieValue = value;
		this.numFaces = numFaces;
	}

	@Override
	public int getNumber() {
		return this.dieNum;
	}

	@Override
	public int getValue() {
		return this.dieValue;
	}

	@Override
	public int getNumFaces() {
		return this.numFaces;
	}

	@Override
	public boolean equals(Die die) {
		if(die==null) {
			return false;
		}else {
			return (die.getNumFaces()==this.numFaces && die.getValue()==this.dieValue);
		}
	}
	
	@Override
	public boolean equals(Object die) {
		if(die==null || !(die instanceof Die)) {
			return false;
		}else {
			return this.equals((Die)die);
		}
	}
	
	@Override
	public int hashCode() {
		return Integer.valueOf(dieValue).hashCode() + Integer.valueOf(numFaces).hashCode();
	}
	
	@Override
	public String toString() {
		
		String[] intToStr = {"One", "Two", "Three", "Four", "Five", "Six", 
                "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve",
                "Thirteen", "Fourteen", "Fifteen"};
		
		return "Dice " + this.dieNum + ": " + intToStr[this.dieValue-1];
	}

}
