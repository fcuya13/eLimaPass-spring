Feature: Recargas Integration Tests
  Scenario: Recargar con Yape
    Given recarga con "yape" de 20 soles de la tarjeta "1212121212"
    When recargo la tarjeta
    Then se genera recarga con "yape"

  Scenario: Recargar con Tarjeta
    Given recarga con "tarjeta" de 20 soles de la tarjeta "1212121212"
    When recargo la tarjeta
    Then se genera recarga con "tarjeta"

  Scenario: Consultar las recargas más recientes de mi tarjeta
    Given tengo una tarjeta con codigo "1212121212"
    When quiero ver mi historial de recargas
    Then debo ver las recargas más recientes ordenadas por fecha descendente