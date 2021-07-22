package com.github.bertware.monkeyc_intellij.language.parser;

import com.github.bertware.monkeyc_intellij.language.psi.MonkeyTypes;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.TokenSet;

public interface MonkeyTokenTypesSets {
  TokenSet STRINGS = TokenSet.create(MonkeyTypes.STRING, MonkeyTypes.CHARLITERAL);
  TokenSet COMMENTS = TokenSet.create(MonkeyTypes.SINGLE_LINE_COMMENT, MonkeyTypes.SINGLE_LINE_DOC_COMMENT, MonkeyTypes.BLOCK_COMMENT);
  TokenSet WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE);

  TokenSet BUILT_IN_IDENTIFIERS = TokenSet.create(
    MonkeyTypes.AND,
    MonkeyTypes.AS,
    MonkeyTypes.BLING,
    MonkeyTypes.CLASS,
    MonkeyTypes.CONST,
    MonkeyTypes.DO,
    MonkeyTypes.ELSE,
    MonkeyTypes.ENUM,
    MonkeyTypes.EXTENDS,
    MonkeyTypes.FALSE,
    MonkeyTypes.FOR,
    MonkeyTypes.FUNCTION,
    MonkeyTypes.HAS,
    MonkeyTypes.HIDDEN,
    MonkeyTypes.IF,
    MonkeyTypes.INSTANCEOF,
    MonkeyTypes.MODULE,
    MonkeyTypes.NATIVE,
    MonkeyTypes.NEW,
    MonkeyTypes.NULL,
    MonkeyTypes.OR,
    MonkeyTypes.RETURN,
    MonkeyTypes.SELF,
    MonkeyTypes.STATIC,
    MonkeyTypes.THIS,
    MonkeyTypes.TRUE,
    MonkeyTypes.USING,
    MonkeyTypes.VAR,
    MonkeyTypes.WHILE
  );


}
