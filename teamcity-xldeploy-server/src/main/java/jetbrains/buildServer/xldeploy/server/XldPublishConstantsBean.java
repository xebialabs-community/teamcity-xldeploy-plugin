package jetbrains.buildServer.xldeploy.server;

import jetbrains.buildServer.xldeploy.common.XldPublishConstants;
import org.jetbrains.annotations.NotNull;

public class XldPublishConstantsBean
{

  public String getXldPublishHost()
  {
    return XldPublishConstants.SETTINGS_XLDPUBLISH_HOST;
  }

  public String getXldPublishPort()
  {
    return XldPublishConstants.SETTINGS_XLDPUBLISH_PORT;
  }

  public String getXldPublishContextRoot()
  {
    return XldPublishConstants.SETTINGS_XLDPUBLISH_CONTEXT_ROOT;
  }

  @NotNull
  public String getXldPublishUsername()
  {
    return XldPublishConstants.SETTINGS_XLDPUBLISH_USERNAME;
  }

  @NotNull
  public String getXldPublishPassword()
  {
    return XldPublishConstants.SETTINGS_XLDPUBLISH_PASSWORD;
  }

  @NotNull
  public String getXldPublishPackagePath()
  {
    return XldPublishConstants.SETTINGS_XLDPUBLISH_PACKAGE_PATH;
  }

  public String getXldPublishHttps()
  {
    return XldPublishConstants.SETTINGS_XLDPUBLISH_HTTPS;
  }

}

