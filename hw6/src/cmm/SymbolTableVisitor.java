package cmm;

import cmm.antlr_gen.CmmParser;
import cmm.symtab.Symbol;
import cmm.symtab.SymbolTable;
import cmm.types.BaseType;
import cmm.types.TypeVisitor;

import java.util.List;

public class SymbolTableVisitor extends CommonVisitor {
  private SymbolTable stack;

  public SymbolTableVisitor() {
    SymbolTable runtime = RuntimeGenerator.generate();

    stack = new SymbolTable("CmmProgram", runtime);
  }

  public SymbolTable getStack() {
    return stack;
  }

  @Override
  public String visitDeclaration(CmmParser.DeclarationContext ctx) {
    BaseType type = TypeVisitor.getInstance().visit(ctx.declaration_specifiers());
    List<CmmParser.Init_declaratorContext> declarators = ctx.init_declarator();

    for (CmmParser.Init_declaratorContext declarator : declarators) {
      String name = declarator.declarator().accept(this);
      stack.put(name, Symbol.createDeclaration(type));
    }

    return null;
  }

  @Override
  public String visitFunction_declaration(CmmParser.Function_declarationContext ctx) {
    String name = ctx.function_identifier().accept(this);

    SymbolTable previous = this.stack;
    this.stack = new SymbolTable(name, previous);

    List<CmmParser.Function_paramemterContext> parameters = ctx.function_paramemter();
    int index = 0;
    for (CmmParser.Function_paramemterContext parameter : parameters) {
      BaseType type = TypeVisitor.getInstance().visit(parameter.declaration_specifiers());
      String parameter_name = parameter.declarator().accept(this);

      this.stack.put(parameter_name, Symbol.createParameter(type, index++));
    }

    String result = super.visitFunction_declaration(ctx);
    BaseType return_type = TypeVisitor.getInstance().visit(ctx.declaration_specifiers());

    previous.put(name, Symbol.createFunction(this.stack, return_type));
    this.stack = previous;
    return result;
  }
}
