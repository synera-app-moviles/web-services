# language: es
Característica: Creación básica de eventos
  Como gerente
  Quiero crear eventos en la aplicación móvil
  Para organizar reuniones y actividades de la empresa

  Escenario: Crear un evento básico
    Dado que soy un gerente autenticado
    Cuando creo un evento con título "Reunión de equipo" y fecha "2025-10-15"
    Y establezco la hora como "14:00"
    Entonces el evento debe ser creado exitosamente
    Y debe aparecer en el calendario de eventos

  Escenario: Crear evento sin fecha
    Dado que soy un gerente autenticado
    Cuando intento crear un evento sin especificar fecha
    Entonces debo recibir un error de validación
    Y el evento no debe ser creado