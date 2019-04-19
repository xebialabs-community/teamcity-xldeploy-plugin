package jetbrains.buildServer.xldeploy.agent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
    String deployables;

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
        deployables = runnerParameters.get(XldPackageConstants.SETTINGS_XLDPACKAGE_DEPLOYABLES);

        packageDar(runningBuild, applicationName, versionName, deployables);

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

    private void packageDar(AgentRunningBuild runningBuild, String applicationName, String versionName, String deployables) throws RunBuildException {

/*
    For now, accept the deployables in XML format.
    TO-DO:  Enlarge the deployables input box on the view and edit JSPs.
    TO-DO:  Add the package level properties as variables on the view and edit JSPs and in the code below.
    TO-DO:  Dynamically modify the view and edit JSPs to present types and properties as the user builds the package
*/

        logger.message(String.format("Deployables: %s", deployables));

        File agentWorkDir = runningBuild.getWorkingDirectory();
        String agentWorkDirPath = agentWorkDir.getPath();
        long buildId = runningBuild.getBuildId();
        logger.message(agentWorkDirPath);
        File dpWorkDir = new File(String.format("%s/%d/deploymentPackage", agentWorkDirPath, buildId));
        dpWorkDir.mkdirs();
        String dpWorkDirPath = dpWorkDir.getPath();
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(String.format("%s/deployit-manifest.xml", dpWorkDirPath, buildId)));
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write(String.format("<udm.DeploymentPackage application=\"%s\" version=\"%s\">\n", applicationName, versionName));
            writer.write("<application />\n");
            writer.write("<orchestrator />\n");
            writer.write("<deployables>\n");
            writer.write(String.format("%s\n", deployables));
            writer.write("</deployables>\n");
            writer.write("<applicationDependencies />\n");
            writer.write("<dependencyResolution>LATEST</dependencyResolution>\n");
            writer.write("<undeployDependencies>false</undeployDependencies>\n");
            writer.write("<templates />\n");
            writer.write("<boundTemplates />\n");
            writer.write("</udm.DeploymentPackage>\n");
            writer.close();
        } catch (IOException e) {
            logger.message("IOException " + e);
            throw new RunBuildException(e);
        }

        try {
            File fileToZip = new File(dpWorkDirPath);
            File[] children = fileToZip.listFiles();
            FileOutputStream fos = new FileOutputStream(String.format("%s/%s-%s.dar", dpWorkDirPath, applicationName, versionName));
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            for (File childFile : children) {
                zipFile(childFile, childFile.getName(), zipOut);
            }
            zipOut.close();
            fos.close();
        } catch (IOException e) {
            logger.message("IOException " + e);
            throw new RunBuildException(e);
        }

    }

    private void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }

}
