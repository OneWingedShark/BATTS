package package_Ada_Language.lexington;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import org.graalvm.collections.Pair;
import org.graalvm.shadowed.org.jcodings.specific.UTF32LEEncoding;
import org.graalvm.shadowed.org.jcodings.unicode.UnicodeEncoding;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleString.CodePointSet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.rmi.server.Operation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Lexer
{
	
	private int is_in(String text, String[] items)
	{int index = Integer.MAX_VALUE;
		for (int i = 0; i < items.length; i++) 
		{ int tmp = text.indexOf(items[i]);
			index = Integer.min(tmp == -1 ? Integer.MAX_VALUE : tmp, index);
		}			

		return index == Integer.MAX_VALUE ? -1 : index;
	} //*/

	/*
	//private ArrayList<Token> result = new ArrayList<Token>();
	private ArrayList<Token> split_text(Token t, String trigger, Token_ID result)
	{	ArrayList<Token> working = new ArrayList<Token>();
		final String text = t.Lexeme();
		int start = 0;
		
			for (int index = text.indexOf(trigger); start != 0; index++) //index < 0; index = text.indexOf(trigger, start))
			{
				String prefix = text.substring(start, index-1);
				if (prefix.length() > 0) { working.add(  new Token(t.ID(), prefix)  ); }
				start = index = trigger.length() - 1;
				working.add(  new Token(result, text.substring(index, start)) );
			}
		
		String postfix = text.substring(start);
		if (postfix.length() > 0)  { working.add(  new Token(t.ID(), postfix)  ); }

		return working;
	}//*/

	
	
	public String Read_File( FileInputStream fs )
	{	String result = "";
		UnicodeReader ur = null;
			//InputStreamReader isr = new InputStreamReader(fs, "UTF-8"); //"UTF-32LE");
		try
		{try 
			{ur = new UnicodeReader(fs, "UTF-8");
				result = ur.toString();
			}
			finally
			{
				if (ur != null) ur.close();
				fs.close();
			}	
		} catch (IOException e) { e.printStackTrace(); }
		
		return result;
	}
	
	public ArrayList<Token> Make_Text( String input )
	{	return new ArrayList<Token>(Collections.singletonList(new Token(Token_ID.Text, input)));	}
	
	/** Pass_1:
	 * Populates whitespace characters.
	 */
	public ArrayList<Token> Pass_1( ArrayList<Token> input )
	{	final ArrayList<Token>  result		= new ArrayList<Token>();
	
		input.forEach(
				new Consumer<Token>() {
					final char nbsp  = (char) 160;
					final char space = ' ';
					final char tilde = '~';
					final char wc256 = (char) 256;
					final char wclst = Character.MAX_VALUE;
					
					private boolean in_range(char c, char low, char high)
					{ return (low <= c && c <= high); }
					private boolean Non_Whitespace( char c )
					{
						return  (in_range(c, space, tilde) || in_range(c, wc256, wclst))
								&& (c != space) && (c != nbsp);
					}
					
					
					@Override
					public void accept(Token item)
					{
						if ( item.ID() == Token_ID.Text)
						{	final String text = item.Lexeme();
							StringBuilder sb = new StringBuilder();

							/* we iterate through the text of the token, collecting non-whitespace characters
							   into the string-builder, when we hit a whitespace character we have to emit
							   that into a text-token, then emit the whitespace-token, emptying the string-
							   builder collection.
							 */
							for (char c : text.toCharArray()) {
								if (Non_Whitespace(c)) { sb.append(c); }
								else {
									if (sb.length() > 0)
									{ result.add( new Token(Token_ID.Text, sb.toString()) ); 
									  sb.delete(0, sb.length());
									}
									sb.append(c);
									result.add( new Token(Token_ID.Whitespace, sb.toString() ) );
									sb.delete(0,1);
								}
							} // for
							
							if (sb.length() > 0) { result.add( new Token(Token_ID.Text, sb.toString()) ); }
						} // if
				} //accept
				} // consumer class
			);
		//ArrayList<String> parts = new ArrayList<>(Arrays.asList(textField.getText().split(",")));
		//Spliterators.
		//ArrayList<Token> JJ = new ArrayList<>( (List)input.spliterator(  ) );
	
		return result;
	}

	public final static char CR  = '\u005Cr'; // (char) 0x0D;
	public final static char LF  = '\u005Cn'; // (char) 0x0A; 
	
	private boolean is_CR( Token t )
	{	return t.Lexeme().equals( Character.toString(CR) );	}
	private boolean is_LF( Token t )
	{	return t.Lexeme().equals( Character.toString(LF) );	}

	/** Pass_2:
	 * Populates End-of-Line tokens.
	 */
	public ArrayList<Token> Pass_2( ArrayList<Token> input )
	{	final ArrayList<Token>  result		= new ArrayList<Token>();
		final String			CRLF 		= Character.toString(CR) + Character.toString(LF);
			  ArrayList<Token>  working		= new ArrayList<Token>();

		/***************************************
		 ** TESTING WHITESPACE TO END_OF_LINE **
		 ***************************************/
		//input.removeIf( new Predicate<Token>() {@Override public boolean test(Token t) {  return t.ID()!=Token_ID.Whitespace;  }});
		
		// Take care of RFC822-style end-of-lines. (CRLF)
		for (int i = 0; i < input.size(); i++) 
		{	Token item = input.get(i);
			if (item.ID() == Token_ID.Whitespace)
			{	 if (item.Lexeme().equals(CRLF)) { working.add( new Token(Token_ID.End_of_Line, "CRLF") );}
			else if (is_CR(item) && i<(input.size()-1) )
				{Token next = input.get(i+1);
				 if (is_LF(next)) {working.add( new Token(Token_ID.End_of_Line, "CRLF") ); ++i;}
				  else { working.add(item); }
				} else { working.add(item); }
			}
			else
			{ // Token is not whitespace
				working.add(item);
			}
		}

		// Handle Apple and Unix style end of line.
		working.forEach( new Consumer<Token>() {
			@Override
			public void accept(Token item){
				if (item.ID() != Token_ID.Whitespace)
				{result.add(item);}
				else
					     if (is_CR(item)) { result.add( new Token(Token_ID.End_of_Line, "CR") );}
					else if (is_LF(item)) { result.add( new Token(Token_ID.End_of_Line, "LF") );}
					else result.add(item);
			}
		} );
		
		return result;
	}
	
	static boolean is_comment(Token item)
	{	final String text = item.Lexeme(); 
	    final boolean possible = item.ID() == Token_ID.Text && text.length() >= 2; 
		return possible ? (text.charAt(0) == '-') && (text.charAt(1) == '-') : false;
	}
	
	/** Pass_3:
	 * handles comments starting text-token.
	 */
	public ArrayList<Token> Pass_3( ArrayList<Token> input )
	{	final ArrayList<Token>  result		= new ArrayList<Token>();
		//final ArrayList<Token>  working		= new ArrayList<Token>();
		final StringBuilder     working		= new StringBuilder();
		
		input.add( new Token(Token_ID.Nil, "") );
		//if (item.ID == Token_ID.Text) {marked = true};
		input.forEach( new Consumer<Token>() {
			boolean marked = false;
			@Override
			public void accept(Token item)
			{
				if (marked) 
				{switch (item.ID()) {
				 case End_of_Line:
				 case Nil:
					 result.add( new Token(Token_ID.Comment, working.toString()) );
					 //result.add(item);
					 working.delete(0, working.length());
					 marked = false;
					 break;
				 default:
					 working.append( item.Lexeme() );
					 break;
				}  }
				else /* if NOT marked. */
				{	if (is_comment(item))
					{ marked = true; working.append( item.Lexeme()/*.substring(2)*/ ); }
					else
					{ result.add(item); }
				}
			}
		}  );
		
		/*
		boolean marked = false;
		for (int i = 0; i < input.size(); i++) 
		{
			Token item = input.get(i);
			if (is_comment(item) && !marked)
	 		{ marked = true; 
	 		  working.add( new Token(Token_ID.Comment, item.Lexeme().substring(2) ) );
	 		} else if ( marked && (item.ID() == Token_ID.End_of_Line || input.size()-1 == i) ) 
	 		{ marked = false; working.add(item); }
	 		else working.add(item);
		}
		
		working.add( new Token(Token_ID.Nil,"") );
		working.forEach( new Consumer<Token>(){			
			
			boolean marked = false;
			StringBuilder working = new StringBuilder();
			@Override
			public void accept(Token item)
		 	{	
				     if (!marked && item.ID() != Token_ID.Comment)
		 		{ result.add(item); }
		 		else if (!marked && item.ID() == Token_ID.Comment)
		 		{ marked = true;  working.append(item.Lexeme()); }
		 		else if ( marked && item.ID() == Token_ID.Comment)
		 		{} // { /throw new Exception("New comment started before old comment was closed.");/ }
		 		else if ( marked && item.ID() == Token_ID.End_of_Line)
		 		{ marked = false; result.add( new Token(Token_ID.Comment, working.toString()) ); working.delete(0, working.length());}
		 		else if ( marked && item.ID() == Token_ID.Nil)
		 		{ marked = false; result.add( new Token(Token_ID.Comment, working.toString()) ); working.delete(0, working.length());}
		 		else
		 		{ working.append( item.Lexeme() ); }
		 }}
		);//*/
	
		return result;
	} /* Pass_3 */
	
	
	public ArrayList<Token> Pass_4( ArrayList<Token> input )
	{	final ArrayList<Token>  result		= new ArrayList<Token>();
			  //ArrayList<Token>  working		= new ArrayList<Token>();

		final Map<Character, Token_ID> delimiter = new HashMap<Character, Token_ID>();
		{
			delimiter.put( '&',  Token_ID.ch_Ampersand );
			delimiter.put( '*',  Token_ID.ch_Astrisk );
			delimiter.put( '\'', Token_ID.ch_Apostrophy );
			delimiter.put( '(',  Token_ID.ch_Open_Paren );
			delimiter.put( ')',  Token_ID.ch_Close_Paren );
			delimiter.put( '+',  Token_ID.ch_Plus );
			delimiter.put( ',',  Token_ID.ch_Comma );
			delimiter.put( '-',  Token_ID.ch_Dash );
			delimiter.put( '/',  Token_ID.ch_Slash );
			delimiter.put( ':',  Token_ID.ch_Colon );
			delimiter.put( ';',  Token_ID.ch_Semicolon );
			delimiter.put( '<',  Token_ID.ch_Less_Than );
			delimiter.put( '>',  Token_ID.ch_Greater_Than );
			delimiter.put( '=',  Token_ID.ch_Equal );
			delimiter.put( '"',  Token_ID.ch_Quote );
			delimiter.put( '.',  Token_ID.ch_Period );
			delimiter.put( '|',  Token_ID.ch_Vertical_Line );
		}; 


		
		input.forEach( new Consumer<Token>() {
			@Override
			public void accept(Token item) {
				if (item.ID() != Token_ID.Text) 
				{  result.add(item);  }
				else 
				{ //final ArrayList<Token>  working = new ArrayList<Token>(); //Collections.singletonList(item));
				  final StringBuilder buffer = new StringBuilder();
				  final String lex = item.Lexeme();
				  for (int i = 0; i < lex.length(); i++)
				  {   final char ch = lex.charAt(i);
					  if ( delimiter.containsKey( ch ) )
					  {
						  if (buffer.length() > 0) { result.add( new Token(Token_ID.Text, buffer.toString()) ); buffer.delete(0, buffer.length()); }
						  result.add( new Token( delimiter.get(ch), ""+lex.charAt(i) ) );
					  }
					  else
					  { buffer.append(ch); }
				  }
				  if (buffer.length() > 0) { result.add( new Token(Token_ID.Text, buffer.toString()) ); buffer.delete(0, buffer.length()); }
/*
				  delimiter.forEach( new BiConsumer<Character, Token_ID>() {
					@Override
					public void accept(Character k, Token_ID value) {
						working.forEach( element -> if ( item.Lexeme().inde ) 
								);
						result.add( new Token( delimiter.get('l'), "" ) );
					 }
				   } );
*/
				} //else
			}
		} );
		
		
		
		/*
		delimiter.forEach( new Consumer<>(){			
			
			boolean marked = false;
			StringBuilder working = new StringBuilder();
			@Override
			public void accept(Token item)
		 	{	
				     if (!marked && item.ID() != Token_ID.Comment)
		 		{ result.add(item); }
		 		else if (!marked && item.ID() == Token_ID.Comment)
		 		{ marked = true;  working.append(item.Lexeme()); }
		 		else if ( marked && item.ID() == Token_ID.Comment)
		 		{  }
		 		else if ( marked && item.ID() == Token_ID.End_of_Line)
		 		{ marked = false; result.add( new Token(Token_ID.Comment, working.toString()) ); working.delete(0, working.length());}
		 		else if ( marked && item.ID() == Token_ID.Nil)
		 		{ marked = false; result.add( new Token(Token_ID.Comment, working.toString()) ); working.delete(0, working.length());}
		 		else
		 		{ working.append( item.Lexeme() ); }
		 }}
		);
		//*/
		
		return result;
	}
	
	/** PASS_5:
	 *  Produce the two-character delimiters.
	 */
	public ArrayList<Token> Pass_5( ArrayList<Token> input )
	{	final ArrayList<Token>  result		= new ArrayList<Token>();
	
		for (int i = 0; i < input.size()-1; i++) 
		{	Token item = input.get(i);
		    Token next = input.get(i+1);
			switch (item.ID()) {
				case ch_Less_Than: 
					switch (next.ID()) {
						case ch_Less_Than:
							result.add( new Token(Token_ID.ss_Open_Label, item.Lexeme()+next.Lexeme()) ); input.remove(i+1);	break;
						case ch_Equal:
							result.add( new Token(Token_ID.ss_Less_Equal, item.Lexeme()+next.Lexeme()) ); input.remove(i+1);	break;
						case ch_Greater_Than:
							result.add( new Token(Token_ID.ss_Box, item.Lexeme()+next.Lexeme()) ); input.remove(i+1);	break;
						default:
							result.add(item);
						break;
					}
				break;
				case ch_Greater_Than:
					switch (next.ID()) {
						case ch_Greater_Than:
							result.add( new Token(Token_ID.ss_Close_Label, item.Lexeme()+next.Lexeme()) ); input.remove(i+1);	break;
						case ch_Equal:
							result.add( new Token(Token_ID.ss_Greater_Equal, item.Lexeme()+next.Lexeme()) ); input.remove(i+1);	break;
						default:
							result.add(item);
						break;
					}
				break;
				case ch_Slash: 
					switch (next.ID()) {
						case ch_Equal:
							result.add( new Token(Token_ID.ss_Not_Equal, item.Lexeme()+next.Lexeme()) ); input.remove(i+1);	break;
						default:
							result.add(item);
						break;
					}
				break;
				case ch_Colon: 
					switch (next.ID()) {
						case ch_Equal:
							result.add( new Token(Token_ID.ss_Assign, item.Lexeme()+next.Lexeme()) ); input.remove(i+1);	break;
						default:
							result.add(item);
						break;
					}
				break;
				case ch_Astrisk: 
					switch (next.ID()) {
						case ch_Astrisk:
							result.add( new Token(Token_ID.ss_Exponent, item.Lexeme()+next.Lexeme()) ); input.remove(i+1);	break;
						default:
							result.add(item);
						break;
					}
				break;
				case ch_Equal: 
					switch (next.ID()) {
						case ch_Greater_Than:
							result.add( new Token(Token_ID.ss_Arrow, item.Lexeme()+next.Lexeme()) ); input.remove(i+1);	break;
						default:
							result.add(item);
						break;
					}
				break;
				case ch_Period: 
					switch (next.ID()) {
						case ch_Period:
							result.add( new Token(Token_ID.ss_Dillipsis, item.Lexeme()+next.Lexeme()) ); input.remove(i+1);	break;
						default:
							result.add(item);
						break;
					}
				break;
			default:
				result.add(item);
				break;
			}
		}
	
		return result;
	}	
	
	private int search_index( ArrayList<Token> input, int from, Token_ID id )
	{ int result = -1;
		
		for (int i = from; i < input.size(); i++)
		{	Token item = input.get(i);
			if (item.ID() == id) { result = i; break;}
		}
		
		return result;
	}
	
	private boolean is_QorA( Token item )
	{	return (item.ID() == Token_ID.ch_Apostrophy || item.ID() == Token_ID.ch_Quote);		}
	
	/** PASS_6
	 * Produces li_Character ONLY for apostrophe and quote.
	 */
	public ArrayList<Token> Pass_6( ArrayList<Token> input )
	{	ArrayList<Token>  result		= input;
		int start_index = 0;
		int found_index;
		
		while ( (found_index = search_index(result, start_index, Token_ID.ch_Apostrophy)) >= 0 )
		{
				final boolean in_range = found_index <= result.size()-1;
				if (in_range)
				{
					final Token second = result.get(found_index+1); 
					final Token third  = result.get(found_index+2);
					if (is_QorA(second) && (third.ID() == Token_ID.ch_Apostrophy) )
					{ 	final char lit_chr = (second.ID() == Token_ID.ch_Apostrophy) ? '\'': '"';
						final Token literal = new Token(Token_ID.li_Character, ""+lit_chr);
						result.set( found_index, literal );
					  result.remove(found_index+1);
					  result.remove(found_index+1);
					}
				}
				start_index = found_index + 1;
		} // while
		
		return result;
	}
	

	/** PASS_7:
	 * Produce literal string (li_String) tokens.
	 */
	public ArrayList<Token> Pass_7( ArrayList<Token> input )
	{	final ArrayList<Token>  result		= new ArrayList<Token>();
		final StringBuilder		working		= new StringBuilder();
		input.add( new Token(Token_ID.Nil, "") );
		
		boolean escaped_quote = false;
		boolean in_string     = false;		
		for (int i = 0; i < input.size()-1; i++) {
			final Token item = input.get(i);
			final boolean is_quote = item.ID() == Token_ID.ch_Quote;
			escaped_quote = input.get(i+1).ID() == Token_ID.ch_Quote;
			
			if (in_string)
					switch (item.ID()) 
					{
					case ch_Quote:		if (escaped_quote)	{working.append('"'); i=i+1;}
										else
										{	in_string = false; 
											result.add( new Token(Token_ID.li_String, working.toString()) );
											working.delete(0, working.length());
										}; 
																				break;
					case li_Character:  working.append("'''");
																				break;
					case Text:
					case Whitespace:
					case ch_Ampersand:
					case ch_Astrisk:
					case ch_Apostrophy:
					case ch_Open_Paren:
					case ch_Close_Paren:
					case ch_Plus:
					case ch_Comma:
					case ch_Dash:
					case ch_Slash:
					case ch_Colon:
					case ch_Semicolon:
					case ch_Less_Than:
					case ch_Greater_Than:
					case ch_Equal:
					case ch_Period:
					case ch_Vertical_Line:
					case ss_Assign:
					case ss_Arrow:
					case ss_Open_Label:
					case ss_Close_Label:
					case ss_Dillipsis:
					case ss_Exponent:
					case ss_Not_Equal:
					case ss_Greater_Equal:
					case ss_Less_Equal:
					case ss_Box:		working.append( item.Lexeme() );
																				break;
					default:
						System.out.println( "ID " +item.ID().toString()+ " is unsupported." );
						break;
					}
			else // when not in string
				if (!is_quote)	{ result.add(item); }
				else			{ in_string = true; }
				
			
		}

		return result;
	}
	
	@SuppressWarnings("unused")
	private int dec_msg( final int count, final String msg ) throws Exception
	{
		if (count < 0) throw new Exception( "Counter exceeded: " + msg );
		else return count-1;
	}
	
	/** Pass_8:
	 * Gather comments.
	 * @throws Exception 
	 */
	public ArrayList<Token> Pass_8( ArrayList<Token> input ) //throws Exception
	{	final ArrayList<Token>  result		= new ArrayList<Token>();
		ArrayList<Token> temp;
		ArrayList<Pair<Integer, Integer>> pair_list = new ArrayList<Pair<Integer, Integer>>();
		final StringBuilder working = new StringBuilder();
		Pair<Integer, Integer> pair;
		int start_index = -1;
		int stop_index;

		
		//Gather Comments
		while ( -1 < (start_index = search_index(input, start_index+1, Token_ID.ch_Dash)) ) 
		{
		  if( input.get(start_index+1).ID() == Token_ID.ch_Dash )
			{	
				stop_index = search_index(input, start_index, Token_ID.End_of_Line);
				pair = Pair.create(start_index, stop_index);
				pair_list.add( pair );
			}
		  else { stop_index = start_index+1; }
		  start_index=stop_index+1;
		}
		
		
		// Scan Data
		int last_index =  input.size()-1;
		while ( pair_list.size() > 0 )
		{
			pair = pair_list.get(pair_list.size()-1);
			// Exclude the double-dash and end-of-line.
			start_index = pair.getLeft()+2;
			stop_index  = pair.getRight()-1;
			
			// Copy non-comment data.
			temp = new ArrayList<Token>( input.subList(stop_index+2,last_index) );
			Collections.reverse(temp);
			temp.forEach(new Consumer<Token>() {
				@Override
				public void accept(Token item) {
					result.add(0, item);
				}
			});
			
			
			//Create comment's text.
			temp.clear();
			temp = new ArrayList<Token>( input.subList(start_index,stop_index) );
			Collections.reverse(temp);
			temp.forEach(new Consumer<Token>() {
				@Override
				public void accept(Token item) {
					switch (item.ID()) {
					case Text:
					case Whitespace:
					case ch_Ampersand:
					case ch_Astrisk:
					case ch_Apostrophy:
					case ch_Open_Paren:
					case ch_Close_Paren:
					case ch_Plus:
					case ch_Comma:
					case ch_Dash:
					case ch_Slash:
					case ch_Colon:
					case ch_Semicolon:
					case ch_Less_Than:
					case ch_Greater_Than:
					case ch_Equal:
					case ch_Quote:
					case ch_Period:
					case ch_Vertical_Line:
					case ss_Assign:
					case ss_Arrow:
					case ss_Open_Label:
					case ss_Close_Label:
					case ss_Dillipsis:
					case ss_Exponent:
					case ss_Not_Equal:
					case ss_Greater_Equal:
					case ss_Less_Equal:
					case ss_Box:			working.insert(0,      item.Lexeme()     );		break;
					case li_Character:		working.insert(0, "'"+ item.Lexeme() +"'");		break;
					case li_String:			working.insert(0, '"'+ item.Lexeme() +'"');		break;
					default:				working.insert(0,  item.ID().toString()  );		break;
					}
				}
			});
			result.add(0, new Token(Token_ID.Comment, working.toString()));
			working.delete(0, working.length());
			
			last_index = pair.getLeft() - 1;
			pair_list.remove( pair_list.size()-1 );
		} //while
	
		temp = new ArrayList<Token>( input.subList(0, last_index) );
		Collections.reverse(temp);
		temp.forEach( new Consumer<Token>() { @Override public void accept(Token item) {	result.add(0, item);	} } );
		
		return result;
	}

	/** Pass_9:
	 * Generate keywords; 
	 */
	public ArrayList<Token> Pass_9( ArrayList<Token> input )
	{	final ArrayList<Token>  result		= new ArrayList<Token>();
		
		input.forEach(
			new Consumer<Token>() {
				@Override
				public void accept(Token t)
				{
					if ( t.ID() != Token_ID.Text) {result.add(t);}
					else
					{
						Token_ID keyword = Token_ID.UNKNOWN;
						for (Token_ID i = Token_ID.kw_Abort; i != Token_ID.next(Token_ID.kw_Xor); i = Token_ID.next(i))
						{ if (0 == i.toString().compareToIgnoreCase( "kw_" + t.Lexeme() ))
							{ keyword = i; break; };
						}

						if (keyword != Token_ID.UNKNOWN)
							result.add( new Token(keyword, t.Lexeme()) );
						else
							result.add(t);
					}
			
				}
			}
		);
	
		return result;
	}

	private static boolean is_Identifier_Start(int c)
	{
		switch (Character.getType( c )) {
		case Character.UPPERCASE_LETTER:
		case Character.LOWERCASE_LETTER:
		case Character.TITLECASE_LETTER:
		case Character.MODIFIER_LETTER:
		case Character.OTHER_LETTER:
		case Character.LETTER_NUMBER:	return true;
		default:						return false;
		} 
	}
	
	private static boolean is_Identifier_Extend(int c)
	{
		switch (Character.getType( c )) {
		case Character.NON_SPACING_MARK:
		case Character.COMBINING_SPACING_MARK:
		case Character.DECIMAL_DIGIT_NUMBER:
		case Character.CONNECTOR_PUNCTUATION:	return true;
		default:								return false;
		} 
	}
	
	private static boolean is_valid_identifier( String input )
	{	final List<Boolean> result = new ArrayList<Boolean>();
		result.add( Boolean.valueOf(input.length() > 0) );
		result.add( Boolean.valueOf(is_Identifier_Start( input.codePointAt(0)) ));
		
		// All code-points allowable characters.
		input.codePoints().forEach( new IntConsumer(){
			@Override public void accept(int c)
			{result.add(Boolean.valueOf(is_Identifier_Start(c) || is_Identifier_Extend(c))); }
			});
		
		// No consecutive connector-punctuation.
		int[] code_points = input.codePoints().toArray();
		for (int i = 0; i < code_points.length-1; i++) 
		{ int j = code_points[i];
		  int k = code_points[i+1];
		  if (Character.getType(j) == Character.CONNECTOR_PUNCTUATION )
			  result.add( Boolean.valueOf( Character.getType(k) != Character.CONNECTOR_PUNCTUATION ) );
		}
		
		// the last character is not in character-punctuation.
		result.add( Boolean.valueOf( Character.getType( code_points[code_points.length-1] ) != Character.CONNECTOR_PUNCTUATION ) );
		
		boolean collector = true;
		for (Boolean b : result) { collector &= b.booleanValue(); }
		return collector;
	};
	
	/** Pass_10:
	 * Generate Identifiers.
	 */
	public ArrayList<Token> Pass_10( ArrayList<Token> input )
	{	final ArrayList<Token>  result		= new ArrayList<Token>();
	
		input.forEach(new Consumer<Token>() {
			@Override
			public void accept(Token t) {
				if (t.ID() != Token_ID.Text)
				{ result.add(t); }
				else
					if ( is_valid_identifier( t.Lexeme() ) )
						result.add( new Token(Token_ID.Identifier, t.Lexeme()) );
					else
						result.add(t);
			}
		} );
		
		return result;
	}
	
	/** Pass_11:
	 * Generate ticks.
	 */
	public ArrayList<Token> Pass_11( ArrayList<Token> input )
	{	final ArrayList<Token>  result		= new ArrayList<Token>();

		input.forEach(new Consumer<Token>() {
			@Override
			public void accept(Token t) {
				if (t.ID() != Token_ID.ch_Apostrophy)
					result.add(t);
				else
					result.add( new Token(Token_ID.ss_Tick, t.Lexeme()) );
			}
		} );
	
		return result;
	}
	
	/** Pass_12:
	 * Generate character literals.
	 */
	public ArrayList<Token> Pass_12( ArrayList<Token> input )
	{	final ArrayList<Token>  result		= new ArrayList<Token>();
		
		input.add( new Token(Token_ID.Nil, "") );
		input.add( new Token(Token_ID.Nil, "") );
	
		for (int i = 0; i < input.size()-2; i++)
		{ final Token thiz  =  input.get(i);
		  final Token next  =  input.get(i+1);
		  final Token third =  input.get(i+2);
			boolean is_chr  =  third.ID() == Token_ID.Text || third.ID() == Token_ID.Whitespace || third.ID() == Token_ID.Identifier;  
		  if (thiz.ID() == Token_ID.ch_Apostrophy & third.ID() == Token_ID.ss_Tick & is_chr)
			  {result.add( new Token(Token_ID.li_Character, next.Lexeme()));  i=i+2;}
		  else
			  result.add(thiz);
		}
	
		return result;
	}
	
	private boolean is_nonbased_integer( String x )
	{	//int[] code_points =
		final ArrayList<Boolean> valid = new ArrayList<Boolean>();
		valid.add(Boolean.valueOf( x.length() > 0 ));
		x.codePoints().forEach( new IntConsumer(){
			boolean spaced = true; // forced first character to be unspaced.
			@Override public void accept(int ch)
				{	int category = Character.getType(ch);
					if (spaced)
						{valid.add( category == Character.DECIMAL_DIGIT_NUMBER );	spaced = false;}
					else
						{	valid.add(category == Character.DECIMAL_DIGIT_NUMBER || category == Character.CONNECTOR_PUNCTUATION );
							spaced = category == Character.CONNECTOR_PUNCTUATION;
						}
				}
			});
		
		boolean result = true;
		for (Boolean b : valid) { result &= b.booleanValue(); }
		return result;
	}
	
	private int parse_nonbased_integer( String x )
	{	final StringBuilder working = new StringBuilder();

		x.codePoints().forEach( new IntConsumer(){
			@Override public void accept(int ch)
				{	int category = Character.getType(ch);
					if (category == Character.DECIMAL_DIGIT_NUMBER) working.append( Character.toString(ch) );
				}
			});
		
		return Integer.parseInt( working.toString() );
	}
	
	
	/** Pass_13:
	 * Generate non-based integer literals.
	 */
	public ArrayList<Token> Pass_13( ArrayList<Token> input )
	{	final ArrayList<Token>  result		= new ArrayList<Token>();

		input.forEach(
				new Consumer<Token>() {
					@Override
					public void accept(Token t)
					{
						if (t.ID() == Token_ID.Text && is_nonbased_integer(t.Lexeme()))
						{	 result.add(new Token(Token_ID.li_Integer, t.Lexeme()));		}
						else result.add(t);
					}
				}
			);
		
		return result;
	}
	
	/** Pass_14:
	 * Generate non-based float literals.
	 */
	public ArrayList<Token> Pass_14( ArrayList<Token> input )
	{	final ArrayList<Token>  result		= new ArrayList<Token>();
		
		input.add( new Token(Token_ID.Nil, "") );
		input.add( new Token(Token_ID.Nil, "") );
	
		for (int i = 0; i < input.size()-2; i++)
		{ final Token thiz  =  input.get(i);
		  final Token next  =  input.get(i+1);
		  final Token third =  input.get(i+2);
			  
		  if (thiz.ID() == Token_ID.li_Integer & next.ID() == Token_ID.ch_Period & third.ID() == Token_ID.li_Integer)
			  {result.add( new Token(Token_ID.li_Float, next.Lexeme()));  i=i+2;}
		  else
			  result.add(thiz);
		}

	
		return result;
	}

	/** Pass 15:
	 * Generate based integer literals.
	 */
	public ArrayList<Token> Pass_15( ArrayList<Token> input )
	{	final ArrayList<Token>  result		= new ArrayList<Token>();
		
		input.forEach(
				new Consumer<Token>() {
					@Override
					public void accept(Token t)
					{
						final String text = t.Lexeme();
						int pound_1 = text.indexOf('#');
						int pound_2 = text.lastIndexOf('#');
						
						if (t.ID() == Token_ID.Text && pound_2 > pound_1)
						{
							final String base   = text.substring(0, pound_1);
							final String digits = text.substring(pound_1+1, pound_2);
							//int value Integer.parseInt(digits, base);
							result.add(new Token(Token_ID.li_Based_Integer, t.Lexeme()));
						}
						else result.add(t);
					}
				}
			);
	
		return result;
	}

	
	/** Pass_16:
	 * Generate based float literals. 
	 */
	public ArrayList<Token> Pass_16( ArrayList<Token> input )
	{	final ArrayList<Token>  result		= new ArrayList<Token>();
	
		input.add( new Token(Token_ID.Nil, "") );
		input.add( new Token(Token_ID.Nil, "") );
		input.add( new Token(Token_ID.Nil, "") );
		input.add( new Token(Token_ID.Nil, "") );
	
		for (int i = 0; i < input.size()-5; i++)
		{ final Token item_1  =  input.get(i+0);
		  final Token item_2  =  input.get(i+1);
		  final Token item_3  =  input.get(i+2);
		  final Token item_4  =  input.get(i+3);
		  final Token item_5  =  input.get(i+4);
		  final String merged = item_1.Lexeme() + item_2.Lexeme() + item_3.Lexeme(); 
		  if (item_1.ID() == Token_ID.Text && item_2.ID() == Token_ID.ch_Period && item_3.ID() == Token_ID.Text)
		  { if ((item_4.ID() == Token_ID.ch_Plus || item_4.ID() == Token_ID.ch_Dash) && item_5.ID() == Token_ID.li_Integer)
		    {  result.add(new Token(Token_ID.li_Based_Float, merged + item_4.Lexeme() + item_5.Lexeme()) );
		  	   i = i + 4;
		  	} else { result.add(new Token(Token_ID.li_Based_Float, merged) ); i = i + 3;}}
		  else { result.add(input.get(i)); };
		}

		return result;
	}
	
	/** Pass_17:
	 * Generates the Comment_Info / Comment_Section / Comment_Block tokens.
	 */
	public ArrayList<Token> Pass_17( ArrayList<Token> input )
	{	final ArrayList<Token>  result		= input; //new ArrayList<Token>();
		
		/******************************
		 *  TO DO: collate comments.  *
		 ******************************/
	
		
		return result;
	}
	
	
	/** Pass_18:
	 *  Translate character-separators into ns_ & op_ tokens.
	 */
	public ArrayList<Token> Pass_18( ArrayList<Token> input )
	{	final ArrayList<Token>  result		= new ArrayList<Token>();
		final Map<Token_ID, Token_ID> table = new HashMap<Token_ID, Token_ID>();
		{
			// Translate Operators.
			table.put(Token_ID.ch_Astrisk,			Token_ID.op_Mul);
			table.put(Token_ID.ch_Slash,			Token_ID.op_Div);
			table.put(Token_ID.ch_Plus,				Token_ID.op_Add);
			table.put(Token_ID.ch_Dash,				Token_ID.op_Sub);
			table.put(Token_ID.ch_Ampersand,		Token_ID.op_Concat);
			table.put(Token_ID.ch_Less_Than,		Token_ID.op_Less_Than);
			table.put(Token_ID.ch_Greater_Than,		Token_ID.op_Greater_Than);
			table.put(Token_ID.ch_Equal,			Token_ID.op_Equal);
			
			// Translate Separators.
			table.put(Token_ID.ch_Open_Paren,		Token_ID.ns_Open_Paren);
			table.put(Token_ID.ch_Close_Paren,		Token_ID.ns_Close_Paren);
			table.put(Token_ID.ch_Comma,			Token_ID.ns_Comma);
			table.put(Token_ID.ch_Colon,			Token_ID.ns_Colon);
			table.put(Token_ID.ch_Semicolon,		Token_ID.ns_Semicolon);
			table.put(Token_ID.ch_Period,			Token_ID.ns_Period);
			
			table.put(Token_ID.ch_Vertical_Line,	Token_ID.ns_Alternitive);
		}
	
		input.forEach(
				new Consumer<Token>() {
					@Override
					public void accept(Token item)
					{
						if (table.containsKey(item.ID()))
							result.add( new Token( table.get(item.ID()) , item.Lexeme()) );
						else
							result.add(item);
					}
				}
			);
		
	
		return result;
	}
	
	
	/** Pass_19:
	 * Filter out artifact tokens.
	 * TODO: move this to the end... disabled for now.
	 */
	public ArrayList<Token> Pass_19( ArrayList<Token> input )
	{	final ArrayList<Token>  result		= new ArrayList<Token>();
		
		input.forEach(
			new Consumer<Token>() {
				@Override
				public void accept(Token item)
				{
					switch ( item.ID() ) {
					case Comment:
					case Whitespace:
					case End_of_Line:
					break;

					default:
						result.add(item);
					}				
				}
			}
		); // ForEach
	
		return result;
	}
	
	
	/** Pass_20:
	 * Strings to Operator-function.
	 */
	public ArrayList<Token> Pass_6B( ArrayList<Token> input )
	{	final ArrayList<Token>  result		= new ArrayList<Token>();
		final Set<String>       table	    = new java.util.HashSet<String>();
		{
			// Operator symbols.
			table.add("**");
			table.add("*");
			table.add("/");
			table.add("+");
			table.add("-");
			table.add("&");
			table.add("<");
			table.add(">");
			table.add("=");
			
			// Operator keywords.
			table.add("ABS");
			table.add("AND");
			table.add("IN");
			table.add("MOD");
			table.add("NOT");
			table.add("OR");
			table.add("REM");
			table.add("XOR");
		}
		
		for (int i = 0; i < input.size()-2; i++)
		{   final Token t1 = input.get(i+0);
			final Token t2 = input.get(i+1);
			final Token t3 = input.get(i+2);
			final boolean possible_fn_name = (t1.ID() == Token_ID.ch_Quote) && (t3.ID() == Token_ID.ch_Quote);
			
			if (possible_fn_name)
			{	boolean match = false;
				for (String op : table) { match = match || op.equalsIgnoreCase(t2.Lexeme()); }
				if (match)
				{ result.add( new Token(Token_ID.op_Function, t2.Lexeme()) ); i = i+2;}
				else
				{ result.add(t1); } 
			}
			else
			{ result.add(t1); }			
		}; // for
		
		return result;
	}
	

	/** Pass_2X:
	 * Filter out artifact tokens.
	 */
	public ArrayList<Token> Pass_21( ArrayList<Token> input )
	{	final ArrayList<Token>  result		= input; //new ArrayList<Token>();

	
		return result;
	}

	
	/** Pass_2X:
	 * Filter out artifact tokens.
	 */
	public ArrayList<Token> Pass_2X( ArrayList<Token> input )
	{	final ArrayList<Token>  result		= input; //new ArrayList<Token>();

	
		return result;
	}
	
	
	public ArrayList<Token> Pass_X( ArrayList<Token> input )
	{	final ArrayList<Token>  result		= new ArrayList<Token>();
		
		return result;
	}

/***************************************
 *  Anonymous Consumer class Template  * 
 *                                     *
 ***************************************/
/*	
		input.forEach(
				new Consumer<Token>() {
					@Override
					public void accept(Token t)
					{
						working.add( new Token(Token_ID.kw_Parallel, "Steve!") );
						if (t.ID() == Token_ID.Text)
						{   //working = new ArrayList<Token>();
							split_text(t, " ",  Token_ID.Whitespace).forEach(null)
		;
						  working.addAll( split_text(t, "\t", Token_ID.Whitespace) );
						  // \u00A0, \u2007 and, \u202F
						  working.addAll( split_text(t, "\u00A0F", Token_ID.Whitespace) );
						  working.addAll( split_text(t, "\u2007F", Token_ID.Whitespace) );
						  working.addAll( split_text(t, "\u202F",  Token_ID.Whitespace) );
						}
				
					}
				}
			);
	
//*/
	
	public ArrayList<Token> Do_Passes( ArrayList<Token> input )
	{
		input = Pass_1 (input);
		input = Pass_2 (input);
		input = Pass_3 (input);
		input = Pass_4 (input);
		input = Pass_5 (input);
		input = Pass_6 (input);
		input = Pass_6B(input);
		input = Pass_7 (input);
		input = Pass_8 (input);
		input = Pass_9 (input);
		input = Pass_10(input);
		input = Pass_11(input);
		input = Pass_12(input);
		input = Pass_13(input);
		input = Pass_14(input);
		input = Pass_15(input);
		input = Pass_16(input);
//*/		
		input = Pass_17(input);
		input = Pass_18(input);
		input = Pass_19(input);
		//input = Pass_20(input);
		input = Pass_21(input);
		//*/
		return input;
	}
}