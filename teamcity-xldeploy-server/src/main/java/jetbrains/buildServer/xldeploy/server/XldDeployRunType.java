package jetbrains.buildServer.xldeploy.server;

import java.util.HashMap;
import java.util.Map;
import jetbrains.buildServer.xldeploy.common.XldDeployConstants;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.serverSide.RunType;
import jetbrains.buildServer.serverSide.RunTypeRegistry;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import org.jetbrains.annotations.NotNull;

public class XldDeployRunType extends RunType
{

  private final PluginDescriptor myPluginDescriptor;

  public XldDeployRunType(final RunTypeRegistry runTypeRegistry, final PluginDescriptor pluginDescriptor)
  {
    myPluginDescriptor = pluginDescriptor;
    runTypeRegistry.registerRunType(this);
  }

  @Override
  public PropertiesProcessor getRunnerPropertiesProcessor()
  {
    return new XldDeployRunTypePropertiesProcessor();
  }

  @NotNull
  @Override
  public String getDescription()
  {
    return XldDeployConstants.RUNNER_DESCRIPTION;
  }

  @Override
  public String getEditRunnerParamsJspFilePath()
  {
    return myPluginDescriptor.getPluginResourcesPath("editXldDeployRunParams.jsp");
  }

  @Override
  public String getViewRunnerParamsJspFilePath()
  {
    return myPluginDescriptor.getPluginResourcesPath("viewXldDeployRunParams.jsp");
  }

  @Override
  public Map<String, String> getDefaultRunnerProperties()
  {
    Map<String, String> parameters = new HashMap<String, String>();
    return parameters;
  }

  @Override
  @NotNull
  public String getType()
  {
    return XldDeployConstants.RUNNER_TYPE;
  }

  @NotNull
  @Override
  public String getDisplayName()
  {
    return XldDeployConstants.RUNNER_DISPLAY_NAME;
  }

  @NotNull
  @Override
  public String describeParameters(@NotNull final Map<String, String> parameters)
  {
    StringBuilder result = new StringBuilder();
    return result.toString();
  }
}

