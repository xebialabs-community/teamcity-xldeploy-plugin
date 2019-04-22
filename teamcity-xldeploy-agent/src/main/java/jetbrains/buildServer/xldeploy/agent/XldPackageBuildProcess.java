package jetbrains.buildServer.xldeploy.agent;

import com.sun.xml.bind.marshaller.DataWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.AgentRunningBuild;
import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.agent.BuildProcess;
import jetbrains.buildServer.agent.BuildProgressLogger;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.xldeploy.agent.XldDeploymentPackage;
import jetbrains.buildServer.xldeploy.agent.XldCustomCharacterEscapeHandler;
import jetbrains.buildServer.xldeploy.common.XldPackageConstants;

import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.apache.commons.io.FileUtils;

/*
    For now:
    Accept the deployables and templates in XML format.
    Accept the applicationDependencies, boundTemplates, and orchestrators as linebreak-separated lists.
*/

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

        XldDeploymentPackage dp = new XldDeploymentPackage();
        dp.setApplication(runnerParameters.get(XldPackageConstants.SETTINGS_XLDPACKAGE_APPLICATION_NAME));
        dp.setVersion(runnerParameters.get(XldPackageConstants.SETTINGS_XLDPACKAGE_VERSION_NAME));
        dp.setDeployables(runnerParameters.get(XldPackageConstants.SETTINGS_XLDPACKAGE_DEPLOYABLES));
        dp.setArtifactLocations(runnerParameters.get(XldPackageConstants.SETTINGS_XLDPACKAGE_ARTIFACT_LOCATIONS));
        dp.setTemplates(runnerParameters.get(XldPackageConstants.SETTINGS_XLDPACKAGE_TEMPLATES));
        dp.setDependencyResolution(runnerParameters.get(XldPackageConstants.SETTINGS_XLDPACKAGE_DEPENDENCY_RESOLUTION));
        dp.setApplicationDependencies(runnerParameters.get(XldPackageConstants.SETTINGS_XLDPACKAGE_APPLICATION_DEPENDENCIES));
        dp.setBoundTemplates(runnerParameters.get(XldPackageConstants.SETTINGS_XLDPACKAGE_BOUND_TEMPLATES));
        dp.setOrchestrator(runnerParameters.get(XldPackageConstants.SETTINGS_XLDPACKAGE_ORCHESTRATOR));
        dp.setUndeployDependencies(runnerParameters.get(XldPackageConstants.SETTINGS_XLDPACKAGE_UNDEPLOY_DEPENDENCIES));

        packageDar(runningBuild, dp);

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

    private void packageDar(AgentRunningBuild runningBuild, XldDeploymentPackage dp) throws RunBuildException {

        File buildWorkDir = runningBuild.getWorkingDirectory();
        File dpWorkDir = new File(buildWorkDir, String.format("%d/deploymentPackage", runningBuild.getBuildId()));
        dpWorkDir.mkdirs();
        buildDeploymentManifest(dpWorkDir, dp);
        copyArtifactsToDpWorkDir(buildWorkDir, dpWorkDir, dp);
        createDeploymentArchive(dpWorkDir, dp);
    }

    private void buildDeploymentManifest(File dpWorkDir, XldDeploymentPackage dp) throws RunBuildException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(XldDeploymentPackage.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            try {
                PrintWriter printWriter = new PrintWriter(new File(String.format("%s/deployit-manifest.xml", dpWorkDir.getPath())));
                DataWriter dataWriter = new DataWriter(printWriter, "UTF-8", new XldCustomCharacterEscapeHandler());
                jaxbMarshaller.marshal(dp, dataWriter);
                printWriter.close();
            } catch (FileNotFoundException e) {
                logger.message("FileNotFoundException " + e);
                throw new RunBuildException(e);
            }
        } catch (JAXBException e) {
            logger.message("JAXBException " + e);
            throw new RunBuildException(e);
        }
    }

    private void copyArtifactsToDpWorkDir(File buildWorkDir, File dpWorkDir, XldDeploymentPackage dp) throws RunBuildException {
        String artifactLocations = dp.getArtifactLocations();
        if (artifactLocations != null) {
            for (String artifactLocation: artifactLocations.split("\n")) {
                try {
                    File source = new File(buildWorkDir, artifactLocation.split("=")[1]);
                    File target = new File(dpWorkDir, artifactLocation.split("=")[0]);
                    target.mkdir();
                    FileUtils.copyToDirectory(source, target);
                } catch (IOException e) {
                    logger.message("IOException " + e);
                    throw new RunBuildException(e);
                }
            }
        }
    }

    private void createDeploymentArchive(File dpWorkDir, XldDeploymentPackage dp)  throws RunBuildException {
        try {
            File [] children = dpWorkDir.listFiles();
            FileOutputStream fos = new FileOutputStream(String.format("%s/%s-%s.dar", dpWorkDir.getPath(), dp.getApplication(), dp.getVersion()));
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
