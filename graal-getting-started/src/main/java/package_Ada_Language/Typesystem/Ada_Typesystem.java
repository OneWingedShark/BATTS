package package_Ada_Language.Typesystem;

import com.oracle.truffle.api.dsl.TypeSystem;

@TypeSystem(
	{	Access_Type.class,	Enumeration_Type.class,	Signed_Type.class,		Modular_Type.class,
		Float_Type.class,	Decimal_Type.class,		Ordinary_Type.class,
		Array_Type.class,	Record_Type.class,		Protected_Type.class,	Task_Type.class
	 })
public class Ada_Typesystem
{
	// No implicit type-conversions, except from Universal_* (or Root_*) to a descendant-type.
}