package com.example.graal_getting_started;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.function.Consumer;

import fr.lirmm.graphik.graal.api.core.ConjunctiveQuery;
import fr.lirmm.graphik.graal.api.kb.KnowledgeBase;
import fr.lirmm.graphik.graal.io.dlp.DlgpParser;
import fr.lirmm.graphik.graal.io.dlp.DlgpWriter;
import fr.lirmm.graphik.graal.kb.KBBuilder;
import fr.lirmm.graphik.util.stream.CloseableIterator;
import package_Ada_Language.lexington.Lexer;
import package_Ada_Language.lexington.Token;



/**
 * Hello world!
 *
 */
public class App //extends TruffleLanguage//<int>
{
	
	public static void main(String[] args) throws Exception {
 /***************************************************************************************
		// 0 - Create a KBBuilder
		KBBuilder kbb = new KBBuilder();
		// 1 - Add a rule
		kbb.add(DlgpParser.parseRule("mortal(X) :- human(X)."));
		// 2 - Add a fact
		kbb.add(DlgpParser.parseAtom("human(socrate)."));
		kbb.add(DlgpParser.parseAtom("human(steve)."));
		// 3 - Generate the KB
		KnowledgeBase kb = kbb.build();
		// 4 - Create a DLGP writer to print data
		DlgpWriter writer = new DlgpWriter();
		// 5 - Parse a query from a Java String
		ConjunctiveQuery query = DlgpParser.parseQuery("?(X) :- mortal(X).");
		// 6 - Query the KB
		CloseableIterator resultIterator = kb.query(query);
		// 7 - Iterate and print results
		writer.write("\n= Answers =\n");
		if (resultIterator.hasNext()) {
			do {
				writer.write(resultIterator.next());
				writer.write("\n");
			} while (resultIterator.hasNext());
		} else {
			writer.write("No answers.\n");
		}
		// 8 - Close resources
		kb.close();
		writer.close();
//***************************************************************************************/		

		final String test_file = "C:\\Users\\tiger\\workspace\\graal-getting-started\\src\\main\\java\\com\\example\\graal_getting_started\\test.adb";
		Lexer lex = new Lexer();
		String s = lex.Read_File( new FileInputStream(test_file) );
		
		ArrayList<Token> tlist = lex.Make_Text(s);
		tlist = lex.Do_Passes(tlist);
		
		System.out.println( s );
		System.out.println( "#####################################################################################" );
		System.out.print  ("(Tokens parsed: ");
		System.out.print  ( tlist.size() );
		System.out.println( ')' );
		tlist.forEach( new Consumer<Token>() {
			@Override
			public void accept(Token c) {
				System.out.println( c.Print() + '[' + c.Lexeme() + ']' );
			}
		});
		
	}
	
}
