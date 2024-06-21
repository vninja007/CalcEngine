
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
			if(right instanceof Divide) {
				return new Divide(new Multiply(left, ((Divide) right).getLeft()),((Divide) right).getRight());
			}
			if(right instanceof Multiply) {
				if(((Multiply) right).getLeft() instanceof Constant) {
					return new Multiply(new Constant(((Constant) left).getVal()*((Constant) ((Multiply) right).getLeft()).getVal()),((Multiply) right).getRight());
				}
			}
		}
		else if (right instanceof Constant) {
			if(((Constant) right).getVal()==0) {return new Constant(0);}
			if(((Constant) right).getVal()==1) {return left;}
			if(left instanceof Constant) { return new Constant(((Constant) left).getVal()*((Constant) right).getVal());}
			if(left instanceof Divide) {
				return new Divide(new Multiply(right, ((Divide) left).getLeft()),((Divide) left).getRight());
			}
			if(left instanceof Multiply) {
				if(((Multiply) left).getLeft() instanceof Constant) {
					return new Multiply(new Constant(((Constant) right).getVal()*((Constant) ((Multiply) left).getLeft()).getVal()),((Multiply) left).getRight());
				}
			}
		}

		if(left.toString().equals(right.toString())) {return new Exponent(left,new Constant(2));}
		return new Multiply(left, right);
	}
	public String toString() {
		return "(%s*%s)".formatted(left.toString(), right.toString());
	}
	public Diffable getLeft() {
		return left;
	}
	public Diffable getRight() {
		return right;
	}
}
