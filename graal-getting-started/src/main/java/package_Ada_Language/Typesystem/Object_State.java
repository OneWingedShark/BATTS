/**
 * 
 */
package package_Ada_Language.Typesystem;

/**
 * 
 */
public enum Object_State
{
	
	POSSIBLY_UNINITIALIZED,
	KNOWN_INITIALIZED
	;
	
    
    public static boolean is_initialized(Object_State obj)
    { return obj != POSSIBLY_UNINITIALIZED; }
//*/
}
