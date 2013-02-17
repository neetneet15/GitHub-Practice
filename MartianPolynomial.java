package p4_student;

/**
 * A general representation of a 2nd degree Martian polynomial of the form:
 * (a*x^2 + b*x + c)
 * 
 * @author
 * CMSC131H Student Name:	Praneet Puppala
 * 					  TA:	Yulu
 *				 Section:	0101
 * 		   Last Modified:	October 31st, 2012
 */

public class MartianPolynomial 
{
	// Declare instance variables. Marked private and final.
	// Type MyDouble, and one for each coefficient.
	private final MyDouble a;
	private final MyDouble b;
	private final MyDouble c;
	
	//---------------------- END INSTANCE VARIABLES -------------------
	
	// 5 Constructors. Marked public. 
	
	// One that takes three parameters representing coefficients a, b, and c.
	public MartianPolynomial(MyDouble aIn, MyDouble bIn, MyDouble cIn)
	{
		this.a = aIn;
		this.b = bIn;
		this.c = cIn;
	}
	
	// One that takes two parameters representing coefficients b and c.
	//	Sets a to zero.
	public MartianPolynomial(MyDouble bIn, MyDouble cIn)
	{
		this.a = MyDouble.zero;
		this.b = bIn;
		this.c = cIn;
	}
	
	// One that takes one parameter representing coefficient c.
	//	Sets a and b to zero.
	public MartianPolynomial(MyDouble cIn)
	{
		this.a = this.b = MyDouble.zero;
		this.c = cIn;
	}
	
	// One that takes no parameters and sets a, b, and c to zero.
	public MartianPolynomial()
	{
		this.a = this.b = this.c = MyDouble.zero;
	}
	
	// A copy constructor.
	public MartianPolynomial(MartianPolynomial polynomialIn)
	{
		this.a = polynomialIn.a;
		this.b = polynomialIn.b;
		this.c = polynomialIn.c;
	}
	
	//--------------------- END CONSTRUCTORS --------------------------
	
	// Instance methods. Marked public.
	
	// Simple getter method that returns value of variable a.
	public MyDouble getA() 
	{
		return a;
	}

	// Simple getter method that returns value of variable b.
	public MyDouble getB() 
	{
		return b;
	}

	// Simple getter method that returns value of variable c.
	public MyDouble getC() 
	{
		return c;
	}
	
	/* 
	 * Evaluate method that evaluates polynomial at a point and returns result.
	 * Expects a parameter of type MyDouble that will be used as value.
	 */
	public MyDouble eval(MyDouble x)
	{
		MyDouble result;		// Final result.
		
		// Compute first term.
		MyDouble result1 = new MyDouble(x.square());	// square input.
		result1 = result1.multiply(a);	// multiply squared input with 'a'.
		
		// Compute second term.
		MyDouble result2 = new MyDouble(x.multiply(b)); // multiply x with 'b'.
		
		result = result1.add(result2);	// add first two computed terms.
		result = result.add(c);		// add 'c' to result.
		
		return result;
	}
	
	/*
	 * Add method that adds two polynomials and returns sum.
	 * Expects polynomial to be added to be passed in.
	 */
	public MartianPolynomial add(MartianPolynomial polyIn)
	{
		MyDouble sumA = a.add(polyIn.getA());	// Add 'a' coefficients.
		MyDouble sumB = b.add(polyIn.getB());	// Add 'b' coefficients.
		MyDouble sumC = c.add(polyIn.getC());	// Add 'c' coefficients.
		
		// Create new MartianPolynomial object with summed coefficients.
		MartianPolynomial sum = new MartianPolynomial(sumA, sumB, sumC);
		return sum;
	}
	
	/* 
	 * Subtract method that subtracts one polynomial from another and returns
	 * 	difference.
	 * Expects polynomial to be subtracted to be passed in.
	 */
	public MartianPolynomial subtract(MartianPolynomial polyIn)
	{
		MyDouble diffA = a.subtract(polyIn.getA());		// Subtract 'a' values.
		MyDouble diffB = b.subtract(polyIn.getB());		// Subtract 'b' values.
		MyDouble diffC = c.subtract(polyIn.getC());		// Subtract 'c' values.
		
		// Create new MartianPolynomial object with differences between
		// 	coefficients.
		MartianPolynomial diff = new MartianPolynomial(diffA, diffB, diffC);
		return diff;
	}
	
