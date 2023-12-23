package package_Ada_Language;

import static com.oracle.truffle.api.CompilerDirectives.shouldNotReachHere;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.graalvm.polyglot.Context;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleLanguage.ContextReference;
import com.oracle.truffle.api.TruffleLanguage.Env;
import com.oracle.truffle.api.dsl.NodeFactory;
import com.oracle.truffle.api.instrumentation.AllocationReporter;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.strings.TruffleString;

/*
import com.oracle.truffle.sl.builtins.SLAddToHostClassPathBuiltinFactory;
import com.oracle.truffle.sl.builtins.SLBuiltinNode;
import com.oracle.truffle.sl.builtins.SLDefineFunctionBuiltinFactory;
import com.oracle.truffle.sl.builtins.SLEvalBuiltinFactory;
import com.oracle.truffle.sl.builtins.SLExitBuiltinFactory;
import com.oracle.truffle.sl.builtins.SLGetSizeBuiltinFactory;
import com.oracle.truffle.sl.builtins.SLHasSizeBuiltinFactory;
import com.oracle.truffle.sl.builtins.SLHelloEqualsWorldBuiltinFactory;
import com.oracle.truffle.sl.builtins.SLImportBuiltinFactory;
import com.oracle.truffle.sl.builtins.SLIsExecutableBuiltinFactory;
import com.oracle.truffle.sl.builtins.SLIsInstanceBuiltinFactory;
import com.oracle.truffle.sl.builtins.SLIsNullBuiltinFactory;
import com.oracle.truffle.sl.builtins.SLJavaTypeBuiltinFactory;
import com.oracle.truffle.sl.builtins.SLNanoTimeBuiltinFactory;
import com.oracle.truffle.sl.builtins.SLNewObjectBuiltinFactory;
import com.oracle.truffle.sl.builtins.SLPrintlnBuiltin;
import com.oracle.truffle.sl.builtins.SLPrintlnBuiltinFactory;
import com.oracle.truffle.sl.builtins.SLReadlnBuiltin;
import com.oracle.truffle.sl.builtins.SLReadlnBuiltinFactory;
import com.oracle.truffle.sl.builtins.SLRegisterShutdownHookBuiltinFactory;
import com.oracle.truffle.sl.builtins.SLStackTraceBuiltinFactory;
import com.oracle.truffle.sl.builtins.SLTypeOfBuiltinFactory;
import com.oracle.truffle.sl.builtins.SLWrapPrimitiveBuiltinFactory;
//*/


/**
 * The run-time state of SL during execution. The context is created by the {@link Ada_Truffle_Language}. It
 * is used, for example, by {@link SLBuiltinNode#getContext() builtin functions}.
 * <p>
 * It would be an error to have two different context instances during the execution of one script.
 * However, if two separate scripts run in one Java VM at the same time, they have a different
 * context. Therefore, the context is not a singleton.
 */
public final class Ada_Context
{

    private final Ada_Truffle_Language language;
    @CompilationFinal private Env env;
    private final BufferedReader input;
    private final PrintWriter output;
//    private final SLFunctionRegistry functionRegistry;
    private final AllocationReporter allocationReporter;
    private final List<Object> shutdownHooks = new ArrayList<>(); //private final List<SLFunction> shutdownHooks = new ArrayList<>();

    public Ada_Context(Ada_Truffle_Language language, TruffleLanguage.Env env, List<NodeFactory<? extends Object>> externalBuiltins)
    //(Ada_Truffle_Language language, TruffleLanguage.Env env, List<NodeFactory<? extends SLBuiltinNode>> externalBuiltins)
    {/*
        this.env = env;
        this.input = new BufferedReader(new InputStreamReader(env.in()));
        this.output = new PrintWriter(env.out(), true);
        this.language = language;
        this.allocationReporter = env.lookup(AllocationReporter.class);
        this.functionRegistry = new SLFunctionRegistry(language);
        installBuiltins();
        for (NodeFactory<? extends SLBuiltinNode> builtin : externalBuiltins) {
            installBuiltin(builtin);
        }
    //*/
        this.env = env;
        this.input = new BufferedReader(new InputStreamReader(env.in()));
        this.output = new PrintWriter(env.out(), true);
        this.language = language;
        this.allocationReporter = env.lookup(AllocationReporter.class);
    }

    /**
     * Patches the {@link SLContext} to use a new {@link Env}. The method is called during the
     * native image execution as a consequence of {@link Context#create(java.lang.String...)}.
     *
     * @param newEnv the new {@link Env} to use.
     * @see TruffleLanguage#patchContext(Object, Env)
     */
    public void patchContext(Env newEnv)
    {	this.env = newEnv;	}

    /**
     * Return the current Truffle environment.
     */
    public Env getEnv()
    {	return env;		}

    /**
     * Returns the default input, i.e., the source for the {@link SLReadlnBuiltin}. To allow unit
     * testing, we do not use {@link System#in} directly.
     */
    public BufferedReader getInput()
    {	return input;	}

