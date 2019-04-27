<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>
<jsp:useBean id="constants" class="jetbrains.buildServer.xldeploy.server.XldPublishConstantsBean"/>

<div styleclass="parameter">
  XL Deploy host: <strong><props:displayValue name="${constants.xldPublishHost}" emptyValue="not specified"/></strong>
</div>

<div styleclass="parameter">
  XL Deploy port: <strong><props:displayValue name="${constants.xldPublishPort}" emptyValue="not specified"/></strong>
</div>

<div styleclass="parameter">
  XL Deploy context root: <strong><props:displayValue name="${constants.xldPublishContextRoot}" emptyValue="not specified"/></strong>
</div>

<div styleclass="parameter">
  XL Deploy username: <strong><props:displayValue name="${constants.xldPublishUsername}" emptyValue="not specified"/></strong>
</div>

<div styleclass="parameter">
  XL Deploy password: <strong><props:displayValue name="${constants.xldPublishPassword}" emptyValue="not specified"/></strong>
</div>

<div styleclass="parameter">
  Deployment Archive package path: <strong><props:displayValue name="${constants.xldPublishPackagePath}" emptyValue="not specified"/></strong>
</div>

<div styleclass="parameter">
  Use HTTPS: <strong><props:displayValue name="${constants.xldPublishHttps}" emptyValue="false"/></strong>
</div>

