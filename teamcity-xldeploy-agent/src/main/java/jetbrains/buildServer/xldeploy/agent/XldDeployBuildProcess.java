package jetbrains.buildServer.xldeploy.agent;

import java.io.IOException;
import java.util.Map;
import org.json.JSONObject;

import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.AgentRunningBuild;
import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.agent.BuildProcess;
import jetbrains.buildServer.agent.BuildProgressLogger;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.xldeploy.common.XldDeployConstants;
import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class XldDeployBuildProcess implements BuildProcess {

    BuildProgressLogger logger;
    OkHttpClient client;
    String host;
    int port;
    String credential;

    public XldDeployBuildProcess(AgentRunningBuild runningBuild, BuildRunnerContext context) throws RunBuildException {

        JSONObject deploymentSpec = null;
        String taskId = null;

        final Map<String, String> runnerParameters = context.getRunnerParameters();

        logger = runningBuild.getBuildLogger();
        logger.progressStarted("Progress started for XldDeployBuildProcess");

        client = new OkHttpClient();

        host = runnerParameters.get(XldDeployConstants.SETTINGS_XLDDEPLOY_HOST);
        port = Integer.parseInt(runnerParameters.get(XldDeployConstants.SETTINGS_XLDDEPLOY_PORT));

        credential = Credentials.basic(runnerParameters.get(XldDeployConstants.SETTINGS_XLDDEPLOY_USERNAME), 
                runnerParameters.get(XldDeployConstants.SETTINGS_XLDDEPLOY_PASSWORD));

        String applicationName = runnerParameters.get(XldDeployConstants.SETTINGS_XLDDEPLOY_APPLICATION_NAME);
        String versionName = runnerParameters.get(XldDeployConstants.SETTINGS_XLDDEPLOY_VERSION_NAME);
        String environmentId = runnerParameters.get(XldDeployConstants.SETTINGS_XLDDEPLOY_ENVIRONMENT_ID);

        logger.message(String.format("Host:  %s", host));
        logger.message(String.format("Port:  %d", port));
        logger.message(String.format("Application name:  %s", applicationName));
        logger.message(String.format("Version name:  %s", versionName));
        logger.message(String.format("Environment id:  %s", environmentId));
        
        String applicationId = determineApplicationId(applicationName);
        String versionId = String.format("%s/%s", applicationId, versionName);
        String deploymentType = checkDeploymentExists(applicationId, environmentId);

        if (deploymentType.equals("update")) {
            logger.message("Update deployment");
            deploymentSpec = prepareUpdate(versionId, String.format("%s/%s", environmentId, applicationName));
        } else if (deploymentType.equals("initial")) {
            logger.message("Initial deployment");
            deploymentSpec = prepareInitial(versionId, environmentId);
        }
        logger.message(deploymentSpec.toString());

        deploymentSpec = prepareAutoDeployeds(deploymentSpec);
        logger.message(deploymentSpec.toString());

        deploymentSpec = validate(deploymentSpec);
        logger.message(deploymentSpec.toString());

        taskId = createDeployTask(deploymentSpec);
        logger.message(taskId);
        startTask(taskId);

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

    private String determineApplicationId(String applicationName) throws RunBuildException {

       HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(host)
                .port(port)
                .addPathSegment("deployit")
                .addPathSegment("repository")
                .addPathSegment("query")
                .addQueryParameter("type", "udm.Application")
                .addQueryParameter("namePattern", applicationName)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("Authorization", credential)
                .header("Accept", "application/json")
                .get()
                .build();

        JSONObject jsonObject = null;
        Response response = null;

        try {
            response = client.newCall(request).execute();

            if (response.isSuccessful()) { 
                jsonObject = new JSONObject(String.format("{searchResults:%s}", response.body().string()));
            } else {
                throw new IOException("Unexpected response code " + response);
            }

        } catch (IOException e) {
            logger.message("IOException " + e);
            throw new RunBuildException(e);
        } finally {
            response.close();
        }
        
        JSONObject jsonItem = (JSONObject) jsonObject.getJSONArray("searchResults").get(0);

        return jsonItem.getString("ref");
    }

    private String checkDeploymentExists(String applicationId, String environmentId) throws RunBuildException {
  
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(host)
                .port(port)
                .addPathSegment("deployit")
                .addPathSegment("deployment")
                .addPathSegment("exists")
                .addQueryParameter("application", applicationId)
                .addQueryParameter("environment", environmentId)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("Authorization", credential)
                .header("Accept", "application/json")
                .get()
                .build();

        JSONObject jsonObject = null;
        Response response = null;
        
        try {
            response = client.newCall(request).execute();

            if (response.isSuccessful()) { 
                jsonObject = new JSONObject(response.body().string());
            } else {
                throw new IOException("Unexpected response code " + response);
            }
        } catch (IOException e) {
            logger.message("IOException " + e);
            throw new RunBuildException(e);
        } finally {
            response.close();
        }

        if (jsonObject.getBoolean("boolean")) {
            return "update";
        }
        else {
            return "initial";
        }
    }

    private JSONObject prepareInitial (String versionId, String environmentId) throws RunBuildException {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(host)
                .port(port)
                .addPathSegment("deployit")
                .addPathSegment("deployment")
                .addPathSegment("prepare")
                .addPathSegment("initial")
                .addQueryParameter("version", versionId)
                .addQueryParameter("environment", environmentId)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("Authorization", credential)
                .header("Accept", "application/json")
                .get()
                .build();

        JSONObject jsonObject = null;
        Response response = null;
        
        try {
            response = client.newCall(request).execute();

            if (response.isSuccessful()) { 
                jsonObject = new JSONObject(response.body().string());
            } else {
                throw new IOException("Unexpected response code " + response);
            }
        } catch (IOException e) {
            logger.message("IOException " + e);
            throw new RunBuildException(e);
        } finally {
            response.close();
        }

        return jsonObject;
    }

    private JSONObject prepareUpdate (String versionId, String deployedApplicationId) throws RunBuildException {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(host)
                .port(port)
                .addPathSegment("deployit")
                .addPathSegment("deployment")
                .addPathSegment("prepare")
                .addPathSegment("update")
                .addQueryParameter("version", versionId)
                .addQueryParameter("deployedApplication", deployedApplicationId)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("Authorization", credential)
                .header("Accept", "application/json")
                .get()
                .build();

        JSONObject jsonObject = null;
        Response response = null;
        
        try {
            response = client.newCall(request).execute();

            if (response.isSuccessful()) { 
                jsonObject = new JSONObject(response.body().string());
            } else {
                throw new IOException("Unexpected response code " + response);
            }
        } catch (IOException e) {
            logger.message("IOException " + e);
            throw new RunBuildException(e);
        } finally {
            response.close();
        }

        return jsonObject;
    }

    private JSONObject prepareAutoDeployeds (JSONObject deploymentSpec) throws RunBuildException {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(host)
                .port(port)
                .addPathSegment("deployit")
                .addPathSegment("deployment")
                .addPathSegment("prepare")
                .addPathSegment("deployeds")
                .build();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, deploymentSpec.toString());

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("Authorization", credential)
                .header("Accept", "application/json")
                .post(requestBody)
                .build();

        JSONObject jsonObject = null;
        Response response = null;
        
        try {
            response = client.newCall(request).execute();

            if (response.isSuccessful()) { 
                jsonObject = new JSONObject(response.body().string());
            } else {
                throw new IOException("Unexpected response code " + response);
            }
        } catch (IOException e) {
            logger.message("IOException " + e);
            throw new RunBuildException(e);
        } finally {
            response.close();
        }

        return jsonObject;
    }

    private JSONObject validate (JSONObject deploymentSpec) throws RunBuildException {

        logger.message("Validate step omitted pending resolution of Zendesk 7587, JIRA DEPL-10909");
        return deploymentSpec;

/* Uncomment when Zendesk 7587, JIRA DEPL-10909 resolved

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(host)
                .port(port)
                .addPathSegment("deployit")
                .addPathSegment("deployment")
                .addPathSegment("validate")
                .build();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, deploymentSpec.toString());

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("Authorization", credential)
                .header("Accept", "application/json")
                .post(requestBody)
                .build();

        JSONObject jsonObject = null;
        Response response = null;
        
        try {
            response = client.newCall(request).execute();

            if (response.isSuccessful()) { 
                jsonObject = new JSONObject(response.body().string());
            } else {
                throw new IOException("Unexpected response code " + response);
            }
        } catch (IOException e) {
            logger.message("IOException " + e);
            throw new RunBuildException(e);
        } finally {
            response.close();
        }

        // loop structure

        boolean error = false;

        for (Object deployed : jsonObject.getJSONArray("deployeds")) {
            for (Object validationMessage : ((JSONObject)deployed).getJSONArray("validation-messages")) {
                error = true;
                logger.message(((JSONObject)validationMessage).toString();     
            }
        }

        if (error){
            throw new RunBuildException("Validation warnings/errors found");
        }

        return jsonObject;
*/
    }

    private String createDeployTask (JSONObject deploymentSpec) throws RunBuildException {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(host)
                .port(port)
                .addPathSegment("deployit")
                .addPathSegment("deployment")
                .build();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, deploymentSpec.toString());

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("Authorization", credential)
                .header("Accept", "application/json")
                .post(requestBody)
                .build();

        JSONObject jsonObject = null;
        Response response = null;
        
        try {
            response = client.newCall(request).execute();

            if (response.isSuccessful()) { 
                jsonObject = new JSONObject(response.body().string());
            } else {
                throw new IOException("Unexpected response code " + response);
            }
        } catch (IOException e) {
            logger.message("IOException " + e);
            throw new RunBuildException(e);
        } finally {
            response.close();
        }

        return jsonObject.getString("string");
    }

    private void startTask (String taskId) throws RunBuildException {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(host)
                .port(port)
                .addPathSegment("deployit")
                .addPathSegment("tasks")
                .addPathSegment("v2")
                .addPathSegment(taskId)
                .addPathSegment("start")
                .build();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, "{}");

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("Authorization", credential)
                .header("Accept", "application/json")
                .post(requestBody)
                .build();

        Response response = null;

        try {
            response = client.newCall(request).execute();

            if (response.isSuccessful()) { 
                /* noop */
            } else {
                throw new IOException("Unexpected response code " + response);
            }
        } catch (IOException e) {
            logger.message("IOException " + e);
            throw new RunBuildException(e);
        } finally {
            response.close();
        }

        return;
    }

    private String getTaskState (String taskId) throws RunBuildException {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(host)
                .port(port)
                .addPathSegment("deployit")
                .addPathSegment("tasks")
                .addPathSegment("v2")
                .addPathSegment(taskId)
                .addPathSegment("start")
                .build();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        JSONObject jsonObject = null;
        RequestBody requestBody = RequestBody.create(JSON, "{}");

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("Authorization", credential)
                .header("Accept", "application/json")
                .post(requestBody)
                .build();

        Response response = null;

        try {
            response = client.newCall(request).execute();

            if (response.isSuccessful()) { 
                jsonObject = new JSONObject(response.body().string());
            } else {
                throw new IOException("Unexpected response code " + response);
            }
        } catch (IOException e) {
            logger.message("IOException " + e);
            throw new RunBuildException(e);
        } finally {
            response.close();
        }

        return jsonObject.getString("state");
    }
}
