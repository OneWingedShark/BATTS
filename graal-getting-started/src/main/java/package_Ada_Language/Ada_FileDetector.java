package package_Ada_Language;

import java.io.IOException;
import java.nio.charset.Charset;

import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleFile.FileTypeDetector;

public class Ada_FileDetector implements FileTypeDetector
{
    @Override
    public String findMimeType(TruffleFile file) throws IOException
    {   final String name = file.getName();
        	 if (name != null && name.endsWith(".ads")) {	return Ada_Truffle_Language.MIME_SPEC;		}
        else if (name != null && name.endsWith(".adb")) {	return Ada_Truffle_Language.MIME_BODY;		}
        else if (name != null && name.endsWith(".ada")) {	return Ada_Truffle_Language.MIME_SEPARATE;	}
        else if (name != null && name.endsWith(".ddb")) {	return Ada_Truffle_Language.MIME_DATABASE;	}
        	 
        return null;
    }

    @Override
    public Charset findEncoding(TruffleFile file) throws IOException
    {
    	//TODO: detect input character-set. (Default UTF-8.)
        return Charset.defaultCharset();
    }

}
