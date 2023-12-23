package package_Ada_Language.Typesystem;

public class Range_Value<Value extends Scalar_Type & Comparable>
{
	Value high;
	Value low;
	
	Range_Value( Value low, Value high )
	{
		this.low  = low;
		this.high = high;
	}
	
	boolean Is_Null()
	{ return !(high.compareTo(low) < 0); }
}