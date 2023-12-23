package package_Ada_Language;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.nodes.NodeInfo;

import package_Ada_Language.lexington.Parser;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.Truffle;

/*
 * @TruffleLanguage.Registration(id = SLLanguage.ID, name = "SL", defaultMimeType = SLLanguage.MIME_TYPE, characterMimeTypes = SLLanguage.MIME_TYPE, contextPolicy = ContextPolicy.SHARED, fileTypeDetectors = SLFileDetector.class, //
                website = "https://www.graalvm.org/graalvm-as-a-platform/implement-language/")
 * @ProvidedTags({StandardTags.CallTag.class, StandardTags.StatementTag.class, StandardTags.RootTag.class, StandardTags.RootBodyTag.class, StandardTags.ExpressionTag.class, DebuggerTags.AlwaysHalt.class,
                StandardTags.ReadVariableTag.class, StandardTags.WriteVariableTag.class}) 
 * 
 */

@TruffleLanguage.Registration(	id = Ada_Truffle_Language.ID, name = Ada_Truffle_Language.NAME,
								defaultMimeType = Ada_Truffle_Language.MIME_DEFAULT,
								characterMimeTypes = {	Ada_Truffle_Language.MIME_DEFAULT,	Ada_Truffle_Language.MIME_SPEC,
														Ada_Truffle_Language.MIME_BODY,		Ada_Truffle_Language.MIME_SEPARATE,
														Ada_Truffle_Language.MIME_DATABASE
													}
							)
public class Ada_Truffle_Language extends TruffleLanguage<Void>
//implements org.graalvm.polyglot.tck.LanguageProvider
{
	private static final String ID				= "Ada_2022";
	private static final String NAME			= "Ada\\2022";
	
	public static final String MIME_DEFAULT		= "application/Ada";
	public static final String MIME_SPEC		= "multipart/Ada-specification";
	public static final String MIME_BODY		= "multipart/Ada-body";
	public static final String MIME_SEPARATE	= "multipart/Ada-separate";
	public static final String MIME_DATABASE	= "application/Ada-DIANA";
	
	
	/** LRM 3.1		—	DECLARATIONS
	
	11		The process by which a construct achieves its run-time effect is called execution.
			This process is also called elaboration for declarations and evaluation for expressions.
			One of the terms execution, elaboration, or evaluation is defined by this Reference Manual for each construct that has a run-time effect.
			 
	12		NOTE:	At compile time, the declaration of an entity declares the entity. At run time, the elaboration of the declaration creates the entity.
	//*/
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/** LRM 1.3.4	—	RUNTIME ACTIONS

	1/5		ASSERTION
			boolean expression that is expected to be True at run time at certain specified places
	2/5		Note: Certain pragmas and aspects define various kinds of assertions.
	
	3/5		ELABORATION
			process by which a declaration achieves its run-time effect
	4/5		Note: Elaboration is one of the forms of execution.
	
	5/5		EVALUATION
			process by which an expression achieves its run-time effect
	6/5		Note: Evaluation is one of the forms of execution.
	
	7/5		EXECUTION
			process by which a construct achieves its run-time effect
	8/5		Note: Execution of a declaration is also called elaboration. Execution of an expression is also called evaluation.
	
	9/5		LOGICAL THREAD OF CONTROL
			activity within the execution of a program that can proceed in parallel with other activities of the same task, or of separate tasks

	10/5	MASTER
			execution of a master construct
	11/5	Note: Each object and task is associated with a master. When a master is left, associated tasks are awaited and associated objects are finalized.
	 
	 //*/
	
    @Override
    protected CallTarget parse(ParsingRequest request) throws Exception
    {
        Ada_Node expression_Node = Parser.parse(request.getSource().getReader());
        Ada_Root_Node rootNode = new Ada_Root_Node(expression_Node);
        return rootNode.getCallTarget();
    }

    @Override
    protected Void createContext(Env env)
    {
        return null;
    }

    
    public static NodeInfo lookupNodeInfo(Class<?> clazz)
    {
        if (clazz == null)	{	return null;	}
        NodeInfo info = clazz.getAnnotation(NodeInfo.class);
        if (info != null)	{	return info;	}
		else				{	return lookupNodeInfo(clazz.getSuperclass());	}
    }
    
}


/*/
@TruffleLanguage.Registration(id = "ezs", name = "EasyScript")
public final class EasyScriptTruffleLanguage extends TruffleLanguage<Void> {
    @Override
    protected CallTarget parse(ParsingRequest request) throws Exception {
        EasyScriptNode exprNode = EasyScriptTruffleParser.parse(request.getSource().getReader());
        var rootNode = new EasyScriptRootNode(exprNode);
        return rootNode.getCallTarget();
    }

    @Override
    protected Void createContext(Env env) {
        return null;
    }
}

//*/