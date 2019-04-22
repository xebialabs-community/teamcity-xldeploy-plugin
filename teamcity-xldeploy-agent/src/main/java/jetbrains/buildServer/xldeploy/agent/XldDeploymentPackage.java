package jetbrains.buildServer.xldeploy.agent;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="udm.DeploymentPackage")
public class XldDeploymentPackage {

    private String application;
    private String version;
    private String deployables;
    private String artifactLocations;
    private String templates;
    private String dependencyResolution;
    private String applicationDependencies;
    private String boundTemplates;
    private String orchestrator;
    private String undeployDependencies;

    @XmlAttribute
    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    @XmlAttribute
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @XmlElement
    public String getDeployables() {
        return deployables;
    }

    public void setDeployables(String deployables) {
        this.deployables = deployables;
    }

    /* Not an XML Element */
    public String getArtifactLocations() {
        return artifactLocations;
    }

    public void setArtifactLocations(String artifactLocations) {
        this.artifactLocations = artifactLocations;
    }

    @XmlElement
    public String getTemplates() {
        return templates;
    }

    public void setTemplates(String templates) {
        this.templates = templates;
    }

    @XmlElement
    public String getDependencyResolution() {
        return dependencyResolution;
    }

    public void setDependencyResolution(String dependencyResolution) {
        this.dependencyResolution = dependencyResolution;
    }

    @XmlElement
    public String getApplicationDependencies() {
        return applicationDependencies;
    }

    public void setApplicationDependencies(String applicationDependencies) {
        if (applicationDependencies != null) {
            StringBuilder xml = new StringBuilder();
            for (String item: applicationDependencies.split("\n")) {
                xml.append(String.format("<entry key=\"%s\">%s</entry>\n", item.split("=")[0], item.split("=")[1]));
            }
            this.applicationDependencies = xml.toString();
        } else {
            this.applicationDependencies = null;
        }
    }

    @XmlElement
    public String getBoundTemplates() {
        return boundTemplates;
    }

    public void setBoundTemplates(String boundTemplates) {
        if (boundTemplates != null) {
            StringBuilder xml = new StringBuilder();
            for (String item: boundTemplates.split("\n")) {
                xml.append(String.format("<ci ref=\"%s\" />\n", item));
            }
            this.boundTemplates = xml.toString();
        } else {
            this.boundTemplates = null;
        }
    }

    @XmlElement
    public String getOrchestrator() {
        return orchestrator;
    }

    public void setOrchestrator(String orchestrator) {
        if (orchestrator != null) {
            StringBuilder xml = new StringBuilder();
            for (String item: orchestrator.split("\n")) {
                xml.append(String.format("<value>%s</value>\n", item));
            }
            this.orchestrator = xml.toString();
        } else {
            this.orchestrator = null;
        }
    }

    @XmlElement
    public String getUndeployDependencies() {
        return undeployDependencies;
    }

    public void setUndeployDependencies(String undeployDependencies) {
        this.undeployDependencies = undeployDependencies;
    }

}
