package jetbrains.buildServer.xldeploy.agent;

import jetbrains.buildServer.xldeploy.common.XldPublishConstants;
import jetbrains.buildServer.agent.AgentBuildRunnerInfo;
import jetbrains.buildServer.agent.BuildAgentConfiguration;
import jetbrains.buildServer.agent.artifacts.ArtifactsWatcher;
import jetbrains.buildServer.agent.inspections.InspectionReporter;
import jetbrains.buildServer.agent.runner.CommandLineBuildService;
import jetbrains.buildServer.agent.runner.CommandLineBuildServiceFactory;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class XldPublishBuildServiceFactory implements CommandLineBuildServiceFactory, AgentBuildRunnerInfo
{

  private static final Logger LOG = Logger.getLogger(XldPublishBuildServiceFactory.class);

  private final ArtifactsWatcher mArtifactsWatcher;
  private final InspectionReporter mInspectionsReporter;

  public XldPublishBuildServiceFactory(@NotNull final ArtifactsWatcher artifactsWatcher,
          @NotNull final InspectionReporter inspectionsReporter)
  {
    mArtifactsWatcher = artifactsWatcher;
    mInspectionsReporter = inspectionsReporter;
  }

  @NotNull
  public String getType()
  {
    return XldPublishConstants.RUNNER_TYPE;
  }

  public boolean canRun(@NotNull final BuildAgentConfiguration agentConfiguration)
  {
    return true;
  }

  @NotNull
  public CommandLineBuildService createService()
  {
    return new XldPublishBuildService(mArtifactsWatcher, mInspectionsReporter);
  }

  @NotNull
  public AgentBuildRunnerInfo getBuildRunnerInfo()
  {
    return this;
  }
}
