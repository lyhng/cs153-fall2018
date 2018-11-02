grammar Cmm;

//Literals
VAR : 'var' ;
SEMI : ';' ;
COMMA : ',' ;
SINGLEQUOTE : '\'' ;
EQUAL : '=' ;
LPAREN : '(' ;
RPAREN : ')' ;
LBRACE : '{' ;
RBRACE : '}' ;
IF : 'if' ;
WHILE : 'while' ;
INT : 'int' ;
CHAR : 'char' ;
VOID : 'void' ;
FUNC : 'func' ;
RETURN : 'return' ;

Whitespace
    :   [ \t]+
        -> skip
    ;

Newline
    :   (   '\r' '\n'?
        |   '\n'
        )
        -> skip
    ;

BlockComment
    :   '/*' .*? '*/'
        -> skip
    ;

LineComment
    :   '//' ~[\r\n]*
        -> skip
    ;


program : statement* ;

//statement
statement : assignment
          | declaration
          | condition_statments
          | loop_statements
          | return_statement
          | func_declaration
          | func_call;

statement_list : statement* ;
block_statement : LBRACE statement_list RBRACE ;

//assignment
assignment : Identifier EQUAL expression SEMI;

expression : Identifier
           | Constant
           | expression Ops expression
           | func_call
           | LPAREN expression RPAREN
           ;


//conditional statement
condition_statments : if_statement ; //currently only implemented if
if_statement : IF LPAREN expression RPAREN block_statement ;

//loop statement
loop_statements : while_statement ; //currently only implemented while loop
while_statement : WHILE LPAREN expression RPAREN block_statement ;

//varibale declaration
declaration : type_specifier Whitespace* assignment;
type_specifier : primitive_types | Identifier | VOID ;
primitive_types : INT | CHAR ; //we only need 2 data types for assignment 5

//procedure & function
func_declaration : func_specifiers type_specifier Identifier LPAREN func_parm_declaration_list? RPAREN block_statement ;
func_specifiers : FUNC ;
func_parm_declaration_list : func_parm_declaration | func_parm_declaration COMMA func_parm_declaration_list ;
func_parm_declaration : type_specifier Whitespace* Identifier ;

func_call : Identifier LPAREN func_param_call_list? RPAREN ;
func_param_call_list : func_param_call | func_param_call COMMA func_param_call_list ;
func_param_call : Whitespace* expression ;


//return statement
return_statement : RETURN expression SEMI;

Constant : Integer | Character ;

Integer : Sign? Number ;
fragment Sign : '+' | '-' ;
fragment Number : [0-9]+ ;

Character : SINGLEQUOTE ~['\\\r\n] SINGLEQUOTE;

Identifier : [a-zA-Z][a-zA-Z0-9]* ;

Ops : '*' | '/' | '+' | '-' | '==' | '!=' | '>' | '<' | '>=' | '<=' ;
