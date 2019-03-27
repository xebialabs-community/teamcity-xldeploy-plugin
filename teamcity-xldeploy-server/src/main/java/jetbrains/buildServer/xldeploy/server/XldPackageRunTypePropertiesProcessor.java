package jetbrains.buildServer.xldeploy.server;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import jetbrains.buildServer.xldeploy.common.XldPackageConstants;
import jetbrains.buildServer.serverSide.InvalidProperty;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.util.PropertiesUtil;
import jetbrains.buildServer.util.StringUtil;

public class XldPackageRunTypePropertiesProcessor implements PropertiesProcessor
{

  public Collection<InvalidProperty> process(Map<String, String> properties)
  {
    List<InvalidProperty> result = new ArrayList<InvalidProperty>();

    final String xldPackageUsername = properties.get(XldPackageConstants.SETTINGS_XLDPACKAGE_USERNAME);
    if (PropertiesUtil.isEmptyOrNull(xldPackageUsername))
    {
      result.add(new InvalidProperty(XldPackageConstants.SETTINGS_XLDPACKAGE_USERNAME,
              "XL Deploy username must be specified."));
    }

    final String xldPackagePassword = properties.get(XldPackageConstants.SETTINGS_XLDPACKAGE_PASSWORD);
    if (PropertiesUtil.isEmptyOrNull(xldPackagePassword))
    {
      result.add(new InvalidProperty(XldPackageConstants.SETTINGS_XLDPACKAGE_PASSWORD,
              "XL Deploy password must be specified."));
    }

    final String xldPackageApplicationName = properties.get(XldPackageConstants.SETTINGS_XLDPACKAGE_APPLICATION_NAME);
    if (PropertiesUtil.isEmptyOrNull(xldPackageApplicationName))
    {
      result.add(new InvalidProperty(XldPackageConstants.SETTINGS_XLDPACKAGE_APPLICATION_NAME,
              "Application name must be specified."));
    }

    final String xldPackageVersionName = properties.get(XldPackageConstants.SETTINGS_XLDPACKAGE_VERSION_NAME);
    if (PropertiesUtil.isEmptyOrNull(xldPackageVersionName))
    {
      result.add(new InvalidProperty(XldPackageConstants.SETTINGS_XLDPACKAGE_VERSION_NAME,
              "Version name must be specified."));
    }

    return result;
  }
}
