package jetbrains.buildServer.xldeploy.agent;

import java.io.File;
import jetbrains.buildServer.agent.runner.BuildServiceAdapter;
import jetbrains.buildServer.RunBuildException;

public class XldAgentUtilities {

    public String getFullScriptPath(BuildServiceAdapter bsa, String scriptPath) throws RunBuildException {
        File fullScriptPath = new File(bsa.getAgentConfiguration().getAgentPluginsDirectory(), "xldeploy-agent/" + scriptPath);
        return fullScriptPath.toString();
  }
}
