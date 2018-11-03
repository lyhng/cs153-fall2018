// Generated from src/Cmm.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CmmParser}.
 */
public interface CmmListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CmmParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(CmmParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmmParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(CmmParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link CmmParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(CmmParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmmParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(CmmParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CmmParser#statement_list}.
	 * @param ctx the parse tree
	 */
	void enterStatement_list(CmmParser.Statement_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmmParser#statement_list}.
	 * @param ctx the parse tree
	 */
	void exitStatement_list(CmmParser.Statement_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link CmmParser#block_statement}.
	 * @param ctx the parse tree
	 */
	void enterBlock_statement(CmmParser.Block_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmmParser#block_statement}.
	 * @param ctx the parse tree
	 */
	void exitBlock_statement(CmmParser.Block_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CmmParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(CmmParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmmParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(CmmParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link CmmParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(CmmParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmmParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(CmmParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CmmParser#condition_statments}.
	 * @param ctx the parse tree
	 */
	void enterCondition_statments(CmmParser.Condition_statmentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmmParser#condition_statments}.
	 * @param ctx the parse tree
	 */
	void exitCondition_statments(CmmParser.Condition_statmentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link CmmParser#if_statement}.
	 * @param ctx the parse tree
	 */
	void enterIf_statement(CmmParser.If_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmmParser#if_statement}.
	 * @param ctx the parse tree
	 */
	void exitIf_statement(CmmParser.If_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CmmParser#loop_statements}.
	 * @param ctx the parse tree
	 */
	void enterLoop_statements(CmmParser.Loop_statementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmmParser#loop_statements}.
	 * @param ctx the parse tree
	 */
	void exitLoop_statements(CmmParser.Loop_statementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link CmmParser#while_statement}.
	 * @param ctx the parse tree
	 */
	void enterWhile_statement(CmmParser.While_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmmParser#while_statement}.
	 * @param ctx the parse tree
	 */
	void exitWhile_statement(CmmParser.While_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CmmParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(CmmParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmmParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(CmmParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CmmParser#type_specifier}.
	 * @param ctx the parse tree
	 */
	void enterType_specifier(CmmParser.Type_specifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmmParser#type_specifier}.
	 * @param ctx the parse tree
	 */
	void exitType_specifier(CmmParser.Type_specifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CmmParser#primitive_types}.
	 * @param ctx the parse tree
	 */
	void enterPrimitive_types(CmmParser.Primitive_typesContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmmParser#primitive_types}.
	 * @param ctx the parse tree
	 */
	void exitPrimitive_types(CmmParser.Primitive_typesContext ctx);
	/**
	 * Enter a parse tree produced by {@link CmmParser#func_declaration}.
	 * @param ctx the parse tree
	 */
	void enterFunc_declaration(CmmParser.Func_declarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmmParser#func_declaration}.
	 * @param ctx the parse tree
	 */
	void exitFunc_declaration(CmmParser.Func_declarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CmmParser#func_specifiers}.
	 * @param ctx the parse tree
	 */
	void enterFunc_specifiers(CmmParser.Func_specifiersContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmmParser#func_specifiers}.
	 * @param ctx the parse tree
	 */
	void exitFunc_specifiers(CmmParser.Func_specifiersContext ctx);
	/**
	 * Enter a parse tree produced by {@link CmmParser#func_parm_declaration_list}.
	 * @param ctx the parse tree
	 */
	void enterFunc_parm_declaration_list(CmmParser.Func_parm_declaration_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmmParser#func_parm_declaration_list}.
	 * @param ctx the parse tree
	 */
	void exitFunc_parm_declaration_list(CmmParser.Func_parm_declaration_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link CmmParser#func_parm_declaration}.
	 * @param ctx the parse tree
	 */
	void enterFunc_parm_declaration(CmmParser.Func_parm_declarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmmParser#func_parm_declaration}.
	 * @param ctx the parse tree
	 */
	void exitFunc_parm_declaration(CmmParser.Func_parm_declarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CmmParser#func_call}.
	 * @param ctx the parse tree
	 */
	void enterFunc_call(CmmParser.Func_callContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmmParser#func_call}.
	 * @param ctx the parse tree
	 */
	void exitFunc_call(CmmParser.Func_callContext ctx);
	/**
	 * Enter a parse tree produced by {@link CmmParser#func_param_call_list}.
	 * @param ctx the parse tree
	 */
	void enterFunc_param_call_list(CmmParser.Func_param_call_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmmParser#func_param_call_list}.
	 * @param ctx the parse tree
	 */
	void exitFunc_param_call_list(CmmParser.Func_param_call_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link CmmParser#func_param_call}.
	 * @param ctx the parse tree
	 */
	void enterFunc_param_call(CmmParser.Func_param_callContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmmParser#func_param_call}.
	 * @param ctx the parse tree
	 */
	void exitFunc_param_call(CmmParser.Func_param_callContext ctx);
	/**
	 * Enter a parse tree produced by {@link CmmParser#return_statement}.
	 * @param ctx the parse tree
	 */
	void enterReturn_statement(CmmParser.Return_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmmParser#return_statement}.
	 * @param ctx the parse tree
	 */
	void exitReturn_statement(CmmParser.Return_statementContext ctx);
}