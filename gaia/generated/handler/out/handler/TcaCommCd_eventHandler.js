$.namespace('TcaCommCd.eventhandler');
TcaCommCd.eventhandler = {

    bindButtonEvent : function() {

        var self = this;
        var grid = self.grid;

        $('#btn_InputTbl_excel').click(function(event) {
            if(grid.getItemCount() == 0) {
                alert(_noSearchMsg);
                return;
            }
            self.onExcelExport();
        });
    },

    gridEvent : {
        onDataCellClicked : function(grid, index) {
        },

        afterQuerySuccess : function(grid, data) {
        },

    },

    // 조회
    onSearch : function(pageIdx) {

        this.grid.cancel();

        pageIdx = !pageIdx ? 0 : pageIdx;
        var self = this;
        var pagingFunc = function(pageIdx) {
            return self.onSearch(pageIdx);
        };

        this.controller.doQuery(this, "", pageIdx, pagingFunc);
    },

    // 엑셀 다운로드
    onExcelExport : function() {

        var self = this;
        var grid = self.grid;

        self.defaultHandler.onExcelExport(grid, "Input Table");

    }
}