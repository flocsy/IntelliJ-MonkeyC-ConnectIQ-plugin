package com.github.bertware.monkeyc_intellij.language.lexer;

import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.github.bertware.monkeyc_intellij.language.psi.MonkeyTypes.*;

%%

%{
  public _MonkeyLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _MonkeyLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

WHITE_SPACE=[ \t\n\x0B\f\r]+
SINGLE_LINE_COMMENT="//"[^!].*
SINGLE_LINE_DOC_COMMENT="//"\!(.*)
BLOCK_COMMENT="/"\*([^*]|\*+[^*/])*(\*+"/")?
IDENTIFIER=[a-zA-Z$_][a-zA-Z0-9$_]*
LONGLITERAL=[0-9]+[lL]
INTLITERAL=[0-9]+
HEX_LITERAL=0[Xx][0-9a-fA-F]*
FLOATLITERAL=([0-9]+[fF]|([0-9]+)?\.[0-9]+[fF]?)
DOUBLELITERAL=([0-9]+)?(\.[0-9]+)?[dD]
STRING=(\"([^\"\\]|\\.)*\")
CHARLITERAL='[^']*'

%%
<YYINITIAL> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }

  "."                            { return DOT; }
  ";"                            { return SEMI; }
  "?"                            { return QUES; }
  ":"                            { return COLON; }
  "class"                        { return CLASS; }
  "function"                     { return FUNCTION; }
  "return"                       { return RETURN; }
  "new"                          { return NEW; }
  "var"                          { return VAR; }
  "const"                        { return CONST; }
  "module"                       { return MODULE; }
  "using"                        { return USING; }
  "as"                           { return AS; }
  "enum"                         { return ENUM; }
  "extends"                      { return EXTENDS; }
  "null"                         { return NULL; }
  "native"                       { return NATIVE; }
  "hidden"                       { return HIDDEN; }
  "static"                       { return STATIC; }
  "instanceof"                   { return INSTANCEOF; }
  "has"                          { return HAS; }
  "if"                           { return IF; }
  "else"                         { return ELSE; }
  "do"                           { return DO; }
  "while"                        { return WHILE; }
  "for"                          { return FOR; }
  "break"                        { return BREAK; }
  "continue"                     { return CONTINUE; }
  "switch"                       { return SWITCH; }
  "default"                      { return DEFAULT; }
  "case"                         { return CASE; }
  "try"                          { return TRY; }
  "catch"                        { return CATCH; }
  "finally"                      { return FINALLY; }
  "throw"                        { return THROW; }
  "and"                          { return AND; }
  "or"                           { return OR; }
  "true"                         { return TRUE; }
  "false"                        { return FALSE; }
  "this"                         { return THIS; }
  "self"                         { return SELF; }
  "$"                            { return BLING; }
  "/*"                           { return MULTI_LINE_COMMENT_START; }
  "*/"                           { return MULTI_LINE_COMMENT_END; }
  "\""                           { return STRING_A; }
  "'"                            { return STRING_B; }
  "{"                            { return LBRACE; }
  "}"                            { return RBRACE; }
  "["                            { return LBRACKET; }
  "]"                            { return RBRACKET; }
  "("                            { return LPAREN; }
  ")"                            { return RPAREN; }
  ","                            { return COMMA; }
  "*"                            { return STAR; }
  "|"                            { return BAR; }
  "<"                            { return LT; }
  ">"                            { return GT; }
  "=>"                           { return EQGT; }
  "&"                            { return AMP; }
  "||"                           { return BARBAR; }
  "&&"                           { return AMPAMP; }
  "++"                           { return PLUSPLUS; }
  "--"                           { return SUBSUB; }
  "="                            { return EQ; }
  "=="                           { return EQEQ; }
  "!="                           { return BANGEQ; }
  "+="                           { return PLUSEQ; }
  "-="                           { return SUBEQ; }
  "*="                           { return STAREQ; }
  "/="                           { return SLASHEQ; }
  "&="                           { return AMPEQ; }
  "|="                           { return CARETEQ; }
  "%="                           { return PERCENTEQ; }
  "^"                            { return CARET; }
  "%"                            { return PERCENT; }
  "~"                            { return TILDE; }
  "!"                            { return BANG; }
  "+"                            { return PLUS; }
  "-"                            { return SUB; }
  "/"                            { return SLASH; }
  "BAREQ"                        { return BAREQ; }
  "VOID"                         { return VOID; }
  "SUPER"                        { return SUPER; }

  {WHITE_SPACE}                  { return WHITE_SPACE; }
  {SINGLE_LINE_COMMENT}          { return SINGLE_LINE_COMMENT; }
  {SINGLE_LINE_DOC_COMMENT}      { return SINGLE_LINE_DOC_COMMENT; }
  {BLOCK_COMMENT}                { return BLOCK_COMMENT; }
  {IDENTIFIER}                   { return IDENTIFIER; }
  {LONGLITERAL}                  { return LONGLITERAL; }
  {INTLITERAL}                   { return INTLITERAL; }
  {HEX_LITERAL}                  { return HEX_LITERAL; }
  {FLOATLITERAL}                 { return FLOATLITERAL; }
  {DOUBLELITERAL}                { return DOUBLELITERAL; }
  {STRING}                       { return STRING; }
  {CHARLITERAL}                  { return CHARLITERAL; }

}

[^] { return BAD_CHARACTER; }