	/*
	 * Glorp method that, in a way, multiplies two Martian Polynomials and 
	 * 	returns product.
	 * 	(ax^2 + bx + c) glorp (dx^2 + ex + f) = 
	 * 		afx^4 + (ae+bf)x^3 + (cf + be + ad)x^2 + (ce+bd)x + cd
	 * 
	 * 	However, product should not have third or fourth order terms, or else
	 * 	 method returns null.
	 * 
	 * Expects polynomial that will be used to glorp current polynomial to be
	 * 	passed in.
	 */
	public MartianPolynomial glorp(MartianPolynomial polyIn)
	{
		// Get coefficients of parameter polynomial.
		MyDouble d = polyIn.getA();
		MyDouble e = polyIn.getB();
		MyDouble f = polyIn.getC();
		
		// Compute coefficients.
		MyDouble fourthOrder = a.multiply(f);	// Fourth order coefficient.
		
		MyDouble thirdOrder;		// Third order coefficient.
		MyDouble ae = a.multiply(e);
		MyDouble bf = b.multiply(f);
		thirdOrder = ae.add(bf);
		
		MyDouble secondOrder;	// Second coefficient.
		MyDouble cf = c.multiply(f);
		MyDouble be = b.multiply(e);
		MyDouble ad = a.multiply(d);
		secondOrder = cf.add(be);
		secondOrder = secondOrder.add(ad);
		
		MyDouble firstOrder;	// First order coefficient.
		MyDouble ce = c.multiply(e);
		MyDouble bd = b.multiply(d);
		firstOrder = ce.add(bd);
		
		MyDouble constant;		// Constant.
		constant = c.multiply(d);
		
		// Check if fourth order or third order coefficients are zero.
		//	If they are not, return null.
		if (!(fourthOrder.isZero()) || !(thirdOrder.isZero()))
		{
			return null;
		}
		
		// If coefficients are zero, return the glorp of the polynomials.
		else
		{
			MartianPolynomial glorp;
			glorp = new MartianPolynomial(secondOrder, firstOrder, constant);
			return glorp;
		}
	}
	
	/*
	 * Method computes 'splee' of current object. 'Splee' of Martian Polynomial
	 * 	ax^2 + bx + c is  4acx + bc. The splee is returned in another 
	 * 	Martian Polynomial.
	 * 
	 * Takes no parameters.
	 */
	public MartianPolynomial splee()
	{
		// Compute coefficient of x term of splee.
		MyDouble xCoeff = a.multiply(c);
		// Can't multiply by integer, so to get four times value, add.
		for (int counter = 0; counter <= 1; counter++)
		{
			xCoeff = xCoeff.add(xCoeff);
		}
		
		// Compute constant (bc) of splee.
		MyDouble constant = b.multiply(c);
		
		// Create new MartianPolynomial for splee with computed terms.
		MartianPolynomial splee;
		splee = new MartianPolynomial(xCoeff, constant);
		
		return splee;
	}
	
	/*
	 * Method computes 'cliff' of current object. 'Cliff' of Martian Polynomial
	 * 	ax^2 + bx + c is sqrt((a+b+c)^2). The cliff is returned in a new 
	 * 	MyDouble object.
	 * 
	 * Takes no parameters.
	 */
	public MyDouble cliff()
	{
		// Compute the sum of the coefficients and square it.
		MyDouble sum = a.add(b);
		sum = sum.add(c);
		sum = sum.square();
		
		// Take the square root and return it, which is 'cliff'.
		MyDouble cliff = sum.sqrt();
		return cliff;
	}
	
	/*
	 * CompareTo method compares cliffs of current object and parameter. 
	 * 	Returns int that quantifies comparison. Equal cliffs return 0. If 
	 * 	current cliff is less than parameter cliff, returns -1. If current 
	 * 	cliff is greater than parameter cliff, returns +1.
	 * 
	 * Expects polynomial that will be compared to to be passed in.
	 */
	public int compareTo(MartianPolynomial polyIn)
	{
		MyDouble currentCliff = this.cliff();
		MyDouble paramCliff = polyIn.cliff();
		
		int comparison = currentCliff.compareTo(paramCliff);
		int returnVal = 100;	// initialize to random value.
		
		if (comparison < 0) 
		{
			returnVal = -1;
		}
		
		else if (comparison == 0)
		{
			returnVal = 0;
		}
		
		else if (comparison > 0)
		{
			returnVal = 1;
		}
	
		return returnVal;
	}
	
