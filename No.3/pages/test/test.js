//test.js
Page({
  data: {
    motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  onLoad: function () {
    //获取应用实例
    const app = getApp();
    //获取应用实例里面的全局变量
    console.log(app.globalData);
    this.setData({
      //全局变量赋值给页面的变量
      motto: app.globalData
    })
  }
})