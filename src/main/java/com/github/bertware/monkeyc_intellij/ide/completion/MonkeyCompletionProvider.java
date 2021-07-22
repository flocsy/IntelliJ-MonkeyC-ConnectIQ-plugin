package com.github.bertware.monkeyc_intellij.ide.completion;

import com.github.bertware.monkeyc_intellij.language.psi.MonkeyClass;
import com.github.bertware.monkeyc_intellij.language.psi.MonkeyComponentName;
import com.github.bertware.monkeyc_intellij.language.psi.MonkeyReference;
import com.github.bertware.monkeyc_intellij.language.resolve.ComponentNameScopeProcessor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.ResolveState;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import gnu.trove.THashSet;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Set;

public class MonkeyCompletionProvider extends CompletionProvider<CompletionParameters> {
  @Override
  protected void addCompletions(@NotNull CompletionParameters parameters, final ProcessingContext context, @NotNull CompletionResultSet result) {
    MonkeyReference reference = PsiTreeUtil.getParentOfType(parameters.getPosition(), MonkeyReference.class);
    if (reference != null) {
      THashSet<MonkeyComponentName> variants = new THashSet<>();

      ComponentNameScopeProcessor processor = new ComponentNameScopeProcessor(variants);
      PsiTreeUtil.treeWalkUp(processor, reference, null, ResolveState.initial());
      MonkeyClass monkeyClass = PsiTreeUtil.getParentOfType(reference, MonkeyClass.class);

      appendVariantsToCompletionSet(result, variants);
    }
  }


  public static Set<String> appendVariantsToCompletionSet(@NotNull final CompletionResultSet result,
                                                          @NotNull final Collection<MonkeyComponentName> variants) {
    final Set<String> addedNames = new THashSet<>();
    for (MonkeyComponentName componentName : variants) {
      LookupElement lookupElement = LookupElementBuilder.create(componentName);
      //.withTypeText("int");

      if (addedNames.add(lookupElement.getLookupString())) {
        result.addElement(lookupElement);
      }
    }
    return addedNames;
  }
}
