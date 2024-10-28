# Описание проекта Virual Cards for T1-Debut

## Поставленная задача

Разработать Backend-приложение, реализующее функционал управления виртуальными картами
- создание
- персонализация
- кастомизация.

## Решение

Данный проект — это система управления виртуальными клубными картами, разработанная на Java с использованием Spring Framework. Система предоставляет CRUD-операции для управления клубными участниками, картами, ролями и шаблонами карт. Она позволяет гибко назначать и управлять виртуальными картами в зависимости от ролей участников и доступных шаблонов:

1. **Управление участниками клуба**: Реализиван `CRUD` (Create, Read, Update, Delete) интерфейс для управления участниками клуба.

2. **Управление клубными картами**: Реализован `CRUD` интерфейс для управления клубными картами.

3. **Персонализация клубных карт**: К каждой новой созданной карте можно привязать (персонализировать) существуещего пользователя (участника) клуба.

4. **Кастомизация клубных карт**: Проект реализует систему ролей, которая управляет шаблонами карт, доступными для участников:
- Роли участников определяют доступные шаблоны карт.
- Если роль участника совпадает с ролью шаблона, то данный шаблон может быть применён к карте участника.


Для начала создаются роли, от роли создается шаблон(ы) и пользователь(и), для каждого пользователя можно создать свою карточку с доступным для него шаблоном.

## API документация

### Карты (Cards)

#### I. Получение информации о карте по ID
1. **Метод запроса**: `GET`
2. **URL**: `/api/v1/cards/{id}`
3. **Параметры**:
    - `id` (UUID, path) — идентификатор карты.
4. **Пример ответа**:
    ```json
    {
      "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "cardMemberId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "templateType": {
      "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "name": "string",
      "role": "string"
      }
    }
    ```
5. **Метод контроллера**:
    ```java
    @GetMapping("/{id}")
    public ResponseEntity<CardResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(cardService.findById(id));
    }
    ```
6. **Возможные ответы**:
    - **200 OK**: Карта найдена и возвращена.
    - **404 Not Found**: Карта с данным `id` не найдена.

---

#### II. Создание новой карты
1. **Метод запроса**: `POST`
2. **URL**: `/api/v1/cards`
3. **Тело запроса**:
    ```json
    {
    "memberId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    "templateTypeId": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    }
    ```
4. **Пример ответа**:
    ```json
    {
      "id": "9a502384-3d0a-47b8-bee6-01f3bf6736de",
      "cardMemberId": "954dba0f-d861-4d59-92e7-d091b0b89356",
      "templateType": {
      "id": "1137f39f-41f2-4006-a2be-e1a5c0f114bc",
      "name": "VIP",
      "role": "VIP"
      }
    }
    ```
5. **Метод контроллера**:
    ```java
    @PostMapping
    public ResponseEntity<?> createCard(@RequestBody CardRequest request) {
        CardResponse response = cardService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    ```
6. **Возможные ответы**:
    - **201 Created**: Карта успешно создана.
    - **400 Bad Request**: Некорректные данные в запросе.
    - **409 Conflict**: У данного пользователя уже есть карта

---

#### III. Получение всех карт (с пагинацией)
1. **Метод запроса**: `GET`
2. **URL**: `/api/v1/cards`
3. **Параметры**:
    - `page` (integer, query) — номер страницы (начиная с 0).
    - `size` (integer, query) — количество элементов на странице.
4. **Пример ответа**:
    ```json
    {
      "totalCount": 2,
      "data": [
          {
            "id": "600210b8-90bc-4768-acd6-f9dda649f4fe",
            "cardMemberId": "88222809-c19b-444c-8549-2e1ef065cd6c",
            "templateType": {
            "id": "1137f39f-41f2-4006-a2be-e1a5c0f114bc",
            "name": "VIP",
            "role": "VIP"
          }
        }
      ]
    }
    ```
5. **Метод контроллера**:
    ```java
    @GetMapping
    public ResponseEntity<ModelListResponse<CardResponse>> getAllCards(Pageable pageable) {
        try {
            return ResponseEntity.ok(cardService.findAll(pageable).get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
    }}
    ```
