
package basics;

import java.util.HashMap;

public class Divide implements Diffable {

	private Diffable left;
	private Diffable right;
	
	public Divide(Diffable left, Diffable right){
		this.left = left;
		this.right = right;
	}
	public Diffable deriv(int depth) {
		Diffable nd =  new Divide(new Subtract(new Multiply(left.deriv(depth+1), right), new Multiply(left, right.deriv(depth+1))),new Multiply(right, right));
		if(depth==0) {
			String past = "";
			while(!past.equals(nd.toString())) { 
				past = nd.toString(); 
				nd = nd.simplify();
			}
		}
		return nd;
	}

	public double eval(HashMap hm) {
		return left.eval(hm) / right.eval(hm);
	}
	public Diffable simplify() {
		left = left.simplify(); right = right.simplify();
		if (left instanceof Constant) {
			if(((Constant) left).getVal()==0) {
				if(!(right instanceof Constant && ((Constant) right).getVal() == 0)) {
					return new Constant(0);
				}
			}
		}
		if (right instanceof Constant) {
			if(((Constant) right).getVal() == 1) {
				return left;
			}
			if(left instanceof Constant) {
				return new Constant(((Constant) left).getVal()/((Constant) right).getVal());
			}
		}
		return new Divide(left, right);
	}
	public String toString() {
		return "(%s/%s)".formatted(left.toString(), right.toString());
	}
	public Diffable getLeft() {
		return left;
	}
	public Diffable getRight() {
		return right;
	}
}
