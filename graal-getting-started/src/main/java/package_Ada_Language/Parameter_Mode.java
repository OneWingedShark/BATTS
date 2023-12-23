package package_Ada_Language;

public enum Parameter_Mode
{
	MODE_IN,
	MODE_INOUT,
	MODE_OUT;
	
	static boolean readable( Parameter_Mode x )
	{ return x == MODE_IN | x == MODE_INOUT; }
	
	static boolean eritable( Parameter_Mode x )
	{ return x == MODE_OUT | x == MODE_INOUT; }
}
