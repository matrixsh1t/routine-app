<!doctype html>
<html lang="en">
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
        Tasks
    </h2>
    <div>
        <form action="/"
              method="post" name="addTaskDto">
            <table class="table">
                <tr>
                    <th>Task</th>
                    <td><input name="task" type="text"
                               value=""/>
                    </td>
                </tr>
                <tr>
                    <th>Comment</th>
                    <td><input name="comment" type="text"
                               value=""/></td>
                </tr>
            </table>
            <button class="btn btn-primary" type="submit">Save</button>
        </form>
        <div>${addTaskDto.task}</div>
        <div>${addTaskDto.comment}</div>
    </div>
</div>
</body>
</html>

