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

  public String getXldPackageContextRoot()
  {
    return XldPackageConstants.SETTINGS_XLDPACKAGE_CONTEXT_ROOT;
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

  public String getXldPackageArtifactLocations()
  {
    return XldPackageConstants.SETTINGS_XLDPACKAGE_ARTIFACT_LOCATIONS;
  }

  public String getXldPackageTemplates()
  {
    return XldPackageConstants.SETTINGS_XLDPACKAGE_TEMPLATES;
  }

  public String getXldPackageDependencyResolution()
  {
    return XldPackageConstants.SETTINGS_XLDPACKAGE_DEPENDENCY_RESOLUTION;
  }

  public String getXldPackageApplicationDependencies()
  {
    return XldPackageConstants.SETTINGS_XLDPACKAGE_APPLICATION_DEPENDENCIES;
  }

  public String getXldPackageBoundTemplates()
  {
    return XldPackageConstants.SETTINGS_XLDPACKAGE_BOUND_TEMPLATES;
  }

  public String getXldPackageOrchestrator()
  {
    return XldPackageConstants.SETTINGS_XLDPACKAGE_ORCHESTRATOR;
  }

  public String getXldPackageUndeployDependencies()
  {
    return XldPackageConstants.SETTINGS_XLDPACKAGE_UNDEPLOY_DEPENDENCIES;
  }

}
