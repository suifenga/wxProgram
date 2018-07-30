//index.js
Page({
  data: {
    motto: '测试下数据绑定',
    testoutcss: '测试外部css样式',
    globalcss: '全局样式',    
    color:"red",
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  }
})
