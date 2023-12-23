package package_Ada_Language.lexington;

public class Token implements Comparable<Token>
{
	private String value = "";
	private int    id    = Magic_Values.ID_For_Null_Token;
	
	public Token(int id, String value)
	{
		this.id    = id;
		this.value = value;
	}

	public Token(Token_ID id, String value)
	{  
		this.id    = id.ID();
		this.value = value;		
	}
	
	public String Lexeme()
	{  return this.value;  }
	
	public String Print()
	{  return Token_ID.ID(this.id).toString()  + ": " + this.Lexeme(); }
			//this.id + ": " + this.Lexeme(); }

	public Token_ID ID()	{  return Token_ID.ID(this.id);  }
	
	
	public int compareTo(Token that) {
             if (this.id == that.id) { return  0; }
        else if (this.id > that.id)  { return  1; }
        else                         { return -1; }
    }
	
}
