# Polynomials
Polynomial operations using Linked Lists

A polynomial class created for CS112-Data Structures at Rutgers University (Spring 2015).


Polynomials, represented as linked lists, are added, multiplied, and evaluated. Individual nodes contain a coefficient and a degree.

Notes about representation

            
      (a) Terms are stored in ASCENDING order of degrees from front to rear in the a non-circular linked list.
      (b) Zero-coefficient terms are NOT stored.
      (c) An EMPTY (zero) polynomial is represented by a linked list with NO NODES in it, i.e. referenced by NULL.
      (d) Coefficients are floating point numbers
      (e) Degrees are POSITIVE integers, except if there is a constant term, in which case the degree is zero.
      (f) There will not be more than one term in the same degree.
      
      
      

The API is as follows

            public class Polynomial {
             public Polynomial()                       //Reads and stores a polynomial from file
             public Polynomial add(Polynomial p)       //Adds this polynomial to p
             public Polynomial multiply(Polynomial p)  //Multiples this polynomial with p
             public float evaluate(float x)            //Evaluates the polynomial at the given value
             public String toString()                  //Returns the string representation of the polynomial
             }


      
This assignment was completed on February 12, 2015
