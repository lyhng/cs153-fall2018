package cmm;

import cmm.antlr_gen.CmmParser;
import cmm.error.BaseError;
import cmm.error.StringError;
import cmm.symtab.Symbol;
import cmm.symtab.SymbolTable;
import cmm.types.BaseType;
import cmm.types.TypeVisitor;

import java.util.ArrayList;
import java.util.List;

public class SymbolTableVisitor extends CommonVisitor {
  private SymbolTable stack;
  private List<BaseError> errors;

  public SymbolTableVisitor() {
    SymbolTable runtime = RuntimeGenerator.generate();

    this.stack = new SymbolTable("CmmProgram", runtime);
    this.errors = new ArrayList<>();
  }

  public SymbolTable getStack() {
    return stack;
  }

  public List<BaseError> getErrors() {
    return errors;
  }

  @Override
  public String visitDeclaration(CmmParser.DeclarationContext ctx) {
    BaseType type = TypeVisitor.getInstance().visit(ctx.declaration_specifiers());
    List<CmmParser.Init_declaratorContext> declarators = ctx.init_declarator();

    for (CmmParser.Init_declaratorContext declarator : declarators) {
      String name = declarator.declarator().accept(this);

      if (stack.contains(name)) {
        this.errors.add(new StringError(declarator.declarator(), "'%s' is re-declared here.", name));
        continue;
      }

      stack.put(name, Symbol.createDeclaration(type));
    }

    return null;
  }

  @Override
  public String visitFunction_declaration(CmmParser.Function_declarationContext ctx) {
    String name = ctx.function_identifier().accept(this);

    if (stack.contains(name)) {
      this.errors.add(new StringError(ctx.function_identifier(), "function '%s' is re-declared.", name));
      return "";
    }

    SymbolTable previous = this.stack;
    this.stack = new SymbolTable(name, previous);

    List<CmmParser.Function_paramemterContext> parameters = ctx.function_paramemter();
    int index = 0;
    for (CmmParser.Function_paramemterContext parameter : parameters) {
      BaseType type = TypeVisitor.getInstance().visit(parameter.declaration_specifiers());
      String parameter_name = parameter.declarator().accept(this);

      if (stack.contains(name)) {
        this.errors.add(new StringError(parameter.declarator(), "function parameter '%s' is re-declared.", parameter_name));
        continue;
      }

      this.stack.put(parameter_name, Symbol.createParameter(type, index++));
    }

    String result = super.visitFunction_declaration(ctx);
    BaseType return_type = TypeVisitor.getInstance().visit(ctx.declaration_specifiers());

    previous.put(name, Symbol.createFunction(this.stack, return_type));
    this.stack = previous;
    return result;
  }
}