6. **Возможные ответы**:
    - **200 OK**: Карты возвращены.
    - **500 Internal Server Error**: Ошибка обработки запроса.

---

#### IV. Обновление информации о карте
1. **Метод запроса**: `PUT`
2. **URL**: `/api/v1/cards/{id}`
3. **Параметры**:
    - `id` (UUID, path) — идентификатор обновляемой карты.
4. **Тело запроса**:
    ```json
    {
    "memberId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    "templateTypeId": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    }
    ```
5. **Пример ответа**:
    ```json
    {
      "id": "9a502384-3d0a-47b8-bee6-01f3bf6736de",
      "cardMemberId": "954dba0f-d861-4d59-92e7-d091b0b89356",
      "templateType": {
      "id": "1137f39f-41f2-4006-a2be-e1a5c0f114bc",
      "name": "VIP",
      "role": "VIP"
      }
    }
    ```
6. **Метод контроллера**:
    ```java
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCard(@PathVariable UUID id, @RequestBody CardRequest request) {
        return ResponseEntity.ok(cardService.update(id, request));
    }
    ```
7. **Возможные ответы**:
    - **200 OK**: Карта успешно обновлена.
    - **400 Bad Request**: Некорректные данные в запросе.
    - **404 Not Found**: Карта с данным `id` не найдена.


### V. Удаление карты
1. **Метод запроса**: `DELETE`
2. **URL**: `/api/v1/cards/{id}`
3. **Параметры**:
    - `id` (UUID, path) — идентификатор удаляемой карты.
4. **Метод контроллера**:
   ```java
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteCard(@PathVariable UUID id) {
       cardService.deleteById(id);
       return ResponseEntity.noContent().build();
   }
   ```
   **Возможные ответы**:
    - **204 No Content**: Карта успешно удалена.
    - **404 Not Found**: Карта с данным `id` не найдена.

### Участники клуба (Card Members)

#### I. Получение информации о участнике карты по ID
1. **Метод запроса**: `GET`
2. **URL**: `/api/v1/card-members/{id}`
3. **Параметры**:
    - `id` (UUID, path) — идентификатор участника карты.
4. **Пример ответа**:
    ```json
    {
      "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "firstname": "string",
      "middlename": "string",
      "lastname": "string",
      "company": "string",
      "position": "string",
      "email": "string",
      "phone": "string",
      "role": {
      "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "role": "string"
      }
    }
    ```
5. **Метод контроллера**:
    ```java
    @GetMapping("/{id}")
    public ResponseEntity<CardMemberResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(cardMemberService.findById(id));
    }
    ```
6. **Возможные ответы**:
    - **200 OK**: Участник карты найден и возвращен.
    - **404 Not Found**: Участник карты с данным `id` не найден.

---

#### II. Создание нового участника карты
1. **Метод запроса**: `POST`
2. **URL**: `/api/v1/card-members`
3. **Тело запроса**:
    ```json
    {
    "firstname": "string",
    "middlename": "string",
    "lastname": "string",
    "company": "string",
    "position": "string",
    "email": "string",
    "phone": "string",
    "roleId": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    }
    ```
4. **Пример ответа**:
    ```json
    {
      "id": "135a8596-f3c5-477e-9a84-eff371af5d5e",
      "firstname": "string",
      "middlename": "string",
      "lastname": "string",
      "company": "string",
      "position": "string",
      "email": "string",
      "phone": "string",
      "role": {
      "id": "be306004-fcdd-41fc-afc5-4ac451e36ed2",
      "role": "ADMIN"
      }
    }
    ```
5. **Метод контроллера**:
    ```java
    @PostMapping
    public ResponseEntity<CardMemberResponse> createCardMember(@RequestBody CardMemberRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cardMemberService.create(request));
    }
    ```
6. **Возможные ответы**:
    - **201 Created**: Участник карты успешно создан.
    - **400 Bad Request**: Некорректные данные в запросе.

