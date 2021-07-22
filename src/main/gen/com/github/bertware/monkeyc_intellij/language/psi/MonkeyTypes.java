// This is a generated file. Not intended for manual editing.
package com.github.bertware.monkeyc_intellij.language.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.github.bertware.monkeyc_intellij.language.psi.impl.*;

public interface MonkeyTypes {

  IElementType ADDITIVE_EXPRESSION = new MonkeyElementType("ADDITIVE_EXPRESSION");
  IElementType AND_EXPRESSION = new MonkeyElementType("AND_EXPRESSION");
  IElementType ANNOTATION = new MonkeyElementType("ANNOTATION");
  IElementType ARGUMENTS = new MonkeyElementType("ARGUMENTS");
  IElementType ARRAY_CREATOR = new MonkeyElementType("ARRAY_CREATOR");
  IElementType ARRAY_INITIALIZER = new MonkeyElementType("ARRAY_INITIALIZER");
  IElementType ASSIGNMENT_OPERATOR = new MonkeyElementType("ASSIGNMENT_OPERATOR");
  IElementType BITWISE_EXPRESSION = new MonkeyElementType("BITWISE_EXPRESSION");
  IElementType BITWISE_OPERATOR = new MonkeyElementType("BITWISE_OPERATOR");
  IElementType BLING_EXPRESSION = new MonkeyElementType("BLING_EXPRESSION");
  IElementType BLOCK = new MonkeyElementType("BLOCK");
  IElementType BLOCK_STATEMENT = new MonkeyElementType("BLOCK_STATEMENT");
  IElementType CATCHES = new MonkeyElementType("CATCHES");
  IElementType CATCH_CLAUSE = new MonkeyElementType("CATCH_CLAUSE");
  IElementType CATCH_PARAMETER = new MonkeyElementType("CATCH_PARAMETER");
  IElementType CLASS_BODY = new MonkeyElementType("CLASS_BODY");
  IElementType CLASS_BODY_MEMBERS = new MonkeyElementType("CLASS_BODY_MEMBERS");
  IElementType CLASS_DECLARATION = new MonkeyElementType("CLASS_DECLARATION");
  IElementType COMPONENT_NAME = new MonkeyElementType("COMPONENT_NAME");
  IElementType CONDITIONAL_AND_EXPRESSION = new MonkeyElementType("CONDITIONAL_AND_EXPRESSION");
  IElementType CONDITIONAL_EXPRESSION = new MonkeyElementType("CONDITIONAL_EXPRESSION");
  IElementType CONDITIONAL_OR_EXPRESSION = new MonkeyElementType("CONDITIONAL_OR_EXPRESSION");
  IElementType CONST_DECLARATION = new MonkeyElementType("CONST_DECLARATION");
  IElementType CREATOR = new MonkeyElementType("CREATOR");
  IElementType DICTIONARY_CREATOR = new MonkeyElementType("DICTIONARY_CREATOR");
  IElementType ENUM_CONSTANT = new MonkeyElementType("ENUM_CONSTANT");
  IElementType ENUM_DECLARATION = new MonkeyElementType("ENUM_DECLARATION");
  IElementType EQUALITY_EXPRESSION = new MonkeyElementType("EQUALITY_EXPRESSION");
  IElementType EXCLUSIVE_OR_EXPRESSION = new MonkeyElementType("EXCLUSIVE_OR_EXPRESSION");
  IElementType EXPRESSION = new MonkeyElementType("EXPRESSION");
  IElementType EXPRESSION_LIST = new MonkeyElementType("EXPRESSION_LIST");
  IElementType FIELD_DECLARATION = new MonkeyElementType("FIELD_DECLARATION");
  IElementType FIELD_DECLARATION_LIST = new MonkeyElementType("FIELD_DECLARATION_LIST");
  IElementType FORMAL_PARAMETER_DECLARATIONS = new MonkeyElementType("FORMAL_PARAMETER_DECLARATIONS");
  IElementType FOR_INIT = new MonkeyElementType("FOR_INIT");
  IElementType FOR_STATEMENT = new MonkeyElementType("FOR_STATEMENT");
  IElementType FUNCTION_DECLARATION = new MonkeyElementType("FUNCTION_DECLARATION");
  IElementType HAS_EXPRESSION = new MonkeyElementType("HAS_EXPRESSION");
  IElementType ID = new MonkeyElementType("ID");
  IElementType IDENTIFIER_SUFFIX = new MonkeyElementType("IDENTIFIER_SUFFIX");
  IElementType INCLUSIVE_OR_EXPRESSION = new MonkeyElementType("INCLUSIVE_OR_EXPRESSION");
  IElementType INSTANCE_OF_EXPRESSION = new MonkeyElementType("INSTANCE_OF_EXPRESSION");
  IElementType KEY_VALUE_INITIALIZER = new MonkeyElementType("KEY_VALUE_INITIALIZER");
  IElementType LITERAL = new MonkeyElementType("LITERAL");
  IElementType MODIFIERS = new MonkeyElementType("MODIFIERS");
  IElementType MODULE_BODY = new MonkeyElementType("MODULE_BODY");
  IElementType MODULE_BODY_MEMBERS = new MonkeyElementType("MODULE_BODY_MEMBERS");
  IElementType MODULE_DECLARATION = new MonkeyElementType("MODULE_DECLARATION");
  IElementType MULTIPLICATIVE_EXPRESSION = new MonkeyElementType("MULTIPLICATIVE_EXPRESSION");
  IElementType OBJECT_CREATOR = new MonkeyElementType("OBJECT_CREATOR");
  IElementType PAR_EXPRESSION = new MonkeyElementType("PAR_EXPRESSION");
  IElementType QUALIFIED_NAME = new MonkeyElementType("QUALIFIED_NAME");
  IElementType REFERENCE_EXPRESSION = new MonkeyElementType("REFERENCE_EXPRESSION");
  IElementType RELATIONAL_EXPRESSION = new MonkeyElementType("RELATIONAL_EXPRESSION");
  IElementType RELATIONAL_OP = new MonkeyElementType("RELATIONAL_OP");
  IElementType SELECTOR = new MonkeyElementType("SELECTOR");
  IElementType SHIFT_EXPRESSION = new MonkeyElementType("SHIFT_EXPRESSION");
  IElementType SHIFT_OP = new MonkeyElementType("SHIFT_OP");
  IElementType STATEMENT = new MonkeyElementType("STATEMENT");
  IElementType SWITCH_BLOCK_STATEMENT_GROUP = new MonkeyElementType("SWITCH_BLOCK_STATEMENT_GROUP");
  IElementType SWITCH_BLOCK_STATEMENT_GROUPS = new MonkeyElementType("SWITCH_BLOCK_STATEMENT_GROUPS");
  IElementType SWITCH_LABEL = new MonkeyElementType("SWITCH_LABEL");
  IElementType SYMBOL = new MonkeyElementType("SYMBOL");
  IElementType THIS_EXPRESSION = new MonkeyElementType("THIS_EXPRESSION");
  IElementType TRY_STATEMENT = new MonkeyElementType("TRY_STATEMENT");
  IElementType UNARY_EXPRESSION = new MonkeyElementType("UNARY_EXPRESSION");
  IElementType USING_DECLARATION = new MonkeyElementType("USING_DECLARATION");
  IElementType VARIABLE_DECLARATION = new MonkeyElementType("VARIABLE_DECLARATION");
  IElementType VARIABLE_DECLARATION_LIST = new MonkeyElementType("VARIABLE_DECLARATION_LIST");
  IElementType VARIABLE_INITIALIZER = new MonkeyElementType("VARIABLE_INITIALIZER");

