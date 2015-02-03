function changeDivStyle(){
    var o_status = $("#o_status").val();	//获取隐藏框值
	if(o_status==1){
		$('#create').css('background', '#DD0000');
		$('#createText').css('color', '#DD0000');
       $('#fileContent').load("fileprocess/fileUpload.html");
       $('#preDiv').css("display","none");
	}else if(o_status==2){
		$('#check').css('background', '#DD0000');
		$('#checkText').css('color', '#DD0000');
        $('#fileContent').load("fileprocess/filepro.html");
        $('#preDiv').css("display","block");
	}else if(o_status==3){
		$('#produce').css('background', '#DD0000');
		$('#produceText').css('color', '#DD0000');
        $('#fileContent').load("fileprocess/downloadFile.html");
        $('#preDiv').css("display","block");
	}else if(o_status==4){
		$('#delivery').css('background', '#DD0000');
		$('#deliveryText').css('color', '#DD0000');
        $('#fileContent').load("fileprocess/finished.html");
        $('#preDiv').css("display","block");
	}else if(o_status>=5){
		$('#received').css('background', '#DD0000');
		$('#receivedText').css('color', '#DD0000');
        $('#fileContent').load("fileprocess/choseWhere.html");
        $('#preDiv').css("display","block");
	}
}