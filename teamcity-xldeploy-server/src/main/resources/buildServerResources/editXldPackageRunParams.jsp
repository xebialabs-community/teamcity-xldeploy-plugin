<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags/admin" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>
<jsp:useBean id="constants" class="jetbrains.buildServer.xldeploy.server.XldPackageConstantsBean"/>

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

</l:settingsGroup>
