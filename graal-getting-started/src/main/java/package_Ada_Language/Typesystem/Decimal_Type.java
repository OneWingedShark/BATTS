package package_Ada_Language.Typesystem;

import java.math.BigInteger;

public abstract class Decimal_Type extends Fixed_Type
{
	/* LRM: 3.5.10
		The following additional attributes are defined for every decimal fixed point subtype S: 
			S'Digits		S'Digits denotes the digits of the decimal fixed point subtype S, which corresponds to the number of decimal digits that are representable in objects of the subtype.
			 				The value of this attribute is of the type universal_integer.
			 				Its value is determined as follows:
								For a first subtype or a subtype defined by a subtype_indication with a digits_constraint, the digits is the value of the expression given after the reserved word digits;
								For a subtype defined by a subtype_indication without a digits_constraint, the digits of the subtype is the same as that of the subtype denoted by the subtype_mark in the subtype_indication;
							The digits of a base subtype is the largest integer D such that the range –(10**D–1)*delta .. +(10**D–1)*delta is included in the base range of the type.
							
			S'Scale			S'Scale denotes the scale of the subtype S, defined as the value N such that S'Delta = 10.0**(–N). The scale indicates the position of the point relative to the rightmost significant digits of values of subtype S.
			 				The value of this attribute is of the type universal_integer.
			 				 
			S'Round			S'Round denotes a function with the following specification: 
								function S'Round(X : universal_real) return S'Base
							The function returns the value obtained by rounding X (away from 0, if X is midway between two values of the type of S).
	*/
		

		abstract	BigInteger		Digits();
		abstract	BigInteger		Scale();
		abstract	Decimal_Type	Round( double x );

}
