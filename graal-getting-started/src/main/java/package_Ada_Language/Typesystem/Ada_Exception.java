package package_Ada_Language.Typesystem;

import com.oracle.truffle.api.exception.AbstractTruffleException;
import package_Ada_Language.Ada_Node;

public abstract class Ada_Exception extends AbstractTruffleException
{
	private static final long serialVersionUID = -2409402633282014892L;
	
	protected Object Auxiliary_Data = null;
	
	public Ada_Exception(String message, Ada_Node location)
	{ super(message, location); }
	
	public Ada_Exception( String message )
	{ this.Auxiliary_Data = message; }

	//*/

}