
package basics;

import java.util.HashMap;

public class Constant implements Diffable {

	public double val;
	
	public Constant(double val){
		this.val = val;
	}
	public Diffable deriv(int depth) {
		return new Constant(0);
	}
	public double eval(HashMap hm) {
		return val;
	}
	
	public double getVal() { return val; }
	
	public Diffable simplify() {
		return new Constant(val);
	}

	public String toString() {
		return Double.toString(val);
	}
}
