
package basics;

import java.util.HashMap;

public class Variable implements Diffable {

	private char var;
	
	public Variable(char var){
		this.var = var;
	}
	public Diffable deriv(int depth) {
		return new Constant(1);
	}
	
	public double eval(HashMap hm) {
		return (double) hm.get(var);
	}
	public Diffable simplify() {
		return new Variable(var);
	}
	public String toString() {
		return ""+var;
	}
}
