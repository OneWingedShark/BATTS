package package_Ada_Language.ir_DIANA_2022;

import package_Ada_Language.Ada_Node;

public final class DIANA_Test<Node extends Ada_Node> extends DIANA_ROOT
{
	private final Ada_Node	test_node;
	private final Node		child;
	
	public DIANA_Test( Ada_Node item )
	{	this(item, null);	}
	
	
	public DIANA_Test( Ada_Node item, Node child )
	{	this.test_node = item; this.child = child;	}
	
	public Boolean test()
	{ return test_node.executeBool(); };
	
	public Node get_child()
	{	return child;	}
}