package package_Ada_Language.Typesystem;

public abstract interface Numeric_Type //extends Scalar_Type 
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
	boolean Equal				( Numeric_Type Left,	Numeric_Type Right );
	boolean Not_Equal			( Numeric_Type Left,	Numeric_Type Right );
	boolean Less_Than			( Numeric_Type Left,	Numeric_Type Right );
	boolean Less_Than_Equal		( Numeric_Type Left,	Numeric_Type Right );
	boolean Greater_Than		( Numeric_Type Left,	Numeric_Type Right );
	boolean Greater_Than_Equal	( Numeric_Type Left,	Numeric_Type Right );
	
	/******************
	 * UNARY OPERATOR * 
	 ******************/
	Numeric_Type Unary_Plus		( 						Numeric_Type Right );
	Numeric_Type Unary_Minus	( 						Numeric_Type Right );
	Numeric_Type Unary_Abs		( 						Numeric_Type Right );
		
	/*******************
	 * BINARY OPERATOR * 
	 *******************/
	Numeric_Type Binary_Plus	( Numeric_Type Left,	Numeric_Type Right );
	Numeric_Type Binary_Minus	( Numeric_Type Left,	Numeric_Type Right );
	Numeric_Type Binary_Mult	( Numeric_Type Left,	Numeric_Type Right );
	Numeric_Type Binary_Div		( Numeric_Type Left,	Numeric_Type Right );
	Numeric_Type Binary_Rem		( Numeric_Type Left,	Numeric_Type Right );
	Numeric_Type Binary_Mod		( Numeric_Type Left,	Numeric_Type Right );
	//Numeric_Type Binary_Exp		( Numeric_Type Left,	Numeric_Type Right );
	
}