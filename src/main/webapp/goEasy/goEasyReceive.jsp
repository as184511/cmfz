<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" language="java" %>
<script type="text/javascript" src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>

<script>


    //接收信息
    var goEasy = new GoEasy({
        appkey: "BC-07858a5c21ad4fefaa551ffab72e40bb"
    });
    goEasy.subscribe({
        channel: "cmfz",
        onMessage: function (message) {
            alert("Channel:" + message.channel + " content:" + message.content);

        }
    });


</script>