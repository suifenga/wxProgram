//form.js

Page({
  inputEvent:function(){
    console.log("inputEvent");
    return "结果很意外吧这个被替换了"
  },
  focusEvent: function () {
    console.log("focusEvent");
  },
  blurEvent: function () {
    console.log("blurEvent");
  },
  confirmEvent: function () {
    console.log("confirmEvent");
  },
})
