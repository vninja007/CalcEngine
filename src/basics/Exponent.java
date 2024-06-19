
package basics;

import java.util.HashMap;

public class Exponent implements Diffable {

	private Diffable base;
	private Diffable exp;
	
	public Exponent(Diffable base, Diffable exp){
		this.base = base;
		this.exp = exp;
	}
	public Diffable deriv(int depth) {
		exp = exp.simplify();
		Diffable nd;
		if(exp instanceof Constant) {
			Diffable newexp = (new Subtract(exp, new Constant(1))).simplify();
			nd =  new Multiply(exp, new Multiply(base.deriv(depth),  new Exponent(base, newexp)));
		}
		else {
			nd =  new Multiply(new Exponent(base, exp), 
					(new Multiply(new Log(new Constant(Math.E), base), exp)).deriv(depth+1)
			);
		}
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
		return Math.pow(base.eval(hm), exp.eval(hm));
	}
	
	public Diffable simplify() {
		base = base.simplify(); exp = exp.simplify();
		if(base instanceof Constant) {
			if(exp instanceof Constant) {
				return new Constant(Math.pow(((Constant) base).getVal(), ((Constant) exp).getVal()));
			}
			if(((Constant) base).getVal() == 0) {
				return new Constant(0);
			}
			if(((Constant) base).getVal() == 1) {
				return new Constant(1);
			}	
		}
		else if(exp instanceof Constant) {
			if(((Constant) exp).getVal() == 0) {
				return new Constant(1);
			}
			if(((Constant) exp).getVal() == 1) {
				return base;
			}
		}
		return new Exponent(base, exp);
	
	}
	public Diffable getExp() { return exp;}
	public Diffable getBase() { return base;}
	public String toString() {
		return "(%s^%s)".formatted(base.toString(), exp.toString());
	}
	
}
