siteMapCheck
============

Este programa verifica la valides del sitemap, en lo q respecta a la navegacion usando pilas para mejorar el rendimiento.
se imboca asi:
```java
java -jar siteMapCheck.jar http://<URLSite.com>/sitemap.xml 10
```
o 
```java
java -jar siteMapCheck.jar file:///D:/Users/Anibal/desktop/71f7703f-ed17-427a-bc9b-be2cc4629174.xml 10
```
para aydarse en la validacion del archivo puede abrirlo con Google Chrome, este le muestra los errores en el archivo
donde el primer valor es la url y la segunda el numero de pilas, no tiene limite de pilas
