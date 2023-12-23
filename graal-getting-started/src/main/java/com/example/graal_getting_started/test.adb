Procedure BOM_Test is
F:Float:=11#8A3.1#e+6*3#1.0#e-1/4#21.12#e3;
I:Integer:= 3#10#; --3#1.0#e-1;
S : String:= Character'('"') & "Another ""One""";
T : Constant:= 2**4;
Function "**"(Left, Right : Character) return Boolean is (True);
------------------------------
Begin--."Things" := adn stuff
    -- case I is
	   when 0|1 => exit;
	  -- when others => Null;
	-- end case;
	Null;
	--A( Id'Image(Io) );
end BOM_Test;