	/*
	 * toString method returns a string representation of the current
	 * 	MartianPolynomial. Form of string is specified on Project page.
	 * 
	 * Takes no parameters.
	 */
	public String toString()
	{
		// Uses toString method of MyDouble to return strings containing 
		//	coefficients of each term. Also create integers comparing 
		// 	coefficients to MyDouble zero.
		String secondOrderCoeff = a.toString() + "x^2";
		int aComparison = a.compareTo(MyDouble.zero);
		
		String firstOrderCoeff = b.toString() + "x";
		int bComparison = b.compareTo(MyDouble.zero);
		
		String constant = c.toString();
		int constantComparison = c.compareTo(MyDouble.zero);
		
		String polyString;		// string for final output.
			
		/* 
		 * Edit strings based on comparison values.
		 * If all coefficients are zero, return String should be zero.
		 * If comparison values are zero, coefficients are zero, meaning they
		 *	should not be included, so their respective strings will be made
		 *	into empty strings.
		 * Also, if comparison values are positive, coefficients are positive,
		 *	so, respective strings will have plus sign added to beginning.
		 *	This is not done for the secondOrderCoeff string because an 
		 * 	equation does not start with a plus sign.
		 */
		if (aComparison == 0 && bComparison == 0 && constantComparison == 0)
		{
			polyString = "0";
			return polyString;
		}
		
		else 
		{
			if (aComparison == 0)
			{
				secondOrderCoeff = "";
			}
		
			if (bComparison == 0)
			{
				firstOrderCoeff = "";
			}
		
			else if (bComparison > 0) 
			{
				if (!(aComparison == 0))
						firstOrderCoeff = "+" + firstOrderCoeff;
			}
		
			if (constantComparison == 0)
			{
				constant = "";
			}
		
			else if (constantComparison > 0)
			{
				if (!(aComparison == 0))
						constant = "+" + constant;
				else if (!(bComparison == 0))
					constant = "+" + constant;
			}
			
			polyString = secondOrderCoeff + firstOrderCoeff + constant;
			return polyString;
		}
	}

	//---------------------- END INSTANCE METHODS -------------------	
	
	// Static method. Marked public.
	
	/* parseMartianPolynomial method that takes a String input and converts
	 * 	it into new MartianPolynomial object.
	 * 
	 * Expects String to be parsed to be passed in. Expectations for input 
	 * 	Strings are defined on project page.
	 */
	
	public static MartianPolynomial parseMartianPolynomial(String input)
	{
		// Coefficients that will be used to create returned polynomial.
		MyDouble aIn = MyDouble.zero;
		MyDouble bIn = MyDouble.zero;
		MyDouble cIn = MyDouble.zero;
		
		// Declares and initializes polynomial to be returned.
		MartianPolynomial result = null;
		
		// Variable for current position from which to search String.
		int position = 0;
		
		// Create polynomial with all zero coefficients if string is zero,
		//	which means that all coefficients are supposed to be zero.
		if (input.equals("0") || input.equals(""))
		{
			result = new MartianPolynomial();
		}
		
		else
		{
			// Remove all spaces.
			input = input.replaceAll(" ", "");
			
			// Remove plus signs.
			input = input.replace("+", "");
			
			// Replace x^2 part of string with y, for ease of separation later.
			input = input.replace("x^2", "y");
			
			// Check for x^2 (replaced with y) term, and if it exists, 
			//	get coefficient.
			if (input.contains("y"))
			{
				int indexACoeff = input.indexOf("y");	// where 'y' term ends.
				
				// Declare and initialize string for just 'y' term.
				String aCoeff = "";		
				for (int pos = position; pos < indexACoeff; pos++)
				{
					// Add characters from start position through 
					//	index of where 'y' term ends to 'y' term string.
					aCoeff = aCoeff + input.charAt(pos);
				}
				// Parse 'y' term string into double for polynomial.
				aIn = new MyDouble(Double.parseDouble(aCoeff));
				
				position = indexACoeff + 1;	// change start position for future.
			}
			
			// Check for x term, and if it exists, get coefficient.
			// See previous loop for how code works.
			if (input.contains("x"))
			{
				int indexBCoeff = input.indexOf("x");
				String bCoeff = "";
				for (int pos = position; pos < indexBCoeff; pos++)
				{
					bCoeff = bCoeff + input.charAt(pos);
				}
				bIn = new MyDouble(Double.parseDouble(bCoeff));
				
				position = indexBCoeff + 1;
			}
			
			// Check for constant term (not ending in x or y (replaced x^2), 
			// 	and if it exists, get coefficient.
			// See previous loop for how code works.
			if (!(input.endsWith("x")) && (!(input.endsWith("y"))))
			{
				String cCoeff = "";
				for (int pos = position; pos < input.length(); pos++)
				{
					cCoeff = cCoeff + input.charAt(pos);
				}
				cIn = new MyDouble(Double.parseDouble(cCoeff));
			}
		}
		
		result = new MartianPolynomial(aIn, bIn, cIn);
		return result;
	}
	
	//NOTE: THIS IS WRITTEN FOR YOU - DO NOT CHANGE
	public boolean equals (Object other) {
		if (other == null) {
			return false;
		}
		else if (this.getClass()!=other.getClass()) {
			return false;
		}
		else {
			MartianPolynomial casted = (MartianPolynomial)other;
			return (
					a.equals(casted.a) && 
					b.equals(casted.b) && 
					c.equals(casted.c)
			);
		}
	}

	
}