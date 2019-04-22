<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags/admin" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>
<jsp:useBean id="constants" class="jetbrains.buildServer.xldeploy.server.XldPackageConstantsBean"/>

<%-- TO-DO:  Dynamically modify the view and edit JSPs to present types and properties as the user builds the package.  --%>

<l:settingsGroup title="XL Deploy:Package">

  <tr id="xldpackagehost">
    <th><label for="${constants.xldPackageHost}">XL Deploy host: <l:star/></label></th>
    <td><props:textProperty name="${constants.xldPackageHost}" className="longField"/>
        <span class="error" id="error_${constants.xldPackageHost}"></span>
        <span class="smallNote">XL Deploy host</span>
    </td>
  </tr>

  <tr id="xldpackageport">
    <th><label for="${constants.xldPackagePort}">XL Deploy port: <l:star/></label></th>
    <td><props:textProperty name="${constants.xldPackagePort}" className="longField"/>
        <span class="error" id="error_${constants.xldPackagePort}"></span>
        <span class="smallNote">XL Deploy port</span>
    </td>
  </tr>

  <tr id="xldpackageusername">
    <th><label for="${constants.xldPackageUsername}">XL Deploy username: <l:star/></label></th>
    <td><props:textProperty name="${constants.xldPackageUsername}" className="longField"/>
        <span class="error" id="error_${constants.xldPackageUsername}"></span>
        <span class="smallNote">XL Deploy username</span>
    </td>
  </tr>

  <tr id="xldpackagepassword">
    <th><label for="${constants.xldPackagePassword}">XL Deploy password: <l:star/></label></th>
    <td><props:textProperty name="${constants.xldPackagePassword}" className="longField"/>
        <span class="error" id="error_${constants.xldPackagePassword}"></span>
        <span class="smallNote">XL Deploy password</span>
    </td>
  </tr>

  <tr id="xldpackageapplname">
    <th><label for="${constants.xldPackageApplicationName}">Application name: <l:star/></label></th>
    <td><props:textProperty name="${constants.xldPackageApplicationName}" className="longField"/>
        <span class="error" id="error_${constants.xldPackageApplicationName}"></span>
        <span class="smallNote">Application name</span>
    </td>
  </tr>

  <tr id="xldpackagevername">
    <th><label for="${constants.xldPackageVersionName}">Version name: <l:star/></label></th>
    <td><props:textProperty name="${constants.xldPackageVersionName}" className="longField"/>
        <span class="error" id="error_${constants.xldPackageVersionName}"></span>
        <span class="smallNote">Version name</span>
    </td>
  </tr>

  <tr id="xldpackagehttps">
    <th><label for="${constants.xldPackageHttps}">Use HTTPS</label></th>
    <td><props:checkboxProperty name="${constants.xldPackageHttps}"/>
        <span class="error" id="error_${constants.xldPackageHttps}"></span>
        <span class="smallNote">Use HTTPS in REST API calls to XL Deploy</span>
    </td>
  </tr>

  <tr id="xldpackagedeployables">
    <th><label for="${constants.xldPackageDeployables}">Deployables: <l:star/></label></th>
    <td><props:textProperty name="${constants.xldPackageDeployables}" className="longField" expandable="true" />
        <span class="error" id="error_${constants.xldPackageDeployables}"></span>
        <span class="smallNote">Deployables as XML</span>
    </td>
  </tr>

  <tr id="xldpackageartifactlocations">
    <th><label for="${constants.xldPackageArtifactLocations}">Artifact locations: </label></th>
    <td><props:textProperty name="${constants.xldPackageArtifactLocations}" className="longField" expandable="true" />
      <span class="error" id="error_${constants.xldPackageArtifactLocations}"></span>
      <span class="smallNote">Artifacts to be included in DAR package</span>
    </td>
  </tr>

  <tr id="xldpackagetemplates">
    <th><label for="${constants.xldPackageTemplates}">Templates: </label></th>
    <td><props:textProperty name="${constants.xldPackageTemplates}" className="longField" expandable="true" />
        <span class="error" id="error_${constants.xldPackageTemplates}"></span>
        <span class="smallNote">Templates as XML</span>
    </td>
  </tr>

  <tr id="xldpackagedependencyresolution">
    <th><label for="${constants.xldPackageDependencyResolution}">Dependency resolution: <l:star/></label></th>
    <td><props:textProperty name="${constants.xldPackageDependencyResolution}" className="longField" />
        <span class="error" id="error_${constants.xldPackageDependencyResolution}"></span>
        <span class="smallNote">Dependency resolution, LATEST or EXISTING</span>
    </td>
  </tr>

  <tr id="xldpackageapplicationdependencies">
    <th><label for="${constants.xldPackageApplicationDependencies}">Application dependencies: </label></th>
    <td><props:textProperty name="${constants.xldPackageApplicationDependencies}" className="longField" expandable="true" />
        <span class="error" id="error_${constants.xldPackageApplicationDependencies}"></span>
        <span class="smallNote">Application dependencies, one per line</span>
    </td>
  </tr>

  <tr id="xldpackageboundtemplates">
    <th><label for="${constants.xldPackageBoundTemplates}">Bound templates: </label></th>
    <td><props:textProperty name="${constants.xldPackageBoundTemplates}" className="longField" expandable="true" />
        <span class="error" id="error_${constants.xldPackageBoundTemplates}"></span>
        <span class="smallNote">Bound templates, one per line</span>
    </td>
  </tr>

  <tr id="xldpackageorchestrator">
    <th><label for="${constants.xldPackageOrchestrator}">Orchestrator: </label></th>
    <td><props:textProperty name="${constants.xldPackageOrchestrator}" className="longField" expandable="true" />
        <span class="error" id="error_${constants.xldPackageOrchestrator}"></span>
        <span class="smallNote">Orchestrators, one per line</span>
    </td>
  </tr>

  <tr id="xldpackageundeploydependencies">
    <th><label for="${constants.xldPackageUndeployDependencies}">Undeploy dependencies: </label></th>
    <td><props:textProperty name="${constants.xldPackageUndeployDependencies}" className="longField" expandable="true" />
        <span class="error" id="error_${constants.xldPackageUndeployDependencies}"></span>
        <span class="smallNote">Undeploy dependencies, TRUE or FALSE</span>
    </td>
  </tr>

</l:settingsGroup>
