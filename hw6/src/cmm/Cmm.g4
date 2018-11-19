grammar Cmm;

// Keywords
Equal: '=';

// Constants
Constant: DecimalNumber | FloatingNumber | String | Character;

// Number
fragment Sign: '+' | '-';
fragment Digit: [0-9];
fragment NonzeroDigit: [1-9];
fragment DecimalNumber: (NonzeroDigit Digit* | Digit);
fragment FloatingNumber: DecimalNumber '.' Digit+ ExponentPart?;
fragment ExponentFlag: 'e' | 'E';
fragment ExponentPart: ExponentFlag Sign? Digit+;

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

// Expression
// See https://en.wikipedia.org/wiki/Operators_in_C_and_C%2B%2B#Operator_precedence
// for the list of operator precedence.
primary_expression: Identifier | Constant | '(' expression ')';

function_call_argument: expression | expression ',' function_call_argument;
postfix_expression: primary_expression
    | postfix_expression '[' expression ']'
    | postfix_expression '(' function_call_argument? ')'
    | postfix_expression '++'
    | postfix_expression '--';

unary_expression: postfix_expression
    | '++' unary_expression
    | '--' unary_expression
    | unary_operator unary_expression;
unary_operator: '&' | '~' | '!' | '+' | '-';

multiplicative_expression: unary_expression
    | multiplicative_expression multiplicative_operator unary_expression;
multiplicative_operator: '*' | '/' | '%';

additive_expression: multiplicative_expression
    | additive_expression additive_operator multiplicative_expression;
additive_operator: '+' | '-';

shift_expression: additive_expression
    | shift_expression shift_operator additive_expression;
shift_operator: '<<' | '>>';

relational_expression: shift_expression
    | relational_expression relational_operator shift_expression;
relational_operator: '>' | '>=' | '<' | '<=';

equality_expression: relational_expression
    | equality_expression equality_operator relational_expression;
equality_operator: '==' | '!=';

and_expression: equality_expression | and_expression '&' equality_expression;
xor_expression: and_expression | xor_expression '^' and_expression;
or_expression: xor_expression | or_expression '|' xor_expression;

logical_and_expression: or_expression | logical_and_expression '&&' or_expression;
logical_or_expression: logical_and_expression | logical_or_expression '||' logical_and_expression;

ternary_expression: logical_or_expression ('?' expression ':' ternary_expression)?;

assignment_operator: '=' | '+=' | '-=' | '*=' | '/=' | '%=' | '>>=' | '<<=' | '&=' | '^=' | '|=';
assignment_expression: ternary_expression | unary_expression assignment_operator expression;

comma_expression: assignment_expression (',' comma_expression)?;

expression: comma_expression;

// Statement
// https://en.wikipedia.org/wiki/C_syntax#Control_structures
builtin_types: ( 'void' | 'char' | 'short' | 'int' | 'long' | 'double' | 'float' | Identifier);

// declarations
initializer: assignment_expression;
declarator: Identifier;
init_declarator: declarator ('=' initializer)?;
declaration_specifiers: builtin_types;
declaration: declaration_specifiers init_declarator (',' init_declarator)? ';';

compound_statement: '{' (statement | declaration)* '}';
expression_statement: expression ';';
selection_statement: 'if' '(' expression ')' statement ('else' statement)?;
iteration_statement: 'while' '(' expression ')' statement
    | 'do' statement 'while' '(' expression ')';
jump_statement: 'continue' ';' | 'break' ';' | 'return' expression? ';';
statement: compound_statement | expression_statement | selection_statement | iteration_statement | jump_statement;

// function declaration
function_paramemter: declaration_specifiers declarator;
function_declaration: declaration_specifiers declarator '(' function_paramemter? (',' function_paramemter)* ')' compound_statement;

cmm: (function_declaration | declaration)*;