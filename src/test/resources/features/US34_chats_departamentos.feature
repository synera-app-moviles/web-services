# language: es
Característica: Chats por departamentos
  Como gerente
  Quiero crear chats automáticos por departamentos
  Para facilitar la comunicación interna

  Escenario: Crear chat de departamento automáticamente
    Dado que soy un gerente autenticado
    Y existe un departamento "Recursos Humanos"
    Cuando creo un chat para el departamento
    Entonces todos los empleados del departamento deben ser agregados automáticamente
    Y el chat debe tener el nombre del departamento

  Escenario: Nuevo empleado se une automáticamente al chat del departamento
    Dado que existe un chat del departamento "Marketing"
    Cuando un nuevo empleado es asignado al departamento "Marketing"
    Entonces debe ser agregado automáticamente al chat del departamento