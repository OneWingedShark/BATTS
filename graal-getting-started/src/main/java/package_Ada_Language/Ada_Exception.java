package package_Ada_Language;

import static com.oracle.truffle.api.CompilerDirectives.shouldNotReachHere;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.source.SourceSection;
//import com.oracle.truffle.sl.runtime.SLLanguageView;


/**
 * SL does not need a sophisticated error checking and reporting mechanism, so all unexpected
 * conditions just abort execution. This exception class is used when we abort from within the SL
 * implementation.
 */
public class Ada_Exception extends AbstractTruffleException
{

    private static final long serialVersionUID = -6799734410727348507L;
    private static final InteropLibrary UNCACHED_LIB = InteropLibrary.getFactory().getUncached();

    @TruffleBoundary
    public Ada_Exception(String message, Node location)
    {	super(message, location);	}

    /**
     * Provides a user-readable message for run-time type errors. SL is strongly typed, i.e., there
     * are no automatic type conversions of values.
     */
    @TruffleBoundary
    public static Ada_Exception typeError(Node operation, Object... values)
    {
        StringBuilder result = new StringBuilder();
        result.append("Type error");

        if (operation != null)
        {   SourceSection ss = operation.getEncapsulatingSourceSection();
            if (ss != null && ss.isAvailable())
            {	result.append(" at ")
            	.append(ss.getSource().getName())
            	.append(" line ").append(ss.getStartLine())
            	.append(" col ").append(ss.getStartColumn());
            }
        }

        result.append(": operation");
        if (operation != null)
        {	NodeInfo nodeInfo = Ada_Truffle_Language.lookupNodeInfo(operation.getClass());
            if (nodeInfo != null)
            {	result.append(" \"").append(nodeInfo.shortName()).append("\"");	}
        }

        result.append(" not defined for");

        String sep = " ";
        for (Object value : values)
        {
            result.append(sep);
            sep = ", ";
            if (value == null)
            {	result.append("ANY");	}
            else
            {
                InteropLibrary valueLib = InteropLibrary.getFactory().getUncached(value);
                if (valueLib.hasMetaObject(value) && !valueLib.isNull(value)) {
                    String qualifiedName;
                    try
                    {
                        qualifiedName = UNCACHED_LIB.asString(UNCACHED_LIB.getMetaQualifiedName(valueLib.getMetaObject(value)));
                    } catch (UnsupportedMessageException e) {
                      throw shouldNotReachHere(e);
                    }
                    result.append(qualifiedName);
                    result.append(" ");
                }
                if (valueLib.isString(value))	{	result.append("\"");	}
                result.append(valueLib.toDisplayString(value));
                if (valueLib.isString(value))	{	result.append("\"");	}
            }
		}
        
        return new Ada_Exception(result.toString(), operation);
    }

}