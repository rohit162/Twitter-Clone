<html>
<body>
    <form>
        USER LOGIN!
        <br>
        <br>
        <div id="time">

        </div>
        <br>
        <br>

        <div>
            <marquee> Made with Love by Divya Chopra </marquee>
        </div>
        <script type="text/javascript">

            function updateTime(){
                document.getElementById("time").innerText = new Date().toString();
            }
            setInterval(updateTime,1000);

        </script>
    </form>
</body>
</html>