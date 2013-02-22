<%-- 
    Document   : Login
    Created on : 3 déc. 2012, 09:48:13
    Author     : Quicksort
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="conference" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<conference:Content>
       <form method="post" action="">
            <fieldset>
                <legend>Login</legend>

                <label for="email">Email<span class="requis">*</span></label>
                <input type="text" id="email" name="email" value="<c:out value="${user.email}"/>" size="20" maxlength="60" />
                <span class="erreur">${form.errors['email']}</span>
                <br />

                <label for="c">Password<span class="requis">*</span></label>
                <input type="password" id="password" name="password" value="" size="20" maxlength="20" />
                <span class="erreur">${form.errors['password']}</span>
                <br />
                <input type="submit" value="Inscription" class="sansLabel" />
                <br />
                
                <p class="${empty form.errors ? 'succes' : 'erreur'}">${form.result}</p>
       </form>
</conference:Content>
