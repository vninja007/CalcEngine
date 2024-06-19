
package basics;

import java.util.HashMap;

public class Multiply implements Diffable {

	private Diffable left;
	private Diffable right;
	
	public Multiply(Diffable left, Diffable right){
		this.left = left;
		this.right = right;
	}
	public Diffable deriv(int depth) {
		Diffable nd =  new Add(new Multiply(left.deriv(depth+1), right), new Multiply(left, right.deriv(depth+1)));
		if(depth==0) {
			Diffable past = new Constant(0);
			while(!past.toString().equals(nd.toString())) { 
				past = nd; nd = nd.simplify();
			}
		}
		return nd;
	}
	public double eval(HashMap hm) {
		return left.eval(hm) * right.eval(hm);
	}
	public Diffable simplify() {
		left = left.simplify(); right = right.simplify();
		if(left instanceof Constant) {
			if(((Constant) left).getVal()==0) {return new Constant(0);}
			if(((Constant) left).getVal()==1) {return right;}
			if(right instanceof Constant) { return new Constant(((Constant) left).getVal()*((Constant) right).getVal());}
		}
		else if (right instanceof Constant) {
			if(((Constant) right).getVal()==0) {return new Constant(0);}
			if(((Constant) right).getVal()==1) {return left;}
		}
		return new Multiply(left, right);
	}
	public String toString() {
		return "(%s*%s)".formatted(left.toString(), right.toString());
	}
}
