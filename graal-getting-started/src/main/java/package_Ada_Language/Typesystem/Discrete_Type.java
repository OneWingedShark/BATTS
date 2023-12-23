package package_Ada_Language.Typesystem;

import java.math.BigInteger;

public abstract class Discrete_Type extends Scalar_Type
{
/* LRM:  
  For every discrete subtype S, the following attributes are defined: 
	S'Enum_Rep							S'Enum_Rep denotes a function with the following specification:
										function S'Enum_Rep (Arg : S'Base) return universal_integer
										This function returns the representation value of the value of Arg, as a value of type universal_integer. The representation value is the internal code specified in an enumeration representation clause, if any, for the type corresponding to the value of Arg, and otherwise is the position number of the value.
										
	S'Enum_Val							S'Enum_Val denotes a function with the following specification:
										function S'Enum_Val (Arg : universal_integer) return S'Base
										This function returns a value of the type of S whose representation value equals the value of Arg. For the evaluation of a call on S'Enum_Val, if there is no value in the base range of its type with the given representation value, Constraint_Error is raised.
//*/
	
	abstract	BigInteger				Enum_Rep( Discrete_Type Arg );
	abstract	Discrete_Type			Enum_Val( BigInteger Arg );
	
/*	void First();
	void Last();
	void Pos();
	void Val();
//*/
}
