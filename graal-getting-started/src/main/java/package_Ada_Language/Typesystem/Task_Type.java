package package_Ada_Language.Typesystem;

import package_Ada_Language.Passing_Style;

public abstract class Task_Type extends Composite_Type
{
	public Passing_Style get_passing_style( Task_Type x )
	{ return Passing_Style.BY_REFERENCE; }
	
	public boolean is_limited()
	{ return true; }
}
