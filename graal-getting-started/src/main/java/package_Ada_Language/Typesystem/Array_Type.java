package package_Ada_Language.Typesystem;

import java.util.ArrayList;
import package_Ada_Language.Passing_Style;

public abstract class Array_Type<Component extends Elementry_Type> extends Composite_Type
{

	protected ArrayList<Component> components;
	
	public ArrayList<Ada_Type> components()
	{	return new ArrayList<Ada_Type>( components );	}
	
	public Passing_Style get_passing_style()
	{	return this.limited ? Passing_Style.BY_REFERENCE : Component.default_passing_style();	}
}