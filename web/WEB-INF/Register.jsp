<%-- 
    Document   : Register
    Created on : 28 nov. 2012, 18:57:26
    Author     : Quicksort
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="conference" tagdir="/WEB-INF/tags" %>
<conference:Content>
        <form method="post" action="">
            <fieldset>
                <legend>Registration</legend>

                <label for="email">Email<span class="requis">*</span></label>
                <input type="text" id="email" name="email" value="<c:out value="${user.email}"/>" size="20" maxlength="60" />
                <span class="erreur">${form.errors['email']}</span>
                <br />

                <label for="c">Password<span class="requis">*</span></label>
                <input type="password" id="password" name="password" value="" size="20" maxlength="20" />
                <span class="erreur">${form.errors['password']}</span>
                <br />

                <label for="password_confirmation">Password confirmation<span class="requis">*</span></label>
                <input type="password" id="password_confirmation" name="password_confirmation" value="" size="20" maxlength="20" />
                <span class="erreur">${form.errors['password_confirmation']}</span>
                <br />

                <label for="display_name">Display name</label>
                <input type="text" id="display_name" name="display_name" value="<c:out value="${user.displayName}"/>" size="20" maxlength="20" />
                <span class="erreur">${form.errors['display_name']}</span>
                <br />

                <input type="submit" value="Inscription" class="sansLabel" />
                <br />
                
                <p class="${empty form.errors ? 'succes' : 'erreur'}">${form.result}</p>
            </fieldset>
        </form>
</conference:Content>