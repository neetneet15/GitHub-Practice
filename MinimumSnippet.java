import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * When you do a web search, the results page shows you a 
 * <a href="http://searchengineland.com/anatomy-of-a-google-snippet-38357">snippet</a> 
 * for each result, showing you search terms in context. For purposes of this project, a
 * snippet is a subsequence of a document that contains all the search terms.
 * 
 * For this project, you will write code that, given a document (a sequence of
 * words) and set of search terms, find the minimal length subsequence in the
 * document that contains all of the search terms.
 * 
 * If there are multiple subsequences that have the same minimal length, you may
 * return any one of them.
 * 
 */
public class MinimumSnippet {

	/**
	 * Compute minimum snippet.
	 * 
	 * Given a document (represented as an List<String>), and a set of terms
	 * (represented as a List<String>), find the shortest subsequence of the
	 * document that contains all of the terms.
	 * 
	 * This constructor should find the minimum snippet, and store information
	 * about the snippet in fields so that the methods can be called to query
	 * information about the snippet. All significant computation should be done
	 * during construction.
	 * 
	 * @param document
	 *            The Document is an Iterable<String>. Do not change the
	 *            document. It is an Iterable, rather than a List, to allow for
	 *            implementations where we scan very large documents that are
	 *            not read entirely into memory. If you have problems figuring
	 *            out how to solve it with the document represented as an
	 *            Iterable, you may cast it to a List<String>; in all but a very
	 *            small number of test cases, in will in fact be a List<String>.
	 * 
	 * @param terms
	 *            The terms you need to look for. The terms will be unique
	 *            (e.g., no term will be repeated), although you do not need to
	 *            check for that. There should always be at least one term and 
	 *            your code should
	 *            throw an IllegalArgumentException if "terms" is
	 *            empty.
	 * 
	 * 
	 */
	
	// final result
	private String finalSnippetResult = "";	
	// holds each possible subsequence while it is being checked
	private String oneSnippetResult = "";
	// default start position. incremented every time iterator moves
	private int startPosition = -1;
	// default end position. incremented every time iterator moves.
	private int endPosition = 0;
	// if current subsequence has all terms that are queried.
	private boolean hasAllTermsNow = true;
	// if any subsequence has all terms that are queried.
	private boolean hasAllTermsEver = false;
	// instance variable for queried terms in order to access during 'getPos'
	List<String> termsArray;
	// Array that holds all possible start positions for matching subsequence
	ArrayList<Integer> posStartPositions;
	// Array that holds all respective end positions for each possible starting
	//	point.
	ArrayList<Integer> endPoints;
	
