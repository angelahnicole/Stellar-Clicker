

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
import java.text.DecimalFormat;

public class BigNumber 
{
   static DecimalFormat numFormat = new DecimalFormat( "###,###,###,###,###,##0.00" );
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
    public static double formatCurrency(double money)
    { 
        double temp = new Double(numFormat.format(money)).doubleValue();
        return temp;
    }
    
    public static String getNumberString(double number)
    {
        // get the index used for the number suffix
        int suffixIndex = (int)Math.log10(number) / 3;
        String numberSuffix = numberSuffixes[suffixIndex];

        // get a truncated version of the number
        number /= Math.pow(10, suffixIndex * 3);
        
        // return the number
        return formatCurrency(number) + " " + numberSuffix;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
}
