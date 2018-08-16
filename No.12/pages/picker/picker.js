//picker.js

Page({
 data:{
   array:['一','二','三','四','五','六','七'],
   arrayObject:[
     { id: 1001, name: '个人网址'},
     { id: 1002, name: 'idig8.com' },
     { id: 1003, name: '公众号' },
     { id: 1004, name: '编程坑太多' },
   ],
   myTitle:'请选择点击确定显示结果',
   arraymult:[
     ['一', '二', '三', '四', '五', '六', '七'],
     ['一', '二', '三', '四', '五', '六', '七']
   ],
   arraymultObject: [
     [
       { id: 1001, name: '个人网址' },
       { id: 1002, name: 'idig8.com' },
       { id: 1003, name: '公众号' },
       { id: 1004, name: '编程坑太多' },
     ],
     [
       { id: 1001, name: '个人网址' },
       { id: 1002, name: 'idig8.com' },
       { id: 1003, name: '公众号' },
       { id: 1004, name: '编程坑太多' },
     ]
   ],
   timeLable: "请选择时间",
   dateLable: "请选择日期",
   cityLable: "请选择城市",
   region: ['河南省', '郑州市', '中原区'],
   customItem: "显示全部"
 },
  mychange:function(){
    console.log(' mychange改变了');
  },
  mycancel:function(){
    console.log(' mycancel取消选择了');
  },
  mychangeResult:function(e){
    debugger
    console.log(' mychangeResult 点击确定');
    var index = e.detail.value;
    var id = this.data.arrayObject[index].id;
    var name = this.data.arrayObject[index].name;
    this.setData({
      myTitle:id+name
    })
  },
  mychangemulticolumn:function(e){
    console.log(e.detail);
  },
  mychangemulti: function (e) {

    var indexs = e.detail.value;
    var arraymultObject = this.data.arraymultObject;
    for (var i = 0; i < indexs.length; i++) {
      var indexTemp = indexs[i];
      var id = arraymultObject[i][indexTemp].id;
      var name = arraymultObject[i][indexTemp].name;
      console.log(id + name);
    }
  },
  changeTime: function (e) {
    this.setData({
      timeLable: e.detail.value
    });
  },

  changeDate: function (e) {
    this.setData({
      dateLable: e.detail.value
    });
  },

  changeCity: function (e) {
    debugger;
    var codes = "";
    var names = "";
    for(var i = 0; i<e.detail.code.length; i++){
        var code = e.detail.code[i];
        var name = e.detail.value[i];
        codes += code;
        names +=name;
        
    }
    this.setData({
      cityLable: codes+names
    });
  }
})
