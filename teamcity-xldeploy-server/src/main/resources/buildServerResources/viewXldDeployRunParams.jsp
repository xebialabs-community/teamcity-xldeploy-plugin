<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>
<jsp:useBean id="constants" class="jetbrains.buildServer.xldeploy.server.XldDeployConstantsBean"/>

<div styleclass="parameter">
  XL Deploy host: <strong><props:displayValue name="${constants.xldDeployHost}" emptyValue="not specified"/></strong>
</div>

<div styleclass="parameter">
  XL Deploy port: <strong><props:displayValue name="${constants.xldDeployPort}" emptyValue="not specified"/></strong>
</div>

<div styleclass="parameter">
  XL Deploy username: <strong><props:displayValue name="${constants.xldDeployUsername}" emptyValue="not specified"/></strong>
</div>

<div styleclass="parameter">
  XL Deploy password: <strong><props:displayValue name="${constants.xldDeployPassword}" emptyValue="not specified"/></strong>
</div>

<div styleclass="parameter">
  Application name: <strong><props:displayValue name="${constants.xldDeployApplicationName}" emptyValue="not specified"/></strong>
</div>

<div styleclass="parameter">
  Version name: <strong><props:displayValue name="${constants.xldDeployVersionName}" emptyValue="not specified"/></strong>
</div>

<div styleclass="parameter">
  Environment id: <strong><props:displayValue name="${constants.xldDeployEnvironmentId}" emptyValue="not specified"/></strong>
</div>

<div styleclass="parameter">
  Use HTTPS: <strong><props:displayValue name="${constants.xldDeployHttps}" emptyValue="false"/></strong>
</div>

<div styleclass="parameter">
  Wait for completion: <strong><props:displayValue name="${constants.xldDeployWait}" emptyValue="false"/></strong>
</div>
