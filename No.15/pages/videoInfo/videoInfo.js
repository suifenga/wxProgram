
Page({

  data: {
    cover:'cover',
  },
  showSearch:function(){
    wx.navigateTo({
      url: '../videoSearch/videoSearch',
    })
  }
})
