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
import jetbrains.buildServer.xldeploy.common.XldPublishConstants;
import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.HttpUrl.Builder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class XldPublishBuildProcess implements BuildProcess {

    BuildProgressLogger logger;
    OkHttpClient client;
    String host;
    int port;
    String contextRoot;
    String credential;
    String scheme;

    public XldPublishBuildProcess(AgentRunningBuild runningBuild, BuildRunnerContext context) throws RunBuildException {

        final Map<String, String> runnerParameters = context.getRunnerParameters();

        logger = runningBuild.getBuildLogger();
        logger.progressStarted("Progress started for XldPublishBuildProcess (300s timeout)");

        client = new OkHttpClient.Builder()
          .readTimeout(300, java.util.concurrent.TimeUnit.SECONDS)
          .writeTimeout(300, java.util.concurrent.TimeUnit.SECONDS)
          .build();

        host = runnerParameters.get(XldPublishConstants.SETTINGS_XLDPUBLISH_HOST);
        port = Integer.parseInt(runnerParameters.get(XldPublishConstants.SETTINGS_XLDPUBLISH_PORT));
        contextRoot = runnerParameters.get(XldPublishConstants.SETTINGS_XLDPUBLISH_CONTEXT_ROOT);

        credential = Credentials.basic(runnerParameters.get(XldPublishConstants.SETTINGS_XLDPUBLISH_USERNAME),
                runnerParameters.get(XldPublishConstants.SETTINGS_XLDPUBLISH_PASSWORD));

        scheme = runnerParameters.get(XldPublishConstants.SETTINGS_XLDPUBLISH_HTTPS) == null?"http":"https";

        File file = new File(runnerParameters.get(XldPublishConstants.SETTINGS_XLDPUBLISH_PACKAGE_PATH));

        publishPackage(file);

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

    private HttpUrl.Builder getXldBaseUrlBuilder() {
        HttpUrl.Builder httpUrlBuilder = new HttpUrl.Builder()
            .scheme(scheme)
            .host(host)
            .port(port);
        if (contextRoot == null) {
            return httpUrlBuilder
                .addPathSegment("deployit");
        } else {
            return httpUrlBuilder
                .addPathSegment(contextRoot)
                .addPathSegment("deployit");
        }
    }

    private void publishPackage (File file) throws RunBuildException {

        HttpUrl httpUrl = getXldBaseUrlBuilder()
                .addPathSegment("package")
                .addPathSegment("upload")
                .addPathSegment("dummy.dar")
                .build();

        MediaType MULTIPART_FORM_DATA = MediaType.parse("multipart/form-data");

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("fileData", file.getName(),
                        RequestBody.create(MULTIPART_FORM_DATA, file))
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("Authorization", credential)
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                logger.message(String.format("Package published successfully %s", file.getName()));
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
