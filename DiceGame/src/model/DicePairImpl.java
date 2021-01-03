package model;

import model.interfaces.DicePair;
import model.interfaces.Die;
import java.util.concurrent.ThreadLocalRandom;

public class DicePairImpl implements DicePair {

	private final int numFaces = 6;
	private Die die1;
	private Die die2;

	
	public DicePairImpl(Die die1, Die die2) {
		this.die1 = die1;
		this.die2 = die2;
	}
	
	public DicePairImpl() {
		int randNum1 = ThreadLocalRandom.current().nextInt(1, numFaces+1);
		int randNum2 = ThreadLocalRandom.current().nextInt(1, numFaces+1);
		die1 = new DieImpl(1, randNum1, this.numFaces);
		die2= new DieImpl(2, randNum2, this.numFaces);
	}

	@Override
	public Die getDie1() {
		return this.die1;
	}

	@Override
	public Die getDie2() {
		return this.die2;
	}

	@Override
	public int getTotal() {
		return this.die1.getValue() + this.die2.getValue();
	}

	@Override
	public boolean equals(DicePair dicePair) {
		Die die1_dp = dicePair.getDie1();
		Die die2_dp = dicePair.getDie2();
		if(die1_dp.equals(this.die1) && die2_dp.equals(this.die2)) {
			return true;
		}
		return false;
	}

	@Override
	 public boolean equals(Object dicePair) {
		if(dicePair==null || !(dicePair instanceof DicePair)) {
			return false;
		}
		return this.equals((DicePair)dicePair);
	}
	
	@Override
	public int hashCode() {
		return this.die1.hashCode() + this.die2.hashCode();
	}
	
	@Override
	 public String toString() {
		//OP: Dice 1: Four, Dice 2: Six .. Total: 10
		return this.die1.toString() + ", " + this.die2.toString() + " .. Total: " + this.getTotal();
	}
	
	@Override
	public int compareTo(DicePair dicePair) {
		//if this < that return -1
		if(this.getTotal() < dicePair.getTotal()) {
			return -1;
		}
		else if(this.getTotal() >  dicePair.getTotal()) {
			return 1;
		}
		else {
			return 0;
		}
	}

}
