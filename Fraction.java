/**
* Class Fraction performs arithmetic computations on the fractional representation of numbers. 
* The class provides methods to add, subtract, multiply, and divide fractions. It also provides
* methods to access fractional components, test fractions for equality, compute the real-number
* value of a fraction, and generate the string representation of the fraction.
*
* As fractions are constructed and operated upon, they are maintained in reduced form. 
* The reduced form is obtained by dividing the numerator and denominator through 
* by their greatest common divisor. For example, 2/3 is the reduced form of 8/12. 
*
* @author Jonathon Elfar jelfar jonathon.elfar@gmail.com
*/
public class Fraction {

    /** Declaring the numerator data field */
    private int numerator;

    /** Declaring the denominator data field */
    private int denominator;

    /**
     * Construct a fraction with numerator 0 and denominator 1.
     */
    public Fraction() {
        this.numerator = 0;
        this.denominator = 1;
    }

    /**
     * Construct a fraction with the given numerator and a denominator of 1.
     * 
     * @param numerator             The numerator of the fraction.
     */
    public Fraction(int numerator) {
        this.numerator = numerator;
        this.denominator = 1;
    }

    /**
     * Construct a fraction with the given numerator and denominator. 
     * The fraction is reduced if necessary, per the reduction rule described in 
     * the class comment. If the demoninator is less than 1, the constructor 
     * throws an IllegalArgumentException.
     *
     * @param numerator             The numerator of the fraction.
     * @param denominator           The denominator of the fraction.
     */
    public Fraction(int numerator, int denominator) {
        if(denominator < 1)
            throw new IllegalArgumentException();
        this.numerator = numerator;
        this.denominator = denominator;
        this.reduce();
    }

    /**
     * Add the given fraction to this fraction and return the result as a third new fraction.
     *
     * @param f                     The fraction to add to this.
     * @return                      A fraction representing the sum of this and f.
     */
    public Fraction add(Fraction f) {
        /** Equal denominators */
        if( f.getDenominator() == this.denominator )
            return new Fraction( f.getNumerator() + this.numerator, this.denominator);
        
        /** Unlike denominators */ 
        
        /** The least common multiple found between both denominators. */
        int lcd = getLCD(this.denominator, f.getDenominator());

        /** Common algorithm to find new numerator with least common denominator(lcd): lcd / denominator * numerator */
        return new Fraction( (lcd / this.denominator * this.numerator) + (lcd / f.getDenominator() * f.getNumerator()), lcd);
    }

    /**
     * Divide this fraction by the given fraction and return the result as a third new fraction.
     * 
     * @param f                     The fraction to divide into this.
     * @return                      A fraction representing the quotient of this divided by f.
     */
    public Fraction div(Fraction f) {
        /** Accounts for the input of a negative fraction */
        if(f.getNumerator() < 0)
            return new Fraction( (-1) * (this.numerator * f.getDenominator()), Math.abs(this.denominator * f.getNumerator()));
        return new Fraction( (this.numerator * f.getDenominator()), (this.denominator * f.getNumerator()));
    }

    /**
     * Return true if this fraction equals the given fraction, i.e, the numerator and denominator of the two fractions are equal.
     *
     * @param f                     The fraction to compare to this fraction.
     * @return                      Returns true if equal, otherwise false.
     */
    public boolean equals(Fraction f) {
        return (this.numerator == f.getNumerator()) && (this.denominator == f.getDenominator());
    }
   
    /**
     * Return the value of this fraction's denominator.
     *
     * @return                      The denominator of the fraction.
     */
    public int getDenominator() {
        return this.denominator;
    }

    /**
     * Return the value of this fraction's numerator.
     *
     * @return                      The numerator of the fraction.
     */
    public int getNumerator() {
        return this.numerator;
    }

    /**
     * Multiply this fraction by the given fraction and return the result as a third new fraction.
     *
     * @param f                     The fraction to multipy by this.
     * @return                      A fraction representing the product of this and f.
     */
    public Fraction mul(Fraction f) {
        return new Fraction(this.numerator * f.getNumerator(), this.denominator * f.getDenominator());
    }

    /**
     * Subtract the given fraction from this fraction and return the result as a third new fraction.
     *
     * @param f                     The fraction to subtract from this.
     * @return                      A fraction representing the difference of this and f.
     */
    public Fraction sub(Fraction f) {
        /** Denominators equal */
        if( f.getDenominator() == this.denominator )
            return new Fraction( this.numerator - f.getNumerator(), this.denominator);
        
        /** Denominators different */
       
        /** The least common multiple found between both denominators. */
        int lcd = getLCD(this.denominator, f.getDenominator());

        /** Common algorithm to find new numerator with least common denominator(lcd): lcd / denominator * numerator */
        return new Fraction( (lcd / this.denominator * this.numerator) - (lcd / f.getDenominator() * f.getNumerator()), lcd);
    }

    /**
     * Produce the string representation of this fraction, of the form "numerator / demoninator", e.g., "1/2" or "3/4".
     *
     * @return                      The fraction as a string.
     */
    public String toString() {
        if(denominator != 1)
            return "" + this.numerator + "/" + this.denominator;
        /** The fraction is a  whole number */
        else 
            return "" + this.numerator;
    }

    /**
     * Return the value of this fraction as a real number.
     *
     * @return                      The value of the fraction as a double.
     */
    public double value() {
        return ((double) this.numerator / this.denominator);
    }

        
    /**
      * Reduce a string by dividing by the greatest common divisor(GCD).
      *
      * @return                     The newly reduced fraction.
      */
    private void reduce() {
        /** The fraction is equal to 0. The numerator is 0 in this case */
        if(this.numerator == 0)
            this.denominator = 1;
        /** The numerator and denominator are not equal */
        else if (this.numerator != this.denominator) {

            /** The greatest common divisor of the numerator and denominator of this fraction */
            int gcd = getGCD(Math.abs(this.numerator), Math.abs(this.denominator));

            /** There is a greatest common divisor greater than 1 */
            if (gcd > 1) {
                this.numerator /= gcd;
                this.denominator /= gcd;
            }
        } 
        /** The fraction is equal to 1 */
        else {
            this.numerator = 1;
            this.denominator = 1;
        }
    }

    /**
      * Returns greatest common divisor between two numbers.
      * Uses the Euclidean algorithm:
      *
      * 1) If the first number (a) is less than the second number (b),
      *    then swap them (put the larger number in a).
      * 2) Divide first number (a) by second number (b) and get remainder (r).
      *    If r is 0, return second number (b) as the gcd.
      * 3) Call gcd function again using second number (b) and remainder (r)
      *    as the inputs respectively.
      *
      * @param a                    The first number.
      * @param b                    The second number.
      * @return                     The greatest common divisor.
      */
    private int getGCD(int a, int b) {
        
        /** This ensures the larger of the two numbers is in a */
        if(a < b){
            /** Temp variable used for swapping two variables */
            int temp = a;
            a = b;
            b = temp;
        }

        /** The remainder needs to be stored to recursively find the gcd */
        int remainder;
        if((remainder = a % b) == 0) {
            return b;
        } else {
            return getGCD(b, remainder);
        }
    }

    /**
      * Returns the least common denominator between two numbers.
      *
      * @param a                    The first number.
      * @param b                    The second number.
      * @return                     The least common denominator.
      */
    private int getLCD(int a, int b){
       return ( (a * b) / getGCD(a, b) ); 
    }
}