    /**
     * The default default, i.e., the output for the {@link SLPrintlnBuiltin}. To allow unit
     * testing, we do not use {@link System#out} directly.
     */
    public PrintWriter getOutput()
    {	return output;	}

    /**
     * Returns the registry of all functions that are currently defined.
     */
/*
    public SLFunctionRegistry getFunctionRegistry() {
        return functionRegistry;
    }
//*/
    
    /**
     * Adds all builtin functions to the {@link SLFunctionRegistry}. This method lists all
     * {@link SLBuiltinNode builtin implementation classes}.
     */
    private void installBuiltins()
    { /*
        installBuiltin(SLReadlnBuiltinFactory.getInstance());
        installBuiltin(SLPrintlnBuiltinFactory.getInstance());
        installBuiltin(SLNanoTimeBuiltinFactory.getInstance());
        installBuiltin(SLDefineFunctionBuiltinFactory.getInstance());
        installBuiltin(SLStackTraceBuiltinFactory.getInstance());
        installBuiltin(SLHelloEqualsWorldBuiltinFactory.getInstance());
        installBuiltin(SLNewObjectBuiltinFactory.getInstance());
        installBuiltin(SLEvalBuiltinFactory.getInstance());
        installBuiltin(SLImportBuiltinFactory.getInstance());
        installBuiltin(SLGetSizeBuiltinFactory.getInstance());
        installBuiltin(SLHasSizeBuiltinFactory.getInstance());
        installBuiltin(SLIsExecutableBuiltinFactory.getInstance());
        installBuiltin(SLIsNullBuiltinFactory.getInstance());
        installBuiltin(SLWrapPrimitiveBuiltinFactory.getInstance());
        installBuiltin(SLTypeOfBuiltinFactory.getInstance());
        installBuiltin(SLIsInstanceBuiltinFactory.getInstance());
        installBuiltin(SLJavaTypeBuiltinFactory.getInstance());
        installBuiltin(SLExitBuiltinFactory.getInstance());
        installBuiltin(SLRegisterShutdownHookBuiltinFactory.getInstance());
        installBuiltin(SLAddToHostClassPathBuiltinFactory.getInstance());
    //*/
    }

    public void installBuiltin(NodeFactory<? extends Ada_Node> factory) //(NodeFactory<? extends SLBuiltinNode> factory)
    {
        /* Register the builtin function in our function registry. */
//        RootCallTarget target = language.lookupBuiltin(factory);
//        getFunctionRegistry().register(SLStrings.getSLRootName(target.getRootNode()), target);
    }

    /*
     * Methods for object creation / object property access.
     */
    public AllocationReporter getAllocationReporter()
    {
        return allocationReporter;
    }

    /*
     * Methods for language interoperability.
     */
    public static Object fromForeignValue(Object a)
    {
        if 		(a instanceof Long || a instanceof BigInteger || a instanceof String || 
        		 a instanceof TruffleString || a instanceof Boolean)		{	return a;									}
        else if (a instanceof Character) 									{	return fromForeignCharacter((Character) a);	} 
        else if (a instanceof Number)										{	return fromForeignNumber(a);				}
        else if (a instanceof TruffleObject)								{	return a;									}
        else if (a instanceof Ada_Context)									{	return a;									}
    
        throw shouldNotReachHere("Value is not a truffle value.");
    }

    @TruffleBoundary
    private static long fromForeignNumber(Object a)
    {	return ((Number) a).longValue();	}

    @TruffleBoundary
    private static String fromForeignCharacter(char c)
    {	return String.valueOf(c);	}

    public CallTarget parse(Source source)
    {	return env.parsePublic(source);	}

    /**
     * Returns an object that contains bindings that were exported across all used languages. To
     * read or write from this object the {@link TruffleObject interop} API can be used.
     */
    public TruffleObject getPolyglotBindings()
    {	return (TruffleObject) env.getPolyglotBindings();	}

    private static final ContextReference<Ada_Context> REFERENCE = null; //ContextReference.create(Ada_Truffle_Language.class);

    public static Ada_Context get(Node node)
    {	return REFERENCE.get(node);	}

    /**
     * Register a function as a shutdown hook. Only no-parameter functions are supported.
     *
     * @param func no-parameter function to be registered as a shutdown hook
     */
    @TruffleBoundary
    public void registerShutdownHook(Object func) //public void registerShutdownHook(SLFunction func)
    {	shutdownHooks.add(func);	}

    /**
     * Run registered shutdown hooks. This method is designed to be executed in
     * {@link TruffleLanguage#exitContext(Object, TruffleLanguage.ExitMode, int)}.
     */
    public void runShutdownHooks()
    {	InteropLibrary interopLibrary = InteropLibrary.getUncached();
        for (Object shutdownHook : shutdownHooks)  // (SLFunction shutdownHook : shutdownHooks)
        {
            try
            {	interopLibrary.execute(shutdownHook);	}
            catch (UnsupportedTypeException | ArityException | UnsupportedMessageException e)
            {
                throw shouldNotReachHere("Shutdown hook is not executable!", e);
            }
        }
    }
}