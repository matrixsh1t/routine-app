<!doctype html>
<head>
    <title>Freemarker Example</title>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1" name="viewport">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"
          rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js">
    </script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js">
    </script>
</head>
<body>
<div class="container">
    <h2>
        Update Task
    </h2>
    <div>
        <form action="/update/${addTaskDto.taskId}" method="post" name="addTaskDto">
            <table class="table">
                <tbody>
                <thead>
                <tr>
                    <th>Field</th>
                    <th>Value</th>
                </tr>
                </thead>
                <tbody>
                    <tr>
                        <th>ID</th>
                        <td>
                            <div name="id">"ID"</div>
                        </td>
                    </tr>
                <tr>
                    <th>First Name</th>
                    <td><input name="Task" type="text" value="<#if addTaskDto.task??>${addTaskDto.task}</#if>"/>
                    </td>
                </tr>
                <tr>
                    <th>Last Name</th>
                    <td><input name="Comment" type="text" value="<#if addTaskDto.comment??>${addTaskDto.comment}</#if>"/></td>
                </tr>
                </tbody>
            </table>
            <button class="btn btn-primary" type="submit">Update</button>
        </form>
    </div>
</div>
</body>
</html>