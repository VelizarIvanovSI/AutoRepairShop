Feature: Управление на заявки за ремонт от администратор

  Scenario: Успешен преглед на всички заявки от администратор
    Given администраторът е влязъл в системата
    When администраторът отвори страницата със заявки за ремонт
    Then се визуализира списък с всички подадени заявки

  Scenario: Потребител без права на администратор се опитва да отвори списъка със заявки
    Given потребителят е влязъл в системата без администраторски права
    When потребителят отвори страницата със заявки за ремонт
    Then се визуализира съобщение "Нямате права за достъп до тази секция"

  Scenario: Администратор променя статус на заявка от "В очакване" на "Одобрена"
    Given администраторът е влязъл в системата
    And има заявка със статус "В очакване"
    When администраторът промени статуса на "Одобрена"
    Then статусът на заявката се актуализира на "Одобрена"

  Scenario: Администратор променя статус на заявка на "Завършена"
    Given администраторът е влязъл в системата
    And има заявка със статус "Одобрена"
    When администраторът промени статуса на "Завършена"
    Then статусът на заявката се актуализира на "Завършена"

  Scenario: Опит за промяна на статус от потребител без права
    Given потребителят е влязъл в системата без администраторски права
    When потребителят се опита да промени статус на заявка
    Then се визуализира съобщение "Нямате права да редактирате заявки"

  Scenario: Администратор отхвърля заявка
    Given администраторът е влязъл в системата
    And има заявка със статус "В очакване"
    When администраторът избере "Отхвърли заявката"
    Then заявката се маркира като "Отхвърлена"

  Scenario: Администратор изтрива заявка
    Given администраторът е влязъл в системата
    And има заявка със статус "Отхвърлена"
    When администраторът избере "Изтрий заявката"
    Then заявката се премахва от списъка