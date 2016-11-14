$.namespace("ca.CodeManagement.list");
ca.CodeManagement.list = {

    initFormLoad : function(){

        console.log("binding init...");

    },

    bindButtonEvent : function(){
        console.log("binding events...");

        // 엔터 입력 시 조회처리
        $(document).ready(function() {
//            lotteindo.common.Util.setupEnterSearch("inputForm", "btn_search");
        });

        // 초기화
        $('#btn_init').click(function(event){
            $('#inputForm')[0].reset();
        });

        // 검색
        $('#btn_search').click(function(){
//            inputTblGrid.eventhandler.onSearch();
        });

        console.log("events bounded...");
    }

};