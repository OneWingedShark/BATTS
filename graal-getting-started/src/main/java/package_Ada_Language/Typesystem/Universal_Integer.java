package package_Ada_Language.Typesystem;

import java.math.BigInteger;


public class Universal_Integer extends BigInteger // implements Numeric_Type
{
	
	public Universal_Integer(int val)
	{	super( Integer.toString(val) );	}
	
	public Universal_Integer(String val)
	{	super(val);	}

	private static final long serialVersionUID = 9141522433571098677L;

}
