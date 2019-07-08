grammar Grammar;

UNARY : '+' | '-';
BINARY: [*/%];
//FUNCTION : 'sqrt'|'sin'|'cos';
LBRAC : '(';
RBRAC : ')';
WHITESPACE : [ \n\t\r]+ -> skip;

fragment DIGIT : [0-9];

INTEGER: DIGIT+;
FLOAT : DIGIT+ [eE.] DIGIT+ ;

integer : INTEGER;
floatp : FLOAT;

expression : expp1 EOF;
expp1 :expp1 BINARY expp1| expp1 UNARY expp1 |UNARY? atom;
atom: integer|floatp | LBRAC expp1 RBRAC;