package basics;

import java.util.HashMap;

public interface Diffable {
	public Diffable deriv(int depth);
	public double eval(HashMap hm);
	public Diffable simplify();
	public String toString();
}