---

#### III. Получение всех участников карт (с пагинацией)
1. **Метод запроса**: `GET`
2. **URL**: `/api/v1/card-members`
3. **Параметры**:
    - `page` (integer, query) — номер страницы (начиная с 0).
    - `size` (integer, query) — количество элементов на странице.
4. **Пример ответа**:
    ```json
   {
    "totalCount": 10,
    "data": [
      {
        "id": "5b36d6e2-cf18-4865-ae6a-c43b1b8bd392",
        "firstname": "string",
        "middlename": "string",
        "lastname": "string",
        "company": "string",
        "position": "string",
        "email": "string",
        "phone": "string",
        "role": {
          "id": "b76483c2-4e95-495a-a3b6-537176f991f9",
          "role": "VIP"
        }
      }
    ]
   }
    ```
5. **Метод контроллера**:
    ```java
    @GetMapping
    public ResponseEntity<ModelListResponse<CardMemberResponse>> getAllCardMembers(Pageable pageable) {
        try {
            return ResponseEntity.ok(cardMemberService.findAll(pageable).get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    ```
6. **Возможные ответы**:
    - **200 OK**: Участники карт возвращены.
    - **500 Internal Server Error**: Ошибка обработки запроса.

---

#### IV. Обновление информации о участнике карты
1. **Метод запроса**: `PUT`
2. **URL**: `/api/v1/card-members/{id}`
3. **Параметры**:
    - `id` (UUID, path) — идентификатор обновляемого участника карты.
4. **Тело запроса**:
    ```json
   {
   "firstname": "string",
   "middlename": "string",
   "lastname": "string",
   "company": "string",
   "position": "string",
   "email": "string",
   "phone": "string",
   "roleId": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
   }
    ```
5. **Пример ответа**:
    ```json
   {
   "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
   "firstname": "string",
   "middlename": "string",
   "lastname": "string",
   "company": "string",
   "position": "string",
   "email": "string",
   "phone": "string",
   "role": {
   "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
   "role": "string"
   }
   }
    ```
6. **Метод контроллера**:
    ```java
    @PutMapping("/{id}")
    public ResponseEntity<CardMemberResponse> updateCardMember(@PathVariable UUID id, @RequestBody CardMemberRequest request) {
        return ResponseEntity.ok(cardMemberService.update(id, request));
    }
    ```
7. **Возможные ответы**:
    - **200 OK**: Участник карты успешно обновлен.
    - **400 Bad Request**: Некорректные данные в запросе.
    - **404 Not Found**: Участник карты с данным `id` не найден.

---

#### V. Удаление участника карты
1. **Метод запроса**: `DELETE`
2. **URL**: `/api/v1/card-members/{id}`
3. **Параметры**:
    - `id` (UUID, path) — идентификатор удаляемого участника карты.
4. **Метод контроллера**:
    ```java
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCardMember(@PathVariable UUID id) {
        cardMemberService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    ```
5. **Возможные ответы**:
    - **204 No Content**: Участник карты успешно удален.
    - **404 Not Found**: Участник карты с данным `id` не найден.


## Роли (Roles)

### I. Получение информации о роли по ID
1. **Метод запроса**: `GET`
2. **URL**: `/api/v1/role/{id}`
3. **Параметры**:
   - `id` (UUID, path) — идентификатор роли.
   4. **Пример ответа**:
       ```json
      {
      "roleId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "role": "string"
      }
       ```
5. **Метод контроллера**:
    ```java
    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(roleService.findById(id));
    }
    ```
6. **Возможные ответы**:
   - **200 OK**: Роль найдена и возвращена.
   - **404 Not Found**: Роль с данным `id` не найдена.

---

### II. Получение всех ролей
1. **Метод запроса**: `GET`
2. **URL**: `/api/v1/role`
3. **Параметры**:
   - `page` (int, query) — номер страницы (по умолчанию 0).
   - `size` (int, query) — количество ролей на странице (по умолчанию 10).
