package package_Ada_Language.Typesystem;

import package_Ada_Language.Passing_Style;

public abstract class Record_Type extends Composite_Type
{
	
	public Passing_Style get_passing_style()
	{ return this.limited ? Passing_Style.BY_REFERENCE : ((Composite_Type)this).get_passing_style(); }
	
}