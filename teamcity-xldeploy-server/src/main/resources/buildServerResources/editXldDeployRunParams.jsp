<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags/admin" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>
<jsp:useBean id="constants" class="jetbrains.buildServer.xldeploy.server.XldDeployConstantsBean"/>

<l:settingsGroup title="XL Deploy:Deploy">

  <tr id="xlddeployhost">
    <th><label for="${constants.xldDeployHost}">XL Deploy host: <l:star/></label></th>
    <td><props:textProperty name="${constants.xldDeployHost}" className="longField"/>
      <span styleclass="error" id="error_${constants.xldDeployHost}"></span>
      <span styleclass="smallNote">XL Deploy host</span>
    </td>
  </tr>

  <tr id="xlddeployport">
    <th><label for="${constants.xldDeployPort}">XL Deploy port: <l:star/></label></th>
    <td><props:textProperty name="${constants.xldDeployPort}" className="longField"/>
      <span styleclass="error" id="error_${constants.xldDeployPort}"></span>
      <span styleclass="smallNote">XL Deploy port</span>
    </td>
  </tr>

  <tr id="xlddeploycontextroot">
    <th><label for="${constants.xldDeployContextRoot}">XL Deploy context root: </label></th>
    <td><props:textProperty name="${constants.xldDeployContextRoot}" className="longField"/>
      <span styleclass="error" id="error_${constants.xldDeployContextRoot}"></span>
      <span styleclass="smallNote">XL Deploy context root</span>
    </td>
  </tr>

  <tr id="xlddeployusername">
    <th><label for="${constants.xldDeployUsername}">XL Deploy username: <l:star/></label></th>
    <td><props:textProperty name="${constants.xldDeployUsername}" className="longField"/>
      <span styleclass="error" id="error_${constants.xldDeployUsername}"></span>
      <span styleclass="smallNote">XL Deploy username</span>
    </td>
  </tr>

  <tr id="xlddeploypassword">
    <th><label for="${constants.xldDeployPassword}">XL Deploy password: <l:star/></label></th>
    <td><props:textProperty name="${constants.xldDeployPassword}" className="longField"/>
      <span styleclass="error" id="error_${constants.xldDeployPassword}"></span>
      <span styleclass="smallNote">XL Deploy password</span>
    </td>
  </tr>

  <tr id="xlddeployapplname">
    <th><label for="${constants.xldDeployApplicationName}">Application name: <l:star/></label></th>
    <td><props:textProperty name="${constants.xldDeployApplicationName}" className="longField"/>
      <span styleclass="error" id="error_${constants.xldDeployApplicationName}"></span>
      <span styleclass="smallNote">Application name</span>
    </td>
  </tr>

  <tr id="xlddeployvername">
    <th><label for="${constants.xldDeployVersionName}">Version name: <l:star/></label></th>
    <td><props:textProperty name="${constants.xldDeployVersionName}" className="longField"/>
      <span styleclass="error" id="error_${constants.xldDeployVersionName}"></span>
      <span styleclass="smallNote">Version name</span>
    </td>
  </tr>

  <tr id="xlddeployenvid">
    <th><label for="${constants.xldDeployEnvironmentId}">Environment id: <l:star/></label></th>
    <td><props:textProperty name="${constants.xldDeployEnvironmentId}" className="longField"/>
      <span styleclass="error" id="error_${constants.xldDeployEnvironmentId}"></span>
      <span styleclass="smallNote">Environment id (full path)</span>
    </td>
  </tr>

  <tr id="xlddeployhttps">
    <th><label for="${constants.xldDeployHttps}">Use HTTPS</label></th>
    <td><props:checkboxProperty name="${constants.xldDeployHttps}"/>
      <span styleclass="error" id="error_${constants.xldDeployHttps}"></span>
      <span styleclass="smallNote">Use HTTPS in REST API calls to XL Deploy</span>
    </td>
  </tr>

  <tr id="xlddeploywait">
    <th><label for="${constants.xldDeployWait}">Wait</label></th>
    <td><props:checkboxProperty name="${constants.xldDeployWait}"/>
      <span styleclass="error" id="error_${constants.xldDeployWait}"></span>
      <span styleclass="smallNote">Wait for XL Deploy to complete the deployment</span>
    </td>
  </tr>

</l:settingsGroup>

