

package stellarclicker.util;


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



/**========================================================================================================================== 
 * @file BigNumber.java
 * --------------------------------------------------------------------------------------------------------------------------
 * @author Angela Gross, Matthew Dolan, Alex Dunn
 * --------------------------------------------------------------------------------------------------------------------------
 * @description This is the custom Big Number type that we define to store and do operations on large numbers without 
 * actually storing a gigantic number in memory.
 *///========================================================================================================================

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class BigNumber 
{
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private static String[] numberSuffixes =
    {
      "",
      "K",
      "Million",
      "Billion",
      "Trillion",
      "Quadrillion",
      "Quintillion",
      "Sextillion",
      "Septillion",
      "Octillion",
      "Nonillion",
      "Decillion",
      "Undecillion",
      "Duodecillion",	
      "Tredecillion",	
      "Quattuordecillion",
      "Quindecillion",	
      "Sexdecillion", 
      "Septendecillion",	
      "Octodecillion",	
      "Novemdecillion", 
      "Vigintillion",	
      "Centillion"	

    };
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    
    public static String getNumberString(double number)
    {
        // get the index used for the number suffix
        int suffixIndex = (int)Math.log(number) / 3;
        String numberSuffix = numberSuffixes[suffixIndex];

        // get a truncated version of the number
        number /= Math.pow(10, suffixIndex * 3);
        
        // return the number
        return number + " " + numberSuffix;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
}
