package jetbrains.buildServer.xldeploy.agent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.intellij.util.xmlb.annotations.Collection;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.xldeploy.common.XldDeployConstants;
import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.agent.artifacts.ArtifactsWatcher;
import jetbrains.buildServer.agent.inspections.InspectionReporter;
import jetbrains.buildServer.agent.runner.BuildServiceAdapter;
import jetbrains.buildServer.agent.runner.ProgramCommandLine;
import jetbrains.buildServer.util.FileUtil;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.util.StringUtils;
import org.jetbrains.annotations.NotNull;

public class XldDeployBuildService extends BuildServiceAdapter
{

  private final ArtifactsWatcher mArtifactsWatcher;
  private final InspectionReporter mInspectionReporter;
  private File mOutputDirectory;
  private File mXmlReportFile;
  private File mHtmlReportFile;
  private List<File> mTempFiles = new ArrayList<File>();

  public XldDeployBuildService(final ArtifactsWatcher artifactsWatcher, final InspectionReporter inspectionReporter)
  {
    mArtifactsWatcher = artifactsWatcher;
    mInspectionReporter = inspectionReporter;
  }

  @Override
  public void afterInitialized() throws RunBuildException
  {
    super.afterInitialized();
  }

  @Override
  public void beforeProcessStarted() throws RunBuildException
  {
    getLogger().progressMessage("Running XLD Deploy");
  }

  @Override
  public void afterProcessFinished() throws RunBuildException
  {
    super.afterProcessFinished();
    for (File file : mTempFiles)
    {
      FileUtil.delete(file);
    }
    mTempFiles.clear();
  }

  @NotNull
  @Override
  public BuildFinishedStatus getRunResult(final int exitCode)
  {
    if (exitCode != 0)
    {
      return BuildFinishedStatus.FINISHED_FAILED;
    }

    return BuildFinishedStatus.FINISHED_SUCCESS;
  }

  @NotNull
  @Override
  public ProgramCommandLine makeProgramCommandLine() throws RunBuildException
  {
    return new ProgramCommandLine()
    {
      @NotNull
      @Override
      public String getExecutablePath() throws RunBuildException
      {
        return getXldDeployComPath();
      }

      @NotNull
      @Override
      public String getWorkingDirectory() throws RunBuildException
      {
        return getCheckoutDirectory().getPath();
      }

      @NotNull
      @Override
      public List<String> getArguments() throws RunBuildException
      {
        return getXldDeployArguments();
      }

      @NotNull
      @Override
      public Map<String, String> getEnvironment() throws RunBuildException
      {
        return getBuildParameters().getEnvironmentVariables();
      }
    };
  }

  @NotNull
  private String getXldDeployComPath() throws RunBuildException
  {
    return getRunnerParameters().get(XldDeployConstants.SETTINGS_XLDDEPLOY_CLI_PATH);
  }

  @NotNull
  public List<String> getXldDeployArguments() throws RunBuildException
  {
    List<String> arguments = new ArrayList<String>();

    String host;
    String port;
    String username;
    String password;
    String applicationName;
    String versionName;
    String environmentId;

    host = getRunnerParameters().get(XldDeployConstants.SETTINGS_XLDDEPLOY_HOST);
    port = getRunnerParameters().get(XldDeployConstants.SETTINGS_XLDDEPLOY_PORT);
    username = getRunnerParameters().get(XldDeployConstants.SETTINGS_XLDDEPLOY_USERNAME);
    password = getRunnerParameters().get(XldDeployConstants.SETTINGS_XLDDEPLOY_PASSWORD);
    applicationName = getRunnerParameters().get(XldDeployConstants.SETTINGS_XLDDEPLOY_APPLICATION_NAME);
    versionName = getRunnerParameters().get(XldDeployConstants.SETTINGS_XLDDEPLOY_VERSION_NAME);
    environmentId = getRunnerParameters().get(XldDeployConstants.SETTINGS_XLDDEPLOY_ENVIRONMENT_ID);

    if (StringUtil.isNotEmpty(host))
    {
        arguments.add("-host");
        arguments.add(host);
    }

    if (StringUtil.isNotEmpty(port))
    {
        arguments.add("-port");
        arguments.add(port);
    }

    if (StringUtil.isNotEmpty(username))
    {
        arguments.add("-username");
        arguments.add(username);
    }

    if (StringUtil.isNotEmpty(password))
    {
        arguments.add("-password");
        arguments.add(password);
    }

    arguments.add("-f");
    arguments.add("deploy-package.py");

    if (StringUtil.isNotEmpty(applicationName))
    {
        arguments.add(applicationName);
    }

    if (StringUtil.isNotEmpty(versionName))
    {
        arguments.add(versionName);
    }

    if (StringUtil.isNotEmpty(environmentId))
    {
        arguments.add(environmentId);
    }

    return arguments;
  }
}
