<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<table class="table table-striped table-bordered table-hover" style="margin: 0px;">
	<tbody>
		<tr>
			<td class="left">IP address</td>
			<td id="hostIp" class="left">${systemInfo.hostIp}</td>
		</tr>
		<tr>
			<td class="left">Host name</td>

			<td class="left" id="hostName">${systemInfo.hostName}</td>
		</tr>
		<tr>
			<td class="left">OS name</td>

			<td class="left" id="osName">${systemInfo.osName}</td>
		</tr>
		<tr>
			<td class="left">OS architecture</td>

			<td class="left" id="arch">${systemInfo.arch}</td>
		</tr>
		<tr>
			<td class="left">OS version</td>

			<td class="left" id="osVersion">${systemInfo.osVersion}</td>
		</tr>
		<tr>
			<td class="left">Processors</td>

			<td class="left" id="processors">${systemInfo.processors}</td>
		</tr>
		<tr>
			<td class="left">Java version</td>

			<td class="left" id="javaVersion">${systemInfo.javaVersion}</td>
		</tr>
		<tr>
			<td class="left">Java supplier URL</td>

			<td class="left" id="javaUrl">${systemInfo.javaUrl}</td>
		</tr>
		<tr>
			<td class="left">Java install path</td>

			<td class="left" id="javaHome">${systemInfo.javaHome}</td>
		</tr>
		<tr>
			<td class="left">Temporary files path</td>

			<td class="left" id="tmpdir">${systemInfo.tmpdir}</td>
		</tr>
	</tbody>
</table>