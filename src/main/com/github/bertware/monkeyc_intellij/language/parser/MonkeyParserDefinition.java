package com.github.bertware.monkeyc_intellij.language.parser;

import com.github.bertware.monkeyc_intellij.MonkeyCLanguage;
import com.github.bertware.monkeyc_intellij.language.parser.MonkeyParser;
import com.github.bertware.monkeyc_intellij.language.psi.MonkeyTypes;
import com.github.bertware.monkeyc_intellij.language.lexer.MonkeyLexer;
import com.github.bertware.monkeyc_intellij.language.psi.impl.MonkeyDocCommentImpl;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

public class MonkeyParserDefinition implements ParserDefinition {
  public static final IFileElementType FILE = new IFileElementType(Language.findInstance(MonkeyCLanguage.class));

  @NotNull
  @Override
  public Lexer createLexer(Project project) {
    return new MonkeyLexer();
  }

  @Override
  public PsiParser createParser(Project project) {
    return new MonkeyParser();
  }

  @Override
  public IFileElementType getFileNodeType() {
    return FILE;
  }

  @NotNull
  @Override
  public TokenSet getWhitespaceTokens() {
    return MonkeyTokenTypesSets.WHITE_SPACES;
  }

  @NotNull
  @Override
  public TokenSet getCommentTokens() {
    return MonkeyTokenTypesSets.COMMENTS;
  }

  @NotNull
  @Override
  public TokenSet getStringLiteralElements() {
    return MonkeyTokenTypesSets.STRINGS;
  }

  @NotNull
  @Override
  public PsiElement createElement(ASTNode node) {
    if (node.getElementType() == MonkeyTypes.SINGLE_LINE_DOC_COMMENT) {
      return new MonkeyDocCommentImpl(node);
    }
    return MonkeyTypes.Factory.createElement(node);
  }

  @Override
  public PsiFile createFile(FileViewProvider viewProvider) {
    return new com.github.bertware.monkeyc_intellij.language.psi.MonkeyFile(viewProvider);
  }

  @Override
  public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
    return SpaceRequirements.MAY;
  }
}
