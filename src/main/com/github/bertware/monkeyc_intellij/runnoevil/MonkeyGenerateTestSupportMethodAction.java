package com.github.bertware.monkeyc_intellij.runnoevil;

import com.github.bertware.monkeyc_intellij.MonkeyCFileType;
import com.intellij.codeInsight.template.Expression;
import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.impl.ConstantNode;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.testIntegration.TestFramework;
import com.intellij.testIntegration.TestIntegrationUtils;
import org.jetbrains.annotations.NotNull;

public class MonkeyGenerateTestSupportMethodAction extends AnAction {
    protected static final Logger LOG = Logger.getInstance("#" + MonkeyGenerateTestSupportMethodAction.class.getName());

    public MonkeyGenerateTestSupportMethodAction() {
        super();
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        boolean isMonkeyCFile = e.getProject() != null
                && e.getProject().getProjectFile() != null
                && e.getProject().getProjectFile().getFileType() == MonkeyCFileType.INSTANCE;
        e.getPresentation().setEnabledAndVisible(isMonkeyCFile);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        if (anActionEvent.getProject() == null) {
            return;
        }
        MonkeyTestFramework framework = new MonkeyTestFramework();
        Template template = createTestMethodTemplate(TestIntegrationUtils.MethodKind.TEST_CLASS, framework, anActionEvent.getProject(), null, true);
        // TODO: Implement creation/opening of test class
    }

    private static Template createTestMethodTemplate(TestIntegrationUtils.MethodKind methodKind, TestFramework testFramework, Project project, String name, boolean automatic) {
        FileTemplateDescriptor templateDesc = methodKind.getFileTemplateDescriptor(testFramework);
        String templateName = templateDesc.getFileName();
        FileTemplate fileTemplate = FileTemplateManager.getInstance(project).getCodeTemplate(templateName);
        Template template = TemplateManager.getInstance(project).createTemplate("", "");

        String templateText = fileTemplate.getText();
        if (name == null) name = methodKind.getDefaultName();
        templateText = StringUtil.replace(templateText, "${BODY}", "");

        int from = 0;
        while (true) {
            int index = templateText.indexOf("${NAME}", from);
            if (index == -1) break;

            template.addTextSegment(templateText.substring(from, index));

            if (index > 0 && !Character.isWhitespace(templateText.charAt(index - 1))) {
                name = StringUtil.capitalize(name);
            } else {
                name = StringUtil.decapitalize(name);
            }
            if (from == 0) {
                Expression nameExpr = new ConstantNode(name);
                template.addVariable("name", nameExpr, nameExpr, !automatic);
            } else {
                template.addVariableSegment("name");
            }

            from = index + "${NAME}".length();
        }
        template.addTextSegment(templateText.substring(from, templateText.length()));
        template.setToIndent(true);
        template.setToReformat(true);
        template.setToShortenLongNames(true);

        return template;
    }


}
