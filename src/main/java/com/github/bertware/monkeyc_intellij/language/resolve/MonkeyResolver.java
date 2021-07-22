package com.github.bertware.monkeyc_intellij.language.resolve;

import com.github.bertware.monkeyc_intellij.MonkeyCFileType;
import com.github.bertware.monkeyc_intellij.language.psi.MonkeyComponentName;
import com.github.bertware.monkeyc_intellij.language.psi.MonkeyFile;
import com.github.bertware.monkeyc_intellij.language.psi.MonkeyReference;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.ResolveState;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.indexing.FileBasedIndex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MonkeyResolver implements ResolveCache.AbstractResolver<MonkeyReference, List<? extends PsiElement>> {
  public static final MonkeyResolver INSTANCE = new MonkeyResolver();

  @Nullable
  @Override
  public List<? extends PsiElement> resolve(@NotNull MonkeyReference reference, boolean incompleteCode) {
    // for some reason, aa.bb() reference for bb in param info, has canonical text ".bb" instead of bb
    // finding the child gives the correct reference
    MonkeyReference[] references = PsiTreeUtil.getChildrenOfType(reference, MonkeyReference.class);

    if (references != null && references.length > 0) {
      MonkeyReference reference1 = references[0];
      return resolveSimpleReference(reference1, reference1.getCanonicalText());
    } else {
      return resolveSimpleReference(reference, reference.getCanonicalText());
    }
  }

  @NotNull
  public static List<? extends PsiElement> resolveSimpleReference(@NotNull final PsiElement scopeElement, @NotNull final String name) {
    final List<MonkeyComponentName> result = new ArrayList<>();
    // local
    final MonkeyResolveProcessor resolveProcessor = new MonkeyResolveProcessor(result, name);
    PsiTreeUtil.treeWalkUp(resolveProcessor, scopeElement, null, ResolveState.initial());

    // global
    if (result.isEmpty()) {
      //GlobalSearchScope resolveScope = scopeElement.getResolveScope();

      Project project = scopeElement.getProject();
      GlobalSearchScope filter1 = GlobalSearchScope.allScope(project);
      Collection<VirtualFile> sourceFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, MonkeyCFileType.INSTANCE, filter1);

      for (VirtualFile sourceFile : sourceFiles) {
        MonkeyFile monkeyFile = (MonkeyFile) PsiManager.getInstance(project).findFile(sourceFile);
        if (monkeyFile != null) {
          final MonkeyResolveProcessor resolveProcessor2 = new MonkeyResolveProcessor(result, name);
          PsiTreeUtil.treeWalkUp(resolveProcessor2, monkeyFile, null, ResolveState.initial());
        }
      }


    }

    // todo: add super, global, etc (check monkey c docs for order)

    /*
    Scoping
    Monkey C is a message-passed language. When a function is called, the virtual machine does a look up operation
    at runtime to find the function being called. Here is the hierarchy that it will search:

    1. Instance members of the class
    2. Members of the superclass
    3. Static members of the class
    4. Members of the parent module, and the parent modules up to the global namespace
    5. Members of the superclass’s parent module up to the global namespace
    */

    // You can bring a module into your scoping level with the using keyword.
    // using allows a module to be imported into another class or module by a symbol.
    // using statements are scoped to the class or module in which they are defined.


    return result;
  }
}
