$.namespace('TcaCommCd.settings');
TcaCommCd.settings = {

    fields :
    [
        {
            fieldName : "commGrpCd", // fieldType = String
        },
        {
            fieldName : "commCd", // fieldType = String
        },
        {
            fieldName : "commCdDesc", // fieldType = String
        },
        {
            fieldName : "sortSord", // fieldType = int
            fieldType : "number"
        },
        {
            fieldName : "useYn", // fieldType = String
        },
        {
            fieldName : "fstRegpId", // fieldType = String
        },
        {
            fieldName : "fstRegDtm", // fieldType = Timestamp
            fieldType : "datetime",
            datetimeFormat : "iso"
        },
        {
            fieldName : "lastModpId", // fieldType = String
        },
        {
            fieldName : "lastModDtm", // fieldType = Timestamp
            fieldType : "datetime",
            datetimeFormat : "iso"
        }
    ],


    columns :
    [
        {
            name : "commGrpCd",
            fieldName : "commGrpCd",
            header : {
                text : MESSAGES.format("ca.label.TcaCommCd.commGrpCd")
            },
            width : 100,
            editable : false,
            styles : {
                textAlignment : "left"
            },
            visible : true
        },        
        {
            name : "commCd",
            fieldName : "commCd",
            header : {
                text : MESSAGES.format("ca.label.TcaCommCd.commCd")
            },
            width : 100,
            editable : false,
            styles : {
                textAlignment : "left"
            },
            visible : true
        },        
        {
            name : "commCdDesc",
            fieldName : "commCdDesc",
            header : {
                text : MESSAGES.format("ca.label.TcaCommCd.commCdDesc")
            },
            width : 100,
            editable : false,
            styles : {
                textAlignment : "left"
            },
            visible : true
        },        
        {
            name : "sortSord",
            fieldName : "sortSord",
            header : {
                text : MESSAGES.format("ca.label.TcaCommCd.sortSord")
            },
            width : 100,
            editable : false,
            styles : {
                textAlignment : "left"
            },
            visible : true
        },        
        {
            name : "useYn",
            fieldName : "useYn",
            header : {
                text : MESSAGES.format("ca.label.TcaCommCd.useYn")
            },
            width : 100,
            editable : false,
            styles : {
                textAlignment : "left"
            },
            visible : true
        },        
        {
            name : "fstRegpId",
            fieldName : "fstRegpId",
            header : {
                text : MESSAGES.format("ca.label.TcaCommCd.fstRegpId")
            },
            width : 100,
            editable : false,
            styles : {
                textAlignment : "left"
            },
            visible : true
        },        
        {
            name : "fstRegDtm",
            fieldName : "fstRegDtm",
            header : {
                text : MESSAGES.format("ca.label.TcaCommCd.fstRegDtm")
            },
            width : 100,
            editable : false,
            styles : {
                textAlignment : "left"
            },
            visible : true
        },        
        {
            name : "lastModpId",
            fieldName : "lastModpId",
            header : {
                text : MESSAGES.format("ca.label.TcaCommCd.lastModpId")
            },
            width : 100,
            editable : false,
            styles : {
                textAlignment : "left"
            },
            visible : true
        },        
        {
            name : "lastModDtm",
            fieldName : "lastModDtm",
            header : {
                text : MESSAGES.format("ca.label.TcaCommCd.lastModDtm")
            },
            width : 100,
            editable : false,
            styles : {
                textAlignment : "left"
            },
            visible : true
        }        
    ], // columns

    props : {
        paging : true,
        defrow : 5,
        rows : [ 5, 100, 200, 500, 1000, 5000, 10000 ], 
        width : "100%", 
        height : "300px", 
        autoFitHeight : true,
        checkbox : true,
        crud : true,
        form : "inputForm",
        action : CONSTANTS.baseUrl + "CodeManagement.list.do",
        saveAction : CONSTANTS.baseUrl + "CodeManagement.saveCUD.do"
    }
}