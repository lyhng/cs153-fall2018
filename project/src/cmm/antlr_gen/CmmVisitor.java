// Generated from hw6/src/cmm/Cmm.g4 by ANTLR 4.7.1
package cmm.antlr_gen;

    import cmm.types.BaseType;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CmmParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CmmVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code decimalNumber}
	 * labeled alternative in {@link CmmParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecimalNumber(CmmParser.DecimalNumberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code floatingNumber}
	 * labeled alternative in {@link CmmParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatingNumber(CmmParser.FloatingNumberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code string}
	 * labeled alternative in {@link CmmParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(CmmParser.StringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code character}
	 * labeled alternative in {@link CmmParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharacter(CmmParser.CharacterContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#primary_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary_expression(CmmParser.Primary_expressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code primaryExpression}
	 * labeled alternative in {@link CmmParser#function_array_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExpression(CmmParser.PrimaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code functionCall}
	 * labeled alternative in {@link CmmParser#function_array_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(CmmParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayIndex}
	 * labeled alternative in {@link CmmParser#function_array_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayIndex(CmmParser.ArrayIndexContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#postfix_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfix_expression(CmmParser.Postfix_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#unary_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary_expression(CmmParser.Unary_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#unary_operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary_operator(CmmParser.Unary_operatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#multiplicative_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicative_expression(CmmParser.Multiplicative_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#multiplicative_operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicative_operator(CmmParser.Multiplicative_operatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#additive_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditive_expression(CmmParser.Additive_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#additive_operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditive_operator(CmmParser.Additive_operatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#shift_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShift_expression(CmmParser.Shift_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#shift_operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShift_operator(CmmParser.Shift_operatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#relational_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelational_expression(CmmParser.Relational_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#relational_operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelational_operator(CmmParser.Relational_operatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#equality_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEquality_expression(CmmParser.Equality_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#equality_operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEquality_operator(CmmParser.Equality_operatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#and_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd_expression(CmmParser.And_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#xor_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXor_expression(CmmParser.Xor_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#or_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOr_expression(CmmParser.Or_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#logical_and_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogical_and_expression(CmmParser.Logical_and_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#logical_or_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogical_or_expression(CmmParser.Logical_or_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#ternary_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTernary_expression(CmmParser.Ternary_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#assignment_operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment_operator(CmmParser.Assignment_operatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#assignment_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment_expression(CmmParser.Assignment_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#comma_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComma_expression(CmmParser.Comma_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(CmmParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#builtin_types}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBuiltin_types(CmmParser.Builtin_typesContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#initializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitializer(CmmParser.InitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#declarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarator(CmmParser.DeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#init_declarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInit_declarator(CmmParser.Init_declaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#declaration_specifiers}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration_specifiers(CmmParser.Declaration_specifiersContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(CmmParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#compound_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompound_statement(CmmParser.Compound_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#expression_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression_statement(CmmParser.Expression_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#selection_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelection_statement(CmmParser.Selection_statementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code WhileStatement}
	 * labeled alternative in {@link CmmParser#iteration_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(CmmParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DoWhileStatement}
	 * labeled alternative in {@link CmmParser#iteration_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoWhileStatement(CmmParser.DoWhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#jump_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJump_statement(CmmParser.Jump_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(CmmParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#function_paramemter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_paramemter(CmmParser.Function_paramemterContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#function_identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_identifier(CmmParser.Function_identifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#function_declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_declaration(CmmParser.Function_declarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CmmParser#cmm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmm(CmmParser.CmmContext ctx);
}