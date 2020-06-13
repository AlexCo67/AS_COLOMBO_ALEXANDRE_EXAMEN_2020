<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="ISO-8859-1">
<title>Fin de Transaction</title>
</head>
<body>
<br> La transaction s'est effectuée avec succès <br>

Le compte <s:text name="numeroCompte"></s:text> a désormais une balance de <s:text name="%{soldeCompte}"> </s:text> <br>


<a href="http:\\localhost:8080/bankofIUT"> Retour à l'écran de connection</a>
</body>
</html>