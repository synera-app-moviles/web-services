# language: es
Característica: Creación de chats grupales
  Como empleado
  Quiero crear chats grupales
  Para discutir temas específicos con mis colegas

  Escenario: Crear un chat grupal
    Dado que soy un empleado autenticado
    Cuando creo un chat grupal con nombre "Proyecto Alpha"
    Y agrego a los empleados "juan@company.com" y "maria@company.com"
    Entonces el chat grupal debe ser creado exitosamente
    Y todos los miembros deben poder ver el chat

  Escenario: Crear chat grupal sin miembros
    Dado que soy un empleado autenticado
    Cuando intento crear un chat grupal sin agregar miembros
    Entonces debo recibir un error de validación
    Y el chat no debe ser creado