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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class XldPublishBuildProcess implements BuildProcess {

    public XldPublishBuildProcess(AgentRunningBuild runningBuild, BuildRunnerContext context) throws RunBuildException {

        final Map<String, String> runnerParameters = context.getRunnerParameters();

        BuildProgressLogger logger = runningBuild.getBuildLogger();
        logger.progressStarted("Progress started for XldPublishBuildProcess");
        logger.message("Host:  " + runnerParameters.get(XldPublishConstants.SETTINGS_XLDPUBLISH_HOST));
        logger.message("Port:  " + runnerParameters.get(XldPublishConstants.SETTINGS_XLDPUBLISH_PORT));
        logger.message("Username:  " + runnerParameters.get(XldPublishConstants.SETTINGS_XLDPUBLISH_USERNAME));
        logger.message("Password:  " + runnerParameters.get(XldPublishConstants.SETTINGS_XLDPUBLISH_PASSWORD));
        logger.message("Package path:  " + runnerParameters.get(XldPublishConstants.SETTINGS_XLDPUBLISH_PACKAGE_PATH));
	
        final OkHttpClient client = new OkHttpClient();
    
        MediaType MULTIPART_FORM_DATA = MediaType.parse("multipart/form-data");   
        
        File file = new File(runnerParameters.get(XldPublishConstants.SETTINGS_XLDPUBLISH_PACKAGE_PATH));
        
        String credential = Credentials.basic(runnerParameters.get(XldPublishConstants.SETTINGS_XLDPUBLISH_USERNAME), 
                runnerParameters.get(XldPublishConstants.SETTINGS_XLDPUBLISH_PASSWORD));
        
        String urlString = String.format("http://%s:%s/deployit/package/upload/dummy.dar", 
                runnerParameters.get(XldPublishConstants.SETTINGS_XLDPUBLISH_HOST),
                runnerParameters.get(XldPublishConstants.SETTINGS_XLDPUBLISH_PORT));

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("fileData", file.getName(),
                        RequestBody.create(MULTIPART_FORM_DATA, file))
                .build();

        Request request = new Request.Builder()
                .url(urlString)
                .header("Authorization", credential)
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
        
            if (response.isSuccessful()) { 
                logger.message(response.body().string());
            }
            else {
                throw new IOException("Unexpected response code " + response);
            }
        } catch (IOException e) {
            logger.message("IOException " + e);
            throw new RunBuildException(e);
        }
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
}