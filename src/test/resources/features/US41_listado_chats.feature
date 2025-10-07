# language: es
Característica: Listado organizado de chats
  Como empleado
  Quiero ver todos los chats de los que forma parte
  Para encontrar rápidamente conversaciones específicas

  Escenario: Ver lista de chats ordenada por actividad reciente
    Dado que soy un empleado autenticado
    Y pertenezco a múltiples chats grupales
    Cuando accedo a la lista de chats
    Entonces debo ver los chats ordenados por último mensaje
    Y cada chat debe mostrar el último mensaje y la hora

  Escenario: Buscar chat específico
    Dado que soy un empleado autenticado
    Y tengo muchos chats en mi lista
    Cuando busco un chat por nombre "Proyecto Beta"
    Entonces solo debe aparecer el chat que coincide con la búsqueda