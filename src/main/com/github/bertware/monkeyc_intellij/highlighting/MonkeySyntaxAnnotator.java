package com.github.bertware.monkeyc_intellij.highlighting;

import com.intellij.codeInsight.CodeInsightUtilCore;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.project.DumbAware;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiRecursiveElementVisitor;
import org.jetbrains.annotations.NotNull;

// Btw, if feeling confused if you should use Annotator or HighlightVisitor, then answer is Annotator, quoting:
// "Annotator API was created specifically for use by third-party language plugins, whereas HighlightVisitor is what IDEA itself originally used"
public class MonkeySyntaxAnnotator implements Annotator, DumbAware {
    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        element.accept(new PsiRecursiveElementVisitor() {
            @Override
            public void visitElement(@NotNull PsiElement element) {
                if (element instanceof com.github.bertware.monkeyc_intellij.language.psi.MonkeyAnnotation) {
                    highlightPsiElement(element, holder, MonkeySyntaxHighlighter.MC_SYMBOL);
                } else if (element instanceof com.github.bertware.monkeyc_intellij.language.psi.MonkeySymbol) {
                    highlightPsiElement(element, holder, MonkeySyntaxHighlighter.MC_SYMBOL);
                } else if (element instanceof com.github.bertware.monkeyc_intellij.language.psi.MonkeyConstDeclaration) {
                    // TODO: would be nice to highlight usage (reference) as well
                    com.github.bertware.monkeyc_intellij.language.psi.MonkeyConstDeclaration monkeyConstDeclaration = (com.github.bertware.monkeyc_intellij.language.psi.MonkeyConstDeclaration) element;
                    highlightPsiElement(monkeyConstDeclaration.getComponentName(), holder, MonkeySyntaxHighlighter.MC_CONSTANT);
                } else if (element instanceof com.github.bertware.monkeyc_intellij.language.psi.MonkeyLiteral) {
                    com.github.bertware.monkeyc_intellij.language.psi.MonkeyLiteral monkeyLiteral = (com.github.bertware.monkeyc_intellij.language.psi.MonkeyLiteral) element;
                    PsiElement charliteral = monkeyLiteral.getCharliteral();
                    if (charliteral != null) {
                        annotateCharLiteral(holder, charliteral);
                    }
                }
            }
        });
    }

    private static void highlightPsiElement(PsiElement element, @NotNull AnnotationHolder holder, @NotNull TextAttributesKey colorKey) {
        TextAttributes enforcedTextAttributes = EditorColorsManager.getInstance().getGlobalScheme().getAttributes(colorKey);
        holder.createInfoAnnotation(element, null)
                .setEnforcedTextAttributes(enforcedTextAttributes);
    }

    // TODO: this is not just syntax annotator but error annotation..., so rename the class or move this to some other annotator?
    private static void annotateCharLiteral(@NotNull AnnotationHolder holder, PsiElement charliteral) {
        String text = charliteral.getText();
        if (text != null && text.length() >= 2) {
            if (text.startsWith("'") && text.endsWith("'")) {
                String withoutQuotes = text.substring(1, text.length() - 1);
                StringBuilder chars = new StringBuilder();
                boolean success = CodeInsightUtilCore.parseStringCharacters(withoutQuotes, chars, null);
                if (!success) {
                    String message = "Illegal escape character in character literal";
                    holder.newAnnotation(HighlightSeverity.ERROR, message).range(charliteral).create();
                }
                int length = chars.length();
                if (length > 1) {
                    String message = "Too many characters in character literal";
                    holder.newAnnotation(HighlightSeverity.ERROR, message).range(charliteral).create();
                } else if (length == 0) {
                    String message = "Empty character literal";
                    holder.newAnnotation(HighlightSeverity.ERROR, message).range(charliteral).create();
                }
            }
        }
    }
}
