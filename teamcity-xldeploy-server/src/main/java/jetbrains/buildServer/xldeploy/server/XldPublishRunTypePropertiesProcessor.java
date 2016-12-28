package jetbrains.buildServer.xldeploy.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import jetbrains.buildServer.xldeploy.common.XldPublishConstants;
import jetbrains.buildServer.serverSide.InvalidProperty;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.util.PropertiesUtil;
import jetbrains.buildServer.util.StringUtil;

public class XldPublishRunTypePropertiesProcessor implements PropertiesProcessor
{

  public Collection<InvalidProperty> process(Map<String, String> properties)
  {
    List<InvalidProperty> result = new ArrayList<InvalidProperty>();

    final String xldPublishHost = properties.get(XldPublishConstants.SETTINGS_XLDPUBLISH_HOST);
    if (PropertiesUtil.isEmptyOrNull(xldPublishHost))
    {
      result.add(new InvalidProperty(XldPublishConstants.SETTINGS_XLDPUBLISH_HOST,
              "XL Deploy host must be specified."));
    }

    final String xldPublishPort = properties.get(XldPublishConstants.SETTINGS_XLDPUBLISH_PORT);
    if (PropertiesUtil.isEmptyOrNull(xldPublishPort))
    {
      result.add(new InvalidProperty(XldPublishConstants.SETTINGS_XLDPUBLISH_PORT,
              "XL Deploy port must be specified."));
    }

    final String xldPublishUsername = properties.get(XldPublishConstants.SETTINGS_XLDPUBLISH_USERNAME);
    if (PropertiesUtil.isEmptyOrNull(xldPublishUsername))
    {
      result.add(new InvalidProperty(XldPublishConstants.SETTINGS_XLDPUBLISH_USERNAME,
              "XL Deploy username must be specified."));
    }

    final String xldPublishPassword = properties.get(XldPublishConstants.SETTINGS_XLDPUBLISH_PASSWORD);
    if (PropertiesUtil.isEmptyOrNull(xldPublishPassword))
    {
      result.add(new InvalidProperty(XldPublishConstants.SETTINGS_XLDPUBLISH_PASSWORD,
              "XL Deploy password must be specified."));
    }

    final String xldPublishPackagePath = properties.get(XldPublishConstants.SETTINGS_XLDPUBLISH_PACKAGE_PATH);
    if (PropertiesUtil.isEmptyOrNull(xldPublishPackagePath))
    {
      result.add(new InvalidProperty(XldPublishConstants.SETTINGS_XLDPUBLISH_PACKAGE_PATH,
              "Deployment Archive package path must be specified."));
    }

    return result;
  }
}
