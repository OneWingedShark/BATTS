/**
 * 
 */
package package_Ada_Language;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
//ControlFlowException
//ExecutionSignature
import com.oracle.truffle.api.nodes.NodeInfo;

//RootNode
//BlockNode
//DirectCallNode
//ExecutableNode
//LoopNode
//IndirectCallNode
import package_Ada_Language.lexington.Token;

/**
 * 
 */
@NodeInfo(language = "Ada/2022", description = "The abstract base node for all expressions.")
public class Ada_Node extends Node
{
	AssertionError ae;
	
	
	//Annex H

	/**
	 * Classification of constructs.
	 * Types-class interfaces.
	 */
	public Ada_Node()
	{
		// TODO Auto-generated constructor stub
	}
	
	public Object executeGeneric(VirtualFrame frame) 
	{
	        return null;
	}

	public boolean executeBool()
	{	return true;	}
}