	public MinimumSnippet(Iterable<String> document, List<String> terms) 
	{	
		// throw exception if "terms" is empty.
		if (terms.isEmpty())
			throw new IllegalArgumentException("'Terms' parameter empty.");
		
		// copy param terms into instance variable.
		this.termsArray = terms;
		
		// Make iterator for document.
		Iterator<String> docIt = document.iterator();

		// find all possible starting positions
		posStartPositions = new ArrayList();
		
		while (docIt.hasNext())
		{
			String currentDocTerm = docIt.next();
			startPosition++;
			
			for (String searchTerm : terms)
			{
				if (currentDocTerm.equals(searchTerm))
				{
					posStartPositions.add(startPosition);
					hasAllTermsNow = true;
					break;
				}
			}
		}
		
		// find end position for each start position.
		endPoints = new ArrayList();
		
		// Cycle through each start position found previously.
		for (Integer i : posStartPositions)
		{
			// reset iterator.
			docIt = document.iterator();
			 
			startPosition = -1;	// reset startPosition variable
			
			// get to each start position from array
			while (startPosition < i)	
			{
				finalSnippetResult = docIt.next();
				startPosition++;
			}
			
			// set endPosition variable to startPosition. common sense: know
			//	that ending point has to be at least at starting point.
			endPosition = startPosition;
			
			// if starting point is at end of document and we have multiple 
			//	query terms, implies no way for subsequence from this start 
			//	point.
			if (!docIt.hasNext() && terms.size() > 1)
			{
				hasAllTermsNow = false;
				break;
			}
			
			// if end position for current subsequence has been found.
			boolean endPosFound = false;
			
			// as long as end position has not been found.
			while (!endPosFound)
			{	
				// check if current subsequence has all queried terms.
				//	if not, break out of this search loop, and move to next
				//		set of conditional statements.
				//	if subsequence does, set associated variables to true.
				for (String searchTerm : terms)
				{
					if (!(finalSnippetResult.contains(searchTerm)))
					{
						hasAllTermsNow = false;
						endPosFound = false;
						break;
					}
					
					else if (finalSnippetResult.contains(searchTerm))
					{
						hasAllTermsNow = true;
						endPosFound = true;
					}
				}
				
				// Specifies that at least one subsequence with all search terms
				//	has been found.
				if (hasAllTermsNow)
				{
					hasAllTermsEver = true;
					
					// add ending position to array containing all end 
					//	positions.
					endPoints.add(endPosition);
				}
				// if not all terms found, and we are not at end of document,
				//	go to next term in document, and add to current result.
				if (!hasAllTermsNow && docIt.hasNext())
				{
					finalSnippetResult = finalSnippetResult + "" + docIt.next();
					endPosition++;
				}
				
				// else if not all terms found and we are at end of document,
				//	move to next start position.
				else if (!hasAllTermsNow && !docIt.hasNext())
				{
					hasAllTermsNow = false;
					break;
				}
			
			}
			
		} 
		
		// set all result Strings to longest possible sequence. next for loop
		//	will shorten and select final result.
		oneSnippetResult = finalSnippetResult = document.toString();
		
		for (int x = 0; x < endPoints.size(); x++)
		{
			// get each pair of respective start and end positions from arrays.
			int currentStartPos = posStartPositions.get(x);
			int currentEndPos = endPoints.get(x);
			
			// difference tells us length. If start and end positions are 
			//	farther apart, no need to do all the checks here.
			if (currentEndPos - currentStartPos < oneSnippetResult.length())
			{
				startPosition = currentStartPos;
				endPosition = currentEndPos;
				
				// reset iterator.
				docIt = document.iterator();
				
				int currentDocPosition = -1;
				// next two while loops will add subsequence corresponding to
				//	current pair of start and end points to the current result 
				//	string.
				while (currentDocPosition < startPosition)
				{
					oneSnippetResult = docIt.next();
					currentDocPosition++;
				}
				
				while (currentDocPosition < endPosition && docIt.hasNext())
				{
					oneSnippetResult = oneSnippetResult + "" + docIt.next();
					currentDocPosition++;
				}
				
				// if the current result string is shorter than what is stored 
				//	in final result string, replace final result string.
				if (oneSnippetResult.length() < finalSnippetResult.length())
					finalSnippetResult = oneSnippetResult;
			}
			
		}
	}

	/**
	 * Returns whether or not all terms were found in the document. If all terms
	 * were not found, then none of the other methods should be called.
	 * 
	 * @return whether all terms were found in the document.
	 */
	public boolean foundAllTerms() 
	{
		return hasAllTermsEver;	// just return associated variable.
	}

	/**
	 * Return the starting position of the snippet
	 * 
	 * @return the index in the document of the first element of the snippet
	 */
	public int getStartingPos() 
	{
		return startPosition;	// just return associated variable.
	}

	/**
	 * Return the ending position of the snippet
	 * 
	 * @return the index in the document of the last element of the snippet
	 */
	public int getEndingPos() 
	{
		return endPosition;		// just return associated variable.
	}

	/**
	 * Return total number of elements contained in the snippet.
	 * 
	 * @return
	 */
	public int getLength() 
	{
		// get and return length of final snippet result string.
		return finalSnippetResult.length();	
	}

	/**
	 * Returns the position of one of the search terms as it appears in the original document
	 * 
	 * @param index index of the term in the original list of terms.  For example, if index
	 * is 0 then the method will return the position (in the document) of the first search term.
	 * If the index is 1, then the method will return the position (in the document) of the
	 * second search term.  Etc.
	 *  
	 * @return position of the term in the document
	 */
	public int getPos(int index) 
	{
		// go to array containing all queried terms, and find the one 
		//	corresponding to index parameter.
		String searchTerm = termsArray.get(index);
		
		// search final result string for just acquired search term, get 
		//	the index within the final result string, and add it to variable
		//	containing start point of subsequence.
		int pos = startPosition + finalSnippetResult.indexOf(searchTerm);
		
		return pos;
	}

}
