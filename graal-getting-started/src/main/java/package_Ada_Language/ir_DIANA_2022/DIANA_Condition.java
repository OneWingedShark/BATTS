package package_Ada_Language.ir_DIANA_2022;
import  package_Ada_Language.Ada_Node;

public class DIANA_Condition<Dependant extends Ada_Node> extends DIANA_ROOT
{
	final Dependant[] nodes; 

	public DIANA_Condition(Dependant[] nodes)
	{	this.nodes = nodes;		}
}
