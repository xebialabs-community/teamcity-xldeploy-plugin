package jetbrains.buildServer.xldeploy.agent;

import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.AgentBuildRunner;
import jetbrains.buildServer.agent.AgentBuildRunnerInfo;
import jetbrains.buildServer.agent.AgentRunningBuild;
import jetbrains.buildServer.agent.BuildAgentConfiguration;
import jetbrains.buildServer.agent.BuildProcess;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.xldeploy.agent.XldDeployBuildProcess;

import org.jetbrains.annotations.NotNull;

public class XldDeployAgentBuildRunner implements AgentBuildRunner, AgentBuildRunnerInfo {

  /* Required by AgentBuildRunner interface */

  public BuildProcess createBuildProcess (@NotNull AgentRunningBuild runningBuild, @NotNull BuildRunnerContext context) throws RunBuildException {
    XldDeployBuildProcess bp = new XldDeployBuildProcess(runningBuild, context);
    return bp;
  }
	
  /* Required by AgentBuildRunner interface */
  @NotNull
  public AgentBuildRunnerInfo getRunnerInfo() {
    return this;    
  }

  /* Required by AgentBuildRunnerInfo interface */
  public boolean canRun(BuildAgentConfiguration agentConfiguration) {
    return true;
  }

  /* Required by AgentBuildRunnerInfo interface */
  public String getType() {
  	return "XldDeploy";
  } 

}
