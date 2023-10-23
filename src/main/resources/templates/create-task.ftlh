<!DOCTYPE html>
<html lang="en">
<head>
    <title>Create task</title>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1" name="viewport">
    <link rel="icon" type="image/x-icon" href="/img/bar.svg">
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h2>Новая задача</h2>
    <div>
        <#if currentUser == "admin">
        <a href="/todo/today-tasks" class="btn btn-primary btn-sm" role="button">Назад</a>
        <#else>
        <a href="/todo/user-act-tasks" class="btn btn-primary btn-sm" role="button">Назад</a>
        </#if>
    </div>
    <div style="margin-top: 5px"></div>
        <form id="myForm" action="/todo/create" method="post" name="addTaskDto">
            <table class="table">
                <tbody>
                    <tr>
                        <th>Задача</th>
                        <td>
                            <input name="Task"
                                   class="form-control form-control-sm"
                                   autofocus
                                   type="text" required placeholder="Новая задача"
                                   maxlength="150"
                                   value="<#if addTaskDto.task??>${addTaskDto.task}</#if>"/>
                        </td>
                    </tr>
                    <tr>
                        <th>Комментарий</th>
                        <td>
                            <textarea
                                    name="Comment"
                                    class="form-control form-control-sm"
                                    rows="5" wrap="hard"
                                    maxlength="255"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <th>Город</th>
                        <td>
                            <div>
                                <input name="City"
                                       id="city"
                                       class="form-control form-control-sm"
                                       type="text"
                                       placeholder="Комментарии к задаче"
                                       readonly
                                       value="<#if addTaskDto.city??>${addTaskDto.city}</#if>"/>
                            </div>
                            <div id="cityButtonContainer">
                                <#list cities as city>
                                    <button class="cityButton btn btn-outline-secondary"
                                            style="--bs-btn-padding-y: .05rem; --bs-btn-padding-x: .5rem; --bs-btn-font-size: .87rem;"
                                            type="button">${city}</button>
                                </#list>
                            </div>
                        </td>
                    </tr>
                <tr>
                    <th>Ответственный</th>
                    <td>
                        <div>
                            <input name="account"
                                   id="nameInput"
                                   class="form-control form-control-sm"
                                   type="text"
                                   required
                                   readonly
                                   value="${addTaskDto.account}"/>
                        </div>
                        <div id="buttonContainer">
                            <#list responsibles as name>
                                <button class="nameButton btn btn-outline-secondary"
                                        style="--bs-btn-padding-y: .05rem; --bs-btn-padding-x: .5rem; --bs-btn-font-size: .87rem;"
                                        type="button">${name}</button>
                            </#list>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>Дедлайн</th>
                    <td>
                        <p>
                            <label><input type="radio" name="weekOrDate" value="date" checked>Дата</label>
                            <label><input type="radio" name="weekOrDate" value="week">Неделя</label>
                        </p>
                        <div id="dateInput" style="width: 20%">
                            <input name="dueDate"
                                   id="date"
                                   class="form-control form-control-sm"
                                   type="date"
                                   value="<#if addTaskDto.dueDate??>${addTaskDto.dueDate}</#if>"/>
                        </div>
                        <div id="weekInput" style="display:none; width: 20%">
                            <input name="dueWeek"
                                   id="week"
                                   class="form-control form-control-sm"
                                   type="week"
                                   value="<#if addTaskDto.dueDate??>${addTaskDto.dueDate}</#if>"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>Тэги</th>
                    <!-- Сравниваем список tagList(все тэги из БД) со списком taskTags(тэги этого таска)
                         и отмечаем те чекбоксы, которые есть в списке taskTags -->
                    <td>
                        <#list tagList as labelForCheckbox>
                            <label>
                                <input type="checkbox"
                                       name="selectedTags"
                                       value="${labelForCheckbox}"> ${labelForCheckbox}
                            </label>
                        </#list>
                    </td>
                </tr>
                </tbody>
            </table>
            <button class="btn btn-success btn-sm" type="submit">Save</button>
        </form>

    <script>
        // выбираем дату или неделю из виджетов, и отдаем в контроллер
        const weekInput = document.getElementById("weekInput");
        const dateInput = document.getElementById("dateInput");
        const radios = document.getElementsByName("weekOrDate");
        for(let i = 0; i < radios.length; i++) {
            radios[i].addEventListener("change", function(event) {
                if(event.target.value === "week") {
                    weekInput.style.display = "block";
                    dateInput.style.display = "none";
                    document.getElementById("date").value = "";
                } else if (event.target.value === "date") {
                    dateInput.style.display = "block";
                    weekInput.style.display = "none";
                    document.getElementById("week").value = "";
                }
            });
        }

        // функция выбора города в поле по нажатию на кнопку
        document.addEventListener("DOMContentLoaded", function() {
            // Получаем ссылки на элементы DOM
            const form = document.getElementById("myForm");
            const cityInput = document.getElementById("city");
            const cityButtonContainer = document.getElementById("cityButtonContainer");

            // Обработчик события клика на кнопки
            cityButtonContainer.addEventListener("click", function(event) {
                if (event.target.classList.contains("cityButton")) {
                    // Вставляем текст кнопки в поле input при клике
                    cityInput.value = event.target.textContent;
                    event.preventDefault(); // Предотвращаем отправку формы
                }
            });
        });

        // функция установки ответсвенного в поле по нажатию на кнопку
        document.addEventListener("DOMContentLoaded", function() {
            // Получаем ссылки на элементы DOM
            const form = document.getElementById("myForm");
            const nameInput = document.getElementById("nameInput");
            const buttonContainer = document.getElementById("buttonContainer");

            // Обработчик события клика на кнопки
            buttonContainer.addEventListener("click", function(event) {
                if (event.target.classList.contains("nameButton")) {
                    // Вставляем текст кнопки в поле input при клике
                    nameInput.value = event.target.textContent;
                    event.preventDefault(); // Предотвращаем отправку формы
                }
            });
        });

    </script>
        </form>
    </div>
</div>
</body>
</html>