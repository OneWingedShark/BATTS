package package_Ada_Language.Typesystem;

public abstract class Access_Type<T extends Ada_Type>
{
	T item;
	
	Access_Type( T value )
	{ this.item = value; }
	
}
