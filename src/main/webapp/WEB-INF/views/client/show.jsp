<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst"%>
<%@ page import="constants.AttributeConst"%>

<c:set var="actRep" value="${ForwardConst.ACT_REP.getValue()}" />
<c:set var="actCli" value="${ForwardConst.ACT_CLI.getValue()}" />
<c:set var="actCR" value="${ForwardConst.ACT_C_REP.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />
<c:set var="commDes" value="${ForwardConst.CMD_DESTROY.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
	<c:param name="content">

		<h3>${client.name}   詳細ページ</h3>

		<h3>現在商談状況　： <c:choose>
								<c:when test="${client.situationFlag == AttributeConst.SIT_TRUE.getIntegerValue()}">商談中</c:when>
								<c:otherwise>
					  	           商談完了
					  	        </c:otherwise>
							</c:choose></h3>

        <h3>商談状況報告： <button type="submit" onclick="location.href='?action=${actCR}&command=${commUpd}&id=${client.id}'">商談完了</button> <button type="submit" onclick="location.href='?action=${actCR}&command=${commDes}&id=${client.id}'">商談完了取消</button></h3>

		<h3>顧客登録者：${client.employee.name}</h3>

        <h3>住所：${client.address}</h3>

		<table id="report_list">
            <tbody>
                <tr>
                    <th class="report_name">氏名</th>
                    <th class="report_date">日付</th>
                    <th class="report_title">タイトル</th>
                    <th class="report_action">日報詳細</th>
                </tr>
                <c:forEach var="report" items="${reports}" varStatus="status">
                    <fmt:parseDate value="${report.reportDate}" pattern="yyyy-MM-dd" var="reportDay" type="date" />
                    <tr class="row${status.count % 2}">
                        <td class="report_name"><c:out value="${report.employee.name}" /></td>
                        <td class="report_date"><fmt:formatDate value='${reportDay}' pattern='yyyy-MM-dd' /></td>
                        <td class="report_title">${report.title}</td>
                        <td class="report_action"><a href="<c:url value='?action=${actRep}&command=${commShow}&id=${client.id}' />">詳細を見る</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

		<p>
			<a href="<c:url value='?action=${actCli}&command=${commEdt}&id=${client.id}'/>">顧客の修正</a>
		</p>

		<p>
			<a href="<c:url value='?action=${actCli}&command=${commIdx}'/>">一覧に戻る</a>
		</p>

	</c:param>
</c:import>
