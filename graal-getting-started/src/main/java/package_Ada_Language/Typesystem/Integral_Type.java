package package_Ada_Language.Typesystem;
//import package_Ada_Language.Typesystem.Numeric_Type;

public abstract class Integral_Type extends Discrete_Type implements Numeric_Type
{
	/*	
    function "="	(Left, Right : Integer'Base)			return Boolean;
    function "/="	(Left, Right : Integer'Base)			return Boolean;
    function "<" 	(Left, Right : Integer'Base)			return Boolean;
    function "<="	(Left, Right : Integer'Base)			return Boolean;
    function ">" 	(Left, Right : Integer'Base)			return Boolean;
    function ">="	(Left, Right : Integer'Base) 			return Boolean;
    function "+"  	(Right : Integer'Base)					return Integer'Base;
    function "-"  	(Right : Integer'Base)					return Integer'Base;
    function "abs"	(Right : Integer'Base) 					return Integer'Base;
    function "+"	(Left, Right : Integer'Base)			return Integer'Base;
    function "-"	(Left, Right : Integer'Base)			return Integer'Base;
    function "*"	(Left, Right : Integer'Base)			return Integer'Base;
    function "/"	(Left, Right : Integer'Base)			return Integer'Base;
    function "rem"	(Left, Right : Integer'Base)			return Integer'Base;
    function "mod"	(Left, Right : Integer'Base)			return Integer'Base;
    function "**" 	(Left : Integer'Base; Right : Natural)	return Integer'Base;
//*/

	/****************
	 * COMPARISIONS *
	 ****************/
	abstract	boolean Equal				( Integral_Type Left,	Integral_Type Right );
	abstract	boolean Not_Equal			( Integral_Type Left,	Integral_Type Right );
	abstract	boolean Less_Than			( Integral_Type Left,	Integral_Type Right );
	abstract	boolean Less_Than_Equal		( Integral_Type Left,	Integral_Type Right );
	abstract	boolean Greater_Than		( Integral_Type Left,	Integral_Type Right );
	abstract	boolean Greater_Than_Equal	( Integral_Type Left,	Integral_Type Right );
	
	/******************
	 * UNARY OPERATOR * 
	 ******************/
	abstract	Integral_Type Unary_Plus	( 						Integral_Type Right );
	abstract	Integral_Type Unary_Minus	( 						Integral_Type Right );
	abstract	Integral_Type Unary_Abs		( 						Integral_Type Right );
		
	/*******************
	 * BINARY OPERATOR * 
	 *******************/
	abstract	Integral_Type Binary_Plus	( Integral_Type Left,	Integral_Type Right );
	abstract	Integral_Type Binary_Minus	( Integral_Type Left,	Integral_Type Right );
	abstract	Integral_Type Binary_Mult	( Integral_Type Left,	Integral_Type Right );
	abstract	Integral_Type Binary_Div	( Integral_Type Left,	Integral_Type Right );
	abstract	Integral_Type Binary_Rem	( Integral_Type Left,	Integral_Type Right );
	abstract	Integral_Type Binary_Mod	( Integral_Type Left,	Integral_Type Right );
	abstract	Integral_Type Binary_Exp	( Integral_Type Left,	Integral_Type Right );

//	/****************
//	 * COMPARISIONS *
//	 ****************/
//	abstract	boolean Equal				( Integral_Type Left,	Integral_Type Right );
//	abstract	boolean Not_Equal			( Integral_Type Left,	Integral_Type Right );
//	abstract	boolean Less_Than			( Integral_Type Left,	Integral_Type Right );
//	abstract	boolean Less_Than_Equal		( Integral_Type Left,	Integral_Type Right );
//	abstract	boolean Greater_Than		( Integral_Type Left,	Integral_Type Right );
//	abstract	boolean Greater_Than_Equal	( Integral_Type Left,	Integral_Type Right );
//	
//	/******************
//	 * UNARY OPERATOR * 
//	 ******************/
//	abstract	Integral_Type Unary_Plus	( 						Integral_Type Right );
//	abstract	Integral_Type Unary_Minus	( 						Integral_Type Right );
//	abstract	Integral_Type Unary_Abs		( 						Integral_Type Right );
//		
//	/*******************
//	 * BINARY OPERATOR * 
//	 *******************/
//	abstract	Integral_Type Binary_Plus	( Integral_Type Left,	Integral_Type Right );
//	abstract	Integral_Type Binary_Minus	( Integral_Type Left,	Integral_Type Right );
//	abstract	Integral_Type Binary_Mult	( Integral_Type Left,	Integral_Type Right );
//	abstract	Integral_Type Binary_Div	( Integral_Type Left,	Integral_Type Right );
//	abstract	Integral_Type Binary_Rem	( Integral_Type Left,	Integral_Type Right );
//	abstract	Integral_Type Binary_Mod	( Integral_Type Left,	Integral_Type Right );
}
