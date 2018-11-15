genrule(
  name = "cmm.antlr_gen",
  srcs = ["src/cmm/Cmm.g4"],
  outs = [
    "src/cmm/antlr_gen/Cmm.interp",
    "src/cmm/antlr_gen/Cmm.tokens",
    "src/cmm/antlr_gen/CmmBaseListener.java",
    "src/cmm/antlr_gen/CmmLexer.interp",
    "src/cmm/antlr_gen/CmmLexer.java",
    "src/cmm/antlr_gen/CmmLexer.tokens",
    "src/cmm/antlr_gen/CmmListener.java",
    "src/cmm/antlr_gen/CmmParser.java"
  ],
  cmd = "antlr -o `dirname $(location src/cmm/antlr_gen/Cmm.interp)` -Xexact-output-dir -package cmm.antlr_gen $<"
)

java_binary(
  name = "cmm",
  srcs = glob(["src/**/*.java"]) + [":cmm.antlr_gen"],
  main_class = "cmm.CmmCompiler",
  deps = [
    "@antlr4_runtime//jar",
  ]
)
