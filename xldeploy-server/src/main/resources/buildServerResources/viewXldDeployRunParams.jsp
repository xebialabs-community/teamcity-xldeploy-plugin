<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>
<jsp:useBean id="constants" class="jetbrains.buildServer.xldeploy.server.XldDeployConstantsBean"/>

<div class="parameter">
  XL Deploy CLI path: <strong><props:displayValue name="${constants.xldDeployCliPath}" emptyValue="not specified"/></strong>
</div>

<div class="parameter">
  XL Deploy host: <strong><props:displayValue name="${constants.xldDeployHost}" emptyValue="not specified"/></strong>
</div>

<div class="parameter">
  XL Deploy port: <strong><props:displayValue name="${constants.xldDeployPort}" emptyValue="not specified"/></strong>
</div>

<div class="parameter">
  XL Deploy username: <strong><props:displayValue name="${constants.xldDeployUsername}" emptyValue="not specified"/></strong>
</div>

<div class="parameter">
  XL Deploy password: <strong><props:displayValue name="${constants.xldDeployPassword}" emptyValue="not specified"/></strong>
</div>

<div class="parameter">
  Application name: <strong><props:displayValue name="${constants.xldDeployApplicationName}" emptyValue="not specified"/></strong>
</div>

<div class="parameter">
  Version name: <strong><props:displayValue name="${constants.xldDeployVersionName}" emptyValue="not specified"/></strong>
</div>

<div class="parameter">
  Environment id: <strong><props:displayValue name="${constants.xldDeployEnvironmentId}" emptyValue="not specified"/></strong>
</div>
