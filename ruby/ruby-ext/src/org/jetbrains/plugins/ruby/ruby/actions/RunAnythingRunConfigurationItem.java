package org.jetbrains.plugins.ruby.ruby.actions;

import com.intellij.execution.Executor;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.actions.ChooseRunConfigurationPopup;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import com.intellij.util.ObjectUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class RunAnythingRunConfigurationItem extends RunAnythingItem<ChooseRunConfigurationPopup.ItemWrapper> {
  @NotNull private final ChooseRunConfigurationPopup.ItemWrapper myWrapper;

  public RunAnythingRunConfigurationItem(@NotNull ChooseRunConfigurationPopup.ItemWrapper wrapper) {
    myWrapper = wrapper;
  }

  @Override
  public void run(@NotNull Project project, @NotNull DataContext dataContext) {
    super.run(project, dataContext);

    Object value = myWrapper.getValue();
    if (value instanceof RunnerAndConfigurationSettings) {
      Executor runExecutor = RunAnythingUtil.findExecutor((RunnerAndConfigurationSettings)value);
      if (runExecutor != null) {
        myWrapper.perform(project, runExecutor, dataContext);
      }
    }
  }

  @NotNull
  @Override
  public String getText() {
    return myWrapper.getText();
  }

  @Override
  public void triggerUsage() {
    RunAnythingUtil.triggerDebuggerStatistics();
  }

  @NotNull
  @Override
  public Icon getIcon() {
    return ObjectUtils.notNull(myWrapper.getIcon(), AllIcons.RunConfigurations.Unknown);
  }

  @NotNull
  @Override
  public ChooseRunConfigurationPopup.ItemWrapper getValue() {
    return myWrapper;
  }

  @NotNull
  @Override
  public Component createComponent(boolean isSelected) {
    return RunAnythingUtil.createRunConfigurationCellRendererComponent(myWrapper, isSelected);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RunAnythingRunConfigurationItem item = (RunAnythingRunConfigurationItem)o;
    return Objects.equals(myWrapper, item.myWrapper);
  }

  @Override
  public int hashCode() {
    return Objects.hash(myWrapper);
  }
}