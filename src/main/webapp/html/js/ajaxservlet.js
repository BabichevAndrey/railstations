//$(document).ready(function(){
//    $('#add_way_dep_station').chosen();
//    $('#add_way_arrival_station').chosen();
//});


$(document).ready(function(){


//   $('#add_way_dep_station').chosen().change();;
//   $('#add_way_arrival_station').chosen().change();

$('#station_pag_next').on('click', function(e){
    e.preventDefault();

   var pagerow = $('#pagerow').val();
   var pagerow_out = parseInt(pagerow) + 1;
   $('#pagerow').val(pagerow_out);
   $('#pag_form').submit();

});

$('#station_pag_pre').on('click', function(e){
    e.preventDefault();

   var pagerow = $('#pagerow').val();
   var pagerow_out = parseInt(pagerow) - 1;
   $('#pagerow').val(pagerow_out);
   $('#pag_form').submit();

});

$('#rows_on_page').on('change', function(e){
    e.preventDefault();
   $('#pag_form').submit();

});


$('#add_new_station').on('click', function(e){
    e.preventDefault();

    var addst = $('#add_station').val();

    if (addst == '') {
        $('#add_station').css('border', '1px solid red');
        alert('Station must have a name!');
    } else {
        $('#add_station_form').submit();
    }


});


$('.delete_station').on('click', function(e){

    e.preventDefault();

    var st_id = $(this).attr('id')
    var cid = st_id.substring(3);

    $('#dialog-confirm').dialog({
      resizable: false,
      height: 'auto',
      width: 400,
      modal: true,
      buttons: {
        'Yes':function() {
                            $(this).dialog('close');
                                  $.ajax({
                                      url : 'ajaxservlet',
                                      data : {
                                          stationid : cid
                                      },
                                      success : function(response) {
                                              $('#dialog-confirm-done').attr('title', response);
                                              if (response == 'Station has been deleted'){ $('#st_row_'+cid).hide(); }
                                              $('#dialog-confirm-done').dialog({
                                                        resizable: false,
                                                        height: 'auto',
                                                        width: 400,
                                                        modal: true,
                                                        buttons: {
                                                          'OK': function() {
                                                                        $(this).dialog('close');
                                                                }
                                                        }

                                              });

                                      }
                                  });
                          },


//         function() {
//          $(this).dialog('close');
//                $.get("ajaxservlet?stationid="+cid);
//                $('#st_row_'+cid).hide();
//        },
        'No': function() {
          $(this).dialog('close');
        }
      }
    });

	});


	$('.delete_way').on('click', function(e){

        e.preventDefault();

        var w_id = $(this).attr('id')
        var wid = w_id.substring(7);

        $('#dialog-confirm').dialog({
          resizable: false,
          height: 'auto',
          width: 400,
          modal: true,
          buttons: {
            'Yes': function() {
              $(this).dialog('close');
                    $.ajax({
                        url : 'ajaxservlet',
                        data : {
                            wayid : wid
                        },
                        success : function(response) {
                                $('#dialog-confirm-done').attr('title', response);
                                if (response == 'Way has been deleted'){ $('#way_row_'+wid).hide(); }
                                $('#dialog-confirm-done').dialog({
                                          resizable: false,
                                          height: 'auto',
                                          width: 400,
                                          modal: true,
                                          buttons: {
                                            'OK': function() {
                                                          $(this).dialog('close');
                                                  }
                                          }

                                });

                        }
                    });

//                    $.get("ajaxservlet?wayid="+wid);
//                    alert("hide");
//                    $('#way_row_'+wid).hide();

            },
            'No': function() {
              $(this).dialog('close');
            }
          }
        });
});


});





