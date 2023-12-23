package package_Ada_Language.lexington;


import java.io.IOException;
import java.io.PushbackInputStream;
import java.io.Reader;
//import package_Ada_Language.lexington.Token;
import java.util.ArrayList;

import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.Trees;

import package_Ada_Language.Ada_Node;

public class Parser
{
	
	
	protected class Lookahead
	{
		int 				current;
		ArrayList<Token>	tokens;
		
		public Lookahead(ArrayList<Token> tokens)
		{	this.tokens =  tokens;
			// This keeps n+1 valid when we get to the end.
			this.tokens.add( new Token(Token_ID.Nil, null) );
			this.current = 0;
		}
		
		public Token THIS()
		{	return this.tokens.get(current);	}
		
		public Token NEXT()
		{	return this.tokens.get(current+1);	}
		
		public void CONSUME()
		{	if (this.current+1 < tokens.size())
			{ this.current = this.current+1; };
		}		
	} /* LOOKAHEAD */
	
	
	public static void pFFF()
	{
		PushbackInputStream pb = new PushbackInputStream(null);
		
	}
	
    public static Ada_Node parse(String program)
    {	return parse(CharStreams.fromString(program));	}

    public static Ada_Node parse(Reader program) throws IOException
    {	return parse(CharStreams.fromReader(program));	}

    private static Ada_Node parse(CharStream inputStream)
    {
    	CommonTokenStream cs = CommonTokenStream(inputStream);
    	Parser parser = new Parser(); 
    	/*
        var lexer = new EasyScriptLexer(inputStream);
        // remove the default console error listener
        lexer.removeErrorListeners();
        var parser = new EasyScriptParser(new CommonTokenStream(lexer));
        // remove the default console error listener
        parser.removeErrorListeners();
        // throw an exception when a parsing error is encountered
        parser.setErrorHandler(new BailErrorStrategy());
        EasyScriptParser.ExprContext context = parser.start().expr();
        return expr2TruffleNode(context);
        //*/
    	return null;
    }
/*
    private static Ada_Node expr2TruffleNode(EasyScriptParser.ExprContext expr)
    {
        return expr instanceof EasyScriptParser.AddExprContext
                ? addExpr2AdditionNode((EasyScriptParser.AddExprContext) expr)
                : literalExpr2ExprNode((EasyScriptParser.LiteralExprContext) expr);
    }

    private static AdditionNode addExpr2AdditionNode(EasyScriptParser.AddExprContext addExpr)
    {
        return AdditionNodeGen.create(
                expr2TruffleNode(addExpr.left),
                expr2TruffleNode(addExpr.right)
        );
    }

    private static Ada_Node literalExpr2ExprNode(EasyScriptParser.LiteralExprContext literalExpr)
    {
        TerminalNode intTerminal = literalExpr.literal().INT();
        return intTerminal != null
                ? parseIntLiteral(intTerminal.getText())
                : parseDoubleLiteral(literalExpr.getText());
    }

    private static Ada_Node parseIntLiteral(String text)
    {
        try {
            return new IntLiteralNode(Integer.parseInt(text));
        } catch (NumberFormatException e) {
            // it's possible that the integer literal is too big to fit in a 32-bit Java `int` -
            // in that case, fall back to a double literal
            return parseDoubleLiteral(text);
        }
    }

    private static DoubleLiteralNode parseDoubleLiteral(String text)
    {
        return new DoubleLiteralNode(Double.parseDouble(text));
    }
//*/	
    /********************************************************
     * 
     * 
     * 
     ********************************************************/
    
    
    
	public Trees parse(ArrayList<Token> input)
	{   
		Trees p;
		;
		
		return null;
	}
	
}
