package com.github.bertware.monkeyc_intellij.project.ui.module.newProject;

import com.github.bertware.monkeyc_intellij.MonkeyCIcons;
import com.github.bertware.monkeyc_intellij.project.module.MonkeyModuleBuilder;
import com.github.bertware.monkeyc_intellij.project.ui.module.AppType;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.platform.ProjectTemplate;
import com.intellij.platform.ProjectTemplatesFactory;
import com.intellij.platform.templates.BuilderBasedTemplate;



import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class MonkeyProjectTemplatesFactory extends ProjectTemplatesFactory {
  @NotNull
  @Override
  public String[] getGroups() {
    return new String[]{"Connect IQ"};
  }

  @Override
  public String getParentGroup(String group) {
    return null;
  }

  @Override
  public Icon getGroupIcon(String group) {
    return MonkeyCIcons.MODULE16;
  }

  @NotNull
  @Override
  public ProjectTemplate[] createTemplates(@Nullable String group, WizardContext context) {
    ProjectTemplate[] templates = {
        new MonkeyProjectTemplate("Watch App", "Watch apps have full control of the device", new MonkeyModuleBuilder(AppType.WATCH_APP)),
        new MonkeyProjectTemplate("Widget", "Widgets provide information at-a-glance to the user", new MonkeyModuleBuilder(AppType.WIDGET)),
        new MonkeyProjectTemplate("Data Field", "Data fields allow customization of Garmin activities", new MonkeyModuleBuilder(AppType.DATA_FIELD)),
        new MonkeyProjectTemplate("Watch Face", "Watch faces provide custom displays for the home screen of the watch", new MonkeyModuleBuilder(AppType.WATCH_FACE)),
    };
    return templates;
  }

  private static class MonkeyProjectTemplate extends BuilderBasedTemplate {

    private final String myName;
    private final String myDescription;

    private MonkeyProjectTemplate(String name, String description, MonkeyModuleBuilder builder) {
      super(builder);
      myName = name;
      myDescription = description;
    }

    @NotNull
    @Override
    public String getName() {
      return myName;
    }

    @Nullable
    @Override
    public String getDescription() {
      return myDescription;
    }
  }
}
