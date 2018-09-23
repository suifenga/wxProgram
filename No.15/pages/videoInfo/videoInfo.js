var videoUtils = require('../../utils/videoUtils.js')
const app = getApp()
Page({

  data: {
    cover:'cover',
    videoContext:"",
    videoInfo:{},
    videId:'',
    src:''
  },
  

  showSearch:function(){
    wx.navigateTo({
      url: '../videoSearch/videoSearch',
    })
  },
  onLoad:function(params){
    var me = this;
    me.videoContext = wx.createVideoContext('myVideo', me);
    var videoInfo = JSON.parse(params.videoInfo);
    var videoWidth = videoInfo.videoWidth;
    var videoHeight = videoInfo.videoHeight;
    var cover = 'cover';
    if (videoWidth > videoHeight){
      cover = '';
    }
    me.setData({
      videId: videoInfo.id,
      src: app.serverUrl + videoInfo.videoPath,
      videoInfo: videoInfo,
      cover: cover
    })


  },
  showIndex:function(){
    wx.redirectTo({
      url: '../index/index',
    })
  },

  onShow:function(){
    var me = this;
    me.videoContext.play();
  },
  onHide:function(){
    var me = this;
    me.videoContext.pause();
  },
  upload:function(){
 
    var me = this;
    var userInfo = app.getGlobalUserInfo();

    var videoInfo = JSON.stringify(me.data.videoInfo);
    var realUrl = '../videoInfo/videoInfo#videoInfo@' + videoInfo;

    if (userInfo.id == '' || userInfo.id == undefined) {
      wx.navigateTo({
        url: '../userLogin/userLogin?realUrl=' + realUrl,
      })
    } else {
      videoUtils.uploadVideo();
    }


  },
  showMine: function () {
    var me = this;
    var userInfo = app.getGlobalUserInfo();

    var videoInfo = JSON.parse

    if (userInfo.id == '' || userInfo.id == undefined){
      wx.navigateTo({
        url: '../userLogin/userLogin',
      })
    }else{
      wx.navigateTo({
        url: '../mine/mine',
      })
    }
    
   
  },
})
