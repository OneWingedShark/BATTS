package package_Ada_Language.Typesystem;

public abstract class Float_Type extends Real_Type
{
/* LRM: A.5.3
	The following representation-oriented attributes are defined for every subtype S of a floating point type T.
	S'Machine_Radix					Yields the radix of the hardware representation of the type T.
	 								The value of this attribute is of the type universal_integer. 
									The values of other representation-oriented attributes of a floating point subtype, and of the “primitive function” attributes of a floating point subtype described later, are defined in terms of a particular representation of nonzero values called the canonical form.
									The canonical form (for the type T) is the form ± mantissa · T'Machine_Radixexponent where mantissa is a fraction in the number base T'Machine_Radix, the first digit of which is nonzero, and exponent is an integer.
									 
	S'Machine_Mantissa				Yields the largest value of p such that every value expressible in the canonical form (for the type T), having a p-digit mantissa and an exponent between T'Machine_Emin and T'Machine_Emax, is a machine number (see 3.5.7) of the type T.
									This attribute yields a value of the type universal_integer.
									 
	S'Machine_Emin					Yields the smallest (most negative) value of exponent such that every value expressible in the canonical form (for the type T), having a mantissa of T'Machine_Mantissa digits, is a machine number (see 3.5.7) of the type T.
	 								This attribute yields a value of the type universal_integer.
	 								
	8S'Machine_Emax					Yields the largest (most positive) value of exponent such that every value expressible in the canonical form (for the type T), having a mantissa of T'Machine_Mantissa digits, is a machine number (see 3.5.7) of the type T.
	 								This attribute yields a value of the type universal_integer.
	 								 
	S'Denorm						Yields the value True if every value expressible in the form ± mantissa · T'Machine_RadixT'Machine_Emin where mantissa is a nonzero T'Machine_Mantissa-digit fraction in the number base T'Machine_Radix, the first digit of which is zero, is a machine number (see 3.5.7) of the type T; yields the value False otherwise.
	 								The value of this attribute is of the predefined type Boolean.
	 								The values described by the formula in the definition of S'Denorm are called denormalized numbers. A nonzero machine number that is not a denormalized number is a normalized number. A normalized number x of a given type T is said to be represented in canonical form when it is expressed in the canonical form (for the type T) with a mantissa having T'Machine_Mantissa digits; the resulting form is the canonical-form representation of x. 
	
	S'Machine_Rounds				Yields the value True if rounding is performed on inexact results of every predefined operation that yields a result of the type T; yields the value False otherwise.
									The value of this attribute is of the predefined type Boolean.
									 
	S'Machine_Overflows				Yields the value True if overflow and divide-by-zero are detected and reported by raising Constraint_Error for every predefined operation that yields a result of the type T; yields the value False otherwise.
									The value of this attribute is of the predefined type Boolean.
									
	S'Signed_Zeros					Yields the value True if the hardware representation for the type T has the capability of representing both positively and negatively signed zeros, these being generated and used by the predefined operations of the type T as specified in IEC 559:1989; yields the value False otherwise.
									The value of this attribute is of the predefined type Boolean.
									For every value x of a floating point type T, the normalized exponent of x is defined as follows:
									* the normalized exponent of zero is (by convention) zero;
									* for nonzero x, the normalized exponent of x is the unique integer k such that T'Machine_Radixk–1 ≤ |x| < T'Machine_Radixk.
									
	The following primitive function attributes are defined for any subtype S of a floating point type T.
	 
	S'Exponent						S'Exponent denotes a function with the following specification:
	 								function S'Exponent (X : T) return universal_integer
	 								The function yields the normalized exponent of X.
	 								
	S'Fraction						S'Fraction denotes a function with the following specification:
	 								function S'Fraction (X : T) return T
	 								The function yields the value X · T'Machine_Radix–k, where k is the normalized exponent of X. A zero result, which can only occur when X is zero, has the sign of X.
	 								 
	S'Compose						S'Compose denotes a function with the following specification:
	 								function S'Compose (Fraction : T; Exponent : universal_integer) return T
	 								Let v be the value Fraction · T'Machine_RadixExponent–k, where k is the normalized exponent of Fraction. If v is a machine number of the type T, or if |v| ≥ T'Model_Small, the function yields v; otherwise, it yields either one of the machine numbers of the type T adjacent to v. Constraint_Error is optionally raised if v is outside the base range of S.
									A zero result has the sign of Fraction when S'Signed_Zeros is True.
									 
	S'Scaling						S'Scaling denotes a function with the following specification:
	 								function S'Scaling (X : T; Adjustment : universal_integer) return T
	 								Let v be the value X · T'Machine_RadixAdjustment. If v is a machine number of the type T, or if |v| ≥ T'Model_Small, the function yields v; otherwise, it yields either one of the machine numbers of the type T adjacent to v. Constraint_Error is optionally raised if v is outside the base range of S. A zero result has the sign of X when S'Signed_Zeros is True.
	 								 
	S'Floor							S'Floor denotes a function with the following specification:
	 								function S'Floor (X : T) return T
	 								The function yields the value Floor(X), that is, the largest (most positive) integral value less than or equal to X. When X is zero, the result has the sign of X; a zero result otherwise has a positive sign.
	 								
	S'Ceiling						S'Ceiling denotes a function with the following specification:
	 								function S'Ceiling (X : T) return T
	 								The function yields the value Ceiling(X), that is, the smallest (most negative) integral value greater than or equal to X. When X is zero, the result has the sign of X; a zero result otherwise has a negative sign when S'Signed_Zeros is True.
	 								
	36S'Rounding					S'Rounding denotes a function with the following specification:
	 								function S'Rounding (X : T) return T
	 								The function yields the integral value nearest to X, rounding away from zero if X lies exactly halfway between two integers. A zero result has the sign of X when S'Signed_Zeros is True.
	 								
	S'Unbiased_Rounding				S'Unbiased_Rounding denotes a function with the following specification:
	 								function S'Unbiased_Rounding (X : T) return T
									The function yields the integral value nearest to X, rounding toward the even integer if X lies exactly halfway between two integers. A zero result has the sign of X when S'Signed_Zeros is True.
									
	S'Machine_Rounding				S'Machine_Rounding denotes a function with the following specification:
	 								function S'Machine_Rounding (X : T) return T
									The function yields the integral value nearest to X. If X lies exactly halfway between two integers, one of those integers is returned, but which of them is returned is unspecified. A zero result has the sign of X when S'Signed_Zeros is True. This function provides access to the rounding behavior which is most efficient on the target processor.
									
	S'Truncation					S'Truncation denotes a function with the following specification:
									function S'Truncation (X : T) return T
									The function yields the value Ceiling(X) when X is negative, and Floor(X) otherwise. A zero result has the sign of X when S'Signed_Zeros is True.
									
	45S'Remainder					S'Remainder denotes a function with the following specification:
	 								function S'Remainder (X, Y : T) return T
	 								For nonzero Y, let v be the value X – n · Y, where n is the integer nearest to the exact value of X/Y; if |n – X/Y| = 1/2, then n is chosen to be even. If v is a machine number of the type T, the function yields v; otherwise, it yields zero. Constraint_Error is raised if Y is zero. A zero result has the sign of X when S'Signed_Zeros is True.
	 								
	S'Adjacent						S'Adjacent denotes a function with the following specification:
									function S'Adjacent (X, Towards : T) return T
									If Towards = X, the function yields X; otherwise, it yields the machine number of the type T adjacent to X in the direction of Towards, if that machine number exists. If the result would be outside the base range of S, Constraint_Error is raised. When T'Signed_Zeros is True, a zero result has the sign of X. When Towards is zero, its sign has no bearing on the result.
									
	S'Copy_Sign						S'Copy_Sign denotes a function with the following specification:
	 								function S'Copy_Sign (Value, Sign : T) return T
	 								If the value of Value is nonzero, the function yields a result whose magnitude is that of Value and whose sign is that of Sign; otherwise, it yields the value zero. Constraint_Error is optionally raised if the result is outside the base range of S. A zero result has the sign of Sign when S'Signed_Zeros is True.
	 								
	S'Leading_Part					S'Leading_Part denotes a function with the following specification:
									function S'Leading_Part (X : T; Radix_Digits : universal_integer) return T
									Let v be the value T'Machine_Radixk–Radix_Digits, where k is the normalized exponent of X. The function yields the value Floor(X/v) · v, when X is nonnegative and Radix_Digits is positive; Ceiling(X/v) · v, when X is negative and Radix_Digits is positive.
									Constraint_Error is raised when Radix_Digits is zero or negative. A zero result, which can only occur when X is zero, has the sign of X.
									 
	S'Machine						S'Machine denotes a function with the following specification:
	 								function S'Machine (X : T) return T
	 								If X is a machine number of the type T, the function yields X; otherwise, it yields the value obtained by rounding or truncating X to either one of the adjacent machine numbers of the type T. Constraint_Error is raised if rounding or truncating X to the precision of the machine numbers results in a value outside the base range of S. A zero result has the sign of X when S'Signed_Zeros is True. 

	The following model-oriented attributes are defined for any subtype S of a floating point type T.
	 
	S'Model_Mantissa				If the Numerics Annex is not supported, this attribute yields an implementation defined value that is greater than or equal to Ceiling(d · log(10) / log(T'Machine_Radix)) + 1, where d is the requested decimal precision of T, and less than or equal to the value of T'Machine_Mantissa. See G.2.2 for further requirements that apply to implementations supporting the Numerics Annex.
									The value of this attribute is of the type universal_integer.
									
	S'Model_Emin					If the Numerics Annex is not supported, this attribute yields an implementation defined value that is greater than or equal to the value of T'Machine_Emin. See G.2.2 for further requirements that apply to implementations supporting the Numerics Annex.
									The value of this attribute is of the type universal_integer.
	
	S'Model_Epsilon					Yields the value T'Machine_Radix1 – T'Model_Mantissa.
									The value of this attribute is of the type universal_real.
									 
	S'Model_Small					Yields the value T'Machine_RadixT'Model_Emin – 1.
									The value of this attribute is of the type universal_real.
									 
	S'Model							S'Model denotes a function with the following specification:
									function S'Model (X : T) return T
									If the Numerics Annex is not supported, the meaning of this attribute is implementation defined; see G.2.2 for the definition that applies to implementations supporting the Numerics Annex.
									
	S'Safe_First					Yields the lower bound of the safe range (see 3.5.7) of the type T. If the Numerics Annex is not supported, the value of this attribute is implementation defined; see G.2.2 for the definition that applies to implementations supporting the Numerics Annex.
									The value of this attribute is of the type universal_real.
									
	S'Safe_Last						Yields the upper bound of the safe range (see 3.5.7) of the type T. If the Numerics Annex is not supported, the value of this attribute is implementation defined; see G.2.2 for the definition that applies to implementations supporting the Numerics Annex.
									The value of this attribute is of the type universal_real.
//*/
	
}
