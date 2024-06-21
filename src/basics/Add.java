
package basics;

import java.util.HashMap;

public class Add implements Diffable {

	private Diffable left;
	private Diffable right;
	
	public Add(Diffable left, Diffable right){
		this.left = left;
		this.right = right;
	}
	public Diffable deriv(int depth) {
		Diffable nd =  new Add(left.deriv(depth+1), right.deriv(depth+1));
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
		return left.eval(hm) + right.eval(hm);
	}
	
	public Diffable simplify() {
		left = left.simplify(); right = right.simplify();
		if(left instanceof Constant) {
			if(((Constant) left).getVal()==0) {return right;}
			if(right instanceof Constant) { return new Constant(((Constant) left).getVal()+((Constant) right).getVal());}
		}
		else if(right instanceof Constant) {
			if(((Constant) right).getVal()==0) {return left;}
		}
		if(left.toString().equals(right.toString())) {return new Multiply(new Constant(2), left);}
		return new Add(left, right);
		
		
	}
	public String toString() {
		return "(%s+%s)".formatted(left.toString(), right.toString());
	}

}
