
package stellarclicker.util;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**========================================================================================================================== 
 * @file BigNumber.java
 * --------------------------------------------------------------------------------------------------------------------------
 * @author Angela Gross, Matthew Dolan, Alex Dunn
 * --------------------------------------------------------------------------------------------------------------------------
 * @description Class that helps format large numbers
 * --------------------------------------------------------------------------------------------------------------------------
 * JME LICENSE
    ******************************************************************************
    Copyright (c) 2003-2016 jMonkeyEngine
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions are
    met:

    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.

    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.

    * Neither the NAME of 'jMonkeyEngine' nor the names of its contributors
      may be used to endorse or promote products derived from this software
      without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
    TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
    PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
    CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
    PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *///========================================================================================================================

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import java.text.DecimalFormat;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class BigNumber 
{
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // ATTRIBUTES
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    static DecimalFormat numFormat = new DecimalFormat( "###,###,###,###,###,##0.00" );

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
    
    /**========================================================================================================================== 
    *  FORMAT CURRENCY
    * 
    * @param money The numerical representation of the number you want to format
    * 
    * @return double A formatted currency version of the double value
    *///=========================================================================================================================
    public static double formatCurrency(double money)
    { 
        double temp = new Double(numFormat.format(money)).doubleValue();
        
        return temp;
    }
    
    /**========================================================================================================================== 
    *  GET NUMBER STRING
    * 
    * @param number The numerical representation of the number you want to format
    * 
    * @return String A formatted currency version that is truncated and suffixed with the correct numerical ending (i.e. 100 
    *  million instead of 100,000,000)
    *///=========================================================================================================================
    public static String getNumberString(double number)
    {
        // get the index used for the number suffix
        int suffixIndex = (int)Math.log10(number) / 3;
        String numberSuffix = "";
        if (suffixIndex >= 0)
        {
            numberSuffix = numberSuffixes[suffixIndex];
        } 

        // get a truncated version of the number
        number /= Math.pow(10, suffixIndex * 3);
        
        // return the number
        return formatCurrency(number) + " " + numberSuffix;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
}
