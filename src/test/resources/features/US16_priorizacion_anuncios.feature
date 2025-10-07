# language: es
Característica: Priorización de anuncios
  Como gerente
  Quiero marcar anuncios como prioritarios
  Para que los empleados los vean primero

  Escenario: Marcar un anuncio como prioritario
    Dado que soy un gerente autenticado
    Y existe un anuncio publicado
    Cuando marco el anuncio como prioritario
    Entonces el anuncio debe aparecer al inicio de la lista
    Y debe tener una etiqueta de "Prioritario"

  Escenario: Remover prioridad de un anuncio
    Dado que soy un gerente autenticado  
    Y existe un anuncio marcado como prioritario
    Cuando remuevo la prioridad del anuncio
    Entonces el anuncio debe volver a su posición normal
    Y no debe mostrar la etiqueta de "Prioritario"