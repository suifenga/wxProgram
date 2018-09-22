function uploadVideo() {
  var me = this
  wx.chooseVideo({
    sourceType: ['album', 'camera'],
    success: function (res) {
      console.log(res);
      var tempDuration = res.duration;
      var tempHeight = res.height;
      var tempWidth = res.width;
      var tempSize = res.size;
      var tempFilePath = res.tempFilePath;
      var thumbTempFilePath = res.thumbTempFilePath;
      if (tempDuration > 20) {
        wx.showToast({
          title: "视频太长了老铁不稳~",
          icon: 'none',
          duration: 3000
        })
      } else if (tempDuration < 5) {
        wx.showToast({
          title: "视频太短了不到5秒。老铁不稳~",
          icon: 'none',
          duration: 3000
        })
      } else {
        wx.navigateTo({
          url: '../chooseBgm/chooseBgm?tempDuration=' + tempDuration
            + '&tempHeight=' + tempHeight
            + '&tempWidth=' + tempWidth
            + '&tempSize=' + tempSize
            + '&tempFilePath=' + tempFilePath
            + '&thumbTempFilePath=' + thumbTempFilePath
        })
      }
    }
  })
}

module.exports={
  uploadVideo: uploadVideo
}