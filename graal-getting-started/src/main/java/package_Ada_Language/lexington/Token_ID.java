package package_Ada_Language.lexington;

public enum Token_ID
{
    UNKNOWN             (Integer.MIN_VALUE),
    
    ///////////////////////////
    //  Seperators, Group 1  //
    ///////////////////////////

    // Artifacts of text/files, these should not be passed to the parser.
    Whitespace			(Magic_Values.Seperators_1 +  0),
    Comment				(Magic_Values.Seperators_1 +  1),
    End_of_Line			(Magic_Values.Seperators_1 +  2),

    // Single character seperators, should not be passed to the parser.
    ch_Ampersand		(Magic_Values.Seperators_1 +  3),
    ch_Astrisk			(Magic_Values.Seperators_1 +  4),
    ch_Apostrophy		(Magic_Values.Seperators_1 +  5),
    ch_Open_Paren		(Magic_Values.Seperators_1 +  6),
    ch_Close_Paren		(Magic_Values.Seperators_1 +  7),
    ch_Plus				(Magic_Values.Seperators_1 +  8),
    ch_Comma			(Magic_Values.Seperators_1 +  9),
    ch_Dash				(Magic_Values.Seperators_1 + 10),
    ch_Slash			(Magic_Values.Seperators_1 + 11),
    ch_Colon			(Magic_Values.Seperators_1 + 12),
    ch_Semicolon		(Magic_Values.Seperators_1 + 13),
    ch_Less_Than		(Magic_Values.Seperators_1 + 14),
    ch_Greater_Than		(Magic_Values.Seperators_1 + 15),
    ch_Equal			(Magic_Values.Seperators_1 + 16),
    ch_Quote			(Magic_Values.Seperators_1 + 17),
    ch_Period			(Magic_Values.Seperators_1 + 18),
    ch_Vertical_Line	(Magic_Values.Seperators_1 + 19),

    ///////////////////////////
    //  Seperators, Group 2  //
    ///////////////////////////

    // Meaningful seperators, may be passed to the parser.
    ss_Assign			(Magic_Values.Seperators_2 +  0),
    ss_Arrow			(Magic_Values.Seperators_2 +  1),
    ss_Open_Label		(Magic_Values.Seperators_2 +  2),
    ss_Close_Label		(Magic_Values.Seperators_2 +  3),
    ss_Dillipsis		(Magic_Values.Seperators_2 +  4),  //> A two/dot ellipsis.
    ss_Exponent			(Magic_Values.Seperators_2 +  5),
    ss_Not_Equal		(Magic_Values.Seperators_2 +  6),
    ss_Greater_Equal	(Magic_Values.Seperators_2 +  7),
    ss_Less_Equal		(Magic_Values.Seperators_2 +  8),
    ss_Box				(Magic_Values.Seperators_2 +  9),
    ss_Tick				(Magic_Values.Seperators_2 + 10),

    // Other seperators; Conceptually Non/semantic.
    ns_Open_Paren		(Magic_Values.Seperators_2 + 11),
    ns_Close_Paren		(Magic_Values.Seperators_2 + 12),
    ns_Comma			(Magic_Values.Seperators_2 + 13),
    ns_Colon			(Magic_Values.Seperators_2 + 14),
    ns_Semicolon		(Magic_Values.Seperators_2 + 15),
    ns_Period			(Magic_Values.Seperators_2 + 16),
    ns_Alternitive		(Magic_Values.Seperators_2 + 17),

    ////////////////
    //  Literals  //
    ////////////////

    // Literals, which are passed to the parser.
    li_String			(Magic_Values.Literal_Group +  0),
    li_Rational			(Magic_Values.Literal_Group +  1),
    li_Integer			(Magic_Values.Literal_Group +  2),
    li_Character		(Magic_Values.Literal_Group +  3),
    li_Float			(Magic_Values.Literal_Group +  4),
    li_Based_Integer	(Magic_Values.Literal_Group +  5),
    li_Based_Float		(Magic_Values.Literal_Group +  6),


//////  Keywords:	Reserved words as per ARM 2.9 (2).  //////////////////

    ////////////////
    //  Keywords  //
    ////////////////

