//progress.js
//获取应用实例
const app = getApp()

Page({
  data:{
    mypercent:15
  },
  addpercent:function(){
    var newpercent = this.data.mypercent+20;
    this.setData({
      mypercent: newpercent
    })
  }
})