4. **Пример ответа**:
    ```json
   {
   "totalCount": 2,
   "data": [
      {
      "roleId": "9517a4f0-d86a-4e8c-ba0b-96ccbd89611b",
      "role": "VIP"
      }
    ]
   }
    ```
5. **Метод контроллера**:
    ```java
    @GetMapping
    public ResponseEntity<ModelListResponse<RoleResponse>> getAllRoles(Pageable pageable) {
        try {
            return ResponseEntity.ok(roleService.findAll(pageable).get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    ```
6. **Возможные ответы**:
   - **200 OK**: Список ролей успешно возвращён.
   - **404 Not Found**: Роли не найдены.

---

### III. Создание новой роли
1. **Метод запроса**: `POST`
2. **URL**: `/api/v1/role`
3. **Тело запроса**:
    ```json
   {
   "role": "string"
   }
    ```
4. **Пример ответа**:
    ```json
   {
   "roleId": "27afbdeb-041f-44e5-96c0-81dc280c4f11",
   "role": "string"
   }
    ```
5. **Метод контроллера**:
    ```java
    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody RoleRequest request) {
        RoleResponse response = roleService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    ```
6. **Возможные ответы**:
   - **201 Created**: Роль успешно создана.
   - **400 Bad Request**: Некорректные данные в запросе.

---

### IV. Обновление информации о роли
1. **Метод запроса**: `PUT`
2. **URL**: `/api/v1/role/{id}`
3. **Параметры**:
   - `id` (UUID, path) — идентификатор роли.
4. **Тело запроса**:
    ```json
   {
   "role": "string"
   }
    ```
5. **Метод контроллера**:
    ```java
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable UUID id, @RequestBody RoleRequest request) {
        return ResponseEntity.ok(roleService.update(id, request));
    }
    ```
6. **Возможные ответы**:
   - **200 OK**: Роль успешно обновлена.
   - **404 Not Found**: Роль с данным `id` не найдена.

---

### V. Удаление роли
1. **Метод запроса**: `DELETE`
2. **URL**: `/api/v1/role/{id}`
3. **Параметры**:
   - `id` (UUID, path) — идентификатор роли.
4. **Метод контроллера**:
    ```java
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable UUID id) {
        roleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    ```
5. **Возможные ответы**:
   - **204 No Content**: Роль успешно удалена.
   - **404 Not Found**: Роль с данным `id` не найдена.


---

## Шаблоны (Templates)

### I. Получение информации о шаблоне по ID
1. **Метод запроса**: `GET`
2. **URL**: `/api/v1/template/{id}`
3. **Параметры**:
   - `id` (UUID, path) — идентификатор шаблона.
4. **Пример ответа**:
    ```json
   {
   "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
   "name": "string",
   "role": "string"
   }
    ```
5. **Метод контроллера**:
    ```java
    @GetMapping("/{id}")
    public ResponseEntity<TemplateResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(templateService.findById(id));
    }
    ```
6. **Возможные ответы**:
   - **200 OK**: Шаблон найден и возвращён.
   - **404 Not Found**: Шаблон с данным `id` не найден.

---

### II. Получение всех шаблонов
1. **Метод запроса**: `GET`
2. **URL**: `/api/v1/template`
3. **Параметры**:
   - `page` (int, query) — номер страницы (по умолчанию 0).
   - `size` (int, query) — количество шаблонов на странице (по умолчанию 10).
4. **Пример ответа**:
    ```json
   {
      "totalCount": 5,
      "data": [
      {
      "id": "8fcdb207-ddbf-439f-834b-9be180c647ff",
      "name": "VIP1",
      "role": "VIP"
      }
    ]
   }
    ```
5. **Метод контроллера**:
    ```java
    @GetMapping
    public ResponseEntity<ModelListResponse<TemplateResponse>> getAllTemplates(Pageable pageable) {
        try {
            return ResponseEntity.ok(templateService.findAll(pageable).get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    ```
6. **Возможные ответы**:
   - **200 OK**: Список шаблонов успешно возвращён.
   - **404 Not Found**: Шаблоны не найдены.

