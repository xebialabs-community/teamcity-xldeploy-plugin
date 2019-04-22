<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>
<jsp:useBean id="constants" class="jetbrains.buildServer.xldeploy.server.XldPackageConstantsBean"/>

<%@ page import="java.util.ArrayList,java.util.List" %>

<%-- TO-DO:  Dynamically modify the view and edit JSPs to present types and properties as the user builds the package.  --%>

<div class="parameter">
  XL Deploy host: <strong><props:displayValue name="${constants.xldPackageHost}" emptyValue="not specified"/></strong>
</div>

<div class="parameter">
  XL Deploy port: <strong><props:displayValue name="${constants.xldPackagePort}" emptyValue="not specified"/></strong>
</div>

<div class="parameter">
  XL Deploy username: <strong><props:displayValue name="${constants.xldPackageUsername}" emptyValue="not specified"/></strong>
</div>

<div class="parameter">
  XL Deploy password: <strong><props:displayValue name="${constants.xldPackagePassword}" emptyValue="not specified"/></strong>
</div>

<div class="parameter">
  Application name: <strong><props:displayValue name="${constants.xldPackageApplicationName}" emptyValue="not specified"/></strong>
</div>

<div class="parameter">
  Version name: <strong><props:displayValue name="${constants.xldPackageVersionName}" emptyValue="not specified"/></strong>
</div>

<div class="parameter">
  Use HTTPS: <strong><props:displayValue name="${constants.xldPackageHttps}" emptyValue="false"/></strong>
</div>

<div class="parameter">
  Deployables: <strong><props:displayValue name="${constants.xldPackageDeployables}"/></strong>
</div>

<div class="parameter">
  Artifact locations: <strong><props:displayValue name="${constants.xldPackageArtifactLocations}"/></strong>
</div>

<div class="parameter">
  Templates: <strong><props:displayValue name="${constants.xldPackageTemplates}"/></strong>
</div>

<div class="parameter">
  Dependency resolution: <strong><props:displayValue name="${constants.xldPackageDependencyResolution}"/></strong>
</div>

<div class="parameter">
  Application dependencies: <strong><props:displayValue name="${constants.xldPackageApplicationDependencies}"/></strong>
</div>

<div class="parameter">
  Bound templates: <strong><props:displayValue name="${constants.xldPackageBoundTemplates}"/></strong>
</div>

<div class="parameter">
  Orchestrator: <strong><props:displayValue name="${constants.xldPackageOrchestrator}"/></strong>
</div>

<div class="parameter">
  Undeploy dependencies: <strong><props:displayValue name="${constants.xldPackageUndeployDependencies}"/></strong>
</div>
