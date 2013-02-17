package p3_student;

import photo.Photograph;
import photo.Pixel;

/**
 * This class will be written by the Student.  It provides various
 * static methods that take a photograph and produce a copy of it with
 * various modifications.
 * 
 * See the project description for details of method implementations.
 * 
 * @author CMSC 131H Student: 	Praneet Puppala
 * 					 TA Name:	Yulu
 * 					 Section:	0101
 * 				   Last Work:	October 17th, 2012 
 *
 */
public class PhotoTools {


	// Declare and Initialize variable for black pixel. Used in a couple methods.
			static Pixel black = new Pixel (0, 0, 0);
			
	/**
	 * 	This 'copyPixels' method will copy pixels from photograph that is passed
	 * 		in into a new picture of the same size.
	 * 
	 * 	Expects the photograph from which the pixels will be copied to be passed in.
	 * 
	 * 	This is its own method because certain methods below require a copy of some sort.
	 */
			
	private static Photograph copyPixels(Photograph photo)
	{
		// Make a new blank photo named 'copy' with same dimensions as photo that is passed in.
		Photograph newCopy = new Photograph(photo.getWidth(), photo.getHeight());
			
		// Copy each pixel from photo passed in to new photo ('copy').
		Pixel original;		// Declare Pixel variable that will contain info related to pixel being worked on.
			
		for (int xcoor = 0; xcoor < photo.getWidth(); xcoor++)
		{
			for (int ycoor = 0; ycoor < photo.getHeight(); ycoor++)
			{
				original = photo.getPixel(xcoor, ycoor);		// Get pixel from photo passed in.
				newCopy.setPixel(xcoor, ycoor, original);		// Set pixel of new photo ('copy').
			}
		}
			
		return newCopy;	// return copied photograph.
	}
	
	/** 
	*   This 'copy' method will return a new Photograph that is an exact copy
	*		of the photograph passed in.
	*
	*	Expects the photograph to be copied to be passed in.
	*/
	public static Photograph copy(Photograph photo) 
	{
		Photograph copy = copyPixels(photo);
		return copy;
	}

	/**
	 * 	This 'makeBlackAndWhite' method will return a new Photograph that is a
	 * 		black and white copy of the photograph passed in.
	 * 
	 * 	Expects the photograph which is to be made into black and white to be passed in.
	 */
	public static Photograph makeBlackAndWhite(Photograph photo) 
	{
		// Make a new blank photo named 'blackAndWhite' with same dimensions as photo that is passed in.
		Photograph blackAndWhite = new Photograph(photo.getWidth(), photo.getHeight());
		
		// Declare RGB values, and other variables for nested loop to follow.
		Pixel original; 		// Contains info of pixel from original image currently being used.
		Pixel newPixel;			// Contains info of pixel in new image.
		int red, green, blue;	// Each of RGB values of current pixel.
		int average;			// Average of RGB values.
		
		// Get each pixel's info, find the average of the RGB values, and set new 
		// 		picture's equal pixel to average.
		for (int xcoor = 0; xcoor < photo.getWidth(); xcoor++)
		{
			for (int ycoor = 0; ycoor < photo.getHeight(); ycoor++)
			{
				// Get pixel info.
				original = photo.getPixel(xcoor, ycoor);
				red = original.getRed();
				green = original.getGreen();
				blue = original.getBlue();
				
				average = (red + green + blue)/3;		// find average and round down, if necessary.
				
				newPixel = new Pixel(average, average, average);
				blackAndWhite.setPixel(xcoor, ycoor, newPixel);
			}
		}
		return blackAndWhite;	// return black and white photograph.
	}

	/**
	 * 	This 'striped' method will return a new Photograph that has black stripes
	 * 		10 columns wide, evenly spaced with 10 original colored columns in between.
	 * 
	 * 	Expects the Photograph which is to be striped to be passed in.
	 */
	public static Photograph striped(Photograph photo) 
	{
		// Make a new blank photo named 'striped' with the same dimensions as photo that is passed in.
		Photograph striped = new Photograph(photo.getWidth(), photo.getHeight());
		
		// Calls on copyPixels method to get copy of Photograph that is passed in.
		striped = copyPixels(photo);
		
		// Nested loop to color every other 10 columns black, starting with column 10.
		for (int counter = 10; counter < photo.getWidth(); counter+= 20)
		{
			for (int xcoor = counter; xcoor < counter + 10; xcoor++)
			{
				for (int ycoor = 0; ycoor < photo.getHeight(); ycoor++)
				{
					striped.setPixel(xcoor, ycoor, black);
				}
			}
		}
		
		return striped;		// return striped photograph.
	}
	
