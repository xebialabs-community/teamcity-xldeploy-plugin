package jetbrains.buildServer.xldeploy.server;

import jetbrains.buildServer.xldeploy.common.XldPackageConstants;
import org.jetbrains.annotations.NotNull;

public class XldPackageConstantsBean
{

  public String getXldPackageHost()
  {
    return XldPackageConstants.SETTINGS_XLDPACKAGE_HOST;
  }

  public String getXldPackagePort()
  {
    return XldPackageConstants.SETTINGS_XLDPACKAGE_PORT;
  }

  @NotNull
  public String getXldPackageUsername()
  {
    return XldPackageConstants.SETTINGS_XLDPACKAGE_USERNAME;
  }

  @NotNull
  public String getXldPackagePassword()
  {
    return XldPackageConstants.SETTINGS_XLDPACKAGE_PASSWORD;
  }

  @NotNull
  public String getXldPackageApplicationName()
  {
    return XldPackageConstants.SETTINGS_XLDPACKAGE_APPLICATION_NAME;
  }

  @NotNull
  public String getXldPackageVersionName()
  {
    return XldPackageConstants.SETTINGS_XLDPACKAGE_VERSION_NAME;
  }

  public String getXldPackageHttps()
  {
    return XldPackageConstants.SETTINGS_XLDPACKAGE_HTTPS;
  }

  public String getXldPackageDeployables()
  {
    return XldPackageConstants.SETTINGS_XLDPACKAGE_DEPLOYABLES;
  }

}
