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
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />



<c:import url="/WEB-INF/views/layout/app.jsp">
	<c:param name="content">
		<c:if test="${flush != null}">
			<div id="flush_success">
				<c:out value="${flush}"></c:out>
			</div>
		</c:if>
		<h2>顧客の一覧</h2>
		<table id="client_list">
			<tbody>
				<tr>
					<th class="client_name">顧客名</th>
					<th class="client_situation">状況</th>
					<th class="client_action">詳細</th>
					<th class="client_report">日報作成</th>
				</tr>
				<c:forEach var="client" items="${client}" varStatus="status">
					<tr class="row${status.count % 2}">
						<td class="client_name"><c:out value="${client.name}" /></td>
						<td class="client.situation"><c:choose>
								<c:when test="${client.situationFlag == AttributeConst.SIT_TRUE.getIntegerValue()}">商談中</c:when>
								<c:otherwise>
					  	           商談完了
					  	        </c:otherwise>
							</c:choose></td>
						<td><c:choose>
								<c:when test="${client.deleteFlag == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()}">(削除済み)</c:when>
								<c:otherwise>
									<a href="<c:url value='?action=${actCR}&command=${commIdx}&id=${client.id}' />">詳細を見る</a>
								</c:otherwise>
							</c:choose></td>
						<td><c:choose>
								<c:when test="${client.deleteFlag == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()}">（削除済み）</c:when>
								<c:otherwise>
									<a href="<c:url value='?action=${actRep}&command=${commNew}&id=${client.id}' />">日報作成</a>
								</c:otherwise>
							</c:choose></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<div id="pagination">
			（全 ${reports_count} 件）<br />
			<c:forEach var="i" begin="1"
				end="${((reports_count - 1) / maxRow) + 1}" step="1">
				<c:choose>
					<c:when test="${i == page}">
						<c:out value="${i}" />&nbsp;
                    </c:when>
					<c:otherwise>
						<a
							href="<c:url value='?action=${actCli}&command=${commIdx}&page=${i}' />"><c:out
								value="${i}" /></a>&nbsp;
                    </c:otherwise>
				</c:choose>
			</c:forEach>
		</div>

		<p>
			<a href="<c:url value='?action=${actCli}&command=${commNew}' />">顧客の追加</a>
		</p>

	</c:param>
</c:import>