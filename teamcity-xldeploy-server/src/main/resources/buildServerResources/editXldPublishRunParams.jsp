<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags/admin" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>
<jsp:useBean id="constants" class="jetbrains.buildServer.xldeploy.server.XldPublishConstantsBean"/>

<l:settingsGroup title="XL Deploy:Publish">

  <tr id="xldpublishhost">
    <th><label for="${constants.xldPublishHost}">XL Deploy host: <l:star/></label></th>
    <td><props:textProperty name="${constants.xldPublishHost}" className="longField"/>
      <span styleclass="error" id="error_${constants.xldPublishHost}"></span>
      <span styleclass="smallNote">XL Deploy host</span>
    </td>
  </tr>

  <tr id="xldpublishport">
    <th><label for="${constants.xldPublishPort}">XL Deploy port: <l:star/></label></th>
    <td><props:textProperty name="${constants.xldPublishPort}" className="longField"/>
      <span styleclass="error" id="error_${constants.xldPublishPort}"></span>
      <span styleclass="smallNote">XL Deploy port</span>
    </td>
  </tr>

  <tr id="xldpublishcontextroot">
    <th><label for="${constants.xldPublishContextRoot}">XL Deploy context root: </label></th>
    <td><props:textProperty name="${constants.xldPublishContextRoot}" className="longField"/>
      <span styleclass="error" id="error_${constants.xldPublishContextRoot}"></span>
      <span styleclass="smallNote">XL Deploy context root</span>
    </td>
  </tr>

  <tr id="xldpublishusername">
    <th><label for="${constants.xldPublishUsername}">XL Deploy username: <l:star/></label></th>
    <td><props:textProperty name="${constants.xldPublishUsername}" className="longField"/>
      <span styleclass="error" id="error_${constants.xldPublishUsername}"></span>
      <span styleclass="smallNote">XL Deploy username</span>
    </td>
  </tr>

  <tr id="xldpublishpassword">
    <th><label for="${constants.xldPublishPassword}">XL Deploy password: <l:star/></label></th>
    <td><props:textProperty name="${constants.xldPublishPassword}" className="longField"/>
      <span styleclass="error" id="error_${constants.xldPublishPassword}"></span>
      <span styleclass="smallNote">XL Deploy password</span>
    </td>
  </tr>

  <tr id="xldpublishpackagepath">
    <th><label for="${constants.xldPublishPackagePath}">Package path: <l:star/></label></th>
    <td><props:textProperty name="${constants.xldPublishPackagePath}" className="longField"/>
      <span styleclass="error" id="error_${constants.xldPublishPackagePath}"></span>
      <span styleclass="smallNote">Path to .dar file (use %teamcity.build.workingDir% for the build workspace)</span>
    </td>
  </tr>

  <tr id="xlddeployhttps">
    <th><label for="${constants.xldPublishHttps}">Use HTTPS</label></th>
    <td><props:checkboxProperty name="${constants.xldPublishHttps}"/>
      <span styleclass="error" id="error_${constants.xldPublishHttps}"></span>
      <span styleclass="smallNote">Use HTTPS in REST API calls to XL Deploy</span>
    </td>
  </tr>

</l:settingsGroup>

