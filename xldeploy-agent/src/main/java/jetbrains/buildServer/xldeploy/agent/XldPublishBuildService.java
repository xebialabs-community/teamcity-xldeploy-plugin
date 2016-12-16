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
import jetbrains.buildServer.xldeploy.agent.XldAgentUtilities;
import jetbrains.buildServer.xldeploy.common.XldPublishConstants;
import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.agent.artifacts.ArtifactsWatcher;
import jetbrains.buildServer.agent.inspections.InspectionReporter;
import jetbrains.buildServer.agent.runner.BuildServiceAdapter;
import jetbrains.buildServer.agent.runner.ProgramCommandLine;
import jetbrains.buildServer.util.FileUtil;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.util.StringUtils;
import org.jetbrains.annotations.NotNull;

public class XldPublishBuildService extends BuildServiceAdapter
{

  private final ArtifactsWatcher mArtifactsWatcher;
  private final InspectionReporter mInspectionReporter;
  private File mOutputDirectory;
  private File mXmlReportFile;
  private File mHtmlReportFile;
  private List<File> mTempFiles = new ArrayList<File>();

  public XldPublishBuildService(final ArtifactsWatcher artifactsWatcher, final InspectionReporter inspectionReporter)
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
    getLogger().progressMessage("Running XebiaLabs XLD Publish");
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
        return getXLDeployComPath();
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
        return getXldPublishArguments();
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
  private String getXLDeployComPath() throws RunBuildException
  {
    return getRunnerParameters().get(XldPublishConstants.SETTINGS_XLDPUBLISH_CLI_PATH);
  }

  @NotNull
  public List<String> getXldPublishArguments() throws RunBuildException
  {
    List<String> arguments = new ArrayList<String>();

    String host;
    String port;
    String username;
    String password;
    String packagePath;

    host = getRunnerParameters().get(XldPublishConstants.SETTINGS_XLDPUBLISH_HOST);
    if (StringUtil.isNotEmpty(host))
    {
        arguments.add("-host");
        arguments.add(host);
    }

    port = getRunnerParameters().get(XldPublishConstants.SETTINGS_XLDPUBLISH_PORT);
    if (StringUtil.isNotEmpty(port))
    {
        arguments.add("-port");
        arguments.add(port);
    }

    username = getRunnerParameters().get(XldPublishConstants.SETTINGS_XLDPUBLISH_USERNAME);
    if (StringUtil.isNotEmpty(username))
    {
        arguments.add("-username");
        arguments.add(username);
    }

    password = getRunnerParameters().get(XldPublishConstants.SETTINGS_XLDPUBLISH_PASSWORD);
    if (StringUtil.isNotEmpty(password))
    {
        arguments.add("-password");
        arguments.add(password);
    }

    arguments.add("-f");
    XldAgentUtilities utils = new XldAgentUtilities();
    arguments.add(utils.getFullScriptPath(this, "scripts/publish-package.py"));

    packagePath = getRunnerParameters().get(XldPublishConstants.SETTINGS_XLDPUBLISH_PACKAGE_PATH);
    if (StringUtil.isNotEmpty(packagePath))
    {
        arguments.add(packagePath);
    }

    return arguments;
  }
}
