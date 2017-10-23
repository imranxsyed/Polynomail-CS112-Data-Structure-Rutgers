package poly;

import java.io.*;
import java.util.StringTokenizer;

/**
 * This class implements a term of a polynomial.
 * 
 * @author runb-cs112
 *
 */
class Term {
	/**
	 * Coefficient of term.
	 */
	public float coeff;

	/**
	 * Degree of term.
	 */
	public int degree;

	/**
	 * Initializes an instance with given coefficient and degree.
	 * 
	 * @param coeff
	 *            Coefficient
	 * @param degree
	 *            Degree
	 */
	public Term(float coeff, int degree) {
		this.coeff = coeff;
		this.degree = degree;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		return other != null && other instanceof Term && coeff == ((Term) other).coeff
				&& degree == ((Term) other).degree;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (degree == 0) {
			return coeff + "";
		} else if (degree == 1) {
			return coeff + "x";
		} else {
			return coeff + "x^" + degree;
		}
	}
}

/**
 * This class implements a linked list node that contains a Term instance.
 * 
 * @author runb-cs112
 *
 */
class Node {

	/**
	 * Term instance.
	 */
	Term term;

	/**
	 * Next node in linked list.
	 */
	Node next;

	/**
	 * Initializes this node with a term with given coefficient and degree,
	 * pointing to the given next node.
	 * 
	 * @param coeff
	 *            Coefficient of term
	 * @param degree
	 *            Degree of term
	 * @param next
	 *            Next node
	 */
	public Node(float coeff, int degree, Node next) {
		term = new Term(coeff, degree);
		this.next = next;
	}
}

