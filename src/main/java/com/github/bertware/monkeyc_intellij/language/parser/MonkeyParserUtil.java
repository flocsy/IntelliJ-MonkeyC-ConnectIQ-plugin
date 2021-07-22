package com.github.bertware.monkeyc_intellij.language.parser;

import com.github.bertware.monkeyc_intellij.language.psi.MonkeyTypes;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.parser.GeneratedParserUtilBase;

public class MonkeyParserUtil extends GeneratedParserUtilBase {
  public static boolean strictID(PsiBuilder builder_, int level_) {
    final PsiBuilder.Marker marker_ = builder_.mark();
    final boolean result_ = consumeToken(builder_, MonkeyTypes.IDENTIFIER);
    if (result_) {
      marker_.done(MonkeyTypes.ID);
      return true;
    }
    marker_.rollbackTo();
    return false;
  }
}
