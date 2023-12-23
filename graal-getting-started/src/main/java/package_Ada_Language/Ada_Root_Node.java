package package_Ada_Language;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;

public class Ada_Root_Node extends RootNode {
    @SuppressWarnings("FieldMayBeFinal")
    @Child
    private Ada_Node ex_Node;

    public Ada_Root_Node(Ada_Node ex_Node)
    {
    	super(null);
    	this.ex_Node = ex_Node;
    }
    
	@Override
	public Object execute(VirtualFrame frame) 
	{
	        return this.ex_Node.executeGeneric(frame);
	}
	    

}
/*/
public final class EasyScriptRootNode extends RootNode {
    @SuppressWarnings("FieldMayBeFinal")
    @Child
    private EasyScriptNode exprNode;

    public EasyScriptRootNode(EasyScriptNode exprNode) {
        super(null);

        this.exprNode = exprNode;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return this.exprNode.executeInt(frame);
    }
}
//*/