package package_Ada_Language.Typesystem;

import java.math.BigInteger;

public abstract class Fixed_Type extends Real_Type
{
/* LRM: X.X.X
	The following representation-oriented attributes are defined for every subtype S of a fixed point type T.
	S'Machine_Radix		Yields the radix of the hardware representation of the type T.
						The value of this attribute is of the type universal_integer.
						
	S'Machine_Rounds	Yields the value True if rounding is performed on inexact results of every predefined operation that yields a result of the type T; yields the value False otherwise.
						The value of this attribute is of the predefined type Boolean.
						
	S'Machine_Overflows	Yields the value True if overflow and divide-by-zero are detected and reported by raising Constraint_Error for every predefined operation that yields a result of the type T; yields the value False otherwise.
						The value of this attribute is of the predefined type Boolean.
//*/
	

	abstract	BigInteger	Machine_Radix();
	abstract	boolean		Machine_Rounds();    
	abstract	boolean		Machine_Overflows();
	
	
	
/* LRM: 3.5.10
	The following attributes are defined for every fixed point subtype S: 
		S'Small			S'Small denotes the small of the type of S. The value of this attribute is of the type universal_real.
		 				Small may be specified for nonderived ordinary fixed point types via an attribute_definition_clause (see 13.3); the expression of such a clause shall be static and positive.
		 				
		S'Delta			S'Delta denotes the delta of the fixed point subtype S.
		 				The value of this attribute is of the type universal_real.
		 				 
		S'Fore			S'Fore yields the minimum number of characters needed before the decimal point for the decimal representation of any value of the subtype S, assuming that the representation does not include an exponent, but includes a one-character prefix that is either a minus sign or a space. (This minimum number does not include superfluous zeros or underlines, and is at least 2.)
		 				The value of this attribute is of the type universal_integer.
		 				
		S'Aft			S'Aft yields the number of decimal digits needed after the decimal point to accommodate the delta of the subtype S, unless the delta of the subtype S is greater than 0.1, in which case the attribute yields the value one. (S'Aft is the smallest positive integer N for which (10**N)*S'Delta is greater than or equal to one.)
		 				The value of this attribute is of the type universal_integer.
*/
	
	abstract	double		Small();
	abstract	double		Delta();
	abstract	BigInteger	Fore();
	abstract	BigInteger	Aft();
	
}