---

### III. Создание нового шаблона
1. **Метод запроса**: `POST`
2. **URL**: `/api/v1/template`
3. **Тело запроса**:
    ```json
   {
   "name": "string",
   "roleId": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
   }
    ```
4. **Пример ответа**:
    ```json
   {
   "id": "475f7dab-8d5f-4a1c-96c6-c3fee4e69096",
   "name": "string",
   "role": "VIP"
   }
    ```
5. **Метод контроллера**:
    ```java
    @PostMapping
    public ResponseEntity<?> createTemplate(@RequestBody TemplateRequest request) {
        TemplateResponse response = templateService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    ```
6. **Возможные ответы**:
   - **201 Created**: Шаблон успешно создан.
   - **400 Bad Request**: Некорректные данные в запросе.

---

### IV. Обновление информации о шаблоне
1. **Метод запроса**: `PUT`
2. **URL**: `/api/v1/template/{id}`
3. **Параметры**:
   - `id` (UUID, path) — идентификатор шаблона.
4. **Тело запроса**:
    ```json
   {
   "name": "string",
   "roleId": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
   }
    ```
5. **Метод контроллера**:
    ```java
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTemplate(@PathVariable UUID id, @RequestBody TemplateRequest request) {
        return ResponseEntity.ok(templateService.update(id, request));
    }
    ```
6. **Возможные ответы**:
   - **200 OK**: Шаблон успешно обновлён.
   - **404 Not Found**: Шаблон с данным `id` не найден.

---

### V. Удаление шаблона
1. **Метод запроса**: `DELETE`
2. **URL**: `/api/v1/template/{id}`
3. **Параметры**:
   - `id` (UUID, path) — идентификатор шаблона.
4. **Метод контроллера**:
    ```java
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable UUID id) {
        templateService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    ```
5. **Возможные ответы**:
   - **204 No Content**: Шаблон успешно удалён.
   - **404 Not Found**: Шаблон с данным `id` не найден.

---

### VI. Получение всех шаблонов по ID роли
1. **Метод запроса**: `GET`
2. **URL**: `/api/v1/template/byRole/{id}`
3. **Параметры**:
   - `id` (UUID, path) — идентификатор роли.
   - `page` (int, query) — номер страницы (по умолчанию 0).
   - `size` (int, query) — количество шаблонов на странице (по умолчанию 10).
4. **Пример ответа**:
    ```json
   {
      "totalCount": 5,
      "data": [
      {
      "id": "8fcdb207-ddbf-439f-834b-9be180c647ff",
      "name": "VIP1",
      "role": "VIP"
      }
    ]
   }
    ```
5. **Метод контроллера**:
    ```java
    @GetMapping("/byRole/{id}")
    public ResponseEntity<ModelListResponse<TemplateResponse>> getAllTemplatesByRoleId(@PathVariable UUID id, Pageable pageable) {
        try {
            return ResponseEntity.ok(templateService.findAllByRoleId(id, pageable).get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    ```
6. **Возможные ответы**:
   - **200 OK**: Список шаблонов успешно возвращён.
   - **404 Not Found**: Шаблоны для заданной роли не найдены.

---

## Запуск проекта

### Требования

- Docker
- Docker Compose

1. Склонируйте репозиторий на свой компьютер:

    ```bash
    git clone https://github.com/Takeasoul/Debut-T1.git
    ```

2. Перейдите в каталог проекта:

    ```bash
    cd Debut-T1
    ```

3. Соберите и запустите контейнеры Docker с помощью Docker Compose:

    ```bash
    docker-compose up --build
    ```

### Порты и доступы

- **PostgreSQL**:
    - **Порт:** 5432
    - **База данных:** Debut
    - **Пользователь:** postgres
    - **Пароль:** admin
    - **Адрес:** localhost:5432

- **Backend приложение**:
    - **Порт:** 8080
    - **Адрес:** localhost:8080
    - **OpenAPI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
    - **Конфигурация:** `application.yaml`

  

