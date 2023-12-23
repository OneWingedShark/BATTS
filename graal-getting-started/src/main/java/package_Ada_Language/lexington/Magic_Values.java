package package_Ada_Language.lexington;

public final class Magic_Values
{
	public final static int ID_For_Null_Token = Integer.MAX_VALUE;
	public final static int ID_For_Text_Token = ID_For_Null_Token - 1;
	
	
	   // We divide the IDs into classes for both organization and grouping.
	   // An offset is used to partition the groups.
	   public final static int Class_Offset  = 128;

	   // This group is used for error signaling.
	   public final static int Error_Group  = Class_Offset * 0;

	   // This is an intermediate token group containing separators.
	   public final static int Seperators_1  = Class_Offset * 1; // Will not be passed to the Parser.

	   // This group is for separators.
	   public final static int Seperators_2  = Class_Offset * 2; // Might be passed to the Parser.

	   // This group is for literals.
	   public final static int Literal_Group = Class_Offset * 3;

	   // This group is for keywords.
	   public final static int Keywords      = Class_Offset * 4;

	   // This group is for Identifiers.
	   public final static int Ident_Group   = Class_Offset * 5;

}
