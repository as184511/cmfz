<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" language="java" %>
<script type="text/javascript" src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>
<script type="text/javascript" src="../js/jquery.min.js"></script>

<script>
    $(function () {


        //发送消息
        var goEasy = new GoEasy({
            appkey: "BC-07858a5c21ad4fefaa551ffab72e40bb"
        });

        goEasy.publish({
            channel: "cmfz",
            message: "Hello, 777!"
        });
    })

</script>