package jetbrains.buildServer.xldeploy.common;

public interface XldDeployConstants
{

  String RUNNER_TYPE = "XldDeploy";
  String RUNNER_DISPLAY_NAME = "XebiaLabs XLD Deploy";
  String SETTINGS_XLDDEPLOY_HOST = "xlddeploy.host";
  String SETTINGS_XLDDEPLOY_PORT = "xlddeploy.port";
  String SETTINGS_XLDDEPLOY_CONTEXT_ROOT = "xlddeploy.context.root";
  String SETTINGS_XLDDEPLOY_USERNAME = "xlddeploy.username";
  String SETTINGS_XLDDEPLOY_PASSWORD = "xlddeploy.password";
  String SETTINGS_XLDDEPLOY_APPLICATION_NAME = "xlddeploy.application.name";
  String SETTINGS_XLDDEPLOY_VERSION_NAME = "xlddeploy.version.name";
  String SETTINGS_XLDDEPLOY_ENVIRONMENT_ID = "xlddeploy.environment.id";
  String SETTINGS_XLDDEPLOY_HTTPS = "xlddeploy.https";
  String SETTINGS_XLDDEPLOY_WAIT = "xlddeploy.wait";
  String SETTINGS_XLDDEPLOY_TASK_STATE_ABORTED = "ABORTED";
  String SETTINGS_XLDDEPLOY_TASK_STATE_EXECUTED = "EXECUTED";
  String SETTINGS_XLDDEPLOY_TASK_STATE_FAILED = "FAILED";
  String SETTINGS_XLDDEPLOY_TASK_STATE_STOPPED = "STOPPED";
  String RUNNER_DESCRIPTION = "Deploy package with XL Deploy.";

}
