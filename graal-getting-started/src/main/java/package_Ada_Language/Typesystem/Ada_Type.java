package package_Ada_Language.Typesystem;

import package_Ada_Language.Ada_Node;
import package_Ada_Language.Passing_Style;

import com.oracle.truffle.api.dsl.TypeSystem;
import com.oracle.truffle.api.staticobject.StaticShape;

public abstract class Ada_Type extends Ada_Node
{   static final private Passing_Style default_passing_style = Passing_Style.BY_COPY; 
	protected boolean limited = this.is_limited();
	
	
	public static Passing_Style default_passing_style()
	{ return default_passing_style; }
		
	public Passing_Style get_passing_style()
	{ return this.default_passing_style(); }
	
	public boolean is_limited()
	{ return false; }

}