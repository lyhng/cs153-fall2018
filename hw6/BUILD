genrule(
  name = "cmm.antlr_gen",
  srcs = ["src/cmm/Cmm.g4"],
  outs = [
    "src/cmm/antlr_gen/Cmm.interp",
    "src/cmm/antlr_gen/Cmm.tokens",
    "src/cmm/antlr_gen/CmmBaseVisitor.java",
    "src/cmm/antlr_gen/CmmLexer.interp",
    "src/cmm/antlr_gen/CmmLexer.java",
    "src/cmm/antlr_gen/CmmLexer.tokens",
    "src/cmm/antlr_gen/CmmVisitor.java",
    "src/cmm/antlr_gen/CmmParser.java"
  ],
  cmd = "antlr -no-listener -visitor -o `dirname $(location src/cmm/antlr_gen/Cmm.interp)` -Xexact-output-dir -package cmm.antlr_gen $<"
)

genrule(
  name = "pcl.antlr_gen",
  srcs = ["src/pcl/Pcl2.g4"],
  outs = [
    "src/pcl/antlr_gen/Pcl2.interp",
    "src/pcl/antlr_gen/Pcl2.tokens",
    "src/pcl/antlr_gen/Pcl2BaseVisitor.java",
    "src/pcl/antlr_gen/Pcl2Lexer.interp",
    "src/pcl/antlr_gen/Pcl2Lexer.java",
    "src/pcl/antlr_gen/Pcl2Lexer.tokens",
    "src/pcl/antlr_gen/Pcl2Visitor.java",
    "src/pcl/antlr_gen/Pcl2Parser.java"
  ],
  cmd = "antlr -no-listener -visitor -o `dirname $(location src/pcl/antlr_gen/Pcl2.interp)` -Xexact-output-dir -package pcl.antlr_gen $<"
)

java_binary(
  name = "cmm",
  srcs = glob(["src/**/*.java"]) + [":cmm.antlr_gen"],
  main_class = "cmm.CmmCompiler",
  deps = [
    "@antlr4_runtime//jar",
  ]
)
