//events.js
//获取应用实例
const app = getApp()

Page({
  data: {
    motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  clickMe: function(e){
    console.log("你点击我这里出来了!")
    console.log(e)
    console.log(e.currentTarget.dataset.fordate)
  }
})