	/**
	 *	This 'isolateColor' method will return a new Photograph that makes a copy of a photograph,
	 *		but only keeps one of the primary colors visible.
	 *
	 *	Expects the Photograph for which the color isolation will occur, and an integer describing
	 *		which color to isolate (0 for red, 1 for green, and 2 for blue).
	 */
	public static Photograph isolateColor(Photograph photo, int type) 
	{
		// Make a new photograph named 'isolateColor' with same dimensions as photograph that is passed in.
		Photograph isolateColor = new Photograph(photo.getWidth(), photo.getHeight());
		
		// Declare variables.
		Pixel original;		// Contains info of pixel currently being used from original picture.
		Pixel newPixel = new Pixel (0, 0, 0);		// Contains info of pixel currently being used in new picture.
		
		// Depending on input, copy pixels from original photo and keep red, green, or blue.
		for (int xcoor = 0; xcoor < photo.getWidth(); xcoor++)
		{
			for (int ycoor = 0; ycoor < photo.getHeight(); ycoor++)
			{
				original = photo.getPixel(xcoor, ycoor);
				
				if (type == 0)	// if type is 0 and red isolation is desired.
				{
					newPixel = new Pixel (original.getRed(), 0, 0);
				}
				
				else if (type == 1)	// if type is 1 and green isolation is desired.
				{
					newPixel = new Pixel (0, original.getGreen(), 0);
				}
				
				else if (type == 2)	// if type is 2 and blue isolation is desired.
				{
					newPixel = new Pixel (0, 0, original.getBlue());
				}
				
				isolateColor.setPixel(xcoor, ycoor, newPixel);
			}
		}
		
		return isolateColor;
	}

	/**
	 *	This 'stretched' method will return a photograph that makes a copy of a photograph, 
	 *		but stretches it either horizontally or vertically.
	 *
	 * 	Expects the Photograph for which the stretch will occur, and an integer describing
	 *		which way to stretch (0 for horizontal or 1 for vertical).
	 */
	public static Photograph stretched(Photograph photo, int type) 
	{
		// Creates new photograph named 'stretched' with dimensions 1,1 that will be changed later.
		Photograph stretched = new Photograph(1,1);
		
		// Declare variables.
		Pixel original;
		
		// If input is 0 and wants horizontal stretch.
		if (type == 0)	
		{
			stretched = new Photograph(2 * photo.getWidth(), photo.getHeight());
			
			for (int xcoor = 0; xcoor < stretched.getWidth(); xcoor++)
			{
				for (int ycoor = 0; ycoor < stretched.getHeight(); ycoor++)
				{
					// Uses stretched dimensions, but uses half of x-coordinate, which is automatically
					// 		rounded down, to get pixel info from original photo. 
					original = photo.getPixel(xcoor/2, ycoor);
					stretched.setPixel(xcoor, ycoor, original);
				}
			}
			return stretched;
		}
				
		else if (type == 1)		// If input is 1 and wants vertical stretch.
		{
			stretched = new Photograph(photo.getWidth(), 2 * photo.getHeight());
			
			for (int xcoor = 0; xcoor < stretched.getWidth(); xcoor++)
			{
				for (int ycoor = 0; ycoor < stretched.getHeight(); ycoor++)
				{
					// Uses stretched dimensions, but uses half of y-coordinate, which is automatically
					// 		rounded down, to get pixel info from original photo. 
					original = photo.getPixel(xcoor, ycoor/2);
					stretched.setPixel(xcoor, ycoor, original);
				}
			}
			return stretched;
		}
		return stretched;
	}

	/**
	 * 	This 'enlargement' method will return a photograph that is twice as long and twice as wide
	 * 		as photograph that is passed in.
	 * 
	 *  Expects photograph to be enlarged to be passed in.
	 */
	public static Photograph enlargement(Photograph photo) 
	{
		// Makes new photo called 'enlargement' with twice the dimensions of photo passed in.
		Photograph enlargement = new Photograph(2 * photo.getWidth(), 2* photo.getHeight());
		
		// Use stretched method with type 0 and original photo to stretch horizontally.
		//	Stores into enlargement photo object.
		enlargement = stretched(photo, 0);
		
		/* 
		* 	Use stretched method with type 1 and horizontally stretched photo stored in enlargement
		*		object to stretch vertically.
		* 	Stretches what is in enlargement (horizontally stretched photo) vertically to create
		*		a horizontally AND vertically stretched photo, or the enlargement we are looking for.
		*/
		enlargement = stretched(enlargement, 1);
		
		return enlargement;
	}
	
