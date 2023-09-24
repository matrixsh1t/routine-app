<!DOCTYPE html>
<html lang="en">
<head>
    <title>Админ-панель</title>
    <link rel="icon" type="image/x-icon" href="/img/bar.svg">
    <link rel="stylesheet" href="/css/admin-panel.css">
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
</head>
<body>
<header>
    <h1>Админ-панель</h1>
</header>
<nav>
    <ul>
        <li><a href="/">Главная</a></li>
        <li><a href="/admin/account-register">Добавить эккаунт</a></li>
        <li><a href="#">Статистика</a></li>
        <li><a href="#">Выход</a></li>
    </ul>
</nav>
<main>
    <h2>Добро пожаловать в админ-панель!</h2>
    <h4>Список зарегестрированных эккаунтов</h4>
    <table class="table table-striped table-bordered table-sm">
        <thead class="thead-light">
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Username</th>
            <th scope="col">Email</th>
            <th scope="col" style="text-align: center">Role</th>
        </tr>
        </thead>
        <tbody>
            <#list accounts as account>
                <tr>
                    <td class="w-25 p-3">${account.id}</td>
                    <td class="w-25 p-3">${account.username}</td>
                    <td class="w-25 p-3">${account.email}</td>
                    <td class="w-25 p-3">${account.role}</td>
                    <td>
                        <a href="/admin/delete/${account.id}"
                        class="btn btn-danger btn-sm"
                        role="button">Удалить</a>
                    </td>
                </tr>
            </#list>
        </tbody>
    </table>
</main>
<footer>
    <p>© 2023 Админ-панель. Все права защищены.</p>
</footer>
</body>
</html>
