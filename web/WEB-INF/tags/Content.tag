<%-- 
    Document   : Content
    Created on : 4 déc. 2012, 04:11:26
    Author     : Quicksort
--%>

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="message"%>

<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="../theme.css">
	<script type="text/javascript" src="jquery.js"></script>
	<script type="text/javascript" src="script.js"></script>
</head>
<body>
	<div class="Header">
             <b>Dark Engines Corporation</b>
		<ul class="Menu">
			<li><a href="#Identity">Qui sommes-nous ?</a></li>
			<li><a href="#Solution">Nos solutions</a></li>
			<li><a href="#Contact">Nous contacter</a></li>
		</ul>
	</div>
	<div class="Content">
            <jsp:doBody />
	</div>
        <div class="Footer">

        </div>
</body>
</html>
