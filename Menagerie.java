package animalManagement;


/**
 * An IMMUTABLE class that represents a list of animals and a name. 
 * For example, a typical menagerie might be called "Oh My" and might 
 * consist of the list:  1 Lion, 2 Tiger, 1 Panda
 * 
 *  @author Name:	Praneet Puppala
 * Class/Section:	CMSC 131H 0101
 * 			  TA:	Yulu
 * Last Modified:	November 23rd, 2012
 */

public class Menagerie implements Listable {
	/*
	 * STUDENTS:  You may NOT add any other instance variables!
	 */
	private final String name;
	private final SortedListOfImmutables animalList;
	
	/**
	 * Standard constructor.  
	 * To ensure that the Menagerie class remains immutable, this
	 * constructor will make a copy of the list that animalListIn refers to.
	 * (This is necessary because a SortedListOfImmutables is mutable.)
	 * 
	 * @param nameIn desired name for this Menagerie
	 * @param animalListIn desired list of Animal objects for this Menagerie
	 */
	public Menagerie(String nameIn, SortedListOfImmutables animalListIn) 
	{
		this.name = nameIn;
		this.animalList = new SortedListOfImmutables(animalListIn);
	}
	
	/**
	 * Getter for name of Menagerie
	 * 
	 * @return reference to the name of Menagerie
	 */
	public String getName() 
	{
		return this.name;
	}
	
	/**
	 *  Getter for animalList for this menagerie.
	 *  
	 *  @return reference to a copy of the animalList for this menagerie
	 */
	public SortedListOfImmutables getAnimalList() 
	{
		SortedListOfImmutables copy = new 
				SortedListOfImmutables(this.animalList);
		
		return copy;
	}
	
	/**
	 * Returns the wholesale cost of the animals in this menagerie
	 * 
	 * @return wholesale cost of the animals in this menagerie
	 */
	public int getWholesaleCost() 
	{
		return this.animalList.getWholesaleCost();
	}
	
	/**
	 * Returns the retail value of the animals in this menagerie
	 * 
	 * @return retail value of the animals in this menagerie
	 */
	public int getRetailValue() 
	{
		return this.animalList.getRetailValue();
	}
	
	/**
	 * Compares the current object to the parameter and returns true if they
	 * have the same name.
	 * 
	 * @param other Menagerie to be compared to the current object
	 * @return true if the current object and the parameter have the same name, 
	 * false otherwise
	 */
	public boolean equals(Object other) 
	{
		boolean equal = false;
		
		if (other == null)
			return equal;
		
		if (this.getClass() != other.getClass())
			return equal;
			
		if (this.getName().equals(((Menagerie)other).getName()))
			equal = true;
		
		return equal;
	}
	
	/* Do not modify this method or you will fail our tests!
	 */
	public String toString() {
		String retValue = "< ";
		for (int i = 0; i < animalList.getSize(); i++) {
			if (i != 0) {
				retValue += ", ";
			}
			retValue += animalList.get(i);
		}
		retValue += " >";
		return retValue;
	}
}
