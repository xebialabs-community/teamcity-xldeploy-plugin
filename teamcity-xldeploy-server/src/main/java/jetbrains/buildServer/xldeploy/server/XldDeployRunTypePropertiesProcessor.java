package jetbrains.buildServer.xldeploy.server;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import jetbrains.buildServer.xldeploy.common.XldDeployConstants;
import jetbrains.buildServer.serverSide.InvalidProperty;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.util.PropertiesUtil;
import jetbrains.buildServer.util.StringUtil;

public class XldDeployRunTypePropertiesProcessor implements PropertiesProcessor
{

  public Collection<InvalidProperty> process(Map<String, String> properties)
  {
    List<InvalidProperty> result = new ArrayList<InvalidProperty>();

    final String xldDeployUsername = properties.get(XldDeployConstants.SETTINGS_XLDDEPLOY_USERNAME);
    if (PropertiesUtil.isEmptyOrNull(xldDeployUsername))
    {
      result.add(new InvalidProperty(XldDeployConstants.SETTINGS_XLDDEPLOY_USERNAME,
              "XL Deploy username must be specified."));
    }

    final String xldDeployPassword = properties.get(XldDeployConstants.SETTINGS_XLDDEPLOY_PASSWORD);
    if (PropertiesUtil.isEmptyOrNull(xldDeployPassword))
    {
      result.add(new InvalidProperty(XldDeployConstants.SETTINGS_XLDDEPLOY_PASSWORD,
              "XL Deploy password must be specified."));
    }

    final String xldDeployApplicationName = properties.get(XldDeployConstants.SETTINGS_XLDDEPLOY_APPLICATION_NAME);
    if (PropertiesUtil.isEmptyOrNull(xldDeployApplicationName))
    {
      result.add(new InvalidProperty(XldDeployConstants.SETTINGS_XLDDEPLOY_APPLICATION_NAME,
              "Application name must be specified."));
    }

    final String xldDeployVersionName = properties.get(XldDeployConstants.SETTINGS_XLDDEPLOY_VERSION_NAME);
    if (PropertiesUtil.isEmptyOrNull(xldDeployVersionName))
    {
      result.add(new InvalidProperty(XldDeployConstants.SETTINGS_XLDDEPLOY_VERSION_NAME,
              "Version name must be specified."));
    }

    final String xldDeployEnvironmentId = properties.get(XldDeployConstants.SETTINGS_XLDDEPLOY_ENVIRONMENT_ID);
    if (PropertiesUtil.isEmptyOrNull(xldDeployEnvironmentId))
    {
      result.add(new InvalidProperty(XldDeployConstants.SETTINGS_XLDDEPLOY_ENVIRONMENT_ID,
              "Version name must be specified."));
    }

    return result;
  }
}
