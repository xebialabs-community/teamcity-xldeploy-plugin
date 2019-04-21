package jetbrains.buildServer.xldeploy.agent;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="udm.DeploymentPackage")
public class XldDeploymentPackage {

    private String application;
    private String version;
    private String deployables;
    private String templates;
    private String dependencyResolution;
    private String applicationDependencies;
    private String boundTemplates;
    private String orchestrator;
    private String undeployDependencies;

    public String getApplication() {
        return application;
    }

    @XmlAttribute
    public void setApplication(String application) {
        this.application = application;
    }

    public String getVersion() {
        return version;
    }

    @XmlAttribute
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
        this.applicationDependencies = applicationDependencies;
    }

    @XmlElement
    public String getBoundTemplates() {
        return boundTemplates;
    }

    public void setBoundTemplates(String boundTemplates) {
        this.boundTemplates = boundTemplates;
    }

    @XmlElement
    public String getOrchestrator() {
        return orchestrator;
    }

    public void setOrchestrator(String orchestrator) {
        this.orchestrator = orchestrator;
    }

    @XmlElement
    public String getUndeployDependencies() {
        return undeployDependencies;
    }

    public void setUndeployDependencies(String undeployDependencies) {
        this.undeployDependencies = undeployDependencies;
    }

}
