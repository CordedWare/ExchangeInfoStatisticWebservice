$(window).on("load", function() {
    $("#uploadNotification").addClass("show");

    setTimeout(function() {
        $("#uploadNotification").removeClass("show");
    }, 5000);
});