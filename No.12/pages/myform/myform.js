
Page({
    data:{
        array:[
            { id: "1001", name:"v中国", value:"中国", checked:true, color:"red", disabled:false},
            { id: "1002",name: "v美国", value: "美国", checked: false, color: "blue", disabled: false },
            { id: "1003",name: "v俄国", value: "俄国", checked: true, color: "pink", disabled: false }
        ],
        inputValue : "这是一个文本框",
        getFocus: false
    },

    changed: function(e) {
    },

    formSubmit: function (e) {
        debugger;
    },

    formReset: function () {
        console.log("data has been resetted...");
    },

    getFocus: function(){
        this.setData({
            getFocus:true
        })
    }   
})
