
package basics;

import java.util.HashMap;

public class Log implements Diffable {

	private Diffable base;
	private Diffable val;
	
	public Log(Diffable base, Diffable val){
		this.base = base;
		this.val = val;
	}
	public Diffable deriv(int depth) {		
//		return new Divide(new Multiply(val.deriv(), base), new Multiply(val,base.deriv()));
//		return new Divide(val.deriv(depth+1), new Multiply(val, new Constant(Math.log(base.eval(null)))));
		if(val instanceof Constant && base instanceof Constant) {return new Constant(0);}
		else { 
			Diffable nd;
			if(base instanceof Constant) {
				 nd =  new Divide(val.deriv(depth+1), new Multiply(val, new Constant(Math.log(((Constant) base).getVal()))));
			}
			else {
				 nd = new Divide(new Log(new Constant(Math.E),val),new Log(new Constant(Math.E),base)).deriv(depth+1);	
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
		
	}
	

	public double eval(HashMap hm) {
		return Math.log(val.eval(hm))/Math.log(base.eval(hm));
	}
	
	public Diffable simplify() {
		base = base.simplify(); val = val.simplify();
		if(base instanceof Constant) {
			if(val instanceof Constant) { return new Constant(Math.log(((Constant) val).getVal())/Math.log(((Constant) base).getVal()));}
		}
		if(val instanceof Exponent) {
			return new Multiply(((Exponent) val).getExp(), new Log(base, ((Exponent) val).getBase()));
		}
		else if(val instanceof Constant) {
			if(((Constant) val).getVal() == 1) {
				return new Constant(0);
			}
		}
		return new Log(base, val);
		
	}
	public String toString() {
		if(base instanceof Constant && ((Constant) base).getVal() == Math.E) {
			return "ln(%s)".formatted(val.toString());
		}
		return "(log_%s(%s)".formatted(base.toString(), val.toString());
	}
	
}
