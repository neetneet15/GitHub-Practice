package animalManagement;

/**
 *  The Pet Store has a name (String), options (list of Menageries), 
 *  an inventory (list of Animal), and an amount of cash on hand, 
 *  measured in pennies (int)
 * 
 *  This class facilitates orders being placed, deliveries being made to the
 *  inventory, and menageries being added to the options.
 *  
 * @author 	Name:	Praneet Puppala
 * Class/Section:	CMSC 131H 0101
 * 			  TA:	Yulu
 * Last Modified:	November 23rd, 2012
 */

public class PetStore {

	/*
	 * STUDENTS:  YOU MAY NOT ADD ANY OTHER INSTANCE VARIABLES TO THIS CLASS!
	 */
	private String name;
	private SortedListOfImmutables options;    // A list of Menagerie objects	
	private SortedListOfImmutables inventory;  // A list of Animal objects
	private int cash;

	/**
	 * Standard constructor.  The options and the inventory are both initialized as 
	 * empty lists.  The name and cash amount are set to match the parameters.
	 * 
	 * @param nameIn name of the pet store
	 * @param startingCash cash amount that the restaurant will have, measured
	 * in pennies
	 */
	public PetStore(String nameIn, int startingCash) 
	{
		this.name = nameIn;
		this.options = new SortedListOfImmutables();
		this.inventory = new SortedListOfImmutables();
		this.cash = startingCash;
	}

	/**
	 * Getter for the name of the pet store.
	 * 
	 * @return reference to the name of the pet store
	 */
	public String getName() 
	{
		return this.name;
	}

	/**
	 * Getter for the options.
	 * 
	 * @return a reference to a copy of the options
	 */
	public SortedListOfImmutables getMenu() 
	{
		// Create a copy of the options list.
		SortedListOfImmutables copyOptions = new 
				SortedListOfImmutables(this.options);
		
		return copyOptions;
	}

	/**
	 * Adds an menagerie to the options.
	 * 
	 * @param menagerieToAdd reference to the menagerie to be added to the options
	 */
	public void addMenagerie(Menagerie menagerieToAdd) 
	{
		// call on the add method from SortedListOfImmutables to accomplish.
		options.add(menagerieToAdd);
	}
	
	/**
	 * Getter for the inventory.
	 * 
	 * @return a reference to a copy of the inventory
	 */
	public SortedListOfImmutables getInventory() 
	{
		// Create a copy of the inventory.
		SortedListOfImmutables copyInventory = new
				SortedListOfImmutables(this.inventory);
		
		return copyInventory;
	}

	/**
	 * Getter for the current amount of cash on hand
	 * 
	 * @return the current amount of cash, measured in pennies
	 */
	public int getCash() 
	{
		return this.cash;
	}

	/**
	 * Checks if the Animal items contained in the specified Menagerie are 
	 * actually contained in the pet store's inventory.
	 * 
	 * @param menagerieIn Menagerie that we are checking against the inventory
	 * @return true if the list of Animal items contained in the Menagerie are
	 * all present in the inventory, false otherwise.
	 */
	public boolean checkIfInInventory(Menagerie menagerieIn) 
	{
		// boolean variable for whether or not menagerie is in inventory.
		boolean exists = false;
		
		if (this.inventory.checkAvailability(menagerieIn.getAnimalList()))
			exists = true;
		
		return exists;
	}

	/**
	 * Adds the specified list of animals to the inventory.  If the 
	 * total wholesale cost of all of the animals combined exceeds 
	 * the amount of cash on hand, then NONE of the animals are added 
	 * to the inventory.  If the amount of cash on hand is sufficient to
	 * pay for the shipment, then the amount of cash on hand is reduced by 
	 * the wholesale cost of the shipment.
	 * 
	 * @param list animals to be added to the inventory
	 * @return true if the animals are added; false if the animals are
	 * not added because their wholesale cost exceeds the current cash
	 * on hand
	 */
	public boolean addShipmentToInventory(SortedListOfImmutables list) 
	{
		// boolean variable for whether or not animals were added.
		boolean added = false;
		
		// don't add animals if wholesale cost exceeds cash on hand, and return
		//	false
		if (list.getWholesaleCost() > this.cash)
			return added;
		
		// if cash is sufficient
		else
		{
			this.inventory.add(list);	// add shipment
			// subtract wholesale cost from cash
			this.cash = cash - list.getWholesaleCost();
			added = true;	// change boolean to true
		}
		
		return added;
	}

	/**
	 * Removes the animals contained in the specified Menagerie from the inventory.
	 * If the inventory does not contain all of the items required for this
	 * Menagerie, then NOTHING is removed from the inventory.  If the inventory contains
	 * all of the required items, then the amount of cash on hand is INCREASED by
	 * the retail value of the Menagerie.
	 *  
	 * @param menagerie Menagerie that has been ordered
	 * @return true if the animals are removed from the inventory; false
	 * if the animals were not removed because one or more required items
	 * were not contained in the inventory
	 */
	public boolean placeOrder(Menagerie menagerie) 
	{
		// boolean variable for whether or not animals are removed from
		//	inventory
		boolean removed = false;
		
		// don't remove any items if inventory does not have all of items
		//	required for this specific menagerie, and return false
		if (!checkIfInInventory(menagerie))
			return removed;
		
		// if all items in inventory
		else
		{
			// remove from inventory
			this.inventory.remove(menagerie.getAnimalList());
			// add retail value of menagerie to cash
			this.cash = cash + menagerie.getRetailValue();
			// change boolean to indicate that items were removed
			removed = true;
		}
		
		return removed;
	}

}
