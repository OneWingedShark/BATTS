package package_Ada_Language.Typesystem;
import package_Ada_Language.Passing_Style;

public abstract class Tagged_Type extends Record_Type
{
	//protected Passing_Style passing_style = Passing_Style.BY_REFERENCE;
	
	public static Passing_Style default_passing_style()
	{ return Passing_Style.BY_REFERENCE; }
	
	public Passing_Style get_passing_style( Tagged_Type x )
	{ return Passing_Style.BY_REFERENCE; }
}