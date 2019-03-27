package jetbrains.buildServer.xldeploy.agent;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.AgentRunningBuild;
import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.agent.BuildProcess;
import jetbrains.buildServer.agent.BuildProgressLogger;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.xldeploy.common.XldPackageConstants;
import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class XldPackageBuildProcess implements BuildProcess {

    BuildProgressLogger logger;
    OkHttpClient client;
    String host;
    int port;
    String credential;
    String scheme;
    String applicationName;
    String versionName;

    public XldPackageBuildProcess(AgentRunningBuild runningBuild, BuildRunnerContext context) throws RunBuildException {

        final Map<String, String> runnerParameters = context.getRunnerParameters();

        logger = runningBuild.getBuildLogger();
        logger.progressStarted("Progress started for XldPackageBuildProcess");

        client = new OkHttpClient();

        host = runnerParameters.get(XldPackageConstants.SETTINGS_XLDPACKAGE_HOST);
        port = Integer.parseInt(runnerParameters.get(XldPackageConstants.SETTINGS_XLDPACKAGE_PORT));

        credential = Credentials.basic(runnerParameters.get(XldPackageConstants.SETTINGS_XLDPACKAGE_USERNAME),
                runnerParameters.get(XldPackageConstants.SETTINGS_XLDPACKAGE_PASSWORD));

        scheme = runnerParameters.get(XldPackageConstants.SETTINGS_XLDPACKAGE_HTTPS) == null?"http":"https";

        applicationName = runnerParameters.get(XldPackageConstants.SETTINGS_XLDPACKAGE_APPLICATION_NAME);
        versionName = runnerParameters.get(XldPackageConstants.SETTINGS_XLDPACKAGE_VERSION_NAME);

        packageDar();

        logger.progressFinished();

	}

    @Override
    public void interrupt() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isInterrupted() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void start() throws RunBuildException {
        // TODO Auto-generated method stub

    }

    @Override
    public BuildFinishedStatus waitFor() throws RunBuildException {
        // TODO Auto-generated method stub
        return null;
    }

    private void packageDar() throws RunBuildException {

/*
   For now, gather all the properties available for a deployment package from the XL Deploy instance specified
   TO-DO:  Dynamically modify the view and edit JSPs to present types and properties as the user builds the package
*/

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(scheme)
                .host(host)
                .port(port)
                .addPathSegment("deployit")
                .addPathSegment("metadata")
                .addPathSegment("type")
                .addPathSegment("udm.DeploymentPackage")
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("Authorization", credential)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                logger.message(response.toString());
            }
            else {
                throw new IOException("Unexpected response code " + response);
            }
        } catch (IOException e) {
            logger.message("IOException " + e);
            throw new RunBuildException(e);
        }

    }
}
