package package_Ada_Language.Typesystem;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.graalvm.shadowed.org.jline.reader.impl.DefaultParser.ArgumentList;
import org.graalvm.shadowed.org.jline.utils.AttributedCharSequence.ForceMode;

import package_Ada_Language.Passing_Style;

public abstract class Composite_Type extends Ada_Type
{
	public abstract ArrayList<Ada_Type> components();
	
	
	protected boolean has_reference_component( ArrayList<Ada_Type> components )
	{ for (Ada_Type item : this.components())
		{	if (item.get_passing_style() == Passing_Style.BY_REFERENCE) {return true;};	}
	 return false;
	}
	
	protected boolean has_reference_component()
	{	return this.has_reference_component( this.components() );	}
	
	public Passing_Style get_passing_style( Composite_Type x )
	{ 
		return x.limited ? Passing_Style.BY_REFERENCE :
			(x.has_reference_component() ? Passing_Style.BY_REFERENCE : //Passing_Style.BY_COPY);
				this.default_passing_style() );
	}

	public boolean is_limited()
	{ for (Ada_Type item : this.components())
		{ if(item.is_limited()) { return true; } }
	 return false; 
	}
}
