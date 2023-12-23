package package_Ada_Language.Typesystem;

import java.math.BigInteger;

public abstract class Scalar_Type extends Elementry_Type implements Comparable
{
	/* ANNEX H: Reviewable */
	Object_State Initialization_State = Object_State.POSSIBLY_UNINITIALIZED;
	
	
/* LRM: 3.5
	For every scalar subtype S, the following attributes are defined: 
	S'First				S'First denotes the lower bound of the range of S.
	 					The value of this attribute is of the type of S.
	 					 
	S'Last				S'Last denotes the upper bound of the range of S.
	 					The value of this attribute is of the type of S.
	 					 
	S'Range				S'Range is equivalent to the range S'First .. S'Last.
	
	S'Base				S'Base denotes an unconstrained subtype of the type of S.
	 					This unconstrained subtype is called the base subtype of the type.
	 					
	S'Min				S'Min denotes a function with the following specification:
						function S'Min(Left, Right : S'Base) return S'Base
							The function returns the lesser of the values of the two parameters.
							 
	S'Max				S'Max denotes a function with the following specification:
						function S'Max(Left, Right : S'Base) return S'Base
							The function returns the greater of the values of the two parameters.
							
	S'Succ				S'Succ denotes a function with the following specification:
						function S'Succ(Arg : S'Base) return S'Base
							For an enumeration type, the function returns the value whose position number is one more than that of the value of Arg; Constraint_Error is raised if there is no such value of the type. For an integer type, the function returns the result of adding one to the value of Arg. For a fixed point type, the function returns the result of adding small to the value of Arg. For a floating point type, the function returns the machine number (as defined in 3.5.7) immediately above the value of Arg; Constraint_Error is raised if there is no such machine number. 
	
	S'Pred				S'Pred denotes a function with the following specification:
						function S'Pred(Arg : S'Base) return S'Base
							For an enumeration type, the function returns the value whose position number is one less than that of the value of Arg; Constraint_Error is raised if there is no such value of the type. For an integer type, the function returns the result of subtracting one from the value of Arg. For a fixed point type, the function returns the result of subtracting small from the value of Arg. For a floating point type, the function returns the machine number (as defined in 3.5.7) immediately below the value of Arg; Constraint_Error is raised if there is no such machine number.
							 
	S'Wide_Wide_Width	S'Wide_Wide_Width denotes the maximum length of a Wide_Wide_String returned by S'Wide_Wide_Image over all values of the subtype S, assuming a default implementation of S'Put_Image. It denotes zero for a subtype that has a null range.
	 					Its type is universal_integer.
	 					
	S'Wide_Width		S'Wide_Width denotes the maximum length of a Wide_String returned by S'Wide_Image over all values of the subtype S, assuming a default implementation of S'Put_Image. It denotes zero for a subtype that has a null range.
	 					Its type is universal_integer.
	 					
	S'Width				S'Width denotes the maximum length of a String returned by S'Image over all values of the subtype S, assuming a default implementation of S'Put_Image. It denotes zero for a subtype that has a null range.
						Its type is universal_integer.
						
	S'Wide_Wide_Value	S'Wide_Wide_Value denotes a function with the following specification:
	 					function S'Wide_Wide_Value(Arg : Wide_Wide_String) return S'Base
	 						This function returns a value given an image of the value as a Wide_Wide_String, ignoring any leading or trailing spaces.
							For the evaluation of a call on S'Wide_Wide_Value for an enumeration subtype S, if the sequence of characters of the parameter (ignoring leading and trailing spaces) has the syntax of an enumeration literal and if it corresponds to a literal of the type of S (or corresponds to the result of S'Wide_Wide_Image for a nongraphic character of the type), the result is the corresponding enumeration value; otherwise, Constraint_Error is raised. 
							For the evaluation of a call on S'Wide_Wide_Value for an integer subtype S, if the sequence of characters of the parameter (ignoring leading and trailing spaces) has the syntax of an integer literal, with an optional leading sign character (plus or minus for a signed type; only plus for a modular type), and the corresponding numeric value belongs to the base range of the type of S, then that value is the result; otherwise, Constraint_Error is raised.
							For the evaluation of a call on S'Wide_Wide_Value for a real subtype S, if the sequence of characters of the parameter (ignoring leading and trailing spaces) has the syntax of one of the following:
							* numeric_literal
							* numeral.[exponent]
							* .numeral[exponent]
							* base#based_numeral.#[exponent]
							* base#.based_numeral#[exponent]
							with an optional leading sign character (plus or minus), and if the corresponding numeric value belongs to the base range of the type of S, then that value is the result; otherwise, Constraint_Error is raised. The sign of a zero value is preserved (positive if none has been specified) if S'Signed_Zeros is True.

	S'Wide_Value		S'Wide_Value denotes a function with the following specification:
						function S'Wide_Value(Arg : Wide_String) return S'Base
							This function returns a value given an image of the value as a Wide_String, ignoring any leading or trailing spaces.
							For the evaluation of a call on S'Wide_Value for an enumeration subtype S, if the sequence of characters of the parameter (ignoring leading and trailing spaces) has the syntax of an enumeration literal and if it corresponds to a literal of the type of S (or corresponds to the result of S'Wide_Image for a value of the type, assuming a default implementation of S'Put_Image), the result is the corresponding enumeration value; otherwise, Constraint_Error is raised. For a numeric subtype S, the evaluation of a call on S'Wide_Value with Arg of type Wide_String is equivalent to a call on S'Wide_Wide_Value for a corresponding Arg of type Wide_Wide_String.
							 
	S'Value				S'Value denotes a function with the following specification:
	 						function S'Value(Arg : String) return S'Base
						This function returns a value given an image of the value as a String, ignoring any leading or trailing spaces.
						For the evaluation of a call on S'Value for an enumeration subtype S, if the sequence of characters of the parameter (ignoring leading and trailing spaces) has the syntax of an enumeration literal and if it corresponds to a literal of the type of S (or corresponds to the result of S'Image for a value of the type, assuming a default implementation of S'Put_Image), the result is the corresponding enumeration value; otherwise, Constraint_Error is raised. For a numeric subtype S, the evaluation of a call on S'Value with Arg of type String is equivalent to a call on S'Wide_Wide_Value for a corresponding Arg of type Wide_Wide_String. }
*/

	abstract	Scalar_Type					First();
	abstract	Scalar_Type					Last();
	abstract	Range_Value<Scalar_Type>	Range();
	abstract	Ada_Type					Base();
				Scalar_Type					Min(Scalar_Type left, Scalar_Type right){ return (left.compareTo(right) >  0) ? right : left; }
				Scalar_Type					Max(Scalar_Type left, Scalar_Type right){ return (left.compareTo(right) <= 0) ? right : left; }
	abstract	Scalar_Type					Succ(Scalar_Type Arg);
	abstract	Scalar_Type					Pred(Scalar_Type Arg);

	abstract	BigInteger					Wide_Wide_Width();
	abstract	BigInteger					Wide_Width();
	abstract	BigInteger					Width();
	abstract	Scalar_Type					Wide_Wide_Value(String Arg);
	abstract	Scalar_Type					Wide_Value(String Arg);
	abstract	Scalar_Type					Value(String Arg);

/*
	Default_Value
	This aspect shall be specified by a static expression, and that expression shall be explicit, even if the aspect has a boolean type. Default_Value shall be specified only on a full_type_declaration.
*/

	//Scalar_Type	default_value; 
	abstract	Scalar_Type					Default_Value();
	//Scalar_Type() {this.default_value = x;}
	
}