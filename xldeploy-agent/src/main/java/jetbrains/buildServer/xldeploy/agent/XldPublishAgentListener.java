package jetbrains.buildServer.xldeploy.agent;

import jetbrains.buildServer.xldeploy.common.XldPublishConstants;
import jetbrains.buildServer.agent.AgentLifeCycleAdapter;
import jetbrains.buildServer.agent.AgentLifeCycleListener;
import jetbrains.buildServer.agent.BuildAgent;
import jetbrains.buildServer.agent.BuildAgentConfiguration;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.util.positioning.PositionAware;
import jetbrains.buildServer.util.positioning.PositionConstraint;
import org.jetbrains.annotations.NotNull;


public class XldPublishAgentListener extends AgentLifeCycleAdapter implements PositionAware
{

  BuildAgentConfiguration mAgentConfiguration;

  public XldPublishAgentListener(@NotNull final BuildAgentConfiguration agentConfiguration, @NotNull final EventDispatcher<AgentLifeCycleListener> events)
  {
    this.mAgentConfiguration = agentConfiguration;
    events.addListener(this);
  }

  @NotNull
  public String getOrderId()
  {
    return XldPublishConstants.RUNNER_TYPE;
  }

  @NotNull
  public PositionConstraint getConstraint()
  {
    return PositionConstraint.last();
  }

  @Override
  public void agentInitialized(@NotNull final BuildAgent agent)
  {
  }
}
