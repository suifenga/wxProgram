const app = getApp()

Page({
    data: {
      poster: '',
      name: '',
      author: '',
      src: '',
      serverUrl:"",
      videoParams:{}
    },
    onLoad:function(params){
      var me = this;
      console.log(params);
     
      me.setData({
        videoParams:params
      })

      wx.showLoading({
        title: '请等待...',
      });
      var serverUrl = app.serverUrl;
      // 调用后端
      wx.request({
        url: serverUrl + '/bgm/list',
        method: "POST",
        header: {
          'content-type': 'application/json', // 默认值
        },
        success: function (res) {
          console.log(res.data);
          wx.hideLoading();
          if (res.data.status == 200) {
            var bgmList = res.data.data;
            me.setData({
              bgmList: bgmList,
              serverUrl: serverUrl
            });
          } else if (res.data.status == 502) {
            wx.showToast({
              title: res.data.msg,
              duration: 2000,
              icon: "none",
              success: function () {
                wx.redirectTo({
                  url: '../userLogin/login',
                })
              }
            });
          }
        }
      })
    },
  upload:function(e){
    var me = this;

    var userInfo = app.getGlobalUserInfo();
    var datasParams = me.data.videoParams;
    var bgmId = e.detail.value.bgmId;
    var desc = e.detail.value.desc;
    console.log("bgmId:"+bgmId);
    console.log("desc:" + desc);
    var tempDuration = datasParams.tempDuration;
    var tempHeight = datasParams.tempHeight;
    var tempWidth = datasParams.tempWidth;
    var tempSize = datasParams.tempSize;
    var tempFilePath = datasParams.tempFilePath;
    var thumbTempFilePath = datasParams.thumbTempFilePath;
    var userId = userInfo.id;


    wx.showLoading({
      title: '请等待...',
    });
    var serverUrl = app.serverUrl;
    // 调用后端
   wx.uploadFile({
     url: serverUrl + '/video/upload',
     filePath: tempFilePath,
     formData:{
       userId: userId,
       bgmId: bgmId,
       videoSeconds: tempDuration,
       videoWidth: tempWidth,
       videoHeight: tempHeight,
       desc: desc,
     },
     name: 'file',
     success:function(res){
      console.log(res);
       var status = JSON.parse(res.data).status;
       debugger;
       wx.hideLoading();
       if (status == 200) {
         wx.showToast({
           title: "上传成功~！",
           icon: 'none',
           duration: 3000
         })
       } else if (status == 500) {
         wx.showToast({
           title: res.data.msg,
           icon: 'none',
           duration: 3000
         })
       }
     }
     
   })
  }
})

