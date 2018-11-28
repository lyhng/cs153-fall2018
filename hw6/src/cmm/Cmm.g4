grammar Cmm;

@header {
    import cmm.types.BaseType;
}
// Expression
// See https://en.wikipedia.org/wiki/Operators_in_C_and_C%2B%2B#Operator_precedence
// for the list of operator precedence.
// Constants
constant: DecimalNumber # decimalNumber
    | FloatingNumber # floatingNumber
    | String # string
    | Character # character;

primary_expression locals [ BaseType type = null ]
    : Identifier | constant | '(' expression ')';

function_array_expression locals [ BaseType type = null ] : primary_expression # primaryExpression
    | function_array_expression '[' expression ']'  # arrayIndex
    | function_identifier '(' expression* ')' # functionCall;

postfix_expression locals [ BaseType type = null ] : function_array_expression
    | postfix_expression '++'
    | postfix_expression '--';

unary_expression locals [ BaseType type = null ] : postfix_expression
    | '++' unary_expression
    | '--' unary_expression
    | unary_operator unary_expression;
unary_operator: '&' | '~' | '!' | '+' | '-';

multiplicative_expression locals [ BaseType type = null ] : unary_expression
    | multiplicative_expression multiplicative_operator unary_expression;
multiplicative_operator: '*' | '/' | '%';

additive_expression locals [ BaseType type = null ] : multiplicative_expression
    | additive_expression additive_operator multiplicative_expression;
additive_operator: '+' | '-';

shift_expression locals [ BaseType type = null ] : additive_expression
    | shift_expression shift_operator additive_expression;
shift_operator: '<<' | '>>';

relational_expression locals [ BaseType type = null ] : shift_expression
    | relational_expression relational_operator shift_expression;
relational_operator: '>' | '>=' | '<' | '<=';

equality_expression locals [ BaseType type = null ] : relational_expression
    | equality_expression equality_operator relational_expression;
equality_operator: '==' | '!=';

and_expression locals [ BaseType type = null ] : equality_expression | and_expression '&' equality_expression;
xor_expression locals [ BaseType type = null ] : and_expression | xor_expression '^' and_expression;
or_expression locals [ BaseType type = null ] : xor_expression | or_expression '|' xor_expression;

logical_and_expression locals [ BaseType type = null ] : or_expression | logical_and_expression '&&' or_expression;
logical_or_expression locals [ BaseType type = null ] : logical_and_expression | logical_or_expression '||' logical_and_expression;

ternary_expression locals [ BaseType type = null ] : logical_or_expression ('?' expression ':' ternary_expression)?;

assignment_operator: '=' | '+=' | '-=' | '*=' | '/=' | '%=' | '>>=' | '<<=' | '&=' | '^=' | '|=';
assignment_expression locals [ BaseType type = null ] : ternary_expression | Identifier assignment_operator expression;

comma_expression locals [ BaseType type = null ] : assignment_expression (',' comma_expression)?;

expression locals [ BaseType type = null ] : comma_expression;

// Statement
// https://en.wikipedia.org/wiki/C_syntax#Control_structures
builtin_types: ( 'void' | 'char' | 'short' | 'int' | 'long' | 'double' | 'float' | Identifier);

// declarations
initializer: assignment_expression;
declarator: Identifier;
init_declarator: declarator ('=' initializer)?;
declaration_specifiers: builtin_types;
declaration: declaration_specifiers init_declarator (',' init_declarator)* ';';

compound_statement: '{' (statement | declaration)* '}';
expression_statement: expression ';';
selection_statement: 'if' '(' expression ')' statement ('else' statement)?;
iteration_statement: 'while' '(' expression ')' statement # WhileStatement
    | 'do' statement 'while' '(' expression ')' # DoWhileStatement;
jump_statement: 'continue' ';' | 'break' ';' | 'return' expression? ';';
statement: compound_statement | expression_statement | selection_statement | iteration_statement | jump_statement;

// function declaration
function_paramemter: declaration_specifiers declarator;
function_identifier: Identifier;
function_declaration: declaration_specifiers function_identifier '(' function_paramemter? (',' function_paramemter)* ')' compound_statement;

cmm: (function_declaration | declaration)*;

// Keywords
Equal: '=';

// Number
fragment Sign: '+' | '-';
fragment Digit: [0-9];
fragment NonzeroDigit: [1-9];
fragment ExponentFlag: 'e' | 'E';
fragment ExponentPart: ExponentFlag Sign? Digit+;

DecimalNumber: (NonzeroDigit Digit* | Digit);
FloatingNumber: DecimalNumber '.' Digit+ ExponentPart?;

// String
fragment EscapeChar: '\\' ['"nrt\\];
fragment Char: ~["\\\r\n] | EscapeChar;
String: '"' Char* '"';
Character: '\'' Char '\'';

// Identifier
fragment Nondigit: [a-zA-Z_];
Identifier: Nondigit (Nondigit | Digit)*;

// skips
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