	/**
	 * 	This 'rotated' method will return a photograph that is the same as the photograph passed in,
	 * 		but rotated 90 degrees clockwise.
	 * 
	 * 	Expects photograph to be rotated to be passed in.
	 */
	public static Photograph rotated(Photograph photo) 
	{
		// Create a new Photograph named stretched with the original photo's height as width
		//	and the original photo's width as height. Results in rotated frame.
		Photograph rotated = new Photograph(photo.getHeight(), photo.getWidth());
		
		// Declare variable to be used in nested loop.
		Pixel original;
		
		/* 	Get original Photograph's pixels and put them in new spot rotated 90 degrees clockwise.
		 * 	Get pixel from (original.width, 0) of original photo and place at 
		 * 		(rotated.width, rotated.height). Work down rows of original photo, placing them
		 * 		leftwards through columns of rotated photo.
		 * 	Then, work leftwards through columns of original photo while working up rows of rotated
		 * 		photo.
		 */
		for (int ycoor = photo.getWidth() - 1; ycoor >= 0; ycoor--)
		{
			for (int xcoor = 0; xcoor < photo.getHeight(); xcoor++)
			{
				original = photo.getPixel(ycoor, xcoor);
				rotated.setPixel(rotated.getWidth() - (xcoor+1), ycoor, original);
			}
		}
	
		return rotated;		// return stretched photograph.
	}
	
	/**
	 * 	This 'upsideDown' method will return a photograph that is an upside down version of the
	 * 		photo that is passed in.
	 * 
	 * 	Expects photograph to be flipped upside down to be passed in.
	 */
	public static Photograph upsideDown(Photograph photo) 
	{
		// Use rotated method twice. For second run, pass in result 
		Photograph halfRotation = rotated(photo);	
		Photograph upsideDown = rotated(halfRotation);
		
		return upsideDown;		
	}
	
	/**  
	 * 	This 'weirdCombo' method returns a Photograph with an upside down version of the photo passed
	 * 		in and a 90 degree clockwise rotated version of the passed in photo placed next to each 
	 * 		other.
	 * 
	 * 	Expects photograph for which this weird combination will be made to be passed in.
	 */
	public static Photograph weirdCombo(Photograph photo) 
	{
		// Don't use 'upsideDown' method because that method will not give you half rotated version
		// 	(it gets lost). Call rotated twice, and you will have both the modified photos you need.
		Photograph halfRotated = rotated(photo);
		Photograph upsideDown = rotated(halfRotated);
		
		// Determine height of weirdCombo by checking whether the height or width of original photo
		//	is larger.
		int weirdComboHeight;		// int for height of weirdCombo.
		
		if (photo.getWidth() > photo.getHeight())
		{
			weirdComboHeight = photo.getWidth();
		}
		
		else
		{
			weirdComboHeight = photo.getHeight();
		}
		
		// Determine width of weirdCombo by adding height and width of original photo.
		int weirdComboWidth = photo.getHeight() + photo.getWidth();		
		
		// Make new Photograph named weirdCombo with dimensions set before.
		Photograph weirdCombo = new Photograph(weirdComboWidth, weirdComboHeight);
	
		// Color all pixels black. Will be covered over, and those not used will remain black.
		for (int xcoor = 0; xcoor < weirdCombo.getWidth(); xcoor++)
		{
			for (int ycoor = 0; ycoor < weirdCombo.getHeight(); ycoor++)
			{
				weirdCombo.setPixel(xcoor, ycoor, black);
			}
		}

		// Add upside down image to left side of weirdCombo photograph.
		for (int xcoor = 0; xcoor < upsideDown.getWidth(); xcoor++)
		{
			for (int ycoor = 0; ycoor < upsideDown.getHeight(); ycoor++)
			{
				Pixel upsideDownPixel = upsideDown.getPixel(xcoor, ycoor);
				weirdCombo.setPixel(xcoor, ycoor, upsideDownPixel);
			}
		}
		
		// variable for x coordinate of weirdCombo.
		int weirdComboXCoor;
		
		// Add rotated image next to the upsideDown Photograph.
		for (int xcoor = 0; xcoor < halfRotated.getWidth(); xcoor++)
		{
			for (int ycoor = 0; ycoor < halfRotated.getHeight(); ycoor++)
			{
				Pixel halfRotatedPixel = halfRotated.getPixel(xcoor, ycoor);
				weirdComboXCoor = upsideDown.getWidth() + xcoor;	// moves pixel to next to rotated photo.
				weirdCombo.setPixel(weirdComboXCoor, ycoor, halfRotatedPixel);
			}
		}
		
		return weirdCombo;
	}

}
	