  IElementType AMP = new MonkeyTokenType("&");
  IElementType AMPAMP = new MonkeyTokenType("&&");
  IElementType AMPEQ = new MonkeyTokenType("&=");
  IElementType AND = new MonkeyTokenType("and");
  IElementType AS = new MonkeyTokenType("as");
  IElementType BANG = new MonkeyTokenType("!");
  IElementType BANGEQ = new MonkeyTokenType("!=");
  IElementType BAR = new MonkeyTokenType("|");
  IElementType BARBAR = new MonkeyTokenType("||");
  IElementType BAREQ = new MonkeyTokenType("BAREQ");
  IElementType BLING = new MonkeyTokenType("$");
  IElementType BLOCK_COMMENT = new MonkeyTokenType("BLOCK_COMMENT");
  IElementType BREAK = new MonkeyTokenType("break");
  IElementType CARET = new MonkeyTokenType("^");
  IElementType CARETEQ = new MonkeyTokenType("|=");
  IElementType CASE = new MonkeyTokenType("case");
  IElementType CATCH = new MonkeyTokenType("catch");
  IElementType CHARLITERAL = new MonkeyTokenType("CHARLITERAL");
  IElementType CLASS = new MonkeyTokenType("class");
  IElementType COLON = new MonkeyTokenType(":");
  IElementType COMMA = new MonkeyTokenType(",");
  IElementType CONST = new MonkeyTokenType("const");
  IElementType CONTINUE = new MonkeyTokenType("continue");
  IElementType DEFAULT = new MonkeyTokenType("default");
  IElementType DO = new MonkeyTokenType("do");
  IElementType DOT = new MonkeyTokenType(".");
  IElementType DOUBLELITERAL = new MonkeyTokenType("DOUBLELITERAL");
  IElementType ELSE = new MonkeyTokenType("else");
  IElementType ENUM = new MonkeyTokenType("enum");
  IElementType EQ = new MonkeyTokenType("=");
  IElementType EQEQ = new MonkeyTokenType("==");
  IElementType EQGT = new MonkeyTokenType("=>");
  IElementType EXTENDS = new MonkeyTokenType("extends");
  IElementType FALSE = new MonkeyTokenType("false");
  IElementType FINALLY = new MonkeyTokenType("finally");
  IElementType FLOATLITERAL = new MonkeyTokenType("FLOATLITERAL");
  IElementType FOR = new MonkeyTokenType("for");
  IElementType FUNCTION = new MonkeyTokenType("function");
  IElementType GT = new MonkeyTokenType(">");
  IElementType HAS = new MonkeyTokenType("has");
  IElementType HEX_LITERAL = new MonkeyTokenType("HEX_LITERAL");
  IElementType HIDDEN = new MonkeyTokenType("hidden");
  IElementType IDENTIFIER = new MonkeyTokenType("IDENTIFIER");
  IElementType IF = new MonkeyTokenType("if");
  IElementType INSTANCEOF = new MonkeyTokenType("instanceof");
  IElementType INTLITERAL = new MonkeyTokenType("INTLITERAL");
  IElementType LBRACE = new MonkeyTokenType("{");
  IElementType LBRACKET = new MonkeyTokenType("[");
  IElementType LONGLITERAL = new MonkeyTokenType("LONGLITERAL");
  IElementType LPAREN = new MonkeyTokenType("(");
  IElementType LT = new MonkeyTokenType("<");
  IElementType MODULE = new MonkeyTokenType("module");
  IElementType MULTI_LINE_COMMENT_END = new MonkeyTokenType("*/");
  IElementType MULTI_LINE_COMMENT_START = new MonkeyTokenType("/*");
  IElementType NATIVE = new MonkeyTokenType("native");
  IElementType NEW = new MonkeyTokenType("new");
  IElementType NULL = new MonkeyTokenType("null");
  IElementType OR = new MonkeyTokenType("or");
  IElementType PERCENT = new MonkeyTokenType("%");
  IElementType PERCENTEQ = new MonkeyTokenType("%=");
  IElementType PLUS = new MonkeyTokenType("+");
  IElementType PLUSEQ = new MonkeyTokenType("+=");
  IElementType PLUSPLUS = new MonkeyTokenType("++");
  IElementType QUES = new MonkeyTokenType("?");
  IElementType RBRACE = new MonkeyTokenType("}");
  IElementType RBRACKET = new MonkeyTokenType("]");
  IElementType RETURN = new MonkeyTokenType("return");
  IElementType RPAREN = new MonkeyTokenType(")");
  IElementType SELF = new MonkeyTokenType("self");
  IElementType SEMI = new MonkeyTokenType(";");
  IElementType SINGLE_LINE_COMMENT = new MonkeyTokenType("SINGLE_LINE_COMMENT");
  IElementType SINGLE_LINE_DOC_COMMENT = new MonkeyTokenType("SINGLE_LINE_DOC_COMMENT");
  IElementType SLASH = new MonkeyTokenType("/");
  IElementType SLASHEQ = new MonkeyTokenType("/=");
  IElementType STAR = new MonkeyTokenType("*");
  IElementType STAREQ = new MonkeyTokenType("*=");
  IElementType STATIC = new MonkeyTokenType("static");
  IElementType STRING = new MonkeyTokenType("string");
  IElementType STRING_A = new MonkeyTokenType("\"");
  IElementType STRING_B = new MonkeyTokenType("'");
  IElementType SUB = new MonkeyTokenType("-");
  IElementType SUBEQ = new MonkeyTokenType("-=");
  IElementType SUBSUB = new MonkeyTokenType("--");
  IElementType SUPER = new MonkeyTokenType("SUPER");
  IElementType SWITCH = new MonkeyTokenType("switch");
  IElementType THIS = new MonkeyTokenType("this");
  IElementType THROW = new MonkeyTokenType("throw");
  IElementType TILDE = new MonkeyTokenType("~");
  IElementType TRUE = new MonkeyTokenType("true");
  IElementType TRY = new MonkeyTokenType("try");
  IElementType USING = new MonkeyTokenType("using");
  IElementType VAR = new MonkeyTokenType("var");
  IElementType VOID = new MonkeyTokenType("VOID");
  IElementType WHILE = new MonkeyTokenType("while");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ADDITIVE_EXPRESSION) {
        return new MonkeyAdditiveExpressionImpl(node);
      }
      else if (type == AND_EXPRESSION) {
        return new MonkeyAndExpressionImpl(node);
      }
      else if (type == ANNOTATION) {
        return new MonkeyAnnotationImpl(node);
      }
      else if (type == ARGUMENTS) {
        return new MonkeyArgumentsImpl(node);
      }
      else if (type == ARRAY_CREATOR) {
        return new MonkeyArrayCreatorImpl(node);
      }
      else if (type == ARRAY_INITIALIZER) {
        return new MonkeyArrayInitializerImpl(node);
      }
      else if (type == ASSIGNMENT_OPERATOR) {
        return new MonkeyAssignmentOperatorImpl(node);
      }
      else if (type == BITWISE_EXPRESSION) {
        return new MonkeyBitwiseExpressionImpl(node);
      }
      else if (type == BITWISE_OPERATOR) {
        return new MonkeyBitwiseOperatorImpl(node);
      }
      else if (type == BLING_EXPRESSION) {
        return new MonkeyBlingExpressionImpl(node);
      }
      else if (type == BLOCK) {
        return new MonkeyBlockImpl(node);
      }
      else if (type == BLOCK_STATEMENT) {
        return new MonkeyBlockStatementImpl(node);
      }
      else if (type == CATCHES) {
        return new MonkeyCatchesImpl(node);
      }
      else if (type == CATCH_CLAUSE) {
        return new MonkeyCatchClauseImpl(node);
      }
      else if (type == CATCH_PARAMETER) {
        return new MonkeyCatchParameterImpl(node);
      }
      else if (type == CLASS_BODY) {
        return new MonkeyClassBodyImpl(node);
      }
      else if (type == CLASS_BODY_MEMBERS) {
        return new MonkeyClassBodyMembersImpl(node);
      }
      else if (type == CLASS_DECLARATION) {
        return new MonkeyClassDeclarationImpl(node);
      }
      else if (type == COMPONENT_NAME) {
        return new MonkeyComponentNameImpl(node);
      }
      else if (type == CONDITIONAL_AND_EXPRESSION) {
        return new MonkeyConditionalAndExpressionImpl(node);
      }
      else if (type == CONDITIONAL_EXPRESSION) {
        return new MonkeyConditionalExpressionImpl(node);
      }
      else if (type == CONDITIONAL_OR_EXPRESSION) {
        return new MonkeyConditionalOrExpressionImpl(node);
      }
      else if (type == CONST_DECLARATION) {
        return new MonkeyConstDeclarationImpl(node);
      }
      else if (type == CREATOR) {
        return new MonkeyCreatorImpl(node);
      }
      else if (type == DICTIONARY_CREATOR) {
        return new MonkeyDictionaryCreatorImpl(node);
      }
      else if (type == ENUM_CONSTANT) {
        return new MonkeyEnumConstantImpl(node);
      }
      else if (type == ENUM_DECLARATION) {
        return new MonkeyEnumDeclarationImpl(node);
      }
      else if (type == EQUALITY_EXPRESSION) {
        return new MonkeyEqualityExpressionImpl(node);
      }
      else if (type == EXCLUSIVE_OR_EXPRESSION) {
        return new MonkeyExclusiveOrExpressionImpl(node);
      }
      else if (type == EXPRESSION) {
        return new MonkeyExpressionImpl(node);
      }
      else if (type == EXPRESSION_LIST) {
        return new MonkeyExpressionListImpl(node);
      }
      else if (type == FIELD_DECLARATION) {
        return new MonkeyFieldDeclarationImpl(node);
      }
      else if (type == FIELD_DECLARATION_LIST) {
        return new MonkeyFieldDeclarationListImpl(node);
      }
      else if (type == FORMAL_PARAMETER_DECLARATIONS) {
        return new MonkeyFormalParameterDeclarationsImpl(node);
      }
      else if (type == FOR_INIT) {
        return new MonkeyForInitImpl(node);
      }
      else if (type == FOR_STATEMENT) {
        return new MonkeyForStatementImpl(node);
      }
      else if (type == FUNCTION_DECLARATION) {
        return new MonkeyFunctionDeclarationImpl(node);
      }
      else if (type == HAS_EXPRESSION) {
        return new MonkeyHasExpressionImpl(node);
      }
      else if (type == ID) {
        return new MonkeyIdImpl(node);
      }
      else if (type == IDENTIFIER_SUFFIX) {
        return new MonkeyIdentifierSuffixImpl(node);
      }
      else if (type == INCLUSIVE_OR_EXPRESSION) {
        return new MonkeyInclusiveOrExpressionImpl(node);
      }
      else if (type == INSTANCE_OF_EXPRESSION) {
        return new MonkeyInstanceOfExpressionImpl(node);
      }
      else if (type == KEY_VALUE_INITIALIZER) {
        return new MonkeyKeyValueInitializerImpl(node);
      }
      else if (type == LITERAL) {
        return new MonkeyLiteralImpl(node);
      }
      else if (type == MODIFIERS) {
        return new MonkeyModifiersImpl(node);
      }
      else if (type == MODULE_BODY) {
        return new MonkeyModuleBodyImpl(node);
      }
      else if (type == MODULE_BODY_MEMBERS) {
        return new MonkeyModuleBodyMembersImpl(node);
      }
      else if (type == MODULE_DECLARATION) {
        return new MonkeyModuleDeclarationImpl(node);
      }
      else if (type == MULTIPLICATIVE_EXPRESSION) {
        return new MonkeyMultiplicativeExpressionImpl(node);
      }
      else if (type == OBJECT_CREATOR) {
        return new MonkeyObjectCreatorImpl(node);
      }
      else if (type == PAR_EXPRESSION) {
        return new MonkeyParExpressionImpl(node);
      }
      else if (type == QUALIFIED_NAME) {
        return new MonkeyQualifiedNameImpl(node);
      }
      else if (type == REFERENCE_EXPRESSION) {
        return new MonkeyReferenceExpressionImpl(node);
      }
      else if (type == RELATIONAL_EXPRESSION) {
        return new MonkeyRelationalExpressionImpl(node);
      }
      else if (type == RELATIONAL_OP) {
        return new MonkeyRelationalOpImpl(node);
      }
      else if (type == SELECTOR) {
        return new MonkeySelectorImpl(node);
      }
      else if (type == SHIFT_EXPRESSION) {
        return new MonkeyShiftExpressionImpl(node);
      }
      else if (type == SHIFT_OP) {
        return new MonkeyShiftOpImpl(node);
      }
      else if (type == STATEMENT) {
        return new MonkeyStatementImpl(node);
      }
      else if (type == SWITCH_BLOCK_STATEMENT_GROUP) {
        return new MonkeySwitchBlockStatementGroupImpl(node);
      }
      else if (type == SWITCH_BLOCK_STATEMENT_GROUPS) {
        return new MonkeySwitchBlockStatementGroupsImpl(node);
      }
      else if (type == SWITCH_LABEL) {
        return new MonkeySwitchLabelImpl(node);
      }
      else if (type == SYMBOL) {
        return new MonkeySymbolImpl(node);
      }
      else if (type == THIS_EXPRESSION) {
        return new MonkeyThisExpressionImpl(node);
      }
      else if (type == TRY_STATEMENT) {
        return new MonkeyTryStatementImpl(node);
      }
      else if (type == UNARY_EXPRESSION) {
        return new MonkeyUnaryExpressionImpl(node);
      }
      else if (type == USING_DECLARATION) {
        return new MonkeyUsingDeclarationImpl(node);
      }
      else if (type == VARIABLE_DECLARATION) {
        return new MonkeyVariableDeclarationImpl(node);
      }
      else if (type == VARIABLE_DECLARATION_LIST) {
        return new MonkeyVariableDeclarationListImpl(node);
      }
      else if (type == VARIABLE_INITIALIZER) {
        return new MonkeyVariableInitializerImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
