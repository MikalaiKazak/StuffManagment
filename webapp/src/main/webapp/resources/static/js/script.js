function showOneDate() {
  $("#oneDate_div").removeClass("none");
  $("#oneDate_div").addClass("choice");

  $("#twoDate_div").removeClass("choice");
  $("#twoDate_div").addClass("none");
}

function showTwoDate() {
  $("#twoDate_div").removeClass("none");
  $("#twoDate_div").addClass("choice");

  $("#oneDate_div").removeClass("choice");
  $("#oneDate_div").addClass("none");
}

function deleteButton(url) {
  bootbox.confirm({
    message: "Are you sure you want to delete?",
    buttons: {
      confirm: {
        label: 'Yes',
        className: 'btn-success'
      },
      cancel: {
        label: 'No',
        className: 'btn-danger'
      }
    },
    callback: function (result) {
      if (result) {
        window.location.href = url;
      }
      if (!result) {
      }
    }
  });
};

$('form').submit(function (e) {
  var currentForm = this;
  e.preventDefault();
  bootbox.confirm("Are you sure?", function (result) {
    if (result) {
      currentForm.submit();
    }
  });
});

