package main;
import java.util.HashMap;

import basics.*;
public class Main {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
//		Diffable a = new Exponent(new Multiply(new Constant(4), new Variable('x')), new Constant(4));
//		Diffable a = new Multiply(new Constant(4), new Multiply(new Variable('x'), new Variable('x')));
//		Diffable a = new Exponent(new Variable('x'), new Constant(4));
//		Diffable a = new Log(new Constant(3), new Variable('x'));
		Diffable a = new Log(new Variable('x'), new Constant(2));
//		Diffable a = new Exponent(new Constant(2), new Exponent(new Constant(2), new Variable('x')));
//		Diffable a = new Log(new Constant(Math.E), new Variable('x'));
//		Diffable a = new Multiply(new Constant(4), new Variable('x'));
//		Diffable a = new Exponent()
		HashMap<Character, Double> hm = new HashMap<Character, Double>();
		hm.put('x', 5.0);
		Diffable b = a.deriv(0);
		System.out.println(b);
		Diffable c = b.deriv(0);
		System.out.println(c);
		System.out.println("Ruuntime: "+(System.currentTimeMillis()-start)+" ms");
		}
	
}
