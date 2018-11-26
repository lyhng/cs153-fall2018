package cmm;

import cmm.antlr_gen.CmmBaseVisitor;
import cmm.antlr_gen.CmmParser;
import wci.intermediate.SymTabEntry;
import wci.intermediate.SymTabFactory;
import wci.intermediate.SymTabStack;
import wci.intermediate.TypeSpec;
import wci.intermediate.symtabimpl.DefinitionImpl;
import wci.intermediate.symtabimpl.Predefined;
import wci.util.CrossReferencer;

import java.util.ArrayList;
import java.util.List;

import static wci.intermediate.symtabimpl.DefinitionImpl.VARIABLE;
import static wci.intermediate.symtabimpl.SymTabKeyImpl.ROUTINE_SYMTAB;

public class CmmVisitor<T> extends CmmBaseVisitor<T> {
  private SymTabStack symbolTableStack;
  private ArrayList<SymTabEntry> variableIdList;

  public CmmVisitor() {
    this.symbolTableStack = SymTabFactory.createSymTabStack();
    Predefined.initialize(symbolTableStack);
  }

  @Override
  public T visitCmm(CmmParser.CmmContext ctx) {
    // TODO: make filename as the program ID
    SymTabEntry programId = symbolTableStack.enterLocal("!!_global");
    programId.setDefinition(DefinitionImpl.PROGRAM);
    programId.setAttribute(ROUTINE_SYMTAB, symbolTableStack.push());
    symbolTableStack.setProgramId(programId);

    T value = super.visitCmm(ctx);

    CrossReferencer crossReferencer = new CrossReferencer();
    crossReferencer.print(this.symbolTableStack);

    return value;
  }

  @Override
  public T visitBuiltin_types(CmmParser.Builtin_typesContext ctx) {
    return super.visitBuiltin_types(ctx);
  }

  @Override
  public T visitDeclaration_specifiers(CmmParser.Declaration_specifiersContext ctx) {
    return super.visitDeclaration_specifiers(ctx);
  }

  @Override
  public T visitDeclaration(CmmParser.DeclarationContext ctx) {
    T value = super.visitDeclaration(ctx);

    CmmParser.Builtin_typesContext typeContext = ctx.declaration_specifiers().builtin_types();
    TypeSpec type = null;

    if (typeContext.Identifier() == null) {
      String typeName = typeContext.getText();
      System.out.println("builtin type: " + typeName);

      if (typeName.equalsIgnoreCase("int") || typeName.equalsIgnoreCase("long") || typeName.equalsIgnoreCase("short")) {
        type = Predefined.integerType;
      } else if (typeName.equalsIgnoreCase("float") || typeName.equalsIgnoreCase("double")) {
        type = Predefined.realType;
      }
    } else {
      System.out.println("user defined type: " + typeContext.Identifier().toString());
    }

    List<CmmParser.Init_declaratorContext> declarators = ctx.init_declarator();

    for (CmmParser.Init_declaratorContext declarator: declarators) {
      String name = declarator.declarator().Identifier().toString();

      SymTabEntry entry = symbolTableStack.enterLocal(name);
      entry.setDefinition(VARIABLE);
      entry.setTypeSpec(type);

      if (declarator.initializer() != null) {
        // assign initializer
        System.out.println("initializer");
      }
    }

    return value;
  }

  @Override
  public T visitFunction_declaration(CmmParser.Function_declarationContext ctx) {
    // TODO: parse function signature (i.e. return type etc.)

    String name = ctx.declarator().Identifier().toString();   // function name

    SymTabEntry function = symbolTableStack.enterLocal(name);
    function.setDefinition(DefinitionImpl.FUNCTION);
    function.setAttribute(ROUTINE_SYMTAB, symbolTableStack.push());
    symbolTableStack.setProgramId(function);

    T result = super.visitFunction_declaration(ctx);

    return result;
  }
}