/**
 * This class implements a polynomial.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {

	/**
	 * Pointer to the front of the linked list that stores the polynomial.
	 */
	Node poly;

	/**
	 * Initializes this polynomial to empty, i.e. there are no terms.
	 *
	 */
	public Polynomial() {
		poly = null;
	}

	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage
	 * format of the polynomial is:
	 * 
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * 
	 * with the guarantee that degrees will be in descending order. For example:
	 * 
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * 
	 * which represents the polynomial:
	 * 
	 * <pre>
	 * 4 * x ^ 5 - 2 * x ^ 3 + 2 * x + 3
	 * </pre>
	 * 
	 * @param br
	 *            BufferedReader from which a polynomial is to be read
	 * @throws IOException
	 *             If there is any input error in reading the polynomial
	 */
	public Polynomial(BufferedReader br) throws IOException {
		String line;
		StringTokenizer tokenizer;
		float coeff;
		int degree;

		poly = null;

		while ((line = br.readLine()) != null) {
			tokenizer = new StringTokenizer(line);
			coeff = Float.parseFloat(tokenizer.nextToken());
			degree = Integer.parseInt(tokenizer.nextToken());
			poly = new Node(coeff, degree, poly);
		}
	}

	/**
	 * Returns the polynomial obtained by adding the given polynomial p to this
	 * polynomial - DOES NOT change this polynomial
	 * 
	 * @param p
	 *            Polynomial to be added
	 * @return A new polynomial which is the sum of this polynomial and p.
	 */
	public Polynomial add(Polynomial p) {
		/** COMPLETE THIS METHOD **/
		Polynomial output = new Polynomial();
		if (poly == null || p.poly == null) {// checking if any of them is null
			if (poly == null) {
				output.poly = p.poly;
				return output;
			} else {
				output.poly = poly;
				return output;

			}
		}
		Node ptr1 = poly;
		Node ptr2 = p.poly;
		Node outputPtr = output.poly;
		while (ptr1 != null) {
			while (ptr2 != null) {
				if (ptr1.term.degree == ptr2.term.degree) {
					if ((ptr1.term.coeff) + (ptr2.term.coeff) != 0) {
						if (output.poly == null) {
							output.poly = new Node((ptr1.term.coeff) + (ptr2.term.coeff), ptr1.term.degree, null);

							outputPtr = output.poly;
							ptr2.term.coeff = 0;
							ptr2.term.degree = 0;
							ptr1 = ptr1.next;
							ptr2 = p.poly;
							break;
						} else {
							outputPtr.next = new Node(ptr1.term.coeff + (ptr2.term.coeff), ptr1.term.degree, null);
							outputPtr = outputPtr.next;
							ptr2.term.coeff = 0;
							ptr2.term.degree = 0;
							ptr1 = ptr1.next;
							ptr2 = p.poly;
							break;
						}
					} else {
						ptr2.term.coeff = 0;
						ptr2.term.degree = 0;
						ptr1 = ptr1.next;
						ptr2 = p.poly;
						break;
					}

				} else {
					ptr2 = ptr2.next;
				}
			}
			if (ptr2 == null) {
				if (output.poly == null) {
					output.poly = new Node((ptr1.term.coeff), ptr1.term.degree, null);

					outputPtr = output.poly;

					ptr1 = ptr1.next;
					ptr2 = p.poly;
				} else {
					outputPtr.next = new Node(ptr1.term.coeff, ptr1.term.degree, null);
					outputPtr = outputPtr.next;

					ptr1 = ptr1.next;
					ptr2 = p.poly;

				}

			}

		}
		outputPtr = output.poly;
		Node outputPrev = null;
		while (ptr2 != null) {
			if (ptr2.term.coeff != 0) {
				if (output.poly.term.degree > ptr2.term.degree) {
					output.poly = new Node(ptr2.term.coeff, ptr2.term.degree, output.poly);
					outputPtr = output.poly;
					ptr2 = ptr2.next;
				} else {
					outputPrev = outputPtr;
					outputPtr = outputPtr.next;
					while (outputPtr != null) {
						if (outputPtr.term.degree > ptr2.term.degree) {
							outputPrev.next = new Node(ptr2.term.coeff, ptr2.term.degree, outputPrev.next);
							ptr2 = ptr2.next;
							outputPtr = output.poly;
							outputPrev = null;
							break;

						} else {
							outputPrev = outputPtr;
							outputPtr = outputPtr.next;
						}

					}
					if (outputPtr == null) {
						outputPrev.next = new Node(ptr2.term.coeff, ptr2.term.degree, outputPrev.next);
						outputPtr = output.poly;
						outputPrev = null;
						ptr2 = ptr2.next;
						/*
						 * outputPtr = output.poly; outputPrev = null; while
						 * (outputPtr !=null){ outputPrev = outputPtr;
						 * outputPtr= outputPtr.next; }if (outputPrev == null){
						 * output.poly = new
						 * Node(ptr2.term.coeff,ptr2.term.degree,null); ptr2=
						 * ptr2.next; outputPtr = output.poly; outputPrev =
						 * null; }else { outputPrev.next = new
						 * Node(ptr2.term.coeff,ptr2.term.degree,
						 * outputPrev.next); outputPtr = output.poly; outputPrev
						 * = null; ptr2= ptr2.next; }
						 */
					}
				}

			} else {
				ptr2 = ptr2.next;
			}
		}
		outputPtr = output.poly;
		outputPrev = null;
		if (outputPtr != null) {
			outputPrev = outputPtr;
			outputPtr = outputPtr.next;
		}
		while (outputPrev != null) {
			while (outputPtr != null) {
				if (outputPrev.term.degree < outputPtr.term.degree) {

					float tempc = outputPrev.term.coeff;
					int tempd = outputPrev.term.degree;
					outputPrev.term.degree = outputPtr.term.degree;
					outputPrev.term.coeff = outputPtr.term.coeff;
					outputPtr.term.coeff = tempc;
					outputPtr.term.degree = tempd;
					outputPtr = outputPtr.next;
				} else {
					outputPtr = outputPtr.next;
				}
			}
			outputPtr = outputPrev;
			outputPrev = outputPrev.next;
		}

		return output;
	}

	/**
	 * Returns the polynomial obtained by multiplying the given polynomial p
	 * with this polynomial - DOES NOT change this polynomial
	 * 
	 * @param p
	 *            Polynomial with which this polynomial is to be multiplied
	 * @return A new polynomial which is the product of this polynomial and p.
	 */
	public Polynomial multiply(Polynomial p) {
		/** COMPLETE THIS METHOD **/
		Polynomial second = new Polynomial();
		Node secondPtr = second.poly;
		Node ptr1 = this.poly;
		Node ptr2 = p.poly;
		Polynomial first = new Polynomial();
		Node firstPtr = first.poly;

		if (poly == null || p.poly == null) {// checking if any of them is null
			if (poly == null) {
				second.poly = p.poly;
				return second;
			}
			second.poly = poly;
			return second;
		}
		while (ptr1 != null) {
			while (ptr2 != null) {
				if (firstPtr == null) {
					first.poly = new Node(ptr1.term.coeff * ptr2.term.coeff, ptr1.term.degree + ptr2.term.degree, null);
					firstPtr = first.poly;
				} else {
					firstPtr.next = new Node(ptr1.term.coeff * ptr2.term.coeff, ptr1.term.degree + ptr2.term.degree,
							null);
					firstPtr = firstPtr.next;
				}
				ptr2 = ptr2.next;

			} // the inner loop ends
			second = first.add(second);
			first.poly = null;
			firstPtr = first.poly;
			ptr2 = p.poly;
			ptr1 = ptr1.next;
		}
		// the outer while loop ends

		return second;
	}

	/**
	 * Evaluates this polynomial at the given value of x
	 * 
	 * @param x
	 *            Value at which this polynomial is to be evaluated
	 * @return Value of this polynomial at x
	 */
	public float evaluate(float x) {
		/** COMPLETE THIS METHOD **/
		if (poly == null) {
			return 0;
		}
		float value = 0;
		Node ptr = poly;
		while (ptr != null) {
			if (ptr.term.degree != 0) {
				value += ptr.term.coeff * Math.pow(x, ptr.term.degree);
			} else {
				value += ptr.term.coeff;
			}
			ptr = ptr.next;
		}
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String retval;

		if (poly == null) {
			return "0";
		} else {
			retval = poly.term.toString();
			for (Node current = poly.next; current != null; current = current.next) {
				retval = current.term.toString() + " + " + retval;
			}
			return retval;
		}
	}
}