    kw_Abort			(Magic_Values.Keywords +  0),
    kw_Abs				(Magic_Values.Keywords +  1),
    kw_Abstract			(Magic_Values.Keywords +  2),
    kw_Accept			(Magic_Values.Keywords +  3),
    kw_Access			(Magic_Values.Keywords +  4),
    kw_Aliased			(Magic_Values.Keywords +  5),
    kw_All				(Magic_Values.Keywords +  6),
    kw_And				(Magic_Values.Keywords +  7),
    kw_Array			(Magic_Values.Keywords +  8),
    kw_At				(Magic_Values.Keywords +  9),
    kw_Begin			(Magic_Values.Keywords + 10),
    kw_Body				(Magic_Values.Keywords + 11),
    kw_Case				(Magic_Values.Keywords + 12),
    kw_Constant			(Magic_Values.Keywords + 13),
    kw_Declare			(Magic_Values.Keywords + 14),
    kw_Delay			(Magic_Values.Keywords + 15),
    kw_Delta			(Magic_Values.Keywords + 16),
    kw_Digits			(Magic_Values.Keywords + 17),
    kw_Do				(Magic_Values.Keywords + 18),
    kw_Else				(Magic_Values.Keywords + 18),
    kw_Elsif			(Magic_Values.Keywords + 20),
    kw_End				(Magic_Values.Keywords + 21),
    kw_Entry			(Magic_Values.Keywords + 22),
    kw_Exception		(Magic_Values.Keywords + 23),
    kw_Exit				(Magic_Values.Keywords + 24),
    kw_For				(Magic_Values.Keywords + 25),
    kw_Function			(Magic_Values.Keywords + 26),
    kw_Generic			(Magic_Values.Keywords + 27),
    kw_Goto				(Magic_Values.Keywords + 28),
    kw_If				(Magic_Values.Keywords + 29),
    kw_In				(Magic_Values.Keywords + 30),
    kw_Interface		(Magic_Values.Keywords + 31),
    kw_Is				(Magic_Values.Keywords + 32),
    kw_Limited			(Magic_Values.Keywords + 33),
    kw_Loop				(Magic_Values.Keywords + 34),
    kw_Mod				(Magic_Values.Keywords + 35),
    kw_New				(Magic_Values.Keywords + 36),
    kw_Not				(Magic_Values.Keywords + 37),
    kw_Null				(Magic_Values.Keywords + 38),
    kw_Of				(Magic_Values.Keywords + 39),
    kw_Or				(Magic_Values.Keywords + 40),
    kw_Others			(Magic_Values.Keywords + 41),
    kw_Out				(Magic_Values.Keywords + 42),
    kw_Overriding		(Magic_Values.Keywords + 43),
    kw_Package			(Magic_Values.Keywords + 44),
    kw_Parallel			(Magic_Values.Keywords + 45),
    kw_Pragma			(Magic_Values.Keywords + 46),
    kw_Private			(Magic_Values.Keywords + 47),
    kw_Procedure		(Magic_Values.Keywords + 48),
    kw_Protected		(Magic_Values.Keywords + 49),
    kw_Raise			(Magic_Values.Keywords + 50),
    kw_Range			(Magic_Values.Keywords + 51),
    kw_Record			(Magic_Values.Keywords + 52),
    kw_Rem				(Magic_Values.Keywords + 53),
    kw_Renames			(Magic_Values.Keywords + 54),
    kw_Requeue			(Magic_Values.Keywords + 55),
    kw_Return			(Magic_Values.Keywords + 56),
    kw_Reverse			(Magic_Values.Keywords + 57),
    kw_Select			(Magic_Values.Keywords + 58),
    kw_Separate			(Magic_Values.Keywords + 59),
    kw_Some				(Magic_Values.Keywords + 60),
    kw_Subtype			(Magic_Values.Keywords + 61),
    kw_Synchronized		(Magic_Values.Keywords + 62),
    kw_Tagged			(Magic_Values.Keywords + 63),
    kw_Task				(Magic_Values.Keywords + 64),
    kw_Terminate		(Magic_Values.Keywords + 65),
    kw_Then				(Magic_Values.Keywords + 66),
    kw_Type				(Magic_Values.Keywords + 67),
    kw_Until			(Magic_Values.Keywords + 68),
    kw_Use				(Magic_Values.Keywords + 69),
    kw_When				(Magic_Values.Keywords + 70),
    kw_While			(Magic_Values.Keywords + 71),
    kw_With				(Magic_Values.Keywords + 72),
    kw_Xor				(Magic_Values.Keywords + 73),

    ///////////////////////////////
    //  Identifiers & Operators  //
    ///////////////////////////////

    op_Mul				(Magic_Values.Ident_Group + 0),
    op_Div				(Magic_Values.Ident_Group + 1),
    op_Add				(Magic_Values.Ident_Group + 2),
    op_Sub				(Magic_Values.Ident_Group + 3),
    op_Concat			(Magic_Values.Ident_Group + 4),
    op_Less_Than		(Magic_Values.Ident_Group + 5),
    op_Greater_Than		(Magic_Values.Ident_Group + 6),
    op_Equal			(Magic_Values.Ident_Group + 7),
    op_Function			(Magic_Values.Ident_Group + 8),
    Identifier			(Magic_Values.Ident_Group + 9),

    Comment_Info		(Magic_Values.ID_For_Text_Token - 4),		// The typical "box comment" for package/info//headers.
    Comment_Section		(Magic_Values.ID_For_Text_Token - 3),		// A separator//header/style comment. (A 1/line box.)
    Comment_Block		(Magic_Values.ID_For_Text_Token - 2),		// The normal "multi/line" comment.
    Comment_Code		(Magic_Values.ID_For_Text_Token - 1),		// Code disabled by comments; perhaps direct AST manip.

    Text 				(Magic_Values.ID_For_Text_Token),
    Nil  				(Magic_Values.ID_For_Null_Token);

    Token_ID(int value) { this.value = value; }
    
    private int value;

    //public String toString() {  return Integer.toString(value);  }
    public int      ID()     {  return this.value;               }

    public static Token_ID ID(int x)
    {
    	for (Token_ID val: Token_ID.values())
    	{ if ( val.ID() == x ) return val; }
    	return UNKNOWN;
    }

    public static Token_ID next(Token_ID x)
    { for (int i = x.ID()+1; i <= Token_ID.Nil.ID(); i++)
      {if ( Token_ID.ID(i) != UNKNOWN ) return Token_ID.ID(i);}
      return UNKNOWN;
    }
    
    public static Token_ID pred(Token_ID x)
    { for (int i = x.ID()-1; i >= Token_ID.Whitespace.ID(); --i)
    	{if ( Token_ID.ID(i) != UNKNOWN ) return Token_ID.ID(i);}
      return UNKNOWN;	
    }
}
