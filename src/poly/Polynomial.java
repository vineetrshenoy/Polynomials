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
	 * @param coeff Coefficient
	 * @param degree Degree
	 */
	public Term(float coeff, int degree) {
		this.coeff = coeff;
		this.degree = degree;
	}
	
	private Term getSmaller(Term other){
		if(this.degree<other.degree)
			return this;
		return other;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		return other != null &&
		other instanceof Term &&
		coeff == ((Term)other).coeff &&
		degree == ((Term)other).degree;
	}
	
	/* (non-Javadoc)
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
	 * @param coeff Coefficient of term
	 * @param degree Degree of term
	 * @param next Next node
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
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param br BufferedReader from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
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
	
	private Polynomial insertAtEnd(Polynomial p,float coeff, int degree){
		if(p.poly==null){
			p.poly=new Node(coeff, degree,null);
			return p;
		}
		Node temp=p.poly;
		while(temp.next!=null)
			temp=temp.next;
		temp.next=new Node(coeff, degree,null);
		return p;
				
	}
	
	//Created by Vineet Shenoy
	
	/**
	 * Returns the polynomial obtained by adding the given polynomial p
	 * to this polynomial - DOES NOT change this polynomial
	 * 
	 * @param p Polynomial to be added
	 * @return A new polynomial which is the sum of this polynomial and p.
	 */
	public Polynomial add(Polynomial p) {
		/** COMPLETE THIS METHOD **/
		
		// Adds two polynomials
		Node temp1=poly;
		Node temp2=p.poly;
		
		Polynomial newPoly =new Polynomial();
		
		while(temp1!=null && temp2!=null){
			if(temp1.term.degree==temp2.term.degree){
				float sum=temp1.term.coeff+temp2.term.coeff;
				if(sum!=0)
					newPoly=this.insertAtEnd(newPoly, sum, temp1.term.degree);
				temp1=temp1.next;
				temp2=temp2.next;
				continue;
			}
			if(temp1.term.degree<temp2.term.degree){
				newPoly=this.insertAtEnd(newPoly, temp1.term.coeff, temp1.term.degree);
				temp1=temp1.next;
			}
			else{
				newPoly=this.insertAtEnd(newPoly, temp2.term.coeff, temp2.term.degree);
				temp2=temp2.next;
			}
		}
		if(temp1==null && temp2!=null){
			while(temp2!=null){
				newPoly=this.insertAtEnd(newPoly, temp2.term.coeff, temp2.term.degree);
				temp2=temp2.next;
			}
		}
		else if (temp1!=null && temp2==null){
			while(temp1!=null){
				newPoly=this.insertAtEnd(newPoly, temp1.term.coeff, temp1.term.degree);
				temp1=temp1.next;
			}
		}
			
		
		return newPoly;
	}
	
	/**
	 * Returns the polynomial obtained by multiplying the given polynomial p
	 * with this polynomial - DOES NOT change this polynomial
	 * 
	 * @param p Polynomial with which this polynomial is to be multiplied
	 * @return A new polynomial which is the product of this polynomial and p.
	 */
	public Polynomial multiply(Polynomial p) {
		/** COMPLETE THIS METHOD **/
		Polynomial retPoly=new Polynomial();
		if(this.poly==null  ||p.poly== null)
			return retPoly;
		
		Node polyHolder=this.poly;
		Node tempNode1=this.poly;
		this.poly=retPoly.poly;
		while (tempNode1!=null){
			Node tempNode2=p.poly;
			Polynomial tempPoly=new Polynomial();
			while(tempNode2!=null){
				float coeff=tempNode1.term.coeff*tempNode2.term.coeff;
				int degree=tempNode1.term.degree+tempNode2.term.degree;
				if(coeff!=0)
					tempPoly=this.insertAtEnd(tempPoly, coeff, degree);
				tempNode2=tempNode2.next;
				
			}
			retPoly=this.add(tempPoly);
			this.poly=retPoly.poly;
			tempNode1=tempNode1.next;
		}
		this.poly=polyHolder;
		return retPoly;
			
	}
	
	/**
	 * Evaluates this polynomial at the given value of x
	 * 
	 * @param x Value at which this polynomial is to be evaluated
	 * @return Value of this polynomial at x
	 */
	public float evaluate(float x) {
		/** COMPLETE THIS METHOD **/
		if(this.poly==null)
			return 0;
		
		float value=0;
		Node temp=this.poly;
		while (temp!=null){
			value+=temp.term.coeff*Math.pow(x, temp.term.degree);
			temp=temp.next;
		}
		return value;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String retval;
		
		if (poly == null) {
			return "0";
		} else {
			retval = poly.term.toString();
			for (Node current = poly.next ;
			current != null ;
			current = current.next) {
				retval = current.term.toString() + " + " + retval;
			}
			return retval;
		}
	}
}
